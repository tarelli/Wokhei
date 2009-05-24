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

	void getNextOrder(OrderDTO order, AsyncCallback<OrderDTO> callback);

	void getPreviousOrder(OrderDTO order,AsyncCallback<OrderDTO> callback);

	void getLatestOrder(AsyncCallback<OrderDTO> callback);

	void submitOrder(OrderDTO orderDTO, AsyncCallback<Boolean> callback);

	void getOrdersByUserAndStatus(Status status, String userEmail, AsyncCallback<List<OrderDTO>> callback);

	void rejectOrder(long orderId, AsyncCallback<Boolean> callback);

}
