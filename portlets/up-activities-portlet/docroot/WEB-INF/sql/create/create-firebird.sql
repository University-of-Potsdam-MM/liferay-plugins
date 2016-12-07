create database 'lportal.gdb' page_size 8192 user 'sysdba' password 'masterkey';
connect 'lportal.gdb' user 'sysdba' password 'masterkey';
create table Activity_ExtSocialActivity (extSocialActivityId int64 not null primary key,userId int64,type_ integer,extServiceActivityType varchar(75),extServiceName varchar(75),extServiceContext varchar(200),data_ varchar(75),published int64);
create table Activity_ExtSocialActivitySet (extSocialActivitySetId int64 not null primary key,userId int64,type_ integer,extServiceName varchar(75),extServiceContext varchar(75),data_ varchar(75),createDate timestamp);
create table Activity_Test (testId int64 not null primary key,groupId int64,companyId int64,userId int64,userName varchar(75),createDate timestamp,modifiedDate timestamp);
