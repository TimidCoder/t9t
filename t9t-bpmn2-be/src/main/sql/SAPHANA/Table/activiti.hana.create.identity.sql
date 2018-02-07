create table ACT_ID_GROUP (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    NAME_ NVARCHAR(255),
    TYPE_ NVARCHAR(255),
    primary key (ID_)
);

create table ACT_ID_MEMBERSHIP (
    USER_ID_ NVARCHAR(64),
    GROUP_ID_ NVARCHAR(64),
    primary key (USER_ID_, GROUP_ID_)
);

create table ACT_ID_USER (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    FIRST_ NVARCHAR(255),
    LAST_ NVARCHAR(255),
    EMAIL_ NVARCHAR(255),
    PWD_ NVARCHAR(255),
    PICTURE_ID_ NVARCHAR(64),
    primary key (ID_)
);

create table ACT_ID_INFO (
    ID_ NVARCHAR(64),
    REV_ INTEGER,
    USER_ID_ NVARCHAR(64),
    TYPE_ NVARCHAR(64),
    KEY_ NVARCHAR(255),
    VALUE_ NVARCHAR(255),
    PASSWORD_ BLOB,
    PARENT_ID_ NVARCHAR(255),
    primary key (ID_)
);

create index ACT_IDX_MEMB_GROUP on ACT_ID_MEMBERSHIP(GROUP_ID_);

create index ACT_IDX_MEMB_USER on ACT_ID_MEMBERSHIP(USER_ID_);
