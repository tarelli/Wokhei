package com.brainz.wokhei.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class AdminModule implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		//Initialize Order Service
		OrderServiceAsync orderService = GWT.create(OrderService.class);
		UtilityServiceAsync utilityService = GWT.create(UtilityService.class);
		AdminServiceAsync adminService = GWT.create(AdminService.class);

		// declare module parts
		AdminOrderBrowserModulePart adminOrderBrowserModulePart = new AdminOrderBrowserModulePart();

		// init module parts
		adminOrderBrowserModulePart.initModulePart(orderService, utilityService, adminService);
	}

}
