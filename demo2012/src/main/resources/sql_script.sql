CREATE TABLE [nasa2].[Z_USER_LOG] (
	[ID] [numeric](19, 0) IDENTITY (1, 1) PRIMARY KEY NOT NULL ,
	[COMPANY_CD] [char] (4) NULL ,
	[USER_CD] [nvarchar] (100) NULL ,
	[ACTION_NAME] [nvarchar] (100) NULL ,
	[OPERATION_TIME] [datetime] NULL ,
	[PARAMETER_INFO] [nvarchar] (4000)  NULL 
) 

INSERT INTO nasa2_Preprd_Dev.nasa2.Z_USER_LOG (COMPANY_CD) VALUES ( '1' )