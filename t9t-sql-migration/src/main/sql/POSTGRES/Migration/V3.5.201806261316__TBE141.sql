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


CREATE TABLE p28_cfg_async_channel (
    -- table columns of java class TrackingBase
    -- table columns of java class WriteTracking
      c_tech_user_id varchar(16) DEFAULT CURRENT_USER NOT NULL
    , c_app_user_id varchar(16) NOT NULL
    , c_timestamp timestamp(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
    , c_process_ref bigint NOT NULL
    -- table columns of java class FullTracking
    , m_tech_user_id varchar(16) DEFAULT CURRENT_USER NOT NULL
    , m_app_user_id varchar(16) NOT NULL
    , m_timestamp timestamp(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
    , m_process_ref bigint NOT NULL
    -- table columns of java class FullTrackingWithVersion
    , version integer NOT NULL
    -- table columns of java class InternalTenantRef42
    , tenant_ref bigint NOT NULL
    -- table columns of java class AbstractRef
    -- table columns of java class Ref
    , object_ref bigint NOT NULL
    -- table columns of java class AsyncChannelRef
    -- table columns of java class AsyncChannelDTO
    , async_channel_id varchar(16) NOT NULL
    , url varchar(255) NOT NULL
    , auth_type varchar(16) NOT NULL
    , auth_param varchar(255) NOT NULL
    , max_retries integer NOT NULL
    , payload_format varchar(1) NOT NULL
    , payload_parameters text
    , serializer_qualifier varchar(16)
);

ALTER TABLE p28_cfg_async_channel ADD CONSTRAINT p28_cfg_async_channel_pk PRIMARY KEY (
    object_ref
);
CREATE UNIQUE INDEX p28_cfg_async_channel_u1 ON p28_cfg_async_channel(
    tenant_ref, async_channel_id
);
GRANT SELECT,INSERT,UPDATE,DELETE ON p28_cfg_async_channel TO p42user;
GRANT SELECT ON p28_cfg_async_channel TO p42ro;
GRANT SELECT,INSERT,UPDATE,DELETE ON p28_cfg_async_channel TO p42rw;

-- comments for columns of java class TrackingBase
-- comments for columns of java class WriteTracking
COMMENT ON COLUMN p28_cfg_async_channel.c_tech_user_id IS 'noinsert removed, causes problems with H2 unit tests';
COMMENT ON COLUMN p28_cfg_async_channel.c_timestamp IS 'noinsert removed, causes problems with H2 unit tests';
-- comments for columns of java class FullTracking
-- comments for columns of java class FullTrackingWithVersion
-- comments for columns of java class InternalTenantRef42
COMMENT ON COLUMN p28_cfg_async_channel.tenant_ref IS 'the multitenancy discriminator';
-- comments for columns of java class AbstractRef
-- comments for columns of java class Ref
COMMENT ON COLUMN p28_cfg_async_channel.object_ref IS 'objectRef, as a primary key it cannot be changed and, if persisted, is never null';
-- comments for columns of java class AsyncChannelRef
-- comments for columns of java class AsyncChannelDTO
COMMENT ON COLUMN p28_cfg_async_channel.async_channel_id IS 'the ID of the channel';
COMMENT ON COLUMN p28_cfg_async_channel.url IS 'remote URL';
COMMENT ON COLUMN p28_cfg_async_channel.auth_type IS 'basic, apikey etc...';
COMMENT ON COLUMN p28_cfg_async_channel.auth_param IS 'userID / password or API-Key (if not required (unlikely), put in "N/A")';
COMMENT ON COLUMN p28_cfg_async_channel.payload_format IS 'XML, JSON etc.';
COMMENT ON COLUMN p28_cfg_async_channel.payload_parameters IS 'XML namespaces etc.';
COMMENT ON COLUMN p28_cfg_async_channel.serializer_qualifier IS 'specific serialization code (for dependency injection)';



CREATE TABLE p28_his_async_channel (
    -- table columns of java class TrackingBase
    -- table columns of java class WriteTracking
      c_tech_user_id varchar(16) DEFAULT CURRENT_USER NOT NULL
    , c_app_user_id varchar(16) NOT NULL
    , c_timestamp timestamp(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
    , c_process_ref bigint NOT NULL
    -- table columns of java class FullTracking
    , m_tech_user_id varchar(16) DEFAULT CURRENT_USER NOT NULL
    , m_app_user_id varchar(16) NOT NULL
    , m_timestamp timestamp(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
    , m_process_ref bigint NOT NULL
    -- table columns of java class FullTrackingWithVersion
    , version integer NOT NULL
    -- table columns of java class InternalTenantRef42
    , tenant_ref bigint NOT NULL
    , history_seq_ref   bigint NOT NULL
    , history_change_type   char(1) NOT NULL
    -- table columns of java class AbstractRef
    -- table columns of java class Ref
    , object_ref bigint NOT NULL
    -- table columns of java class AsyncChannelRef
    -- table columns of java class AsyncChannelDTO
    , async_channel_id varchar(16) NOT NULL
    , url varchar(255) NOT NULL
    , auth_type varchar(16) NOT NULL
    , auth_param varchar(255) NOT NULL
    , max_retries integer NOT NULL
    , payload_format varchar(1) NOT NULL
    , payload_parameters text
    , serializer_qualifier varchar(16)
);

ALTER TABLE p28_his_async_channel ADD CONSTRAINT p28_his_async_channel_pk PRIMARY KEY (
    object_ref, history_seq_ref
);
GRANT SELECT ON p28_his_async_channel TO p42rw;
GRANT SELECT ON p28_his_async_channel TO p42ro;

-- comments for columns of java class TrackingBase
-- comments for columns of java class WriteTracking
COMMENT ON COLUMN p28_his_async_channel.c_tech_user_id IS 'noinsert removed, causes problems with H2 unit tests';
COMMENT ON COLUMN p28_his_async_channel.c_timestamp IS 'noinsert removed, causes problems with H2 unit tests';
-- comments for columns of java class FullTracking
-- comments for columns of java class FullTrackingWithVersion
-- comments for columns of java class InternalTenantRef42
COMMENT ON COLUMN p28_his_async_channel.tenant_ref IS 'the multitenancy discriminator';
COMMENT ON COLUMN p28_his_async_channel.history_seq_ref IS 'current sequence number of history entry';
COMMENT ON COLUMN p28_his_async_channel.history_change_type IS 'type of change (C=create/insert, U=update, D=delete)';
-- comments for columns of java class AbstractRef
-- comments for columns of java class Ref
COMMENT ON COLUMN p28_his_async_channel.object_ref IS 'objectRef, as a primary key it cannot be changed and, if persisted, is never null';
-- comments for columns of java class AsyncChannelRef
-- comments for columns of java class AsyncChannelDTO
COMMENT ON COLUMN p28_his_async_channel.async_channel_id IS 'the ID of the channel';
COMMENT ON COLUMN p28_his_async_channel.url IS 'remote URL';
COMMENT ON COLUMN p28_his_async_channel.auth_type IS 'basic, apikey etc...';
COMMENT ON COLUMN p28_his_async_channel.auth_param IS 'userID / password or API-Key (if not required (unlikely), put in "N/A")';
COMMENT ON COLUMN p28_his_async_channel.payload_format IS 'XML, JSON etc.';
COMMENT ON COLUMN p28_his_async_channel.payload_parameters IS 'XML namespaces etc.';
COMMENT ON COLUMN p28_his_async_channel.serializer_qualifier IS 'specific serialization code (for dependency injection)';



CREATE TABLE p42_int_async_messages (
    -- table columns of java class TrackingBase
    -- table columns of java class WriteTracking
      c_tech_user_id varchar(16) DEFAULT CURRENT_USER NOT NULL
    , c_app_user_id varchar(16) NOT NULL
    , c_timestamp timestamp(0) DEFAULT CURRENT_TIMESTAMP NOT NULL
    , c_process_ref bigint NOT NULL
    -- table columns of java class InternalTenantRef42
    , tenant_ref bigint NOT NULL
    -- table columns of java class AbstractRef
    -- table columns of java class Ref
    , object_ref bigint NOT NULL
    -- table columns of java class AsyncMessageRef
    -- table columns of java class AsyncMessageDTO
    , async_channel_id varchar(16) NOT NULL
    , status varchar(1)
    , last_attempt timestamp(0)
    , attempts integer NOT NULL
    , payload bytea NOT NULL
);

ALTER TABLE p42_int_async_messages ADD CONSTRAINT p42_int_async_messages_pk PRIMARY KEY (
    object_ref
);
CREATE INDEX p42_int_async_messages_i1 ON p42_int_async_messages(
    status
);
GRANT SELECT,INSERT,UPDATE,DELETE ON p42_int_async_messages TO p42user;
GRANT SELECT ON p42_int_async_messages TO p42ro;
GRANT SELECT,INSERT,UPDATE,DELETE ON p42_int_async_messages TO p42rw;

-- comments for columns of java class TrackingBase
-- comments for columns of java class WriteTracking
COMMENT ON COLUMN p42_int_async_messages.c_tech_user_id IS 'noinsert removed, causes problems with H2 unit tests';
COMMENT ON COLUMN p42_int_async_messages.c_timestamp IS 'noinsert removed, causes problems with H2 unit tests';
-- comments for columns of java class InternalTenantRef42
COMMENT ON COLUMN p42_int_async_messages.tenant_ref IS 'the multitenancy discriminator';
-- comments for columns of java class AbstractRef
-- comments for columns of java class Ref
COMMENT ON COLUMN p42_int_async_messages.object_ref IS 'objectRef, as a primary key it cannot be changed and, if persisted, is never null';
-- comments for columns of java class AsyncMessageRef
-- comments for columns of java class AsyncMessageDTO
