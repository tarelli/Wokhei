package com.brainz.wokhei.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Giovacchia
 *
 */

public interface UtilityServiceAsync extends UtilityService {

	void getServerTimestamp(AsyncCallback<Date> callback);

}
