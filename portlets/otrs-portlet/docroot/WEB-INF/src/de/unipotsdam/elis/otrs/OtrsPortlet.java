package de.unipotsdam.elis.otrs;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class OtrsPortlet extends MVCPortlet {

	public void submitForm(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException, JSONException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		JSONObject json = JSONFactoryUtil.createJSONObject();
		JSONObject ticketJson = JSONFactoryUtil.createJSONObject();
		ticketJson.put("Title", ParamUtil.getString(actionRequest, "title"));
		ticketJson.put("CustomerUser", "kundenbenutzer");
		ticketJson.put("Queue", ParamUtil.getString(actionRequest, "queue"));
		ticketJson.put("State", "new");
		ticketJson.put("PriorityID", ParamUtil.getString(actionRequest, "priority"));
		JSONObject articleJson = JSONFactoryUtil.createJSONObject();
		articleJson.put("Subject", "somesubject");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Date:" + ParamUtil.getString(actionRequest, "date"));
		sb.append("\n");
		sb.append("Content:" + ParamUtil.getString(actionRequest, "content"));
		sb.append("\n");
		sb.append("Comment:" + ParamUtil.getString(actionRequest, "comment"));
		sb.append("\n");

		articleJson.put("Body", sb.toString());
		articleJson.put("MimeType", MediaType.TEXT_PLAIN);
		articleJson.put("Charset", "ISO-8859-15");
		
		json.put("Ticket", ticketJson);
		json.put("Article", articleJson);
		
		System.out.println(json.toString());

		Client client = ClientBuilder.newClient();
		WebTarget target = client
				.target("http://larvenroller.cs.uni-potsdam.de/otrs/nph-genericinterface.pl/Webservice/Test/SessionCreate?UserLogin=test&Password=test");

		Response response = target.request(MediaType.APPLICATION_JSON_TYPE).get();
		
		if (response.getStatus() == 200){
			JSONObject responseJson = JSONFactoryUtil.createJSONObject(response.readEntity(String.class));
			String sessionId = responseJson.getString("SessionID");
			
			target = client.target("http://larvenroller.cs.uni-potsdam.de/otrs/nph-genericinterface.pl/Webservice/Test/TicketCreate?SessionID=" + sessionId);
			
			response = target.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(json.toString(), MediaType.APPLICATION_JSON));
			
			System.out.println(response.getStatus());
			System.out.println(response.readEntity(String.class));
		}
	}

}
