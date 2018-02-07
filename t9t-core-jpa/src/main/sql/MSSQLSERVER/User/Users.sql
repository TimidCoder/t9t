-- users are create for the whole SQL server, therefore reference the master application
USE [master]
GO
CREATE LOGIN [appp42] WITH PASSWORD=N'DBA', DEFAULT_DATABASE=[Cambridge], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
USE [Cambridge]
GO
CREATE USER [appp42] FOR LOGIN [appp42]
GO
USE [Cambridge]
GO
EXEC sp_addrolemember N'p42adm', N'appp42'
GO
USE [master]
GO
CREATE LOGIN [fortytwo] WITH PASSWORD=N'fortytwopw', DEFAULT_DATABASE=[Cambridge], CHECK_EXPIRATION=OFF, CHECK_POLICY=OFF
GO
USE [Cambridge]
GO
CREATE USER [fortytwo] FOR LOGIN [fortytwo]
GO
USE [Cambridge]
GO
EXEC sp_addrolemember N'p42user', N'fortytwo'
GO
