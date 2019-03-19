-----------------------------------------------------
-- Export file for user TCC@10.0.0.2_31521_TRADE   --
-- Created by Administrator on 2019/3/19, 23:47:30 --
-----------------------------------------------------

set define off
spool tcc.log

prompt
prompt Creating table TCC_TRANSACTION_CAPITAL_BUYER
prompt ============================================
prompt
create table TCC.TCC_TRANSACTION_CAPITAL_BUYER
(
  transaction_id   VARCHAR2(32) default sys_guid() not null,
  domain           VARCHAR2(100),
  global_tx_id     VARCHAR2(32) not null,
  branch_qualifier VARCHAR2(32) not null,
  content          BLOB,
  status           NUMBER(11),
  transaction_type NUMBER(11),
  retried_count    NUMBER(11),
  create_time      DATE,
  last_update_time DATE,
  version          NUMBER(11)
)
;

prompt
prompt Creating table TCC_TRANSACTION_CAPITAL_SELLER
prompt =============================================
prompt
create table TCC.TCC_TRANSACTION_CAPITAL_SELLER
(
  transaction_id   VARCHAR2(32) default sys_guid() not null,
  domain           VARCHAR2(100),
  global_tx_id     VARCHAR2(32) not null,
  branch_qualifier VARCHAR2(32) not null,
  content          BLOB,
  status           NUMBER(11),
  transaction_type NUMBER(11),
  retried_count    NUMBER(11),
  create_time      DATE,
  last_update_time DATE,
  version          NUMBER(11)
)
;

prompt
prompt Creating table TCC_TRANSACTION_POINT
prompt ====================================
prompt
create table TCC.TCC_TRANSACTION_POINT
(
  transaction_id   VARCHAR2(32) default sys_guid() not null,
  domain           VARCHAR2(100),
  global_tx_id     VARCHAR2(32) not null,
  branch_qualifier VARCHAR2(32) not null,
  content          RAW(2000),
  status           NUMBER(11),
  transaction_type NUMBER(11),
  retried_count    NUMBER(11),
  create_time      DATE,
  last_update_time DATE,
  version          NUMBER(11)
)
;

prompt
prompt Creating table TCC_TRANSACTION_SHOP_ORDER
prompt =========================================
prompt
create table TCC.TCC_TRANSACTION_SHOP_ORDER
(
  transaction_id   VARCHAR2(32) default sys_guid() not null,
  domain           VARCHAR2(100),
  global_tx_id     VARCHAR2(32) not null,
  branch_qualifier VARCHAR2(32) not null,
  content          BLOB,
  status           NUMBER(2),
  transaction_type NUMBER(2),
  retried_count    NUMBER(5),
  create_time      DATE,
  last_update_time DATE,
  version          NUMBER(5)
)
;

prompt
prompt Creating table TCC_TRANSACTION_SHOP_PRODUCT
prompt ===========================================
prompt
create table TCC.TCC_TRANSACTION_SHOP_PRODUCT
(
  transaction_id   VARCHAR2(32) default sys_guid() not null,
  domain           VARCHAR2(100),
  global_tx_id     VARCHAR2(32) not null,
  branch_qualifier VARCHAR2(32) not null,
  content          BLOB,
  status           NUMBER(2),
  transaction_type NUMBER(2),
  retried_count    NUMBER(5),
  create_time      DATE,
  last_update_time DATE,
  version          NUMBER(5)
)
;

prompt
prompt Creating table TCC_TRANSACTION_TRADE
prompt ====================================
prompt
create table TCC.TCC_TRANSACTION_TRADE
(
  transaction_id   VARCHAR2(32) default sys_guid() not null,
  domain           VARCHAR2(100),
  global_tx_id     VARCHAR2(32) not null,
  branch_qualifier VARCHAR2(32) not null,
  content          BLOB,
  status           NUMBER(11),
  transaction_type NUMBER(11),
  retried_count    NUMBER(11),
  create_time      DATE,
  last_update_time DATE,
  version          NUMBER(11)
)
;


spool off
