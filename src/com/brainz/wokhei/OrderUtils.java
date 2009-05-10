/**
 * 
 */
package com.brainz.wokhei;

import java.util.ArrayList;
import java.util.List;

import com.brainz.wokhei.shared.OrderDTO;


/**
 * @author matteocantarelli
 *
 */
public class OrderUtils {


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


	/**
	 * @param order
	 * @return
	 */
	public static OrderDTO getOrderDTO(Order order) {
		String[] strArr = new String[order.getTags().size()];
		System.arraycopy(order.getTags().toArray(), 0, strArr, 0, order.getTags().size());
		return new OrderDTO(order.getId(),order.getText(),strArr,order.getDate(),order.getStatus(), order.getCustomer().getEmail());
	}

	public static List<OrderDTO> getOrderDTOList(List<Order> orders)
	{
		List<OrderDTO> ordersDTO=new ArrayList<OrderDTO>();

		if(orders != null)
		{
			for(Order o:orders)
			{
				ordersDTO.add(getOrderDTO(o));
			}
		}

		return ordersDTO;
	}

}
