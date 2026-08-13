
--liquibase formatted sql

--preconditions onFail:MARK_RAN onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = 'public' AND table_name = 'hello_james_get_started_liquibase';

--changeset James:002
INSERT INTO hello_james_get_started_liquibase(message)
VALUES ('Nguyen Huu Duy'),('Nguyen Van Jam run liquibase');

--rollback DELETE FROM hello_james_get_started_liquibase;