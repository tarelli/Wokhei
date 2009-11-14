package com.brainz.wokhei;

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.brainz.wokhei.shared.Colour;
import com.brainz.wokhei.shared.Status;
import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Order implements IsSerializable{


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id; 

	@Persistent
	private Integer progressive;

	@Persistent
	private User customer;

	@Persistent
	private String text;

	@Persistent
	private List<String> descriptions;

	@Persistent
	private Date date;

	@Persistent
	private Date acceptedDate; 

	@Persistent
	private Date viewedDate;

	@Persistent
	private Status status;

	@Persistent
	private Colour colour;


	public Order(User author, String text, List<String> tags, Colour colour, Date date, Integer progressive) {
		this.customer = author;
		this.text=text;
		this.descriptions = tags;
		this.date = date;
		this.colour=colour;
		this.progressive=progressive;

		//default values when instantiating
		this.acceptedDate= null;
		this.viewedDate = null;
		this.status=Status.PENDING;
	}

	public Colour getColour() {
		return colour;
	}

	public void setColour(Colour colour) {
		this.colour = colour;
	}

	public Long getId() {
		return id;
	}

	public User getCustomer() {
		return customer;
	}

	public List<String> getDescriptions() {
		return descriptions;
	}

	public Date getDate() {
		return date;
	}

	public Date getAcceptedDate() {
		return acceptedDate;
	}

	public Date getViewedDate() {
		return viewedDate;
	}

	public void setCustomer(User author) {
		this.customer = author;
	}

	public void setDescriptions(List<String> tags) {
		this.descriptions = tags;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setAcceptedDate(Date date) {
		this.acceptedDate = date;
	}


	public void setViewedDate(Date date) {
		this.viewedDate = date;
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

	public Integer getProgressive() {
		return progressive;
	}

	public void setProgressive(Integer progressive) {
		this.progressive = progressive;
	}
}

