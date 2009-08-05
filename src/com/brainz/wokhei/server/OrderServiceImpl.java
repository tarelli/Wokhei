/**
 * 
 */
package com.brainz.wokhei.server;

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
import com.brainz.wokhei.Invoice;
import com.brainz.wokhei.Order;
import com.brainz.wokhei.PMF;
import com.brainz.wokhei.WokheiConfig;
import com.brainz.wokhei.client.common.OrderService;
import com.brainz.wokhei.resources.Mails;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.shared.InvoiceDTO;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.QueryBuilder;
import com.brainz.wokhei.shared.Status;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * @author matteocantarelli/GiovencaAustralis
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
		Integer newNumber=0; 

		// retrieve user
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String select_query = "select from " + Order.class.getName() + " order by progressive desc range 0,1";
		Query query = pm.newQuery(select_query); 

		List<Order> results = (List<Order>)query.execute();

		if(!results.isEmpty())
		{
			newNumber=results.get(0).getProgressive();
		}
		if(newNumber!=null)
		{
			newNumber++;
		}
		else
		{
			newNumber=0;
		}

		// Instantiate order
		Order order = new Order(user, orderDTO.getText(),Arrays.asList(orderDTO.getTags()),orderDTO.getColour(), new Date(), newNumber);

		if (user!= null)
		{
			order.setCustomer(user);

			try {
				pm.makePersistent(order);

				returnValue = true;

				//notify via email cool people
				List<String> recipients = new ArrayList<String>();
				recipients.add(Mails.GIOVANNI.getMailAddress());
				recipients.add(Mails.MATTEO.getMailAddress());
				recipients.add(Mails.SIMONE.getMailAddress());
				recipients.add(Mails.ADMIN.getMailAddress());
				//subject
				String subj = Messages.NOTIFY_SUBMITTED_SUBJ.getString() + order.getCustomer().getEmail() + "!";
				//msgbody
				String msgBody = Messages.NOTIFY_SUBMITTED_BODY.getString() + order.getCustomer().getEmail() + ":\n\n";
				msgBody += "Progressive: " + order.getProgressive() + "\n";
				msgBody += "OrderID: " + order.getId() + "\n";
				msgBody += "Text: " + order.getText() + "\n";
				msgBody += "TagZ: " + order.getTags().toString() + "\n";
				msgBody += "Colour: " + order.getColour().toString() + "\n";
				EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, subj, msgBody);

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
			String userEmail, Date startDate, Date endDate,int offset, int maxResult, boolean orderByDateDesc) {

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

		//add order by clause if input param says so
		//TODO: add range after order by to avoid problem in case of > 1000 orders
		String orderBy = "";
		if(orderByDateDesc)
		{
			orderBy += " ORDER BY date DESC ";
		}

		try
		{
			// attenzione a questa cascata di porcate immonde
			if(user != null || status!=null || startDate!=null || endDate!=null)
			{
				// if any of these is not null then invoke execute method on query object with reflection
				query = pm.newQuery("select from " + Order.class.getName() + orderBy );
				query.setFilter(QueryBuilder._filters);
				query.declareParameters(QueryBuilder._paramDeclarations);

				//execute query
				List<Order> realOrderList = (List<Order>) query.executeWithArray(paramList.toArray());

				// convert to OrderDTO
				orderList = OrderUtils.getOrderDTOList(realOrderList);
			}
			else
			{
				query = pm.newQuery("select from " + Order.class.getName() + orderBy );
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

		//TODO Fai una query che prende solo quelli che servono! --> stucazzo! 
		//--> misa che fa male senza cagare cazzi con cosa di getRange
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
				//set new status and accepted/viewed date
				order.setStatus(newStatus);
				if(newStatus == Status.ACCEPTED)
				{
					order.setAcceptedDate(new Date());
				}
				else if (newStatus == Status.VIEWED)
				{
					order.setViewedDate(new Date());
				}

				//persist change
				pm.makePersistent(order);

				returnValue = order.getId();

				// if order is rejected or accepted sen email to user
				if(newStatus == Status.REJECTED || newStatus == Status.ACCEPTED)
				{	
					//-----------------------------------------------------------------------------
					// send email to user
					Properties props = new Properties();
					Session sessionX = Session.getDefaultInstance(props, null);

					List<String> recipients = new ArrayList<String>();
					recipients.add(order.getCustomer().getEmail());

					String msgBodyHead = "";
					String msgBody = "";
					String msgFooter = "";

					if(newStatus==Status.REJECTED)
					{
						//rejected message
						msgBodyHead = Messages.EMAIL_ORDER_REJECTED.getString() + "\n\n";

						msgFooter = Messages.EMAIL_ORDER_REJECTED_FOOTER.getString();
					}
					else if (newStatus==Status.ACCEPTED)
					{
						//accepted message
						msgBodyHead = Messages.EMAIL_ORDER_ACCEPTED.getString() + "\n\n";
						msgFooter = Messages.EMAIL_ORDER_ACCEPTED_FOOTER.getString();
					}

					//common stuff
					//add updated orders to email
					msgBody+= 	"Order details: \n" + 
					"Text: " + order.getText() + "\n" + 
					"Tags: " + order.getTags().toString() + "\n" + 
					"Colour: " + order.getColour().toString() + 
					"\n\n";
					//msgFooter
					msgFooter+= "\n\n" + Messages.EMAIL_ORDER_GOODBYE.getString();

					EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, "Order Status Notification", msgBodyHead + msgBody + msgFooter);
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

	public synchronized Boolean setOrderKillswitch(boolean isOn)
	{
		// returnValue shows success of the setting operation
		Boolean returnValue = false;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try
		{

			String selectQuery = "select from " + WokheiConfig.class.getName();
			Query query = pm.newQuery(selectQuery);
			//execute
			List<WokheiConfig> configOptions = (List<WokheiConfig>) query.execute();

			if(configOptions.isEmpty())
			{
				// need to create it if not already there
				WokheiConfig config = new WokheiConfig(isOn, true);
				config.setSandBox(false);
				pm.makePersistent(config);
				log.log(Level.INFO, "Order Killswitch set for the first time: " + isOn);
			}
			else
			{
				WokheiConfig config = configOptions.get(0);
				config.setOrderKillswitch(isOn);
				log.log(Level.INFO, "Order Killswitch set: " + isOn);
			}

			returnValue = true;
		}
		catch(Exception e)
		{
			log.log(Level.SEVERE, e.toString());
		}
		finally
		{
			pm.close();
		}

		return returnValue;
	}

	public synchronized Boolean getOrderKillswitch()
	{
		// returnValue shows success of the setting operation
		Boolean returnValue = false;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try
		{

			String selectQuery = "select from " + WokheiConfig.class.getName();
			Query query = pm.newQuery(selectQuery);

			//execute
			List<WokheiConfig> configOptions = (List<WokheiConfig>) query.execute();

			if(configOptions.isEmpty())
			{
				// if there's nothing there then false (no-one ever set it)
				returnValue = false;
			}
			else
			{
				WokheiConfig config = configOptions.get(0);
				returnValue = config.getOrderKillswitch();
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

		return returnValue;
	}

	public InvoiceDTO attachInvoice(long orderID) {
		Integer newNumber=0; 

		//prepare query
		PersistenceManager pm = PMF.get().getPersistenceManager();

		String select_query_order = "select from " + Order.class.getName();
		Query queryOrder = pm.newQuery(select_query_order);
		queryOrder.setFilter("id == paramId");
		queryOrder.declareParameters("java.lang.Long paramId");
		//execute
		List<Order> orders = (List<Order>) queryOrder.execute(orderID);

		if(!orders.isEmpty())
		{
			String select_query = "select from " + Invoice.class.getName() + " order by invoiceNumber desc range 0,1";
			Query query = pm.newQuery(select_query); 

			List<Invoice> results = (List<Invoice>)query.execute();

			if(!results.isEmpty())
			{
				newNumber=results.get(0).getInvoiceNumber();
			}
			newNumber++;

			//TODO check if the user for that order and the current
			Invoice newInvoice=new Invoice(orders.get(0).getCustomer(),new Date(), newNumber, orderID);

			try {
				pm.makePersistent(newInvoice);

			} 
			catch(Exception ex)
			{
			}
			finally {
				pm.close();
			}

			return new InvoiceDTO(newInvoice.getId(),newInvoice.getDate(),newNumber,newInvoice.getCustomer().getEmail(),newInvoice.getCustomer().getNickname());
		}
		else
		{
			log.log(Level.SEVERE, "No order found with id "+orderID);
			return null;
		}
	}

	public Boolean sendEnquiry(OrderDTO order)
	{
		Boolean returnValue = false;

		try {
			//send enquiry
			String msgBody = Messages.ENQUIRY_EMAIL_BODY.getString() + "\n";
			msgBody += "Progressive ID: " + order.getProgressive().toString() + "\n";
			msgBody += "Order ID: " + order.getId().toString() + "\n";
			msgBody += "Text: " + order.getText().toString() + "\n";
			msgBody += "TagZ: " + Arrays.asList(order.getTags()).toString() + "\n";
			msgBody += "Colour: " + order.getColour().toString() + "\n\n";
			msgBody += "Let's make some money!";

			List<String> recipients = new ArrayList<String>();
			recipients.add(Mails.ADMIN.getMailAddress());
			recipients.add(Mails.GIOVANNI.getMailAddress());
			recipients.add(Mails.MATTEO.getMailAddress());

			EmailSender.sendEmail(Mails.YOURLOGO.getMailAddress(), recipients, order.getCustomerEmail() + Messages.ENQUIRY_EMAIL_SUBJECT.getString(), msgBody);
			returnValue = true;

			log.log(Level.INFO, order.getCustomerEmail() + " wants to buy an archived order - email sent!");
		} 
		catch(Exception ex)
		{
			log.log(Level.SEVERE, "Enquiry for archived logo: something went terribly wrong in the re-enactment of the re-enactment!");
		}

		return returnValue;
	}

}
