/**
 * 
 */
package com.brainz.wokhei.client.faq;

import com.brainz.wokhei.client.common.AModulePart;
import com.google.gwt.dom.client.Element;
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

	// static strings to be harnessed from html
	private String _faqTitle;
	private String _faqQ1;
	private String _faqA1;
	private String _faqQ2;
	private String _faqA2;
	private String _faqQ3;
	private String _faqA3;
	private String _faqQ4;
	private String _faqA4;
	private String _faqQ5;
	private String _faqA5;
	private String _faqQ6;
	private String _faqA6;
	private String _faqQ7;
	private String _faqA7;
	private String _faqQ8;
	private String _faqA8;
	private String _faqQ9;
	private String _faqA9;

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

		HorizontalPanel main=new HorizontalPanel();
		main.add(leftColumnPanel);
		main.add(new Image("../images/stuff1.png"));
		main.add(getWhiteSpace(30));
		main.add(rightColumnPanel);

		//RIGHT COLUMN 
		_title.setStyleName("sectionTitle");
		_title.addStyleName("fontAR");
		_title.setText(_faqQ1);
		_text.setStyleName("sectionText");
		_text.setHTML(_faqA1);
		rightColumnPanel.add(_title);
		rightColumnPanel.add(getWhiteSpace(20));
		rightColumnPanel.add(_text);

		//LEFT COLUMN
		Label aboutWokhei = new Label(_faqTitle);
		aboutWokhei.setStyleName("pageTitle");
		aboutWokhei.addStyleName("fontAR");

		VerticalPanel menu=new VerticalPanel();
		menu.setSpacing(10);

		addMenuItem(menu,_faqQ1,_faqA1);
		addMenuItem(menu,_faqQ2,_faqA2);
		addMenuItem(menu,_faqQ3,_faqA3);
		addMenuItem(menu,_faqQ4,_faqA4);
		addMenuItem(menu,_faqQ5,_faqA5);
		addMenuItem(menu,_faqQ6,_faqA6);
		addMenuItem(menu,_faqQ7,_faqA7);
		addMenuItem(menu,_faqQ8,_faqA8);
		addMenuItem(menu,_faqQ9,_faqA9);

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

	// this method extracts static text from the underlying html page. 
	// this text will be injected in gwt widgets. 
	// We need it as static text for google to index it.
	private void HarnessStringsFromHTML() {
		Element element = RootPanel.get("FAQ_TITLE").getElement();
		_faqTitle = element.getInnerHTML();

		element = RootPanel.get("FAQ_1").getElement();
		_faqQ1 = element.getInnerHTML();
		element = RootPanel.get("FAQ_1_ANSWER").getElement();
		_faqA1 = element.getInnerHTML();

		element = RootPanel.get("FAQ_2").getElement();
		_faqQ2 = element.getInnerHTML();
		element = RootPanel.get("FAQ_2_ANSWER").getElement();
		_faqA2 = element.getInnerHTML();

		element = RootPanel.get("FAQ_3").getElement();
		_faqQ3 = element.getInnerHTML();
		element = RootPanel.get("FAQ_3_ANSWER").getElement();
		_faqA3 = element.getInnerHTML();

		element = RootPanel.get("FAQ_4").getElement();
		_faqQ4 = element.getInnerHTML();
		element = RootPanel.get("FAQ_4_ANSWER").getElement();
		_faqA4 = element.getInnerHTML();

		element = RootPanel.get("FAQ_5").getElement();
		_faqQ5 = element.getInnerHTML();
		element = RootPanel.get("FAQ_5_ANSWER").getElement();
		_faqA5 = element.getInnerHTML();

		element = RootPanel.get("FAQ_6").getElement();
		_faqQ6 = element.getInnerHTML();
		element = RootPanel.get("FAQ_6_ANSWER").getElement();
		_faqA6 = element.getInnerHTML();

		element = RootPanel.get("FAQ_7").getElement();
		_faqQ7 = element.getInnerHTML();
		element = RootPanel.get("FAQ_7_ANSWER").getElement();
		_faqA7 = element.getInnerHTML();

		element = RootPanel.get("FAQ_8").getElement();
		_faqQ8 = element.getInnerHTML();
		element = RootPanel.get("FAQ_8_ANSWER").getElement();
		_faqA8 = element.getInnerHTML();

		element = RootPanel.get("FAQ_9").getElement();
		_faqQ9 = element.getInnerHTML();
		element = RootPanel.get("FAQ_9_ANSWER").getElement();
		_faqA9 = element.getInnerHTML();
	}
}
