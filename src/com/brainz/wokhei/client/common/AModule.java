/**
 * 
 */
package com.brainz.wokhei.client.common;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author matteocantarelli
 *
 */
public abstract class AModule implements EntryPoint {

	private LoginInfo _loginInfo = null;


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
				//the module will be loaded only after the response is received
				loadModule();

			}
		});
	}

	/**
	 * @return
	 */
	public LoginInfo getLoginInfo()
	{
		return _loginInfo;
	}

	public abstract void loadModule();

}
