package com.brainz.wokhei.client.common;

import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Giovacchia
 *
 */
@RemoteServiceRelativePath("utilityservice")
public interface UtilityService extends RemoteService {

	Date getServerTimestamp();

	String getCurrentUsername();

}
