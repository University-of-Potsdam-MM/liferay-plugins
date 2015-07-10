create table Portfolio_Portfolio (
	plid LONG not null primary key,
	publishmentType INTEGER
);

create table Portfolio_PortfolioFeedback (
	plid LONG not null,
	userId LONG not null,
	feedbackStatus INTEGER,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (plid, userId)
);

create table Portfolio_PortfolioPermission (
	plid LONG not null,
	userId LONG not null,
	feedbackStatus INTEGER,
	createDate DATE null,
	modifiedDate DATE null,
	primary key (plid, userId)
);