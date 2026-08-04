
--  One curriculum one
CREATE TABLE learning_progressives
(
    id            BIGSERIAL PRIMARY KEY,

    user_curriculum_id       BIGINT,
    learning_minutes integer,

    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    version       BIGINT  NOT NULL DEFAULT 0,

    created_at    BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
    updated_at    BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);
