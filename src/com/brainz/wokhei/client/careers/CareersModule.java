/**
 * 
 */
package com.brainz.wokhei.client.careers;

import com.brainz.wokhei.client.common.AModule;
import com.brainz.wokhei.client.common.FooterModulePart;
import com.brainz.wokhei.client.common.HeaderModulePart;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class CareersModule extends AModule {


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		if(RootPanel.get("careers")!=null)
		{
			initModule();
		}

	}

	@Override
	public void loadModule() {
		//Create the module parts
		CareersModulePart indexModulePart = new CareersModulePart();
		FooterModulePart footerModulePart = new FooterModulePart();
		HeaderModulePart headerModulePart = new HeaderModulePart();


		//Initialize the module parts
		indexModulePart.initModulePart(this);
		footerModulePart.initModulePart(this);
		headerModulePart.initModulePart(this);

	}

}


