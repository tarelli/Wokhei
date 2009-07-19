/**
 * 
 */
package com.brainz.wokhei.server;

import java.util.ArrayList;
import java.util.List;

import com.brainz.wokhei.Order;
import com.brainz.wokhei.shared.OrderDTO;

/**
 * @author matteocantarelli
 *
 */
public class OrderUtils {

	/**
	 * @param order
	 * @return
	 */
	public static OrderDTO getOrderDTO(Order order) 
	{
		String[] strArr = new String[order.getTags().size()];
		System.arraycopy(order.getTags().toArray(), 0, strArr, 0, order.getTags().size());
		return new OrderDTO(order.getId(),order.getText(),strArr,order.getDate(),order.getAcceptedDate(),order.getStatus(),order.getColour(), order.getCustomer().getEmail());
	}

	/**
	 * @param orders
	 * @return
	 */
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
