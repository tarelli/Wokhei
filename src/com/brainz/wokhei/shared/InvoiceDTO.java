package com.brainz.wokhei.shared;

import java.io.Serializable;
import java.util.Date;

public class InvoiceDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5961185183534572357L;

	private Long _id;

	private Date _date;

	private Integer _invoiceNumber;

	private String _email;

	private String _nick;


	/**
	 * 
	 */
	public InvoiceDTO() 
	{
		super();	
	}


	/**
	 * @param id
	 * @param date
	 * @param invoiceNumber
	 * @param email
	 * @param nick
	 */
	public InvoiceDTO(Long id, Date date, Integer invoiceNumber, String email,	String nick) 
	{
		super();
		this._id = id;
		this._date = date;
		this._invoiceNumber = invoiceNumber;
		this._email = email;
		this._nick = nick;
	}


	public Long get_id() {
		return _id;
	}


	public void set_id(Long _id) {
		this._id = _id;
	}


	public Date get_date() {
		return _date;
	}


	public void set_date(Date _date) {
		this._date = _date;
	}


	public Integer getInvoiceNumber() {
		return _invoiceNumber;
	}


	public void set_invoiceNumber(Integer number) {
		_invoiceNumber = number;
	}


	public String getEmail() {
		return _email;
	}


	public void set_email(String _email) {
		this._email = _email;
	}


	public String getNick() {
		return _nick;
	}


	public void set_nick(String _nick) {
		this._nick = _nick;
	}


}
