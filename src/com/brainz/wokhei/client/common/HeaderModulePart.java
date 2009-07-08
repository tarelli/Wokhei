/**
 * 
 */
package com.brainz.wokhei.client.common;

import com.brainz.wokhei.client.admin.AdminModule;
import com.brainz.wokhei.resources.Images;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
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


	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("headerPanel")!=null)
		{
			_mainPanel.setWidth("482px");
			_mainPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);

			if(getModule().getLoginInfo().isLoggedIn())
			{
				if(getModule().getLoginInfo().getNickname()!=null)
				{
					setWelcomeLabelMsg(getModule().getLoginInfo().getNickname());
				}
				else
				{
					setWelcomeLabelMsg(getModule().getLoginInfo().getEmailAddress());
				}
				_welcomeUserLbl.setStyleName("username");
				_innerPanel.setSpacing(5);
				_innerPanel.add(new Label("Hello"));
				_innerPanel.add(_welcomeUserLbl);
				_innerPanel.add(new Label("we reserved you the usual table"));
				_mainPanel.add(_innerPanel);
				Anchor logout=new Anchor("Logout");
				logout.setHref(getModule().getLoginInfo().getLogoutUrl());
				logout.setStyleName("labelButton");
				logout.addStyleName("labelLink");
				logout.addStyleName("fontAR");
				_mainPanel.add(logout);

			}
			else
			{
				Anchor login=new Anchor("Login");
				login.setHref(getModule().getLoginInfo().getLoginUrl());
				login.setStyleName("labelButton");
				login.addStyleName("labelLink");
				login.addStyleName("fontAR");
				_mainPanel.add(login);
			}



			if(getModule().getLoginInfo().isLoggedIn())
			{
				if((getModule().getLoginInfo().getEmailAddress().equals("matteo.cantarelli@wokhei.com")) || (getModule().getLoginInfo().isAdmin()))
				{
					if(getModule() instanceof AdminModule)
					{
						Anchor home=new Anchor("Home");
						home.setHref("home.jsp");
						home.setStyleName("labelButton");
						home.addStyleName("labelLink");
						home.addStyleName("fontAR");
						_mainPanel.add(home);
					}
					else
					{
						Anchor admin=new Anchor("Admin");
						admin.setHref("admin.jsp");
						admin.setStyleName("labelButton");
						admin.addStyleName("labelLink");
						admin.addStyleName("fontAR");
						_mainPanel.add(admin);
					}

				}
			}

			Label wokheiHover=new Label();
			wokheiHover.setSize("230px","80px");
			wokheiHover.setStyleName("labelButton");
			wokheiHover.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					Window.open("index.jsp", "_self", "");

				}});


			RootPanel.get("headerPanel").add(_mainPanel,395,0);
			RootPanel.get("headerPanel").add(wokheiHover,10,10);

			RootPanel.get("headerPanel").add(new Image(Images.BETA.getImageURL()), 222, 35);
		}
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
