/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.List;

import com.brainz.wokhei.client.Validator.ColourErrors;
import com.brainz.wokhei.client.Validator.LogoErrors;
import com.brainz.wokhei.client.Validator.TagsErrors;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.Colour;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.OrderDTOUtils;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
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



	private static final int NUM_COLOURS = 24;

	//root panel to host main and alternate panel
	private final VerticalPanel _rootPanel = new VerticalPanel();

	private final VerticalPanel _mainPanel = new VerticalPanel();

	// logotext controls
	private final VerticalPanel _logoTextPanel = new VerticalPanel();
	private final TextBox _logoTextBox = new TextBox();
	private final Label _logoTextLabel = new Label(Messages.LOGO_NAME_LBL.getString()); //$NON-NLS-1$
	private final Label _logoHintLabel = new Label(Messages.LOGO_NAME_EG_LBL.getString()); //$NON-NLS-1$
	private final Label _logoErrorLabel = new Label(); 
	private final Image _logoOkImage = new Image();

	//COLOURS
	private final VerticalPanel _colorPanel = new VerticalPanel();
	private final Label _colourLabel = new Label(Messages.LOGO_COLOUR_LBL.getString()); //$NON-NLS-1$
	private final Label _colorHintLabel = new Label(Messages.LOGO_COLOUR_EG_LBL.getString()); //$NON-NLS-1$
	private final Label _colourErrorLabel = new Label();
	private final Image _colourOkImage = new Image();

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
	private final Label _tagsErrorLabel = new Label(); 
	private final Image _tagsOkImage = new Image();

	// a pretty self-explanatory submit button
	private final Button _submitOrderButton = new Button(Messages.SEND_REQUEST.getString()); //$NON-NLS-1$
	private final AbsolutePanel _okImagesPanel = new AbsolutePanel();

	// these panels are the place olders for the drink images
	private final VerticalPanel _alternateRootPanelBody= new VerticalPanel();
	private final VerticalPanel _alternateRootPanelBodyTile= new VerticalPanel();
	private final VerticalPanel _alternateRootPanelFooter= new VerticalPanel();

	// these panels are the place olders for the drink images
	private final AbsolutePanel _alternateSubPanelBody= new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelBodyTile= new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelFooter= new AbsolutePanel();


	private final Label _waitLabel = new Label(); //$NON-NLS-1$

	// alternate/main panel switch
	private boolean _isMainPanelVisible = true;

	private final Label _requestLabel = new Label(Messages.REQUEST_LOGO_LBL.getString()); //$NON-NLS-1$

	private final Label _whiteSpace=new Label();

	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;

	private AsyncCallback<Boolean> _submitOrderCallback =null;

	private OrderDTO _submittedOrder = null;

	@Override
	public void initModulePart(OrderServiceAsync orderService, UtilityServiceAsync utilityService) {

		if((RootPanel.get("orderSubmitter")!=null)&&
				(RootPanel.get("orderSubmitterAlternateBody")!=null)&&
				(RootPanel.get("orderSubmitterAlternateBodytile")!=null)&&
				(RootPanel.get("orderSubmitterAlternateFooter")!=null))
		{

			super.initModulePart(orderService,utilityService);

			hookUpCallbacks();

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
			_logoTextPanel.add(_logoErrorLabel);


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

			_logoTagsBox.addChangeHandler(new ChangeHandler(){
				public void onChange(ChangeEvent event) {
					if(_logoTagsBox.getText().length()!=0)
					{
						String[] tags=_logoTagsBox.getText().split(" ");
						String tagsString="";
						for(int i=0;i<tags.length;i++)
						{
							if(tags[i].charAt(0)!='#')
							{
								tagsString+="#"+tags[i]+" ";
							}
							else
							{
								tagsString+=tags[i]+" ";
							}
						}
						_logoTagsBox.setText(tagsString.trim());
					}
				}});

			_logoTagsPanel.setSpacing(1);
			_logoTagsPanel.add(_logoTagsLabel);
			_logoTagsPanel.add(_tagsHintLabel);
			_logoTagsPanel.add(_logoTagsBox);
			_logoTagsPanel.add(_tagsErrorLabel);

			_colourLabel.setStyleName("label"); //$NON-NLS-1$
			_colorHintLabel.setStyleName("hintLabel"); //$NON-NLS-1$
			_pantoneTextBox.setStyleName("pantoneLabel"); //$NON-NLS-1$
			_whiteSpace.setWidth("5px");
			_colorSubPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
			_colorSubPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
			_colorSubPanel.add(_colourLabel);
			_colorSubPanel.add(_whiteSpace);
			_colorSubPanel.add(_pantoneTextBox);
			_colorSubPanel.setHeight("15px"); //$NON-NLS-1$

			_colorPanel.add(_colorSubPanel);
			_colorPanel.add(_colorHintLabel);

			configureColoursPanels();

			//_pantoneTextBox.setWidth("180px"); //$NON-NLS-1$


			_colorPanel.setWidth("300px"); //$NON-NLS-1$
			_colorPanel.setSpacing(0);

			_rows.setSpacing(0);
			_rows.add(_firstRow);
			_rows.add(_secondRow);
			_rows.add(_thirdRow);

			_colorPanel.add(_rows);
			_colorPanel.add(_colourErrorLabel);


			_tagsErrorLabel.addStyleName("errorLabel"); //$NON-NLS-1$
			_logoErrorLabel.addStyleName("errorLabel"); //$NON-NLS-1$
			_colourErrorLabel.addStyleName("errorLabel"); //$NON-NLS-1$
			_submitOrderButton.addStyleName("submitRequest"); //$NON-NLS-1$

			_okImagesPanel.setHeight("600px");
			_okImagesPanel.setWidth("500px");
			_okImagesPanel.add(_logoOkImage,  390,82);
			_okImagesPanel.add(_tagsOkImage, 390,162);
			_okImagesPanel.add(_colourOkImage, 390,212);
			// Fill up that son of a bitch of a mainPanel

			_mainPanel.add(_logoTextPanel);
			_mainPanel.add(_logoTagsPanel);
			_mainPanel.add(_colorPanel);
			_mainPanel.add(_submitOrderButton);


			//prepare alternate panel with timer
			// TODO : add timer and shit
			// 1. check difference between timestamp and server time
			// 2. setup countdown
			// 3. setup timer to refresh client with updated countdown timer every sec 
			_waitLabel.addStyleName("waitLabel"); //$NON-NLS-1$
			_waitLabel.setWidth("350px"); //$NON-NLS-1$

			_alternateSubPanelBody.addStyleName("orderSubmitterAlternateBody"); //$NON-NLS-1$
			_alternateSubPanelBodyTile.addStyleName("orderSubmitterAlternateBodytile"); //$NON-NLS-1$
			_alternateSubPanelFooter.addStyleName("orderSubmitterAlternateFooter"); //$NON-NLS-1$


			_alternateSubPanelBody.add(_waitLabel,80,20);
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
			_submitOrderButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					submitOrder();
				}
			});

			RootPanel.get("okImagesPanel").add(_okImagesPanel);

			RootPanel.get("orderSubmitter").add(getOrderSubmitPanel()); //$NON-NLS-1$

			RootPanel.get("orderSubmitterAlternateBody").add(getOrderSubmitAlternateBodyPanel()); //$NON-NLS-1$

			RootPanel.get("orderSubmitterAlternateBodytile").add(getOrderSubmitAlternateBodytilePanel()); //$NON-NLS-1$

			RootPanel.get("orderSubmitterAlternateFooter").add(getOrderSubmitAlternateFooterPanel()); //$NON-NLS-1$
		}

	}


	private void hookUpCallbacks() {
		// Set up the callback object
		_getOrdersCallback = new AsyncCallback<List<OrderDTO>>() {

			public void onSuccess(List<OrderDTO> result) {
				// check state and accordingly set alternate/main panel switch
				setShowHideStateByLatestOrder(OrderDTOUtils.getMostRecentOrder(result));
				showHidePanels();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		};

		// Set up the callback object.
		_submitOrderCallback  = new AsyncCallback<Boolean>() {
			public void onFailure(Throwable caught) {
				//				_messageLabel.setText(Messages.GENERIC_ERROR.getString() + caught.getMessage()); //$NON-NLS-1$
			}

			public void onSuccess(Boolean result) {
				if(result!=false && _submittedOrder!=null)
				{
					updateAlternatePanelMessage(_submittedOrder, result);
					_isMainPanelVisible = false;
					showHidePanels();
					notifyChanges();
				}
			}
		};
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
		if(checkErrors())
		{
			// Make the call to the stock price service.
			_submittedOrder=new OrderDTO();
			_submittedOrder.setStatus(Status.INCOMING);
			_submittedOrder.setTags(_logoTagsBox.getText().split(" ")); //$NON-NLS-1$
			_submittedOrder.setText(_logoTextBox.getText());
			_submittedOrder.setColour(_selectedColour);

			_orderService.submitOrder(_submittedOrder, _submitOrderCallback);	
		}
	}

	/**
	 * @return
	 */
	private boolean checkErrors() 
	{
		Validator.TagsErrors tagsError=Validator.validateTags(_logoTagsBox.getText());
		Validator.LogoErrors logoError=Validator.validateLogoName(_logoTextBox.getText());
		Validator.ColourErrors colourError=Validator.validateColour(_selectedColour);

		setTagErrorStatus(tagsError);
		setColourError(colourError);
		setLogoNameError(logoError);

		return tagsError.equals(TagsErrors.NONE) && 
		colourError.equals(ColourErrors.NONE) && 
		logoError.equals(LogoErrors.NONE);
	}


	/**
	 * @param logoError
	 */
	private void setLogoNameError(LogoErrors logoError) 
	{
		switch(logoError)
		{
		case EMPTY:
			_logoErrorLabel.setText(Messages.LOGO_NAME_ERROR_NONE.getString());
			_logoOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case TOO_LONG:
			_logoErrorLabel.setText(Messages.LOGO_NAME_ERROR_TOOLONG.getString());
			_logoOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case NONE:
			_logoErrorLabel.setText("");
			_logoOkImage.setUrl(Images.OK.getImageURL());
			break;
		}
	}


	/**
	 * @param colourError
	 */
	private void setColourError(ColourErrors colourError) 
	{
		switch(colourError)
		{
		case NO_COLOR:
			_colourErrorLabel.setText(Messages.LOGO_COLOUR_ERROR_NONE.getString());
			_colourOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case NONE:
			_colourErrorLabel.setText("");
			_colourOkImage.setUrl(Images.OK.getImageURL());
			break;
		}
	}


	/**
	 * @param tagsError
	 */
	private void setTagErrorStatus(TagsErrors tagsError) 
	{
		switch(tagsError)
		{
		case TOO_FEW_TAGS:
			_tagsErrorLabel.setText(Messages.LOGO_TAGS_ERROR_NOTENOUGH.getString());
			_tagsOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case NONE:
			_tagsErrorLabel.setText("");
			_tagsOkImage.setUrl(Images.OK.getImageURL());
			break;
		case TAGS_TOO_LONG:
			_tagsErrorLabel.setText(Messages.LOGO_TAGS_ERROR_TOOLONG.getString());
			_tagsOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case TOO_MANY_TAGS:
			_tagsErrorLabel.setText(Messages.LOGO_TAGS_ERROR_TOOMANY.getString());
			_tagsOkImage.setUrl(Images.NOK.getImageURL());
			break;
		}
	}


	/**
	 * @param error
	 */
	protected void updateAlternatePanelMessage(OrderDTO order, Boolean error) 
	{
		if(error!=null)
		{
			this._waitLabel.setText(Messages.valueOf(order.getStatus().toString()+"_WAITMSG").getString()); //$NON-NLS-1$
		}
		else
		{
			this._waitLabel.setText(Messages.ERROR_WAITMSG.getString()); //$NON-NLS-1$
		}
	}


	/**
	 * Gets latest order and hooks up event success/failure handlers (a bit fucked if you ask me)
	 */
	protected void setViewByLatestOrder() 
	{
		_orderService.getOrdersForCurrentUser(_getOrdersCallback);
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
			_okImagesPanel.setVisible(true);
			showAlternatePanels(false);
		}
		else
		{
			_mainPanel.setVisible(false);
			_okImagesPanel.setVisible(false);
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
				|| result.getStatus() == Status.REJECTED))
		{
			_isMainPanelVisible = true;
		}
		else
		{
			_isMainPanelVisible = false;
			updateAlternatePanelMessage(result,false);
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
	}


}
