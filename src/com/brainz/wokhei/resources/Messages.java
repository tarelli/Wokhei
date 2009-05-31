package com.brainz.wokhei.resources;

public enum Messages {

	LOGO_NAME_LBL("Logo name"),
	LOGO_NAME_EG_LBL("e.g. Franco Bovale Restaurant"),
	LOGO_NAME_TXTBOX("the text in your logo"),
	LOGO_NAME_ERROR_NONE("You need to write the text for you logo!"),
	LOGO_NAME_ERROR_TOOLONG("Sorry the text for your logo is too long!"),
	LOGO_COLOUR_LBL("Main color"),
	LOGO_TAGS_TXTBOX("describe what the logo is for!"),
	LOGO_COLOUR_EG_LBL("Logo's primary colour"),
	LOGO_TAGS_LBL("Up to 5 Tags"),
	LOGO_TAGS_EG_LBL("e.g. #FoodIndustry #Restaurant #Fancy #FrenchCuisine"),
	LOGO_TAGS_ERROR_TOOMANY("Sorry, our Chefs get confused with more than five tags!"),
	LOGO_TAGS_ERROR_TOOLONG("Sorry, tags can't be too long!"),
	LOGO_COLOUR_ERROR_NONE("Pick up the main colour!"),
	GENERIC_ERROR("Error"), 
	LOGO_TAGS_ERROR_NOTENOUGH("You need to put at least three tags!"),
	REQUEST_LOGO_LBL("Request your logo"),
	SEND_REQUEST("Send request!"),

	ACCEPTED_WAITMSG("Your logo is getting ready, do you fancy a drink meanwhile?"),
	ERROR_WAITMSG("An error occurred while sending your request. Please do complain!"),
	INCOMING_WAITMSG("Your request has been sent to the kitchen, enjoy the apetizers!"),


	INCOMING_TITLE("Pending request..."),
	INCOMING_TEXT("Your request has been registered. In a short time our chef Tony will approve it or reject it. Just give him a moment, he might be on his coffee break!"),
	ACCEPTED_TITLE("Request accepted!"),
	ACCEPTED_TEXT("You request has been accepted by our chef Tony - in 24hrs you will receive the logo you requested. We are tasking one of our pure-genius help-chefs with the job!"),
	REJECTED_TITLE("Request rejected"),
	REJECTED_TEXT("Unfortunately Tony didn't accept this request. This can happen if the request violated some copyright, or Tony didn't like it."),
	IN_PROGRESS_TITLE("Designing your logo..."),
	IN_PROGRESS_TEXT("has been chosen to cook you up a tasty logo. You are in very good hands (yes we do say the same for all of them)."),
	QUALITY_GATE_TITLE("Checking quality..."),
	QUALITY_GATE_TEXT("Tony himself manages the quality gate to making sure your logo is as good as you deserve!"),
	READY_TITLE("It's ready"),
	READY_TEXT("Your logo is finally ready - check it out clicking on the icon above and flood your senses with Wokhei goodness!"),
	VIEWED_TITLE("Don't make it go cold!"),
	VIEWED_TEXT("We hope you like your logo (we like it very much, if that can help!). You have 24hrs to buy it: in case you decide to pull out the money you will be able to use the logo for commercial purposes (and you'll get access to the fancy high resolution and vectorial versions)."),
	BOUGHT_TITLE("You purchased this logo"),
	BOUGHT_TEXT("This logo is yours, just make anything you want with it!"),
	ARCHIVED_TITLE("This logo is archived"),
	ARCHIVED_TEXT("You didn't purchase this logo. You can still use it under licence for non-commercial purposes. We will appreciate if you'll notify Wokhei whenever you'll decide to use this logo - so don't be shy!"), 


	ADMIN_STATUS_FILTER_LABEL("Pick Status"),
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
