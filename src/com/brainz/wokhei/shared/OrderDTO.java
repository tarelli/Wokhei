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

	private Date _reviewingDate;

	private Status _status;

	private String _customerEmail;

	private Colour _colour;

	private Integer _progressive;

	private Float _tip;

	private Integer _revisionCounter;

	private Float[] _revisionTip;

	private boolean _isRevisionOngoing=false;


	/**
	 * 
	 */
	public OrderDTO() 
	{
		super();
		_revisionCounter=0;
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
	public OrderDTO(Long id, String text, String[] descriptions, Date date, Date acceptedDate, Date reviewingDate,
			Status status, Colour colour, String customerEmail, Integer progressive, Integer revisionCounter, Float tip, Float[] revisionTip) 
	{
		super();
		_id = id;
		_text = text;
		_descriptions = descriptions;
		_date = date;
		_acceptedDate = acceptedDate;
		_reviewingDate = reviewingDate;
		_status = status;
		_colour = colour;
		_customerEmail = customerEmail;
		_revisionCounter=revisionCounter;
		_progressive=progressive;
		_revisionTip=revisionTip;
		_tip=tip;
	}


	public OrderDTO(OrderDTO currentOrder) {
		super();
		_id = currentOrder.getId();
		_text = currentOrder.getText();
		_descriptions = currentOrder.getDescriptions();
		_date = currentOrder.getDate();
		_acceptedDate = currentOrder.getAcceptedDate();
		_reviewingDate = currentOrder.getReviewingDate();
		_status = currentOrder.getStatus();
		_colour = currentOrder.getColour();
		_customerEmail = currentOrder.getCustomerEmail();
		_revisionCounter=currentOrder.getRevisionCounter();
		_progressive=currentOrder.getProgressive();
		_tip=currentOrder.getTip();
		_revisionTip=currentOrder.getRevisionTip();
	}

	/**
	 * @return
	 */
	public Float getTip() 
	{
		return _tip;
	}

	/**
	 * @param tip
	 */
	public void setTip(Float tip) 
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
	public Date getReviewingDate() 
	{
		return _reviewingDate;
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
	public void setReviewingDate(Date date) 
	{
		_reviewingDate = date;
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

	public Integer getRevisionCounter() {
		return _revisionCounter;
	}

	public void setRevisionCounter(Integer revisionCounter) {
		this._revisionCounter = revisionCounter;
	}

	/**
	 * @return
	 */
	public boolean hasCompletedReview() 
	{
		return _status.equals(Status.VIEWED) && _revisionCounter!=0 && _revisionCounter==_descriptions.length-1;
	}

	/**
	 * @return
	 */
	public boolean isRevisionOngoing() 
	{
		return _isRevisionOngoing;
	}

	public Float[] getRevisionTip() {
		return _revisionTip;
	}

	/**
	 * @return
	 */
	public Float getTotalTips()
	{
		float totalTips=_tip;
		if(_revisionTip!=null)
		{
			for(int i=0;i<_revisionTip.length;i++)
			{
				totalTips+=_revisionTip[i];
			}
		}
		return totalTips;
	}

	/**
	 * @return
	 */
	public Float getTotalPaidTips()
	{
		if(_revisionTip!=null && _revisionTip.length>0)
		{
			//il +1 ŽÊli perche revision counter comprende anche la prima revision che ŽÊgratuita
			//mentre per quella non c'Ž un tip
			if(_revisionCounter<(_revisionTip.length+1))
			{
				//l'ultimo tip ŽÊsolo storato ma non pagato, sbogalo male che non voglio il bug/trick di pagare di meno il logo
				//se uno ha iniziato a fare un'altra revisione, ha settato il tip ma poi non l'ha fatta e poi va a tornare il logo
				//GENTE CHE NON SI FA FOTTERE NULLA
				return getTotalTips()-_revisionTip[_revisionTip.length-1];
			}
		}
		return getTotalTips();
	}

	/**
	 * @param descriptions
	 */
	public void setRevisionTip(Float[] revisionTip) 
	{ 
		_revisionTip=revisionTip;		
	} 

	public void setRevisionOngoing(boolean revisionOngoing)
	{
		_isRevisionOngoing=revisionOngoing;
	}


}
