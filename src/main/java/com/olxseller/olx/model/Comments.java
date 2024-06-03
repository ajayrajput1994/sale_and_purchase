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
	
	//@ManyToOne(cascade=CascadeType.ALL)
	@NotNull
	private String blog;

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

	public String getBlog() {
		return blog;
	}

	public void setBlog(String blog) {
		this.blog = blog;
	}

	public String getPostid() {
		return postid;
	}

	public void setPostid(String postid) {
		this.postid = postid;
	}
	
	public Comments() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comments(int commentId, String name, String email, String subject, String description, String blog,String postid) {
		super();
		this.commentId = commentId;
		this.name = name;
		this.email = email;
		this.subject = subject;
		this.description = description;
		this.blog = blog;
		this.postid=postid;
	}

	@Override
	public String toString() {
		return "Comments [commentId=" + commentId + ", name=" + name + ", email=" + email + ", subject=" + subject
				+ ", description=" + description + ", blog=" + blog + ", postid=" + postid +"]";
	}

	
	
	
	
}
