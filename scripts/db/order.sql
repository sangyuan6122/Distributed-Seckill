???prompt PL/SQL Developer import file
prompt Created on 2019年3月19日 by Administrator
set feedback off
set define off
prompt Creating SHOP_ORDER...
create table SHOP_ORDER
(
  order_id         VARCHAR2(32) not null,
  gtid             NUMBER(18),
  status           VARCHAR2(100),
  product_id       VARCHAR2(32),
  product_title    VARCHAR2(255),
  product_count    NUMBER(10),
  product_price    NUMBER(18,2),
  total_amount     NUMBER(18,2),
  user_id          VARCHAR2(255),
  sales_way        NUMBER(5),
  order_channel    NUMBER(5),
  create_time      DATE,
  last_update_time DATE,
  pay_time         DATE,
  seckill_id       VARCHAR2(32)
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
comment on column SHOP_ORDER.gtid
  is '全局事务ID';
comment on column SHOP_ORDER.status
  is '状态(0未付款,1已付款,2已发货,3已签收,4退货申请,5退货中,6已退货,7取消交易,8已完成)';
comment on column SHOP_ORDER.product_id
  is '商品ID';
comment on column SHOP_ORDER.product_title
  is '商品名称';
comment on column SHOP_ORDER.product_count
  is '商品数量';
comment on column SHOP_ORDER.product_price
  is '商品价格';
comment on column SHOP_ORDER.total_amount
  is '总金额';
comment on column SHOP_ORDER.user_id
  is '购买用户';
comment on column SHOP_ORDER.sales_way
  is '促销方式(0正常销售,1秒杀,2团购)';
comment on column SHOP_ORDER.order_channel
  is '订单渠道(0PC,1ANDROID,2IOS)';
comment on column SHOP_ORDER.create_time
  is '创建订单时间';
comment on column SHOP_ORDER.last_update_time
  is '最后更新时间';
comment on column SHOP_ORDER.pay_time
  is '支付时间';
comment on column SHOP_ORDER.seckill_id
  is '秒杀商品ID';
alter table SHOP_ORDER
  add primary key (ORDER_ID)
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
alter table SHOP_ORDER
  add constraint SHOP_ORDER_SECILLID_USERID unique (SECKILL_ID, USER_ID)
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
alter table SHOP_ORDER
  add constraint UK_GXBT83DUHNNHOGBPCNV96US8F unique (GTID)
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

prompt Creating SHOP_ORDER_HIS...
create table SHOP_ORDER_HIS
(
  order_his_id VARCHAR2(32) not null,
  order_id     VARCHAR2(32) not null,
  gtid         NUMBER(19),
  status       VARCHAR2(100),
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
comment on column SHOP_ORDER_HIS.order_id
  is '订单主键';
comment on column SHOP_ORDER_HIS.gtid
  is '全局事务ID';
comment on column SHOP_ORDER_HIS.status
  is '状态';
comment on column SHOP_ORDER_HIS.create_time
  is '创建时间';
alter table SHOP_ORDER_HIS
  add constraint SHOP_ORDER_HIS_PKEY primary key (ORDER_HIS_ID)
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
alter table SHOP_ORDER_HIS
  add constraint SHOP_ORDER_HIS_GTID_STATUS unique (GTID, STATUS)
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

prompt Disabling triggers for SHOP_ORDER...
alter table SHOP_ORDER disable all triggers;
prompt Disabling triggers for SHOP_ORDER_HIS...
alter table SHOP_ORDER_HIS disable all triggers;
prompt Deleting SHOP_ORDER_HIS...
delete from SHOP_ORDER_HIS;
commit;
prompt Deleting SHOP_ORDER...
delete from SHOP_ORDER;
commit;
prompt Loading SHOP_ORDER...
prompt Table is empty
prompt Loading SHOP_ORDER_HIS...
prompt Table is empty
prompt Enabling triggers for SHOP_ORDER...
alter table SHOP_ORDER enable all triggers;
prompt Enabling triggers for SHOP_ORDER_HIS...
alter table SHOP_ORDER_HIS enable all triggers;
set feedback on
set define on
prompt Done.
