/**
 * 
 */
package com.brainz.wokhei.shared;

/**
 * @author matteocantarelli
 *
 */
public enum TransactionType {

	MICROPAYMENT(3.50f,"Stir Fried Logo 24h Order submission request"),
	BUYING_LOGO(98.00f,"Stir Fried Logo 24h");

	private Float _value;
	private String _description;

	private TransactionType(Float value, String description)
	{
		_value=value;
		_description=description;
	}

	public String getDescription()
	{
		return _description;	
	}

	public Float getValue()
	{
		return _value;
	}

	public Float getTax(Float gross)
	{
		return getNet(gross)*0.2f;
	}

	public Float getNet(Float gross)
	{
		return _value/1.2f;
	}
}
