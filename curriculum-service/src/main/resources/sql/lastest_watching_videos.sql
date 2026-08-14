

--liquibase formatted sql

--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'lastest_watching_videos';

--changeset James:003

CREATE TABLE lastest_watching_videos
(
    id            BIGSERIAL PRIMARY KEY,

    curriculum_id BIGINT,
    session_id    BIGINT,
    content_id    BIGINT,
    user_id       BIGINT,
    paused_at     integer,
    content_type  varchar(100),

    is_active     BOOLEAN NOT NULL DEFAULT TRUE,
    version       BIGINT  NOT NULL DEFAULT 0,

    created_at    BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
    updated_at    BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);
