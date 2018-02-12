--
-- Copyright (c) 2012 - 2018 Arvato Systems GmbH
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

create table ACT_GE_PROPERTY (
    NAME_ NVARCHAR(64),
    VALUE_ NVARCHAR(300),
    REV_ INTEGER,
    primary key (NAME_)
);

insert into ACT_GE_PROPERTY
values ('schema.version', '5.13', 1);

insert into ACT_GE_PROPERTY
values ('schema.history', 'create(5.13)', 1);

insert into ACT_GE_PROPERTY
values ('next.dbid', '1', 1);

create table ACT_GE_BYTEARRAY (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    NAME_ NVARCHAR(255),
    DEPLOYMENT_ID_ NVARCHAR(64),
    BYTES_ BLOB,
    GENERATED_ NUMBER(1,0),
    primary key (ID_)
);

create table ACT_RE_DEPLOYMENT (
    ID_ NVARCHAR(64),
    NAME_ NVARCHAR(255),
    CATEGORY_ NVARCHAR(255),
    DEPLOY_TIME_ seconddate,
    primary key (ID_)
);

create table ACT_RE_MODEL (
    ID_ NVARCHAR(64) not null,
    REV_ INTEGER,
    NAME_ NVARCHAR(255),
    KEY_ NVARCHAR(255),
    CATEGORY_ NVARCHAR(255),
    CREATE_TIME_ seconddate,
    LAST_UPDATE_TIME_ seconddate,
    VERSION_ INTEGER,
    META_INFO_ NVARCHAR(2000),
    DEPLOYMENT_ID_ NVARCHAR(64),
    EDITOR_SOURCE_VALUE_ID_ NVARCHAR(64),
    EDITOR_SOURCE_EXTRA_VALUE_ID_ NVARCHAR(64),
    primary key (ID_)
);

create table ACT_RU_EXECUTION (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    PROC_INST_ID_ NVARCHAR(64),
    BUSINESS_KEY_ NVARCHAR(255),
    PARENT_ID_ NVARCHAR(64),
    PROC_DEF_ID_ NVARCHAR(64),
    SUPER_EXEC_ NVARCHAR(64),
    ACT_ID_ NVARCHAR(255),
    IS_ACTIVE_ NUMBER(1,0),
    IS_CONCURRENT_ NUMBER(1,0),
    IS_SCOPE_ NUMBER(1,0),
    IS_EVENT_SCOPE_ NUMBER(1,0),
    SUSPENSION_STATE_ INTEGER,
    CACHED_ENT_STATE_ INTEGER,
    primary key (ID_)
);

create table ACT_RU_JOB (
    ID_ NVARCHAR(64) NOT NULL,
    REV_ INTEGER,
    TYPE_ NVARCHAR(255) NOT NULL,
    LOCK_EXP_TIME_ seconddate,
    LOCK_OWNER_ NVARCHAR(255),
    EXCLUSIVE_ NUMBER(1,0),
    EXECUTION_ID_ NVARCHAR(64),
    PROCESS_INSTANCE_ID_ NVARCHAR(64),
    PROC_DEF_ID_ NVARCHAR(64),
    RETRIES_ INTEGER,
    EXCEPTION_STACK_ID_ NVARCHAR(64),
    EXCEPTION_MSG_ NVARCHAR(2000),
    DUEDATE_ seconddate,
    REPEAT_ NVARCHAR(255),
    HANDLER_TYPE_ NVARCHAR(255),
    HANDLER_CFG_ NVARCHAR(2000),
    primary key (ID_)
);

create table ACT_RE_PROCDEF (
    ID_ NVARCHAR(64) NOT NULL,
    REV_ INTEGER,
    CATEGORY_ NVARCHAR(255),
    NAME_ NVARCHAR(255),
    KEY_ NVARCHAR(255) NOT NULL,
    VERSION_ INTEGER NOT NULL,
    DEPLOYMENT_ID_ NVARCHAR(64),
    RESOURCE_NAME_ NVARCHAR(2000),
    DGRM_RESOURCE_NAME_ varchar(4000),
    DESCRIPTION_ NVARCHAR(2000),
    HAS_START_FORM_KEY_ NUMBER(1,0),
    SUSPENSION_STATE_ INTEGER,
    primary key (ID_)
);

create table ACT_RU_TASK (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    EXECUTION_ID_ NVARCHAR(64),
    PROC_INST_ID_ NVARCHAR(64),
    PROC_DEF_ID_ NVARCHAR(64),
    NAME_ NVARCHAR(255),
    PARENT_TASK_ID_ NVARCHAR(64),
    DESCRIPTION_ NVARCHAR(2000),
    TASK_DEF_KEY_ NVARCHAR(255),
    OWNER_ NVARCHAR(255),
    ASSIGNEE_ NVARCHAR(255),
    DELEGATION_ NVARCHAR(64),
    PRIORITY_ INTEGER,
    CREATE_TIME_ seconddate,
    DUE_DATE_ seconddate,
    SUSPENSION_STATE_ INTEGER,
    primary key (ID_)
);

create table ACT_RU_IDENTITYLINK (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    GROUP_ID_ NVARCHAR(255),
    TYPE_ NVARCHAR(255),
    USER_ID_ NVARCHAR(255),
    TASK_ID_ NVARCHAR(64),
    PROC_INST_ID_ NVARCHAR(64),
    PROC_DEF_ID_ NVARCHAR(64),
    primary key (ID_)
);

create table ACT_RU_VARIABLE (
    ID_ NVARCHAR(64) not null,
    REV_ INTEGER,
    TYPE_ NVARCHAR(255) not null,
    NAME_ NVARCHAR(255) not null,
    EXECUTION_ID_ NVARCHAR(64),
    PROC_INST_ID_ NVARCHAR(64),
    TASK_ID_ NVARCHAR(64),
    BYTEARRAY_ID_ NVARCHAR(64),
    DOUBLE_ NUMBER(19,10),
    LONG_ NUMBER(19,0),
    TEXT_ NVARCHAR(2000),
    TEXT2_ NVARCHAR(2000),
    primary key (ID_)
);

create table ACT_RU_EVENT_SUBSCR (
    ID_ NVARCHAR(64) not null,
    REV_ integer,
    EVENT_TYPE_ NVARCHAR(255) not null,
    EVENT_NAME_ NVARCHAR(255),
    EXECUTION_ID_ NVARCHAR(64),
    PROC_INST_ID_ NVARCHAR(64),
    ACTIVITY_ID_ NVARCHAR(64),
    CONFIGURATION_ NVARCHAR(255),
    CREATED_ seconddate not null,
    primary key (ID_)
);

create index ACT_IDX_EXEC_BUSKEY on ACT_RU_EXECUTION(BUSINESS_KEY_);
create index ACT_IDX_TASK_CREATE on ACT_RU_TASK(CREATE_TIME_);

create index ACT_IDX_IDENT_LNK_USER on ACT_RU_IDENTITYLINK(USER_ID_);
create index ACT_IDX_IDENT_LNK_GROUP on ACT_RU_IDENTITYLINK(GROUP_ID_);

create index ACT_IDX_EVENT_SUBSCR_CONFIG_ on ACT_RU_EVENT_SUBSCR(CONFIGURATION_);

create index ACT_IDX_VARIABLE_TASK_ID on ACT_RU_VARIABLE(TASK_ID_);

create index ACT_IDX_BYTEAR_DEPL on ACT_GE_BYTEARRAY(DEPLOYMENT_ID_);

alter table ACT_RE_PROCDEF
    add constraint ACT_UNIQ_PROCDEF
    unique (KEY_,VERSION_);

create index ACT_IDX_EXE_PROCINST on ACT_RU_EXECUTION(PROC_INST_ID_);
create index ACT_IDX_EXE_PARENT on ACT_RU_EXECUTION(PARENT_ID_);
create index ACT_IDX_EXE_SUPER on ACT_RU_EXECUTION(SUPER_EXEC_);
create index ACT_IDX_EXE_PROCDEF on ACT_RU_EXECUTION(PROC_DEF_ID_);

create index ACT_IDX_TSKASS_TASK on ACT_RU_IDENTITYLINK(TASK_ID_);
create index ACT_IDX_ATHRZ_PROCEDEF  on ACT_RU_IDENTITYLINK(PROC_DEF_ID_);

create index ACT_IDX_IDL_PROCINST on ACT_RU_IDENTITYLINK(PROC_INST_ID_);

create index ACT_IDX_TASK_EXEC on ACT_RU_TASK(EXECUTION_ID_);
create index ACT_IDX_TASK_PROCINST on ACT_RU_TASK(PROC_INST_ID_);
create index ACT_IDX_TASK_PROCDEF on ACT_RU_TASK(PROC_DEF_ID_);

create index ACT_IDX_VAR_EXE on ACT_RU_VARIABLE(EXECUTION_ID_);
create index ACT_IDX_VAR_PROCINST on ACT_RU_VARIABLE(PROC_INST_ID_);
create index ACT_IDX_VAR_BYTEARRAY on ACT_RU_VARIABLE(BYTEARRAY_ID_);

create index ACT_IDX_JOB_EXCEPTION on ACT_RU_JOB(EXCEPTION_STACK_ID_);

create index ACT_IDX_EVENT_SUBSCR on ACT_RU_EVENT_SUBSCR(EXECUTION_ID_);

create index ACT_IDX_MODEL_SOURCE on ACT_RE_MODEL(EDITOR_SOURCE_VALUE_ID_);
create index ACT_IDX_MODEL_SOURCE_EXTRA on ACT_RE_MODEL(EDITOR_SOURCE_EXTRA_VALUE_ID_);
create index ACT_IDX_MODEL_DEPLOYMENT on ACT_RE_MODEL(DEPLOYMENT_ID_);

--create unique index ACT_UNIQ_RU_BUS_KEY on ACT_RU_EXECUTION
--   (case when BUSINESS_KEY_ is null then null else PROC_DEF_ID_ end,
--    case when BUSINESS_KEY_ is null then null else BUSINESS_KEY_ end);
