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
