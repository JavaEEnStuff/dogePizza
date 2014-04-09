package com.wow.doge.managedbeans;

import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.Answer;
import com.wow.doge.domain.Question;
import com.wow.doge.services.QuestionService;

@ManagedBean
@RequestScoped
public class QuestionBean {
	private static final Logger logger = Logger.getLogger(QuestionBean.class);

	@ManagedProperty("#{param.questionId}")
	private int questionId;

	public int getQuestionId() {
		return questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	private Question question;

	public QuestionBean() {
		question = new Question();
	}

	public int getId() {
		return question.getId();
	}

	public void setId(int id) {
		question.setId(id);
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

	// ===== Funktionen ============

	public List<Answer> getAllAnswers() {
		return question.getAnswers();
	}

	public String save() {
		logger.info("Versuche Question zu speichern... " + question);
		QuestionService service = new QuestionService();
		service.saveOrUpdate(question);

		return "questionList.xhtml";
	}

	public List<Question> getAllQuestions() {
		QuestionService service = new QuestionService();
		List<Question> questionList = service.getList();
		List<Question> questionListUnique = new LinkedList<Question>();
		for (Question q : questionList) {
			if (!(questionListUnique.contains(q)))
				questionListUnique.add(q);
		}
		return questionListUnique;
	}

	// ===== Links ============
	
	public String showQuestion() {
		QuestionService service = new QuestionService();
		question = service.get(questionId);
		return "showQuestion.xhtml";
	}

	public String changeQuestion() {
		QuestionService service = new QuestionService();
		question = service.get(questionId);
		logger.info("Versuche Question zu aendern: " + question);
		return "changeQuestion.xhtml";
	}

	public String addQuestion() {
		return "createQuestion.xhtml";
	}

	public String questionList() {
		return "/resources/javaee/question/questionList.xhtml";
	}

	public String main() {
		return "/resources/javaee/main.xhtml";
	}
}
