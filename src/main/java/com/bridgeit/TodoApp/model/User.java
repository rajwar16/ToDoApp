package com.bridgeit.TodoApp.model;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;



@SuppressWarnings("serial")
@Entity
@Table(name="User")
public class User implements Serializable
{
	@Id
	@GenericGenerator(name="id",strategy="increment")
	@GeneratedValue(generator="id")
	
	private long id;
	private String userName;
	private String password;
	private String mobileNo;
	private String email;
	private String facebookProfile; 
	
	@Lob
    @Column(name="Profile_IMAGE", columnDefinition="mediumblob")
    private String profileImage;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFacebookProfile() {
		return facebookProfile;
	}

	public void setFacebookProfile(String facebookProfile) {
		this.facebookProfile = facebookProfile;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", mobileNo=" + mobileNo
				+ ", email=" + email + ", facebookProfile=" + facebookProfile + ", profileImage=" + profileImage + "]";
	}
}	
