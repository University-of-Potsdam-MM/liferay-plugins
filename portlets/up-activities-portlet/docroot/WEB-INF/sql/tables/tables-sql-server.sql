create table Activity_ExtSocialActivity (
	extSocialActivityId bigint not null primary key,
	userId bigint,
	type_ int,
	extServiceActivityType nvarchar(75) null,
	extServiceName nvarchar(75) null,
	extServiceContext nvarchar(200) null,
	data_ nvarchar(75) null,
	published bigint
);

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId bigint not null primary key,
	userId bigint,
	type_ int,
	extServiceName nvarchar(75) null,
	extServiceContext nvarchar(75) null,
	data_ nvarchar(75) null,
	createDate datetime null
);

create table Activity_Test (
	testId bigint not null primary key,
	groupId bigint,
	companyId bigint,
	userId bigint,
	userName nvarchar(75) null,
	createDate datetime null,
	modifiedDate datetime null
);
