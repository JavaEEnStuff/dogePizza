package com.wow.doge.managedbeans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

import com.wow.doge.services.UserService;

@ManagedBean
public class ChangePasswordBean {

	@ManagedProperty(value = "#{sessionBean}")
	private SessionBean sessionBean;

	public void setSessionBean(SessionBean sessionBean) {
		this.sessionBean = sessionBean;
	}

	private String oldPassword;
	private String newPassword;
	private String newPasswordConfirmation;
	private String errorText = "";

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getNewPasswordConfirmation() {
		return newPasswordConfirmation;
	}

	public void setNewPasswordConfirmation(String newPasswordConfirmation) {
		this.newPasswordConfirmation = newPasswordConfirmation;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String changePassword() {
		if (oldPassword.equals(sessionBean.getLoggedInUser().getPassword())) {
			if (newPassword.equals(newPasswordConfirmation)) {
				sessionBean.getLoggedInUser().setPassword(newPassword);
				UserService service = new UserService();
				service.saveOrUpdate(sessionBean.getLoggedInUser());
				return sessionBean.main();
			} else {
				errorText = "Die beiden Eingaben zum neuen Passwort sind nicht gleich!";
			}
		} else {
			errorText = "Das bisherige Passwort wurde nicht korrekt bestätigt!";
		}
		return "";
	}

}
