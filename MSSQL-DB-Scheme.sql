BEGIN TRY
	BEGIN TRANSACTION

	-- ***************************
	-- * Delete tables if exists *
	-- ***************************

	IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Users_Roles_JoinTable]') AND type in (N'U'))
		drop table [Users_Roles_JoinTable]

	IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Advertisements]') AND type in (N'U'))
		drop table [Advertisements]

	IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Roles]') AND type in (N'U'))
		drop table [Roles]

	IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[Users]') AND type in (N'U'))
		drop table [Users]

	-- ***************************
	-- *      Create tables      *
	-- ***************************

	CREATE TABLE [Users] (
		[ID] INT PRIMARY KEY IDENTITY(1, 1),
		[Username] NVARCHAR(40) NOT NULL UNIQUE,
		[Password] NVARCHAR(100) NOT NULL,
		[Email] NVARCHAR(50) NOT NULL UNIQUE,
		[Firstname] NVARCHAR(30) NOT NULL,
		[Lastname] NVARCHAR(30) NOT NULL
	)

	CREATE TABLE [Advertisements] (
		[ID] INT PRIMARY KEY IDENTITY(1, 1),
		[Name] NVARCHAR(30) NOT NULL,
		[Description] NVARCHAR(MAX),
		[User_ID] INT NOT NULL,

		CONSTRAINT FK_Advertisements_User_ID FOREIGN KEY ([User_ID]) REFERENCES [Users](ID)
	)

	CREATE TABLE [Roles] (
		[ID] INT PRIMARY KEY IDENTITY(1, 1),
		[Name] NVARCHAR(20) NOT NULL
	)

	CREATE TABLE [Users_Roles_JoinTable] (
		[User_ID] INT NOT NULL,
		[Role_ID] INT NOT NULL,

		CONSTRAINT PK_JoinTable PRIMARY KEY ([User_ID], [Role_ID]),
		CONSTRAINT FK_JoinTable_User_ID FOREIGN KEY ([User_ID]) REFERENCES [Users](ID),
		CONSTRAINT FK_JoinTable_Role_ID FOREIGN KEY ([Role_ID]) REFERENCES [Roles](ID)
	)

	INSERT INTO [Roles] ([Name]) VALUES ('ROLE_USER');
	INSERT INTO [Roles] ([Name]) VALUES ('ROLE_ADMIN');

	IF @@Trancount > 0
		COMMIT
END TRY
BEGIN CATCH
	IF @@Trancount > 0
		rollback
	IF  CURSOR_STATUS('global','cur') >= -1
		deallocate cur
	
	SELECT 
        ERROR_NUMBER() AS ErrorNumber,
        ERROR_SEVERITY() AS ErrorSeverity,
        ERROR_STATE() as ErrorState,
        ERROR_PROCEDURE() as ErrorProcedure,
        ERROR_LINE() as ErrorLine,
        ERROR_MESSAGE() as ErrorMessage
END CATCH