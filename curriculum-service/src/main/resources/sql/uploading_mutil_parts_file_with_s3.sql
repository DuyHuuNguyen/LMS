CREATE TABLE uploading_sessions
(
    id         BIGSERIAL PRIMARY KEY,

    part_size  BIGINT,

    s3_upload_id  varchar(200),

    bucket     VARCHAR(200),

    object_key VARCHAR(256),

    status     VARCHAR(100),

    is_active  BOOLEAN NOT NULL DEFAULT TRUE,
    version    BIGINT  NOT NULL DEFAULT 0,

    created_at BIGINT  NOT NULL DEFAULT (
        EXTRACT(EPOCH FROM NOW()) * 1000
        )::BIGINT,

    updated_at BIGINT  NOT NULL DEFAULT (
        EXTRACT(EPOCH FROM NOW()) * 1000
        )::BIGINT
);

CREATE TABLE part_files
(
    id                   BIGSERIAL PRIMARY KEY,

    part_number          INTEGER,

    content_length       BIGINT,

    etag                 VARCHAR(200),

    uploading_session_id BIGINT,

    is_active            BOOLEAN NOT NULL DEFAULT TRUE,
    version              BIGINT  NOT NULL DEFAULT 0,

    created_at           BIGINT  NOT NULL DEFAULT (
        EXTRACT(EPOCH FROM NOW()) * 1000
        )::BIGINT,

    updated_at           BIGINT  NOT NULL DEFAULT (
        EXTRACT(EPOCH FROM NOW()) * 1000
        )::BIGINT,

    CONSTRAINT fk_part_files_uploading_session
        FOREIGN KEY (uploading_session_id)
            REFERENCES uploading_sessions (id)
            ON DELETE CASCADE
);

