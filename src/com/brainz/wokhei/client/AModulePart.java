/**
 * 
 */
package com.brainz.wokhei.client;


/**
 * @author matteocantarelli
 *
 */
public abstract class AModulePart {

	protected HomeModuleServiceAsync _service;

	/**
	 * Init the panel 
	 */
	public void initModulePart(HomeModuleServiceAsync service)
	{
		_service=service;
	}



}
