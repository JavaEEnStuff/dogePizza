package com.wow.doge.startup;

import java.util.List;

import com.wow.doge.domain.questionnaire.QuestionDraft;
import com.wow.doge.services.QuestionDraftService;

public class QuestionDraftStartupCreator implements StartupCreator {

	@Override
	public void create() {
		QuestionDraftService service = new QuestionDraftService();
		List<QuestionDraft> drafts = service.getList();
		if (drafts == null || drafts.size() == 0) {
			QuestionDraft time = new QuestionDraft();
			time.setQuestionText("Geschwindigkeit der Lieferung");
			service.saveOrUpdate(time);

			QuestionDraft taste = new QuestionDraft();
			taste.setQuestionText("Geschmack");
			service.saveOrUpdate(taste);

			QuestionDraft temperature = new QuestionDraft();
			temperature.setQuestionText("Temperatur");
			service.saveOrUpdate(temperature);

			QuestionDraft amount = new QuestionDraft();
			amount.setQuestionText("Menge");
			service.saveOrUpdate(amount);
		}
	}

}
