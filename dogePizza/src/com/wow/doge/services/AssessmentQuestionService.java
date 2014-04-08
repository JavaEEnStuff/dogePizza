package com.wow.doge.services;

import com.wow.doge.domain.questionnaire.AssessmentQuestion;

public class AssessmentQuestionService extends AbstractService<AssessmentQuestion> {

	@Override
	protected Class<AssessmentQuestion> getHibernateClass() {
		return AssessmentQuestion.class;
	}

}
