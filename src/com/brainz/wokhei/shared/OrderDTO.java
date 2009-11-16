package com.brainz.wokhei.shared;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3810546406756646291L;

	private Long _id;

	private String _text;

	private String[] _descriptions;

	private Date _date;

	private Date _acceptedDate;

	private Date _viewedDate;

	private Status _status;

	private String _customerEmail;

	private Colour _colour;

	private Integer _progressive;

	private Double _tip;

	private Integer _revisionCounter;



	/**
	 * 
	 */
	public OrderDTO() 
	{
		super();	
	}

	/**
	 * @param id
	 * @param text
	 * @param descriptions
	 * @param date
	 * @param status
	 * @param colour
	 * @param customerEmail
	 */
	public OrderDTO(Long id, String text, String[] descriptions, Date date, Date acceptedDate, Date viewedDate,
			Status status, Colour colour, String customerEmail, Integer progressive, Integer revisionCounter) 
	{
		super();
		_id = id;
		_text = text;
		_descriptions = descriptions;
		_date = date;
		_acceptedDate = acceptedDate;
		_viewedDate = viewedDate;
		_status = status;
		_colour = colour;
		_customerEmail = customerEmail;
		_revisionCounter=revisionCounter;
		_progressive=progressive;
	}


	/**
	 * @return
	 */
	public Double getTip() 
	{
		return _tip;
	}

	/**
	 * @param tip
	 */
	public void setTip(Double tip) 
	{
		_tip = tip;
	}

	/**
	 * @return
	 */
	public String getCustomerEmail()
	{
		return _customerEmail;
	}

	/**
	 * @param email
	 */
	public void setCustomerEmail(String email) 
	{
		_customerEmail = email;
	}

	/**
	 * @return
	 */
	public Colour getColour() 
	{
		return _colour;
	}

	/**
	 * @param colour
	 */
	public void setColour(Colour colour) 
	{
		this._colour = colour;
	}

	/**
	 * @return
	 */
	public Long getId() 
	{
		return _id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) 
	{
		_id = id;
	}


	/**
	 * @return
	 */
	public String getText() 
	{
		return _text;
	}

	/**
	 * @param text
	 */
	public void setText(String text) 
	{
		_text = text;
	}

	/**
	 * @return
	 */
	public String[] getDescriptions() 
	{
		return _descriptions;
	}

	/**
	 * @param descriptions
	 */
	public void setDescriptions(String[] descriptions) 
	{
		_descriptions = descriptions;
	}

	/**
	 * @return
	 */
	public Date getDate() 
	{
		return _date;
	}

	/**
	 * @return
	 */
	public Date getAcceptedDate() 
	{
		return _acceptedDate;
	}

	/**
	 * @return
	 */
	public Date getViewedDate() 
	{
		return _viewedDate;
	}

	/**
	 * @param date
	 */
	public void setDate(Date date) 
	{
		_date = date;
	}

	/**
	 * @param date
	 */
	public void setAcceptedDate(Date date) 
	{
		_acceptedDate = date;
	}

	/**
	 * @param date
	 */
	public void setViewedDate(Date date) 
	{
		_viewedDate = date;
	}

	/**
	 * @return
	 */
	public Status getStatus() 
	{
		return _status;
	}

	/**
	 * @param status
	 */
	public void setStatus(Status status) 
	{
		_status = status;
	}

	public Integer getProgressive() {
		return _progressive;
	}

	public void setProgressive(Integer _progressive) {
		this._progressive = _progressive;
	}

	public int getRevisionCounter() {
		return _revisionCounter;
	}

	public void setRevisionCounter(Integer revisionCounter) {
		this._revisionCounter = revisionCounter;
	}

}
