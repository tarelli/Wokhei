package com.brainz.wokhei.client;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("submitorderservice")
public interface SubmitOrderService extends RemoteService {
	public Boolean submitOrder(String logoText, List<String> logoTags);
}
