package com.bridgeit.TodoApp.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name="PageScrapedata")
public class PageScrapedata 
{
	@Id
	@GenericGenerator(name="id",strategy="increment")
	@GeneratedValue(generator="id")
	private long id;
	
	private String hostName;
	private String urlTitle;
	private String urlImage;
	private String redirectUrl;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="Noteid")
	private ToDoNotes noteid;


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public String getHostName() {
		return hostName;
	}


	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getUrlTitle() {
		return urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		this.urlTitle = urlTitle;
	}


	public String getUrlImage() {
		return urlImage;
	}


	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public ToDoNotes getNoteid() {
		return noteid;
	}

	public void setNoteid(ToDoNotes noteid) {
		this.noteid = noteid;
	}

	@Override
	public String toString() {
		return "PageScrapedata [id=" + id + ", hostName=" + hostName + ", urlTitle=" + urlTitle + ", urlImage="
				+ urlImage + ", redirectUrl=" + redirectUrl + ", noteid=" + noteid + "]";
	}
}
