/**
 * 
 */
package com.brainz.wokhei.shared;

/**
 * @author matteocantarelli
 *
 */
public enum Colour {
	PANTONE534("PANTONE\u00ae 534"),
	PANTONE3308("PANTONE\u00ae 3308"),
	PANTONE512("PANTONE\u00ae 512"), 
	PANTONEProcessBlue("PANTONE\u00ae Process Blue"),
	PANTONEOrange021("PANTONE\u00ae Orange 021"), 
	PANTONE456("PANTONE\u00ae 456"),
	PANTONE186("PANTONE\u00ae 186"),
	PANTONEWhite("PANTONE\u00ae White"), 
	PANTONEWarmGray8("PANTONE\u00ae Warm Gray"),
	PANTONE485("PANTONE\u00ae 485"),
	PANTONE357("PANTONE\u00ae 357"), 
	PANTONE371("PANTONE\u00ae 371"), 
	PANTONE457("PANTONE\u00ae 457"),
	PANTONE7427("PANTONE\u00ae 7427"),
	PANTONEBlack("PANTONE\u00ae Black"), 
	PANTONE361("PANTONE\u00ae 361"), 
	PANTONE368("PANTONE\u00ae 368"), 
	PANTONERubinRed("PANTONE\u00ae Rubin Red"), 
	PANTONE306("PANTONE\u00ae 306"),
	PANTONE676("PANTONE\u00ae 676"), 
	PANTONE159("PANTONE\u00ae 159"), 
	PANTONEWarmRed("PANTONE\u00ae Warm Red"), 
	PANTONEPurple("PANTONE\u00ae Purple"), 
	PANTONE7449("PANTONE\u00ae 7449")
	;

	private String _name;

	Colour(String name)
	{
		_name=name;
	}

	public String getName()
	{
		return _name;
	}

}
