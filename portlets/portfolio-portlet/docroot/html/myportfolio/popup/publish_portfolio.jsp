<%@ include file="/html/init.jsp"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

<%
String portfolioPlid = renderRequest.getParameter("portfolioPlid"); 
String globalPubllishmentMethod = renderResponse.getNamespace() + "addGlobalPublishment()";
%>

<c:if test='<%= SessionMessages.contains(renderRequest, "requestProcessed") %>'>
	<div class="portlet-msg-success">
		<liferay-ui:message key="your-request-processed-successfully" />
	</div>
</c:if>

<div id="alertFeedbackRequested"></div>

<div id="<portlet:namespace />inviteMembersContainer">
	<div class="user-search-wrapper">
		<h2>
			<liferay-ui:message key="find-members" />
		</h2>

		<input class="invite-user-search" id="<portlet:namespace />inviteUserSearch" name="<portlet:namespace />userName" type="text" />

		<div class="search">
			<div class="list"></div>
		</div>
	</div>

	<div class="invited-users-wrapper">
		<div class="user-invited">
			<h2>
				<liferay-ui:message key="portfolio-manage-publishments" />

				<span>
					<liferay-ui:message key="to-add,-click-members-on-the-left" />
				</span>
			</h2>

			<div class="list">
			</div>
		</div>


		<div class="invite-actions">
			<portlet:actionURL name="publishPortfolio" var="publishPortfolioURL" />

			<portlet:renderURL var="redirectHereURL" windowState="<%= LiferayWindowState.EXCLUSIVE.toString() %>">
				<portlet:param name="mvcPath" value="/html/myportfolio/popup/publish_portfolio.jsp" />
				<portlet:param name="portfolioPlid" value="<%= portfolioPlid %>" />
			</portlet:renderURL>

			
			<aui:button value="<%=LanguageUtil.get(portletConfig, locale, \"portfolio-portalwide-publishment\")%>" onClick="<%= globalPubllishmentMethod %>" />
				
			<aui:form action="<%= publishPortfolioURL %>" id="<portlet:namespace />fm" method="post" name="<portlet:namespace />fm">
				<aui:input name="redirect" type="hidden" value="<%= redirectHereURL %>" />
				<aui:input name="receiverUserIds" type="hidden" value="" />
				<aui:input name="portfolioPlid" type="hidden" value="<%= portfolioPlid %>" />
				<aui:input name="globalPublishment" type="hidden" value="false" />
				

				<aui:button id="submit" type="submit"  />
			</aui:form>
		</div>
	</div>
</div>

<aui:script use="aui-base,datasource-io,datatype-number,liferay-so-invite-members-list">
	var closeButton = A.one('.close');
	closeButton.on("click",function(){Liferay.Util.getOpener().refreshPortlet()});
	
	var inviteMembersContainer = A.one('#<portlet:namespace />inviteMembersContainer');

	var invitedMembersList = inviteMembersContainer.one('.user-invited .list');
	var searchList = inviteMembersContainer.one('.search .list');

	var pageDelta = 50;

	var createDataSource = function(url) {
		return new A.DataSource.IO(
			{
				ioConfig: {
					method: "post"
				},
				on: {
					request: function(event) {
						var data = event.request;

						event.cfg.data = {
							<portlet:namespace />end: data.<portlet:namespace />end || pageDelta,
							<portlet:namespace />keywords: data.<portlet:namespace />keywords || '',
							<portlet:namespace />start: data.<portlet:namespace />start || 0
						}
					}
				},
				source: url
			}
		);
	}
	
	Liferay.provide(window, '<portlet:namespace />addGlobalPublishment',
	function (){
		invitedMembersList.all('.user').each(
				function(item, index) {
					var user = searchList.one('[data-userId="' + item.attr('data-userId') + '"]');
					if (user){
						user.removeClass('invited');
					}
					item.remove();
				});
		
		if (!invitedMembersList.one('.global')){
		invitedMembersList.append(getGlobalPublishmentDiv());}
	});
	
	var getGlobalPublishmentDiv = function(){
		return '<div class="invited global" onClick="<portlet:namespace />removeGlobalPublishment(event)" >' +
		'<span>'+ "<%=LanguageUtil.get(portletConfig, locale, "portfolio-portalwide-publishment")%>" + '</span>'+
		'</div>';
	}
	
	Liferay.provide(window, '<portlet:namespace />removeGlobalPublishment',
			function (event){
			event.currentTarget.remove();
			fillInvitedList(true);
			});
	
	var fillInvitedList = function(globalPublishmentDeleted){
		A.io.request(
				'<portlet:resourceURL id="getUsersPortfolioPublishedTo" ><portlet:param name="<%=Constants.CMD%>" value="getUsersPortfolioPublishedTo" /></portlet:resourceURL>',
				{
					after: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							invitedMembersList.append(renderResults(responseData,true,globalPublishmentDeleted).join(''));
							

						}
					},
					data: {
						<portlet:namespace />portfolioPlid: "<%= portfolioPlid %>"
					},
					dataType: 'json'
				}
			);
	}
	

	var inviteMembersList = new Liferay.SO.InviteMembersList(
		{
			inputNode: '#<portlet:namespace />inviteMembersContainer #<portlet:namespace />inviteUserSearch',
			listNode: '#<portlet:namespace />inviteMembersContainer .search .list',
			minQueryLength: 0,
			requestTemplate: function(query) {
				return {
					<portlet:namespace />end: pageDelta,
					<portlet:namespace />keywords: query,
					<portlet:namespace />start: 0
				}
			},
			resultTextLocator: function(response) {
				var result = '';

				if (typeof response.toString != 'undefined') {
					result = response.toString();
				}
				else if (typeof response.responseText != 'undefined') {
					result = response.responseText;
				}

				return result;
			},
			source: createDataSource('<portlet:resourceURL id="getAvailableUsers" ><portlet:param name="<%=Constants.CMD%>" value="getAvailableUsers" /></portlet:resourceURL>')
		}
	);

	var renderResults = function(responseData,invitedList,globalPublishmentDeleted) {
		var count = responseData.count;
		var options = responseData.options;
		var results = responseData.users;
		var isGlobal = responseData.isGlobal;

		var buffer = [];
		
		if (invitedList && isGlobal && !globalPublishmentDeleted){
			buffer.push(getGlobalPublishmentDiv());
			return buffer;
		}

		if (results.length == 0 && !invitedList) {
			if (options.start == 0) {
				buffer.push(
					'<div class="empty"><liferay-ui:message key="there-are-no-users-to-invite" unicode="<%= true %>" /></div>'
				);
			}
		}
		else {
			buffer.push(
				A.Array.map(
					results,
					function(result) {
						var userTemplate =
							'<div class="{cssClass}" data-userId="{userId}">' +
								'<span class="name">{userFullName}</span>'+
								'<span class="email">{userEmailAddress}</span>' +
							'</div>';
							
						var invitedUser = invitedMembersList.one('[data-userId="' + result.userId + '"]');

						var invited = invitedList || invitedUser;
						
						var feedbackRequested = "";
						if (invitedList){
							console.log(result.feedbackRequested + ' ' + result.userFullName );
							if (result.feedbackRequested){
								feedbackRequested = " feedbackRequested";
							}
							var inviteUser = searchList.one('[data-userId="' + result.userId + '"]');
							if (inviteUser ){
								if (!inviteUser.hasClass('invited')){
									inviteUser.addClass('invited');
								}
							}
						}
						else if (invitedUser){
							if (invitedUser.hasClass('feedbackRequested')){
								feedbackRequested = " feedbackRequested";
							}
						}
						
						return A.Lang.sub(
							userTemplate,
							{
								cssClass: (invited ? "invited user" : "user") + feedbackRequested,
								userEmailAddress: result.userEmailAddress,
								userFullName: result.userFullName,
								userId: result.userId
							}
						);
					}
				).join('')
			);

			if (count > results.length && !invitedList) {
				buffer.push(
					'<div class="more-results">' +
						'<a href="javascript:;" data-end="' + options.end + '"><liferay-ui:message key="view-more" unicode="<%= true %>" /></a>' +
					'</div>'
				);
			}
		}

		return buffer;
	}

	var showMoreResults = function(responseData) {
		var moreResults = searchList.one('.more-results');

		moreResults.remove();

		searchList.append(renderResults(responseData,false,false).join(''));
	}

	var updateInviteMembersList = function(event) {
		var responseData = A.JSON.parse(event.data.responseText);

		searchList.html(renderResults(responseData,false,false).join(''));
	}

	inviteMembersList.on('results', updateInviteMembersList);

	searchList.delegate(
		'click',
		function(event) {
			var node = event.currentTarget;

			var start = A.DataType.Number.parse(node.getAttribute('data-end'));

			var end = start + pageDelta;

			var inviteUserSearch = inviteMembersContainer.one('#<portlet:namespace />inviteUserSearch');

			A.io.request(
				'<portlet:resourceURL id="getAvailableUsers" ><portlet:param name="<%=Constants.CMD%>" value="getAvailableUsers" /></portlet:resourceURL>',
				{
					after: {
						success: function(event, id, obj) {
							var responseData = this.get('responseData');

							showMoreResults(responseData);
						}
					},
					data: {
						<portlet:namespace />end: end,
						<portlet:namespace />keywords: inviteUserSearch.get('value'),
						<portlet:namespace />start: start
					},
					dataType: 'json'
				}
			);
		},
		'.more-results a'
	);

	fillInvitedList(false);
	inviteMembersList.sendRequest();

</aui:script>