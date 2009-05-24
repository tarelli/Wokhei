/**
 * 
 */
package com.brainz.wokhei.shared;

import java.util.List;


/**
 * @author matteocantarelli
 *
 */
public class OrderDTOUtils {


	/**
	 * @param orders
	 * @return the most recent Order
	 */
	public static OrderDTO getMostRecentOrder(List<OrderDTO> orders)
	{
		OrderDTO mostRecentOrder=null;
		for (OrderDTO o : orders) 
		{
			if((mostRecentOrder==null)||(mostRecentOrder.getDate().before(o.getDate())))
			{
				mostRecentOrder=o;
			}
		}
		return mostRecentOrder;
	}

	/**
	 * @param orders
	 * @param refOrder
	 * @return the order previous refOrder
	 */
	public static OrderDTO getPreviousOrder(List<OrderDTO> orders, OrderDTO refOrder)
	{
		OrderDTO previousOrder=refOrder;
		for (OrderDTO o : orders) 
		{
			if
			(
					(previousOrder==refOrder && (o.getDate().before(refOrder.getDate())) )
					||
					(
							(o.getDate().before(refOrder.getDate()))
							&&
							(o.getDate().after(previousOrder.getDate()))
					)
			)
			{
				previousOrder=o;
			}
		}
		return previousOrder;
	}

	/**
	 * @param orders
	 * @param refOrder
	 * @return the order previous refOrder
	 */
	public static OrderDTO getNextOrder(List<OrderDTO> orders, OrderDTO refOrder)
	{
		OrderDTO nextOrder=refOrder;
		for (OrderDTO o : orders) 
		{
			if
			(
					(nextOrder==refOrder && (o.getDate().after(refOrder.getDate())) )
					||
					(
							(o.getDate().after(refOrder.getDate()))
							&&
							(o.getDate().before(nextOrder.getDate()))
					)
			)
			{
				nextOrder=o;
			}
		}
		return nextOrder;
	}




}
