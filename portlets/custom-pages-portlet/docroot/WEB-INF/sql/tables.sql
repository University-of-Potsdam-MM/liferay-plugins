create table CustomPage_CustomPageFeedback (
	plid LONG not null,
	userId LONG not null,
	feedbackStatus INTEGER,
	hidden_ BOOLEAN,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (plid, userId)
);

create table CustomPages_CustomPageFeedback (
	plid LONG not null,
	userId LONG not null,
	feedbackStatus INTEGER,
	hidden_ BOOLEAN,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (plid, userId)
);