/**
 * 
 */
package com.brainz.wokhei.client.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author matteocantarelli
 *
 */
public abstract class AModulePart {

	protected Map<Service, IServiceAsync> _services=new HashMap<Service, IServiceAsync>();
	private final List<AModulePart> _moduleParts=new ArrayList<AModulePart>();


	/**
	 * Init the panel 
	 */
	public void initModulePart()
	{
		loadModulePart();
	}

	/**
	 * 
	 */
	protected abstract void loadModulePart();

	/**
	 * @param service
	 * @return
	 */
	public IServiceAsync getService(Service service)
	{
		if(_services!=null)
		{
			if(_services.containsKey(service))
			{
				return _services.get(service);
			}
		}
		return null;
	}

	/**
	 * @param serviceType
	 * @param service
	 */
	public void addService(Service serviceType, IServiceAsync service)
	{
		_services.put(serviceType,service);
	}

	/**
	 * 
	 */
	public abstract void updateModulePart();

	/**
	 * 
	 */
	public static native void applyCufon() /*-{
	  $wnd.applyCufon();
	}-*/;

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
