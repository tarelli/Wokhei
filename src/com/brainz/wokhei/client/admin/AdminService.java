package com.brainz.wokhei.client.admin;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author latroiadigiovazza
 *
 */
@RemoteServiceRelativePath("adminservice")
public interface AdminService extends RemoteService {

	Boolean addAdmin(String adminEmail);
}