package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Answer;
import com.wow.doge.domain.Question;
import com.wow.doge.services.AnswerService;
import com.wow.doge.services.QuestionService;

@ManagedBean
@RequestScoped
public class AnswerBean {
	private static final Logger logger = Logger.getLogger(IngredientBean.class);


	@ManagedProperty("#{param.answerId}")
	private int answerId;

	private int questionId;
	private Answer answer;
	private Question question;
	
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
	
	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}
	
	public String getQuestionTitle() {
		return question.getTitle();
	}
	
	public String getQuestionDescription() {
		return question.getDescription();
	}

	public String createAnswer() {
		QuestionService service = new QuestionService();
		question = service.get(questionId);
		return "../Answer/createAnswer.xhtml";
	}
	
	public String save() {
		logger.info("Versuche Answer zu speichern... "+ answer);
		AnswerService answerService = new AnswerService();
		answerService.saveOrUpdate(answer);
		
		logger.info("Versuche Question zu speichern... "+ question);		
		QuestionService questionService = new QuestionService();
		List<Answer> answers = question.getAnswers();
		answers.add(answer);
		question.setAnswers(answers);
		int answerCount = question.getAnswerCount();
		answerCount++;
		question.setAnswerCount(answerCount);
		questionService.saveOrUpdate(question);
		
		return "questionList.xhtml";
	}
}
