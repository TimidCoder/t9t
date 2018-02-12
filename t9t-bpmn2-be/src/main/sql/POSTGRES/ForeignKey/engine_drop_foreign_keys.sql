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

alter table ACT_GE_BYTEARRAY drop constraint ACT_FK_BYTEARR_DEPL;

alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_PROCINST;
alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_PARENT;
alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_SUPER;
alter table ACT_RU_EXECUTION drop constraint ACT_FK_EXE_PROCDEF;

alter table ACT_RU_IDENTITYLINK drop constraint ACT_FK_TSKASS_TASK;
alter table ACT_RU_IDENTITYLINK drop constraint ACT_FK_ATHRZ_PROCEDEF;
alter table ACT_RU_IDENTITYLINK drop constraint ACT_FK_IDL_PROCINST;

alter table ACT_RU_TASK drop constraint ACT_FK_TASK_EXE;
alter table ACT_RU_TASK drop constraint ACT_FK_TASK_PROCINST;
alter table ACT_RU_TASK drop constraint ACT_FK_TASK_PROCDEF;

alter table ACT_RU_VARIABLE drop constraint ACT_FK_VAR_EXE;
alter table ACT_RU_VARIABLE drop constraint ACT_FK_VAR_PROCINST;
alter table ACT_RU_VARIABLE drop constraint ACT_FK_VAR_BYTEARRAY;

alter table ACT_RU_JOB drop constraint ACT_FK_JOB_EXCEPTION;

alter table ACT_RU_EVENT_SUBSCR drop constraint ACT_FK_EVENT_EXEC;

alter table ACT_RE_MODEL drop constraint ACT_FK_MODEL_SOURCE;
alter table ACT_RE_MODEL drop constraint ACT_FK_MODEL_SOURCE_EXTRA;
alter table ACT_RE_MODEL drop constraint ACT_FK_MODEL_DEPLOYMENT;
