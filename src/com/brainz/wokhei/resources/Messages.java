package com.brainz.wokhei.resources;

public enum Messages {

	LOGO_NAME_LBL("Logo name"),
	LOGO_NAME_EG_LBL("e.g. Franco Restaurant"),
	LOGO_NAME_TXTBOX("the text in your logo"),
	LOGO_COLOUR_LBL("Main color"),
	LOGO_TAGS_TXTBOX("describe what the logo is for!"),
	LOGO_COLOUR_EG_LBL("Logo's primary colour"),
	LOGO_TAGS_LBL("5 Tags"),
	LOGO_TAGS_EG_LBL("e.g. #FoodIndustry #Restaurant #Fancy #FrenchCuisine"),
	LOGO_TAGS_ERROR_TOOMANY("Sorry, our Chefs get confused with more than 5 tags!"),
	LOGO_COLOUR_ERROR_NONE("Pick up the main colour!"),
	GENERIC_ERROR("Error"), 
	LOGO_TAGS_ERROR_NOTENOUGH("You need to put at least some tags!"),
	ACCEPTED_WAITMSG("Your logo is getting ready, do you fancy a drink meanwhile?"),
	SEND_REQUEST("Send request!"),
	ERROR_WAITMSG("An error occurred while sending your request. Please do complain!"),
	INCOMING_WAITMSG("Your request has been sent to the kitchen, enjoy the apetizers!"),
	REQUEST_LOGO_LBL("Request your logo")
	;

	private String _text;

	Messages(String text)
	{
		_text=text;
	}

	public String getString()
	{
		return _text;
	}
}
