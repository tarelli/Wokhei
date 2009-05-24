/**
 * 
 */
package com.brainz.wokhei.server;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

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
	public List<OrderDTO> getOrdersByUserAndStatus(Status status,
			String userEmail) {

		List<OrderDTO> orderList = null;
		User user = null;

		// create user given user email (if any - else we're gonna go all the way and get the whole bunch of users)
		if(userEmail != null)
		{
			user = new User(userEmail, "wokhei.com");
		}

		//prepare query
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String select_query = "select from " + Order.class.getName(); 
		Query query = pm.newQuery(select_query); 

		try
		{
			//attenzione che qui ci sono porcate malefiche
			if(userEmail!= null && status !=null)
			{
				query.setFilter("customer == paramCustomer && status == paramStatus"); 
				query.declareParameters("java.lang.String paramCustomer");
				query.declareParameters("java.lang.String paramStatus");
				//execute
				orderList = OrderUtils.getOrderDTOList((List<Order>) query.execute(user, status));
			}
			else if(status != null)
			{
				query.setFilter("status == paramStatus");
				query.declareParameters("java.lang.String paramStatus");
				//execute
				orderList = OrderUtils.getOrderDTOList((List<Order>) query.execute(status));
			}
			else if(userEmail!= null)
			{
				query.setFilter("customer == paramCustomer"); 
				query.declareParameters("java.lang.String paramCustomer");
				//execute
				orderList = OrderUtils.getOrderDTOList((List<Order>) query.execute(user));
			}
			else
			{
				//execute
				orderList = OrderUtils.getOrderDTOList((List<Order>) query.execute());
			}
		}
		catch(Exception ex)
		{
			log.log(Level.SEVERE, ex.toString());
		}
		finally
		{
			pm.close();
		}

		return orderList;
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
	public List<OrderDTO> getOrdersForCurrentUser() 
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
