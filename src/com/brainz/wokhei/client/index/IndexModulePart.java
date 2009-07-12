/**
 * 
 */
package com.brainz.wokhei.client.index;

import pl.rmalinowski.gwt2swf.client.ui.SWFWidget;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
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
		loadIndex();


	}

	private void loadIndex() {
		if(RootPanel.get("indexBodyPart")!=null)
		{
			AbsolutePanel mainPanel= new AbsolutePanel();
			mainPanel.setSize("900px", "450px");

			final Button watchVideo=new Button(Messages.WATCH_A_VIDEO.getString());

			Button loginWokhei=new Button(Messages.LOGIN.getString());
			Label loginTip= new Label(Messages.LOGIN_TIP.getString());
			loginTip.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					Window.open("https://www.google.com/accounts/NewAccount", "_new", "");
				}});

			Label theBigPurple = new Label(Messages.THE_BIG_PURPLE.getString());
			Label cloud = new Label();
			Label firstStep = new Label();
			Label secondStep = new Label();
			Label thirdStep = new Label();
			final Label screen = new Label();
			final SWFWidget swfWidget = new SWFWidget("./videos/Wokhei.swf");
			final Label screenLogos=new Label();
			final Label logos = new Label();
			Label firstStepTitle = new Label(Messages.FIRST_STEP_TITLE.getString());
			Label secondStepTitle = new Label(Messages.SECOND_STEP_TITLE.getString());
			Label thirdStepTitle = new Label(Messages.THIRD_STEP_TITLE.getString());
			Label firstStepText = new Label(Messages.FIRST_STEP.getString());
			Label secondStepText = new Label(Messages.SECOND_STEP.getString());
			Label thirdStepText = new Label(Messages.THIRD_STEP.getString());


			watchVideo.removeStyleName("gwt-Button");
			watchVideo.setStyleName("login");
			watchVideo.addStyleName("fontAR");
			watchVideo.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					if(screenLogos.isVisible())
					{
						watchVideo.setText(Messages.HIDE_VIDEO.getString());
						screen.setVisible(true);
						swfWidget.setVisible(true);
						screenLogos.setVisible(false);
						logos.setVisible(false);
					}
					else
					{
						watchVideo.setText(Messages.WATCH_A_VIDEO.getString());
						screen.setVisible(false);
						swfWidget.setVisible(false);
						screenLogos.setVisible(true);
						logos.setVisible(true);
					}
				}});

			loginTip.setStyleName("loginTip");

			theBigPurple.setStyleName("bigPurple");
			theBigPurple.addStyleName("fontAR");
			theBigPurple.setWidth("220px");

			loginWokhei.removeStyleName("gwt-Button");
			loginWokhei.setStyleName("watchVideo");
			loginWokhei.addStyleName("fontAR");
			loginWokhei.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					Window.open(getModule().getLoginInfo().getLoginUrl(), "_self", "");

				}});

			cloud.setStyleName("cloud");
			cloud.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					Window.open(GWT.getHostPageBaseURL()+"careers.html", "_self", "");

				}});

			screen.setStyleName("screen");
			screenLogos.setStyleName("screenLogos");
			logos.setStyleName("logos");


			firstStep.setStyleName("hat");
			secondStep.setStyleName("clock");
			thirdStep.setStyleName("ready");
			firstStepTitle.setStyleName("stepTitle");
			firstStepTitle.addStyleName("fontAR");
			secondStepTitle.setStyleName("stepTitle");
			secondStepTitle.addStyleName("fontAR");
			thirdStepTitle.setStyleName("stepTitle");
			thirdStepTitle.addStyleName("fontAR");
			firstStepText.setStyleName("stepText");
			firstStepText.setWidth("180px");
			secondStepText.setStyleName("stepText");
			secondStepText.setWidth("180px");
			thirdStepText.setStyleName("stepText");
			thirdStepText.setWidth("230px");



			swfWidget.setWidth("260px");
			swfWidget.setHeight("237px");
			swfWidget.setStyleName("video");
			swfWidget.setVisible(false);
			screen.setVisible(false);

			mainPanel.add(loginWokhei);
			mainPanel.add(cloud);
			mainPanel.add(screen);
			mainPanel.add(screenLogos);
			mainPanel.add(logos);
			mainPanel.add(loginTip,502,226);

			mainPanel.add(firstStep);
			mainPanel.add(secondStep);
			mainPanel.add(thirdStep);
			mainPanel.add(theBigPurple, 420,0);
			mainPanel.add(firstStepTitle,80, 280);
			mainPanel.add(secondStepTitle,360, 280);
			mainPanel.add(thirdStepTitle,620, 280);
			mainPanel.add(firstStepText,80, 310);
			mainPanel.add(secondStepText,360, 310);
			mainPanel.add(thirdStepText,620, 310);

			mainPanel.add(swfWidget);

			RootPanel.get("indexBodyPart").add(mainPanel);

			Label copyright=new Label(Messages.INDEX_COPYRIGHT.getString());
			copyright.setStyleName("indexCopyright");
			RootPanel.get("indexHeaderPanel").add(copyright,770,10);
			RootPanel.get("indexHeaderPanel").add(new Image(Images.BETA.getImageURL()), 222, 35);
			RootPanel.get("indexHeaderPanel").add(watchVideo,620,5);

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
