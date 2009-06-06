package com.brainz.wokhei.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author mr.troia
 *
 */
public interface AdminServiceAsync {

	void addAdmin(String adminEmail, AsyncCallback<Boolean> callback);

}
