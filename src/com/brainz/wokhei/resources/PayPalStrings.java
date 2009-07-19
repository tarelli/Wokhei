package com.brainz.wokhei.resources;

public enum PayPalStrings {
	PAYPAL_SANDBOX_ACTION("https://www.sandbox.paypal.com/cgi-bin/webscr"),
	PAYPAL_ACTION("https://www.paypal.com/cgi-bin/webscr"),
	PAYPAL_BUSINESS_NAME("business"),
	PAYPAL_SANDBOX_BUSINESS_VALUE("seller_1247276806_biz@gmail.com"),
	PAYPAL_CMD_NAME("cmd"),
	PAYPAL_CMD_VALUE("_xclick"),
	PAYPAL_ITEMNAME_NAME("item_name"),
	PAYPAL_ITEMNAME_VALUE("Stir Fried Logo 24h"),
	PAYPAL_AMOUNT_NAME("amount"),
	PAYPAL_AMOUNT_VALUE("49.9"),
	PAYPAL_CURRENCY_NAME("currency_code"),
	PAYPAL_CURRENCY_VALUE("EUR"),
	;

	private String _text;

	PayPalStrings(String text)
	{
		_text=text;
	}

	public String getString()
	{
		return _text;
	}
}
