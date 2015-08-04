package de.unipotsdam.elis.activities;

import com.liferay.portlet.social.model.SocialRelationConstants;

public class ExtendedSocialRelationConstants extends SocialRelationConstants {

	public static final int TYPE_PORTFOLIO = 100;

	public static boolean isTypeBi(int type) {
		if ((type == TYPE_BI_CONNECTION) || (type == TYPE_BI_COWORKER) || (type == TYPE_BI_FRIEND)
				|| (type == TYPE_BI_ROMANTIC_PARTNER) || (type == TYPE_BI_SIBLING) || (type == TYPE_BI_SPOUSE)) {

			return true;
		} else {
			return false;
		}
	}

}
