<%@ include file="/html/init.jsp"%>

<div class="portlet-msg-success" id="<portlet:namespace />myAlert">wegrerh</div>

<portlet:renderURL var="redirectURL" windowState="<%= LiferayWindowState.NORMAL.toString() %>" />
<liferay-portlet:renderURL portletName="1_WAR_privatemessagingportlet" windowState="<%= LiferayWindowState.POP_UP.toString() %>" var="privateMessagingURL">
	<portlet:param name="mvcPath" value="/new_message.jsp"/>
	<portlet:param name="redirect" value="<%= redirectURL %>"/>
</liferay-portlet:renderURL> 

<aui:script>
Liferay.provide(
    window,
    '<portlet:namespace />sendMessage',
    function(userId) {
        var uri = '<%=privateMessagingURL.toString()%>';
        uri = Liferay.Util.addParams('<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>userIds=' + userId, uri) || uri;

        Liferay.Util.openWindow({
            dialog: {
                centered: true,
                constrain: true,
                cssClass: 'private-messaging-portlet',
                destroyOnHide: true,
                height: 600,
                modal: true,
                plugins: [Liferay.WidgetZIndex],
                width: 600
            },
            id: '<%= PortalUtil.getPortletNamespace("1_WAR_privatemessagingportlet") %>Dialog',
            title: '<%= UnicodeLanguageUtil.get(pageContext, "new-message") %>',
            uri: uri
        });
    }
);
AUI().use(
    'aui-datatable',
    'datatable-paginator',
    function(A) {
        PaginatorTemplates = A.DataTable.Templates.Paginator;
        CustomPaginatorView = A.Base.create('CustomPaginatorView', A.DataTable.Paginator.View, [], {
            _initStrings: function() {
                var foo = {
                    first: '<%= LanguageUtil.get(pageContext, "first")%>',
                    prev: '<%= LanguageUtil.get(pageContext, "previous")%>',
                    next: '<%= LanguageUtil.get(pageContext, "next")%>',
                    last: '<%= LanguageUtil.get(pageContext, "last")%>',
                    goToLabel: '<%= LanguageUtil.get(pageContext, "page")%>:',
                    goToAction: '<%= LanguageUtil.get(pageContext, "custompages-go")%>',
                    perPage: '<%= LanguageUtil.get(pageContext, "custompages-rows")%>:',
                    of: '<%= LanguageUtil.get(pageContext, "of")%>'
                };
                A.Intl.add('datatable-paginator', '<%=themeDisplay.getLocale().toString()%>', foo);
                this.set('strings', A.mix((this.get('strings') || {}),
                    A.Intl.get('datatable-paginator')));
            },
            _buildGotoGroup: function() {
                var strings = this.get('strings');
                var gotoPage = PaginatorTemplates.gotoPage({
                    classNames: this.classNames,
                    strings: strings,
                    page: this.get('model').get('page')
                });
                gotoPage = gotoPage.replace('<button>', strings.of + ' <span id="itemsPerPage"></span><button>');
                return gotoPage;
            },
            _modelChange: function(e) {
                var changed = e.changed,
                    page = (changed && changed.page),
                    itemsPerPage = (changed && changed.itemsPerPage);

                container = this.get('container');
                container.one('#itemsPerPage').set('text', this.get('model').get('totalPages'));

                if (page) {
                    this._updateControlsUI(page.newVal);
                }
                if (itemsPerPage) {
                    this._updateItemsPerPageUI(itemsPerPage.newVal);
                    if (!page) {
                        this._updateControlsUI(e.target.get('page'));
                    }
                }

            }

        }, {});
    });
    
function confirmDeletion(){
	return confirm("<%=LanguageUtil.get(pageContext, "custompages-confirm-deletion")%>");
}
    
function <portlet:namespace />fadeInAlert(text){   
	var alertDiv = $( "div#<portlet:namespace />myAlert" );
	alertDiv.text(text);
	alertDiv.fadeIn( 300 ).delay( 3500 ).fadeOut( 400 );
}
</aui:script>