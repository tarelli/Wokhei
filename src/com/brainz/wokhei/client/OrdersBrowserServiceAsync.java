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
public interface OrdersBrowserServiceAsync {

	void getNextOrder(OrderDTO order, AsyncCallback<OrderDTO> callback);

	void getPreviousOrder(OrderDTO order,AsyncCallback<OrderDTO> callback);

	void getLatestOrder(AsyncCallback<OrderDTO> callback);

}
