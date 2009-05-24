/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.List;

import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author matteocantarelli
 *
 */
public interface OrderServiceAsync {

	void getOrdersForCurrentUser(AsyncCallback<List<OrderDTO>> callback);

	void submitOrder(OrderDTO orderDTO, AsyncCallback<Boolean> callback);

	void getOrdersByUserAndStatus(Status status, String userEmail, AsyncCallback<List<OrderDTO>> callback);

	void setOrderStatus(long orderId, Status newStatus, AsyncCallback<Boolean> callback);

}
