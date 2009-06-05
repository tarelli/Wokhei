package com.brainz.wokhei.resources;

public enum TagsOracle {
	ADVERTISING("Advertising"),
	INSURANCE("Insurance"),
	AEROSPACE("Aerospace"),
	INDUSTRIAL("Industrial"),
	AGRIBUSINESS("Agribusiness"),
	INVESTEMENT("Investment"),
	AIRLINES("Airlines"),
	GAMING("Gaming"),
	ALCHOOLIC("Alcoholic"),
	BEVERAGES("Beverages"),
	MACHINERY("Machinery"),
	APPAREL("Apparel"),
	FOOTWEAR("Footwear"),
	METALS("Metals"),
	AUTOS("Autos"),
	MOVIES("Movies"),
	HOME_ENTERTAINMENT("HomeEntertainment"),
	BANKING("Banking"),
	NATURAL_GAS("NaturalGas"),
	BIOTECHNOLOGY("Biotechnology"),
	PAPER("Paper"),
	FOREST("Forest"),
	BROADCASTING("Broadcasting"),
	PACKAGING("Packaging"),
	CONTAINERS("Containers"),
	CABLETV("CableTV"),
	PUBLISHING("Publishing"),
	CHEMICALS("Chemicals"),
	RECREATION("Recreation"),
	RESTAURANTS("Restaurants"),
	RETAILING("Retailing"),
	COMPUTERS("Computers"),
	HARDWARE("Hardware"),
	NETWORKING("Networking"),
	SOFTWARE("Software"),
	EDUCATIONAL("Educationial"),
	SEMICONDUCTORS("Semiconductors"),
	ENVIRONMENTAL("Environmental"),
	SHIPBUILDING("Shipbuilding"),
	FINANCIAL("Financial"),
	SUPERMARKETS("Supermarkets"),
	FOODS("Foods"),
	TELECOMMUNICATION("Telecommunications"),
	WIRELESS("Wireless"),
	HEALTHCARE("Healthcare"),
	WIRELINE("Wireline"),
	TEXTILE("Textile"),
	PHARMACEUTICALS("Pharmaceuticals"),
	TRANSPORTATION("Transportation"),
	TRAVEL("Travel"),
	TOURISM("Tourism"),
	HOME_BUILDING("Homebuilding"),
	WHOLESALING("Wholesaling"),
	;

	private String _string;

	TagsOracle(String string)
	{
		_string=string;
	}

	public String getString()
	{
		return _string;
	}

	public String getHashedString()
	{
		return "#"+_string;
	}
}
