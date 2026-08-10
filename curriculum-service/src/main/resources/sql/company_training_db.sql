CREATE TABLE "companies"
(
    "id"                    BIGSERIAL PRIMARY KEY NOT NULL,
    "company_name"          varchar(255),
    "user_admin_company_id" bigserial,
    "is_active"             boolean               NOT NULL DEFAULT true,
    "version"               bigint                NOT NULL DEFAULT 0,
    "created_at"            bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"            bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "company_possess_curriculums"
(
    "id"            BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id"    bigint                NOT NULL,
    "curriculum_id" bigint                NOT NULL,
    "is_active"     boolean               NOT NULL DEFAULT true,
    "version"       bigint                NOT NULL DEFAULT 0,
    "created_at"    bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"    bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

create table "curriculum_training_sets"
(
    "id"                BIGSERIAL PRIMARY KEY NOT NULL,
    "company_id"        bigint                NOT NULL,
    "training_set_name" varchar(255)          not null,
    "is_active"         boolean               NOT NULL DEFAULT true,
    "version"           bigint                NOT NULL DEFAULT 0,
    "created_at"        bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"        bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

create table "element_training_in_sets"
(
    "id"                            BIGSERIAL PRIMARY KEY NOT NULL,
    "curriculum_training_set_id"    bigint                NOT NULL,
    "company_possess_curriculum_id" bigint                NOT NULL,
    "is_active"                     boolean               NOT NULL DEFAULT true,
    "version"                       bigint                NOT NULL DEFAULT 0,
    "created_at"                    bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"                    bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

create table "curriculum_training_set_training_sessions"
(

    "id"                         BIGSERIAL PRIMARY KEY NOT NULL,
    "curriculum_training_set_id" bigint                NOT NULL,
    "training_session_id"        bigint                NOT NULL,
    "is_active"                  boolean               NOT NULL DEFAULT true,
    "version"                    bigint                NOT NULL DEFAULT 0,
    "created_at"                 bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"                 bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint

);


CREATE TABLE "groups"
(
    "id"                  BIGSERIAL PRIMARY KEY NOT NULL,
    "user_admin_group_id" bigserial             NOT NULL,
    "company_id"          bigserial             NOT NULL,
    "max_group_size"      int                   NOT NULL,
    "group_name"          varchar(100)          NOT NULL,
    "is_active"           boolean               NOT NULL DEFAULT true,
    "version"             bigint                NOT NULL DEFAULT 0,
    "created_at"          bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"          bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "group_members"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "group_id"   bigint                NOT NULL,
    "user_id"    bigint                NOT NULL,
    "is_active"  boolean               NOT NULL DEFAULT true,
    "version"    bigint                NOT NULL DEFAULT 0,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "training_sessions"
(
    "id"         BIGSERIAL PRIMARY KEY NOT NULL,
    "group_id"   bigserial             NOT NULL,
    "started_at" bigint,
    "ended_at"   bigint,
    "name"       varchar(200),
    "is_active"  boolean               NOT NULL DEFAULT true,
    "version"    bigint                NOT NULL DEFAULT 0,
    "created_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at" bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "training_exams"
(
    "id"                  BIGSERIAL PRIMARY KEY NOT NULL,
    "training_session_id" bigint                NOT NULL,
    "exam_name"           varchar(100)          NOT NULL,
    "is_active"           boolean               NOT NULL DEFAULT true,
    "version"             bigint                NOT NULL DEFAULT 0,
    "created_at"          bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"          bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "training_multiple_choice_tests"
(
    "id"               BIGSERIAL PRIMARY KEY NOT NULL,
    "index"            integer UNIQUE        NOT NULL,
    "question"         varchar(255)          NOT NULL,
    "chooses"          jsonb,
    "answer"           varchar(10),
    "training_exam_id" bigint                NOT NULL,
    "is_active"        boolean               NOT NULL DEFAULT true,
    "version"          bigint                NOT NULL DEFAULT 0,
    "created_at"       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "training_essay_tests"
(
    "id"               BIGSERIAL PRIMARY KEY NOT NULL,
    "index"            integer UNIQUE        NOT NULL,
    "question"         varchar(255)          NOT NULL,
    "training_exam_id" bigint                NOT NULL,
    "is_active"        boolean               NOT NULL DEFAULT true,
    "version"          bigint                NOT NULL DEFAULT 0,
    "created_at"       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "essay_test_answers"
(
    "id"                     BIGSERIAL PRIMARY KEY NOT NULL,
    "group_member_id"        bigint                NOT NULL,
    "answer"                 varchar(255),
    "training_essay_test_id" bigint,
    "is_active"              boolean               NOT NULL DEFAULT true,
    "version"                bigint                NOT NULL DEFAULT 0,
    "created_at"             bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"             bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);

CREATE TABLE "multiple_choice_test_answers"
(
    "id"                               BIGSERIAL PRIMARY KEY NOT NULL,
    "group_member_id"                  bigint                NOT NULL,
    "answer"                           varchar(10),
    "training_multiple_choice_test_id" bigint,
    "is_active"                        boolean               NOT NULL DEFAULT true,
    "version"                          bigint                NOT NULL DEFAULT 0,
    "created_at"                       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint,
    "updated_at"                       bigint                NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000)::bigint
);