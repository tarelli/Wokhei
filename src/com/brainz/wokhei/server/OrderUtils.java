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
		//descriptions
		String[] strArr = new String[order.getDescriptions().size()];
		System.arraycopy(order.getDescriptions().toArray(), 0, strArr, 0, order.getDescriptions().size());
		//revisionTip
		Float[] floatArr = new Float[order.getRevisionTip().size()];
		System.arraycopy(order.getRevisionTip().toArray(), 0, floatArr, 0, order.getRevisionTip().size());

		return new OrderDTO(order.getId(),order.getText(),strArr,order.getDate(),order.getAcceptedDate(),order.getReviewingDate(),order.getStatus(),order.getColour(), order.getCustomer().getEmail(),order.getProgressive(),order.getRevisionCounter(), order.getTip(), floatArr);
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
