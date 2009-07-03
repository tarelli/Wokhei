package com.brainz.wokhei.client.common;

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

			RootPanel.get("footerPanel").add(_mainPanel);
		}
	}

	private void setupFooterPanel() {
		_footerAbout.removeStyleName("gwt-Hyperlink");
		_footerAbout.setStyleName("footerLinks");
		_footerContactUs.removeStyleName("gwt-Hyperlink");
		_footerContactUs.setStyleName("footerLinks");
		_footerFaqs.removeStyleName("gwt-Hyperlink");
		_footerFaqs.setStyleName("footerLinks");
		_footerCareers.removeStyleName("gwt-Hyperlink");
		_footerCareers.setStyleName("footerLinks");

		_footerPanel.setSpacing(10);

		_footerPanel.add(_footerAbout);
		_footerPanel.add(getSeparator());
		_footerPanel.add(_footerContactUs);
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
	public void updateModulePart() {
		// TODO Auto-generated method stub

	}

}
