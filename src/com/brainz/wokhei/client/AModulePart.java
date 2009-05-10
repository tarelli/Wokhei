/**
 * 
 */
package com.brainz.wokhei.client;


/**
 * @author matteocantarelli
 *
 */
public abstract class AModulePart {

	protected OrderServiceAsync _service;

	/**
	 * Init the panel 
	 */
	public void initModulePart(OrderServiceAsync service)
	{
		_service=service;
	}



}
