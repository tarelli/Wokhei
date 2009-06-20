package com.brainz.wokhei.client;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Giovacchia
 *
 */

public interface UtilityServiceAsync {

	void getServerTimestamp(AsyncCallback<Date> result);
	void getCurrentUsername(AsyncCallback<String> result);
}