package com.wow.doge.services;

import com.wow.doge.domain.questionnaire.Questionnaire;

public class QuestionnaireService extends AbstractService<Questionnaire>{

	@Override
	protected Class<Questionnaire> getHibernateClass() {
		return Questionnaire.class;
	}

}
