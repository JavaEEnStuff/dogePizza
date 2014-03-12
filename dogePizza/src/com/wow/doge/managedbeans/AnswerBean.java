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

	private int answerId;

	@ManagedProperty("#{param.questionId}")
	private int questionId;
	
	private Answer answer;
	private Question question;
	
	public AnswerBean() {
		answer = new Answer();
		question = new Question();
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
		return "/resources/javaee/Answer/createAnswer.xhtml";
	}
	
	public String save() {
		logger.info("Versuche Answer zu speichern... "+ answer);
		AnswerService answerService = new AnswerService();
		answerService.saveOrUpdate(answer);
		
		QuestionService questionService = new QuestionService();
		question = questionService.get(questionId);
		logger.info("Versuche Question zu speichern... "+ question);	
		List<Answer> answers = question.getAnswers();
		answers.add(answer);
		question.setAnswers(answers);
		int answerCount = question.getAnswerCount();
		answerCount++;
		question.setAnswerCount(answerCount);
		questionService.saveOrUpdate(question);
		
		return "/resources/javaee/question/questionList.xhtml";
	}
}
