/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.Date;
import java.util.List;

import org.gwtwidgets.client.ui.pagination.Results;

import com.brainz.wokhei.shared.FilesToUpload;
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

	void getOrdersByUserAndStatus(Status status, String userEmail, Date startDate, Date endDate, int offset, int maxResult, AsyncCallback<Results> callback);

	void setOrderStatus(long orderId, Status newStatus, AsyncCallback<Long> callback);

	void hasLogo(long orderID, FilesToUpload file, AsyncCallback<Boolean> callback);

}
