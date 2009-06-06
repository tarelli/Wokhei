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

	protected AdminServiceAsync _adminService;
	protected OrderServiceAsync _orderService;
	protected UtilityServiceAsync _utlityService;
	private final List<AModulePart> _moduleParts=new ArrayList<AModulePart>();


	/**
	 * Init the panel 
	 */
	public void initModulePart(OrderServiceAsync orderService, UtilityServiceAsync utilityService, AdminServiceAsync adminService)
	{
		_orderService=orderService;
		_adminService = adminService;
		_utlityService=utilityService;
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
