package com.brainz.wokhei.client.common;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * @author Giovacchia
 *
 */

public interface UtilityServiceAsync extends IServiceAsync{

	void getServerTimestamp(AsyncCallback<Date> result);
	void getCurrentUsername(AsyncCallback<String> result);
}