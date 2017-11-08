-- Add study blob column
alter table i2b2demodata.study add column study_blob text;

-- Add reference to tag options in the tags table
alter table i2b2metadata.i2b2_tags add column tag_option_id integer;

ALTER TABLE ONLY i2b2metadata.i2b2_tags
    ADD CONSTRAINT i2b2_tags_tag_option_fk FOREIGN KEY (tag_option_id)
    REFERENCES i2b2metadata.i2b2_tag_options(tag_option_id);