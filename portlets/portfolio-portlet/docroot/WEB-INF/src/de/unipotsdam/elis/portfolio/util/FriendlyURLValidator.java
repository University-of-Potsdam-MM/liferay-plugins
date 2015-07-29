package de.unipotsdam.elis.portfolio.util;

import java.util.List;
import java.util.Locale;

import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutFriendlyURL;
import com.liferay.portal.service.LayoutFriendlyURLLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.util.Portal;
import com.liferay.portal.util.PortalUtil;

public class FriendlyURLValidator {
	
	public static boolean isDuplicate(long groupId, boolean privateLayout, long layoutId, String friendlyURL) throws SystemException, PortalException{
		@SuppressWarnings("unchecked")
		List<LayoutFriendlyURL> layoutFriendlyURLs = LayoutFriendlyURLLocalServiceUtil
				.dynamicQuery(DynamicQueryFactoryUtil.forClass(LayoutFriendlyURL.class)
						.add(PropertyFactoryUtil.forName("groupId").eq(groupId))
						.add(PropertyFactoryUtil.forName("friendlyURL").eq(friendlyURL)));

		for (LayoutFriendlyURL layoutFriendlyURL : layoutFriendlyURLs) {
			Layout layout = LayoutLocalServiceUtil.getLayout(layoutFriendlyURL.getPlid());

			if (layout.getLayoutId() != layoutId) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean validateFriendlyURL(long groupId, boolean privateLayout, long layoutId, String friendlyURL) throws SystemException, PortalException{
		if (friendlyURL.length() < 2) {
			return false;
		}

		if ((friendlyURL.length() > LayoutConstants.FRIENDLY_URL_MAX_LENGTH)) {

			return false;
		}

		if (!friendlyURL.startsWith(StringPool.SLASH)) {
			return false;
		}

		if (friendlyURL.endsWith(StringPool.SLASH)) {
			return false;
		}

		if (friendlyURL.contains(StringPool.DOUBLE_SLASH)) {
			return false;
		}

		for (char c : friendlyURL.toCharArray()) {
			if (!Validator.isChar(c) && !Validator.isDigit(c) &&
				(c != CharPool.DASH) && (c != CharPool.PERCENT) &&
				(c != CharPool.PERIOD) && (c != CharPool.PLUS) &&
				(c != CharPool.SLASH) && (c != CharPool.STAR) &&
				(c != CharPool.UNDERLINE)) {

				return false;
			}
		}
		
		if (friendlyURL.contains(Portal.FRIENDLY_URL_SEPARATOR)) {
			return false;
		}
		
		List<FriendlyURLMapper> friendlyURLMappers =
				PortletLocalServiceUtil.getFriendlyURLMappers();

			for (FriendlyURLMapper friendlyURLMapper : friendlyURLMappers) {
				if (friendlyURLMapper.isCheckMappingWithPrefix()) {
					continue;
				}

				String mapping = StringPool.SLASH + friendlyURLMapper.getMapping();

				if (friendlyURL.contains(mapping + StringPool.SLASH) ||
					friendlyURL.endsWith(mapping)) {

					return false;
				}
			}
			
			Locale[] availableLocales = LanguageUtil.getAvailableLocales();

			for (Locale locale : availableLocales) {
				String languageId = StringUtil.toLowerCase(
					LocaleUtil.toLanguageId(locale));

				String i18nPathLanguageId =
					StringPool.SLASH +
						PortalUtil.getI18nPathLanguageId(locale, languageId);

				if (friendlyURL.startsWith(i18nPathLanguageId + StringPool.SLASH) ||
					friendlyURL.startsWith(
						StringPool.SLASH + languageId + StringPool.SLASH) ||
					friendlyURL.endsWith(i18nPathLanguageId) ||
					friendlyURL.endsWith(StringPool.SLASH + languageId)) {

					return false;
				}
			}
			
			String layoutIdFriendlyURL = friendlyURL.substring(1);

			if (Validator.isNumber(layoutIdFriendlyURL) &&
				!layoutIdFriendlyURL.equals(String.valueOf(layoutId))) {

				return false;
			}
		
		return true;
	}

}
