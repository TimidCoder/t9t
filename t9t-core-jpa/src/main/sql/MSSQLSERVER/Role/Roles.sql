-- For MS SQL server, in this example, Cambridge is the database. The connect right is assigned to the user, not the role.
-- The database must be selected first.
USE [Cambridge]
GO
CREATE ROLE [p42ro]
GO
USE [Cambridge]
GO
CREATE ROLE [p42rw]
GO
USE [Cambridge]
GO
CREATE ROLE [p42user]
GO
USE [Cambridge]
GO
CREATE ROLE [p42app]
GO
USE [Cambridge]
GO
CREATE ROLE [p42adm]
GO

--Vergabe der Rechte zu einem bestimmten Schema
use [Cambridge]
GO
GRANT UPDATE ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT ALTER ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT EXECUTE ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT SELECT ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT VIEW DEFINITION ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT INSERT ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT DELETE ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT CONTROL ON SCHEMA::[Cambridge] TO [p42adm]
GO
use [Cambridge]
GO
GRANT REFERENCES ON SCHEMA::[Cambridge] TO [p42adm]
GO
