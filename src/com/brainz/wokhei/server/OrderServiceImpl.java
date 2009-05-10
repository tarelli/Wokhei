/**
 * 
 */
package com.brainz.wokhei.server;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.OrderUtils;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.client.OrderService;
import com.brainz.wokhei.shared.OrderDTO;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author matteocantarelli --> piccolino il caghino
 *
 */
public class OrderServiceImpl extends RemoteServiceServlet implements OrderService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5060860293974160016L;

	private static final Logger log = Logger.getLogger(OrderServiceImpl.class.getName());

	private List<OrderDTO> _orders;

	public synchronized OrderDTO getLatestOrder() {
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
		_orders = OrderUtils.getOrderDTOList((List<Order>) query.execute(user));

		//return the shit
		return OrderUtils.getMostRecentOrder(_orders);
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.HomeModuleService#getNextOrder(com.brainz.wokhei.shared.OrderDTO)
	 */
	public OrderDTO getNextOrder(OrderDTO order) 
	{
		return OrderUtils.getNextOrder(_orders, order);
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.HomeModuleService#getPreviousOrder(com.brainz.wokhei.shared.OrderDTO)
	 */
	public OrderDTO getPreviousOrder(OrderDTO order) 
	{
		return OrderUtils.getPreviousOrder(_orders, order);
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.HomeModuleService#submitOrder(java.lang.String, java.util.List)
	 */
	public Boolean submitOrder(String logoText, List<String> logoTags) 
	{
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
