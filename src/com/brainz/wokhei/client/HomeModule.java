/**
 * 
 */
package com.brainz.wokhei.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class HomeModule implements EntryPoint {

	private final HomeModuleServiceAsync _homeModuleService =GWT.create(HomeModuleService.class);

	private final OrderBrowserModulePart _orderBrowserModulePart = new OrderBrowserModulePart();

	private final OrderSubmitterModulePart _orderSubmitterModulePart = new OrderSubmitterModulePart();

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

		_orderBrowserModulePart.initModulePart(_homeModuleService);
		_orderSubmitterModulePart.initModulePart(_homeModuleService);

		RootPanel.get("ordersBrowser").add(_orderBrowserModulePart.getLablesPanel());
		RootPanel.get("ordersBrowserButtons").add(_orderBrowserModulePart.getButtonsPanel());
		RootPanel.get("ordersBrowserImage").add(_orderBrowserModulePart.getImageStatusPanel());

		RootPanel.get("orderSubmitter").add(_orderSubmitterModulePart.getOrderSubmitPanel());
		RootPanel.get("orderSubmitterAlternateBody").add(_orderSubmitterModulePart.getOrderSubmitAlternateBodyPanel());
		RootPanel.get("orderSubmitterAlternateBodytile").add(_orderSubmitterModulePart.getOrderSubmitAlternateBodytilePanel());
		RootPanel.get("orderSubmitterAlternateFooter").add(_orderSubmitterModulePart.getOrderSubmitAlternateFooterPanel());


	}


}


