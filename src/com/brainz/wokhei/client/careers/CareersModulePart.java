/**
 * 
 */
package com.brainz.wokhei.client.careers;

import java.util.ArrayList;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.resources.Messages;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
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
public class CareersModulePart extends AModulePart {

	final Label _title = new Label();
	final Label _text = new Label();
	final List<Widget> _panels = new ArrayList<Widget>();

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
		//MAIN LAYOUT
		VerticalPanel leftColumnPanel=new VerticalPanel();
		leftColumnPanel.setWidth("240px");
		leftColumnPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

		VerticalPanel rightColumnPanel=new VerticalPanel();
		rightColumnPanel.setWidth("450px");
		rightColumnPanel.setHorizontalAlignment(VerticalPanel.ALIGN_LEFT);

		HorizontalPanel main=new HorizontalPanel();
		main.add(leftColumnPanel);
		main.add(new Image("../images/stuff3.png"));
		main.add(getWhiteSpace(30));
		main.add(rightColumnPanel);

		//RIGHT COLUMN 
		_title.setStyleName("sectionTitle");
		_title.addStyleName("fontAR");
		_title.setText(Messages.CAREERS_GRAPHIC_TITLE.getString());
		_text.setStyleName("sectionText");
		//_text.setText(Messages.CAREERS_GRAPHIC_TEXT.getString());
		rightColumnPanel.add(_title);
		rightColumnPanel.add(getWhiteSpace(20));
		Panel panel=getGraphicPanel();
		_panels.add(panel);
		rightColumnPanel.add(panel);

		//LEFT COLUMN
		Label aboutWokhei = new Label(Messages.CAREERS_TITLE.getString());
		aboutWokhei.setStyleName("pageTitle");
		aboutWokhei.addStyleName("fontAR");

		VerticalPanel menu=new VerticalPanel();
		menu.setSpacing(10);



		addMenuItem(menu,Messages.CAREERS_GRAPHIC_TITLE,panel);


		leftColumnPanel.add(aboutWokhei);
		leftColumnPanel.add(getWhiteSpace(10));
		leftColumnPanel.add(menu);


		RootPanel.get("careersBodyPart").add(main);
		applyCufon();
	}


	private Label addMenuItem(VerticalPanel menu, final Messages title, final Panel panel) 
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
	private Panel getGraphicPanel() 
	{
		VerticalPanel panel=new VerticalPanel();
		//panel.setWidth("450px");

		panel.add(getSectionText("Wokhei's network is looking for yet another extraordinary graphic designer with strong concepting skills who has proven experience in creating logos for industry. The ideal candidate should have an awareness of current trends in marketing, web communication, and new media.","sectionText"));
		panel.add(getWhiteSpace(15));
		panel.add(getSectionText("Responsibilities", "sectionHeader"));
		panel.add(getWhiteSpace(10));
		panel.add(getSectionText("Design, contribute to, and oversee logos", "sectionText"));
		panel.add(getWhiteSpace(10));
		panel.add(getSectionText("Creative involvement with logos from beginning to end", "sectionText"));
		panel.add(getWhiteSpace(15));
		panel.add(getSectionText("Requirements", "sectionHeader"));
		panel.add(getWhiteSpace(10));
		panel.add(getSectionText("Comprehensive understanding of design principles and typography", "sectionText"));
		panel.add(getWhiteSpace(10));
		panel.add(getSectionText("Strong conceptual design skills and the ability to execute designs efficiently and at a high level of quality", "sectionText"));
		panel.add(getWhiteSpace(10));		
		panel.add(getSectionText("Expert-level knowledge of Photoshop, Illustrator, InDesign, and other graphic design tools", "sectionText"));
		panel.add(getWhiteSpace(10));		
		panel.add(getSectionText("Ability to work well under the 24hrs Wokhei turnaround", "sectionText"));
		panel.add(getWhiteSpace(10));		
		panel.add(getSectionText("Share the Wokhei view and strong culture.", "sectionText"));
		panel.add(getWhiteSpace(15));		
		panel.add(getSectionText("Please send a cover letter, resume, and portfolio to:","sectionText"));
		panel.add(getWhiteSpace(5));
		Anchor mail=new Anchor("resumes@wokhei.com","mailto:resumes@wokhei.com");
		mail.setStyleName("mailLink");
		panel.add(mail);
		panel.add(getWhiteSpace(5));
		panel.add(getSectionText("with art samples attached or a link to an online portfolio in the body of the email. A portfolio of relevant work is required for your application. Please list this job and your name in the subject line.", "sectionText"));
		panel.add(getWhiteSpace(15));
		panel.add(getSectionText("No calls or follow-up emails, please. This is a freelance position.", "sectionText"));

		return panel;
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
				_text.setText(text.getString());
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
