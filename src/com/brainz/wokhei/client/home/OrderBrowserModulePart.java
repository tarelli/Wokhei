/**
 * 
 */
package com.brainz.wokhei.client.home;

import java.util.Arrays;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.resources.PayPalStrings;
import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.OrderDTOUtils;
import com.brainz.wokhei.shared.Status;
import com.codelathe.gwt.client.SlideShow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
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

	private final Label colourSpace = new Label();

	private final SlideShow slideShow = new SlideShow();

	// will load-up the panel with license + paypal 
	private final Label _buyNowImage = new Label();
	private final PopupPanel _buyNowPopUpPanel = new PopupPanel(true);
	private final VerticalPanel _buyNowPanel = new VerticalPanel();
	private final TextArea _licenceText = new TextArea();
	private final CheckBox _acceptLicenseCheckBox = new CheckBox("I accept the terms and conditions");
	private final Label _feedBackLabel = new Label("");
	private final FormPanel _paypalForm = new FormPanel("");

	private AsyncCallback<Long> _setOrderStatusCallback = null;

	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;

	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("ordersBrowser")!=null)
		{
			hookUpCallbacks();

			getOrdersForCurrentCustomer();

			setupLightBox();

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
			mainPanel.setWidth("900px");
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

			mainPanel.add(orderImage, 462, 18);
			mainPanel.add(previousOrderButton,670,150);
			mainPanel.add(nextOrderButton,720,150);
			mainPanel.add(statusDescription,470,250);
			mainPanel.add(statusTitle,470,220);
			mainPanel.add(infoButton,447,221);
			mainPanel.add(ordersPanel,660,43);
			mainPanel.add(infos,490,20);

			//add Buy Now Icon
			mainPanel.add(_buyNowImage, 650, 320);

			RootPanel.get("ordersBrowser").add(getPanel());

			applyCufon();
		}
	}

	private void hookUpCallbacks() 
	{
		_setOrderStatusCallback = new AsyncCallback<Long>() {

			public void onSuccess(Long result) {
				if(result!=null)
					updatePanel();
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
	}

	private void setupLightBox() {
		orderImage.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{	
				if(_currentOrder.getStatus().equals(Status.READY) 
						|| _currentOrder.getStatus().equals(Status.VIEWED) 
						|| _currentOrder.getStatus().equals(Status.BOUGHT) 
						|| _currentOrder.getStatus().equals(Status.ARCHIVED))
					slideShow.showSingleImage("/wokhei/getfile?fileType="+FileType.PNG_LOGO.toString()+"&orderid="+_currentOrder.getId(), Messages.COPYRIGHT.getString());
				if(_currentOrder.getStatus().equals(Status.READY))
				{
					if(_setOrderStatusCallback!=null)
					{
						((OrderServiceAsync) getService(Service.ORDER_SERVICE)).setOrderStatus(_currentOrder.getId(),Status.VIEWED, _setOrderStatusCallback);
					}
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
	private void updatePanel() {
		//buy now false by default
		_buyNowImage.setVisible(false);

		//TODO: remove - this is here just for testing
		//setupBuyNowStuff();

		if(_currentOrder!=null)
		{

			orderImage.setVisible(true);

			//set next arrow visibility
			if(_orders.indexOf(_currentOrder) == _orders.size() -1)
			{
				nextOrderButton.setVisible(false);
			}
			else
			{
				nextOrderButton.setVisible(true);
			}

			//set prev arrow visibility
			if(_orders.indexOf(_currentOrder) == 0)
			{
				previousOrderButton.setVisible(false);
			}
			else
			{
				previousOrderButton.setVisible(true);
			}

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
			case ARCHIVED:
			case BOUGHT:
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

	private void setupBuyNowStuff() {
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

	private void setupBuyNowPopup() {
		setupPayPalForm(); 

		_buyNowPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);

		//TODO: set and format license in a disabled multi-line text box --> _license Label
		_licenceText.setWidth("340px");
		_licenceText.setHeight("300px");
		_licenceText.setReadOnly(true);
		_licenceText.setText("--> LICENSE HERE <--");

		_buyNowPanel.add(_licenceText);
		_buyNowPanel.add(_acceptLicenseCheckBox);
		_buyNowPanel.add(_paypalForm);
		_buyNowPanel.add(_feedBackLabel);

		_buyNowPopUpPanel.setWidth("350px");
		_buyNowPopUpPanel.add(_buyNowPanel);
	}

	private void setupPayPalForm()
	{
		//fill-up paypal form
		_paypalForm.setAction(PayPalStrings.PAYPAL_SANDBOX_ACTION.getString());
		_paypalForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		_paypalForm.setMethod(FormPanel.METHOD_POST);

		VerticalPanel formPlaceHolder = new VerticalPanel();

		//setup input element for seller
		Hidden sellerInfo = new Hidden();

		sellerInfo.setName(PayPalStrings.PAYPAL_BUSINESS_NAME.getString());
		sellerInfo.setValue(PayPalStrings.PAYPAL_SANDBOX_BUSINESS_VALUE.getString());

		formPlaceHolder.add(sellerInfo);
		//specify buy now button
		Hidden cmdInfo = new Hidden();

		cmdInfo.setName(PayPalStrings.PAYPAL_CMD_NAME.getString());
		cmdInfo.setValue(PayPalStrings.PAYPAL_CMD_VALUE.getString());

		formPlaceHolder.add(cmdInfo);
		//specify purchase details
		Hidden itemNameInfo = new Hidden();
		Hidden amountInfo = new Hidden();
		Hidden currencyInfo = new Hidden();
		Hidden notifyInfo = new Hidden();

		itemNameInfo.setName(PayPalStrings.PAYPAL_ITEMNAME_NAME.getString());
		itemNameInfo.setValue(PayPalStrings.PAYPAL_ITEMNAME_VALUE.getString());
		formPlaceHolder.add(itemNameInfo);

		amountInfo.setName(PayPalStrings.PAYPAL_AMOUNT_NAME.getString());
		amountInfo.setValue(PayPalStrings.PAYPAL_AMOUNT_VALUE.getString());
		formPlaceHolder.add(amountInfo);

		currencyInfo.setName(PayPalStrings.PAYPAL_CURRENCY_NAME.getString());
		currencyInfo.setValue(PayPalStrings.PAYPAL_CURRENCY_VALUE.getString());
		formPlaceHolder.add(currencyInfo);

		notifyInfo.setName(PayPalStrings.PAYPAL_NOTIFY_URL_NAME.getString());
		notifyInfo.setValue(PayPalStrings.PAYPAL_NOTIFY_URL_VALUE.getString() + _currentOrder.getId());
		formPlaceHolder.add(notifyInfo);

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
					_feedBackLabel.setText("You need to accept terms and conditions first!");
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
