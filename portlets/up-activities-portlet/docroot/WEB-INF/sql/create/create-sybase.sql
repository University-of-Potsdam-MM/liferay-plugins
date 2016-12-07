use master
exec sp_dboption 'lportal', 'allow nulls by default' , true
go

exec sp_dboption 'lportal', 'select into/bulkcopy/pllsort' , true
go

use lportal

create table Activity_ExtSocialActivity (
	extSocialActivityId decimal(20,0) not null primary key,
	userId decimal(20,0),
	type_ int,
	extServiceActivityType varchar(75) null,
	extServiceName varchar(75) null,
	extServiceContext varchar(200) null,
	data_ varchar(75) null,
	published decimal(20,0)
)
go

create table Activity_ExtSocialActivitySet (
	extSocialActivitySetId decimal(20,0) not null primary key,
	userId decimal(20,0),
	type_ int,
	extServiceName varchar(75) null,
	extServiceContext varchar(75) null,
	data_ varchar(75) null,
	createDate datetime null
)
go

create table Activity_Test (
	testId decimal(20,0) not null primary key,
	groupId decimal(20,0),
	companyId decimal(20,0),
	userId decimal(20,0),
	userName varchar(75) null,
	createDate datetime null,
	modifiedDate datetime null
)
go




