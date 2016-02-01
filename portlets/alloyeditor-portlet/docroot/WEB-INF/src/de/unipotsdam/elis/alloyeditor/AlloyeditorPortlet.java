package de.unipotsdam.elis.alloyeditor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

import de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil;

public class AlloyeditorPortlet extends MVCPortlet {

	@Override
	public void serveResource(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws IOException,
			PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		String cmd = ParamUtil.getString(resourceRequest, Constants.CMD);
		try {
			if (cmd.equals("saveContent"))
				saveContent(resourceRequest, resourceResponse);

		} catch (Exception e) {
			e.printStackTrace();
			throw new PortletException(e);
		}
	}

	private void saveContent(ResourceRequest resourceRequest, ResourceResponse resourceResponse) throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay) resourceRequest.getAttribute(WebKeys.THEME_DISPLAY);

		String content = ParamUtil.getString(resourceRequest, "content");
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		String portletId = portletDisplay.getId();

		Map<Locale, String> contentMap = new HashMap<Locale, String>();
		contentMap.put(Locale.US, "![CDATA[" + content + "]]");
		String xmlContent = LocalizationUtil.updateLocalization(contentMap, "", "static-content", LocaleUtil.toLanguageId(Locale.US));

		JournalArticle article = JournalArticleLocalServiceUtil.fetchArticle(themeDisplay.getLayout().getGroupId(),
				portletId, 1);
		
		if (article == null) {
			Map<Locale, String> titleMap = new HashMap<Locale, String>();
			String title = StringUtil.randomId();
			titleMap.put(Locale.US, title);
			titleMap.put(Locale.GERMANY, title);
			
			article = JournalArticleLocalServiceUtil.addArticle(themeDisplay.getUserId(), themeDisplay.getLayout()
					.getGroupId(), 0, titleMap, null, xmlContent, null, null, ServiceContextFactory
					.getInstance(resourceRequest));
			article.setArticleId(portletId);
			JournalArticleLocalServiceUtil.updateJournalArticle(article);
		} else {
			article.setContent(xmlContent);
			JournalArticleLocalServiceUtil.updateJournalArticle(article);
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		jsonObject.put("success", Boolean.TRUE);
		writeJSON(resourceRequest, resourceResponse, jsonObject);
	}
	
	@Override
	protected void writeJSON(PortletRequest portletRequest, ActionResponse actionResponse, Object json)
			throws IOException {
		HttpServletResponse response = PortalUtil.getHttpServletResponse(actionResponse);
		response.setContentType(ContentTypes.TEXT_HTML);
		ServletResponseUtil.write(response, json.toString());
		response.flushBuffer();
	}

	@Override
	protected void writeJSON(PortletRequest portletRequest, MimeResponse mimeResponse, Object json) throws IOException {
		mimeResponse.setContentType(ContentTypes.TEXT_HTML);
		PortletResponseUtil.write(mimeResponse, json.toString());
		mimeResponse.flushBuffer();
	}
}
