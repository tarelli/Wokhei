package com.brainz.wokhei.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.brainz.wokhei.Admin;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.client.AdminService;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author matteocantarelli
 *
 */
public class AdminServiceImpl extends RemoteServiceServlet implements AdminService {

	private static final Logger log = Logger.getLogger(AdminServiceImpl.class.getName());

	@Override
	public Boolean addAdmin(String adminEmail) {

		boolean returnValue;

		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		User user = new User(adminEmail, "wokhei.com");

		Admin admin = new Admin(user);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(admin);

			returnValue = true;

			if (user!= null)
			{
				log.info("Admin added by " + currentUser.getNickname() + ": " + adminEmail);
			}
			else
			{
				log.info("Admin added anonymously (WTF - shouldn't happen!): " + adminEmail);
			}

		} 
		catch(Exception ex)
		{
			returnValue = false;
			log.log(Level.SEVERE, "Error: " + ex.getMessage());
		}
		finally {
			pm.close();
		}

		return returnValue;
	}

}
