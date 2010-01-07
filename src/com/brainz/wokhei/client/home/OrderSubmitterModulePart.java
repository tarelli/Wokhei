/**
 * 
 */
package com.brainz.wokhei.client.home;

import java.util.List;

import pl.rmalinowski.gwt2swf.client.ui.SWFWidget;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.client.home.Validator.ColourErrors;
import com.brainz.wokhei.client.home.Validator.DescriptionErrors;
import com.brainz.wokhei.client.home.Validator.LogoErrors;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.resources.PayPalStrings;
import com.brainz.wokhei.shared.Colour;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.OrderDTOUtils;
import com.brainz.wokhei.shared.Status;
import com.brainz.wokhei.shared.TransactionType;
import com.google.gwt.dom.client.Element;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author matteocantarelli / giovazza
 */
public class OrderSubmitterModulePart extends AModulePart {
	private static final int NUM_COLOURS = 24;

	private static final boolean DEBUG = false; //set to true to disable the micropayment mandatory transaction

	// root panel to host main and alternate panel
	private final VerticalPanel _rootPanel = new VerticalPanel();

	private final VerticalPanel _mainPanel = new VerticalPanel();

	// NAME
	private final VerticalPanel _logoTextPanel = new VerticalPanel();
	private final TextBox _logoTextBox = new TextBox();
	private final Label _logoTextHelpMark = new Label();
	private final Label _logoTextHelpLabel = new Label(
			Messages.LOGO_NAME_HELP_MESSAGE.getString());
	private final PopupPanel _logoTextHelpPopup = new PopupPanel(true);
	private final Label _logoTextLabel = new Label(Messages.LOGO_NAME_LBL
			.getString()); //$NON-NLS-1$
	private final HorizontalPanel _logoTextLabelPanel = new HorizontalPanel();
	private final Label _logoHintLabel = new Label(Messages.LOGO_NAME_EG_LBL
			.getString()); //$NON-NLS-1$
	private final Label _logoErrorLabel = new Label();
	private final Image _logoOkImage = new Image();
	private boolean _nameModified = false;

	// COLOURS
	private final VerticalPanel _colorPanel = new VerticalPanel();
	private final Label _logoColourHelpMark = new Label();
	private final Label _logoColourHelpLabel = new Label(
			Messages.LOGO_COLOUR_HELP_MESSAGE.getString());
	private final PopupPanel _logoColourHelpPopup = new PopupPanel(true);
	private final Label _colourLabel = new Label(Messages.LOGO_COLOUR_LBL
			.getString()); //$NON-NLS-1$
	private final HorizontalPanel _colourLabelPanel = new HorizontalPanel();
	private final Label _colourErrorLabel = new Label();
	private final Image _colourOkImage = new Image();
	private boolean _coloursModified = false;

	private final VerticalPanel _rows = new VerticalPanel();
	private final HorizontalPanel _colorSubPanel = new HorizontalPanel();
	private final HorizontalPanel _firstRow = new HorizontalPanel();
	private final HorizontalPanel _secondRow = new HorizontalPanel();
	private final HorizontalPanel _thirdRow = new HorizontalPanel();
	private final Label _pantoneTextBox = new Label();
	private final Label _colours[] = new Label[NUM_COLOURS];

	Hidden _amountInfo = new Hidden();
	Hidden _taxInfo = new Hidden();

	private Colour _selectedColour;
	private Label _selectedColourButton = null;

	// Description
	private final VerticalPanel _logoDescriptionPanel = new VerticalPanel();
	private final TextArea _logoDescBox = new TextArea();
	private final Label _logoDescHelpMark = new Label();
	private final Label _logoDescHelpLabel = new Label(
			Messages.LOGO_DESC_HELP_MESSAGE.getString());
	private final PopupPanel _logoDescHelpPopup = new PopupPanel(true);
	private final Label _logoDescLabel = new Label(Messages.LOGO_DESC_LBL
			.getString()); //$NON-NLS-1$
	private final HorizontalPanel _logoDescLabelPanel = new HorizontalPanel();
	private final Label _descHintLabel = new Label(Messages.LOGO_DESC_EG_LBL
			.getString()); //$NON-NLS-1$
	private final Label _descErrorLabel = new Label();
	private final Image _descOkImage = new Image();
	private boolean _descModified = false;

	// a pretty self-explanatory submit button
	private final Button _submitOrderButton = new Button(Messages.SEND_REQUEST
			.getString()); //$NON-NLS-1$

	// these panels are the place-holders for the drink images
	private final VerticalPanel _alternateRootPanelBody = new VerticalPanel();
	private final VerticalPanel _alternateRootPanelBodyTile = new VerticalPanel();
	private final VerticalPanel _alternateRootPanelFooter = new VerticalPanel();

	// these panels are the place olders for the drink images
	private final AbsolutePanel _alternateSubPanelBody = new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelBodyTile = new AbsolutePanel();
	private final AbsolutePanel _alternateSubPanelFooter = new AbsolutePanel();

	private final Label _waitLabel = new Label(); //$NON-NLS-1$

	private final HorizontalPanel _logoTextBoxPanel = new HorizontalPanel();

	private final HorizontalPanel _logoDescBoxPanel = new HorizontalPanel();

	// callbacks
	//la cosa di avere i callbacks statici ŽÊuna MERDATA.
	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;
	private AsyncCallback<List<OrderDTO>> _getOrdersForKillswitchOnCallback = null;
	private AsyncCallback<Long> _submitOrderCallback = null;

	private PopupPanel _micropaymentPopup = null;

	private OrderDTO _submittedOrder = null;

	private final Label _charactersLabel = new Label();

	private SWFWidget _waiterSWFWidget=null;

	Label _requestLabel = new Label(Messages.REQUEST_LOGO_LBL.getString());

	private final HandlerRegistration[] _coloursHandler =new HandlerRegistration[NUM_COLOURS];;

	@Override
	public void loadModulePart() {

		if ((RootPanel.get("orderSubmitter") != null)
				&& (RootPanel.get("orderSubmitterAlternateBody") != null)
				&& (RootPanel.get("orderSubmitterAlternateBodytile") != null)
				&& (RootPanel.get("orderSubmitterAlternateFooter") != null)) {
			hookUpCallbacks();

			if (!getModule().isKillSwitchOn()) {
				// set main panel spacing
				_mainPanel.setSpacing(3);

				// setup stuff
				setEverythingToInvisible();
				setupLogoTextPanel();
				setupLogoDescPanel();
				setupColorPickerPanel();
				setupAllHelpPopups();
				setErrorLabelsStyles();
				setSubmitButtonStuff();
				setupAlternatePanel();

				fillMainPanel();

				// setup page state
				setViewByLatestOrder();
				// Move cursor focus to the logoText input box.
				_logoTextBox.setFocus(true);

				// add main and alternate panel to root panel
				_rootPanel.add(_mainPanel);
				// inject stuff into page
				RootPanel.get("orderSubmitter").add(_rootPanel);
				RootPanel
				.get("orderSubmitter").add(getOrderSubmitPanel(), 90, 15); //$NON-NLS-1$
				RootPanel
				.get("orderSubmitterAlternateBody").add(getOrderSubmitAlternateBodyPanel()); //$NON-NLS-1$
				RootPanel
				.get("orderSubmitterAlternateBodytile").add(getOrderSubmitAlternateBodytilePanel()); //$NON-NLS-1$
				RootPanel
				.get("orderSubmitterAlternateFooter").add(getOrderSubmitAlternateFooterPanel()); //$NON-NLS-1$
			} else {
				setAlternatePanelKillswitchOn();

				((OrderServiceAsync) getService(Service.ORDER_SERVICE))
				.getOrdersForCurrentUser(_getOrdersForKillswitchOnCallback);
			}

			// apply cufon for nice fonts
			applyCufon();
		}

	}

	private void setAlternatePanelKillswitchOn() {
		// in case kill-switch is switched ON
		_waitLabel.addStyleName("waitLabel"); //$NON-NLS-1$
		_waitLabel.addStyleName("fontAR");
		_waitLabel.setWidth("350px"); //$NON-NLS-1$
		_waitLabel.addStyleName("labelButton");

		_alternateSubPanelBody.addStyleName("orderSubmitterAlternateBody"); //$NON-NLS-1$
		_alternateSubPanelBodyTile
		.addStyleName("orderSubmitterAlternateBodytile"); //$NON-NLS-1$
		_alternateSubPanelFooter.addStyleName("orderSubmitterAlternateFooter"); //$NON-NLS-1$
		_alternateSubPanelBody.add(_waitLabel, 85, 30);
		_alternateRootPanelBody.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelBodyTile.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelFooter.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelBody.add(_alternateSubPanelBody);
		_alternateRootPanelBodyTile.add(_alternateSubPanelBodyTile);
		_alternateRootPanelFooter.add(_alternateSubPanelFooter);

		RootPanel
		.get("orderSubmitterAlternateBody").add(getOrderSubmitAlternateBodyPanel()); //$NON-NLS-1$
		RootPanel
		.get("orderSubmitterAlternateBodytile").add(getOrderSubmitAlternateBodytilePanel()); //$NON-NLS-1$
		RootPanel
		.get("orderSubmitterAlternateFooter").add(getOrderSubmitAlternateFooterPanel()); //$NON-NLS-1$
	}

	private void setupAlternatePanel() {
		// alternate panel stuff
		_waitLabel.addStyleName("waitLabel"); //$NON-NLS-1$
		_waitLabel.addStyleName("fontAR");
		_waitLabel.setWidth("350px"); //$NON-NLS-1$
		_waitLabel.addStyleName("labelButton");
		_waitLabel.addClickHandler(new ClickHandler() {

			public void onClick(ClickEvent event) {
				for (AModulePart modulePart : _moduleParts) {
					if (modulePart instanceof OrderBrowserModulePart) {
						((OrderBrowserModulePart) modulePart).getLastOrder();
						notifyChanges(_submittedOrder);
						break;
					}
				}

			}
		});

		_alternateSubPanelBody.addStyleName("orderSubmitterAlternateBody"); //$NON-NLS-1$
		_alternateSubPanelBodyTile
		.addStyleName("orderSubmitterAlternateBodytile"); //$NON-NLS-1$
		_alternateSubPanelFooter.addStyleName("orderSubmitterAlternateFooter"); //$NON-NLS-1$

		_alternateSubPanelBody.add(_waitLabel, 85, 30);
		_alternateRootPanelBody.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelBodyTile.setWidth("500px"); //$NON-NLS-1$
		_alternateRootPanelFooter.setWidth("500px"); //$NON-NLS-1$

		_alternateRootPanelBody.add(_alternateSubPanelBody);
		_alternateRootPanelBodyTile.add(_alternateSubPanelBodyTile);
		_alternateRootPanelFooter.add(_alternateSubPanelFooter);
	}

	private void fillMainPanel() {
		// Fill up that son of a bitch of a mainPanel

		_requestLabel.addStyleName("h3");
		_requestLabel.addStyleName("fontAR");

		_mainPanel.add(_requestLabel);

		_mainPanel.add(_logoTextPanel);
		_mainPanel.add(_logoDescriptionPanel);
		_mainPanel.add(_colorPanel);
		_mainPanel.add(_submitOrderButton);
	}

	private void setSubmitButtonStuff() {
		_submitOrderButton.removeStyleName("gwt-Button");
		_submitOrderButton.addStyleName("submitRequest"); //$NON-NLS-1$
		_submitOrderButton.addStyleName("fontAR"); //$NON-NLS-1$

		// Listen for mouse events on the Add button.
		_submitOrderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				submitOrder();
			}
		});
	}

	private void setErrorLabelsStyles() {
		_descErrorLabel.setStyleName("errorLabel"); //$NON-NLS-1$
		_logoErrorLabel.setStyleName("errorLabel"); //$NON-NLS-1$
		_colourErrorLabel.setStyleName("errorLabel"); //$NON-NLS-1$
	}

	private void setupColorPickerPanel() {
		// Setup color picker stuff
		_colourLabel.setStyleName("label"); //$NON-NLS-1$
		_colourLabel.addStyleName("fontAR");

		_pantoneTextBox.setStyleName("pantoneLabel"); //$NON-NLS-1$
		_pantoneTextBox.addStyleName("fontAR");

		_colourLabelPanel.add(_logoColourHelpMark);
		_colourLabelPanel.add(getNewWhiteSpace(5));
		_colourLabelPanel.add(_colourLabel);

		_colorSubPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		_colorSubPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
		_colorSubPanel.add(_colourLabelPanel);
		_colorSubPanel.add(getNewWhiteSpace(5));
		_colorSubPanel.add(_pantoneTextBox);
		_colorSubPanel.setHeight("15px"); //$NON-NLS-1$

		_colorPanel.add(_colorSubPanel);
		_colorPanel.add(_colourErrorLabel);

		configureColoursPanels();

		_colorPanel.setWidth("300px"); //$NON-NLS-1$
		_colorPanel.setSpacing(2);

		_rows.setSpacing(0);
		_rows.add(_firstRow);
		_rows.add(_secondRow);
		_rows.add(_thirdRow);

		_colorPanel.add(_rows);
	}

	private void setupAllHelpPopups() {
		// 3.Setup help popups for Logo Text - Tags - Color choice
		_logoTextHelpPopup.setStyleName("helpPopup");
		_logoDescHelpPopup.setStyleName("helpPopup");
		_logoColourHelpPopup.setStyleName("helpPopup");

		_logoTextHelpMark.addStyleName("infoButton");
		_logoTextHelpMark.addStyleName("labelButton");
		_logoDescHelpMark.addStyleName("infoButton");
		_logoDescHelpMark.addStyleName("labelButton");
		_logoColourHelpMark.addStyleName("infoButton");
		_logoColourHelpMark.addStyleName("labelButton");

		_logoTextHelpPopup.setWidth("220px");
		_logoDescHelpPopup.setWidth("220px");
		_logoColourHelpPopup.setWidth("220px");

		_logoTextHelpMark.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!_logoTextHelpPopup.isShowing()) {
					_logoTextHelpPopup.showRelativeTo(_logoTextLabel);
					_logoTextHelpMark.removeStyleName("infoButton");
					_logoTextHelpMark.addStyleName("infoButtonClicked");
				}
			}
		});

		_logoTextHelpPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			public void onClose(CloseEvent<PopupPanel> event) {
				_logoTextHelpMark.addStyleName("infoButton");
				_logoTextHelpMark.removeStyleName("infoButtonClicked");
			}
		});

		_logoTextHelpPopup.setWidget(_logoTextHelpLabel);

		_logoDescHelpMark.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!_logoDescHelpPopup.isShowing()) {
					_logoDescHelpPopup.showRelativeTo(_logoDescLabel);
					_logoDescHelpMark.removeStyleName("infoButton");
					_logoDescHelpMark.addStyleName("infoButtonClicked");
				}
			}
		});

		_logoDescHelpPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			public void onClose(CloseEvent<PopupPanel> event) {
				_logoDescHelpMark.addStyleName("infoButton");
				_logoDescHelpMark.removeStyleName("infoButtonClicked");
			}
		});

		_logoDescHelpPopup.setWidget(_logoDescHelpLabel);

		_logoColourHelpMark.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (!_logoColourHelpPopup.isShowing()) {
					_logoColourHelpPopup.showRelativeTo(_colourLabel);
					_logoColourHelpMark.removeStyleName("infoButton");
					_logoColourHelpMark.addStyleName("infoButtonClicked");
				}
			}
		});

		_logoColourHelpPopup.addCloseHandler(new CloseHandler<PopupPanel>() {
			public void onClose(CloseEvent<PopupPanel> event) {
				_logoColourHelpMark.removeStyleName("infoButtonClicked");
				_logoColourHelpMark.addStyleName("infoButton");
			}
		});

		_logoColourHelpPopup.setWidget(_logoColourHelpLabel);
	}

	private void setupLogoDescPanel() {
		// 2.Tags section setup
		_logoDescLabel.addStyleName("label");
		_logoDescLabel.addStyleName("fontAR");
		_charactersLabel.addStyleName("charsLabel");
		_charactersLabel.addStyleName("fontAR");
		_descHintLabel.addStyleName("hintLabel");

		_logoDescBox.setWidth("290px");
		_logoDescBox.setText(Messages.LOGO_DESC_TXTBOX.getString());
		_logoDescBox.setStyleName("textDescBox");
		_logoDescBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				setDescModified(true);
				checkErrors();
			}
		});

		_logoDescBox.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(_logoDescBox.getText().equals(Messages.LOGO_DESC_TXTBOX.getString()) || _logoDescBox.getText().equals(Messages.LOGO_DESC_TXTBOX_REVISION.getString()))
				{
					_logoDescBox.selectAll();
				}
			}
		});
		_logoDescBox.addKeyPressHandler(new KeyPressHandler() {

			@Override
			public void onKeyPress(KeyPressEvent event) {
				_charactersLabel.setText(250 - _logoDescBox.getText().length()
						+ " chars left");
				if (_logoDescBox.getText().length() > 250) {
					_charactersLabel.removeStyleName("timerGreen");
					_charactersLabel.addStyleName("timerRed");

				} else {
					_charactersLabel.removeStyleName("timerRed");
					_charactersLabel.addStyleName("timerGreen");
				}
				applyCufon();

			}
		});

		_logoDescBoxPanel.add(_logoDescBox);
		_logoDescBoxPanel.add(getNewWhiteSpace(10));
		_logoDescBoxPanel.add(_descOkImage);

		_logoDescriptionPanel.setSpacing(2);

		_logoDescLabelPanel.setVerticalAlignment(HorizontalPanel.ALIGN_BOTTOM);
		_logoDescLabelPanel.add(_logoDescHelpMark);
		_logoDescLabelPanel.add(getNewWhiteSpace(5));
		_logoDescLabelPanel.add(_logoDescLabel);
		_logoDescLabelPanel.add(getNewWhiteSpace(125));
		_logoDescLabelPanel.add(_charactersLabel);

		_logoDescriptionPanel.add(_logoDescLabelPanel);
		_logoDescriptionPanel.add(_descHintLabel);
		_logoDescriptionPanel.add(_logoDescBoxPanel);
		_logoDescriptionPanel.add(_descErrorLabel);
	}

	private void setupLogoTextPanel() {
		// 1.Logo Name section setup
		_logoTextLabel.addStyleName("label"); //$NON-NLS-1$
		_logoTextLabel.addStyleName("fontAR");
		_logoHintLabel.setStyleName("hintLabel"); //$NON-NLS-1$

		_logoTextBox.setText(Messages.LOGO_NAME_TXTBOX.getString()); //$NON-NLS-1$
		_logoTextBox.setWidth("290px"); //$NON-NLS-1$
		_logoTextBox.setStyleName("textBox"); //$NON-NLS-1$
		_logoTextBox.addStyleName("fontAR");
		_logoTextBox.addBlurHandler(new BlurHandler() {
			public void onBlur(BlurEvent event) {
				setNameModified(true);
				checkErrors();
			}
		});

		_logoTextBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				if (_logoTextBox.getText().equals(
						Messages.LOGO_NAME_TXTBOX.getString())) //$NON-NLS-1$
				{
					_logoTextBox.selectAll();
				}
			}
		});

		_logoTextBoxPanel.add(_logoTextBox);
		_logoTextBoxPanel.add(getNewWhiteSpace(10));
		_logoTextBoxPanel.add(_logoOkImage);

		// prepare logoText vertical panel
		_logoTextPanel.setSpacing(2);

		_logoTextLabelPanel.setVerticalAlignment(VerticalPanel.ALIGN_TOP);
		_logoTextLabelPanel.add(_logoTextHelpMark);
		_logoTextLabelPanel.add(getNewWhiteSpace(10));
		_logoTextLabelPanel.add(_logoTextLabel);

		_logoTextPanel.add(_logoTextLabelPanel);
		_logoTextPanel.add(_logoHintLabel);
		_logoTextPanel.add(_logoTextBoxPanel);
		_logoTextPanel.add(_logoErrorLabel);
	}

	private void setEverythingToInvisible() {
		// set everything to invisible
		setNameModified(false);
		setDescModified(false);
		setColourModified(false);
		_mainPanel.setVisible(false);
	}

	private void hideValidationImages() {
		_colourOkImage.setVisible(false);
		_logoOkImage.setVisible(false);
		_descOkImage.setVisible(false);
	}

	/**
	 * @param i
	 * @return
	 */
	private Widget getNewWhiteSpace(Integer i) {
		Label whiteSpace = new Label();
		whiteSpace.setWidth(i.toString() + "px");
		return whiteSpace;
	}

	private void hookUpCallbacks() {
		// Set up the callback object
		_getOrdersCallback = new AsyncCallback<List<OrderDTO>>() {

			public void onSuccess(List<OrderDTO> result) {
				// check state and accordingly set alternate/main panel switch
				setShowHideStateByLatestOrder(OrderDTOUtils
						.getMostRecentOrder(result));
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		};

		// set up get latest order callback
		_getOrdersForKillswitchOnCallback = new AsyncCallback<List<OrderDTO>>() {

			public void onSuccess(List<OrderDTO> result) {
				// sets wait message when killswitch is on depending on the
				// latest order
				setMessageWithKillswitchOn(OrderDTOUtils
						.getMostRecentOrder(result));
			}

			public void onFailure(Throwable caught) {
				// on failure la porcata - defaulta a killswitch
				_waitLabel.setText(Messages.ERROR_WAITMSG.getString());
			}
		};

		// Set up the callback object.
		_submitOrderCallback = new AsyncCallback<Long>() {
			public void onFailure(Throwable caught) {
				//				_messageLabel.setText(Messages.GENERIC_ERROR.getString() + caught.getMessage()); //$NON-NLS-1$
			}

			public void onSuccess(Long result) {
				//QUESTI IF SONO LA PORCATA I CALLBACK VANNO ANONYMOUS INLINED NELLA CALL
				if (result != null && getSubmittedOrder() != null) {
					getSubmittedOrder().setId(result);

					_submitOrderButton.setEnabled(true);
					_submitOrderButton.setText(Messages.SEND_REQUEST.getString());
					if(getSubmittedOrder().getRevisionCounter()>0)
					{
						hideMainPanelShowAlternate(getSubmittedOrder());
						notifyChanges(getSubmittedOrder());
					}
					else
					{
						if (_micropaymentPopup == null
								|| !_micropaymentPopup.isShowing()) {
							//the micropayment popup panel is not open
							_micropaymentPopup = getMicroPaymentPanel();
							_micropaymentPopup.center(); 
							_micropaymentPopup.show();
							applyCufon();
						}
						else
						{
							_micropaymentPopup.hide();
							hideMainPanelShowAlternate(getSubmittedOrder());
						}
					}
					// QUESTO DOVRA ESSERE FATTO NELLA SERVLET QUANDO ARRIVA IL
					// PAGAMENTO..forse
					// updateAlternatePanelMessage(_submittedOrder, result);
					//
					// _mainPanel.setVisible(false);
					//
					// showHidePanels();
					// notifyChanges();
				}
			}
		};

	}

	protected void setMessageWithKillswitchOn(OrderDTO latestOrder) {
		// sets wait message when killswitch is on depending on the latest order

		if(latestOrder==null || ( latestOrder.getStatus() == Status.BOUGHT || latestOrder.getStatus() == Status.REJECTED || latestOrder.getStatus() == Status.PENDING))
		{
			//set wait label text --> killswitch message
			_waitLabel.setText(Messages.valueOf("KILLSWITCH_ON_WAITMSG").getString());
		}
		else
		{
			//set wait label text according to latest order status
			updateAlternatePanelMessage(latestOrder,false);
		}

	}

	private void configureColoursPanels() {
		_firstRow.setSpacing(1);
		_secondRow.setSpacing(1);
		_thirdRow.setSpacing(1);

		for (int i = 0; i < NUM_COLOURS; i++) {
			_colours[i] = new Label();
			_colours[i].setHeight("28px"); //$NON-NLS-1$
			_colours[i].setWidth("28px"); //$NON-NLS-1$
			_colours[i].addStyleName("colour" + Colour.values()[i].toString()); //$NON-NLS-1$
			_colours[i].addStyleName("colourNormal"); //$NON-NLS-1$
			_colours[i].addStyleName("colourBorder"); //$NON-NLS-1$

			final int index = i;
			_coloursHandler[i]=_colours[i].addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					//no possible to change the colour if there is a request ongoing
					if(_submittedOrder==null || !_submittedOrder.isReviewRequestOngoing())
					{
						_pantoneTextBox.setText(Colour.values()[index].getName());
						if (_selectedColourButton != null) {
							_selectedColourButton.removeStyleName("colourSelected"); //$NON-NLS-1$
							_selectedColourButton.addStyleName("colourNormal"); //$NON-NLS-1$
						}
						_selectedColour = Colour.values()[index];
						_colours[index].removeStyleName("colourNormal"); //$NON-NLS-1$
						_colours[index].addStyleName("colourSelected"); //$NON-NLS-1$
						_selectedColourButton = _colours[index];
						setColourModified(true);
						checkErrors();
					}

				}
			});

			if (i < 8)
				_firstRow.add(_colours[i]);
			else if (i < 16)
				_secondRow.add(_colours[i]);
			else if (i < NUM_COLOURS)
				_thirdRow.add(_colours[i]);
		}

		// Lucky Number Seven
		_colours[7].setText("?");
		_colours[7].addStyleName("fontAR");
		_colours[7].addStyleName("colourSurpriseMeHomeSubmission");

		_firstRow.add(getNewWhiteSpace(26));
		_firstRow.add(_colourOkImage);

	}

	private FormPanel getPayPalForm() {
		final FormPanel paypalForm = new FormPanel("");
		boolean isSandbox = getModule().isSandBox();
		// fill-up paypal form
		if (isSandbox) {
			paypalForm.setAction(PayPalStrings.PAYPAL_SANDBOX_ACTION
					.getString());
		} else {
			paypalForm.setAction(PayPalStrings.PAYPAL_ACTION.getString());
		}

		paypalForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		paypalForm.setMethod(FormPanel.METHOD_POST);

		VerticalPanel formPlaceHolder = new VerticalPanel();

		// setup input element for seller
		Hidden sellerInfo = new Hidden();

		sellerInfo.setName(PayPalStrings.PAYPAL_BUSINESS_NAME.getString());

		if (isSandbox) {
			sellerInfo.setValue(PayPalStrings.PAYPAL_SANDBOX_BUSINESS_VALUE
					.getString());
		} else {
			sellerInfo
			.setValue(PayPalStrings.PAYPAL_BUSINESS_VALUE.getString());
		}

		formPlaceHolder.add(sellerInfo);
		// specify buy now button
		Hidden cmdInfo = new Hidden();

		cmdInfo.setName(PayPalStrings.PAYPAL_CMD_NAME.getString());

		cmdInfo.setValue(PayPalStrings.PAYPAL_CMD_VALUE.getString());

		formPlaceHolder.add(cmdInfo);
		// specify purchase details
		Hidden itemNameInfo = new Hidden();
		_amountInfo = new Hidden();
		_taxInfo = new Hidden();
		Hidden currencyInfo = new Hidden();
		Hidden notifyInfo = new Hidden();
		Hidden returnInfo = new Hidden();
		Hidden custom = new Hidden();
		Hidden locale = new Hidden();

		itemNameInfo.setName(PayPalStrings.PAYPAL_ITEMNAME_NAME.getString());
		itemNameInfo.setValue(TransactionType.MICROPAYMENT.getDescription());
		formPlaceHolder.add(itemNameInfo);

		_amountInfo.setName(PayPalStrings.PAYPAL_AMOUNT_NAME.getString());
		_amountInfo.setValue(TransactionType.MICROPAYMENT.getNet(
				getSubmittedOrder().getTip()).toString());
		formPlaceHolder.add(_amountInfo);

		_taxInfo.setName(PayPalStrings.PAYPAL_TAX_NAME.getString());
		_taxInfo.setValue(TransactionType.MICROPAYMENT.getTax(
				getSubmittedOrder().getTip()).toString());
		formPlaceHolder.add(_taxInfo);

		currencyInfo.setName(PayPalStrings.PAYPAL_CURRENCY_NAME.getString());
		currencyInfo.setValue(PayPalStrings.PAYPAL_CURRENCY_VALUE.getString());
		formPlaceHolder.add(currencyInfo);

		notifyInfo.setName(PayPalStrings.PAYPAL_NOTIFY_URL_NAME.getString());
		if (isSandbox) {
			notifyInfo.setValue(PayPalStrings.PAYPAL_NOTIFY_URL_SANDBOX_VALUE
					.getString());
		} else {
			notifyInfo.setValue(PayPalStrings.PAYPAL_NOTIFY_URL_VALUE
					.getString());

		}
		formPlaceHolder.add(notifyInfo);

		returnInfo.setName(PayPalStrings.PAYPAL_RETURN_NAME.getString());
		returnInfo.setValue(PayPalStrings.PAYPAL_RETURN_VALUE.getString());

		custom.setName(PayPalStrings.PAYPAL_CUSTOM_NAME.getString());
		custom.setValue(getSubmittedOrder().getId().toString()+";"+TransactionType.MICROPAYMENT.toString());
		formPlaceHolder.add(custom);

		locale.setName(PayPalStrings.PAYPAL_LOCALE_NAME.getString());
		locale.setValue(PayPalStrings.PAYPAL_LOCALE_NAME.getString());
		formPlaceHolder.add(locale);

		// setup submit button
		final Button payTipButton = new Button();
		payTipButton.removeStyleName("gwt-Button");
		payTipButton.setStyleName("sendTip");
		payTipButton.addClickHandler(new ClickHandler(){


			public void onClick(ClickEvent event) 
			{
				payTipButton.setEnabled(false);
				if(!DEBUG)
				{
					((OrderServiceAsync)getService(Service.ORDER_SERVICE)).submitOrder(getSubmittedOrder(), _submitOrderCallback);


					//setup submit handlers

					paypalForm.submit();

				}
				else //DEBUGGING, don't open paypal, not required, change instead the status to INCOMING
				{
					getSubmittedOrder().setStatus(Status.INCOMING);
					((OrderServiceAsync)getService(Service.ORDER_SERVICE)).submitOrder(getSubmittedOrder(), _submitOrderCallback);
					//					((OrderServiceAsync)getService(Service.ORDER_SERVICE)).setOrderStatus(getSubmittedOrder().getId(), Status.INCOMING, _setOrderStatusCallback);
				}
			}
		});
		formPlaceHolder.add(payTipButton);

		paypalForm.add(formPlaceHolder);

		return paypalForm;
	}

	/**
	 * 
	 */
	private void updateHiddenTip() 
	{
		_amountInfo.setValue(TransactionType.MICROPAYMENT.getNet(
				getSubmittedOrder().getTip()).toString());
		_taxInfo.setValue(TransactionType.MICROPAYMENT.getTax(
				getSubmittedOrder().getTip()).toString());
	}

	/**
	 * @param swfWidget
	 * @param status
	 * @return
	 */
	native String sendStatusToSeppia(Element swfWidget, String status) /*-{
		swfWidget.childNodes[0].sendToActionScript(status);
	}-*/;

	// COMMENTING THE MICROPAYMENT POPUP PANEL FOR NOW.
	// WE NEVER KNOW IF WE CHANGE OUR MIND AGAIN.
	// Update: WISE MAN!!! Panel is back
	// sweet Johnny Drama

	private PopupPanel getMicroPaymentPanel()
	{
		FormPanel paypalForm=getPayPalForm();

		//setup submit button
		final VerticalPanel microPaymentPanel=new VerticalPanel();

		final SWFWidget waiterSWFWidget = getWaiterSWFWidget(Images.WAITER.getImageURL());

		Image tipInstructions = new Image(Images.TIP_INSTRUCTIONS.getImageURL());

		HorizontalPanel tipHPanel =new HorizontalPanel();

		tipHPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);

		final Label tipBox=new Label();

		if(getSubmittedOrder().getTip()==null)
		{
			getSubmittedOrder().setTip(new Float(6.5f));
		}
		tipBox.setText(getSubmittedOrder().getTip()+Messages.EUR.getString()); //$NON-NLS-1$
		tipBox.setWidth("100px"); //$NON-NLS-1$
		tipBox.setHeight("19px");
		tipBox.setStyleName("tipLabel"); //$NON-NLS-1$
		tipBox.addStyleName("fontAR");


		VerticalPanel tipChangeVPanel=new VerticalPanel();
		tipChangeVPanel.setSpacing(5);

		Label decreaseTip=new Label();
		decreaseTip.setStyleName("downArrow");
		decreaseTip.addStyleName("labelButton");
		decreaseTip.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				//it's already the minimum value 
				if(!getSubmittedOrder().getTip().equals(TransactionType.MICROPAYMENT.getValue()))
				{
					setWaiterMood(waiterSWFWidget,getSubmittedOrder().getTip(),getSubmittedOrder().getTip()-0.5f);
					getSubmittedOrder().setTip(getSubmittedOrder().getTip()-0.5f);
					updateHiddenTip();
					tipBox.setText(getSubmittedOrder().getTip()+Messages.EUR.getString()); //$NON-NLS-1$
					applyCufon();
				}
			}
		});

		Label increaseTip=new Label();
		increaseTip.setStyleName("upArrow");
		increaseTip.addStyleName("labelButton");
		increaseTip.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				if(!getSubmittedOrder().getTip().equals(98f))
				{
					setWaiterMood(waiterSWFWidget,getSubmittedOrder().getTip(),getSubmittedOrder().getTip()+0.5f);
					getSubmittedOrder().setTip(getSubmittedOrder().getTip()+0.5f);
					updateHiddenTip();
					tipBox.setText(getSubmittedOrder().getTip()+Messages.EUR.getString()); //$NON-NLS-1$
					applyCufon();
				}
			}



		});





		tipChangeVPanel.add(increaseTip);
		tipChangeVPanel.add(decreaseTip);

		tipHPanel.add(tipBox);
		tipHPanel.add(tipChangeVPanel);
		tipHPanel.add(paypalForm);



		microPaymentPanel.setSpacing(10);
		microPaymentPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		microPaymentPanel.add(waiterSWFWidget);
		microPaymentPanel.add(tipHPanel);
		microPaymentPanel.add(tipInstructions);

		PopupPanel micropaymentPopup= new PopupPanel(true); 
		micropaymentPopup.setStyleName("microPaymentPopup");
		micropaymentPopup.setWidget(microPaymentPanel);
		micropaymentPopup.setWidth("350px");
		return micropaymentPopup;

	}

	/**
	 * @param waiterSWFWidget
	 * @param oldtip
	 * @param newtip
	 */
	private void setWaiterMood(SWFWidget waiterSWFWidget, double oldtip, double newtip)
	{
		String mood="start";
		if(newtip>oldtip)
		{
			mood="smile";
		}
		else if (newtip<oldtip)
		{
			mood="sad";
		}
		if(newtip>=15d)
		{
			sendStatusToSeppia(waiterSWFWidget.getElement(),"start");
			mood="smokingrock";
		}
		else if(newtip>=12d)
		{
			sendStatusToSeppia(waiterSWFWidget.getElement(),"start");
			mood="smokinghot";
		}
		else if(newtip>=9d)
		{
			sendStatusToSeppia(waiterSWFWidget.getElement(),"start");
			mood="smokingcool";
		}
		else if(newtip>=7d)
		{
			sendStatusToSeppia(waiterSWFWidget.getElement(),"start");
			mood="smoking";
		}
		sendStatusToSeppia(waiterSWFWidget.getElement(),mood);
	}

	/**
	 * @param imageURL
	 * @return
	 */
	private SWFWidget getWaiterSWFWidget(String imageURL) {
		if(_micropaymentPopup!=null && _micropaymentPopup.isShowing())
		{
			return _waiterSWFWidget;
		}
		else
		{
			_waiterSWFWidget = new SWFWidget(imageURL);
			_waiterSWFWidget.setWidth("300px");
			_waiterSWFWidget.setHeight("300px");
			_waiterSWFWidget.setVisible(true);
			return _waiterSWFWidget; 
		}
	}

	/**
	 * 
	 */
	protected void submitOrder() {
		setNameModified(true);
		setDescModified(true);
		setColourModified(true);
		if (checkErrors()) {
			if(getSubmittedOrder()!=null && getSubmittedOrder().getRevisionCounter()==0)
			{
				//stiamo inviando l'ordine la prima volta, si aprira il pannellino una volta ricevuta la risposta
				//se la connessione ŽÊlenta ci potrebbe volere un po, nel frattempo disabilito il buttone
				//per evitare succedano casini
				_submitOrderButton.setText(Messages.WAIT.getString());
				_submitOrderButton.setEnabled(false);
				getSubmittedOrder().setStatus(Status.PENDING);
				String[] descriptions = { _logoDescBox.getText() };
				getSubmittedOrder().setDescriptions(descriptions);
				getSubmittedOrder().setText(_logoTextBox.getText());
				getSubmittedOrder().setColour(_selectedColour);
			}
			else
			{
				//-1 because the first one is not a revision
				if(getSubmittedOrder().getDescriptions().length-1<getSubmittedOrder().getRevisionCounter())
				{
					//going to REVIEWING (there is a check in the server code,
					getSubmittedOrder().setStatus(Status.REVIEWING);
					//the text and the colour of the logo can't change

					String[] descriptions = new String[getSubmittedOrder().getDescriptions().length+1];
					int i=0;
					for(String description:getSubmittedOrder().getDescriptions())
					{
						descriptions[i++]=description;
					}
					descriptions[getSubmittedOrder().getDescriptions().length]= _logoDescBox.getText();
					getSubmittedOrder().setDescriptions(descriptions);
					((OrderServiceAsync) getService(Service.ORDER_SERVICE))
					.setOrderStatus(getSubmittedOrder().getId(), Status.REVIEWING, new AsyncCallback<Long>() {

						public void onSuccess(Long result) {
							//setViewByLatestOrder(); //WHY??
							getSubmittedOrder().setStatus(Status.REVIEWING);
							notifyChanges(_submittedOrder);
						}

						public void onFailure(Throwable caught) {
							// TODO give feedback to the user that something went wrong!
						}
					});
				}
			}
			((OrderServiceAsync) getService(Service.ORDER_SERVICE))
			.submitOrder(getSubmittedOrder(), _submitOrderCallback);
		}
	}

	/**
	 * 
	 */
	private OrderDTO getSubmittedOrder() {
		if (_submittedOrder == null) {
			_submittedOrder = new OrderDTO();
		}
		return _submittedOrder;
	}

	/**
	 * @return
	 */
	private boolean checkErrors() {
		Validator.DescriptionErrors descError = DescriptionErrors.NONE;
		Validator.LogoErrors logoError = LogoErrors.NONE;
		Validator.ColourErrors colourError = ColourErrors.NONE;

		if (isDescModified()) {
			descError = Validator.validateDescription(_logoDescBox.getText());
			setTagErrorStatus(descError);
		}

		if (isNameModified()) {
			logoError = Validator.validateLogoName(_logoTextBox.getText());
			setLogoNameError(logoError);
		}

		if (isColoursModified()) {
			colourError = Validator.validateColour(_selectedColour);
			setColourError(colourError);
		}

		return descError.equals(DescriptionErrors.NONE)
		&& colourError.equals(ColourErrors.NONE)
		&& logoError.equals(LogoErrors.NONE);

	}

	/**
	 * @param logoError
	 */
	private void setLogoNameError(LogoErrors logoError) {
		switch (logoError) {
		case EMPTY:
			_logoErrorLabel.setText(Messages.LOGO_NAME_ERROR_NONE.getString());
			_logoOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case TOO_LONG:
			_logoErrorLabel.setText(Messages.LOGO_NAME_ERROR_TOOLONG
					.getString());
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
	private void setColourError(ColourErrors colourError) {
		switch (colourError) {
		case NO_COLOR:
			_colourErrorLabel.setText(Messages.LOGO_COLOUR_ERROR_NONE
					.getString());
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
	private void setTagErrorStatus(DescriptionErrors tagsError) {
		switch (tagsError) {
		case TOO_SHORT:
			_descErrorLabel.setText(Messages.LOGO_DESC_ERROR_TOOSHORT
					.getString());
			_descOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case NONE:
			_descErrorLabel.setText("");
			_descOkImage.setUrl(Images.OK.getImageURL());
			break;
		case TOO_LONG:
			_descErrorLabel.setText(Messages.LOGO_DESC_ERROR_TOOLONG
					.getString());
			_descOkImage.setUrl(Images.NOK.getImageURL());
			break;
		case EMPTY:
			_descErrorLabel.setText(Messages.LOGO_DESC_ERROR_EMPTY.getString());
			_descOkImage.setUrl(Images.NOK.getImageURL());
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
			//			if(order.getStatus() != Status.VIEWED)
			//			{
			if(order.hasCompletedReview())
			{
				this._waitLabel.setText(Messages.valueOf("RE"+order.getStatus().toString()+"_WAITMSG").getString()); //$NON-NLS-1$

			}
			else
			{
				this._waitLabel.setText(Messages.valueOf(order.getStatus().toString()+"_WAITMSG").getString()); //$NON-NLS-1$
			}
		}
		//			}
		//			else
		//			{
		//				this._waitLabel.setText(Messages.VIEWED_WAITMSG.getString());
		//			}

		else 
		{
			this._waitLabel.setText(Messages.ERROR_WAITMSG.getString());
		}

		applyCufon();
	}


	/**
	 * Gets latest order and hooks up event success/failure handlers
	 */
	protected void setViewByLatestOrder() {
		((OrderServiceAsync) getService(Service.ORDER_SERVICE))
		.getOrdersForCurrentUser(_getOrdersCallback);
	}

	/**
	 * 
	 */
	protected void showHidePanels() {
		if (_mainPanel.isVisible()) {
			// nascondi le cazzo di immagini per IE
			hideValidationImages();
			showAlternatePanels(false);
		} else {
			_logoOkImage.setVisible(false);
			_colourOkImage.setVisible(false);
			_descOkImage.setVisible(false);
			showAlternatePanels(true);
		}
	}

	/**
	 * @param result
	 */

	protected void setShowHideStateByLatestOrder(OrderDTO result) 
	{
		if(result==null || (result.getStatus() == Status.BOUGHT || result.getStatus() == Status.REJECTED|| result
				.getStatus() == Status.PENDING || result.isReviewRequestOngoing()))
		{
			_submittedOrder=result;
			_mainPanel.setVisible(true);
			_requestLabel.setText(Messages.REQUEST_LOGO_LBL.getString());

			// nascondi le cazzo di immagini per IE
			hideValidationImages();
			showAlternatePanels(false);

			if(result!=null)
			{
				if (result.getStatus().equals(Status.PENDING)) {
					// load in the submitter the data from the pending request
					_logoDescBox.setText(result.getDescriptions()[0]);
					_logoTextBox.setText(result.getText());

					_pantoneTextBox.setText(result.getColour().getName());
					int index = Colour.indexOf(result.getColour());
					_selectedColourButton = _colours[index];
					_selectedColour = result.getColour();
					_colours[index].removeStyleName("colourNormal"); //$NON-NLS-1$
					_colours[index].addStyleName("colourSelected"); //$NON-NLS-1$

					setNameModified(true);
					setDescModified(true);
					setColourModified(true);
					checkErrors();
				}
				if(result.isReviewRequestOngoing())
				{
					_requestLabel.setText(Messages.REVISION_LOGO_LBL.getString());

					// load in the submitter the data from the pending request

					_logoDescBox.setText(Messages.LOGO_DESC_TXTBOX_REVISION.getString());
					_logoTextBox.setText(result.getText());
					_logoTextBox.setEnabled(false);
					_pantoneTextBox.setText(result.getColour().getName());
					int index = Colour.indexOf(result.getColour());
					_selectedColourButton = _colours[index];
					_selectedColour = result.getColour();
					_colours[index].removeStyleName("colourNormal"); //$NON-NLS-1$
					_colours[index].addStyleName("colourSelected"); //$NON-NLS-1$

					setNameModified(true);
					setColourModified(true);
					checkErrors();

				}
			}
		} else {
			hideMainPanelShowAlternate(result);

		}
		applyCufon();
	}


	//
	//	/**
	//	 * 
	//	 */
	//	private void disableColours()
	//	{
	//		for (int i = 0; i < NUM_COLOURS; i++) {
	//			if(_coloursHandler[i]!=null)
	//			{
	//				_coloursHandler[i].removeHandler();
	//			}
	//		}		
	//	}

	private void hideMainPanelShowAlternate(OrderDTO result)
	{
		_mainPanel.setVisible(false);
		updateAlternatePanelMessage(result, false);
		_logoOkImage.setVisible(false);
		_colourOkImage.setVisible(false);
		_descOkImage.setVisible(false);
		showAlternatePanels(true);
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitPanel() {
		return _mainPanel;
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitAlternateFooterPanel() {
		return _alternateRootPanelFooter;
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitAlternateBodyPanel() {
		return _alternateRootPanelBody;
	}

	/**
	 * @return
	 */
	private Widget getOrderSubmitAlternateBodytilePanel() {
		return _alternateRootPanelBodyTile;
	}

	/**
	 * @param show
	 */
	private void showAlternatePanels(boolean show) {
		_alternateSubPanelBody.setVisible(show);
		_alternateSubPanelBodyTile.setVisible(show);
		_alternateSubPanelFooter.setVisible(show);
	}

	@Override
	public void updateModulePart(OrderDTO selection) {
		//		if(selection.getRevisionCounter()>0)
		//		{
		if(selection!=null)
		{
			_submittedOrder=selection;
			setShowHideStateByLatestOrder(_submittedOrder);
		}
		//		}

	}

	private boolean isNameModified() {
		return _nameModified;
	}

	private void setNameModified(boolean nameModified) {
		_nameModified = nameModified;
		_logoOkImage.setVisible(nameModified);
	}

	private boolean isColoursModified() {
		return _coloursModified;
	}

	private void setColourModified(boolean coloursModified) {
		_coloursModified = coloursModified;
		_colourOkImage.setVisible(coloursModified);
	}

	private boolean isDescModified() {
		return _descModified;
	}

	private void setDescModified(boolean descModified) {
		_descModified = descModified;
		_descOkImage.setVisible(descModified);

	}

}

