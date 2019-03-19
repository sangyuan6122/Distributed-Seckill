???prompt PL/SQL Developer import file
prompt Created on 2019年3月19日 by Administrator
set feedback off
set define off
prompt Creating TRADE_RECORD...
create table TRADE_RECORD
(
  trade_id     VARCHAR2(32) not null,
  buyer_id     VARCHAR2(50),
  seller_id    VARCHAR2(50),
  trade_amount NUMBER(18,3),
  pay_method   VARCHAR2(50),
  out_order_no VARCHAR2(100),
  gtid         NUMBER(19),
  status       VARCHAR2(16),
  create_time  DATE,
  update_date  DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column TRADE_RECORD.buyer_id
  is '买方ID';
comment on column TRADE_RECORD.seller_id
  is '商家ID';
comment on column TRADE_RECORD.trade_amount
  is '支付金额';
comment on column TRADE_RECORD.pay_method
  is '支付方式';
comment on column TRADE_RECORD.out_order_no
  is '第三方支付单号';
comment on column TRADE_RECORD.gtid
  is '商品订单号';
comment on column TRADE_RECORD.status
  is '交易状态';
comment on column TRADE_RECORD.create_time
  is '创建时间';
comment on column TRADE_RECORD.update_date
  is '修改时间';
alter table TRADE_RECORD
  add primary key (TRADE_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table TRADE_RECORD
  add constraint TRADE_RECORD_GTID unique (GTID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Creating TRADE_RECORD_HIS...
create table TRADE_RECORD_HIS
(
  trade_his_id VARCHAR2(32) not null,
  gtid         NUMBER(19),
  status       VARCHAR2(16),
  create_time  DATE
)
tablespace USERS
  pctfree 10
  initrans 1
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
comment on column TRADE_RECORD_HIS.trade_his_id
  is '主键';
comment on column TRADE_RECORD_HIS.gtid
  is '全局事务ID';
comment on column TRADE_RECORD_HIS.status
  is '状态';
comment on column TRADE_RECORD_HIS.create_time
  is '创建时间';
create unique index TRADE_RECORD_HIS_GTID on TRADE_RECORD_HIS (GTID, STATUS)
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );
alter table TRADE_RECORD_HIS
  add primary key (TRADE_HIS_ID)
  using index 
  tablespace USERS
  pctfree 10
  initrans 2
  maxtrans 255
  storage
  (
    initial 64K
    next 1M
    minextents 1
    maxextents unlimited
  );

prompt Disabling triggers for TRADE_RECORD...
alter table TRADE_RECORD disable all triggers;
prompt Disabling triggers for TRADE_RECORD_HIS...
alter table TRADE_RECORD_HIS disable all triggers;
prompt Deleting TRADE_RECORD_HIS...
delete from TRADE_RECORD_HIS;
commit;
prompt Deleting TRADE_RECORD...
delete from TRADE_RECORD;
commit;
prompt Loading TRADE_RECORD...
prompt Table is empty
prompt Loading TRADE_RECORD_HIS...
prompt Table is empty
prompt Enabling triggers for TRADE_RECORD...
alter table TRADE_RECORD enable all triggers;
prompt Enabling triggers for TRADE_RECORD_HIS...
alter table TRADE_RECORD_HIS enable all triggers;
set feedback on
set define on
prompt Done.
