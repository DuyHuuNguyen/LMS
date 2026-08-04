CREATE TABLE essays
(
    id         BIGSERIAL PRIMARY KEY,

    "index"    INTEGER      NOT NULL UNIQUE,
    question   VARCHAR(255) NOT NULL,
    answer     VARCHAR(255) NOT NULL,

    exam_id    BIGINT       NOT NULL,

    is_active  BOOLEAN      NOT NULL DEFAULT TRUE,
    version    BIGINT       NOT NULL DEFAULT 0,

    created_at BIGINT       NOT NULL DEFAULT ((EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT) ,
     updated_at BIGINT NOT NULL DEFAULT ((EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT)
)
