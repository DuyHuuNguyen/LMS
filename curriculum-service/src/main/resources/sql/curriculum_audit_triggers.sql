-- Recalculate curriculum audit totals whenever sessions/videos/exams are inserted.
-- If curriculum_audits row does not exist for a curriculum, insert it automatically.

-- Compatibility/migration for older curriculum_audits schemas.
DO $$
BEGIN
  IF EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'tota_sessions'
  ) AND NOT EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'total_sessions'
  ) THEN
    ALTER TABLE curriculum_audits RENAME COLUMN tota_sessions TO total_sessions;
  END IF;

  IF EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'tota_videos'
  ) AND NOT EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'total_videos'
  ) THEN
    ALTER TABLE curriculum_audits RENAME COLUMN tota_videos TO total_videos;
  END IF;

  IF EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'total_exam'
  ) AND NOT EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'total_exams'
  ) THEN
    ALTER TABLE curriculum_audits RENAME COLUMN total_exam TO total_exams;
  END IF;

  IF EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'duration_seconds'
  ) AND NOT EXISTS (
    SELECT 1
    FROM information_schema.columns
    WHERE table_name = 'curriculum_audits' AND column_name = 'total_duration_seconds'
  ) THEN
    ALTER TABLE curriculum_audits RENAME COLUMN duration_seconds TO total_duration_seconds;
  END IF;
END $$;

ALTER TABLE curriculum_audits
  ADD COLUMN IF NOT EXISTS total_sessions INTEGER,
  ADD COLUMN IF NOT EXISTS total_videos INTEGER,
  ADD COLUMN IF NOT EXISTS total_exams INTEGER,
  ADD COLUMN IF NOT EXISTS total_duration_seconds BIGINT;

ALTER TABLE curriculum_audits
  ALTER COLUMN total_sessions SET DEFAULT 0,
  ALTER COLUMN total_videos SET DEFAULT 0,
  ALTER COLUMN total_exams SET DEFAULT 0,
  ALTER COLUMN total_duration_seconds SET DEFAULT 0;

UPDATE curriculum_audits
SET total_sessions = COALESCE(total_sessions, 0),
    total_videos = COALESCE(total_videos, 0),
    total_exams = COALESCE(total_exams, 0),
    total_duration_seconds = COALESCE(total_duration_seconds, 0);

DO $$
BEGIN
  IF NOT EXISTS (
    SELECT 1
    FROM pg_constraint
    WHERE conname = 'uk_curriculum_audits_curriculum_id'
  ) THEN
    ALTER TABLE curriculum_audits
      ADD CONSTRAINT uk_curriculum_audits_curriculum_id UNIQUE (curriculum_id);
  END IF;
END $$;

CREATE OR REPLACE FUNCTION refresh_curriculum_audit(p_curriculum_id BIGINT)
RETURNS VOID
LANGUAGE plpgsql
AS $$
DECLARE
  v_now BIGINT := (EXTRACT(EPOCH FROM clock_timestamp()) * 1000)::BIGINT;
BEGIN
  IF p_curriculum_id IS NULL THEN
    RETURN;
  END IF;

  INSERT INTO curriculum_audits (
    curriculum_id,
    total_sessions,
    total_videos,
    total_exams,
    total_duration_seconds,
    version,
    is_active,
    created_at,
    updated_at
  )
  VALUES (
    p_curriculum_id,
    (
      SELECT COUNT(*)::INTEGER
      FROM sessions s
      WHERE s.curriculum_id = p_curriculum_id
        AND COALESCE(s.is_active, TRUE) = TRUE
    ),
    (
      SELECT COUNT(*)::INTEGER
      FROM videos v
      JOIN sessions s ON s.id = v.session_id
      WHERE s.curriculum_id = p_curriculum_id
        AND COALESCE(v.is_active, TRUE) = TRUE
    ),
    (
      SELECT COUNT(*)::INTEGER
      FROM exams e
      JOIN sessions s ON s.id = e.session_id
      WHERE s.curriculum_id = p_curriculum_id
        AND COALESCE(e.is_active, TRUE) = TRUE
    ),
    (
      SELECT COALESCE(SUM(v.duration_seconds), 0)::BIGINT
      FROM videos v
      JOIN sessions s ON s.id = v.session_id
      WHERE s.curriculum_id = p_curriculum_id
        AND COALESCE(v.is_active, TRUE) = TRUE
    ),
    0,
    TRUE,
    v_now,
    v_now
  )
  ON CONFLICT (curriculum_id)
  DO UPDATE
    SET total_sessions = EXCLUDED.total_sessions,
        total_videos = EXCLUDED.total_videos,
        total_exams = EXCLUDED.total_exams,
        total_duration_seconds = EXCLUDED.total_duration_seconds,
        is_active = TRUE,
        updated_at = v_now;
END;
$$;

CREATE OR REPLACE FUNCTION refresh_curriculum_audit_by_session(p_session_id BIGINT)
RETURNS VOID
LANGUAGE plpgsql
AS $$
DECLARE
  v_curriculum_id BIGINT;
BEGIN
  SELECT s.curriculum_id
  INTO v_curriculum_id
  FROM sessions s
  WHERE s.id = p_session_id;

  IF v_curriculum_id IS NULL THEN
    RETURN;
  END IF;

  PERFORM refresh_curriculum_audit(v_curriculum_id);
END;
$$;

CREATE OR REPLACE FUNCTION trg_refresh_curriculum_audit_after_session_insert()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
  PERFORM refresh_curriculum_audit_by_session(NEW.id);
  RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION trg_refresh_curriculum_audit_after_video_insert()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
  PERFORM refresh_curriculum_audit_by_session(NEW.session_id);
  RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION trg_refresh_curriculum_audit_after_exam_insert()
RETURNS TRIGGER
LANGUAGE plpgsql
AS $$
BEGIN
  PERFORM refresh_curriculum_audit_by_session(NEW.session_id);
  RETURN NEW;
END;
$$;

DROP TRIGGER IF EXISTS after_video_insert_refresh_curriculum_audit ON videos;
CREATE TRIGGER after_video_insert_refresh_curriculum_audit
AFTER INSERT ON videos
FOR EACH ROW
EXECUTE FUNCTION trg_refresh_curriculum_audit_after_video_insert();

DROP TRIGGER IF EXISTS after_exam_insert_refresh_curriculum_audit ON exams;
CREATE TRIGGER after_exam_insert_refresh_curriculum_audit
AFTER INSERT ON exams
FOR EACH ROW
EXECUTE FUNCTION trg_refresh_curriculum_audit_after_exam_insert();

DROP TRIGGER IF EXISTS after_session_insert_refresh_curriculum_audit ON sessions;
CREATE TRIGGER after_session_insert_refresh_curriculum_audit
AFTER INSERT ON sessions
FOR EACH ROW
EXECUTE FUNCTION trg_refresh_curriculum_audit_after_session_insert();
