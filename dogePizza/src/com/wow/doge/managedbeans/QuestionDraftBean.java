package com.wow.doge.managedbeans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import org.apache.log4j.Logger;

import com.wow.doge.domain.questionnaire.QuestionDraft;
import com.wow.doge.services.QuestionDraftService;

@ManagedBean
@RequestScoped
public class QuestionDraftBean {

	private static final Logger logger = Logger.getLogger(QuestionDraftBean.class);

	@ManagedProperty("#{param.questionDraftId}")
	private int questionDraftId;

	private QuestionDraft questionDraft;

	public QuestionDraftBean() {
		questionDraft = new QuestionDraft();
	}

	public Integer getId() {
		return questionDraft.getId();
	}

	public void setId(Integer id) {
		questionDraft.setId(id);
	}

	public String getQuestionText() {
		return questionDraft.getQuestionText();
	}

	public void setQuestionText(String name) {
		questionDraft.setQuestionText(name);
	}

	public int getQuestionDraftId() {
		return questionDraftId;
	}

	public void setQuestionDraftId(int questionDraftId) {
		this.questionDraftId = questionDraftId;
	}

	@Override
	public String toString() {
		return questionDraft.toString();
	}

	// ====== Funktionen ===========

	public List<QuestionDraft> getAllQuestionDrafts() {
		QuestionDraftService service = new QuestionDraftService();
		return service.getList();
	}

	public String save() {
		logger.info("Versuche QuestionDraft zu speichern... " + questionDraft);
		QuestionDraftService service = new QuestionDraftService();
		service.saveOrUpdate(questionDraft);

		return questionDraftList();
	}

	public String deleteQuestionDraft() {
		QuestionDraftService service = new QuestionDraftService();
		QuestionDraft questionDraftToDelete = service.get(questionDraftId);
		logger.info("Versuche QuestionDraf zu löschen: " + questionDraftToDelete);
		service.delete(questionDraftToDelete);
		return questionDraftList();
	}

	// ====== Links ===========

	public String createQuestionDraft() {
		return "createQuestionDraft.jsf";
	}

	public String questionDraftList() {
		return "questionDraftList.xhtml";
	}

	public String showQuestionDraft() {
		QuestionDraftService service = new QuestionDraftService();
		questionDraft = service.get(questionDraftId);
		return "showQuestionDraft.jsf";
	}

	public String changeQuestionDraft() {
		QuestionDraftService service = new QuestionDraftService();
		questionDraft = service.get(questionDraftId);
		logger.info("Versuche QuestionDraft zu aendern: " + questionDraft);
		return "changeQuestionDraft.jsf";
	}
}
