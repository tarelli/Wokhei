/**
 * 
 */
package com.brainz.wokhei.client.about;

import java.util.ArrayList;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Mails;
import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author matteocantarelli
 *
 */
public class AboutModulePart extends AModulePart implements ValueChangeHandler<String>{


	final Label _title = new Label();
	final HTML _text = new HTML();
	final List<Widget> _panels = new ArrayList<Widget>();

	//strings harnessed from html
	private String _aboutTitle = "";
	private String _aboutUsTitle = "";
	private String _aboutUsText = "";
	private String _whatWokheiTitle = "";
	private String _whatWokheiText = "";
	private String _differentWokheiTitle = "";
	private String _differentWokheiText = "";
	private String _restaurantTitle = "";
	private String _restaurantText = "";
	private String _networkTitle = "";
	private String _networkText = "";
	private String _contactUsTitle = "";
	private String _contactUsText = "";
	private String _licensesTitle = "";
	private String _licensesText = "";
	private Panel _licensesPanel;
	private Panel _contactUsPanel;

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{

		History.addValueChangeHandler(this);

		HarnessStringsFromHTML();

		//MAIN LAYOUT
		VerticalPanel leftColumnPanel=new VerticalPanel();
		leftColumnPanel.setWidth("280px");
		leftColumnPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

		VerticalPanel rightColumnPanel=new VerticalPanel();
		rightColumnPanel.setWidth("400px");
		rightColumnPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

		DOM.setStyleAttribute(_text.getElement(), "whiteSpace", "pre");
		_text.setWidth("370px");
		_text.setWordWrap(true);
		HorizontalPanel main=new HorizontalPanel();
		main.add(leftColumnPanel);
		main.add(new Image("../images/stuff2.png"));
		main.add(getWhiteSpace(30));
		main.add(rightColumnPanel);

		//RIGHT COLUMN 
		_title.setStyleName("sectionTitle");
		_title.addStyleName("fontAR");
		_title.setText(_aboutUsTitle);
		_text.setStyleName("sectionText");
		_text.setHTML(_aboutUsText);

		_contactUsPanel=getContactUsPanel();
		_contactUsPanel.setVisible(false);

		_licensesPanel=getLicensesPanel();
		_licensesPanel.setVisible(false);

		_panels.add(_contactUsPanel);
		_panels.add(_licensesPanel);

		rightColumnPanel.add(_title);
		rightColumnPanel.add(getWhiteSpace(20));
		rightColumnPanel.add(_text);
		rightColumnPanel.add(_contactUsPanel);
		rightColumnPanel.add(_licensesPanel);

		//LEFT COLUMN
		Label aboutWokhei = new Label(_aboutTitle);
		aboutWokhei.setStyleName("pageTitle");
		aboutWokhei.addStyleName("fontAR");

		VerticalPanel menu=new VerticalPanel();
		menu.setSpacing(10);

		addMenuItem(menu,_aboutUsTitle,_aboutUsText,"aboutUs");
		addMenuItem(menu,_whatWokheiTitle,_whatWokheiText,"whatWokhei");
		addMenuItem(menu,_differentWokheiTitle,_differentWokheiText,"whatDifferent");
		addMenuItem(menu,_licensesTitle,_licensesPanel,"licenses");
		addMenuItem(menu,_restaurantTitle,_restaurantText,"whyRestaurant");
		addMenuItem(menu,_networkTitle,_networkText,"network");
		addMenuItem(menu,_contactUsTitle,_contactUsPanel,"contactUs");

		leftColumnPanel.add(aboutWokhei);
		leftColumnPanel.add(getWhiteSpace(10));
		leftColumnPanel.add(menu);


		RootPanel.get("aboutBodyPart").add(main);
		applyCufon();

		History.fireCurrentHistoryState();
	}

	/**
	 * @return
	 */
	private Panel getLicensesPanel()
	{
		VerticalPanel panel=new VerticalPanel();

		panel.add(getSectionText(_licensesText,"sectionText"));

		//LICENSES ARE GONE! 
		//		FlexTable table=new FlexTable();
		//		table.setStyleName("licenseTable");
		//
		//		Label wll=new Label("Wokhei Limited License");
		//		wll.setStyleName("fontAR");
		//		Label wcl=new Label("Wokhei Commercial License");
		//		wcl.setStyleName("fontAR");
		//
		//		table.setWidget(0, 1, wll);
		//		table.setWidget(0, 2, wcl);
		//
		//		table.getCellFormatter().setAlignment(1, 1,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.getCellFormatter().setAlignment(1, 2,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.setText(1,0,"Non Commercial Use");
		//		table.setWidget(1, 1, new Image(Images.OK.getImageURL()));
		//		table.setWidget(1, 2, new Image(Images.OK.getImageURL()));
		//
		//		table.getCellFormatter().setAlignment(2, 1,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.getCellFormatter().setAlignment(2, 2,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.setText(2,0,"Commercial use");
		//		table.setWidget(2, 1, new Image(Images.NOK.getImageURL()));
		//		table.setWidget(2, 2, new Image(Images.OK.getImageURL()));
		//
		//		table.getCellFormatter().setAlignment(3, 1,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.getCellFormatter().setAlignment(3, 2,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.setText(3,0,"Uncredited use");
		//		table.setWidget(3, 1, new Image(Images.NOK.getImageURL()));
		//		table.setWidget(3, 2, new Image(Images.OK.getImageURL()));
		//
		//		table.getCellFormatter().setAlignment(4, 1,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.getCellFormatter().setAlignment(4, 2,HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
		//		table.setText(4,0,"Price");
		//		table.setText(4, 1, "FREE");
		//		table.setText(4, 2, "105 EUR");
		//
		//
		//
		//		panel.add(table);
		return panel;
	}

	// this method extracts static text from the underlying html page. 
	// this text will be injected in gwt widgets. 
	// We need it as static text for google to index it.
	private void HarnessStringsFromHTML() {
		Element element = RootPanel.get("ABOUT_TITLE").getElement();
		_aboutTitle = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_ABOUT_US").getElement();
		_aboutUsTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_ABOUT_US_TEXT").getElement();
		_aboutUsText = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_WHATWOKHEI").getElement();
		_whatWokheiTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_WHATWOKHEI_TEXT").getElement();
		_whatWokheiText = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_LICENSES").getElement();
		_licensesTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_LICENSES_TEXT").getElement();
		_licensesText = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_DIFFERENTWOKHEI").getElement();
		_differentWokheiTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_DIFFERENTWOKHEI_TEXT").getElement();
		_differentWokheiText = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_RESTAURANT").getElement();
		_restaurantTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_RESTAURANT_TEXT").getElement();
		_restaurantText = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_GRAPHICSNETWORK").getElement();
		_networkTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_GRAPHICSNETWORK_TEXT").getElement();
		_networkText = element.getInnerHTML();

		element = RootPanel.get("ABOUT_MENU_CONTACTUS").getElement();
		_contactUsTitle = element.getInnerHTML();
		element = RootPanel.get("ABOUT_MENU_CONTACTUS_TEXT").getElement();
		_contactUsText = element.getInnerHTML();
	}


	/**
	 * @return
	 */
	private Panel getContactUsPanel() 
	{
		VerticalPanel panel=new VerticalPanel();
		panel.add(getSectionText("For ongoing information about Wokhei, please follow us on Twitter. Also, feel free to contact us with service questions, partnership proposals, or media inquiries.","sectionText"));
		panel.add(getWhiteSpace(15) );
		Anchor mail=new Anchor("Partnership inquires","mailto:"+Mails.PARTNER.getMailAddress());
		mail.setStyleName("mailLink");
		Anchor mail2=new Anchor("Customer support","mailto:"+Mails.SUPPORT.getMailAddress());
		mail2.setStyleName("mailLink");
		Anchor mail3=new Anchor("Press inquiries","mailto:"+Mails.PRESS.getMailAddress());
		mail3.setStyleName("mailLink");
		panel.add(mail);
		panel.add(getWhiteSpace(5));
		panel.add(mail2);
		panel.add(getWhiteSpace(5));
		panel.add(mail3);
		return panel;
	}


	/**
	 * @param i
	 * @return
	 */
	private Widget getWhiteSpace(int i) 
	{
		Label whiteSpace=new Label();
		whiteSpace.setWidth(i+"px");
		whiteSpace.setHeight(i+"px");
		return whiteSpace;
	}

	/**
	 * @param text
	 * @param style
	 * @return
	 */
	private HTML getSectionText(String text,String style) 
	{
		HTML textLabel=new HTML(text);
		textLabel.setStyleName(style);
		return textLabel;
	}


	private Label addMenuItem(Panel menu, final String title, final Panel panel,final String token) 
	{
		Label menuItem=new Label();
		menuItem.setStyleName("labelButton");
		menuItem.addStyleName("menuItem");

		menuItem.setText(title);
		menuItem.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_title.setText(title);
				hideAllPanels();
				panel.setVisible(true);
				_text.setVisible(false);
				History.newItem(token, false);
				applyCufon();
			}});

		menu.add(menuItem);

		return menuItem;

	}

	protected void hideAllPanels() {
		for(Widget panel:_panels)
		{
			panel.setVisible(false);
		}
	}

	/**
	 * @return
	 */
	private Label addMenuItem(Panel menu, final String title, final String text, final String token) 
	{
		Label menuItem=new Label();
		menuItem.setStyleName("labelButton");
		menuItem.addStyleName("menuItem");

		menuItem.setText(title);
		menuItem.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_title.setText(title);
				_text.setHTML(text);
				hideAllPanels();
				_text.setVisible(true);
				History.newItem(token, false);
				applyCufon();
			}});

		menu.add(menuItem);

		return menuItem;
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart(OrderDTO selection) {	

	}

	public void onValueChange(ValueChangeEvent<String> event) {
		if(event.getValue().equals("licenses"))
		{
			_title.setText(_licensesTitle);
			hideAllPanels();
			_licensesPanel.setVisible(true);
			_text.setVisible(false);
			applyCufon();
		}

	}

}
