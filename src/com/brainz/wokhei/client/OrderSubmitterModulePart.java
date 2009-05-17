/**
 * 
 */
package com.brainz.wokhei.client;

import com.brainz.wokhei.resources.Messages;
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
	private final Label _logoTextLabel = new Label(Messages.LOGO_NAME_LBL.getString()); //$NON-NLS-1$
	private final Label _logoHintLabel = new Label(Messages.LOGO_NAME_EG_LBL.getString()); //$NON-NLS-1$

	//COLOURS
	private final VerticalPanel _colorPanel = new VerticalPanel();
	private final Label _colorLabel = new Label(Messages.LOGO_COLOUR_LBL.getString()); //$NON-NLS-1$
	private final Label _colorHintLabel = new Label(Messages.LOGO_COLOUR_EG_LBL.getString()); //$NON-NLS-1$
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
	private final Label _logoTagsLabel = new Label(Messages.LOGO_TAGS_LBL.getString()); //$NON-NLS-1$
	private final Label _tagsHintLabel = new Label(Messages.LOGO_TAGS_EG_LBL.getString()); //$NON-NLS-1$

	// a pretty self-explanatory submit button
	private final Button _submitOrder = new Button(Messages.SEND_REQUEST.getString()); //$NON-NLS-1$
	private final Label _messageLabel = new Label(""); //$NON-NLS-1$

	// these panels are the place olders for the drink images
	private final VerticalPanel _alternateRootPanelBody= new VerticalPanel();
	private final VerticalPanel _alternateRootPanelBodyTile= new VerticalPanel();
	private final VerticalPanel _alternateRootPanelFooter= new VerticalPanel();

	// these panels are the place olders for the drink images
	private final AbsolutePanel _alternateSubPanelBody= new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelBodyTile= new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelFooter= new AbsolutePanel();


	private final Label _timerLabel = new Label(); //$NON-NLS-1$

	// alternate/main panel switch
	private boolean _isMainPanelVisible = true;

	private final Label _requestLabel = new Label(Messages.REQUEST_LOGO_LBL.getString()); //$NON-NLS-1$



	@Override
	public void initModulePart(OrderServiceAsync service) {
		super.initModulePart(service);

		_mainPanel.setSpacing(10);

		_requestLabel.addStyleName("h3"); //$NON-NLS-1$
		_mainPanel.add(_requestLabel );

		_logoTextLabel.addStyleName("label"); //$NON-NLS-1$

		_logoHintLabel.setStyleName("hintLabel"); //$NON-NLS-1$

		_logoTextBox.setText(Messages.LOGO_NAME_TXTBOX.getString()); //$NON-NLS-1$
		_logoTextBox.setWidth("255px"); //$NON-NLS-1$
		_logoTextBox.setStyleName("textBox"); //$NON-NLS-1$

		_logoTextBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(_logoTextBox.getText().equals(Messages.LOGO_NAME_TXTBOX.getString())) //$NON-NLS-1$
				{
					_logoTextBox.selectAll();
				}
			}});

		_logoTagsLabel.addStyleName("label"); //$NON-NLS-1$
		_tagsHintLabel.addStyleName("hintLabel"); //$NON-NLS-1$

		// prepare my motherfuckin' logoText vertical panel
		_logoTextPanel.setSpacing(1);
		_logoTextPanel.add(_logoTextLabel);
		_logoTextPanel.add(_logoHintLabel);
		_logoTextPanel.add(_logoTextBox);


		// prepare my cock-fuckerin' tags vertical panel
		_logoTagsBox.setWidth("255px"); //$NON-NLS-1$
		_logoTagsBox.setText(Messages.LOGO_TAGS_TXTBOX.getString()); //$NON-NLS-1$
		_logoTagsBox.setStyleName("textBox"); //$NON-NLS-1$
		_logoTagsBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(_logoTagsBox.getText().equals(Messages.LOGO_TAGS_TXTBOX.getString())) //$NON-NLS-1$
				{
					_logoTagsBox.selectAll();
				}
			}});

		_logoTagsPanel.setSpacing(1);
		_logoTagsPanel.add(_logoTagsLabel);
		_logoTagsPanel.add(_tagsHintLabel);
		_logoTagsPanel.add(_logoTagsBox);

		_colorLabel.addStyleName("label"); //$NON-NLS-1$
		_colorHintLabel.addStyleName("hintLabel"); //$NON-NLS-1$
		_colorPanel.add(_colorLabel);
		_colorSubPanel.setHeight("15px"); //$NON-NLS-1$
		configureColoursPanels();

		_pantoneTextBox.setWidth("180px"); //$NON-NLS-1$
		_pantoneTextBox.setStyleName("pantoneLabel"); //$NON-NLS-1$

		_colorSubPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
		_colorSubPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_RIGHT);
		_colorSubPanel.add(_colorHintLabel);
		_colorSubPanel.add(_pantoneTextBox);

		_colorPanel.setWidth("300px"); //$NON-NLS-1$
		_colorPanel.setSpacing(0);
		_rows.setSpacing(0);
		_colorPanel.add(_colorSubPanel);



		_rows.add(_firstRow);
		_rows.add(_secondRow);
		_rows.add(_thirdRow);

		_colorPanel.add(_rows);


		_messageLabel.addStyleName("errorLabel"); //$NON-NLS-1$
		_submitOrder.addStyleName("submitRequest"); //$NON-NLS-1$

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
		_timerLabel.addStyleName("waitLabel"); //$NON-NLS-1$
		_timerLabel.setWidth("350px"); //$NON-NLS-1$

		_alternateSubPanelBody.addStyleName("orderSubmitterAlternateBody"); //$NON-NLS-1$
		_alternateSubPanelBodyTile.addStyleName("orderSubmitterAlternateBodytile"); //$NON-NLS-1$
		_alternateSubPanelFooter.addStyleName("orderSubmitterAlternateFooter"); //$NON-NLS-1$


		_alternateSubPanelBody.add(_timerLabel,80,50);
		_alternateRootPanelBody.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelBodyTile.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelFooter.setWidth("500px"); //$NON-NLS-1$

		_alternateRootPanelBody.add(_alternateSubPanelBody);
		_alternateRootPanelBodyTile.add(_alternateSubPanelBodyTile);
		_alternateRootPanelFooter.add(_alternateSubPanelFooter);

		// set default visibility
		_mainPanel.setVisible(true);
		showAlternatePanels(false);

		//add main and alternate panel to root panel
		_rootPanel.add(_mainPanel);

		setViewByLatestOrder();

		RootPanel.get("orderSubmitter").add(_rootPanel); //$NON-NLS-1$

		// Move cursor focus to the logoText input box.
		_logoTextBox.setFocus(true);

		// Listen for mouse events on the Add button.
		_submitOrder.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				submitOrder();
			}
		});

		RootPanel.get("orderSubmitter").add(getOrderSubmitPanel()); //$NON-NLS-1$
		RootPanel.get("orderSubmitterAlternateBody").add(getOrderSubmitAlternateBodyPanel()); //$NON-NLS-1$
		RootPanel.get("orderSubmitterAlternateBodytile").add(getOrderSubmitAlternateBodytilePanel()); //$NON-NLS-1$
		RootPanel.get("orderSubmitterAlternateFooter").add(getOrderSubmitAlternateFooterPanel()); //$NON-NLS-1$
	}


	private void configureColoursPanels() 
	{
		_firstRow.setSpacing(1);
		_secondRow.setSpacing(1);
		_thirdRow.setSpacing(1);

		for(int i=0;i<NUM_COLOURS;i++)
		{
			_colours[i]=new Label();
			_colours[i].setHeight("28px"); //$NON-NLS-1$
			_colours[i].setWidth("28px"); //$NON-NLS-1$
			_colours[i].addStyleName("colour"+Colour.values()[i].toString()); //$NON-NLS-1$
			_colours[i].addStyleName("colourNormal"); //$NON-NLS-1$
			_colours[i].addStyleName("colourBorder"); //$NON-NLS-1$

			final int index=i;

			_colours[i].addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					_pantoneTextBox.setText(Colour.values()[index].getName());
					if(_selectedColourButton!=null)
					{
						_selectedColourButton.removeStyleName("colourSelected"); //$NON-NLS-1$
						_selectedColourButton.addStyleName("colourNormal"); //$NON-NLS-1$
					}
					_selectedColour=Colour.values()[index];
					_colours[index].removeStyleName("colourNormal"); //$NON-NLS-1$
					_colours[index].addStyleName("colourSelected"); //$NON-NLS-1$
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
			if(_logoTagsBox.getText().split(" ").length>=MAX_TAGS) //$NON-NLS-1$
			{
				_messageLabel.setText(Messages.LOGO_TAGS_ERROR_TOOMANY.getString()); //$NON-NLS-1$
			}
			else if(_selectedColour==null)
			{
				_messageLabel.setText(Messages.LOGO_COLOUR_ERROR_NONE.getString()); //$NON-NLS-1$
			}
			else
			{
				// Set up the callback object.
				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						_messageLabel.setText(Messages.GENERIC_ERROR.getString() + caught.getMessage()); //$NON-NLS-1$
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
				order.setTags(_logoTagsBox.getText().split(" ")); //$NON-NLS-1$
				order.setText(_logoTextBox.getText());
				order.setColour(_selectedColour);
				_service.submitOrder(order, callback);			
			}
		}
		else
		{
			_messageLabel.setText(Messages.LOGO_TAGS_ERROR_NOTENOUGH.getString()); //$NON-NLS-1$
		}
	}

	/**
	 * @param result
	 */
	protected void updateAlternatePanelMessage(Boolean result) 
	{
		if(result)
		{
			this._timerLabel.setText(Messages.INCOMING_WAITMSG.getString()); //$NON-NLS-1$
		}
		else
		{
			this._timerLabel.setText(Messages.ERROR_WAITMSG.getString()); //$NON-NLS-1$
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
