package com.bridgeit.TodoApp.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("serial")
@Entity
@Table(name="CollaboratorNote")
public class Collaborator implements Serializable{
	@Id
	@GenericGenerator(name="collaboratorId",strategy="increment")
	@GeneratedValue(generator="collaboratorId")
	
	private long collaboratorId;
	private long ownerId;
	private long sharedId;
	private long sharedNoteId;
	
	public long getCollaboratorId() {
		return collaboratorId;
	}
	
	public void setCollaboratorId(long collaboratorId) {
		this.collaboratorId = collaboratorId;
	}
	
	public long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(long ownerId) {
		this.ownerId = ownerId;
	}
	public long getSharedId() {
		return sharedId;
	}
	public void setSharedId(long sharedId) {
		this.sharedId = sharedId;
	}
	public long getSharedNoteId() {
		return sharedNoteId;
	}
	public void setSharedNoteId(long sharedNoteId) {
		this.sharedNoteId = sharedNoteId;
	}
	
	public String toString() {
		return "Collaborator [collaboratorId=" + collaboratorId + ", ownerId=" + ownerId + ", sharedId=" + sharedId
				+ ", sharedNoteId=" + sharedNoteId + "]";
	}
	
}
