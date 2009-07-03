package com.brainz.wokhei.client.admin;

import com.brainz.wokhei.client.common.IServiceAsync;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author mr.troia
 *
 */
public interface AdminServiceAsync extends IServiceAsync {

	void addAdmin(String adminEmail, AsyncCallback<Boolean> callback);

}
