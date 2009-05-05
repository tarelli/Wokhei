package com.brainz.wokhei.client;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SubmitOrderServiceAsync {

	void submitOrder(String logoText, List<String> logoTags, AsyncCallback<Boolean> callback);

}
