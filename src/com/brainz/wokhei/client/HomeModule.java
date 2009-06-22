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
		UtilityServiceAsync utilityService = GWT.create(UtilityService.class);
		AdminServiceAsync adminService = GWT.create(AdminService.class);

		//Create the module parts
		HeaderModulePart headerModulePart = new HeaderModulePart();
		OrderSubmitterModulePart orderSubmitterModulePart = new OrderSubmitterModulePart();
		OrderBrowserModulePart orderBrowserModulePart = new OrderBrowserModulePart();
		FooterModulePart footerModulePart = new FooterModulePart();

		//Initialize the module parts
		orderSubmitterModulePart.addModulePartListener(orderBrowserModulePart);

		orderBrowserModulePart.initModulePart(orderService, utilityService, adminService);
		orderSubmitterModulePart.initModulePart(orderService, utilityService, adminService);
		headerModulePart.initModulePart(orderService, utilityService, adminService);
		footerModulePart.initModulePart(orderService, utilityService, adminService);
	}

}


