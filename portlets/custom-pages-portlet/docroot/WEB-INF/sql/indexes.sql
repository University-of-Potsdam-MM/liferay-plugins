create index IX_49CABFD5 on CustomPage_CustomPageFeedback (plid);
create index IX_D70A4D60 on CustomPage_CustomPageFeedback (plid, feedbackStatus);
create index IX_75CCAF44 on CustomPage_CustomPageFeedback (userId);

create index IX_BD15C302 on CustomPages_CustomPageFeedback (plid);
create index IX_7FCB568D on CustomPages_CustomPageFeedback (plid, feedbackStatus);
create index IX_42639B31 on CustomPages_CustomPageFeedback (userId);