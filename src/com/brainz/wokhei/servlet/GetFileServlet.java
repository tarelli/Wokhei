/**
 * 
 */
package com.brainz.wokhei.servlet;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.brainz.wokhei.Admin;
import com.brainz.wokhei.File;
import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.shared.FileType;
import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


/**
 * @author matteocantarelli
 *
 */
public class GetFileServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6003934788688102743L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException 
	{
		Long orderId = Long.parseLong(req.getParameter("orderid"));
		FileType fileType = FileType.valueOf(req.getParameter("fileType"));

		res.setContentType(fileType.getContentType());

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		PersistenceManager pm = PMF.get().getPersistenceManager();


		String select_query = "select from " + Order.class.getName();
		Query query = pm.newQuery(select_query);
		query.setFilter("id == paramId");
		query.declareParameters("java.lang.Long paramId");
		//execute
		List<Order> orders = (List<Order>) query.execute(orderId);

		String fileSelectQuery = "select from " + File.class.getName();
		Query fileQuery = pm.newQuery(fileSelectQuery);
		fileQuery.setFilter("orderid == paramId  &&  type == paramType");
		fileQuery.declareParameters("java.lang.Long paramId , com.brainz.wokhei.shared.FileType paramType");
		//execute
		List<File> files = (List<File>) fileQuery.execute(orderId,fileType);

		Boolean accessGranted=false;
		if (!files.isEmpty() && !orders.isEmpty()) {
			//should be only one - safety check here
			Order order = orders.get(0);
			if(order.getCustomer().equals(user))
			{
				accessGranted=true;
			}
			else 
			{
				String selectAdmin_query = "select from " + Admin.class.getName();
				Query adminQuery = pm.newQuery(selectAdmin_query);
				adminQuery.setFilter("administrator == paramUser");
				adminQuery.declareParameters("com.google.appengine.api.users.User paramUser");
				List<Admin> admins=(List<Admin>) adminQuery.execute(user);
				if(!admins.isEmpty())
				{
					accessGranted=true;
				}
			}

			if(accessGranted)
			{
				try 
				{
					Blob image=files.get(0).getFile();
					res.getOutputStream().write(image.getBytes());
				} 
				finally 
				{
					pm.close();
				}
			}

		} 

	}

}
