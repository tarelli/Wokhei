package com.brainz.wokhei.resources;

public enum Images {

	INCOMING("./images/status/incoming.png","./images/status/incomingSmall.png"),
	READY("./images/status/ready.png","./images/status/readySmall.png"),
	ARCHIVED("./images/status/archived.png","./images/status/archivedSmall.png"),
	IN_PROGRESS("./images/status/inprogress.png","./images/status/inprogressSmall.png"),
	TASTING("./images/status/tasting.png","./images/status/tastingSmall.png"),
	BOUGHT("./images/status/bought.png","./images/status/boughtSmall.png"),
	REJECTED("./images/status/rejected.png","./images/status/rejectedSmall.png"),
	ACCEPTED("./images/status/accepted.png","./images/status/acceptedSmall.png"),
	QUALITY_GATE("./images/status/qualitygate.png","./images/status/qualitygateSmall.png"),
	VIEWED("./images/status/ready.png","./images/status/readySmall.png"),

	DRINKBODY("./images/bodydrink.png"),
	DRINKBODYTILE("./images/bodytiledrink.png"),
	DRINKFOOTER("./images/footerdrink.png"),

	OK("./images/okSign.png"),
	NOK("./images/nokSign.png"),

	FILTER("./images/filter.png"),
	OPTIONS("./images/options.png"),

	INFOS("./images/legend.png"), 
	BETA("./images/beta.png")
	;

	String _imageName;
	String _smallImageName;

	private Images()
	{

	}

	private Images(String imageName)
	{
		_imageName=imageName;
	}

	private Images(String imageName,String smallImageName)
	{
		_imageName=imageName;
		_smallImageName=smallImageName;
	}

	public String getImageURL()
	{
		return _imageName;
	}

	public String getSmallImageURL() {
		return _smallImageName;
	}
}
