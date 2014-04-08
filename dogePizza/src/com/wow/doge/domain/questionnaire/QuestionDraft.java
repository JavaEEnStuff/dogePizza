package com.wow.doge.domain.questionnaire;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class QuestionDraft implements Comparable<QuestionDraft> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String questionText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public String getQuestionTextShortened() {
		return questionText.substring(0, Math.min(15, questionText.length())) + "...";
	}

	@Override
	public String toString() {
		return "QuestionDraft [id=" + id + ", questionText=" + questionText + "]";
	}

	/**
	 * natural order: id
	 */
	@Override
	public int compareTo(QuestionDraft o) {
		return getId().compareTo(o.getId());
	}

}
