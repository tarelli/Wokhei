package com.brainz.wokhei.server;

import java.util.Date;
import java.util.logging.Logger;

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

		// log it
		log.info("timestamp retrieved from server: " + timeStamp.toString());

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

}
