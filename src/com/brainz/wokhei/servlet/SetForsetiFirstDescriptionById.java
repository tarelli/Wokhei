package com.brainz.wokhei.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.AdminAuthenticator;
import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class SetForsetiFirstDescriptionById extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		Long orderId = Long.parseLong(req.getParameter("orderid"));

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		if(AdminAuthenticator.isAdmin(user))
		{
			// la porcata dell'anno
			List<String> desc = new ArrayList<String>();
			desc
					.add("Logo for Forseti Holdings LLC. "
							+ "Forseti is the old Norse god of justice and fairness. "
							+ "In icelandic, known as the presiding one, chairman or president.");

			PersistenceManager pm = PMF.get().getPersistenceManager();

			String select_query = "select from " + Order.class.getName();
			Query query = pm.newQuery(select_query);
			query.setFilter("id == paramId");
			query.declareParameters("java.lang.Long paramId");
			//execute
			List<Order> orders = (List<Order>) query.execute(orderId);

			if (!orders.isEmpty()) {
				//should be only one - safety check here
				Order order = orders.get(0);

				// set description
				order.setDescriptions(desc);
				pm.close();

				// will it work?
				res.sendRedirect("http://dotnetbutchering.blogspot.com/");
			}
		}


	}

}
