drop user &1 cascade;
create user &1 identified by &2;
grant connect,resource to &1;
connect &1/&2;
set define off;

create table Activity_ExtSocialActivity (
	extSocialActivityId number(30,0) not null primary key,
	userId number(30,0),
	type_ number(30,0),
	extServiceActivityType VARCHAR2(75 CHAR) null,
	extServiceName VARCHAR2(75 CHAR) null,
	extServiceContext VARCHAR2(200 CHAR) null,
	data_ VARCHAR2(75 CHAR) null,
	published number(30,0)
);

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId number(30,0) not null primary key,
	userId number(30,0),
	type_ number(30,0),
	extServiceName VARCHAR2(75 CHAR) null,
	extServiceContext VARCHAR2(75 CHAR) null,
	data_ VARCHAR2(75 CHAR) null,
	createDate timestamp null
);

create table Activity_Test (
	testId number(30,0) not null primary key,
	groupId number(30,0),
	companyId number(30,0),
	userId number(30,0),
	userName VARCHAR2(75 CHAR) null,
	createDate timestamp null,
	modifiedDate timestamp null
);


create index IX_9360B8B5 on Activity_ExtSocialActivity (extSocialActivityId);
create index IX_9B7274E7 on Activity_ExtSocialActivity (extSocialActivitySetId);



quit