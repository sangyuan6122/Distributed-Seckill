/*web库中创建用于压力测试的部门及人员*/
truncate table taw_system_user;
truncate table taw_system_dept;
insert into taw_system_user values (sys_guid(), 'admin',  '管理员',-999,null, 'JARUM2Ev5Hti9qj4/oY3UMowEei2s+l6RFj8AEqIrVBXpToQbiI9QEczsRUjK3aXoLNUwtNTRpdud/KuCrdnDw==',null,null,null,null,null,null,null,'admin') ;
insert into taw_system_dept values(27000000,'总公司',-999,'总公司',null,null,null,null,null,'用于压力测试');  
insert into taw_system_dept values(2700000000,'压测部',27000000,'压测部',null,null,null,null,null,'用于压力测试');  
commit; 
declare
  deptCount number; 
  userCount number; 
  deptId number;
  deptName char(100);
begin
  deptCount:=1;
  userCount := 1;
  for i in 1 .. 10 loop 
    deptName:='测试组'||deptCount;
    if deptCount<10 then deptId:= 27000000000+i;
    else
      deptId:= 2700000000+deptCount;
    end if;
    insert into taw_system_dept values(deptId,deptName,2700000000,deptName,null,null,null,null,deptCount,'用于压力测试'); 
    for j in 1 .. 10000 loop         
        insert into taw_system_user values 
        (sys_guid(), 'user'||userCount,  '测试员'||userCount,deptId,deptName, 'cxCTuuDtJcUq9QJwnH8Rc2wNZXTuCvVa19hYuCX81LI0ceexAUeuDnFJZZ+FYbQdTj//IV+/Ig2JK1VMmMXgWg==',null,null,null,null,null,null,j,'admin') ;      
        userCount := userCount + 1;      
    end loop;
    commit;
    
    deptCount:=deptCount+1;
  end loop;  
end;
/ 
/*商家库中创建资金用户*/
truncate table capital_account;
insert into capital_account values('844C3D5832B07788E05011AC10A3A','admin','1',0,0,sysdate,null);
/*买家库中创建资金账户*/
truncate table capital_account;
insert into capital_account values('844C3D5832B07788E05011AC10A3A','admin','1',1000000.00,0,sysdate,null);
declare
  userCount number; 
begin
  userCount := 1;  
    for j in 1 .. 100000 loop         
        insert into capital_account values 
        (sys_guid(), 'user'||userCount, '1',1000000,0,sysdate,null) ;      
        userCount := userCount + 1;      
    end loop;
    commit;
    
end;
/


/*积分库中创建账户*/
truncate table account_point;
insert into account_point values(sys_guid(), 'admin', '1',0,sysdate,null) ;  
declare
  userCount number; 
begin
  userCount := 1;  
    for j in 1 .. 100000 loop         
        insert into account_point values 
        (sys_guid(), 'user'||userCount, '1',0,sysdate,null) ;      
        userCount := userCount + 1;      
    end loop;
    commit;    
end;
/ 