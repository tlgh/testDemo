
delete from `organization` ;

insert into `organization` (id,create_time,description,last_modify_time,name,parent_id,principal_user_id) values ('orgRoot',NOW(),'系统创建的顶级部门',NOW(),'XXX数据中心',null,null);