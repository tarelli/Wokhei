package com.brainz.wokhei.client;

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;



public class FooterModulePart extends AModulePart {


	//footer stuff
	private final Hyperlink _footerAbout = new Hyperlink(" About ", "/about.jsp");
	private final Hyperlink _footerContactUs = new Hyperlink(" Contact Us ", "/contactus.jsp");
	private final Hyperlink _footerFaqs = new Hyperlink(" FAQs ", "/fags.jsp");
	private final Hyperlink _footerCareers = new Hyperlink(" Careers ", "/careers.jsp");

	private final VerticalPanel _mainPanel = new VerticalPanel();
	private final HorizontalPanel _footerPanel=new HorizontalPanel();

	@Override
	public void initModulePart(OrderServiceAsync orderService, UtilityServiceAsync utilityService, AdminServiceAsync adminService) 
	{
		if(RootPanel.get("footerPanel")!=null)
		{
			super.initModulePart(orderService, utilityService, adminService);

			setupFooterPanel();

			_mainPanel.setWidth("454px");
			_mainPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
			_mainPanel.add(_footerPanel);

			RootPanel.get("footerPanel").add(_mainPanel);
		}
	}

	private void setupFooterPanel() {
		_footerAbout.setStyleName("footerLinks");
		_footerContactUs.setStyleName("footerLinks");
		_footerFaqs.setStyleName("footerLinks");
		_footerCareers.setStyleName("footerLinks");

		_footerPanel.setSpacing(10);

		_footerPanel.add(_footerAbout);
		_footerPanel.add(new Label("|"));
		_footerPanel.add(_footerContactUs);
		_footerPanel.add(new Label("|"));
		_footerPanel.add(_footerFaqs);
		_footerPanel.add(new Label("|"));
		_footerPanel.add(_footerCareers);
	}

	@Override
	public void updateModulePart() {
		// TODO Auto-generated method stub
	}

}
