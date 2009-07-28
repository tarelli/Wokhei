/**
 * 
 */
package com.brainz.wokhei.client.home;

import java.util.Arrays;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.resources.HtmlLicenses;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.resources.PayPalStrings;
import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.OrderDTOUtils;
import com.brainz.wokhei.shared.Status;
import com.codelathe.gwt.client.SlideShow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;

/**
 * @author matteocantarelli
 *
 */
public class OrderBrowserModulePart extends AModulePart{

	private final AbsolutePanel mainPanel = new AbsolutePanel();

	private final VerticalPanel ordersPanel = new VerticalPanel();

	private final Label orderNameLabel = new Label();

	private final Label orderTagsLabel = new Label();

	private final Image orderImage = new Image();

	private final Label colour= new Label();

	private final Label infoButton = new Label();

	private final Image infos = new Image();

	private final Label statusDescription = new Label();

	private final Label statusTitle = new Label();

	private final Label previousOrderButton = new Label();

	private final Label nextOrderButton = new Label();

	private OrderDTO _currentOrder=null;

	private List<OrderDTO> _orders=null;

	private final Label orderDateLabel = new Label();

	private final Label colourLabel = new Label();

	private final HorizontalPanel colourPanel = new HorizontalPanel();

	private VerticalPanel downloadPanel=null;

	private final VerticalPanel downloadPanelContainer=new VerticalPanel();

	private final Label colourSpace = new Label();

	private final SlideShow slideShow = new SlideShow();

	// will load-up the panel with license + paypal 
	private final Label _buyNowImage = new Label();
	private final PopupPanel _buyNowPopUpPanel = new PopupPanel(true);
	private final VerticalPanel _buyNowPanel = new VerticalPanel();
	private final HTMLPanel _licenseText = new HTMLPanel(HtmlLicenses.COMMERCIAL_LICENSE.getString());
	private final CheckBox _acceptLicenseCheckBox = new CheckBox(Messages.ACCEPT_CONDITIONS.getString());
	private final Label _feedBackLabel = new Label("");
	private final FormPanel _paypalForm = new FormPanel("");
	private final PopupPanel _acceptAgreementPopupPanel= new PopupPanel(true);

	// Enquiry for archived logos
	private final Label _forgotToBuyLbl = new Label(Messages.ENQUIRY_ARCHIVED_QUESTION.getString());
	private final Label _sendEnquiryLblBtn = new Label(Messages.ENQUIRY_ARCHIVED_ACTION.getString()); 
	private final Label _enquiryFeedback = new Label("");
	private final HorizontalPanel _enquiryPanel = new HorizontalPanel();

	private AsyncCallback<Long> _setOrderStatusCallback = null;

	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;

	private AsyncCallback<Boolean> _sendEnquiryCallback = null;

	private boolean _buyNowLoaded=false;

	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("ordersBrowser")!=null)
		{
			hookUpCallbacks();

			getOrdersForCurrentCustomer();

			setupLightBox();

			setupEnquiryControls();

			setupAcceptAgreement();		

			previousOrderButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getPreviousOrder();

					updatePanel();
				}
			});

			nextOrderButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					getNextOrder();

					updatePanel();
				}
			});

			infoButton.addStyleName("infoButton");
			infoButton.addStyleName("labelButton");
			infoButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					infos.setVisible(!infos.isVisible());
					if(infos.isVisible())
					{
						infoButton.removeStyleName("infoButton");
						infoButton.addStyleName("infoButtonClicked");
					}
					else
					{
						infoButton.removeStyleName("infoButtonClicked");
						infoButton.addStyleName("infoButton");
					}
				}
			});

			previousOrderButton.setStyleName("leftArrow");
			previousOrderButton.addStyleName("labelButton");
			nextOrderButton.setStyleName("rightArrow");
			nextOrderButton.addStyleName("labelButton");
			orderNameLabel.setStyleName("logoNameLabel");
			orderNameLabel.addStyleName("fontAR");
			orderTagsLabel.setStyleName("logoTagsDateLabel");
			orderDateLabel.setStyleName("logoTagsDateLabel");

			infos.setVisible(false);
			infos.setUrl(Images.INFOS.getImageURL());

			mainPanel.setHeight("450px");
			mainPanel.setWidth("600px");
			colour.setHeight("10px");
			colour.setWidth("10px");
			statusDescription.setHeight("150px");
			statusDescription.setWidth("250px");
			statusDescription.setStylePrimaryName("statusDescription");
			statusTitle.setStylePrimaryName("statusTitle");
			statusTitle.addStyleName("fontAR");
			colourSpace.setWidth("3px");

			colourLabel.setStyleName("pantoneLabel");
			colourPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
			colourPanel.add(colourLabel);
			colourPanel.add(colourSpace);
			colourPanel.add(colour);

			ordersPanel.setWidth("150px");

			ordersPanel.add(orderNameLabel);
			ordersPanel.add(orderTagsLabel);
			ordersPanel.add(colourPanel);
			ordersPanel.add(orderDateLabel);			

			_enquiryPanel.add(_forgotToBuyLbl);
			_enquiryPanel.add(_sendEnquiryLblBtn);	
			_enquiryPanel.setSpacing(5);

			mainPanel.add(orderImage, 154, 0);
			mainPanel.add(previousOrderButton,370,150);
			mainPanel.add(nextOrderButton,420,150);
			mainPanel.add(statusDescription,170,250);
			mainPanel.add(statusTitle,170,220);
			mainPanel.add(infoButton,147,221);
			mainPanel.add(ordersPanel,360,43);
			mainPanel.add(_buyNowImage, 220, 370);
			mainPanel.add(downloadPanelContainer,165,330);
			mainPanel.add(_enquiryPanel, 165, 380);
			mainPanel.add(_enquiryFeedback, 170, 400);
			mainPanel.add(infos,190,20);

			RootPanel.get("ordersBrowser").add(getPanel());

			applyCufon();
		}
	}

	private void setupEnquiryControls() {
		setEnquiryControlsVisibility(false);

		_sendEnquiryLblBtn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				((OrderServiceAsync)getService(Service.ORDER_SERVICE)).sendEnquiry(_currentOrder, _sendEnquiryCallback);
			}
		});

		//style
		_forgotToBuyLbl.setStyleName("labelForgot");
		_sendEnquiryLblBtn.addStyleName("labelButton");
		_sendEnquiryLblBtn.addStyleName("labelLink");
		_sendEnquiryLblBtn.addStyleName("labelForgot");
		_enquiryFeedback.setStyleName("labelForgotFeedback");
	}

	private void setEnquiryControlsVisibility(boolean visible)
	{
		_enquiryFeedback.setText("");

		_forgotToBuyLbl.setVisible(visible);
		_sendEnquiryLblBtn.setVisible(visible);
		_enquiryFeedback.setVisible(visible);
	}

	private void hookUpCallbacks() 
	{
		_setOrderStatusCallback = new AsyncCallback<Long>() {

			public void onSuccess(Long result) {
				if(_currentOrder.getStatus().equals(Status.READY))
				{
					//This is la porcata definitiva.
					//Assuming -as it is at the moment- that status callback gets called only for the Ready->Viewed transition
					_currentOrder.setStatus(Status.VIEWED);
					updatePanel();
					notifyChanges();
					slideShow.showSingleImage("/wokhei/getfile?fileType="+FileType.PNG_LOGO_PRESENTATION.toString()+"&orderid="+_currentOrder.getId(), Messages.COPYRIGHT.getString());
				}
			}

			public void onFailure(Throwable caught) {
				//TODO give feedback to the user that something went wrong!
			}
		};

		// Set up the callback object
		_getOrdersCallback = new AsyncCallback<List<OrderDTO>>() {

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(List<OrderDTO> result) {
				_orders=result;
				_currentOrder=OrderDTOUtils.getMostRecentOrder(result);
				updatePanel();
			}
		};

		_sendEnquiryCallback = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				if(result)
					_enquiryFeedback.setText(Messages.ENQUIRY_FEEDBACK_OK.getString());
				else
					_enquiryFeedback.setText(Messages.ENQUIRY_FEEDBACK_KO.getString());
			}

			public void onFailure(Throwable caught) {
				_enquiryFeedback.setText(Messages.ENQUIRY_FEEDBACK_KO.getString());
			}

		};
	}

	private void setupLightBox() {
		orderImage.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{	
				switch (_currentOrder.getStatus())
				{
				case READY:
					if(_setOrderStatusCallback!=null)
					{
						((OrderServiceAsync) getService(Service.ORDER_SERVICE)).setOrderStatus(_currentOrder.getId(),Status.VIEWED, _setOrderStatusCallback);						
					}
					break;
				case VIEWED:
				case BOUGHT:
				case ARCHIVED:
					slideShow.showSingleImage("/wokhei/getfile?fileType="+FileType.PNG_LOGO_PRESENTATION.toString()+"&orderid="+_currentOrder.getId(), Messages.COPYRIGHT.getString());
					break;
				}

			}

		});

	}

	/**
	 * @return
	 */
	private Widget getPanel() 
	{
		return mainPanel;
	}

	/**
	 * 
	 */
	protected void getOrdersForCurrentCustomer() 
	{
		((OrderServiceAsync) getService(Service.ORDER_SERVICE)).getOrdersForCurrentUser(_getOrdersCallback);
	}

	/**
	 * 
	 */
	protected void getNextOrder() 
	{
		_currentOrder=OrderDTOUtils.getNextOrder(_orders,_currentOrder);
	}

	/**
	 * 
	 */
	protected void getPreviousOrder() 
	{
		_currentOrder=OrderDTOUtils.getPreviousOrder(_orders,_currentOrder);
	}

	/**
	 * 
	 */
	public void getLastOrder() 
	{
		_currentOrder=OrderDTOUtils.getMostRecentOrder(_orders);
	}


	/**
	 * 
	 */
	private void updatePanel() {
		//buy now false by default
		_buyNowImage.setVisible(false);
		//send enquiry visibility false by default
		setEnquiryControlsVisibility(false);

		if(downloadPanel!=null)
		{
			downloadPanelContainer.remove(downloadPanel);
		}

		if(_currentOrder!=null)
		{

			orderImage.setVisible(true);

			nextOrderButton.setVisible(OrderDTOUtils.getNextOrder(_orders, _currentOrder)!=_currentOrder);
			previousOrderButton.setVisible(OrderDTOUtils.getPreviousOrder(_orders, _currentOrder)!=_currentOrder);

			alwaysInfos(false);
			orderNameLabel.setText(_currentOrder.getText());
			String list=Arrays.asList(_currentOrder.getTags()).toString().replace(",","");
			DateTimeFormat fmt = DateTimeFormat.getFormat("EEE MMM yy k:m");
			colour.setStyleName("colour"+_currentOrder.getColour().toString());
			colourLabel.setText(_currentOrder.getColour().getName()+" ");
			orderTagsLabel.setText(list.substring(1, list.length()-1));
			orderDateLabel.setText(fmt.format(_currentOrder.getDate()));
			statusTitle.setText(Messages.valueOf(_currentOrder.getStatus().toString()+"_TITLE").getString());
			statusDescription.setText(Messages.valueOf(_currentOrder.getStatus().toString()+"_TEXT").getString());
			switch(_currentOrder.getStatus())
			{
			case INCOMING:
			case ACCEPTED:
			case IN_PROGRESS:
			case QUALITY_GATE:
			case REJECTED:
				orderImage.removeStyleName("labelButton");
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL());
				break;
			case READY:
				orderImage.addStyleName("labelButton");
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL());
				break;
			case VIEWED:
				orderImage.addStyleName("labelButton");
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL()); 
				setupBuyNowStuff();
				break;
			case BOUGHT:
				orderImage.addStyleName("labelButton");
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL()); 
				setupDownloadStuff(_currentOrder.getStatus());
				break;
			case ARCHIVED:
				orderImage.addStyleName("labelButton");
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL()); 
				setupDownloadStuff(_currentOrder.getStatus());
				setEnquiryControlsVisibility(true);
				break;
			}
		}
		else
		{
			//there's no current order means we have no order - need to set invisible for IE
			orderImage.setVisible(false);
			alwaysInfos(true);
		}
		applyCufon();
	}

	private void setupDownloadStuff(final Status status) {
		downloadPanel=new VerticalPanel();
		downloadPanel.setSpacing(5);
		Label downloadPng=new Label(Messages.DOWNLOAD_RASTERIZED.getString());
		downloadPng.setStyleName("labelButton");
		downloadPng.addStyleName("downloadLabelLink");
		downloadPng.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(status.equals(Status.BOUGHT))
				{
					Window.open(GWT.getHostPageBaseURL()+"wokhei/getfile?orderid="+_currentOrder.getId()+"&fileType="+FileType.PNG_LOGO, "_new", "");
				}
				else
				{
					_acceptAgreementPopupPanel.center();
					_acceptAgreementPopupPanel.show();
				}
			}});

		downloadPanel.add(downloadPng);

		if(status.equals(Status.BOUGHT))
		{
			Label downloadPdf=new Label(Messages.DOWNLOAD_VECTORIAL.getString());
			downloadPdf.setStyleName("labelButton");
			downloadPdf.addStyleName("downloadLabelLink");
			downloadPdf.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) {
					Window.open(GWT.getHostPageBaseURL()+"wokhei/getfile?orderid="+_currentOrder.getId()+"&fileType="+FileType.PDF_VECTORIAL_LOGO, "_new", "");
				}});

			downloadPanel.add(downloadPdf);
		}
		downloadPanelContainer.add(downloadPanel);
	}


	private void setupBuyNowStuff() {
		if(!_buyNowLoaded)
		{
			_buyNowLoaded=true;
			// fill-up buy now pop-up panel
			setupBuyNowPopup();

			// setup BuyNow image click handler

			ClickHandler buyNowClickHandler=new ClickHandler(){

				public void onClick(ClickEvent event) 
				{	
					// setup style and show
					_buyNowPopUpPanel.setStyleName("genericPopup");
					_buyNowPopUpPanel.center();
					_buyNowPopUpPanel.show();
				}

			};

			_buyNowImage.addClickHandler(buyNowClickHandler);

			// setup BuyNow Icon if needed then make it visible.
			_buyNowImage.addStyleName("labelButton");
			_buyNowImage.addStyleName("buyNowButton");
			_buyNowImage.setVisible(true);
		}
		else
		{
			_buyNowImage.setVisible(true);
		}
	}

	private void setupBuyNowPopup() {
		setupPayPalForm(); 

		_buyNowPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		_buyNowPanel.setSpacing(10);

		ScrollPanel sp=new ScrollPanel();
		_licenseText.setWidth("340px");
		_licenseText.setHeight("300px");
		_licenseText.setStyleName("license");
		sp.setStyleName("licensePanel");

		_feedBackLabel.setStyleName("errorLabel");
		sp.add(_licenseText);
		_buyNowPanel.add(sp);
		_buyNowPanel.add(_acceptLicenseCheckBox);
		_buyNowPanel.add(_paypalForm);
		_buyNowPanel.add(_feedBackLabel);

		_buyNowPopUpPanel.setWidth("390px");
		_buyNowPopUpPanel.add(_buyNowPanel);

	}

	private void setupAcceptAgreement() {

		HTMLPanel license= new HTMLPanel(HtmlLicenses.LIMITED_LICENSE.getString());	
		ScrollPanel sp=new ScrollPanel();
		license.setWidth("340px");
		license.setHeight("300px");
		license.setStyleName("license");
		sp.setStyleName("licensePanel");

		sp.add(license);
		VerticalPanel acceptAgreementPanel=new VerticalPanel();
		acceptAgreementPanel.setSpacing(10);
		acceptAgreementPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		acceptAgreementPanel.add(sp);
		final CheckBox acceptLicenseCheckBox=new CheckBox(Messages.ACCEPT_CONDITIONS.getString());
		acceptAgreementPanel.add(acceptLicenseCheckBox);
		final Label feedBackLabel=new Label(Messages.MUST_ACCEPT_LICENSE.getString());

		Label downloadButton = new Label(Messages.DOWNLOAD_RASTERIZED.getString());
		downloadButton.setStyleName("labelButton");
		//		downloadButton.addStyleName("fontAR");
		downloadButton.addStyleName("downloadLabelLink");
		downloadButton.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(acceptLicenseCheckBox.getValue())
				{
					Window.open(GWT.getHostPageBaseURL()+"wokhei/getfile?orderid="+_currentOrder.getId()+"&fileType="+FileType.PNG_LOGO, "_new", "");
					_acceptAgreementPopupPanel.hide();
				}
				else
				{
					feedBackLabel.setVisible(true);
				}

			}});

		acceptAgreementPanel.add(downloadButton);
		feedBackLabel.setVisible(false);
		feedBackLabel.setStyleName("errorLabel");
		acceptAgreementPanel.add(feedBackLabel);

		_acceptAgreementPopupPanel.setStyleName("genericPopup");
		_acceptAgreementPopupPanel.setWidth("390px");
		_acceptAgreementPopupPanel.add(acceptAgreementPanel);
	}

	private void setupPayPalForm()
	{
		//fill-up paypal form
		_paypalForm.setAction(PayPalStrings.PAYPAL_ACTION.getString());
		_paypalForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		_paypalForm.setMethod(FormPanel.METHOD_POST);

		VerticalPanel formPlaceHolder = new VerticalPanel();

		//setup input element for seller
		Hidden sellerInfo = new Hidden();

		sellerInfo.setName(PayPalStrings.PAYPAL_BUSINESS_NAME.getString());
		sellerInfo.setValue(PayPalStrings.PAYPAL_BUSINESS_VALUE.getString());

		formPlaceHolder.add(sellerInfo);
		//specify buy now button
		Hidden cmdInfo = new Hidden();

		cmdInfo.setName(PayPalStrings.PAYPAL_CMD_NAME.getString());
		cmdInfo.setValue(PayPalStrings.PAYPAL_CMD_VALUE.getString());

		formPlaceHolder.add(cmdInfo);
		//specify purchase details
		Hidden itemNameInfo = new Hidden();
		Hidden amountInfo = new Hidden();
		Hidden taxInfo = new Hidden();
		Hidden currencyInfo = new Hidden();
		Hidden notifyInfo = new Hidden();
		Hidden returnInfo = new Hidden();
		Hidden custom = new Hidden();

		itemNameInfo.setName(PayPalStrings.PAYPAL_ITEMNAME_NAME.getString());
		itemNameInfo.setValue(PayPalStrings.PAYPAL_ITEMNAME_VALUE.getString());
		formPlaceHolder.add(itemNameInfo);

		amountInfo.setName(PayPalStrings.PAYPAL_AMOUNT_NAME.getString());
		amountInfo.setValue(PayPalStrings.PAYPAL_AMOUNT_VALUE.getString());
		formPlaceHolder.add(amountInfo);

		taxInfo.setName(PayPalStrings.PAYPAL_TAX_NAME.getString());
		taxInfo.setValue(PayPalStrings.PAYPAL_TAX_VALUE.getString());
		formPlaceHolder.add(taxInfo);

		currencyInfo.setName(PayPalStrings.PAYPAL_CURRENCY_NAME.getString());
		currencyInfo.setValue(PayPalStrings.PAYPAL_CURRENCY_VALUE.getString());
		formPlaceHolder.add(currencyInfo);

		notifyInfo.setName(PayPalStrings.PAYPAL_NOTIFY_URL_NAME.getString());
		notifyInfo.setValue(PayPalStrings.PAYPAL_NOTIFY_URL_VALUE.getString());
		formPlaceHolder.add(notifyInfo);

		returnInfo.setName(PayPalStrings.PAYPAL_RETURN_NAME.getString());
		returnInfo.setValue(PayPalStrings.PAYPAL_RETURN_VALUE.getString());

		custom.setName(PayPalStrings.PAYPAL_CUSTOM_NAME.getString());
		custom.setValue(_currentOrder.getId().toString());
		formPlaceHolder.add(custom);

		//setup submit button
		Image buyNowButton = new Image();
		buyNowButton.setStyleName("labelButton");
		buyNowButton.setUrl(Images.PAYPAL_BUTTON.getImageURL());

		buyNowButton.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{
				_paypalForm.submit();
			}
		});
		formPlaceHolder.add(buyNowButton);

		_paypalForm.add(formPlaceHolder);

		//setup submit handlers
		_paypalForm.addSubmitHandler(new SubmitHandler(){
			public void onSubmit(SubmitEvent event) {
				//cancel if license has not been accepted
				if (! _acceptLicenseCheckBox.getValue())
				{
					_feedBackLabel.setText(Messages.MUST_ACCEPT_LICENSE.getString());
					event.cancel();
				}
			}
		});

		_paypalForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			public void onSubmitComplete(SubmitCompleteEvent event)
			{
				//nothing to handle? whoo-yeah! AVP sucks dick
			}
		});

	}

	/**
	 * @param show
	 */
	private void alwaysInfos(boolean show) 
	{
		if(show==infoButton.isVisible())
		{
			infoButton.setVisible(!show);
			infos.setVisible(show);
		}
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() 
	{
		getOrdersForCurrentCustomer();
	}


}
