/**
 * 
 */
package com.brainz.wokhei.client.faq;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Messages;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class FaqModulePart extends AModulePart {

	final Label _title = new Label();
	final HTML _text = new HTML();

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

		HorizontalPanel main=new HorizontalPanel();
		main.add(leftColumnPanel);
		main.add(new Image("../images/stuff1.png"));
		main.add(getWhiteSpace(30));
		main.add(rightColumnPanel);

		//RIGHT COLUMN 
		_title.setStyleName("sectionTitle");
		_title.addStyleName("fontAR");
		_title.setText(Messages.FAQ_1.getString());
		_text.setStyleName("sectionText");
		_text.setHTML(Messages.FAQ_1_ANSWER.getString());
		rightColumnPanel.add(_title);
		rightColumnPanel.add(getWhiteSpace(20));
		rightColumnPanel.add(_text);

		//LEFT COLUMN
		Label aboutWokhei = new Label(Messages.FAQ_TITLE.getString());
		aboutWokhei.setStyleName("pageTitle");
		aboutWokhei.addStyleName("fontAR");

		VerticalPanel menu=new VerticalPanel();
		menu.setSpacing(10);

		addMenuItem(menu,Messages.FAQ_1,Messages.FAQ_1_ANSWER);
		addMenuItem(menu,Messages.FAQ_2,Messages.FAQ_2_ANSWER);
		addMenuItem(menu,Messages.FAQ_3,Messages.FAQ_3_ANSWER);
		addMenuItem(menu,Messages.FAQ_4,Messages.FAQ_4_ANSWER);
		addMenuItem(menu,Messages.FAQ_5,Messages.FAQ_5_ANSWER);
		addMenuItem(menu,Messages.FAQ_6,Messages.FAQ_6_ANSWER);
		addMenuItem(menu,Messages.FAQ_7,Messages.FAQ_7_ANSWER);
		addMenuItem(menu,Messages.FAQ_8,Messages.FAQ_8_ANSWER);
		addMenuItem(menu,Messages.FAQ_9,Messages.FAQ_9_ANSWER);

		leftColumnPanel.add(aboutWokhei);
		leftColumnPanel.add(getWhiteSpace(10));
		leftColumnPanel.add(menu);


		RootPanel.get("faqBodyPart").add(main);
		applyCufon();
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
