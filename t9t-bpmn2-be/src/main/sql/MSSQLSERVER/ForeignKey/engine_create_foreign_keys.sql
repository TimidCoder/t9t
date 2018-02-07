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
