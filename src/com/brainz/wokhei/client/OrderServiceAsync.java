/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.List;

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

	void submitOrder(String logoText, List<String> logoTags, AsyncCallback<Boolean> callback);


}
