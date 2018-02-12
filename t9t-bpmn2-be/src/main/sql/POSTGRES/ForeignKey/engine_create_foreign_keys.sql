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

alter table ACT_GE_BYTEARRAY add constraint ACT_FK_BYTEARR_DEPL foreign key (DEPLOYMENT_ID_) references ACT_RE_DEPLOYMENT (ID_);

alter table ACT_RU_EXECUTION add constraint ACT_FK_EXE_PROCINST foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_EXECUTION add constraint ACT_FK_EXE_PARENT foreign key (PARENT_ID_) references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_EXECUTION add constraint ACT_FK_EXE_SUPER foreign key (SUPER_EXEC_) references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_EXECUTION add constraint ACT_FK_EXE_PROCDEF foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_);

alter table ACT_RU_IDENTITYLINK add constraint ACT_FK_TSKASS_TASK foreign key (TASK_ID_) references ACT_RU_TASK (ID_);
alter table ACT_RU_IDENTITYLINK add constraint ACT_FK_ATHRZ_PROCEDEF foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_);
alter table ACT_RU_IDENTITYLINK add constraint ACT_FK_IDL_PROCINST foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_);

alter table ACT_RU_TASK add constraint ACT_FK_TASK_EXE foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_TASK add constraint ACT_FK_TASK_PROCINST foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_TASK add constraint ACT_FK_TASK_PROCDEF foreign key (PROC_DEF_ID_) references ACT_RE_PROCDEF (ID_);

alter table ACT_RU_VARIABLE add constraint ACT_FK_VAR_EXE foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION (ID_);
alter table ACT_RU_VARIABLE add constraint ACT_FK_VAR_PROCINST foreign key (PROC_INST_ID_) references ACT_RU_EXECUTION(ID_);
alter table ACT_RU_VARIABLE add constraint ACT_FK_VAR_BYTEARRAY foreign key (BYTEARRAY_ID_) references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_JOB add constraint ACT_FK_JOB_EXCEPTION foreign key (EXCEPTION_STACK_ID_) references ACT_GE_BYTEARRAY (ID_);

alter table ACT_RU_EVENT_SUBSCR add constraint ACT_FK_EVENT_EXEC foreign key (EXECUTION_ID_) references ACT_RU_EXECUTION(ID_);

alter table ACT_RE_MODEL add constraint ACT_FK_MODEL_SOURCE foreign key (EDITOR_SOURCE_VALUE_ID_) references ACT_GE_BYTEARRAY (ID_);
alter table ACT_RE_MODEL add constraint ACT_FK_MODEL_SOURCE_EXTRA foreign key (EDITOR_SOURCE_EXTRA_VALUE_ID_) references ACT_GE_BYTEARRAY (ID_);
alter table ACT_RE_MODEL add constraint ACT_FK_MODEL_DEPLOYMENT foreign key (DEPLOYMENT_ID_) references ACT_RE_DEPLOYMENT (ID_);
