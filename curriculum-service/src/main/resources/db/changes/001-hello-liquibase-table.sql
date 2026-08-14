--liquibase formatted sql

--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'hello_james_get_started_liquibase';

--changeset James:001
CREATE TABLE IF NOT EXISTS hello_james_get_started_liquibase
(
    id         BIGSERIAL PRIMARY KEY,

    message    VARCHAR(255),

    is_active  BOOLEAN NOT NULL DEFAULT TRUE,
    version    BIGINT  NOT NULL DEFAULT 0,
    created_at BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
    updated_at BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

--rollback DROP TABLE hello_james_get_started_liquibase;