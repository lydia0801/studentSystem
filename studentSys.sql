drop table student;
drop table teacher;
drop table classInfo;
--�༶��Ϣ
create table classInfo(
       class_id  int primary key,
       class_name  varchar2(20) unique not null
);
create sequence seq_class_id  start with 1 increment by 1;
insert into classInfo  values(seq_class_id.nextval,'�ƿ�1401');
insert into classInfo values(seq_class_id.nextval,'�ƿ�1402');
insert into classInfo values(seq_class_id.nextval,'�ƿ�1403');
insert into classInfo values(seq_class_id.nextval,'����1401');
insert into classInfo values(seq_class_id.nextval,'����1402');
insert into classInfo values(seq_class_id.nextval,'����1403'); 
select *  from  classInfo ;


--ѧ����Ϣ  
create table student(
   stu_id  int primary  key,    --ѧ��
   class_id  int ,   --�༶���
   stu_name  varchar2(20)  not null,
   stu_pwd varchar2(20) default 'aaaaa',
   stu_sex  char(4)  not null  ,
   stu_addr  varchar2(200),
   stu_img  blob,
   school_date  date,    --��ѧʱ��
   leave_date date ,--��Уʱ��
   status   varchar2(20)  default '�ڶ�' -- �ڶ�   ��ѧ  תУ   ��ҵ  
);


create sequence seq_stu_id  start with 1000 increment by 1;
alter  table student add constraints  fk_class_id  foreign key(class_id)  references  classInfo(class_id);
insert  into student values(seq_stu_id.nextval,1,'WANGWU',default,'��','����ʡ������',null,sysdate,null,'�ڶ�');
select * from  student;

create table typeInfo(
       tid int primary key,
       tname varchar2(20)
);

create sequence seq_type_id  start with 1000 increment by 1;
insert into typeInfo values(seq_type_id.nextval,'������Ա');
insert into typeInfo values(seq_type_id.nextval,'����Ա');
select * from typeInfo;
create table teacher(
    tea_id int primary key,
    tea_name varchar2(20)  not null unique ,
    tea_pwd varchar2(20),
    tid int references typeInfo(tid)
    
);


create sequence seq_tea_id  start with 1 increment by 1;

insert into teacher values (seq_tea_id.nextval,'ܽ�ؽ��','hunan',1000);
insert into teacher values (seq_tea_id.nextval,'���ɽ��','hunan',1001);
commit ;
select * from  teacher ;

// ��¼������� 

//���±�

//���ݿ����

//�����������









