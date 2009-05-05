package com.brainz.wokhei.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")

public class SubmitOrderServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(SubmitOrderServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String text = req.getParameter("text");
		ArrayList<String> tags = (ArrayList<String>) Arrays.asList(req.getParameter("tags").split(" "));
		Date date = new Date();
		Order order = new Order(user, text, tags, date);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(order);

			if (user!= null)
			{
				log.info("order submitted by user " + user.getNickname() + ": " + text + " - " + tags.toString());
			}
			else
			{
				log.info("order submitted anonymously: " + tags.toString());
			}

		} finally {
			pm.close();
		}

		resp.sendRedirect("/home.jsp");
	}
}
