/**
 * 
 */
package com.brainz.wokhei.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import org.gwtwidgets.client.ui.pagination.Results;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.client.OrderService;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author matteocantarelli
 *
 */
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5060860293974160016L;

	private static final Logger log = Logger.getLogger(OrderServiceImpl.class.getName());

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.OrderService#submitOrder(com.brainz.wokhei.shared.OrderDTO)
	 */
	public Boolean submitOrder(OrderDTO orderDTO) 
	{
		Boolean returnValue;

		// retrieve user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		// Instantiate order
		Order order = new Order(user, orderDTO.getText(),Arrays.asList(orderDTO.getTags()),orderDTO.getColour(), new Date());

		if (user!= null)
		{

			order.setCustomer(user);

			PersistenceManager pm = PMF.get().getPersistenceManager();
			try {
				pm.makePersistent(order);

				returnValue = true;

				// log - this is fucked-up - if no user set there's something wrong
				log.info("order submitted by user " + order.getCustomer().getNickname() + ": " + order.getText() + " - " + order.getTags().toString() + " COLOUR:" + order.getColour().getName());
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

		return returnValue;
	}

	// un bel metodo per succhiare il cazzo all'amministratore e a tarelli frocio male
	@SuppressWarnings("unchecked")
	public Results getOrdersByUserAndStatus(Status status,
			String userEmail,int offset, int maxResult) {

		List<OrderDTO> orderList = null;
		User user = null;

		// create user given user email (if any - else we're gonna go all the way and get the whole bunch of users)
		if(userEmail != null)
		{
			user = new User(userEmail, "wokhei.com");
		}

		//prepare query
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String filters = "";

		//build filters
		if (status!=null)
		{
			if (filters!="")
			{
				filters += " && ";
			}

			filters += " ( ";

			//build status filter

			filters += " status == '" + status.toString() + "'";

			filters += " ) ";
		}

		if (user!=null)
		{
			if (filters!=null)
			{
				filters += " && ";
			}

			filters += " ( ";

			//build user filter
			filters += " customer == '" + user.getEmail() + "'";

			filters += " ) ";
		}

		Query query;

		// new query
		if(filters!="")
		{
			query = pm.newQuery(Order.class, filters);
		}
		else
		{
			query = pm.newQuery(Order.class);
		}

		try
		{
			orderList = OrderUtils.getOrderDTOList((List<Order>) query.execute());
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.toString());
		}
		finally
		{
			pm.close();
		}

		//TODO Fai una query che prende solo quelli che servono! --> stucazzo!
		List<OrderDTO> partialResult=new ArrayList<OrderDTO>();
		int maxNumber=Math.min(offset+maxResult,orderList.size());
		for(int i=offset;i<maxNumber;i++)
		{
			partialResult.add(orderList.get(i));
		}
		return new Results(orderList.size(),partialResult);
	}

	public Boolean setOrderStatus(long orderId, Status newStatus)
	{
		Boolean returnValue = false;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

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

			try {

				order.setStatus(newStatus);
				//persist change
				pm.makePersistent(order);

				returnValue = true;

				if (user != null) 
				{
					log.info("order [" + order.getId() + "] status change to " + newStatus.toString() + " from " + user.getNickname());
				} 
				else 
				{
					//should never happen!
					log.info("order [" + order.getId() + "] status changed to " + newStatus.toString() + " from NOBODY");
				}
			} 
			finally 
			{
				pm.close();
			}
		} 

		return returnValue;
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.OrderService#getOrdersForCurrentUser()
	 */
	public synchronized List<OrderDTO> getOrdersForCurrentUser() 
	{
		//get current user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		//prepare query
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String select_query = "select from " + Order.class.getName(); 
		Query query = pm.newQuery(select_query); 
		query.setFilter("customer == paramCustomer"); 
		query.declareParameters("java.lang.String paramCustomer"); 

		//execute
		List<OrderDTO> result= OrderUtils.getOrderDTOList((List<Order>) query.execute(user));

		pm.close();

		return result;
	}

}
