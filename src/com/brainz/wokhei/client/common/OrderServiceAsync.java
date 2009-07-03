/**
 * 
 */
package com.brainz.wokhei.client.common;

import java.util.Date;
import java.util.List;

import org.gwtwidgets.client.ui.pagination.Results;

import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author matteocantarelli
 *
 */
public interface OrderServiceAsync extends IServiceAsync {

	void getOrdersForCurrentUser(AsyncCallback<List<OrderDTO>> callback);

	void submitOrder(OrderDTO orderDTO, AsyncCallback<Boolean> callback);

	void getOrdersByUserAndStatus(Status status, String userEmail, Date startDate, Date endDate, int offset, int maxResult, AsyncCallback<Results> callback);

	void setOrderStatus(long orderId, Status newStatus, AsyncCallback<Long> callback);

	void hasFileUploaded(long orderID, FileType fileType, AsyncCallback<Boolean> callback);

	void setOrderKillswitch(boolean isOn, AsyncCallback<Boolean> callback);

	void getOrderKillswitch(AsyncCallback<Boolean> callback);

}
