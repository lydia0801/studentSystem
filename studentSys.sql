drop table student;
drop table teacher;
drop table classInfo;
--班级信息
create table classInfo(
       class_id  int primary key,
       class_name  varchar2(20) unique not null
);
create sequence seq_class_id  start with 1 increment by 1;
insert into classInfo  values(seq_class_id.nextval,'计科1401');
insert into classInfo values(seq_class_id.nextval,'计科1402');
insert into classInfo values(seq_class_id.nextval,'计科1403');
insert into classInfo values(seq_class_id.nextval,'网络1401');
insert into classInfo values(seq_class_id.nextval,'网络1402');
insert into classInfo values(seq_class_id.nextval,'网络1403'); 
select *  from  classInfo ;


--学生信息  
create table student(
   stu_id  int primary  key,    --学号
   class_id  int ,   --班级编号
   stu_name  varchar2(20)  not null,
   stu_pwd varchar2(20) default 'aaaaa',
   stu_sex  char(4)  not null  ,
   stu_addr  varchar2(200),
   stu_img  blob,
   school_date  date,    --入学时间
   leave_date date ,--离校时间
   status   varchar2(20)  default '在读' -- 在读   休学  转校   毕业  
);


create sequence seq_stu_id  start with 1000 increment by 1;
alter  table student add constraints  fk_class_id  foreign key(class_id)  references  classInfo(class_id);
insert  into student values(seq_stu_id.nextval,1,'WANGWU',default,'男','湖南省衡阳市',null,sysdate,null,'在读');
select * from  student;

create table typeInfo(
       tid int primary key,
       tname varchar2(20)
);

create sequence seq_type_id  start with 1000 increment by 1;
insert into typeInfo values(seq_type_id.nextval,'教务人员');
insert into typeInfo values(seq_type_id.nextval,'辅导员');
select * from typeInfo;
create table teacher(
    tea_id int primary key,
    tea_name varchar2(20)  not null unique ,
    tea_pwd varchar2(20),
    tid int references typeInfo(tid)
    
);


create sequence seq_tea_id  start with 1 increment by 1;

insert into teacher values (seq_tea_id.nextval,'芙蓉姐姐','hunan',1000);
insert into teacher values (seq_tea_id.nextval,'神仙姐姐','hunan',1001);
commit ;
select * from  teacher ;

// 登录界面完成 

//记事本

//数据库设计

//继续界面设计









