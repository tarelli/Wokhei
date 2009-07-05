package com.brainz.wokhei.resources;

public enum Messages {

	INCOMING("Incoming"),
	ACCEPTED("Accepted"),
	REJECTED("Rejected"),
	IN_PROGRESS("In progress"),
	QUALITY_GATE("Quality Gate"),
	READY("Ready"),
	BOUGHT("Bought"),
	VIEWED("Viewed"),
	ARCHIVED("Archived"),

	LOGO_NAME_LBL("Product name"),
	LOGO_NAME_HELP_MESSAGE("This is the name of the company, product, service, website, association, etc. that your logo is for. This name will appear in the logo unless you'll specify the #notext tag, in that case the logo will only be a symbolic representation!"),
	LOGO_NAME_EG_LBL("e.g. Franco Bovale Restaurant"),
	LOGO_NAME_TXTBOX("the name of your product"),
	LOGO_NAME_ERROR_NONE("You need to specify a product name!"),
	LOGO_NAME_ERROR_TOOLONG("Sorry the product name is too long!"),
	LOGO_COLOUR_LBL("Main colour"),
	LOGO_COLOUR_HELP_MESSAGE("This will be the primary colour of your logo. Note that the logo could have multiple colours even respecting the main tone!"),
	LOGO_TAGS_TXTBOX("describe what the logo is for with tags!"),
	LOGO_COLOUR_EG_LBL("Logo's primary colour"),
	LOGO_TAGS_LBL("Up to 5 Tags"),
	LOGO_TAGS_HELP_MESSAGE("Tags describe what the logo is for.\nPlease do not put sentences here - just tags!"),
	LOGO_TAGS_EG_LBL("e.g. #FoodIndustry #Restaurant #Fancy #FrenchCuisine"),
	LOGO_TAGS_ERROR_TOOMANY("Sorry, our designers get confused with more than five tags!"),
	LOGO_TAGS_ERROR_TOOLONG("Sorry, tags can't be too long!"),
	LOGO_COLOUR_ERROR_NONE("Pick up the main colour!"),
	GENERIC_ERROR("Error"), 
	LOGO_TAGS_ERROR_NOTENOUGH("You need to insert at least three tags!"),
	REQUEST_LOGO_LBL("Request your logo"),
	SEND_REQUEST("Send request!"),

	ACCEPTED_WAITMSG("Your logo is getting ready, fancy a drink meanwhile?"),
	ERROR_WAITMSG("An error occurred while sending your request. Please do complain!"),
	INCOMING_WAITMSG("Your request has been sent to the kitchen, enjoy the appetizers!"),
	IN_PROGRESS_WAITMSG("Your logo is being cooked-up, have some more appetizers!"),
	QUALITY_GATE_WAITMSG("Your logo is being reviewed by our quality assurance experts, it will be ready soon enough!"),
	READY_WAITMSG("Your logo is ready, click on the icon and have some!"),
	COPYRIGHT("Copyright\u00a9 2009 WOKHEI"),

	INCOMING_TITLE("Pending request..."),
	INCOMING_TEXT("Your request has been registered. In a short time our chef Tony will approve it or reject it. Just give him a moment, he might be on his coffee break!"),
	ACCEPTED_TITLE("Request accepted!"),
	ACCEPTED_TEXT("You request has been accepted by our chef Tony - in 24hrs you will receive the logo you requested. We are tasking one of our pure-genius help-chefs with the job!"),
	REJECTED_TITLE("Request rejected"),
	REJECTED_TEXT("Unfortunately Tony didn't accept this request. This can happen if the request violated some copyright, or Tony didn't like it."),
	IN_PROGRESS_TITLE("Designing your logo..."),
	IN_PROGRESS_TEXT("One of our best designers has been chosen to cook you up a tasty logo. You are in very good hands (yes we do say the same to everyone)."),
	QUALITY_GATE_TITLE("Checking quality..."),
	QUALITY_GATE_TEXT("Tony himself manages the quality gate to make sure your logo is as good as you deserve!"),
	READY_TITLE("It's ready"),
	READY_TEXT("Your logo is finally ready - check it out clicking on the icon above and flood your senses with Wokhei goodness!"),
	VIEWED_TITLE("Don't make it go cold!"),
	VIEWED_TEXT("We hope you like your logo (we like it very much, if that can help!). You have 24hrs to buy it: in case you decide to show us the money you will be able to use the logo for commercial purposes (and you'll get access to the fancy vectorial versions)."),
	BOUGHT_TITLE("You purchased this logo"),
	BOUGHT_TEXT("This logo is yours, just make anything you want with it!"),
	ARCHIVED_TITLE("This logo is archived"),
	ARCHIVED_TEXT("You didn't purchase this logo. You can still use it under licence for non-commercial purposes. We would appreciate if you could notify Wokhei if you decide to use this logo - so don't be shy, we won't sue you!"), 


	ADMIN_STATUS_FILTER_LABEL("Status"),
	ADMIN_USER_FILTER_LABEL("User"),
	ADMIN_USER_FILTER_BOX("User Email Here"),
	ADMIN_DATE_FILTER_LABEL("Date Range"),
	ADMIN_CLEAR_FILTERS("Clear Filters"), 
	ADMIN_ADD_ADMIN_DEFAULT_TXT("Insert new admin email"), 
	ADMIN_CONFIRM_ACCEPT_TXT("Are you sure you want to accept this order?"), 
	ADMIN_CONFIRM_REJECT_TXT("Are you sure you want to reject this order"), 
	RASTERIZED_LBL("Rasterized PNG"),
	PRESENTATION_LBL("Presentation PNG"),
	VECTORIAL_LBL("Vectorial PDF"), 
	BROWSE("Browse..."), 
	UPLOAD("Upload!"),

	EMAIL_ORDER_ACCEPTED("Your recent Logo Order has been ACCEPTED by Wokhei - it will be ready in 24h:/n"),
	EMAIL_ORDER_REJECTED("Your recent Logo Order has been REJECTED by Wokhei:/n"), 

	//INDEX
	WATCH_A_VIDEO("Watch a video"),
	HIDE_VIDEO("Hide video"), 
	LOGIN("Login")
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
