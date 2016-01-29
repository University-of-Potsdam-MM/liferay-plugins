package de.unipotsdam.elis.language;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletResponse;

import com.liferay.compat.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.servlet.ServletResponseUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import de.unipotsdam.elis.language.model.LanguageKey;
import de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil;
import de.unipotsdam.elis.language.service.impl.LanguageKeyLocalServiceImpl;

public class OverrideHelpIconPortlet extends MVCPortlet {

	public void processAction(ActionRequest actionRequest, ActionResponse actionResponse) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		try {
			String actionName = ParamUtil.getString(actionRequest, ActionRequest.ACTION_NAME);
			
			if (actionName.equals("createLanguageKey")) {
				createLanguageKey(actionRequest, actionResponse);
			}
			if (actionName.equals("updateLanguageKey")) {
				updateLanguageKey(actionRequest, actionResponse);
			} else {
				super.processAction(actionRequest, actionResponse);
			}
		} catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void createLanguageKey(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// get arguments
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String key = ParamUtil.getString(uploadRequest, "key");
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		
		if (key.isEmpty())
			return;
		
		Map<Locale, String> valueMap = new HashMap<Locale, String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			valueMap.put(locale, ParamUtil.getString(uploadRequest, locale.getLanguage()));
		}

		LanguageKey languageKey = LanguageKeyLocalServiceUtil.fetchLanguageKey(key);
		
		if (languageKey == null) {
			LanguageKeyLocalServiceUtil.addLanguageKey(key,
					LocalizationUtil.updateLocalization(valueMap, "", "Name", LocaleUtil.toLanguageId(Locale.GERMAN)));
			jsonObject.put("success", Boolean.TRUE);
		} else {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void updateLanguageKey(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		// get arguments
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String key = ParamUtil.getString(uploadRequest, "key");
		Map<Locale, String> valueMap = new HashMap<Locale, String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			valueMap.put(locale, ParamUtil.getString(uploadRequest, locale.getLanguage()));
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		LanguageKey languageKey = LanguageKeyLocalServiceUtil.fetchLanguageKey(key);
		if (languageKey != null) {
			languageKey.setValue(LocalizationUtil.updateLocalization(valueMap, "", "Name",
					LocaleUtil.toLanguageId(Locale.GERMAN)));
			LanguageKeyLocalServiceUtil.updateLanguageKey(languageKey);
			jsonObject.put("success", Boolean.TRUE);
		} else {
			jsonObject.put("success", Boolean.FALSE);
		}
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void deleteEntry(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		String key = ParamUtil.getString(actionRequest, "key");
		LanguageKey languageKey = LanguageKeyLocalServiceUtil.fetchLanguageKey(key);
		if (languageKey != null)
			LanguageKeyLocalServiceUtil.deleteLanguageKey(languageKey);
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
