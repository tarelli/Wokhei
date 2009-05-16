/**
 * 
 */
package com.brainz.wokhei.client;

import com.brainz.wokhei.shared.OrderDTO;
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


}
