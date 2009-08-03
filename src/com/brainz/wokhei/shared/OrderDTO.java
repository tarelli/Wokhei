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

	private String[] _tags;

	private Date _date;

	private Date _acceptedDate;

	private Date _viewedDate;

	private Status _status;

	private String _customerEmail;

	private Colour _colour;

	private Integer _progressive;

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
	 * @param tags
	 * @param date
	 * @param status
	 * @param colour
	 * @param customerEmail
	 */
	public OrderDTO(Long id, String text, String[] tags, Date date, Date acceptedDate, Date viewedDate,
			Status status, Colour colour, String customerEmail, Integer progressive) 
	{
		super();
		_id = id;
		_text = text;
		_tags = tags;
		_date = date;
		_acceptedDate = acceptedDate;
		_viewedDate = viewedDate;
		_status = status;
		_colour = colour;
		_customerEmail = customerEmail;
		_progressive=progressive;
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
	public String[] getTags() 
	{
		return _tags;
	}

	/**
	 * @param tags
	 */
	public void setTags(String[] tags) 
	{
		_tags = tags;
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

}
