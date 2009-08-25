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
	LOGO_NAME_HELP_MESSAGE("This is the name of the company, product, service, website, association (or anything else) that you need the logo for. This name will appear in the logo unless you specify the #notext tag, in that case the logo will only be a graphic representation."),
	LOGO_NAME_EG_LBL("e.g. Tree Of Life Ltd"),
	LOGO_NAME_TXTBOX("the name of your business or product"),
	LOGO_NAME_ERROR_NONE("You need to specify a product name!"),
	LOGO_NAME_ERROR_TOOLONG("Sorry the product name is too long!"),
	LOGO_COLOUR_LBL("Main colour"),
	LOGO_COLOUR_HELP_MESSAGE("This will be the main colour of your logo. Note that the logo might have more secondary colours or shades of the main colour you pick."),
	LOGO_TAGS_TXTBOX("tags to describe your business or product"),
	LOGO_COLOUR_EG_LBL("Logo primary colour"),
	LOGO_TAGS_LBL("Up to 5 Tags"),
	LOGO_TAGS_HELP_MESSAGE("Use tags as labels to describe your business or product. You can choose a maximum of 5 tags so make sure to pick them carefully! Please do not write sentences."),
	LOGO_TAGS_EG_LBL("e.g. #healthcare #safety #family #assistance"),
	LOGO_TAGS_ERROR_TOOMANY("Sorry, max five tags!"),
	LOGO_TAGS_ERROR_TOOLONG("Sorry, tags can't be too long!"),
	LOGO_COLOUR_ERROR_NONE("Pick the main colour!"),
	GENERIC_ERROR("Error"), 
	LOGO_TAGS_ERROR_NOTENOUGH("You need to pick at least three tags!"),
	REQUEST_LOGO_LBL("Request your logo"),
	SEND_REQUEST("Send request!"),

	ACCEPTED_WAITMSG("Your logo is getting ready, fancy a drink meanwhile?"),
	ERROR_WAITMSG("An error occurred while sending your request. Please do complain!"),
	INCOMING_WAITMSG("Your request has been sent to the kitchen. Enjoy the appetizers!"),
	IN_PROGRESS_WAITMSG("Your logo is being cooked-up, have some more appetizers!"),
	QUALITY_GATE_WAITMSG("Your logo is being reviewed by our quality experts, it will be ready soon enough!"),
	READY_WAITMSG("Your logo is ready! Click on the wok to see it!"),
	VIEWED_WAITMSG_1("Enjoy your tasty Stir Fried Logo! You have "),
	VIEWED_WAITMSG_2("hrs left to buy. Hurry up!"),
	KILLSWITCH_ON_WAITMSG("Sorry, we are fully booked at the moment, come back later!"),
	COPYRIGHT("Copyright\u00a9 2009 WOKHEI"),

	INCOMING_TITLE("Pending request..."),
	INCOMING_TEXT("Your request has been registered. It will be shortly approved or rejected and you will be notified via email. Just give us a moment, we might be on a coffee break!"),
	ACCEPTED_TITLE("Request accepted!"),
	ACCEPTED_TEXT("You request has been accepted - in 24hrs your logo will be ready. Your logo is being assigned to one of the designers in the Wokhei network with expertise in your area."),
	REJECTED_TITLE("Request rejected"),
	REJECTED_TEXT("Sorry, your order has been rejected. This can happen if the request was in violation of copyright, it looked like a joke or ... we just didn't like it. Go ahead - give it another shot!"),
	IN_PROGRESS_TITLE("Designing your logo..."),
	IN_PROGRESS_TEXT("One of our best designers has been chosen to cook you up a tasty logo. You are in very good hands (yes, we do say the same to everyone)."),
	QUALITY_GATE_TITLE("Quality gate"),
	QUALITY_GATE_TEXT("We take very seriously the quality of your logo. We are now reviewing your logo and making sure it is as good as you deserve!"),
	READY_TITLE("It's ready!"),
	READY_TEXT("Your logo is finally ready - check it out clicking on the icon above and flood your senses with Wokhei goodness!"),
	VIEWED_TITLE("It's ready!"),
	VIEWED_TEXT("You have 24hrs to buy the logo for commercial use for only 105 EUR (VAT included) - approx 145 USD. If you don't buy you still will be able to use the logo but only unexclusively and under the Wokhei Limited License."),
	BOUGHT_TITLE("You purchased this logo"),
	BOUGHT_TEXT("You can use this logo according to the Wokhei Commercial License. Now it's your turn to make money!"),
	ARCHIVED_TITLE("This logo is archived"),
	ARCHIVED_TEXT("You didn't buy this logo, you can use it under the Wokhei Limited Licence. Please remember to read the license agreement and to credit Wokhei if you use it."), 

	DOWNLOAD_RASTERIZED("Download Logo (Rasterized PNG)"),
	DOWNLOAD_VECTORIAL("Download Logo (Vectorial PDF)"),

	ADMIN_STATUS_FILTER_LABEL("Status"),
	ADMIN_USER_FILTER_LABEL("User"),
	ADMIN_USER_FILTER_BOX("User Email Here"),
	ADMIN_DATE_FILTER_LABEL("Date Range"),
	ADMIN_CLEAR_FILTERS("Clear Filters"), 
	ADMIN_ADD_ADMIN_DEFAULT_TXT("Insert new admin email"), 
	ADMIN_CONFIRM_ACCEPT_TXT("Are you sure you want to accept this order?"), 
	ADMIN_CONFIRM_REJECT_TXT("Are you sure you want to reject this order?"), 
	RASTERIZED_LBL("Rasterized PNG"),
	PRESENTATION_LBL("Presentation PNG"),
	VECTORIAL_LBL("Vectorial PDF"), 
	BROWSE("Browse..."), 
	UPLOAD("Upload!"),

	//NOTIFICATION EMAILS
	EMAIL_ORDER_READY_SUBJ("Your Stir Fried Logo is ready!"),
	EMAIL_ORDER_SUBJ("Order update from Wokhei"),

	EMAIL_ORDER_ACCEPTED("Your Stir Fried Logo order has been accepted - it will be ready in 24hrs! "),
	EMAIL_ORDER_ACCEPTED_FOOTER("Check out [ $w ] to find out more about your order status."),

	EMAIL_ORDER_REJECTED("Apologies, your Stir Fried Logo order has been rejected! "),
	EMAIL_ORDER_REJECTED_FOOTER("Visit [ $w ] to find out more and submit a new order."),

	EMAIL_ORDER_IN_PROGRESS("Your Stir Fried Logo has been assigned to one of the designers in the Wokhei network and is now in progress."),
	EMAIL_ORDER_IN_PROGRESS_FOOTER("Check out [ $w ] to find out more about your order status."),

	EMAIL_ORDER_QUALITY_GATE("Your Stir Fried Logo is currently being reviewed for quality check."),
	EMAIL_ORDER_QUALITY_GATE_FOOTER("Check out [ $w ] to find out more about your order status."),

	EMAIL_ORDER_READY("Your Stir Fried Logo is ready!\nWhat are you waiting for? Go check it out at [ $w ] and enjoy!"),
	EMAIL_ORDER_READY_FOOTER("Remember, you have 24hrs to buy the logo starting from the first time you see it. Buying the logo will give you rights for commercial use under the Wokhei Commercial License."),

	EMAIL_ORDER_GOODBYE("Wokhei - Stir Fried Logos"),
	//NOTIFY EMAILS WHEN LOGO ORDER IS SUBMITTED
	NOTIFY_SUBMITTED_SUBJ("New Incoming Order from "),
	NOTIFY_SUBMITTED_BODY("A new order has been submitted by "),
	NOTIFY_QUALITY_GATE_SUBJ("Quality Gate Order with incomplete Upload"),
	NOTIFY_QUALITY_GATE_BODY("The following logo is in quality gate but the upload is not complete:"),

	//INDEX
	WATCH_A_VIDEO("Watch a video"),
	HIDE_VIDEO("Hide video"), 
	LOGIN("Login to Wokhei"), 
	LOGIN_SANDBOX("Login to Wokhei Sandbox"),
	LOGIN_TIP("Click HERE if you don't have a Google Account"),
	INDEX_COPYRIGHT("\u00a9 2009 Wokhei"), 

	//ABOUT --> moved to HTML

	//FAQ --> moved to HTML

	//CAREEERS
	CAREERS_TITLE("Careers"), 
	CAREERS_GRAPHIC_TITLE("Logo graphic designer"),  

	// Enquiry for archived logo
	ENQUIRY_ARCHIVED_QUESTION("Want to buy this archived logo?"),
	ENQUIRY_ARCHIVED_ACTION("Click HERE to send an enquiry!"),
	ENQUIRY_MADE("Your enquiry has been sent - we'll be in contact soon."),
	ENQUIRY_EMAIL_SUBJECT(" wants to buy an archived logo"),
	ENQUIRY_EMAIL_BODY("Happy days! Looks like this guy wants to buy an archived logo:\n"),
	ENQUIRY_FEEDBACK_OK("Enquiry submitted. We'll be in contact soon!"),
	ENQUIRY_FEEDBACK_KO("Something went terribly wrong and your enquiry was not submitted. Try again!"),

	//EXTRA
	EXTRAORDINARY_NOTIFICATION("Urgent notification from Wokhei"),
	SUCCHIA_MELO("AZIZ AZIZA WHAT GOT INTO YOU?? I DONT KNOW, IM NOT DOING IT!"), 
	MUST_ACCEPT_LICENSE("You need to accept terms and conditions first!"),
	ACCEPT_CONDITIONS("I accept the terms and conditions"), 
	LESS_THAN_HOUR("less than an hour "),
	SANDBOX("Sandbox"),
	WEBSITE("http://www.wokhei.com"),
	WEBSITE_SANDBOX("http://wokheisandbox.appspot.com/")

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
