package com.brainz.wokhei.client.admin;

import com.brainz.wokhei.client.common.AModule;
import com.brainz.wokhei.client.common.FooterModulePart;
import com.brainz.wokhei.client.common.HeaderModulePart;
import com.brainz.wokhei.client.common.OrderService;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.client.common.UtilityService;
import com.brainz.wokhei.client.common.UtilityServiceAsync;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

public class AdminModule extends AModule {

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		if(RootPanel.get("admin")!=null)
		{
			initModule();
		}
	}

	@Override
	public void loadModule() {
		// Initialize Order Service
		OrderServiceAsync orderService = GWT.create(OrderService.class);
		UtilityServiceAsync utilityService = GWT.create(UtilityService.class);
		AdminServiceAsync adminService = GWT.create(AdminService.class);

		// declare module parts
		AdminOrderBrowserModulePart adminOrderBrowserModulePart = new AdminOrderBrowserModulePart();
		FooterModulePart footerModulePart = new FooterModulePart();
		HeaderModulePart headerModulePart = new HeaderModulePart();

		// add required services to the module part
		adminOrderBrowserModulePart.addService(Service.ADMIN_SERVICE, adminService);
		adminOrderBrowserModulePart.addService(Service.ORDER_SERVICE, orderService);
		adminOrderBrowserModulePart.addService(Service.UTILITY_SERVICE,utilityService);

		// init module parts
		footerModulePart.initModulePart(this);
		headerModulePart.initModulePart(this);
		adminOrderBrowserModulePart.initModulePart(this);
	}

}
