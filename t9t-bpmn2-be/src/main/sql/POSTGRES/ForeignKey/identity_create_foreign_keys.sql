alter table ACT_ID_MEMBERSHIP add constraint ACT_FK_MEMB_GROUP foreign key (GROUP_ID_) references ACT_ID_GROUP (ID_);

alter table ACT_ID_MEMBERSHIP add constraint ACT_FK_MEMB_USER foreign key (USER_ID_) references ACT_ID_USER (ID_);
