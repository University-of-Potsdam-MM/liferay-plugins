create table Activity_ExtSocialActivity (
	extSocialActivityId bigint not null primary key,
	userId bigint,
	type_ integer,
	extServiceActivityType varchar(75),
	extServiceName varchar(75),
	extServiceContext varchar(200),
	data_ varchar(75),
	published bigint
);

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId bigint not null primary key,
	userId bigint,
	type_ integer,
	extServiceName varchar(75),
	extServiceContext varchar(75),
	data_ varchar(75),
	createDate timestamp
);

create table Activity_Test (
	testId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName varchar(75),
	createDate timestamp,
	modifiedDate timestamp
);
