package com.brainz.wokhei.resources;

public enum Messages {

	PENDING("Pending"),

	INCOMING("Incoming"),

	ACCEPTED("Accepted"),

	REJECTED("Rejected"),

	REVIEWING("Reviewing"),

	IN_PROGRESS("In progress"),

	QUALITY_GATE("Quality Gate"),

	READY("Ready"),

	BOUGHT("Bought"),

	VIEWED("Viewed"),

	LOGO_NAME_LBL("Product name"),

	LOGO_NAME_HELP_MESSAGE(
			"This is the name of the company, product, service (or anything else!) that you need the logo for. This name will appear in the logo unless you specify otherwise in the description."),

	LOGO_NAME_EG_LBL("e.g. Tree Of Life Ltd"),

	LOGO_NAME_TXTBOX("the name of your business or product"),

	LOGO_NAME_ERROR_NONE("You need to specify a product name!"),

	LOGO_NAME_ERROR_TOOLONG("Sorry the product name is too long!"),

	LOGO_COLOUR_LBL("Main colour"),

	LOGO_COLOUR_HELP_MESSAGE(
			"This will be the main colour of your logo. Note that the logo might have more colours or different shades of the main colour you pick. If you have no colour preference, or if you want to entrust our designers with this choice, pick the question mark: we'll surprise you!"),

	LOGO_DESC_TXTBOX("Describe here how you want your logo and what it is for!"),

	LOGO_DESC_TXTBOX_REVISION(
			"Describe here how you would like to change your logo, what you did not like and whatnot. With your help the next iteration will be just perfect!"),

	LOGO_COLOUR_EG_LBL("Logo primary colour"),

	LOGO_DESC_LBL("Description"),

	LOGO_DESC_HELP_MESSAGE(
			"You can use up to 250 characters to tell us how you would like your logo and something more about your business or product."),

	LOGO_DESC_EG_LBL(
			"e.g. We'd like a tree in the logo for our recycling business (...)"),

	LOGO_DESC_ERROR_EMPTY("Sorry, you need to put in a description!"),

	LOGO_DESC_ERROR_TOOLONG(
			"Sorry, description can't be longer than 250 characters!"),

	LOGO_COLOUR_ERROR_NONE("Pick the main colour!"),

	GENERIC_ERROR("Error"),

	LOGO_DESC_ERROR_TOOSHORT(
			"Sorry, description needs to be at least 25 characters long!"),

	REQUEST_LOGO_LBL("Request a new logo"),

	REVISION_LOGO_LBL("Review your logo"),

	SEND_REQUEST("Send request!"),

	ACCEPTED_WAITMSG("Your logo is getting ready, fancy a drink meanwhile?"),

	ERROR_WAITMSG(
			"An error occurred while sending your request. Please do complain!"),

	INCOMING_WAITMSG(
			"Your request has been sent to the kitchen. Enjoy the appetizers!"),

	PENDING_WAITMSG("Thanks for your tip! Redirecting to PayPal..."),

	IN_PROGRESS_WAITMSG(
			"Your logo is being cooked-up, have some more appetizers!"),

	QUALITY_GATE_WAITMSG(
			"Your logo is being reviewed by our quality experts, it will be ready soon enough!"),

	READY_WAITMSG(
			"Your Stir Fried logo is ready! Click on the steamin' wok to view it!"),

	VIEWED_WAITMSG(
			"You can now buy the logo or add spice claiming your free revision!"),

	REVIEWED_WAITMSG(
			"Thanks to your suggestions the final version of your logo is now ready!"),

	REVIEWING_WAITMSG(
			"We are working on your suggestions to give your logo the final touch!"),

	KILLSWITCH_ON_WAITMSG(
			"Sorry, we are fully booked at the moment, come back later!"),

	COPYRIGHT("Copyright\u00a9 2009 WOKHEI"),

	INCOMING_TITLE("Pending request..."),

	INCOMING_TEXT(
			"Your request has been registered. It will be shortly approved and you will be notified via email. Just give us a moment, we might be on a coffee break!"),

	ACCEPTED_TITLE("Request accepted!"),

	ACCEPTED_TEXT(
			"You request has been accepted - your logo will be ready in 24hrs. Your logo is being assigned to one of the designers in the Wokhei network with expertise in your area."),

	REJECTED_TITLE("Request rejected"),

	REJECTED_TEXT(
			"Sorry, your order has been rejected. This can happen if the request was in violation of copyright or it sounded like a joke. Go ahead - give it another shot!"),

	IN_PROGRESS_TITLE("Designing your logo..."),

	IN_PROGRESS_TEXT(
			"One of our best designers has been chosen to cook you up a tasty logo. You are in very good hands (yes, we do say the same to everyone)."),

	QUALITY_GATE_TITLE("Quality gate"),

	QUALITY_GATE_TEXT(
			"We take very seriously the quality of our products. We are now reviewing your logo and making sure it is as good as you deserve!"),

	READY_TITLE("Your logo it's ready!"),

	READY_TEXT(
			"Your logo is finally ready - check it out clicking on the steamin' wok icon above and flood your senses with Wokhei goodness!"),

	VIEWED_TITLE("It's ready!"),

	VIEWED_TEXT(
			"Your logo is ready! Buy it now for 98 EUR (VAT Included - tip will be deducted from total) or request some changes for FREE by clicking the 'Make Your Changes' button below, then inserting a new description and hitting 'Submit'!"),

	REVIEWING_TITLE("Taking onboard your changes!"),

	REVIEWING_TEXT(
			"Thanks for your suggestions, we are now working on the changes you requested. Your logo will be ready in 24hrs!"),

	REVIEWED_TITLE("The revision is now ready!"),

	REVIEWED_TEXT(
			"Thanks to your suggestions a revised version of your logo is now ready. Make it yours in a few clicks for only 98 EUR (VAT Included - upfront tip will be deducted from total). After the payment you'll be able to download rasterized and vectorial versions of your logo!"),

	BOUGHT_TITLE("You purchased this logo"),

	BOUGHT_TEXT(
			"You are the proud owner of this stir fried logo! Now it's your turn to make money!"),

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

	// NOTIFICATION EMAILS

	EMAIL_ORDER_READY_SUBJ("Your Stir Fried Logo is ready!"),

	EMAIL_ORDER_SUBJ("Order update from Wokhei"),

	EMAIL_ORDER_ACCEPTED(
			"Your Stir Fried Logo order has been accepted - it will be ready in 24hrs! "),

	EMAIL_ORDER_ACCEPTED_FOOTER(
			"Check out [ $w ] to find out more about your order status."),

	EMAIL_ORDER_REJECTED(
			"Apologies, your Stir Fried Logo order has been rejected! "),

	EMAIL_ORDER_REJECTED_FOOTER(
			"Visit [ $w ] to find out more and submit a new order."),

	EMAIL_ORDER_IN_PROGRESS(
			"Your Stir Fried Logo has been assigned to one of the designers in the Wokhei network and is now in progress."),

	EMAIL_ORDER_IN_PROGRESS_FOOTER(
			"Check out [ $w ] to find out more about your order status."),

	EMAIL_ORDER_QUALITY_GATE(
			"Your Stir Fried Logo is currently being reviewed for quality check."),

	EMAIL_ORDER_QUALITY_GATE_FOOTER(
			"Check out [ $w ] to find out more about your order status."),

	EMAIL_ORDER_READY(
			"Your Stir Fried Logo is ready!\nWhat are you waiting for? Go check it out at [ $w ] and enjoy!"),

	EMAIL_ORDER_READY_FOOTER("Purchase the logo or claim your free revision!"),

	EMAIL_ORDER_READY_AFTER_REVIEW(
			"Your free revision is ready!\nOur designers refined the logo as per your suggestions. What are you waiting for? Go check it out at [ $w ] and enjoy!"),

	EMAIL_ORDER_READY_FOOTER_AFTER_REVIEW(
			"Purchase the logo to be the proud owner of this unique Wokhei creation!"),

	EMAIL_ORDER_GOODBYE("Wokhei - Stir Fried Logos"),

	// NOTIFY EMAILS WHEN LOGO ORDER IS SUBMITTED

	NOTIFY_SUBMITTED_SUBJ("New Incoming Order from "),

	NOTIFY_SUBMITTED_BODY("A new order has been submitted by "),

	NOTIFY_QUALITY_GATE_SUBJ("Quality Gate Order with incomplete Upload"),

	NOTIFY_QUALITY_GATE_BODY(
			"The following logo is in quality gate but the upload is not complete:"),

	// INDEX

	WATCH_A_VIDEO("Watch a video"),

	HIDE_VIDEO("Hide video"),

	LOGIN("Login to Wokhei"),

	LOGIN_SANDBOX("Login to Wokhei Sandbox"),

	LOGIN_TIP("Click HERE if you don't have a Google Account"),

	INDEX_COPYRIGHT("\u00a9 2009 Wokhei"),

	// ABOUT --> moved to HTML

	// FAQ --> moved to HTML

	// CAREEERS

	CAREERS_TITLE("Careers"),

	CAREERS_GRAPHIC_TITLE("Logo graphic designer"),

	// EXTRA

	MUST_ACCEPT_LICENSE("You need to accept terms and conditions first!"),

	ACCEPT_CONDITIONS("I accept the terms and conditions"),

	LESS_THAN_HOUR("less than an hour "),

	SANDBOX("Sandbox"),

	WEBSITE("http://www.wokhei.com"),

	WEBSITE_SANDBOX("http://wokheisandbox.appspot.com/"),

	EUR(" EUR"), WAIT("Wait..."),

	;

	private String _text;

	Messages(String text) {
		_text = text;
	}

	public String getString() {
		return _text;
	}
}
