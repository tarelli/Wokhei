/**
 * 
 */
package com.brainz.wokhei.client.index;

import pl.rmalinowski.gwt2swf.client.ui.SWFWidget;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class IndexModulePart extends AModulePart {

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("indexBodyPart")!=null)
		{
			AbsolutePanel mainPanel= new AbsolutePanel();
			mainPanel.setSize("900px", "600px");

			Button watchVideo=new Button(Messages.WATCH_A_VIDEO.getString());
			Button login=new Button(Messages.LOGIN.getString());
			Label firstStep = new Label();
			Label secondStep = new Label();
			Label thirdStep = new Label();
			Label twitter=new Label();

			login.removeStyleName("gwt-Button");
			login.setStyleName("login");
			login.addStyleName("fontAR");

			watchVideo.removeStyleName("gwt-Button");
			watchVideo.setStyleName("watchVideo");
			watchVideo.addStyleName("fontAR");

			firstStep.setStyleName("hat");
			secondStep.setStyleName("clock");
			thirdStep.setStyleName("ready");
			twitter.setStyleName("twitter");

			SWFWidget swfWidget = new SWFWidget("./videos/Wokhei.swf");
			swfWidget.setWidth("312px");
			swfWidget.setHeight("277px");
			swfWidget.setStyleName("video");

			mainPanel.add(login);
			mainPanel.add(twitter);
			mainPanel.add(watchVideo);
			mainPanel.add(firstStep);
			mainPanel.add(secondStep);
			mainPanel.add(thirdStep);
			mainPanel.add(swfWidget);

			RootPanel.get("indexBodyPart").add(mainPanel);
			RootPanel.get("indexHeaderPanel").add(new Image(Images.BETA.getImageURL()), 222, 35);

			applyCufon();

		}
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() {	

	}

}
