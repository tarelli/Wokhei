/**
 * 
 */
package com.brainz.wokhei.client.index;

import com.google.gwt.core.client.EntryPoint;

/**
 * @author matteocantarelli
 *
 */
public class IndexModule implements EntryPoint {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		//Create the module parts
		IndexModulePart indexModulePart = new IndexModulePart();

		//Initialize the module parts
		indexModulePart.initModulePart();
	}

}


