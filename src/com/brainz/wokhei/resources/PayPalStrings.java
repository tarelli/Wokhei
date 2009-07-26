package com.brainz.wokhei.resources;

public enum PayPalStrings {
	PAYPAL_SANDBOX_ACTION("https://www.sandbox.paypal.com/cgi-bin/webscr"),
	PAYPAL_ACTION("https://www.paypal.com/cgi-bin/webscr"),
	PAYPAL_BUSINESS_NAME("business"),
	PAYPAL_SANDBOX_BUSINESS_VALUE("G_SELL_1248133279_biz@gmail.com"),
	PAYPAL_CMD_NAME("cmd"),
	PAYPAL_CMD_VALUE("_xclick"),
	PAYPAL_CMD_NOTIFY_VALIDATE("cmd=_notify-validate"),
	PAYPAL_ITEMNAME_NAME("item_name"),
	PAYPAL_ITEMNAME_VALUE("Stir Fried Logo 24h"),
	PAYPAL_AMOUNT_NAME("amount"),
	PAYPAL_AMOUNT_VALUE("49.90"),
	PAYPAL_CURRENCY_NAME("currency_code"),
	PAYPAL_CURRENCY_VALUE("EUR"),
	PAYPAL_NOTIFY_URL_NAME("notify_url"),
	PAYPAL_NOTIFY_URL_VALUE("http://www.wokhei.com/wokhei/paymentDone"),
	PAYPAL_TAX_NAME("tax"),
	PAYPAL_TAX_VALUE("9.98"),
	PAYPAL_CUSTOM_NAME("custom"),
	PAYPAL_RETURN_NAME("return"),
	PAYPAL_RETURN_VALUE("http://www.wokhei.com/home.html"),
	PAYPAL_AMOUNT_TOTAL("59.88"),
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
