create index IX_28DE5762 on Portfolio_Portfolio (plid);
create index IX_99B9E5C0 on Portfolio_Portfolio (publishmentType);

create index IX_6168D107 on Portfolio_PortfolioFeedback (plid);
create index IX_FB93BA92 on Portfolio_PortfolioFeedback (plid, feedbackStatus);
create index IX_836284BE on Portfolio_PortfolioFeedback (plid, hidden_);
create index IX_C0FB31FB on Portfolio_PortfolioFeedback (plid, visible);
create index IX_1E2B3BF6 on Portfolio_PortfolioFeedback (userId);

create index IX_AF3D4911 on Portfolio_PortfolioPermission (plid);
create index IX_E81ADE9C on Portfolio_PortfolioPermission (plid, feedbackStatus);
create index IX_48C1D980 on Portfolio_PortfolioPermission (userId);