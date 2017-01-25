package de.unipotsdam.elis.language;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.MimeResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import javax.servlet.http.HttpServletResponse;

import com.liferay.compat.util.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.servlet.HttpHeaders;
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

public class OverrideHelpIconPortlet extends MVCPortlet {

	public void processAction(ActionRequest actionRequest,
			ActionResponse actionResponse) throws PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isSignedIn()) {
			return;
		}

		try {
			String actionName = ParamUtil.getString(actionRequest,
					ActionRequest.ACTION_NAME);

			if (actionName.equals("createLanguageKey")) {
				createLanguageKey(actionRequest, actionResponse);
			}
			if (actionName.equals("updateLanguageKey")) {
				updateLanguageKey(actionRequest, actionResponse);
			}
			if (actionName.equals("importLanguageKeys")) {
				importLanguageKeys(actionRequest, actionResponse);
			} else {
				super.processAction(actionRequest, actionResponse);
			}
		} catch (Exception e) {
			throw new PortletException(e);
		}
	}

	public void serveResource(ResourceRequest req, ResourceResponse res)
			throws PortletException, IOException {
		try {
			exportLanguageKeys(req, res);
		} catch (Exception e) {
			e.printStackTrace();
		}

		super.serveResource(req, res);
	}

	public void createLanguageKey(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		// get arguments
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);
		String key = ParamUtil.getString(uploadRequest, "key");
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (key.isEmpty())
			return;

		Map<Locale, String> valueMap = new HashMap<Locale, String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			valueMap.put(locale,
					ParamUtil.getString(uploadRequest, locale.getLanguage()));
		}

		LanguageKey languageKey = LanguageKeyLocalServiceUtil
				.fetchLanguageKey(key);

		if (languageKey == null) {
			LanguageKeyLocalServiceUtil.addLanguageKey(key, LocalizationUtil
					.updateLocalization(valueMap, "", "Name",
							LocaleUtil.toLanguageId(Locale.GERMAN)));
			jsonObject.put("success", Boolean.TRUE);
		} else {
			jsonObject.put("success", Boolean.FALSE);
		}

		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void updateLanguageKey(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		// get arguments
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);
		String key = ParamUtil.getString(uploadRequest, "key");
		Map<Locale, String> valueMap = new HashMap<Locale, String>();
		for (Locale locale : LanguageUtil.getAvailableLocales()) {
			valueMap.put(locale,
					ParamUtil.getString(uploadRequest, locale.getLanguage()));
		}

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		LanguageKey languageKey = LanguageKeyLocalServiceUtil
				.fetchLanguageKey(key);
		if (languageKey != null) {
			languageKey.setValue(LocalizationUtil.updateLocalization(valueMap,
					"", "Name", LocaleUtil.toLanguageId(Locale.GERMAN)));
			LanguageKeyLocalServiceUtil.updateLanguageKey(languageKey);
			jsonObject.put("success", Boolean.TRUE);
		} else {
			jsonObject.put("success", Boolean.FALSE);
		}
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	public void deleteEntry(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		String key = ParamUtil.getString(actionRequest, "key");
		LanguageKey languageKey = LanguageKeyLocalServiceUtil
				.fetchLanguageKey(key);
		if (languageKey != null)
			LanguageKeyLocalServiceUtil.deleteLanguageKey(languageKey);
	}

	public void exportLanguageKeys(ResourceRequest req, ResourceResponse res)
			throws Exception {
		res.setContentType("text/plain");
		res.setProperty(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"languageKeys.json\"");
		res.addProperty(HttpHeaders.CACHE_CONTROL,
				"max-age=3600, must-revalidate");

		OutputStream out = res.getPortletOutputStream();

		List<LanguageKey> languageKeys = LanguageKeyLocalServiceUtil
				.getLanguageKeies(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		JSONArray result = JSONFactoryUtil.createJSONArray();
		for (LanguageKey languageKey : languageKeys) {
			JSONObject languageKeyJSON = JSONFactoryUtil.createJSONObject();
			languageKeyJSON.put("key", languageKey.getKey());
			languageKeyJSON.put("value", languageKey.getValue());
			result.put(languageKeyJSON);
		}

		InputStream in = new ByteArrayInputStream(result.toString().getBytes(
				StandardCharsets.UTF_8));

		byte[] buffer = new byte[4096];
		int len;

		while ((len = in.read(buffer)) != -1) {
			out.write(buffer, 0, len);
		}

		out.flush();
		in.close();
		out.close();
	}

	public void importLanguageKeys(ActionRequest actionRequest,
			ActionResponse actionResponse) throws Exception {
		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);
		String languageKeys = ParamUtil.getString(uploadRequest,
				"importContent");
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();
		try {
			JSONArray languageKeysJSONArray = JSONFactoryUtil
					.createJSONArray(languageKeys);
			for (int i = 0; i < languageKeysJSONArray.length(); i++) {
				JSONObject languageKeyJSONObject = languageKeysJSONArray
						.getJSONObject(i);
				LanguageKey languageKey = LanguageKeyLocalServiceUtil
						.fetchLanguageKey(languageKeyJSONObject
								.getString("key"));

				if (languageKey != null) {
					languageKey.setValue(languageKeyJSONObject
							.getString("value"));
					LanguageKeyLocalServiceUtil.updateLanguageKey(languageKey);
				} else {
					LanguageKeyLocalServiceUtil.addLanguageKey(
							languageKeyJSONObject.getString("key"),
							languageKeyJSONObject.getString("value"));
				}
			}
		} catch (Exception e) {
			jsonObject.put("success", Boolean.FALSE);
			writeJSON(actionRequest, actionResponse, jsonObject);
		}
		jsonObject.put("success", Boolean.TRUE);
		writeJSON(actionRequest, actionResponse, jsonObject);
	}

	@Override
	protected void writeJSON(PortletRequest portletRequest,
			ActionResponse actionResponse, Object json) throws IOException {
		HttpServletResponse response = PortalUtil
				.getHttpServletResponse(actionResponse);
		response.setContentType(ContentTypes.TEXT_HTML);
		ServletResponseUtil.write(response, json.toString());
		response.flushBuffer();
	}

	@Override
	protected void writeJSON(PortletRequest portletRequest,
			MimeResponse mimeResponse, Object json) throws IOException {
		mimeResponse.setContentType(ContentTypes.TEXT_HTML);
		PortletResponseUtil.write(mimeResponse, json.toString());
		mimeResponse.flushBuffer();
	}
}
