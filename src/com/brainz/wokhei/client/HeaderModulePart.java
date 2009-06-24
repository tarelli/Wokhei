/**
 * 
 */
package com.brainz.wokhei.client;

import com.brainz.wokhei.resources.Images;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author matteocantarelli
 *
 */
public class HeaderModulePart extends AModulePart {


	private final VerticalPanel _mainPanel= new VerticalPanel();
	private final HorizontalPanel _innerPanel=new HorizontalPanel();

	private final Label _welcomeUserLbl = new Label();
	private AsyncCallback<String> _getUsernameCallback;

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#initModulePart(com.brainz.wokhei.client.OrderServiceAsync, com.brainz.wokhei.client.UtilityServiceAsync)
	 */
	@Override
	public void initModulePart(OrderServiceAsync orderService,
			UtilityServiceAsync utilityService, AdminServiceAsync adminService) 
	{
		if(RootPanel.get("headerPanel")!=null)
		{
			super.initModulePart(orderService, utilityService, adminService);

			hookUpCallbacks();
			utilityService.getCurrentUsername(_getUsernameCallback);
			_mainPanel.setWidth("482px");
			_mainPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
			_welcomeUserLbl.setStyleName("username");
			_innerPanel.setSpacing(5);
			_innerPanel.add(new Label("Hello"));
			_innerPanel.add(_welcomeUserLbl);
			_innerPanel.add(new Label("we reserved you the usual table"));
			_mainPanel.add(_innerPanel);
			RootPanel.get("headerPanel").add(_mainPanel,395,0);
			RootPanel.get("headerPanel").add(new Image(Images.BETA.getImageURL()), 222, 35);
		}
	}

	private void hookUpCallbacks() {
		// Set up the callback object
		_getUsernameCallback = new AsyncCallback<String>() {

			public void onSuccess(String username) {
				setWelcomeLabelMsg(username);
			}

			public void onFailure(Throwable caught) {
			}
		};

	}

	/**
	 * @param username
	 */
	private void setWelcomeLabelMsg(String username) 
	{		
		_welcomeUserLbl.setText(processUsername(username));
	}


	/**
	 * @param username
	 * @return
	 */
	private String processUsername(String username)
	{
		if(username.contains("@"))
			return (username.substring(0, username.indexOf('@')));
		else
			return username;
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() {

	}

}
