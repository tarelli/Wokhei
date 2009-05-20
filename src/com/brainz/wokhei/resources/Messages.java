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
	REQUEST_LOGO_LBL("Request your logo"),
	SEND_REQUEST("Send request!"),

	ACCEPTED_WAITMSG("Your logo is getting ready, do you fancy a drink meanwhile?"),
	ERROR_WAITMSG("An error occurred while sending your request. Please do complain!"),
	INCOMING_WAITMSG("Your request has been sent to the kitchen, enjoy the apetizers!"),


	INCOMING_TITLE("Pending request..."),
	INCOMING_TEXT("Your request has been registered. In a short time our coordinator (Tony) will approve it or reject it. Just give him a moment, it might be on his coffee break!"),
	ACCEPTED_TITLE("Request accepted!"),
	ACCEPTED_TEXT("You request has been accepted (thanks Tony), this means that in 24hrs you will receive the logo you requested. We are assigning the job to one of our pure-genius designers."),
	REJECTED_TITLE("Request rejected"),
	REJECTED_TEXT("Unfortunately we couldn't accept this request (blame Tony). This might happen in case the request violated some copyright, or if he considered it inappropriate, offensive or just a joke (sometimes he just doesn't get them). Unfortunately we have so many serious requests that we don't have time to deal with the funny ones. That's the business!"),
	IN_PROGRESS_TITLE("Designing your logo..."),
	IN_PROGRESS_TEXT("has been chosen to design your logo. He's very good don't worry, you are in very good hands (yes we do say the same for all of them)"),
	QUALITY_GATE_TITLE("Checking quality..."),
	QUALITY_GATE_TEXT("Does the logo have the Wokhei quality? Does it have what makes each one of our logos a success? That's what we are checking, we deliver only the best designs, that's what you deserve!"),
	READY_TITLE("It's ready"),
	READY_TEXT("Your logo is ready there for you. Check it out clicking on the icon above! This logo has been created from our experience and competence. Does it have the Wokhei taste? Oh yeah..."),
	VIEWED_TITLE("Don't make it go cold!"),
	VIEWED_TEXT("We hope you liked our job, we do like it very much! You have 24hrs to buy the logo and make it yours having so the rights to use it for any purpose you have in mind (yes also commercial ones!). You'll have also access to the high resolution version and to the vectorial format. Thanks for your time!"),
	BOUGHT_TITLE("You purchased this logo"),
	BOUGHT_TEXT("This logo is yours, just make anything you want with it!"),
	ARCHIVED_TITLE("This logo is archived"),
	ARCHIVED_TEXT("You didn't purchase this logo. We know that you still do recnognize and appreciate the effort and expertise we put into realizing this logo for you, so you can still use it under licence for non commercial purposes. We will appreciate if you'll advertise Wokhei wherever you'll decide to use this logo so don't be shy!"),

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
