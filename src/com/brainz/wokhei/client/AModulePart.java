/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.ArrayList;
import java.util.List;


/**
 * @author matteocantarelli
 *
 */
public abstract class AModulePart {

	protected HomeModuleServiceAsync _service;
	private final List<AModulePart> _moduleParts=new ArrayList<AModulePart>();

	/**
	 * Init the panel 
	 */
	public void initModulePart(HomeModuleServiceAsync service)
	{
		_service=service;
	}


	/**
	 * 
	 */
	public abstract void updateModulePart();


	/**
	 * 
	 */
	public void notifyChanges()
	{
		for(AModulePart modulePart:_moduleParts)
		{
			modulePart.updateModulePart();
		}
	}

	/**
	 * @param modulePart
	 */
	public void addModulePartListener(AModulePart modulePart)
	{
		_moduleParts.add(modulePart);
	}

}
