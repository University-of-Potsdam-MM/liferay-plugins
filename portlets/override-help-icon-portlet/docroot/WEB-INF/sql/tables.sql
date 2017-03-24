create table Language_LanguageKey (
	key_ VARCHAR(75) not null primary key,
	value TEXT null,
	tooltipContent TEXT null
);

create table Language_LanguageKeyJournalArticle (
	key_ VARCHAR(75) not null,
	articleId VARCHAR(75) not null,
	primary key (key_, articleId)
);