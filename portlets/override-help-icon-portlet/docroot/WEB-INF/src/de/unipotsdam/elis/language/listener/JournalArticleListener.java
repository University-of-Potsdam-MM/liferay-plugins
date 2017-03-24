package de.unipotsdam.elis.language.listener;

import java.util.List;

import com.liferay.portal.ModelListenerException;
import com.liferay.portal.model.BaseModelListener;
import com.liferay.portlet.journal.model.JournalArticle;

import de.unipotsdam.elis.language.HelperUtil;
import de.unipotsdam.elis.language.model.LanguageKey;
import de.unipotsdam.elis.language.model.LanguageKeyJournalArticle;
import de.unipotsdam.elis.language.service.LanguageKeyJournalArticleLocalServiceUtil;
import de.unipotsdam.elis.language.service.LanguageKeyLocalServiceUtil;

public class JournalArticleListener extends BaseModelListener<JournalArticle> {

	@Override
	public void onAfterUpdate(JournalArticle model)
			throws ModelListenerException {
		updateLanguageKey(model);
		super.onAfterUpdate(model);
	}

	private void updateLanguageKey(JournalArticle model)
			throws ModelListenerException {
		try {
			List<LanguageKeyJournalArticle> languageKeyJournalArticles = LanguageKeyJournalArticleLocalServiceUtil
					.getLanguageKeyJournalArticlesByArticleId(model
							.getArticleId());

			for (LanguageKeyJournalArticle languageKeyJournalArticle : languageKeyJournalArticles) {
				LanguageKey languageKey = LanguageKeyLocalServiceUtil
						.fetchLanguageKey(languageKeyJournalArticle.getKey());
				if (languageKey == null)
					LanguageKeyJournalArticleLocalServiceUtil
							.deleteLanguageKeyJournalArticle(languageKeyJournalArticle);
				HelperUtil.updateTooltipContent(languageKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
