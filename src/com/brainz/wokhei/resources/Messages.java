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
	INCOMING_WAITMSG("Your request has been sent to the kitchen, enjoy the appetizers!"),
	IN_PROGRESS_WAITMSG("Your logo is being cooked-up, have some more appetizers!"),
	QUALITY_GATE_WAITMSG("Your logo is being reviewed by our quality experts, it will be ready soon enough!"),
	READY_WAITMSG("Your logo is ready! Click on the wok to see it!"),
	VIEWED_WAITMSG_1("Enjoy your tasty Stir Fried Logo! You have "),
	VIEWED_WAITMSG_2("hrs left to buy. Hurry up!"),
	KILLSWITCH_ON_WAITMSG("Sorry, we are fully booked at the moment, come back later!"),
	COPYRIGHT("Copyright\u00a9 2009 WOKHEI"),

	INCOMING_TITLE("Pending request..."),
	INCOMING_TEXT("Your request has been registered. In a short time it will be approved or rejected. Just give us a moment, we might be on a coffee break!"),
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
	VIEWED_TEXT("You have 24hrs from the moment you first see the logo to buy it for commercial use. After the 24hrs you still will be able to use it but unexclusively and under the Wokhei Limited License."),
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

	EMAIL_ORDER_ACCEPTED("Your logo order has been ACCEPTED by Wokhei - it will be ready in 24h:"),
	EMAIL_ORDER_REJECTED("Your logo order has been REJECTED by Wokhei:"), 
	EMAIL_ORDER_READY("This is a notification from Wokhei: your stir fried logo is ready!"),

	//INDEX
	WATCH_A_VIDEO("Watch a video"),
	HIDE_VIDEO("Hide video"), 
	LOGIN("Login to Wokhei"), 
	LOGIN_TIP("Click HERE if you don't have a Google Account"),
	FIRST_STEP_TITLE("Request your logo"),
	SECOND_STEP_TITLE("Wait 24hrs"),
	THIRD_STEP_TITLE("Your logo is ready!"),
	FIRST_STEP("Login with your Google Account, insert your product/company/service name, up to 5 tags to describe your logo and pick your preference for the main colour: as easy as that!"),
	SECOND_STEP("Once your request is accepted it gets assigned to one of the graphic designers in the Wokhei network with expertise in your product area. He will prepare your logo in 24hrs!"),
	THIRD_STEP("That was quick! Buy the logo within 24hrs from the first time you see it for only 49.90 EUR (VAT excluded) - approx 70 USD - to use it under the Wokhei Commercial License. Or ... use it for free under the Wokhei Limited License!"), 
	INDEX_COPYRIGHT("\u00a9 2009 Wokhei"), 

	//ABOUT
	ABOUT_TITLE("About Wokhei"),
	ABOUT_MENU_ABOUT_US("About Us"),
	ABOUT_MENU_WHATWOKHEI("What does Wokhei mean?"),
	ABOUT_MENU_DIFFERENTWOKHEI("What is different about Wokhei?"),
	ABOUT_MENU_RESTAURANT("Why Wokhei looks like a restaurant?"),
	ABOUT_MENU_GRAPHICSNETWORK("Who is part of the Wokhei network?"),
	ABOUT_MENU_CONTACTUS("Contact Us"),


	ABOUT_MENU_ABOUT_US_TEXT("Wokhei is a startup founded in February 2009 with one simple goal: gather talented graphic designers from all around the globe and make them part of a unique network to offer a truly unique logo service.<br>Wokhei is now used every day by Companies, Associations and individuals to create their logos with just a few clicks.<br><br>For us at Wokhei art is not something that can be forced: art doesn't just happen at will within the white walls of an office. Because of its very essence art emerges out of different cultures, traditions and individual intuitions.<br> Art can't be standardized - that's why Wokhei is built on a completely distributed structure.<br> Wokhei truly believes in this and all of the Wokhei designers work from their chosen environment: a sunny beach, a rainy forest or - more conventionally - their bedroom or studio.<br> Wokhei simply channels its associates' creativity - with a twist!"),
	ABOUT_MENU_WHATWOKHEI_TEXT("Wokhei (wok-hei) is a term in referring to the flavour, taste and ''essence'' imparted by a hot wok on the food.<br> When cooked correctly, the essence of the food comes through the flavour and the dish is said to ''have wok hei''.<br> To impart ''wok hei'', the food must be cooked in a wok over a high flame while being stirred and tossed quickly.<br> In practical terms, the flavour imparted by chemical compounds results from caramelization, Maillard reactions, and the partial combustion of oil that comes from charring and searing of the food at very high heat in excess of 200C degrees. Stir fry technique does not requires as much fat as typical western dishes because of a combination of the metal type and the constant shifting of the food during cook time. As diners usually elect their food with chopsticks from a shared serving bowl, any excess oil remains in the bottom of the serving dish rather than being eaten.<br>"),
	ABOUT_MENU_DIFFERENTWOKHEI_TEXT("Wokhei is not about endless and complicated revision workflows.<br> <br>Wokhei is about entrusting designers with the power of surpising the customer.<br><br>Wokhei is a game of creativity for both the customer who requests the logo and the designer who makes it.<br><br>Wokhei is a fresh and experimental approach to logo design!"),
	ABOUT_MENU_RESTAURANT_TEXT("Because Wokhei is your favourite place when you're hungry for stir fried logos!<br>The concept behind Wokhei is driven by the metaphor of culinary deli as artistic expression.<br>Customers in need of a tasty logo for their company or product reach out to Wokhei to get a mouthful of stir fried goodness in a reasonable time for a reasonable price, and Wokhei always responds with tasteful bites!"),
	ABOUT_MENU_GRAPHICSNETWORK_TEXT("Wokhei is a dynamic and increasingly growing network of professional designers evenly distributed throughout the four continents.<br>See the careers section if you wish to gather more information about the unique opportunity of joining the Wokhei network.<br>"),
	ABOUT_MENU_CONTACTUS_TEXT("Contact Us"),

	//FAQ
	FAQ_TITLE("Frequently Asked Questions"),
	FAQ_1("Can I login without a Google Account?"),
	FAQ_1_ANSWER("No!\n\nAt the moment having a Google account is the only way to access Wokhei services.\n\nWhy? Because virtually everyone has a Google account and this way you don't have to remember yet another password. Ok, also because we are huge Google fans. <br><br>Don't worry, the login operation is completely managed by Google, Wokhei does not have access to your password or any other personal info!"),
	FAQ_2("What if I want to slightly modify my logo?"),
	FAQ_2_ANSWER("Revisions are not part of the Wokhei workflow.<br>If you don't like your logo (unlikely but possible) just order another one and cross your fingers - ain't it good fun?!"),
	FAQ_3("How should I use tags when requesting my logo?"),
	FAQ_3_ANSWER("The tags you have to pick when ordering your logo are descriptive labels to tell the designer what you need the logo for and how you would like your logo to look like. <br> You can choose a maximum of 5 tags so make sure to pick them carefully!"),
	FAQ_4("What if I want no text in my logo?"),
	FAQ_4_ANSWER("If you don't want any text to appear in your logo you still have to specify the product name but you can use the #no-text tag in order to receive a graphics only logo."),
	FAQ_5("When can I download my logo?"),
	FAQ_5_ANSWER("If you decide not to buy you will be able to download your logo 24hrs after you sees it for the first time.\n\nIn case you buy you can download it right after the payment."),
	FAQ_6("Why should I buy the logo if I can use it for free?"),
	FAQ_6_ANSWER("Fair enough question!<br>If you don't buy the logo you can only use it under the Wokhei Limited License, which excludes any commercial use. Also, if you don't buy your logo someone else can pick it up from the gallery (coming soon) and use it!"),
	FAQ_7("How do I pay for my logo?"),
	FAQ_7_ANSWER("You can easily pay through Paypal either using your Paypal account or directly via all the majors credit cards."),
	FAQ_8("Is Wokhei connected in any way to Google?"),
	FAQ_8_ANSWER("No. Wokhei is powered by the Google App Engine and the Google Web Toolkit but is not connected in any other way to Google."),
	FAQ_9("Is there a limit in how many times I can request a same logo?"),
	FAQ_9_ANSWER("No, there is no official limit. But the admins have a nice Reject button for that kind of things, so don't push your luck!"),

	//CAREEERS
	CAREERS_TITLE("Careers"), 
	CAREERS_GRAPHIC_TITLE("Logo graphic designer"),  

	//EXTRA
	EXTRAORDINARY_NOTIFICATION("Urgent notification from Wokhei"),
	SUCCHIA_MELO("AZIZ AZIZ ... SUCCHIA-MELO E PALLE IN CULO"), 
	MUST_ACCEPT_LICENSE("You need to accept terms and conditions first!"),
	ACCEPT_CONDITIONS("I accept the terms and conditions"),

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
