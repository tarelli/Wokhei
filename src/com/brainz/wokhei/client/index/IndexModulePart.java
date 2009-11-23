/**
 * 
 */
package com.brainz.wokhei.client.index;

import pl.rmalinowski.gwt2swf.client.ui.SWFWidget;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class IndexModulePart extends AModulePart {


	private String _firstStepTitle = "";
	private String _secondStepTitle = "";
	private String _thirdStepTitle = "";
	private String _firstStep = "";
	private String _secondStep = "";
	private String _thirdStep = "";
	private String _fourthStepTitle = "";
	private String _fourthStep = "";

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
			HarnessStringsFromHTML();

			AbsolutePanel mainPanel= new AbsolutePanel();
			mainPanel.setSize("900px", "450px");

			final Button watchVideo=new Button(Messages.WATCH_A_VIDEO.getString());
			watchVideo.setText(Messages.WATCH_A_VIDEO.getString());

			Button loginWokhei=new Button();

			Label loginTip= new Label(Messages.LOGIN_TIP.getString());
			loginTip.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					Window.open("https://www.google.com/accounts/NewAccount", "_new", "");
				}});

			Label firstStep = new Label();
			Label secondStep = new Label();
			Label thirdStep = new Label();
			Label fourthStep = new Label();
			//			final Label screen = new Label();



			final SWFWidget swfScreen = new SWFWidget("./videos/screen.swf");

			final SWFWidget swfVideoWokhei = new SWFWidget("./videos/Wokhei.swf");

			Image the24hrs=new Image(Images.TWENTYFOURHRS.getImageURL());
			Image noObligation=new Image(Images.NOOBLIGATION.getImageURL());

			Label firstStepTitle = new Label(_firstStepTitle);
			Label secondStepTitle = new Label(_secondStepTitle);
			Label thirdStepTitle = new Label(_thirdStepTitle);
			Label fourthStepTitle = new Label(_fourthStepTitle);
			HTML firstStepText = new HTML(_firstStep);
			HTML secondStepText = new HTML(_secondStep);
			HTML thirdStepText = new HTML(_thirdStep);
			HTML fourthStepText = new HTML(_fourthStep);

			watchVideo.removeStyleName("gwt-Button");
			watchVideo.setStyleName("watchVideo");
			watchVideo.addStyleName("fontAR");
			watchVideo.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					if(swfScreen.isVisible())
					{
						watchVideo.setText(Messages.HIDE_VIDEO.getString());
						swfScreen.setVisible(false);
						swfVideoWokhei.setVisible(true);
					}
					else
					{
						watchVideo.setText(Messages.WATCH_A_VIDEO.getString());
						swfScreen.setVisible(true);
						swfVideoWokhei.setVisible(false);
					}
				}});

			loginTip.setStyleName("loginTip");


			loginWokhei.removeStyleName("gwt-Button");
			loginWokhei.setStyleName("login");
			loginWokhei.addStyleName("fontAR");
			loginWokhei.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					Window.open(getModule().getLoginInfo().getLoginUrl(), "_self", "");

				}});




			firstStep.setStyleName("hat");
			secondStep.setStyleName("clock");
			thirdStep.setStyleName("revision");
			fourthStep.setStyleName("ready");
			firstStepTitle.setStyleName("stepTitle");
			firstStepTitle.addStyleName("fontAR");
			secondStepTitle.setStyleName("stepTitle");
			secondStepTitle.addStyleName("fontAR");
			thirdStepTitle.setStyleName("stepTitle");
			thirdStepTitle.addStyleName("fontAR");
			fourthStepTitle.setStyleName("stepTitle");
			fourthStepTitle.addStyleName("fontAR");
			firstStepText.setStyleName("stepText");
			firstStepText.setWidth("200px");
			secondStepText.setStyleName("stepText");
			secondStepText.setWidth("200px");
			thirdStepText.setStyleName("stepText");
			thirdStepText.setWidth("200px");
			fourthStepText.setStyleName("stepText");
			fourthStepText.setWidth("200px");



			swfVideoWokhei.setWidth("260px");
			swfVideoWokhei.setHeight("237px");
			swfVideoWokhei.setStyleName("video");
			swfVideoWokhei.setVisible(false);

			swfScreen.setWidth("295px");
			swfScreen.setHeight("219px");
			swfScreen.setStyleName("videoScreen");
			swfScreen.setVisible(true);

			mainPanel.add(loginWokhei);
			mainPanel.add(swfScreen);
			mainPanel.add(loginTip,338,343);

			mainPanel.add(firstStep);
			mainPanel.add(secondStep);
			mainPanel.add(thirdStep);
			mainPanel.add(fourthStep);

			mainPanel.add(the24hrs,660,0);
			mainPanel.add(noObligation,332,360);

			mainPanel.add(firstStepTitle,85, 150);
			mainPanel.add(firstStepText,85, 170);

			mainPanel.add(secondStepTitle,85, 290);
			mainPanel.add(secondStepText,85, 310);

			mainPanel.add(thirdStepTitle,675, 150);
			mainPanel.add(thirdStepText,675, 170);

			mainPanel.add(fourthStepTitle,675, 290);
			mainPanel.add(fourthStepText,675, 310);

			mainPanel.add(swfVideoWokhei);

			RootPanel.get("indexBodyPart").add(mainPanel);

			Label copyright=new Label(Messages.INDEX_COPYRIGHT.getString());
			copyright.setStyleName("indexCopyright");
			RootPanel.get("indexHeaderPanel").add(copyright,770,20);
			RootPanel.get("indexHeaderPanel").add(new Image(Images.TOOLS.getImageURL()), 360, 0);
			RootPanel.get("indexHeaderPanel").add(watchVideo,620,15);

			applyCufon();
		}
	}

	// this method extracts static text from the underlying html page. 
	// this text will be injected in gwt widgets. 
	// We need it as static text for google to index it.
	private void HarnessStringsFromHTML() {
		//steps titles
		Element element = RootPanel.get("FIRST_STEP_TITLE").getElement();
		_firstStepTitle = element.getInnerHTML();
		element = RootPanel.get("SECOND_STEP_TITLE").getElement();
		_secondStepTitle = element.getInnerHTML();
		element = RootPanel.get("THIRD_STEP_TITLE").getElement();
		_thirdStepTitle = element.getInnerHTML();
		element = RootPanel.get("FOURTH_STEP_TITLE").getElement();
		_fourthStepTitle = element.getInnerHTML();

		// steps descriptions
		element = RootPanel.get("FIRST_STEP").getElement();
		_firstStep = element.getInnerHTML();
		element = RootPanel.get("SECOND_STEP").getElement();
		_secondStep = element.getInnerHTML();
		element = RootPanel.get("THIRD_STEP").getElement();
		_thirdStep = element.getInnerHTML();
		element = RootPanel.get("FOURTH_STEP").getElement();
		_fourthStep = element.getInnerHTML();
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart(OrderDTO selection) {	

	}

}
