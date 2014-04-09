package com.wow.doge.domain.html;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Menüklasse, repräsentiert ein HTML-Menü
 */
@Entity
public class Menu {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String text;
	@Column(nullable = false)
	private String link;
	@Column(nullable = false)
	private boolean loginRequired;
	@Column(nullable = false)
	private boolean adminMenu;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isLoginRequired() {
		return loginRequired;
	}

	public void setLoginRequired(boolean loginRequired) {
		this.loginRequired = loginRequired;
	}

	public boolean isAdminMenu() {
		return adminMenu;
	}

	public void setAdminMenu(boolean adminMenu) {
		this.adminMenu = adminMenu;
	}

	@Override
	public String toString() {
		return "Menu [name=" + name + ", text=" + text + ", link=" + link + ", loginRequired=" + loginRequired + ", adminMenu=" + adminMenu + "]";
	}

}
