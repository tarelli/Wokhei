/**
 * 
 */
package com.brainz.wokhei.client.about;

import com.brainz.wokhei.client.common.AModule;
import com.brainz.wokhei.client.common.FooterModulePart;
import com.brainz.wokhei.client.common.HeaderModulePart;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class AboutModule extends AModule{


	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() 
	{
		if(RootPanel.get("about")!=null)
		{
			initModule();
		}
	}

	@Override
	public void loadModule() {
		//Create the module parts
		AboutModulePart aboutModulePart = new AboutModulePart();
		FooterModulePart footerModulePart = new FooterModulePart();
		HeaderModulePart headerModulePart = new HeaderModulePart();

		//Initialize the module parts
		aboutModulePart.initModulePart(this);
		footerModulePart.initModulePart(this);
		headerModulePart.initModulePart(this);
	}

}


