create table Owncloud_CustomSiteRootFolder (
	userId LONG not null,
	originalPath VARCHAR(75) not null,
	customPath VARCHAR(75) null,
	primary key (userId, originalPath)
);

create table Owncloud_UserOwncloudDirectory (
	userId LONG not null,
	siteId LONG not null,
	directoryPath VARCHAR(75) null,
	primary key (userId, siteId)
);