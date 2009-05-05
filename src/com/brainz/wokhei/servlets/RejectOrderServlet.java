package com.brainz.wokhei.servlets;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.shared.Status;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

@SuppressWarnings("serial")
public class RejectOrderServlet extends HttpServlet {
	private static final Logger log = Logger.getLogger(RejectOrderServlet.class.getName());

	@SuppressWarnings("unchecked")
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		Enumeration parmamEnum = req.getParameterNames();
		HashMap<String, String> map = new HashMap<String, String>();
		while (parmamEnum.hasMoreElements()) 
		{
			String name = (String) parmamEnum.nextElement();
			String value = req.getParameter(name);
			map.put(name, value);
		}

		PersistenceManager pm = PMF.get().getPersistenceManager();
		String select_query = "select from " + Order.class.getName();
		Query query = pm.newQuery(select_query);
		query.setFilter("id == paramId");
		query.declareParameters("java.lang.Long paramId");
		List<Order> orders = (List<Order>) query.execute(Long.valueOf(map.get("id")));

		if (!orders.isEmpty()) {
			Order order = orders.get(0);

			try {
				order.setStatus(Status.REJECTED);
				pm.makePersistent(order);

				if (user != null) 
				{
					log.info("order " + order.getId() + " rejected from " + user.getNickname());
				} 
				else 
				{
					log.info("order " + order.getId() + " rejected from nobody");
				}
			} 
			finally 
			{
				pm.close();
			}
		} 
		else 
		{
			log.info("Could not find the order " + Long.getLong(map.get("id")));
		}

		resp.sendRedirect("/admin.jsp");
	}
}
