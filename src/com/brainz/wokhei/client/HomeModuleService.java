/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.List;

import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author matteocantarelli
 *
 */
@RemoteServiceRelativePath("homemoduleservice")
public interface HomeModuleService extends RemoteService {

	OrderDTO getNextOrder(OrderDTO order);

	OrderDTO getPreviousOrder(OrderDTO order);

	OrderDTO getLatestOrder();

	Boolean submitOrder(String logoText, List<String> logoTags);

}
