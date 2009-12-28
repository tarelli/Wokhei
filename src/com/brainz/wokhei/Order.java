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
	private Date reviewingDate;

	@Persistent
	private Status status;

	@Persistent
	private Colour colour;

	@Persistent
	private Integer revisionCounter;

	@Persistent
	private Float tip;


	public Order(User author, String text, List<String> tags, Colour colour, Date date, Integer progressive) {
		this.customer = author;
		this.text=text;
		this.descriptions = tags;
		this.date = date;
		this.colour=colour;
		this.progressive=progressive;

		//default values when instantiating
		this.acceptedDate= null;
		this.reviewingDate=null;
		this.status=Status.PENDING;
		this.revisionCounter=new Integer(0);
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

	public Date getReviewingDate() {
		return reviewingDate;
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

	public void setReviewingDate(Date date) {
		this.reviewingDate = date;
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

	public Integer getRevisionCounter() {
		return revisionCounter;
	}

	public void setRevisionCounter(Integer revisionCounter) {
		this.revisionCounter = revisionCounter;
	}

	public Float getTip() {
		return tip;
	}

	public void setTip(Float tip) {
		this.tip = tip;
	}
}

