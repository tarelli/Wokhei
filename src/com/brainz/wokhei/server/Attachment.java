package com.brainz.wokhei.server;

/**
 * @author matteocantarelli
 *
 */
public class Attachment 
{

	private byte[] _attachmentData;

	private String _filename;

	private String _fileType;

	/**
	 * @param data
	 * @param _filename
	 * @param type
	 */
	public Attachment(byte[] data, String _filename, String type) 
	{
		super();
		_attachmentData = data;
		this._filename = _filename;
		_fileType = type;
	}

	public byte[] getData() {
		return _attachmentData;
	}

	public void set_attachmentData(byte[] data) {
		_attachmentData = data;
	}

	public String getFilename() {
		return _filename;
	}

	public void set_filename(String _filename) {
		this._filename = _filename;
	}

	public String getContentType() {
		return _fileType;
	}

	public void set_fileType(String type) {
		_fileType = type;
	}


}
