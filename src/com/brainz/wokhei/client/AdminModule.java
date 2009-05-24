package com.brainz.wokhei.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;

public class AdminModule implements EntryPoint {

	@Override
	public void onModuleLoad() {
		//Initialize Order Service
		OrderServiceAsync orderService = GWT.create(OrderService.class);

		// declare module parts
		AdminOrderBrowserModulePart adminOrderBrowserModulePart = new AdminOrderBrowserModulePart();

		// init module parts
		adminOrderBrowserModulePart.initModulePart(orderService);
	}

}
