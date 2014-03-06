package com.wow.doge.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Answer;

@ManagedBean
@RequestScoped
public class AnswerBean {
	private static final Logger logger = Logger.getLogger(IngredientBean.class);


	@ManagedProperty("#{param.answerId}")
	private int answerId;

	private Answer answer;
	
	public AnswerBean() {
		answer = new Answer();
	}

	public int getAnswerId() {
		return answerId;
	}

	public void setAnswerId(int answerId) {
		this.answerId = answerId;
	}
	
	public int getId() {
		return answer.getId();
	}
	
	public void setId(int id) {
		answer.setId(id);
	}
	
	public String getText() {
		return answer.getText();
	}
	
	public void setText(String text) {
		answer.setText(text);
	}
}
