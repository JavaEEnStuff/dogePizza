package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Ingredient;
import com.wow.doge.domain.Question;
import com.wow.doge.services.IngredientService;
import com.wow.doge.services.QuestionService;

@ManagedBean
@RequestScoped
public class QuestionBean {
	private static final Logger logger = Logger.getLogger(MealBean.class);

	private Question question;

	public QuestionBean() {
		question = new Question();
	}
	
	public int getId() {
		return question.getId();
	}
	
	public String getTitle() {
		return question.getTitle();
	}
	
	public void setTitle(String title) {
		question.setTitle(title);
	}
	
	public String getDescription() {
		return question.getDescription();
	}
	
	public void setDescription(String description) {
		question.setDescription(description);
	}
	
	public int getAnswerCount() {
		return question.getAnswerCount();
	}
	
	public void setAnswerCount(int answerCount) {
		question.setAnswerCount(answerCount);
	}
	
	public String addQuestion() {
		return "createQuestion.xhtml";
	}
	
	public String questionList() {
		return "questionList.xhtml";
	}
	
	public String save() {
		logger.info("Versuche Question zu speichern... "+ question);
		QuestionService service = new QuestionService();
		service.saveOrUpdate(question);

		return "questionList.xhtml";
	}
	
	public List<Question> getAllQuestions() {
		QuestionService service = new QuestionService();
		return service.getList();
	}
}
