package com.olxseller.olx.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int commentId;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@NotNull
	private String subject;
	@NotNull
	private String description;
	@NotNull
	private String postid;
	@NotNull
	private String create_at;
	@NotNull
	private String update_at;


	
	

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}
	
	
	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public String getUpdate_at() {
		return update_at;
	}

	public void setUpdate_at(String update_at) {
		this.update_at = update_at;
	}

	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comments(int commentId, String name, String email, String subject, String description,String postid) {
		super();
		this.commentId = commentId;
		this.name = name;
		this.email = email;
		this.subject = subject;
		this.description = description;
		this.postid=postid;
		this.create_at=create_at;
		this.update_at=update_at;
	}

	@Override
	public String toString() {
		return "Comments [commentId=" + commentId + ", name=" + name + ", email=" + email + ", subject=" + subject
				+ ", description=" + description + ", postid=" + postid + ", create_at=" + create_at + ", update_at="
				+ update_at + "]";
	}



	
	
	
	
}
