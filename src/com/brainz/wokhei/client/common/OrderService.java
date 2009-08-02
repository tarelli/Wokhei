/**
 * 
 */
package com.brainz.wokhei.client.common;

import java.util.Date;
import java.util.List;

import org.gwtwidgets.client.ui.pagination.Results;

import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.shared.InvoiceDTO;
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

	Results getOrdersByUserAndStatus(Status status, String userEmail, Date startDate, Date endDate, int offset, int maxResult, boolean orderByDate);

	InvoiceDTO attachInvoice(long orderID);

	List<OrderDTO> getOrdersForCurrentUser();

	Long setOrderStatus(long orderID, Status newStatus);

	Boolean hasFileUploaded(long orderID, FileType fileType); 

	Boolean setOrderKillswitch(boolean isOn);

	Boolean getOrderKillswitch();

	Boolean sendEnquiry(OrderDTO order);
}
