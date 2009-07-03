/**
 * 
 */
package com.brainz.wokhei.client.faq;

import com.google.gwt.core.client.EntryPoint;

/**
 * @author matteocantarelli
 *
 */
public class FaqModule implements EntryPoint {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		//Create the module parts
		FaqModulePart indexModulePart = new FaqModulePart();

		//Initialize the module parts
		indexModulePart.initModulePart();
	}

}


