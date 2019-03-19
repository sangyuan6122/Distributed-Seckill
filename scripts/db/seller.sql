???prompt PL/SQL Developer import file
prompt Created on 2019年3月19日 by Administrator
set feedback off
set define off
prompt Creating CAPITAL_ACCOUNT...
create table CAPITAL_ACCOUNT
(
  id             VARCHAR2(32) not null,
  user_id        VARCHAR2(32),
  account_type   VARCHAR2(32),
  balance        NUMBER(18,2),
  balance_frozen NUMBER(18,2),
  create_time    DATE,
  update_date    DATE
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
comment on column CAPITAL_ACCOUNT.user_id
  is '用户ID';
comment on column CAPITAL_ACCOUNT.account_type
  is '账户类型(1:买家 2:商户)';
comment on column CAPITAL_ACCOUNT.balance
  is '可用余额';
comment on column CAPITAL_ACCOUNT.balance_frozen
  is '冻结余额';
comment on column CAPITAL_ACCOUNT.create_time
  is '创建时间';
comment on column CAPITAL_ACCOUNT.update_date
  is '修改时间';
alter table CAPITAL_ACCOUNT
  add primary key (ID)
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
alter table CAPITAL_ACCOUNT
  add constraint CAPITAL_ACCOUNT_USERID unique (USER_ID)
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

prompt Creating CAPITAL_ACCOUNT_HIS...
create table CAPITAL_ACCOUNT_HIS
(
  id           VARCHAR2(32) not null,
  user_id      VARCHAR2(32),
  account_type VARCHAR2(32),
  trade_amount NUMBER(18,2),
  trade_type   VARCHAR2(32),
  balance      NUMBER(18,2),
  status       VARCHAR2(32),
  gtid         NUMBER(18),
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
comment on column CAPITAL_ACCOUNT_HIS.user_id
  is '账户ID';
comment on column CAPITAL_ACCOUNT_HIS.account_type
  is '账户类型(1:买家，2:商家)';
comment on column CAPITAL_ACCOUNT_HIS.trade_amount
  is '交易金额';
comment on column CAPITAL_ACCOUNT_HIS.trade_type
  is '交易类型(add:加款,sub:减款)';
comment on column CAPITAL_ACCOUNT_HIS.balance
  is '余额';
comment on column CAPITAL_ACCOUNT_HIS.status
  is '资金账户流水状态';
comment on column CAPITAL_ACCOUNT_HIS.gtid
  is '全局订单号';
comment on column CAPITAL_ACCOUNT_HIS.create_time
  is '创建时间';
comment on column CAPITAL_ACCOUNT_HIS.update_date
  is '更新时间';
alter table CAPITAL_ACCOUNT_HIS
  add primary key (ID)
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

prompt Disabling triggers for CAPITAL_ACCOUNT...
alter table CAPITAL_ACCOUNT disable all triggers;
prompt Disabling triggers for CAPITAL_ACCOUNT_HIS...
alter table CAPITAL_ACCOUNT_HIS disable all triggers;
prompt Deleting CAPITAL_ACCOUNT_HIS...
delete from CAPITAL_ACCOUNT_HIS;
commit;
prompt Deleting CAPITAL_ACCOUNT...
delete from CAPITAL_ACCOUNT;
commit;
prompt Loading CAPITAL_ACCOUNT...
prompt Table is empty
prompt Loading CAPITAL_ACCOUNT_HIS...
prompt Table is empty
prompt Enabling triggers for CAPITAL_ACCOUNT...
alter table CAPITAL_ACCOUNT enable all triggers;
prompt Enabling triggers for CAPITAL_ACCOUNT_HIS...
alter table CAPITAL_ACCOUNT_HIS enable all triggers;
set feedback on
set define on
prompt Done.
