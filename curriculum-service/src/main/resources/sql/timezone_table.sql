
CREATE TABLE time_zones
(
    id          BIGSERIAL PRIMARY KEY,

    code        VARCHAR(100) NOT NULL UNIQUE,   -- Asia/Ho_Chi_Minh
    display_name VARCHAR(200) NOT NULL,         -- Việt Nam (GMT+07:00)

    is_active   BOOLEAN NOT NULL DEFAULT TRUE,
    version     BIGINT NOT NULL DEFAULT 0,

    created_at  BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
    updated_at  BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
);

-- data
INSERT INTO time_zones (code, display_name) VALUES
('UTC', 'UTC (GMT+00:00)'),
('Asia/Ho_Chi_Minh', 'Việt Nam (GMT+07:00)'),
('Asia/Bangkok', 'Thailand (GMT+07:00)'),
('Asia/Singapore', 'Singapore (GMT+08:00)'),
('Asia/Tokyo', 'Japan (GMT+09:00)'),
('Asia/Seoul', 'South Korea (GMT+09:00)'),
('Asia/Shanghai', 'China (GMT+08:00)'),
('Europe/London', 'United Kingdom (GMT±00:00)'),
('Europe/Paris', 'France (GMT+01:00)'),
('America/New_York', 'Eastern Time (US & Canada)'),
('America/Chicago', 'Central Time (US & Canada)'),
('America/Denver', 'Mountain Time (US & Canada)'),
('America/Los_Angeles', 'Pacific Time (US & Canada)'),
('Australia/Sydney', 'Australia (Sydney)');

create table user_time_zones(
    id                   BIGSERIAL PRIMARY KEY,

    user_id              BIGINT,
    time_zone_id         BIGINT,

    is_active            BOOLEAN NOT NULL DEFAULT TRUE,
    version              BIGINT  NOT NULL DEFAULT 0,

    created_at           BIGINT  NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT,
    updated_at           BIGINT  NOT NULL DEFAULT ( EXTRACT(EPOCH FROM NOW()) * 1000)::BIGINT
)