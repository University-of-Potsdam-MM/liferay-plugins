<%@ page import="java.util.List"%>
<%@ page import="java.text.Format"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="javax.portlet.PortletURL"%>

<%@ page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.portlet.LiferayPortletMode"%>
<%@ page import="com.liferay.portal.kernel.dao.search.ResultRow"%>
<%@ page import="com.liferay.portal.kernel.util.LocaleUtil"%>
<%@ page import="com.liferay.portal.kernel.util.ParamUtil"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys"%>
<%@ page import="com.liferay.portal.kernel.util.Constants"%>
<%@ page import="com.liferay.portal.kernel.util.FastDateFormatFactoryUtil"%>
<%@ page import="com.liferay.portal.kernel.language.UnicodeLanguageUtil"%>
<%@ page import="com.liferay.portal.kernel.language.LanguageUtil"%>
<%@ page import="com.liferay.portal.kernel.json.JSONFactoryUtil"%>
<%@ page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@ page import="com.liferay.portal.service.LayoutLocalServiceUtil"%>
<%@ page import="com.liferay.portal.service.UserLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.Layout"%>
<%@ page import="com.liferay.portal.model.User"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>
<%@ page import="com.liferay.compat.portal.kernel.util.HtmlUtil"%>
<%@ page import="com.liferay.portal.model.Team"%>
<%@ page import="com.liferay.portal.service.permission.GroupPermissionUtil"%>
<%@ page import="com.liferay.portal.service.TeamLocalServiceUtil"%>
<%@ page import="com.liferay.portal.security.permission.ActionKeys"%>
<%@ page import="com.liferay.portal.kernel.dao.orm.QueryUtil"%>
<%@ page import="com.liferay.portal.model.RoleConstants"%>
<%@ page import="com.liferay.portlet.usersadmin.util.UsersAdminUtil"%>
<%@ page import="com.liferay.portal.service.RoleLocalServiceUtil"%>
<%@ page import="com.liferay.portal.model.Role"%>
<%@ page import="com.liferay.portal.model.Group"%>
<%@ page import="com.liferay.portal.service.GroupLocalServiceUtil"%>
<%@ page import="com.liferay.portal.kernel.servlet.SessionMessages"%>
<%@ page import="de.unipotsdam.elis.portfolio.util.jsp.JspHelper"%>
<%@ page import="de.unipotsdam.elis.portfolio.model.Portfolio"%>
<%@ page import="de.unipotsdam.elis.portfolio.model.PortfolioFeedback"%>
<%@ page import="de.unipotsdam.elis.portfolio.PortfolioStatics"%>
<%@ page import="de.unipotsdam.elis.portfolio.service.PortfolioLocalServiceUtil"%>

<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib uri="http://liferay.com/tld/theme" prefix="liferay-theme"%>
<%@ taglib uri="http://liferay.com/tld/ui" prefix="liferay-ui"%>
<%@ taglib uri="http://liferay.com/tld/aui" prefix="aui"%>
<%@ taglib uri="http://liferay.com/tld/portlet" prefix="liferay-portlet"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<portlet:defineObjects />
<liferay-theme:defineObjects />

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
                    goToAction: '<%= LanguageUtil.get(pageContext, "portfolio-go")%>',
                    perPage: '<%= LanguageUtil.get(pageContext, "portfolio-rows")%>:',
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
</aui:script>