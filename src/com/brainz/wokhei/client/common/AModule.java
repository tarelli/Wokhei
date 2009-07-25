/**
 * 
 */
package com.brainz.wokhei.client.common;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author matteocantarelli
 *
 */
public abstract class AModule implements EntryPoint {

	private LoginInfo _loginInfo = null;


	private Date _timeStamp=null;


	private boolean _firstResponseReceived=false;

	/**
	 * 
	 */
	public void initModule() 
	{
		// Check login status using login service
		LoginServiceAsync loginService = GWT.create(LoginService.class);
		loginService.login(GWT.getHostPageBaseURL(), new AsyncCallback<LoginInfo>() {
			public void onFailure(Throwable error) {
			}

			public void onSuccess(LoginInfo result) {
				_loginInfo = result;
				responseReceived();
			}


		});

		UtilityServiceAsync utilityService =  GWT.create(UtilityService.class);
		utilityService.getServerTimestamp(new AsyncCallback<Date>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Date result) {
				_timeStamp=result;

				responseReceived();
			}});		
	}

	/**
	 * 
	 */
	private synchronized void responseReceived() 
	{
		if(_firstResponseReceived)
		{
			//the module will be loaded only after both response is received
			loadModule();
		}
		else
		{
			_firstResponseReceived=true;
		}
	}

	/**
	 * @return
	 */
	public LoginInfo getLoginInfo()
	{
		return _loginInfo;
	}

	/**
	 * @return the _timeStamp
	 */
	public Date getServerTimeStamp() {
		return _timeStamp;
	}
	public abstract void loadModule();

}
