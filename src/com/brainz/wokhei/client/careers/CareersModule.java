/**
 * 
 */
package com.brainz.wokhei.client.careers;

import com.google.gwt.core.client.EntryPoint;

/**
 * @author matteocantarelli
 *
 */
public class CareersModule implements EntryPoint {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		//Create the module parts
		CareersModulePart indexModulePart = new CareersModulePart();

		//Initialize the module parts
		indexModulePart.initModulePart();
	}

}


