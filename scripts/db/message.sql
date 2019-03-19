------------------------------------------------------
-- Export file for user JECP@10.0.0.2_31525_MESSAGE --
-- Created by Administrator on 2019/3/19, 23:41:09 ---
------------------------------------------------------

set define off
spool message.log

prompt
prompt Creating table RELIABLE_MESSAGE
prompt ===============================
prompt
create table JECP.RELIABLE_MESSAGE
(
  id                    VARCHAR2(32) not null,
  creater               VARCHAR2(100),
  editor                VARCHAR2(100),
  create_time           DATE,
  last_update_time      DATE,
  message_key           VARCHAR2(50),
  message_body          CLOB,
  message_data_type     VARCHAR2(32),
  confirm_timeout_queue VARCHAR2(50),
  consumer_queue        VARCHAR2(50),
  check_producer_times  NUMBER(5),
  resend_times          NUMBER(5),
  already_dead          NUMBER(5),
  status                VARCHAR2(32),
  deleted               NUMBER(5)
)
;
comment on column JECP.RELIABLE_MESSAGE.creater
  is '创建者';
comment on column JECP.RELIABLE_MESSAGE.editor
  is '修改者';
comment on column JECP.RELIABLE_MESSAGE.create_time
  is '创建时间';
comment on column JECP.RELIABLE_MESSAGE.last_update_time
  is '最后更新时间';
comment on column JECP.RELIABLE_MESSAGE.message_key
  is '消息ID';
comment on column JECP.RELIABLE_MESSAGE.message_body
  is '消息内容';
comment on column JECP.RELIABLE_MESSAGE.message_data_type
  is '数据类型';
comment on column JECP.RELIABLE_MESSAGE.confirm_timeout_queue
  is '确认超时队列';
comment on column JECP.RELIABLE_MESSAGE.consumer_queue
  is '消费者队列';
comment on column JECP.RELIABLE_MESSAGE.check_producer_times
  is '回查次数';
comment on column JECP.RELIABLE_MESSAGE.resend_times
  is '重发次数';
comment on column JECP.RELIABLE_MESSAGE.already_dead
  is '消息是否死亡';
comment on column JECP.RELIABLE_MESSAGE.status
  is '消息状态';
comment on column JECP.RELIABLE_MESSAGE.deleted
  is '删除标记';
alter table JECP.RELIABLE_MESSAGE
  add primary key (ID);
alter table JECP.RELIABLE_MESSAGE
  add constraint UK_O5UINT6HJDVER9OSDXADKDGPA unique (MESSAGE_KEY);

prompt
prompt Creating table RELIABLE_MESSAGE_DELETED
prompt =======================================
prompt
create table JECP.RELIABLE_MESSAGE_DELETED
(
  id                    VARCHAR2(32) not null,
  creater               VARCHAR2(100),
  editor                VARCHAR2(100),
  create_time           DATE,
  last_update_time      DATE,
  message_key           VARCHAR2(50),
  message_body          CLOB,
  message_data_type     VARCHAR2(32),
  confirm_timeout_queue VARCHAR2(50),
  consumer_queue        VARCHAR2(50),
  check_producer_times  NUMBER(5),
  resend_times          NUMBER(5),
  already_dead          NUMBER(5),
  status                VARCHAR2(32),
  deleted               NUMBER(5)
)
;
comment on column JECP.RELIABLE_MESSAGE_DELETED.creater
  is '创建者';
comment on column JECP.RELIABLE_MESSAGE_DELETED.editor
  is '修改者';
comment on column JECP.RELIABLE_MESSAGE_DELETED.create_time
  is '创建时间';
comment on column JECP.RELIABLE_MESSAGE_DELETED.last_update_time
  is '最后更新时间';
comment on column JECP.RELIABLE_MESSAGE_DELETED.message_key
  is '消息ID';
comment on column JECP.RELIABLE_MESSAGE_DELETED.message_body
  is '消息内容';
comment on column JECP.RELIABLE_MESSAGE_DELETED.message_data_type
  is '数据类型';
comment on column JECP.RELIABLE_MESSAGE_DELETED.confirm_timeout_queue
  is '确认超时队列';
comment on column JECP.RELIABLE_MESSAGE_DELETED.consumer_queue
  is '消费者队列';
comment on column JECP.RELIABLE_MESSAGE_DELETED.check_producer_times
  is '回查次数';
comment on column JECP.RELIABLE_MESSAGE_DELETED.resend_times
  is '重发次数';
comment on column JECP.RELIABLE_MESSAGE_DELETED.already_dead
  is '消息是否死亡';
comment on column JECP.RELIABLE_MESSAGE_DELETED.status
  is '消息状态';
comment on column JECP.RELIABLE_MESSAGE_DELETED.deleted
  is '删除标记';
alter table JECP.RELIABLE_MESSAGE_DELETED
  add primary key (ID);
alter table JECP.RELIABLE_MESSAGE_DELETED
  add constraint UK_SXOEVXM9CIDRUBBME8XOGAN0A unique (MESSAGE_KEY);


spool off
