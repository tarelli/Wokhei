package com.brainz.wokhei.client.admin;

import com.brainz.wokhei.client.common.OrderService;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.client.common.UtilityService;
import com.brainz.wokhei.client.common.UtilityServiceAsync;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class AdminModule implements EntryPoint {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		// Initialize Order Service
		OrderServiceAsync orderService = GWT.create(OrderService.class);
		UtilityServiceAsync utilityService = GWT.create(UtilityService.class);
		AdminServiceAsync adminService = GWT.create(AdminService.class);

		// declare module parts
		AdminOrderBrowserModulePart adminOrderBrowserModulePart = new AdminOrderBrowserModulePart();

		// add required services to the module part
		adminOrderBrowserModulePart.addService(Service.ADMIN_SERVICE, adminService);
		adminOrderBrowserModulePart.addService(Service.ORDER_SERVICE, orderService);
		adminOrderBrowserModulePart.addService(Service.UTILITY_SERVICE,utilityService);

		// init module parts
		adminOrderBrowserModulePart.initModulePart();
	}

}
