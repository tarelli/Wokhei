package com.brainz.wokhei.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.client.SubmitOrderService;
import com.brainz.wokhei.servlets.SubmitOrderServlet;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SubmitOrderServiceImpl extends RemoteServiceServlet implements SubmitOrderService {

	private static final long serialVersionUID = 1L;
	private static final Logger log = Logger.getLogger(SubmitOrderServlet.class.getName());

	public Boolean submitOrder(String logoText, List<String> logoTags) {
		Boolean returnValue;

		// retrieve user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// Instantiate order
		Order order = new Order(user, logoText, logoTags, new Date());

		if (user!= null)
		{

			order.setCustomer(user);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(order);

				returnValue = true;

				// log - this is fucked-up - if no user set there's something wrong
				log.info("order submitted by user " + order.getCustomer().getNickname() + ": " + order.getText() + " - " + order.getTags().toString());
			} 
			catch(Exception ex)
			{
				returnValue = false;
				log.log(Level.SEVERE, ex.toString());
			}
			finally {
				pm.close();
			}
		}
		else
			returnValue = false;

		//TODO: some kind of response.redirect here? Maybe not

		return returnValue;
	}

}
