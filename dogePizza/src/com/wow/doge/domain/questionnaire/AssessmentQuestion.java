package com.wow.doge.domain.questionnaire;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Teilfrage eines Fragebogens
 */
@Entity
public class AssessmentQuestion implements Comparable<AssessmentQuestion> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	/** die Fragestellung */
	private String questionText;
	/** die Note, die vergeben wurde */
	private int answer;
	@ManyToOne(cascade = CascadeType.ALL)
	private Questionnaire questionnaire;

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

	public int getAnswer() {
		return answer;
	}

	public void setAnswer(int answer) {
		this.answer = answer;
	}

	public Questionnaire getQuestionnaire() {
		return questionnaire;
	}

	public void setQuestionnaire(Questionnaire questionnaire) {
		this.questionnaire = questionnaire;
	}

	@Override
	public String toString() {
		return "AssessmentQuestion [id=" + id + ", questionText=" + questionText + ", answer=" + answer + "]";
	}

	@Override
	public int compareTo(AssessmentQuestion o) {
		return getId().compareTo(o.getId());
	}
}
