create index IX_10401B05 on ShortLink_ShortLinkEntry (autogenerated);
create index IX_52A1B216 on ShortLink_ShortLinkEntry (modifiedDate);
create index IX_16F201AD on ShortLink_ShortLinkEntry (originalURL, autogenerated);
create index IX_2A06AD2 on ShortLink_ShortLinkEntry (shortURL);
create index IX_E542105E on ShortLink_ShortLinkEntry (shortURL, autogenerated);
create index IX_9219F049 on ShortLink_ShortLinkEntry (uuid_);