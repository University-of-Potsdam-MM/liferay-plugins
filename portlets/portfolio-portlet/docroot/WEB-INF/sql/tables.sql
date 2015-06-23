create table Portfolio_PortfolioPermission (
	plid LONG not null,
	userId LONG not null,
	primary key (plid, userId)
);