/*
Navicat SQL Server Data Transfer

Source Server         : localhost_sqlserver
Source Server Version : 110000
Source Host           : localhost:1433
Source Database       : SSM
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 110000
File Encoding         : 65001

Date: 2018-03-30 09:27:43
*/


-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE [dbo].[sys_role]
GO
CREATE TABLE [dbo].[sys_role] (
[roleId] bigint NOT NULL ,
[roleName] varchar(50) NOT NULL DEFAULT '' ,
[roleDes] varchar(255) NULL DEFAULT '' ,
[createTime] datetime NOT NULL DEFAULT (getdate()) ,
[bakParam1] varchar(255) NULL DEFAULT '' ,
[bakParam2] varchar(255) NULL DEFAULT '' ,
[bakParam3] varchar(255) NULL DEFAULT '' 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'roleId')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'roleId'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'roleId'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'roleName')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'角色名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'roleName'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'角色名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'roleName'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'roleDes')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'角色描述'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'roleDes'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'角色描述'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'roleDes'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'createTime')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'createTime'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'createTime'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'bakParam1')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数1'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'bakParam1'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数1'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'bakParam1'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'bakParam2')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数2'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'bakParam2'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数2'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'bakParam2'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_role', 
'COLUMN', N'bakParam3')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数3'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'bakParam3'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数3'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_role'
, @level2type = 'COLUMN', @level2name = N'bakParam3'
GO

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO [dbo].[sys_role] ([roleId], [roleName], [roleDes], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803231714173373280', N'运营管理员', N'系统最高权限', N'2018-03-23 17:14:17.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_role] ([roleId], [roleName], [roleDes], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803231729282256195', N'测试员', N'我是测试员', N'2018-03-23 17:29:28.000', N'', N'', N'')
GO
GO

-- ----------------------------
-- Indexes structure for table sys_role
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sys_role
-- ----------------------------
ALTER TABLE [dbo].[sys_role] ADD PRIMARY KEY ([roleId])
GO
