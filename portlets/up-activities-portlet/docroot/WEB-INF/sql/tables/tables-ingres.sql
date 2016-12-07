create table Activity_ExtSocialActivity (
	extSocialActivityId bigint not null primary key,
	userId bigint,
	type_ integer,
	extServiceActivityType varchar(75) null,
	extServiceName varchar(75) null,
	extServiceContext varchar(200) null,
	data_ varchar(75) null,
	published bigint
);

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId bigint not null primary key,
	userId bigint,
	type_ integer,
	extServiceName varchar(75) null,
	extServiceContext varchar(75) null,
	data_ varchar(75) null,
	createDate timestamp null
);

create table Activity_Test (
	testId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75) null,
	createDate timestamp null,
	modifiedDate timestamp null
);
