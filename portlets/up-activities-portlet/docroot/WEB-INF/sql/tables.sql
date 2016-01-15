create table Activity_ExtSocialActivity (
	extSocialActivityId LONG not null primary key,
	userId LONG,
	type_ INTEGER,
	extServiceActivityType VARCHAR(75) null,
	extServiceName VARCHAR(75) null,
	extServiceContext VARCHAR(200) null,
	data_ TEXT null,
	published LONG
);

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId LONG not null primary key,
	userId LONG,
	type_ INTEGER,
	extServiceName VARCHAR(75) null,
	extServiceContext VARCHAR(75) null,
	data_ VARCHAR(75) null,
	createDate DATE null
);

create table Activity_MoodleSocialActivity (
	extSocialActivityId LONG not null primary key,
	userId LONG,
	type_ INTEGER,
	extServiceActivityType VARCHAR(75) null,
	extServiceContext VARCHAR(1000) null,
	data_ TEXT null,
	published LONG
);

create table Activity_Test (
	testId LONG not null primary key,
	groupId LONG,
	companyId LONG,
	userId LONG,
	userName VARCHAR(75) null,
	createDate DATE null,
	modifiedDate DATE null
);