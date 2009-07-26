/**
 * 
 */
package com.brainz.wokhei.client.about;

import java.util.ArrayList;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Mails;
import com.brainz.wokhei.resources.Messages;
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

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
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
		_title.setText(Messages.ABOUT_MENU_ABOUT_US.getString());
		_text.setStyleName("sectionText");
		_text.setHTML(Messages.ABOUT_MENU_ABOUT_US_TEXT.getString());
		Panel contactUsPanel=getContactUsPanel();
		contactUsPanel.setVisible(false);
		_panels.add(contactUsPanel);
		rightColumnPanel.add(_title);
		rightColumnPanel.add(getWhiteSpace(20));
		rightColumnPanel.add(_text);
		rightColumnPanel.add(contactUsPanel);

		//LEFT COLUMN
		Label aboutWokhei = new Label(Messages.ABOUT_TITLE.getString());
		aboutWokhei.setStyleName("pageTitle");
		aboutWokhei.addStyleName("fontAR");

		VerticalPanel menu=new VerticalPanel();
		menu.setSpacing(10);

		addMenuItem(menu,Messages.ABOUT_MENU_ABOUT_US,Messages.ABOUT_MENU_ABOUT_US_TEXT);
		addMenuItem(menu,Messages.ABOUT_MENU_WHATWOKHEI,Messages.ABOUT_MENU_WHATWOKHEI_TEXT);
		addMenuItem(menu,Messages.ABOUT_MENU_DIFFERENTWOKHEI,Messages.ABOUT_MENU_DIFFERENTWOKHEI_TEXT);
		addMenuItem(menu,Messages.ABOUT_MENU_RESTAURANT,Messages.ABOUT_MENU_RESTAURANT_TEXT);
		addMenuItem(menu,Messages.ABOUT_MENU_GRAPHICSNETWORK,Messages.ABOUT_MENU_GRAPHICSNETWORK_TEXT);
		addMenuItem(menu,Messages.ABOUT_MENU_CONTACTUS,contactUsPanel);

		leftColumnPanel.add(aboutWokhei);
		leftColumnPanel.add(getWhiteSpace(10));
		leftColumnPanel.add(menu);


		RootPanel.get("aboutBodyPart").add(main);
		applyCufon();
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


	private Label addMenuItem(Panel menu, final Messages title, final Panel panel) 
	{
		Label menuItem=new Label();
		menuItem.setStyleName("labelButton");
		menuItem.addStyleName("menuItem");

		menuItem.setText(title.getString());
		menuItem.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_title.setText(title.getString());
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
	private Label addMenuItem(Panel menu, final Messages title, final Messages text) 
	{
		Label menuItem=new Label();
		menuItem.setStyleName("labelButton");
		menuItem.addStyleName("menuItem");

		menuItem.setText(title.getString());
		menuItem.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_title.setText(title.getString());
				_text.setHTML(text.getString());
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
