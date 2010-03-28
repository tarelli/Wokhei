/**
 * 
 */
package com.brainz.wokhei.shared;

/**
 * @author matteocantarelli
 *
 */
public enum TransactionType {

	MICROPAYMENT(15f,"Stir Fried Logo 24h Order submission request"),
	BUYING_LOGO(160.00f,"Stir Fried Logo 24h"), 
	REVISION(15f,"Stir Fried Logo 24h Revision");

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
		return round(gross-getNet(gross));
	}

	public Float getNet(Float gross)
	{
		return round(gross/1.2f);
	}

	public Float getGrossToPay(Float micropayment)
	{
		return _value-micropayment;	
	}


	private Float round(Float toRound)
	{
		return Float.valueOf((int)((toRound+0.005f)*10.0f)/10.0f);
	}
}
