package com.brainz.wokhei.servlets;

import java.io.IOException;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.Admin;
import com.brainz.wokhei.PMF;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")

public class AddAdminServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(AddAdminServlet.class.getName());

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {

		UserService userService = UserServiceFactory.getUserService();
		User currentUser = userService.getCurrentUser();

		String newAdminEmail = req.getParameter("email");

		User user = new User(newAdminEmail, "gmail.com");

		Admin admin = new Admin(user);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			pm.makePersistent(admin);

			if (user!= null)
			{
				log.info("Admin added by " + currentUser.getNickname() + ": " + newAdminEmail);
			}
			else
			{
				log.info("Admin added anonymously (WTF - shouldn't happen!): " + newAdminEmail);
			}

		} finally {
			pm.close();
		}

		resp.sendRedirect("/admin.jsp");
	}
}

