/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.Date;
import java.util.List;

import org.gwtwidgets.client.ui.pagination.Results;

import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author matteocantarelli & latroiadigiovazza
 *
 */
@RemoteServiceRelativePath("orderservice")
public interface OrderService extends RemoteService {

	Boolean submitOrder(OrderDTO orderDTO);

	Results getOrdersByUserAndStatus(Status status, String userEmail, Date startDate, Date endDate, int offset, int maxResult);

	List<OrderDTO> getOrdersForCurrentUser();

	Boolean setOrderStatus(long orderID, Status newStatus);
}
