/**
 * 
 */
package com.brainz.wokhei.client.faq;

import com.brainz.wokhei.client.common.AModule;
import com.brainz.wokhei.client.common.FooterModulePart;
import com.brainz.wokhei.client.common.HeaderModulePart;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class FaqModule extends AModule {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		if(RootPanel.get("faq")!=null)
		{
			initModule();
		}

	}

	@Override
	public void loadModule() {
		//Create the module parts
		FaqModulePart indexModulePart = new FaqModulePart();
		FooterModulePart footerModulePart = new FooterModulePart();
		HeaderModulePart headerModulePart = new HeaderModulePart();

		//Initialize the module parts
		indexModulePart.initModulePart(this);
		footerModulePart.initModulePart(this);
		headerModulePart.initModulePart(this);
	}

}


