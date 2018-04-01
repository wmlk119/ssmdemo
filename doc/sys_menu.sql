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

Date: 2018-03-30 09:27:35
*/


-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE [dbo].[sys_menu]
GO
CREATE TABLE [dbo].[sys_menu] (
[menuId] bigint NOT NULL ,
[menuLevel] int NULL DEFAULT ((0)) ,
[menuName] varchar(50) NOT NULL DEFAULT '' ,
[supMenuId] varchar(32) NULL ,
[supMenuName] varchar(50) NULL DEFAULT '' ,
[menuUrl] varchar(255) NULL DEFAULT '' ,
[menuSeq] int NULL DEFAULT ((0)) ,
[menuIcon] varchar(255) NULL DEFAULT '' ,
[createTime] datetime NOT NULL DEFAULT (getdate()) ,
[bakParam1] varchar(255) NULL DEFAULT '' ,
[bakParam2] varchar(255) NULL DEFAULT '' ,
[bakParam3] varchar(255) NULL DEFAULT '' 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'menuId')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuId'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuId'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'menuLevel')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'菜单级别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuLevel'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'菜单级别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuLevel'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'menuName')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'菜单名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuName'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'菜单名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuName'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'supMenuId')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'父级菜单ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'supMenuId'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'父级菜单ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'supMenuId'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'supMenuName')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'父级菜单名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'supMenuName'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'父级菜单名称'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'supMenuName'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'menuUrl')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'节点链接'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuUrl'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'节点链接'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuUrl'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'menuSeq')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'菜单顺序'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuSeq'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'菜单顺序'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuSeq'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'menuIcon')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'菜单图标'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuIcon'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'菜单图标'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'menuIcon'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'createTime')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'createTime'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建时间'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'createTime'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'bakParam1')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数1'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'bakParam1'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数1'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'bakParam1'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'bakParam2')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数2'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'bakParam2'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数2'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'bakParam2'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'sys_menu', 
'COLUMN', N'bakParam3')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数3'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'bakParam3'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数3'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'sys_menu'
, @level2type = 'COLUMN', @level2name = N'bakParam3'
GO

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291406531642654', N'1', N'系统管理', N'', N'', N'', N'1', N'', N'2018-03-29 14:06:53.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291453233528057', N'2', N'用户管理', N'1803291406531642654', N'系统管理', N'', N'1', N'', N'2018-03-29 14:53:23.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291454522431660', N'2', N'常用功能', N'1803291406531642654', N'系统管理', N'', N'4', N'', N'2018-03-29 14:55:14.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291454527908813', N'2', N'角色管理', N'1803291406531642654', N'系统管理', N'', N'2', N'', N'2018-03-29 14:54:46.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291454528136103', N'2', N'菜单管理', N'1803291406531642654', N'系统管理', N'', N'3', N'', N'2018-03-29 14:55:01.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291457375031270', N'3', N'角色管理', N'1803291453233528057', N'角色管理', N'/statics/html/base/rolelist.html', N'1', N'', N'2018-03-29 14:58:07.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291457377905371', N'3', N'用户管理', N'1803291453233528057', N'用户管理', N'/statics/html/base/userlist.html', N'1', N'', N'2018-03-29 14:57:33.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291505171847250', N'3', N'图标管理', N'1803291454528136103', N'菜单管理', N'/statics/html/base/iconlist.html', N'2', N'', N'2018-03-29 15:05:28.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291505173523741', N'3', N'菜单管理', N'1803291454528136103', N'菜单管理', N'/statics/html/base/menulist.html', N'1', N'', N'2018-03-29 15:05:12.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291508100358835', N'3', N'个人信息', N'1803291454522431660', N'常用功能', N'/statics/html/base/userinfo.html', N'1', N'', N'2018-03-29 15:08:05.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291508103526146', N'3', N'密码修改', N'1803291454522431660', N'常用功能', N'/statics/html/base/pwdmodify.html', N'2', N'', N'2018-03-29 15:08:25.000', N'', N'', N'')
GO
GO
INSERT INTO [dbo].[sys_menu] ([menuId], [menuLevel], [menuName], [supMenuId], [supMenuName], [menuUrl], [menuSeq], [menuIcon], [createTime], [bakParam1], [bakParam2], [bakParam3]) VALUES (N'1803291508471841622', N'3', N'系统公告', N'1803291454522431660', N'常用功能', N'/statics/html/base/noticelist.html', N'3', N'', N'2018-03-29 15:08:41.000', N'', N'', N'')
GO
GO

-- ----------------------------
-- Indexes structure for table sys_menu
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table sys_menu
-- ----------------------------
ALTER TABLE [dbo].[sys_menu] ADD PRIMARY KEY ([menuId])
GO
