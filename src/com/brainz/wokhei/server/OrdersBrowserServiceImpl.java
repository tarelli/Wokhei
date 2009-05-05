/**
 * 
 */
package com.brainz.wokhei.server;

import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.OrderUtils;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.client.OrdersBrowserService;
import com.brainz.wokhei.shared.OrderDTO;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author matteocantarelli
 *
 */
public class OrdersBrowserServiceImpl extends RemoteServiceServlet implements
OrdersBrowserService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5060860293974160016L;
	private List<OrderDTO> _orders;

	public OrderDTO getLatestOrder() {
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String select_query = "select from " + Order.class.getName(); 
		Query query = pm.newQuery(select_query); 
		query.setFilter("customer == paramCustomer"); 
		query.declareParameters("java.lang.String paramCustomer"); 
		_orders = OrderUtils.getOrderDTOList((List<Order>) query.execute(user));
		return OrderUtils.getMostRecentOrder(_orders);
	}

	public OrderDTO getNextOrder(OrderDTO order) {
		return OrderUtils.getNextOrder(_orders, order);
	}

	public OrderDTO getPreviousOrder(OrderDTO order) {
		return OrderUtils.getPreviousOrder(_orders, order);
	}

}
