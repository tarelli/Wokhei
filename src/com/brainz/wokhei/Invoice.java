package com.brainz.wokhei;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.users.User;
import com.google.gwt.user.client.rpc.IsSerializable;

@PersistenceCapable(identityType = IdentityType.APPLICATION)

public class Invoice implements IsSerializable
{

	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long id;

	@Persistent
	private User customer;

	@Persistent
	private Date date;

	@Persistent
	private Integer invoiceNumber;

	@Persistent
	private Long orderid;

	/**
	 * @param customer
	 * @param date
	 * @param invoiceNumber
	 */
	public Invoice(User customer, Date date, Integer invoiceNumber, Long orderId) 
	{
		this.customer = customer;
		this.date = date;
		this.orderid=orderId;
		this.invoiceNumber=invoiceNumber;
	}

	public Long getId() {
		return id;
	}

	public User getCustomer() {
		return customer;
	}

	public Integer getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(Integer invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setOrderid(Long orderId) {
		this.orderid = orderId;
	}

	public Long getOrderid() {
		return orderid;
	}

	public void setCustomer(User author) {
		this.customer = author;
	}

	public void setDate(Date date) {
		this.date = date;
	}


}


