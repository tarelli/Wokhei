package com.brainz.wokhei.resources;

public enum Images {

	INCOMING("./images/status/incoming.png"),
	READY("./images/status/ready.png"),
	ARCHIVED("./images/status/archived.png"),
	IN_PROGRESS("./images/status/inprogress.png"),
	TASTING("./images/status/tasting.png"),
	BOUGHT("./images/status/bought.png"),
	REJECTED("./images/status/rejected.png"),
	ACCEPTED("./images/status/accepted.png"),
	QUALITY_GATE("./images/status/qualitygate.png"),

	DRINKBODY("./images/bodydrink.png"),
	DRINKBODYTILE("./images/bodytiledrink.png"),
	DRINKFOOTER("./images/footerdrink.png"),

	OK("./images/okSign.png"),
	NOK("./images/nokSign.png"),

	INFOS("./images/legend.png");

	String _imageName;

	private Images()
	{

	}

	private Images(String imageName)
	{
		_imageName=imageName;
	}

	public String getImageURL()
	{
		return _imageName;
	}
}
