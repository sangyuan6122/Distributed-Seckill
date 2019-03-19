-----------------------------------------------------
-- Export file for user JECP@10.0.0.2_31521_TRADE  --
-- Created by Administrator on 2019/3/19, 23:38:08 --
-----------------------------------------------------

set define off
spool sys.log

prompt
prompt Creating table TAW_SYSTEM_ATTACHMENT
prompt ====================================
prompt
create table JECP.TAW_SYSTEM_ATTACHMENT
(
  id         VARCHAR2(32) not null,
  fileenname VARCHAR2(255),
  filecnname VARCHAR2(255),
  attachsize VARCHAR2(32),
  path       VARCHAR2(255),
  uploadtime DATE,
  uploader   VARCHAR2(32),
  module     VARCHAR2(32),
  remark     VARCHAR2(32)
)
;

prompt
prompt Creating table TAW_SYSTEM_DEPT
prompt ==============================
prompt
create table JECP.TAW_SYSTEM_DEPT
(
  deptid          NUMBER(18) not null,
  deptname        VARCHAR2(255),
  parentdeptid    NUMBER(18),
  deptfullname    VARCHAR2(255),
  rtxdeptid       NUMBER(18),
  rtxparentdeptid VARCHAR2(255),
  deleted         NUMBER(1),
  external        NUMBER(1),
  seq             NUMBER(5),
  remark          VARCHAR2(255)
)
;
alter table JECP.TAW_SYSTEM_DEPT
  add constraint TAW_SYSRTEM_DEPT_DEPTID primary key (DEPTID);

prompt
prompt Creating table TAW_SYSTEM_DEPT_BAK
prompt ==================================
prompt
create table JECP.TAW_SYSTEM_DEPT_BAK
(
  deptid          NUMBER(18) not null,
  deptname        VARCHAR2(255),
  parentdeptid    NUMBER(18),
  deptfullname    VARCHAR2(255),
  rtxdeptid       NUMBER(18),
  rtxparentdeptid VARCHAR2(255),
  deleted         NUMBER(1),
  external        NUMBER(1),
  seq             NUMBER(5),
  remark          VARCHAR2(255)
)
;

prompt
prompt Creating table TAW_SYSTEM_DICTTYPE
prompt ==================================
prompt
create table JECP.TAW_SYSTEM_DICTTYPE
(
  dictid       NUMBER(8) not null,
  dictname     VARCHAR2(255),
  dictalias    VARCHAR2(255),
  parentdictid VARCHAR2(100),
  dictcode     VARCHAR2(100),
  remark       VARCHAR2(255),
  sort         NUMBER(10)
)
;

prompt
prompt Creating table TAW_SYSTEM_PRIV
prompt ==============================
prompt
create table JECP.TAW_SYSTEM_PRIV
(
  privid       VARCHAR2(32) not null,
  parentprivid VARCHAR2(50),
  name         VARCHAR2(255),
  url          VARCHAR2(500),
  menulevel    VARCHAR2(10),
  hide         VARCHAR2(10),
  sort         VARCHAR2(10),
  clienttype   VARCHAR2(1),
  ico          VARCHAR2(255),
  remark       VARCHAR2(255)
)
;

prompt
prompt Creating table TAW_SYSTEM_PRIV_MENU
prompt ===================================
prompt
create table JECP.TAW_SYSTEM_PRIV_MENU
(
  id            VARCHAR2(32) not null,
  menuname      VARCHAR2(255),
  remark        VARCHAR2(255),
  operateuserid VARCHAR2(50),
  clienttype    VARCHAR2(1)
)
;

prompt
prompt Creating table TAW_SYSTEM_PRIV_MENUASSIGN
prompt =========================================
prompt
create table JECP.TAW_SYSTEM_PRIV_MENUASSIGN
(
  id         VARCHAR2(32) not null,
  menuid     VARCHAR2(32),
  menulevel  VARCHAR2(10),
  privid     VARCHAR2(32),
  url        VARCHAR2(255),
  type       VARCHAR2(10),
  owner      VARCHAR2(255),
  clienttype VARCHAR2(1)
)
;

prompt
prompt Creating table TAW_SYSTEM_PRIV_MENUITEM
prompt =======================================
prompt
create table JECP.TAW_SYSTEM_PRIV_MENUITEM
(
  id         VARCHAR2(32) not null,
  menuid     VARCHAR2(32),
  privid     VARCHAR2(32),
  clienttype VARCHAR2(1)
)
;

prompt
prompt Creating table TAW_SYSTEM_ROLE
prompt ==============================
prompt
create table JECP.TAW_SYSTEM_ROLE
(
  roleid   VARCHAR2(32) not null,
  parentid VARCHAR2(32),
  deleted  VARCHAR2(5),
  name     VARCHAR2(255),
  type     VARCHAR2(5),
  sort     VARCHAR2(5),
  remark   VARCHAR2(255)
)
;

prompt
prompt Creating table TAW_SYSTEM_SUBROLE
prompt =================================
prompt
create table JECP.TAW_SYSTEM_SUBROLE
(
  id          VARCHAR2(32) not null,
  roleid      VARCHAR2(32),
  deptid      VARCHAR2(32),
  deptname    VARCHAR2(255),
  subrolename VARCHAR2(255),
  featurecode VARCHAR2(255),
  remark      VARCHAR2(255)
)
;

prompt
prompt Creating table TAW_SYSTEM_SUBROLEUSER
prompt =====================================
prompt
create table JECP.TAW_SYSTEM_SUBROLEUSER
(
  id          VARCHAR2(255) not null,
  roleid      VARCHAR2(255),
  subroleid   VARCHAR2(255),
  userid      VARCHAR2(255),
  username    VARCHAR2(255),
  featurecode VARCHAR2(255)
)
;

prompt
prompt Creating table TAW_SYSTEM_USER
prompt ==============================
prompt
create table JECP.TAW_SYSTEM_USER
(
  id             VARCHAR2(32) not null,
  userid         VARCHAR2(100) not null,
  username       VARCHAR2(255),
  deptid         NUMBER(18),
  deptname       VARCHAR2(100),
  password       VARCHAR2(255),
  mobile         VARCHAR2(100),
  account_locked VARCHAR2(10),
  fail_count     VARCHAR2(100),
  weixinid       VARCHAR2(32),
  external       NUMBER(1),
  deleted        NUMBER(1),
  seq            NUMBER(5),
  remark         VARCHAR2(255)
)
;
alter table JECP.TAW_SYSTEM_USER
  add constraint TAW_SYSTEM_USER_ID primary key (ID);
alter table JECP.TAW_SYSTEM_USER
  add constraint TAW_SYSTEM_USER_USERID unique (USERID);


spool off
