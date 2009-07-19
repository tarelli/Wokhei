package com.brainz.wokhei.resources;

public enum Mails {

	MATTEO("matteo.cantarelli@wokhei.com"),
	GIOVANNI("giovanni.idili@wokhei.com"),
	SIMONE("simone.cantarelli@wokhei.com"),

	ADMIN("admin@wokhei.com"),
	YOURLOGO("yourlogo@wokhei.com"),
	INVOICES("invoices@wokhei.com"),
	PRESS("press@wokhei.com"),
	SUPPORT("support@wokhei.com"),
	PARTNER("partner@wokhei.com"),
	RESUMES("resumes@wokhei.com"),
	;

	private String _text;

	Mails(String text)
	{
		_text=text;
	}

	public String getMailAddress()
	{
		return _text;
	}
}
