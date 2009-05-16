/**
 * 
 */
package com.brainz.wokhei.shared;

/**
 * @author matteocantarelli
 *
 */
public enum Colour {
	PANTONE534("PANTONE 534"),
	PANTONE3308("PANTONE 3308"),
	PANTONE512("PANTONE 512"), 
	PANTONEProcessBlue("PANTONE Process Blue"),
	PANTONEOrange021("PANTONE Orange 021"), 
	PANTONE456("PANTONE 456"),
	PANTONE186("PANTONE 186"),
	PANTONEWhite("PANTONE White"), 
	PANTONEWarmGray8("PANTONE Warm Gray"),
	PANTONE485("PANTONE 485"),
	PANTONE357("PANTONE 357"), 
	PANTONE371("PANTONE 371"), 
	PANTONE457("PANTONE 457"),
	PANTONE7427("PANTONE 7427"),
	PANTONEBlack("PANTONE Black"), 
	PANTONE361("PANTONE 361"), 
	PANTONE368("PANTONE 368"), 
	PANTONERubinRed("PANTONE Rubin Red"), 
	PANTONE306("PANTONE 306"),
	PANTONE676("PANTONE 676"), 
	PANTONE159("PANTONE 159"), 
	PANTONEWarmRed("PANTONE Warm Red"), 
	PANTONEPurple("PANTONE Purple"), 
	PANTONE7449("PANTONE 7449")
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
