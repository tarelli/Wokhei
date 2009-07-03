/**
 * 
 */
package com.brainz.wokhei.client.home;

import com.brainz.wokhei.client.common.FooterModulePart;
import com.brainz.wokhei.client.common.HeaderModulePart;
import com.brainz.wokhei.client.common.OrderService;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.client.common.UtilityService;
import com.brainz.wokhei.client.common.UtilityServiceAsync;
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
		UtilityServiceAsync utilityService = GWT.create(UtilityService.class);


		//Create the module parts
		HeaderModulePart headerModulePart = new HeaderModulePart();
		OrderSubmitterModulePart orderSubmitterModulePart = new OrderSubmitterModulePart();
		OrderBrowserModulePart orderBrowserModulePart = new OrderBrowserModulePart();
		FooterModulePart footerModulePart = new FooterModulePart();

		//Initialize the module parts
		orderSubmitterModulePart.addModulePartListener(orderBrowserModulePart);

		//Add the services used in each module part
		orderSubmitterModulePart.addService(Service.ORDER_SERVICE, orderService);
		orderBrowserModulePart.addService(Service.ORDER_SERVICE, orderService);
		headerModulePart.addService(Service.UTILITY_SERVICE,utilityService);

		orderBrowserModulePart.initModulePart();
		orderSubmitterModulePart.initModulePart();
		headerModulePart.initModulePart();
		footerModulePart.initModulePart();
	}

}


