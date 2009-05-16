/**
 * 
 */
package com.brainz.wokhei.client;

import com.brainz.wokhei.shared.Colour;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author matteocantarelli
 *
 */
public class OrderSubmitterModulePart extends AModulePart {

	private static final int MAX_TAGS = 5;

	private static final int NUM_COLOURS = 24;

	//root panel to host main and alternate panel
	private final VerticalPanel _rootPanel = new VerticalPanel();

	private final VerticalPanel _mainPanel = new VerticalPanel();

	// logotext controls
	private final VerticalPanel _logoTextPanel = new VerticalPanel();
	private final TextBox _logoTextBox = new TextBox();
	private final Label _logoTextLabel = new Label("Logo name");
	private final Label _logoHintLabel = new Label("e.g. Franco Restaurant");

	//COLOURS
	private final VerticalPanel _colorPanel = new VerticalPanel();
	private final Label _colorLabel = new Label("Main color  ");
	private final Label _colorHintLabel = new Label("Logo's primary colour");
	private final VerticalPanel _rows = new VerticalPanel();
	private final HorizontalPanel _colorSubPanel= new HorizontalPanel();
	private final HorizontalPanel _firstRow = new HorizontalPanel();
	private final HorizontalPanel _secondRow = new HorizontalPanel();
	private final HorizontalPanel _thirdRow = new HorizontalPanel();
	private final Label _pantoneTextBox = new Label();
	private final Label _colours[] = new Label[NUM_COLOURS];

	private Colour _selectedColour;
	private Label _selectedColourButton=null;



	// logotags controls
	private final VerticalPanel _logoTagsPanel = new VerticalPanel();
	private final TextBox _logoTagsBox = new TextBox();
	private final Label _logoTagsLabel = new Label("5 Tags");
	private final Label _tagsHintLabel = new Label("e.g. #FoodIndustry #Restaurant #Fancy #FrenchCuisine");

	// a pretty self-explanatory submit button
	private final Button _submitOrder = new Button("Send request!");
	private final Label _messageLabel = new Label("");

	// these panels are the place olders for the drink images
	private final VerticalPanel _alternateRootPanelBody= new VerticalPanel();
	private final VerticalPanel _alternateRootPanelBodyTile= new VerticalPanel();
	private final VerticalPanel _alternateRootPanelFooter= new VerticalPanel();

	// these panels are the place olders for the drink images
	private final AbsolutePanel _alternateSubPanelBody= new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelBodyTile= new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelFooter= new AbsolutePanel();


	private final Label _timerLabel = new Label("Your logo is getting ready, do you fancy a drink meanwhile?");

	// alternate/main panel switch
	private boolean _isMainPanelVisible = true;

	private final Label _requestLabel = new Label("Request your logo");



	@Override
	public void initModulePart(OrderServiceAsync service) {
		super.initModulePart(service);

		_mainPanel.setSpacing(10);

		_requestLabel.addStyleName("h3");
		_mainPanel.add(_requestLabel );

		_logoTextLabel.addStyleName("label");

		_logoHintLabel.setStyleName("hintLabel");

		_logoTextBox.setText("the text in your logo");
		_logoTextBox.setWidth("255px");
		_logoTextBox.setStyleName("textBox");

		_logoTextBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(_logoTextBox.getText().equals("the text in your logo"))
				{
					_logoTextBox.selectAll();
				}
			}});

		_logoTagsLabel.addStyleName("label");
		_tagsHintLabel.addStyleName("hintLabel");

		// prepare my motherfuckin' logoText vertical panel
		_logoTextPanel.setSpacing(1);
		_logoTextPanel.add(_logoTextLabel);
		_logoTextPanel.add(_logoHintLabel);
		_logoTextPanel.add(_logoTextBox);


		// prepare my cock-fuckerin' tags vertical panel
		_logoTagsBox.setWidth("255px");
		_logoTagsBox.setText("describe what the logo is for!");
		_logoTagsBox.setStyleName("textBox");
		_logoTagsBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(_logoTagsBox.getText().equals("describe what the logo is for!"))
				{
					_logoTagsBox.selectAll();
				}
			}});

		_logoTagsPanel.setSpacing(1);
		_logoTagsPanel.add(_logoTagsLabel);
		_logoTagsPanel.add(_tagsHintLabel);
		_logoTagsPanel.add(_logoTagsBox);

		_colorLabel.addStyleName("label");
		_colorLabel.setWidth("80px");
		_colorHintLabel.addStyleName("hintLabel");
		_colorPanel.add(_colorLabel);
		_colorSubPanel.setHeight("15px");
		configureColoursPanels();

		_pantoneTextBox.setWidth("180px");
		_pantoneTextBox.setStyleName("pantoneLabel");

		_colorSubPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
		_colorSubPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		_colorSubPanel.add(_colorHintLabel);
		_colorSubPanel.add(_pantoneTextBox);

		_colorPanel.setWidth("300px");
		_colorPanel.setSpacing(0);
		_rows.setSpacing(0);
		_colorPanel.add(_colorSubPanel);



		_rows.add(_firstRow);
		_rows.add(_secondRow);
		_rows.add(_thirdRow);

		_colorPanel.add(_rows);


		_messageLabel.addStyleName("errorLabel");
		_submitOrder.addStyleName("submitRequest");

		// Fill up that son of a bitch of a mainPanel
		_mainPanel.add(_logoTextPanel);
		_mainPanel.add(_logoTagsPanel);
		_mainPanel.add(_colorPanel);
		_mainPanel.add(_messageLabel);
		_mainPanel.add(_submitOrder);

		//prepare alternate panel with timer
		// TODO : add timer and shit
		// 1. check difference between timestamp and server time
		// 2. setup countdown
		// 3. setup timer to refresh client with updated countdown timer every sec 
		_timerLabel.addStyleName("waitLabel");
		_timerLabel.setWidth("350px");

		_alternateSubPanelBody.addStyleName("orderSubmitterAlternateBody");
		_alternateSubPanelBodyTile.addStyleName("orderSubmitterAlternateBodytile");
		_alternateSubPanelFooter.addStyleName("orderSubmitterAlternateFooter");


		_alternateSubPanelBody.add(_timerLabel,80,50);
		_alternateRootPanelBody.setWidth("500px");
		_alternateRootPanelBodyTile.setWidth("500px");
		_alternateRootPanelFooter.setWidth("500px");

		_alternateRootPanelBody.add(_alternateSubPanelBody);
		_alternateRootPanelBodyTile.add(_alternateSubPanelBodyTile);
		_alternateRootPanelFooter.add(_alternateSubPanelFooter);

		// set default visibility
		_mainPanel.setVisible(true);
		showAlternatePanels(false);

		//add main and alternate panel to root panel
		_rootPanel.add(_mainPanel);

		setViewByLatestOrder();

		RootPanel.get("orderSubmitter").add(_rootPanel);

		// Move cursor focus to the logoText input box.
		_logoTextBox.setFocus(true);

		// Listen for mouse events on the Add button.
		_submitOrder.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				submitOrder();
			}
		});

		RootPanel.get("orderSubmitter").add(getOrderSubmitPanel());
		RootPanel.get("orderSubmitterAlternateBody").add(getOrderSubmitAlternateBodyPanel());
		RootPanel.get("orderSubmitterAlternateBodytile").add(getOrderSubmitAlternateBodytilePanel());
		RootPanel.get("orderSubmitterAlternateFooter").add(getOrderSubmitAlternateFooterPanel());
	}


	private void configureColoursPanels() 
	{
		_firstRow.setSpacing(1);
		_secondRow.setSpacing(1);
		_thirdRow.setSpacing(1);

		for(int i=0;i<NUM_COLOURS;i++)
		{
			_colours[i]=new Label();
			_colours[i].setHeight("28px");
			_colours[i].setWidth("28px");
			_colours[i].addStyleName("colour"+Colour.values()[i].toString());
			_colours[i].addStyleName("colourNormal");
			_colours[i].addStyleName("colourBorder");

			final int index=i;

			_colours[i].addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					_pantoneTextBox.setText(Colour.values()[index].getName());
					if(_selectedColourButton!=null)
					{
						_selectedColourButton.removeStyleName("colourSelected");
						_selectedColourButton.addStyleName("colourNormal");
					}
					_selectedColour=Colour.values()[index];
					_colours[index].removeStyleName("colourNormal");
					_colours[index].addStyleName("colourSelected");
					_selectedColourButton=_colours[index];
				}});

			if(i<8)
				_firstRow.add(_colours[i]);
			else if(i<16)
				_secondRow.add(_colours[i]);
			else if(i<NUM_COLOURS)
				_thirdRow.add(_colours[i]);
		}

	}


	/**
	 * 
	 */
	protected void submitOrder() 
	{

		if (this._logoTagsBox.getText().trim().length()!=0)
		{		
			if(_logoTagsBox.getText().split(" ").length>MAX_TAGS)
			{
				_messageLabel.setText("Sorry, our Chefs get confused with more than 5 tags!");
			}
			else if(_selectedColour==null)
			{
				_messageLabel.setText("Pick up the main colour!");
			}
			else
			{
				// Set up the callback object.
				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						_messageLabel.setText("Fuck-up: " + caught.getMessage());
					}

					public void onSuccess(Boolean result) {

						//updateMessage(result);
						updateAlternatePanelMessage(result);
						_isMainPanelVisible = false;
						showHidePanels();
						notifyChanges();
					}
				};

				// Make the call to the stock price service.
				OrderDTO order=new OrderDTO();
				order.setStatus(Status.INCOMING);
				order.setTags(_logoTagsBox.getText().split(" "));
				order.setText(_logoTextBox.getText());
				order.setColour(_selectedColour);
				_service.submitOrder(order, callback);			
			}
		}
		else
		{
			_messageLabel.setText("You need to put at least some tags!");
		}
	}

	/**
	 * @param result
	 */
	protected void updateAlternatePanelMessage(Boolean result) 
	{
		if(result)
		{
			this._timerLabel.setText("Your logo is getting ready, do you fancy a drink meanwhile?");
		}
		else
		{
			this._timerLabel.setText("An error occurred while sending your request. Please do complain!");
		}
	}

	/**
	 * @param result
	 */
	protected void updateMessage(Boolean result) 
	{
		if(result)
		{
			_messageLabel.setText("It's all good - Your request has been sent to the kitchen!");
		}
		else
		{
			_messageLabel.setText("Error: A bit of a Fuck-up!");
		}
	}

	/**
	 * Gets latest order and hooks up event success/failure handlers (a bit fucked if you ask me)
	 */
	protected void setViewByLatestOrder() 
	{
		// Set up the callback object
		AsyncCallback<OrderDTO> callback = new AsyncCallback<OrderDTO>() {

			public void onSuccess(OrderDTO result) {
				// check state and accordingly set alternate/main panel switch
				setShowHideStateByLatestOrder(result);
				showHidePanels();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		};

		_service.getLatestOrder(callback);
	}


	/**
	 * 
	 */
	protected void showHidePanels() 
	{
		if(_isMainPanelVisible)
		{
			// Associate the feckin' Main panel with the HTML element on the host page.
			_mainPanel.setVisible(true);
			showAlternatePanels(false);
		}
		else
		{
			_mainPanel.setVisible(false);
			showAlternatePanels(true);	
		}
	}

	/**
	 * @param result
	 */
	protected void setShowHideStateByLatestOrder(OrderDTO result) 
	{
		if(result==null || (result.getStatus() == Status.ARCHIVED 
				|| result.getStatus() == Status.BOUGHT 
				|| result.getStatus() == Status.PAYED
				|| result.getStatus() == Status.REJECTED))
		{
			_isMainPanelVisible = true;
		}
		else
		{
			_isMainPanelVisible = false;
		}
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitPanel() 
	{
		return _mainPanel;
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitAlternateFooterPanel() 
	{
		return _alternateRootPanelFooter;
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitAlternateBodyPanel() 
	{
		return _alternateRootPanelBody;
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitAlternateBodytilePanel() 
	{
		return _alternateRootPanelBodyTile;
	}

	/**
	 * @param show
	 */
	private void showAlternatePanels(boolean show)
	{
		_alternateSubPanelBody.setVisible(show);
		_alternateSubPanelBodyTile.setVisible(show);
		_alternateSubPanelFooter.setVisible(show);
	}


	@Override
	public void updateModulePart() {
		// TODO Auto-generated method stub

	}


}
