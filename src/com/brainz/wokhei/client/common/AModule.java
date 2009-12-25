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

	protected LoginInfo _loginInfo = null;

	protected Date _timeStamp=null;

	protected boolean _isKillSwitch=false;

	protected int _responsesReceived=0;

	protected Boolean _isSandBox=false;

	private static final int DEFAULT_NUM_REQUESTS=4;

	/**
	 * 
	 */
	protected void initModule() 
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

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(Date result) {
				_timeStamp=result;

				responseReceived();
			}});

		utilityService.isSandBox(new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(Boolean result) {	
				_isSandBox=result;

				responseReceived();
			}});

		OrderServiceAsync orderService=GWT.create(OrderService.class);
		orderService.getOrderKillswitch(new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				_isKillSwitch = result;
				responseReceived();
			}

			public void onFailure(Throwable caught) {
				//TODO - do something in case of failure
			}
		}
		);
	}

	/**
	 * 
	 */
	protected synchronized void responseReceived() 
	{
		if(++_responsesReceived==getNumRequests())
		{
			//the module will be loaded only after all the responses are received
			loadModule();
		}
	}

	protected int getNumRequests() 
	{
		return DEFAULT_NUM_REQUESTS;
	}

	public Boolean isSandBox() {
		return _isSandBox;
	}

	public Boolean isKillSwitchOn() {
		return _isKillSwitch;
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
