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

Date: 2018-03-30 09:27:49
*/


-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE [dbo].[user_info]
GO
CREATE TABLE [dbo].[user_info] (
[userId] bigint NOT NULL ,
[loginName] varchar(50) NOT NULL DEFAULT '' ,
[loginPwd] varchar(50) NOT NULL DEFAULT '' ,
[pwdSalt] varchar(50) NOT NULL DEFAULT '' ,
[userName] varchar(50) NULL DEFAULT '' ,
[sex] tinyint NOT NULL DEFAULT ((1)) ,
[phoneNum] varchar(20) NOT NULL DEFAULT '' ,
[districtCode] tinyint NOT NULL ,
[isEnable] tinyint NOT NULL DEFAULT ((1)) ,
[createTime] datetime NOT NULL DEFAULT (getdate()) ,
[remark] varchar(255) NULL DEFAULT '' ,
[bakParam1] varchar(255) NULL DEFAULT '' ,
[bakParam2] varchar(255) NULL DEFAULT '' ,
[bakParam3] varchar(255) NULL DEFAULT '' ,
[roleId] bigint NULL 
)


GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'userId')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'用户ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'userId'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'用户ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'userId'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'loginName')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'登录名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'loginName'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'登录名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'loginName'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'loginPwd')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'密码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'loginPwd'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'密码'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'loginPwd'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'pwdSalt')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'加密盐值'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'pwdSalt'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'加密盐值'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'pwdSalt'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'userName')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'用户名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'userName'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'用户名'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'userName'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'sex')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'性别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'sex'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'性别'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'sex'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'phoneNum')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'联系电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'phoneNum'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'联系电话'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'phoneNum'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'districtCode')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'所属区县'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'districtCode'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'所属区县'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'districtCode'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'isEnable')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'是否有效'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'isEnable'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'是否有效'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'isEnable'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'createTime')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'创建日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'createTime'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'创建日期'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'createTime'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'remark')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'remark'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'备注'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'remark'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'bakParam1')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数1'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'bakParam1'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数1'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'bakParam1'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'bakParam2')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数2'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'bakParam2'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数2'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'bakParam2'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'bakParam3')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'预留参数3'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'bakParam3'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'预留参数3'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'bakParam3'
GO
IF ((SELECT COUNT(*) from fn_listextendedproperty('MS_Description', 
'SCHEMA', N'dbo', 
'TABLE', N'user_info', 
'COLUMN', N'roleId')) > 0) 
EXEC sp_updateextendedproperty @name = N'MS_Description', @value = N'角色ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'roleId'
ELSE
EXEC sp_addextendedproperty @name = N'MS_Description', @value = N'角色ID'
, @level0type = 'SCHEMA', @level0name = N'dbo'
, @level1type = 'TABLE', @level1name = N'user_info'
, @level2type = 'COLUMN', @level2name = N'roleId'
GO

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281135157185485', N'admin', N'6f00651879295c61ee0e03e96304e675', N'be5c4fd8764a4721a04fde2dde764d7f', N'管理员', N'1', N'051683601272', N'1', N'1', N'2018-03-28 11:35:15.000', N'测试一下', N'', N'', N'', N'1803231714173373280')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281446287846560', N'test01', N'5fdd4d1f87009da8b5115231cdecc512', N'5950c22272cf4f17aafcb5cf77e22311', N'测试员一', N'1', N'051683601272', N'1', N'1', N'2018-03-28 14:46:28.000', N'测试员一', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281451530835141', N'test03', N'f57fff0cbc0f3c6bee39752c46cc1344', N'4f8500a7d0234243a7a440b181b22815', N'测试员三', N'1', N'051683601272', N'1', N'1', N'2018-03-28 14:52:22.000', N'', N'', N'', N'', N'1803231714173373280')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281451532487384', N'test02', N'08585824352c6e8723255b8d9aab8962', N'b422bd01e5f04bf7a572123c1bac9038', N'测试员二', N'1', N'051683601272', N'1', N'1', N'2018-03-28 14:51:53.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281458115918659', N'test5', N'c3c3199fb8fe4282107f60455eb1129a', N'9d2f78ffc10d4c86b5e1b0b7b684fa53', N'ceshiyuan', N'1', N'15899658522', N'1', N'1', N'2018-03-28 14:58:30.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281458118456999', N'test4', N'c0d6e223aa33e8d068159504cd37a807', N'1fb775cf3b5c4c5b87e8c627c464fc41', N'测试员4', N'1', N'18052552256', N'1', N'1', N'2018-03-28 14:58:03.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281459060837218', N'tom', N'096d4171e6f6e0b45389d6239867e398', N'524b16da418b48829757ef650fbfc14c', N'汤姆森', N'1', N'051683601272', N'1', N'1', N'2018-03-28 14:59:37.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281459064843687', N'test6', N'60f25dbbd70da529a8457673ed329e82', N'3ab7a980f84a463c8fdd5043b2574a1a', N'tom_6', N'1', N'18569885544', N'1', N'1', N'2018-03-28 14:58:58.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281459067761989', N'tom1', N'224dae06646dc2ec8be5c5488e03191b', N'c39ec25f42904169b34c601840a0ddea', N'汤姆森1', N'1', N'051683601272', N'1', N'1', N'2018-03-28 14:59:55.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281500200834181', N'jim', N'8d21131dcf139c4cd67dad41659428cd', N'0ddce103ca8546d49714b836d20a0861', N'吉姆', N'1', N'051683601273', N'1', N'1', N'2018-03-28 15:00:40.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO
INSERT INTO [dbo].[user_info] ([userId], [loginName], [loginPwd], [pwdSalt], [userName], [sex], [phoneNum], [districtCode], [isEnable], [createTime], [remark], [bakParam1], [bakParam2], [bakParam3], [roleId]) VALUES (N'1803281500204846215', N'tom3', N'84a8013eaaabae56bff4d512d78ca296', N'4028a45ebcc44a57bc81d524df44357a', N'汤姆森三', N'1', N'051683601272', N'1', N'1', N'2018-03-28 15:00:12.000', N'', N'', N'', N'', N'1803231729282256195')
GO
GO

-- ----------------------------
-- Indexes structure for table user_info
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table user_info
-- ----------------------------
ALTER TABLE [dbo].[user_info] ADD PRIMARY KEY ([userId])
GO
