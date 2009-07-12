/**
 * 
 */
package com.brainz.wokhei.client.index;

import com.brainz.wokhei.client.common.AModule;
import com.brainz.wokhei.client.common.FooterModulePart;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class IndexModule extends AModule implements EntryPoint {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		if(RootPanel.get("index")!=null)
		{
			initModule();
		}
	}


	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModule#loadModule()
	 */
	@Override
	public void loadModule() 
	{
		if(getLoginInfo().isLoggedIn()) {
			//redirect to home
			Window.open(GWT.getHostPageBaseURL()+"home.html", "_self", "");
		} else {
			//Create the module parts
			IndexModulePart indexModulePart = new IndexModulePart();
			FooterModulePart footerModulePart = new FooterModulePart();

			//Initialize the module parts
			indexModulePart.initModulePart(this);
			footerModulePart.initModulePart(this);
		}
	}



}


