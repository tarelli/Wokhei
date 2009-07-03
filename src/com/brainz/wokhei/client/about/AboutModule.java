/**
 * 
 */
package com.brainz.wokhei.client.about;

import com.google.gwt.core.client.EntryPoint;

/**
 * @author matteocantarelli
 *
 */
public class AboutModule implements EntryPoint {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		//Create the module parts
		AboutModulePart indexModulePart = new AboutModulePart();

		//Initialize the module parts
		indexModulePart.initModulePart();
	}

}


