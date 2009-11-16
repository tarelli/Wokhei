/**
 * 
 */
package com.brainz.wokhei.client.home;

import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.Colour;

/**
 * @author matteocantarelli
 *
 */
public class Validator {

	private static final int MAX_LOGONAME_LENGTH = 30;
	private static final int DESC_MAX_LENGTH = 250;
	private static final int DESC_MIN_LENGTH = 25;

	public enum DescriptionErrors
	{
		NONE,
		TOO_SHORT,
		TOO_LONG,
		EMPTY
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
	 * @param description
	 * @return
	 */
	public static DescriptionErrors validateDescription(String description)
	{
		String desc = description;

		if(desc.length() == 0 || desc.equals(Messages.LOGO_DESC_TXTBOX.getString()) || desc.equals(Messages.LOGO_DESC_TXTBOX_REVISION.getString()))
		{
			return DescriptionErrors.EMPTY;
		}
		if(desc.length() < DESC_MIN_LENGTH)
		{
			return DescriptionErrors.TOO_SHORT;
		}
		else if(desc.length() > DESC_MAX_LENGTH)
		{
			return DescriptionErrors.TOO_LONG;
		}

		return DescriptionErrors.NONE;
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
