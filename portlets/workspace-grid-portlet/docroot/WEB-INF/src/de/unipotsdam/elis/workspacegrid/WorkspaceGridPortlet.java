package de.unipotsdam.elis.workspacegrid;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.portlet.ValidatorException;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.User;
import com.liferay.portal.service.LayoutSetPrototypeServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.social.service.SocialActivityLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class WorkspaceGridPortlet extends MVCPortlet {

	public final static String[] INIT_COLORS = new String[] { "#AB94FA", "#F6CE87", "#A7E37A", "#8BCBE6", "#FF8C9F"};
	public final static String WORKSPACE_COLOR = "workspace_color_";
	public final static String NO_TEMPLATE = "no_template";
	public final static String DEFAULT_COLOR = "#D0DAE3";

	@Override
	public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		try {
			ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
			PortletPreferences portletPreferences = request.getPreferences();

			String color = portletPreferences.getValue(WORKSPACE_COLOR + NO_TEMPLATE, "");
			if (color == "")
				portletPreferences.setValue(WORKSPACE_COLOR + NO_TEMPLATE, INIT_COLORS[0]);

			List<LayoutSetPrototype> prototypes = LayoutSetPrototypeServiceUtil.search(themeDisplay.getCompanyId(),
					true, null);
			for (int i = 0; i < prototypes.size(); i++) {
				color = portletPreferences.getValue(WORKSPACE_COLOR + prototypes.get(i).getUuid(), "");
				if (color.equals("")) {
					portletPreferences
							.setValue(WORKSPACE_COLOR + prototypes.get(i).getUuid(), INIT_COLORS[(i + 1) % (INIT_COLORS.length-1)]);
				}
			}

			portletPreferences.store();
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		super.render(request, response);
	}

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);

		try {
			if (cmd.equals("getUserWorkspaces"))
				getUserWorkspaces(resourceRequest, resourceResponse);
			else if (cmd.equals("saveWorkspaceOrder"))
				saveWorkspaceOrder(resourceRequest, resourceResponse);
			else
				super.serveResource(resourceRequest, resourceResponse);

		} catch (Exception e) {
			e.printStackTrace();
			throw new PortletException(e);
		}
	}

	public void saveWorkspaceOrder(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException, IOException, ReadOnlyException, ValidatorException {
		PortletPreferences portletPreferences = resourceRequest.getPreferences();
		List<String> workspaceOrder = new LinkedList<String>(Arrays.asList(portletPreferences.getValues(
				"workspaceOrder", new String[] {})));
		String prev = ParamUtil.getString(resourceRequest, "prev");
		String next = ParamUtil.getString(resourceRequest, "next");
		String current = ParamUtil.getString(resourceRequest, "current");
		workspaceOrder.remove(current);
		if (prev.equals("null") || prev.equals(current))
			workspaceOrder.add(0, current);
		else if (next.equals("null") || next.equals(current))
			workspaceOrder.add(current);
		else {
			workspaceOrder.add(workspaceOrder.indexOf(prev) + 1, current);
		}
		portletPreferences.setValues("workspaceOrder", workspaceOrder.toArray(new String[] {}));
		portletPreferences.store();
	}

	public void getUserWorkspaces(ResourceRequest resourceRequest, ResourceResponse resourceResponse)
			throws SystemException, PortalException, IOException, ReadOnlyException, ValidatorException,
			WindowStateException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);
		JSONArray groupJSONArray = JSONFactoryUtil.createJSONArray();
		PortletPreferences portletPreferences = resourceRequest.getPreferences();
		String[] workspaceOrder = portletPreferences.getValues("workspaceOrder", new String[] {});
		Map<String, Group> userGroups = getUserGroupMap(themeDisplay.getUser());
		List<String> newWorkspaceOrder = new ArrayList<String>();

		for (String groupId : workspaceOrder) {
			if (userGroups.containsKey(groupId)) {
				groupJSONArray.put(createGroupJSON(userGroups.remove(groupId), themeDisplay, portletPreferences,
						(LiferayPortletResponse) resourceResponse));
				newWorkspaceOrder.add(groupId);
			}
		}

		for (Group group : userGroups.values()) {
			groupJSONArray.put(createGroupJSON(group, themeDisplay, portletPreferences,
					(LiferayPortletResponse) resourceResponse));
			newWorkspaceOrder.add(String.valueOf(group.getGroupId()));
		}

		PrintWriter out = resourceResponse.getWriter();
		out.println(groupJSONArray.toString());

		portletPreferences.setValues("workspaceOrder", newWorkspaceOrder.toArray(new String[] {}));
		portletPreferences.store();
	}

	private JSONObject createGroupJSON(Group group, ThemeDisplay themeDisplay, PortletPreferences prefs,
			LiferayPortletResponse response) throws SystemException, PortalException, WindowStateException {
		JSONObject groupJSON = JSONFactoryUtil.createJSONObject();
		groupJSON.put("groupId", group.getGroupId());
		groupJSON.put("name", group.getDescriptiveName(themeDisplay.getLocale()));
		groupJSON.put("prototypeId", group.getPrivateLayoutSet().getLayoutSetPrototypeId());
		
		PortletURL portletURL = response.createActionURL(PortletKeys.SITE_REDIRECTOR);
		portletURL.setParameter("struts_action", "/my_sites/view");
		portletURL.setParameter("groupId", String.valueOf(group.getGroupId()));
		portletURL.setWindowState(WindowState.NORMAL);
		
		if (group.hasPrivateLayouts())
			portletURL.setParameter("privateLayout", Boolean.TRUE.toString());
		else
			portletURL.setParameter("privateLayout", Boolean.FALSE.toString());
		groupJSON.put("url", portletURL.toString());
		String prototypeUUID = group.getPrivateLayoutSet().getLayoutSetPrototypeUuid();
		groupJSON.put("color", prefs.getValue(WORKSPACE_COLOR
				+ ((prototypeUUID.equals("") ? NO_TEMPLATE : prototypeUUID)), DEFAULT_COLOR));
		int activitiesCount = SocialActivityLocalServiceUtil.getGroupActivitiesCount(group.getGroupId());
		groupJSON.put("activitiesCount", (activitiesCount > 0) ? String.valueOf(activitiesCount) : "");
		return groupJSON;
	}

	private Map<String, Group> getUserGroupMap(User user) throws SystemException {
		Map<String, Group> result = new HashMap<String, Group>();
		for (Group group : user.getGroups()) {
			result.put(String.valueOf(group.getGroupId()), group);
		}
		return result;
	}
}
