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


ALTER TABLE p28_cfg_async_channel ADD COLUMN is_active BOOLEAN NOT NULL;
ALTER TABLE p28_cfg_async_channel ADD COLUMN description varchar(80) NOT NULL;
ALTER TABLE p28_cfg_async_channel ALTER COLUMN auth_type DROP NOT NULL;
ALTER TABLE p28_cfg_async_channel ALTER COLUMN auth_param DROP NOT NULL;
ALTER TABLE p28_cfg_async_channel ALTER COLUMN max_retries DROP NOT NULL;

ALTER TABLE p28_his_async_channel ADD COLUMN is_active BOOLEAN;
ALTER TABLE p28_his_async_channel ADD COLUMN description varchar(80);
ALTER TABLE p28_his_async_channel ALTER COLUMN auth_type DROP NOT NULL;
ALTER TABLE p28_his_async_channel ALTER COLUMN auth_param DROP NOT NULL;
ALTER TABLE p28_his_async_channel ALTER COLUMN max_retries DROP NOT NULL;