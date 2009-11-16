package com.brainz.wokhei.client.common;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



public class FooterModulePart extends AModulePart {


	//footer stuff
	private final Label _footerAbout = new Label(" About ");
	private final Label _footerFaqs = new Label(" FAQs ");
	private final Label _footerCareers = new Label(" Careers ");

	private final VerticalPanel _mainPanel = new VerticalPanel();
	private final HorizontalPanel _footerPanel=new HorizontalPanel();

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("footerPanel")!=null)
		{
			setupFooterPanel();

			_mainPanel.setWidth("900px");
			_mainPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
			_mainPanel.add(_footerPanel);

			RootPanel.get("footerPanel").add(_mainPanel,0,70);
		}
	}

	private void setupFooterPanel() {


		_footerAbout.setStyleName("footerLinks");
		_footerAbout.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL()+"about.html", "_self", "");

			}});


		_footerFaqs.setStyleName("footerLinks");
		_footerFaqs.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL()+"faq.html", "_self", "");

			}});
		_footerCareers.setStyleName("footerLinks");
		_footerCareers.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Window.open(GWT.getHostPageBaseURL()+"careers.html", "_self", "");

			}});

		_footerPanel.setSpacing(10);
		_footerPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		Image twitter=new Image(Images.TWITTER.getImageURL());

		twitter.setStyleName("twitter");
		twitter.setWidth("107px");
		twitter.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Window.open("http://twitter.com/wokhei", "_new", "");

			}});

		_footerPanel.add(twitter);
		_footerPanel.add(_footerAbout);
		_footerPanel.add(getSeparator());
		_footerPanel.add(twitter);
		_footerPanel.add(getSeparator());
		_footerPanel.add(_footerFaqs);
		_footerPanel.add(getSeparator());
		_footerPanel.add(_footerCareers);
	}

	/**
	 * @return
	 */
	private Label getSeparator() 
	{
		Label separator= new Label("|");
		separator.setStyleName("separator");
		return separator;
	}

	@Override
	public void updateModulePart(OrderDTO selection) {
		// TODO Auto-generated method stub

	}

}
