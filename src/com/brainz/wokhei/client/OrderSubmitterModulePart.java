/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.Arrays;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Image;
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

	//root panel to host main and alternate panel
	private final VerticalPanel _rootPanel = new VerticalPanel();

	private final VerticalPanel _mainPanel = new VerticalPanel();

	// logotext controls
	private final VerticalPanel _logoTextPanel = new VerticalPanel();
	private final TextBox _logoTextBox = new TextBox();
	private final Label _logoTextLabel = new Label("Logo name");
	private final Label _logoHintLabel = new Label("e.g. Franco Restaurant");

	// TODO: Add color-picker
	private final VerticalPanel _colorPanel = new VerticalPanel();
	//private final TextBox logoTextBox = new TextBox();
	private final Label _colorLabel = new Label("Main color");
	private final Label _colorHintLabel = new Label("a hint for what main colour you would like for your logo");

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
	private final VerticalPanel _alternateSubPanelBody= new VerticalPanel();
	private final VerticalPanel _alternateSubPanelBodyTile= new VerticalPanel();
	private final VerticalPanel _alternateSubPanelFooter= new VerticalPanel();

	//these panels are those linked to CSS that will actually contain the images.
	//these panels can be either hidden or visible
	private final Image _bodyDrinkImage= new Image(Images.DRINKBODY.getImageURL());
	private final Image _bodyTileDrinkImage= new Image(Images.DRINKBODYTILE.getImageURL());
	private final Image _footerDrinkImage= new Image(Images.DRINKFOOTER.getImageURL());

	private final Label _timerLabel = new Label("timer here");

	// alternate/main panel switch
	private boolean _isMainPanelVisible = true;

	@Override
	public void initModulePart(HomeModuleServiceAsync service) {
		super.initModulePart(service);

		_mainPanel.setSpacing(10);

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
		_colorHintLabel.addStyleName("hintLabel");

		_colorPanel.setSpacing(1);
		_colorPanel.add(_colorLabel);
		_colorPanel.add(_colorHintLabel);

		_messageLabel.addStyleName("errorLabel");

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
		_timerLabel.addStyleName("label");

		_alternateSubPanelBody.add(_bodyDrinkImage);
		_alternateSubPanelBodyTile.add(_bodyTileDrinkImage);
		_alternateSubPanelFooter.add(_footerDrinkImage);

		_alternateSubPanelBody.add(_timerLabel);

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
				_service.submitOrder(this._logoTextBox.getText(), Arrays.asList(_logoTagsBox.getText().split(" ")), callback);			
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
			this._timerLabel.setText("all good - timer will go here!");
		}
		else
		{
			this._timerLabel.setText("Error: you're in some deep shit!");
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
