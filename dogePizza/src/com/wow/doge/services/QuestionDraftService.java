package com.wow.doge.services;

import com.wow.doge.domain.questionnaire.QuestionDraft;

public class QuestionDraftService extends AbstractService<QuestionDraft> {

	@Override
	protected Class<QuestionDraft> getHibernateClass() {
		return QuestionDraft.class;
	}

}
