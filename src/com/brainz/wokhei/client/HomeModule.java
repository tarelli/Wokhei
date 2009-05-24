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
		OrderServiceAsync orderService = GWT.create(OrderService.class);

		//Create the module parts
		OrderSubmitterModulePart orderSubmitterModulePart = new OrderSubmitterModulePart();
		OrderBrowserModulePart orderBrowserModulePart = new OrderBrowserModulePart();

		//Initialize the module parts
		orderSubmitterModulePart.addModulePartListener(orderBrowserModulePart);

		orderBrowserModulePart.initModulePart(orderService);
		orderSubmitterModulePart.initModulePart(orderService);
	}

}


