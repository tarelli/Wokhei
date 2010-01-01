package com.brainz.wokhei.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.brainz.wokhei.PMF;
import com.brainz.wokhei.WokheiConfig;
import com.brainz.wokhei.client.common.UtilityService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class UtilityServiceImpl extends RemoteServiceServlet implements UtilityService {

	private static final long serialVersionUID = 4038017158054767940L;

	private static final Logger log = Logger.getLogger(UtilityServiceImpl.class.getName());

	public Date getServerTimestamp() {
		Date timeStamp = new Date(); 
		return timeStamp;
	} 

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.UtilityService#getCurrentUsername()
	 */
	public String getCurrentUsername() 
	{
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		return user.getNickname();
	}

	public synchronized Boolean isSandBox()
	{
		// returnValue shows success of the setting operation
		Boolean returnValue = false;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try
		{

			String selectQuery = "select from " + WokheiConfig.class.getName();
			Query query = pm.newQuery(selectQuery);

			//execute
			List<WokheiConfig> configOptions = (List<WokheiConfig>) query.execute();

			if(configOptions.isEmpty())
			{
				// if there's nothing there then false (no-one ever set it)
				returnValue = false;
			}
			else
			{
				WokheiConfig config = configOptions.get(0);
				returnValue = config.isSandBox();
			}
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.toString());
		}
		finally
		{
			pm.close();
		}

		return returnValue;
	}

	public synchronized Boolean setSandBox(boolean isSandBox)
	{
		// returnValue shows success of the setting operation
		Boolean returnValue = false;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try
		{

			String selectQuery = "select from " + WokheiConfig.class.getName();
			Query query = pm.newQuery(selectQuery);
			//execute
			List<WokheiConfig> configOptions = (List<WokheiConfig>) query.execute();

			if(configOptions.isEmpty())
			{
				//is sandbox false by default
				boolean isOn = false;
				// need to create it if not already there
				WokheiConfig config = new WokheiConfig(isOn, isSandBox);
				pm.makePersistent(config);

				log.log(Level.INFO, "Sandbox set to: " + isOn);
			}
			else
			{
				WokheiConfig config = configOptions.get(0);
				config.setSandBox(isSandBox);
				log.log(Level.INFO, "Sandbox set to: " + isSandBox);
			}

			returnValue = true;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.toString());
		}
		finally
		{
			pm.close();
		}

		return returnValue;
	}

}
