

--liquibase formatted sql

--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'lastest_watching_videos';

--changeset James:003
insert into lastest_watching_videos (curriculum_id,session_id,content_id,content_type)
values (1,1,1,'VIDEO');
