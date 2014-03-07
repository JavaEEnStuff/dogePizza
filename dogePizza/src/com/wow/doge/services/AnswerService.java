package com.wow.doge.services;

import com.wow.doge.domain.Answer;

public class AnswerService extends AbstractService<Answer> {
	@Override
	protected Class<Answer> getHibernateClass() {
		return Answer.class;
	}
}
