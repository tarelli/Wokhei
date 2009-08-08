/**
 * 
 */
package com.brainz.wokhei.client.about;

import java.util.ArrayList;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Mails;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
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
public class AboutModulePart extends AModulePart {


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

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
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
		Panel contactUsPanel=getContactUsPanel();
		contactUsPanel.setVisible(false);
		_panels.add(contactUsPanel);
		rightColumnPanel.add(_title);
		rightColumnPanel.add(getWhiteSpace(20));
		rightColumnPanel.add(_text);
		rightColumnPanel.add(contactUsPanel);

		//LEFT COLUMN
		Label aboutWokhei = new Label(_aboutTitle);
		aboutWokhei.setStyleName("pageTitle");
		aboutWokhei.addStyleName("fontAR");

		VerticalPanel menu=new VerticalPanel();
		menu.setSpacing(10);

		addMenuItem(menu,_aboutUsTitle,_aboutUsText);
		addMenuItem(menu,_whatWokheiTitle,_whatWokheiText);
		addMenuItem(menu,_differentWokheiTitle,_differentWokheiText);
		addMenuItem(menu,_restaurantTitle,_restaurantText);
		addMenuItem(menu,_networkTitle,_networkText);
		addMenuItem(menu,_contactUsTitle,contactUsPanel);

		leftColumnPanel.add(aboutWokhei);
		leftColumnPanel.add(getWhiteSpace(10));
		leftColumnPanel.add(menu);


		RootPanel.get("aboutBodyPart").add(main);
		applyCufon();
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
		panel.add(getWhiteSpace(15));
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
	private Label getSectionText(String text,String style) 
	{
		Label textLabel=new Label(text);
		textLabel.setStyleName(style);
		return textLabel;
	}


	private Label addMenuItem(Panel menu, final String title, final Panel panel) 
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
	private Label addMenuItem(Panel menu, final String title, final String text) 
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
				applyCufon();
			}});

		menu.add(menuItem);

		return menuItem;
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() {	

	}

}
