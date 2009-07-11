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
	KILLSWITCH_ON_WAITMSG("Sorry, we are fully booked at the moment, come back later!"),
	COPYRIGHT("Copyright\u00a9 2009 WOKHEI"),

	INCOMING_TITLE("Pending request..."),
	INCOMING_TEXT("Your request has been registered. In a short time our chef Tony will approve it or reject it. Just give him a moment, he might be on his coffee break!"),
	ACCEPTED_TITLE("Request accepted!"),
	ACCEPTED_TEXT("You request has been accepted by our chef Tony - in 24hrs you will receive the logo you requested. We are tasking one of our pure-genius help-chefs with the job!"),
	REJECTED_TITLE("Request rejected"),
	REJECTED_TEXT("Unfortunately our chef Tony didn't accept this order. This can happen if the request violated some copyright, looked like a joke or ... Tony just didn't like it!"),
	IN_PROGRESS_TITLE("Designing your logo..."),
	IN_PROGRESS_TEXT("One of our best designers has been chosen to cook you up a tasty logo. You are in very good hands (yes we do say the same to everyone)."),
	QUALITY_GATE_TITLE("Checking quality..."),
	QUALITY_GATE_TEXT("Tony himself (our chef) manages the quality gate to make sure your logo is as good as you deserve!"),
	READY_TITLE("It's ready"),
	READY_TEXT("Your logo is finally ready - check it out clicking on the icon above and flood your senses with Wokhei goodness!"),
	VIEWED_TITLE("Don't make it go cold!"),
	VIEWED_TEXT("We hope you like your logo. You have 24hrs to buy it: in case you decide to show us the money you will be able to use the logo for commercial purposes."),
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
	LOGIN("Login to Wokhei"), 
	LOGIN_TIP("Click HERE if you don't have a Google Account"),
	FIRST_STEP_TITLE("Request your logo"),
	SECOND_STEP_TITLE("Wait 24hrs"),
	THIRD_STEP_TITLE("Your logo is ready!"),
	FIRST_STEP("Insert your product/company/service name, up to 5 tags to describe what the logo is for and your preference for the logo main colour: as easy as that!"),
	SECOND_STEP("Once your request is accepted it will get assigned to one of the graphic designers in the Wokhei network with expertise in your product area. He will design your logo in 24hrs!"),
	THIRD_STEP("You can buy the logo within 24hrs from the first time you view it to be free to use the logo under the Wokhei Commercial Licence. Or ... you can use it for free under the Wokhei Open Licence!"), 
	THE_BIG_PURPLE("Give it the Wokhei touch!"),
	INDEX_COPYRIGHT("\u00a9 2009 Wokhei"), 

	//ABOUT
	ABOUT_TITLE("About Wokhei"),
	ABOUT_MENU_ABOUT_US("About us"),
	ABOUT_MENU_WHATWOKHEI("What is Wokhei?"),
	ABOUT_MENU_DIFFERENTWOKHEI("What is different about Wokhei?"),
	ABOUT_MENU_RESTAURANT("Why Wokhei looks like a restaurant?"),
	ABOUT_MENU_GRAPHICSNETWORK("Who is part of the Wokhei network?"),
	ABOUT_MENU_CONTACTUS("Contact us"),
	ABOUT_MENU_LOGO("Download our logo"),

	ABOUT_MENU_ABOUT_US_TEXT("Wokhei is a privately funded startup which started in February 2009 with one simple goal: gather talented graphic designers from all around the world and make them part of a unique network to offer a unique service. In countries all around the world Wokhei is used every day from Companies, Associations and individuals to create their logos with just few clicks. The structure of Wokhei is completely distributed. In our idea art is not something that you can force, art doesn't evolve constrained within the white walls of an office. Because of its very essence art emerges out of different cultures and traditions, art can't be serialized. Wokhei truly believes in this and that is where its real essence comes from. Wokhei is constantly building a network of the most talented graphic designers from all around the world. All of the Wokhei graphics work from their chosen environment, a sunny beach, a rainy forest or most commonly their bedroom or studio. Wokhei is simply the vehicle of their creativity."),
	ABOUT_MENU_WHATWOKHEI_TEXT("Wokhei (wok-hei) is a term in referring to the flavour, tastes, and ''essence'' imparted by a hot wok on the food. The word ''hei'' is equivalent to ''qi'' . The term is sometimes rendered as ''wok chi'' in Western cookbooks. When cooked correctly, the essence of the food comes through the flavour and the dish is said to 'have wok hei'. To impart ''wok hei'', the food must be cooked in a wok over a high flame while being stirred and tossed quickly. In practical terms, the flavour imparted by chemical compounds results from caramelization, Maillard reactions, and the partial combustion of oil that come from charring and searing of the food at very high heat in excess of 200C . Stir fry technique does not require as much fat as typical western dishes because of a combination of the metal type and the constant shifting of the food during cook time. As diners usually elect their food with chopsticks from a shared serving bowl, any excess oil remains in the bottom of the serving dish rather than being eaten."),
	ABOUT_MENU_DIFFERENTWOKHEI_TEXT("What is different about Wokhei?"),
	ABOUT_MENU_RESTAURANT_TEXT("Why Wokhei looks like a restaurant?"),
	ABOUT_MENU_GRAPHICSNETWORK_TEXT("Who is part of the Wokhei network?"),
	ABOUT_MENU_CONTACTUS_TEXT("Contact us"),
	ABOUT_MENU_LOGO_TEXT("Download our logo"),


	//FAQ
	FAQ_TITLE("Frequently Asked Questions"),
	FAQ_1("Question 1?"),
	FAQ_1_ANSWER("Answer 1"),
	FAQ_2("Question 2?"),
	FAQ_2_ANSWER("Answer 2"),
	FAQ_3("Question 3?"),
	FAQ_3_ANSWER("Answer 3"),
	FAQ_4("Question 4?"),
	FAQ_4_ANSWER("Answer 4"),
	FAQ_5("Question 5?"),
	FAQ_5_ANSWER("Answer 5"),
	FAQ_6("Question 6?"),
	FAQ_6_ANSWER("Answer 6"),
	FAQ_7("Question 7?"),
	FAQ_7_ANSWER("Answer 7"),
	FAQ_8("Question 8?"),
	FAQ_8_ANSWER("Answer 8"),
	FAQ_9("Question 9?"),
	FAQ_9_ANSWER("Answer 9"),
	FAQ_10("Question 10?"),
	FAQ_10_ANSWER("Answer 10"),

	//CAREEERS
	CAREERS_TITLE("Careers"), 
	CAREERS_GRAPHIC_TITLE("Logo graphic designer"),
	CAREERS_GRAPHIC_TEXT("Do you have what it takes?"),

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
