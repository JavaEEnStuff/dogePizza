package com.wow.doge.services;

import com.wow.doge.domain.Question;

public class QuestionService extends AbstractService<Question> {
	@Override
	protected Class<Question> getHibernateClass() {
		return Question.class;
	}
}
