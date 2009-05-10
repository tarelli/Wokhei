package com.brainz.wokhei.shared;

import java.io.Serializable;
import java.util.Date;

public class OrderDTO implements Serializable{


	private Long _id;

	private String _text;

	private String[] _tags;

	private Date _date;

	private Status _status;

	private String _customerEmail;

	public Long getId() {
		return _id;
	}

	public void setId(Long id) {
		_id = id;
	}

	public OrderDTO() {
		super();	
	}

	public OrderDTO(Long id, String text, String[] tags, Date date,
			Status status, String customerEmail) {
		super();
		_id = id;
		_text = text;
		_tags = tags;
		_date = date;
		_status = status;
		_customerEmail = customerEmail;
	}

	public String getText() {
		return _text;
	}

	public void setText(String text) {
		_text = text;
	}

	public String[] getTags() {
		return _tags;
	}

	public void setTags(String[] tags) {
		_tags = tags;
	}

	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public Status getStatus() {
		return _status;
	}

	public void setStatus(Status status) {
		_status = status;
	}

}
