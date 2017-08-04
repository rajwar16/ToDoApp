package com.bridgeit.TodoApp.JSONResponse;

import java.util.List;

import org.springframework.validation.FieldError;

public class RegisterErrorResponse extends Response
{
	private String firstNameError;
	private String lastNameError;
	private String userNameError;
	private String passwordError;
	private String birthDateError;
	private String genderError;
	private long mobilePhoneError;
	private String emailIdError;
	private List<FieldError> errorList;
	
	public String getFirstNameError() {
		return firstNameError;
	}
	public void setFirstNameError(String firstNameError) {
		this.firstNameError = firstNameError;
	}
	public String getLastNameError() {
		return lastNameError;
	}
	public void setLastNameError(String lastNameError) {
		this.lastNameError = lastNameError;
	}
	public String getUserNameError() {
		return userNameError;
	}
	public void setUserNameError(String userNameError) {
		this.userNameError = userNameError;
	}
	public String getPasswordError() {
		return passwordError;
	}
	public void setPasswordError(String passwordError) {
		this.passwordError = passwordError;
	}
	public String getBirthDateError() {
		return birthDateError;
	}
	public void setBirthDateError(String birthDateError) {
		this.birthDateError = birthDateError;
	}
	public String getGenderError() {
		return genderError;
	}
	public void setGenderError(String genderError) {
		this.genderError = genderError;
	}
	public long getMobilePhoneError() {
		return mobilePhoneError;
	}
	public void setMobilePhoneError(long mobilePhoneError) {
		this.mobilePhoneError = mobilePhoneError;
	}
	public String getEmailIdError() {
		return emailIdError;
	}
	public void setEmailIdError(String emailIdError) {
		this.emailIdError = emailIdError;
	}
	public List<FieldError> getErrorList() {
		return errorList;
	}
	public void setErrorList(List<FieldError> errorList) {
		this.errorList = errorList;
	}
	
	
}
