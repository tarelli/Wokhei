package com.brainz.wokhei.resources;

public enum Images {

	INCOMING("./images/status/incoming.png"),
	READY("./images/status/ready.png"),
	BOUGHT("./images/status/bought.png"),
	ARCHIVED("./images/status/archived.png"),
	COOKING("./images/status/cooking.png"),
	TASTING("./images/status/tasting.png"),
	PAYED("./images/status/payed.png"),
	REJECTED("./images/status/rejected.png");

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
