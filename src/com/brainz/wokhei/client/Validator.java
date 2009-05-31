/**
 * 
 */
package com.brainz.wokhei.client;

import com.brainz.wokhei.shared.Colour;

/**
 * @author matteocantarelli
 *
 */
public class Validator {

	private static final int MAX_LOGONAME_LENGTH = 20;
	private static final int MAX_TAGS = 5;
	private static final int TAG_MAX_LENGTH = 15;
	private static final int MIN_TAGS = 3;

	public enum TagsErrors
	{
		NONE,
		TOO_MANY_TAGS,
		TAGS_TOO_LONG,
		TOO_FEW_TAGS
	}

	public enum ColourErrors{
		NONE,
		NO_COLOR
	}

	public enum LogoErrors {
		NONE,
		TOO_LONG,
		EMPTY
	}

	/**
	 * @param tagsString
	 * @return
	 */
	public static TagsErrors validateTags(String tagsString)
	{
		String[] tags=tagsString.split(" ");
		if(tags.length<MIN_TAGS)
		{
			return TagsErrors.TOO_FEW_TAGS;
		}
		else if(tags.length>MAX_TAGS)
		{
			return TagsErrors.TOO_MANY_TAGS;
		}
		for(int i=0;i<tags.length;i++)
		{
			if(tags[i].length()-1>TAG_MAX_LENGTH)
			{
				return TagsErrors.TAGS_TOO_LONG;
			}
		}
		return TagsErrors.NONE;
	}

	/**
	 * @param logoName
	 * @return
	 */
	public static LogoErrors validateLogoName(String logoName) 
	{
		if(logoName.length()>MAX_LOGONAME_LENGTH)
		{
			return LogoErrors.TOO_LONG;
		}
		else if(logoName.length()==0)
		{
			return LogoErrors.EMPTY;
		}
		return LogoErrors.NONE;
	}


	/**
	 * @param colour
	 * @return
	 */
	public static ColourErrors validateColour(Colour colour)
	{
		if(colour==null)
		{
			return ColourErrors.NO_COLOR;
		}
		return ColourErrors.NONE;
	}


}
