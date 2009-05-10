/**
 * 
 */
package com.brainz.wokhei.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

/**
 * @author matteocantarelli
 *
 */
public class HomeModule implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		//Initialize Order Service
		HomeModuleServiceAsync homeModuleService = GWT.create(HomeModuleService.class);

		//Create the module parts
		OrderBrowserModulePart orderBrowserModulePart = new OrderBrowserModulePart();
		OrderSubmitterModulePart orderSubmitterModulePart = new OrderSubmitterModulePart();

		//Initialize the module parts
		orderSubmitterModulePart.addModulePartListener(orderBrowserModulePart);

		orderBrowserModulePart.initModulePart(homeModuleService);
		orderSubmitterModulePart.initModulePart(homeModuleService);
	}

}


