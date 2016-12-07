create table Activity_ExtSocialActivity (
	extSocialActivityId int8 not null primary key,
	userId int8,
	type_ int,
	extServiceActivityType varchar(75),
	extServiceName varchar(75),
	extServiceContext varchar(200),
	data_ varchar(75),
	published int8
)
extent size 16 next size 16
lock mode row;

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId int8 not null primary key,
	userId int8,
	type_ int,
	extServiceName varchar(75),
	extServiceContext varchar(75),
	data_ varchar(75),
	createDate datetime YEAR TO FRACTION
)
extent size 16 next size 16
lock mode row;

create table Activity_Test (
	testId int8 not null primary key,
	groupId int8,
	companyId int8,
	userId int8,
	userName varchar(75),
	createDate datetime YEAR TO FRACTION,
	modifiedDate datetime YEAR TO FRACTION
)
extent size 16 next size 16
lock mode row;
