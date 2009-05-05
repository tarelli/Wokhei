/**
 * 
 */
package com.brainz.wokhei.client;

import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author matteocantarelli
 *
 */
@RemoteServiceRelativePath("ordersbrowserservice")
public interface OrdersBrowserService extends RemoteService {

	OrderDTO getNextOrder(OrderDTO order);

	OrderDTO getPreviousOrder(OrderDTO order);

	OrderDTO getLatestOrder();

}
