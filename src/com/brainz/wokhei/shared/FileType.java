/**
 * 
 */
package com.brainz.wokhei.shared;

/**
 * @author matteocantarelli
 *
 */
public enum FileType 
{
	PDF_VECTORIAL_LOGO("application/pdf"),
	PNG_LOGO("image/png"),
	PNG_LOGO_PRESENTATION("image/png"),
	;

	private String _contentType;

	FileType(String contentType)
	{
		_contentType=contentType;
	}

	public String getContentType()
	{
		return _contentType;
	}
}
