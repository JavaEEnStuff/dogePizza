package com.wow.doge.managedbeans;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;

import com.wow.doge.domain.Order;
import com.wow.doge.domain.questionnaire.AssessmentQuestion;
import com.wow.doge.domain.questionnaire.QuestionDraft;
import com.wow.doge.domain.questionnaire.Questionnaire;
import com.wow.doge.services.AssessmentQuestionService;
import com.wow.doge.services.OrderService;
import com.wow.doge.services.QuestionDraftService;
import com.wow.doge.services.QuestionnaireService;
import com.wow.doge.services.SelectionHelper;

@ManagedBean
@RequestScoped
public class QuestionnaireBean {

	private static final Logger logger = Logger.getLogger(QuestionnaireBean.class);

	//======= ManagedProperties ============

	@ManagedProperty("#{param.questionnaireId}")
	private int questionnaireId;

	public int getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(int questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	public SessionBean getSessionBean() {
		return sessionBean;
	}

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	@ManagedProperty("#{param.orderId}")
	private int orderId;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		logger.info("OrderID:" + orderId);
		this.orderId = orderId;
		OrderService orderService = new OrderService();
		Order order = orderService.get(orderId);

		QuestionnaireService questionnaireService = new QuestionnaireService();
		SelectionHelper<Questionnaire> helper = new SelectionHelper<>();
		helper.addCriterion(Restrictions.eq("order", order));
		List<Questionnaire> found = questionnaireService.getList(helper);
		if (found == null || found.size() == 0) {
			logger.info("Kein Questionnaire zum Auftrag gefunden!");
			questionnaire = new Questionnaire();
			questionnaire.setDate(new Date().getTime());
			questionnaire.setOrder(order);
			questions = new LinkedList<>();
			QuestionDraftService service = new QuestionDraftService();
			List<QuestionDraft> drafts = service.getList();
			for (QuestionDraft draft : drafts) {
				AssessmentQuestion e = new AssessmentQuestion();
				e.setQuestionText(draft.getQuestionText());
				questions.add(e);
			}
		} else {
			logger.info("Es wurde ein Questionnaire zum Auftrag gefunden!");
			// Bereits vorhanden
			questionnaire = found.get(0);
			questions = new LinkedList<>();
			for (AssessmentQuestion nextQuestion : questionnaire.getQuestions()) {
				logger.info(nextQuestion);
				questions.add(nextQuestion);
			}
		}
	}

	// ========= Rest der Bean ==============

	private Questionnaire questionnaire;
	private List<AssessmentQuestion> questions;

	public QuestionnaireBean() {
		logger.info("Konstruktor");
		questionnaire = new Questionnaire();
	}

	public Integer getId() {
		return questionnaire.getId();
	}

	public void setId(Integer id) {
		questionnaire.setId(id);
	}

	public Long getDate() {
		return questionnaire.getDate();
	}

	public void setDate(Long date) {
		logger.info("date:" + date);
		questionnaire.setDate(date);
	}

	public Integer getOverallGrade() {
		return questionnaire.getOverallGrade();
	}

	public void setOverallGrade(Integer overallGrade) {
		logger.info("overallGrade:" + overallGrade);
		questionnaire.setOverallGrade(overallGrade);
	}

	public String getRemark() {
		return questionnaire.getRemark();
	}

	public void setRemark(String remark) {
		logger.info("remark:" + remark);
		questionnaire.setRemark(remark);
	}

	public List<AssessmentQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<AssessmentQuestion> questions) {
		this.questions = questions;
	}

	public String save() {
		logger.info("Versuche Questionnaire zu speichern... " + questionnaire);
		QuestionnaireService service = new QuestionnaireService();
		OrderService orderService = new OrderService();
		
		// Questionnaire wegschreiben
		Order order = orderService.get(orderId);
		questionnaire.setOrder(order);
		service.saveOrUpdate(questionnaire);
		
		// Bestellung wegschreiben
		order.setQuestionnaire(questionnaire);
		service.saveOrUpdate(questionnaire);
		
		// Questions wegschreiben
		AssessmentQuestionService aqService = new AssessmentQuestionService();
		for (AssessmentQuestion q : questions) {
			q.setQuestionnaire(questionnaire);
			aqService.saveOrUpdate(q);
		}
		return "../order/orderList.xhtml";
	}

	public String showQuestionnaire() {
		QuestionnaireService service = new QuestionnaireService();
		questionnaire = service.get(questionnaireId);
		return "showQuestionnaire.xhtml";
	}

	public String questionnaire() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		Integer id = Integer.parseInt(facesContext.getExternalContext().getRequestParameterMap().get("orderId"));

		OrderService orderService = new OrderService();
		Order order = orderService.get(id);
		QuestionnaireService questionnaireService = new QuestionnaireService();
		SelectionHelper<Questionnaire> helper = new SelectionHelper<>();
		helper.addCriterion(Restrictions.eq("order", order));
		List<Questionnaire> found = questionnaireService.getList(helper);
		if (found != null && found.size() >= 1) {
			return "/resources/javaee/questionnaire/showOrderAssessment.jsf";
		} else {
			return "/resources/javaee/questionnaire/assessOrder.jsf";
		}
	}

}
