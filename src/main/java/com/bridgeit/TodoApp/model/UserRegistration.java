/*package com.bridgeit.TodoApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.bind.annotation.SessionAttributes;

@SuppressWarnings("serial")
@Entity
@Table(name="User_Registration")
public class UserRegistration implements Serializable
{
	@Id
	@GenericGenerator(name="id",strategy="increment")
	@GeneratedValue(generator="id")
	
	private long id;
	private String firstName;
	private String lastName;
	private String userName;
	private String password;
	private String birthDate;
	private String gender;
	private String mobilePhone;
	private String emailId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	@Override
	public String toString() {
		return "UserRegistration [id=" + id + ", FirstName=" + firstName + ", lastName=" + lastName + ", userName="
				+ userName + ", password=" + password + ", birthDate=" + birthDate + ", gender=" + gender
				+ ", mobilePhone=" + mobilePhone + ", emailId=" + emailId + "]";
	}
}
*/