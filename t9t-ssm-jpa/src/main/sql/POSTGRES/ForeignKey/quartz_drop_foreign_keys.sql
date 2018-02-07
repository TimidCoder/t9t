ALTER TABLE qrtz_blob_triggers
    DROP CONSTRAINT qrtz_blob_triggers_FK;

ALTER TABLE qrtz_simprop_triggers
    DROP CONSTRAINT qrtz_simprop_triggers_FK;

ALTER TABLE qrtz_cron_triggers
    DROP CONSTRAINT qrtz_cron_triggers_FK;

ALTER TABLE qrtz_simple_triggers
    DROP CONSTRAINT qrtz_simple_triggers_FK;

ALTER TABLE qrtz_triggers
    DROP CONSTRAINT qrtz_triggers_FK;
