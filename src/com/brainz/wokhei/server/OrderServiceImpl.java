/**
 * 
 */
package com.brainz.wokhei.server;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.mail.Session;

import org.gwtwidgets.client.ui.pagination.Results;

import com.brainz.wokhei.File;
import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.client.OrderService;
import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.QueryBuilder;
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
			String userEmail, Date startDate, Date endDate,int offset, int maxResult) {

		//clean up filter and paramDeclaration for Query
		QueryBuilder._filters = "";
		QueryBuilder._paramDeclarations = "";

		List<OrderDTO> orderList = null;
		User user = null;

		// create user given user email (if any - else we're gonna go all the way and get the whole bunch of users)
		if(userEmail != null)
		{
			user = new User(userEmail, "@wokhei.com");
		}

		//prepare persistance manager for query
		PersistenceManager pm = PMF.get().getPersistenceManager();

		//init paramList
		List<Object> paramList = new ArrayList<Object>();

		//status
		if (status!=null)
		{	
			QueryBuilder.AddObjectToFilterAndParamForQuery(status, "==", "status", "paramStatus", paramList);
		}

		//User
		if (user!=null)
		{	
			QueryBuilder.AddObjectToFilterAndParamForQuery(user, "==", "customer", "paramUser", paramList);
		}

		//StartDate
		if (startDate!=null)
		{	
			QueryBuilder.AddObjectToFilterAndParamForQuery(startDate, ">=", "date", "paramStartDate", paramList);
		}

		//StartDate
		if (endDate!=null)
		{	
			QueryBuilder.AddObjectToFilterAndParamForQuery(endDate, "<=", "date", "paramEndDate", paramList);
		}

		Query query;
		try
		{
			// attenzione a questa cascata di porcate immonde
			if(user != null || status!=null || startDate!=null || endDate!=null)
			{
				// if any of these is not null then invoke execute method on query object with reflection
				query = pm.newQuery(Order.class, QueryBuilder._filters);
				query.declareParameters(QueryBuilder._paramDeclarations);

				// now prepare for reflection

				//get class
				Class cls = Class.forName("javax.jdo.Query");
				//get param types --> we need to pass in a single parameter of type Object[]
				Class parTypes[] = new Class[]{Object[].class};
				// get method
				Method meth = cls.getMethod("executeWithArray", parTypes);
				//put our argList in another argList (we need to pass an arglist with an array to the method)
				Object[] argList = new Object[]{paramList.toArray()};
				// invoke! invoke!
				List<Order> realOrderList = (List<Order>) meth.invoke(query, argList);
				// convert to OrderDTO
				orderList = OrderUtils.getOrderDTOList(realOrderList);
			}
			else
			{
				query = pm.newQuery(Order.class);
				orderList = OrderUtils.getOrderDTOList((List<Order>) query.execute());
			}

		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.toString());
		}
		finally
		{
			pm.close();
		}

		//TODO Fai una query che prende solo quelli che servono! --> stucazzo! --> misa che fa male senza cagare cazzi con cosa di range
		List<OrderDTO> partialResult=new ArrayList<OrderDTO>();
		int maxNumber=Math.min(offset+maxResult,orderList.size());
		for(int i=offset;i<maxNumber;i++)
		{
			partialResult.add(orderList.get(i));
		}
		return new Results(orderList.size(),partialResult);
	}

	public Long setOrderStatus(long orderId, Status newStatus)
	{
		Long returnValue = null;

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

				// if order is rejected or accepted sen email to user
				if(newStatus == Status.REJECTED || newStatus == Status.ACCEPTED)
				{
					//-----------------------------------------------------------------------------
					// send email to user
					Properties props = new Properties();
					Session sessionX = Session.getDefaultInstance(props, null);

					String msgBody = "";

					if(newStatus==Status.REJECTED)
					{
						//rejected message
						msgBody = Messages.EMAIL_ORDER_REJECTED.getString() + "/n" + order.getText() + "/n" + order.getTags().toString();
					}
					else
					{
						//accepted message
						msgBody = Messages.EMAIL_ORDER_ACCEPTED.getString() + "/n" + order.getText() + "/n" + order.getTags().toString();
					}

					List<String> recipients = new ArrayList<String>();
					recipients.add(order.getCustomer().getEmail());

					EmailSender.sendEmail("yourlogo@wokhei.com", recipients, "Order Status Notification - " + new Date().toString(), msgBody);
				}

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
		query.declareParameters("com.google.appengine.api.users.User paramCustomer"); 

		//execute
		List<OrderDTO> result= OrderUtils.getOrderDTOList((List<Order>) query.execute(user));

		pm.close();

		return result;
	}


	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.OrderService#hasFileUploaded(long, com.brainz.wokhei.shared.FilesToUpload)
	 */
	public synchronized Boolean hasFileUploaded(long orderID, FileType fileType) 
	{
		Boolean isFileUploaded=false;
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String fileSelectQuery = "select from " + File.class.getName();
		Query fileQuery = pm.newQuery(fileSelectQuery);
		fileQuery.setFilter("orderid == paramId  &&  type == paramType");
		fileQuery.declareParameters("java.lang.Long paramId , com.brainz.wokhei.shared.FileType paramType");
		//execute
		List<File> files = (List<File>) fileQuery.execute(orderID,fileType);
		//execute

		isFileUploaded=!files.isEmpty();
		return isFileUploaded;
	}

}
