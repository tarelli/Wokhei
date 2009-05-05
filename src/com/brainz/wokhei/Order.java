package com.brainz.wokhei;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.brainz.wokhei.shared.Status;
import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Order implements IsSerializable{


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private User customer;

	@Persistent
	private String text;

	@Persistent
	private List<String> tags;

	@Persistent
	private Date date;

	@Persistent
	private Status status;

	public Order(User author, String text, List<String> tags, Date date) {
		this.customer = author;
		this.setText(text);
		this.tags = tags;
		this.date = date;
		this.status=Status.INCOMING;
	}

	public Long getId() {
		return id;
	}

	public User getCustomer() {
		return customer;
	}

	public List<String> getTags() {
		return tags;
	}

	public Date getDate() {
		return date;
	}

	public void setCustomer(User author) {
		this.customer = author;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
}

