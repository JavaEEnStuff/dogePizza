package com.wow.doge.domain.questionnaire;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.wow.doge.domain.Order;

@Entity
public class Questionnaire {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private long date;
	@OneToMany(mappedBy = "questionnaire", fetch = FetchType.EAGER)
	private Set<AssessmentQuestion> questions;
	private Integer overallGrade;
	private String remark;

	@OneToOne(fetch = FetchType.EAGER)
	private Order order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

	public Set<AssessmentQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(Set<AssessmentQuestion> questions) {
		this.questions = questions;
	}

	public Integer getOverallGrade() {
		return overallGrade;
	}

	public void setOverallGrade(Integer overallGrade) {
		this.overallGrade = overallGrade;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Questionnaire [id=").append(id).append(", date=").append(date).append(", ");
		if (questions != null)
			builder.append("questions=").append(questions.size()).append(", ");
		if (overallGrade != null)
			builder.append("overallGrade=").append(overallGrade).append(", ");
		if (remark != null)
			builder.append("remark=").append(remark).append(", ");
		if (order != null)
			builder.append("order=").append(order.getId());
		builder.append("]");
		return builder.toString();
	}

}
