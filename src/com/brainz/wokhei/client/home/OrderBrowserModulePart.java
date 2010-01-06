/**
 * 
 */
package com.brainz.wokhei.client.home;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.resources.PayPalStrings;
import com.brainz.wokhei.shared.Colour;
import com.brainz.wokhei.shared.FileType;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.OrderDTOUtils;
import com.brainz.wokhei.shared.Status;
import com.brainz.wokhei.shared.TransactionType;
import com.codelathe.gwt.client.SlideShow;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;

/**
 * @author matteocantarelli
 *
 */
public class OrderBrowserModulePart extends AModulePart{

	private final AbsolutePanel mainPanel = new AbsolutePanel();
	private final VerticalPanel ordersPanel = new VerticalPanel();
	private final Label orderNameLabel = new Label();

	// Sub this label with a panel with up/down arrows and a label to display descriptions/revisions
	private final HorizontalPanel _descriptionsContainer = new HorizontalPanel();
	private final VerticalPanel _descriptionsArrowsPanel = new VerticalPanel();
	private final VerticalPanel _orderDescriptionsPanel = new VerticalPanel();
	private Label _descriptionLabel;
	private Label _upArrow;
	private Label _downArrow;
	private int _currentDescIndex;


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

	// Will load-up the panel with license + paypal 
	private final Button _buyNowImage = new Button();
	private final FormPanel _paypalForm = new FormPanel("");

	private AsyncCallback<Long> _setOrderStatusCallback = null;
	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;

	private boolean _buyNowLoaded=false;
	private final Label _askRevisionImage= new Label();

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
			orderDateLabel.setStyleName("logoTagsDateLabel");

			infos.setVisible(false);
			infos.setUrl(Images.INFOS.getImageURL());

			mainPanel.setHeight("450px");
			mainPanel.setWidth("600px");
			colour.setHeight("10px");
			colour.setWidth("10px");
			statusDescription.setHeight("150px");
			statusDescription.setWidth("340px");
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

			_descriptionsContainer.setHeight("100px");

			_descriptionsContainer.add(_orderDescriptionsPanel);
			_descriptionsContainer.add(_descriptionsArrowsPanel);

			ordersPanel.add(_descriptionsContainer);
			ordersPanel.add(colourPanel);
			ordersPanel.add(orderDateLabel);			

			mainPanel.add(orderImage, 154, 0);
			mainPanel.add(previousOrderButton,370,170);
			mainPanel.add(nextOrderButton,420,170);
			mainPanel.add(statusDescription,170,240);
			mainPanel.add(statusTitle,170,210);
			mainPanel.add(infoButton,120,211);
			mainPanel.add(ordersPanel,360,13);
			mainPanel.add(_paypalForm, 220, 325);
			mainPanel.add(_askRevisionImage, 220, 385);
			mainPanel.add(downloadPanelContainer,165,330);
			mainPanel.add(infos,130,10);

			RootPanel.get("ordersBrowser").add(getPanel());

			applyCufon();
		}
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
					notifyChanges(_currentOrder);
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
				List<OrderDTO> pendingOrders=new ArrayList<OrderDTO>();

				//remove all the pending orders from the received ones
				//if needed the pendingorders could become global				
				for(OrderDTO o:_orders)
				{
					if(o.getStatus().equals(Status.PENDING))
					{
						pendingOrders.add(o);
					}
				}
				_orders.removeAll(pendingOrders);

				_currentOrder=OrderDTOUtils.getMostRecentOrder(result);
				updatePanel();
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
		_askRevisionImage.setVisible(false);
		notifyChanges(_currentOrder);


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

			DateTimeFormat fmt = DateTimeFormat.getFormat("EEE MMM yy k:m");
			colour.setStyleName("colour"+_currentOrder.getColour().toString());
			if(_currentOrder.getColour().equals(Colour.SurpriseMe))
			{
				colour.setText("?");
				colour.addStyleName("fontAR");
				colour.addStyleName("colourSurpriseMeHomeBrowsing");
			}
			else
			{
				colour.setText("");
			}
			colourLabel.setText(_currentOrder.getColour().getName()+" ");

			// Set original description + revisions --> a panel with small top-down small arrows
			setupDescriptionsPanel();

			orderDateLabel.setText(fmt.format(_currentOrder.getDate()));
			if(_currentOrder.getStatus().equals(Status.VIEWED) && _currentOrder.hasCompletedReview())
			{
				statusTitle.setText(Messages.valueOf("RE"+_currentOrder.getStatus().toString()+"_TITLE").getString());
				statusDescription.setText(Messages.valueOf("RE"+_currentOrder.getStatus().toString()+"_TEXT").getString());
			}
			else
			{
				statusTitle.setText(Messages.valueOf(_currentOrder.getStatus().toString()+"_TITLE").getString());
				statusDescription.setText(Messages.valueOf(_currentOrder.getStatus().toString()+"_TEXT").getString());
			}
			switch(_currentOrder.getStatus())
			{
			case INCOMING:
			case ACCEPTED:
			case IN_PROGRESS:
			case QUALITY_GATE:
			case REJECTED:
			case REVIEWING:
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

	private void setupDescriptionsPanel() {
		//0. clear descPanel
		_orderDescriptionsPanel.clear();
		_descriptionsArrowsPanel.clear();

		// 1. create label to hold description
		_descriptionLabel = new Label();
		_descriptionLabel.setStyleName("logoTagsDateLabel");

		//set text to latest description
		int size = Arrays.asList(_currentOrder.getDescriptions()).size();
		_descriptionLabel.setText(Arrays.asList(_currentOrder.getDescriptions()).get(size-1));

		//set index
		_currentDescIndex = size - 1;

		// 2. create up arrow and down arrow to show prev/next description and set arrows visibility
		//instatiate buttons
		_upArrow = new Label();
		_upArrow.addStyleName("descUp");
		_upArrow.addStyleName("labelButton");

		_downArrow = new Label();
		_downArrow.setStyleName("descDown");
		_downArrow.addStyleName("labelButton");

		_upArrow.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_descriptionLabel.setText(Arrays.asList(_currentOrder.getDescriptions()).get(++_currentDescIndex));
				setUpDownArrowsVisibility(Arrays.asList(_currentOrder.getDescriptions()));
			}});

		_downArrow.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_descriptionLabel.setText(Arrays.asList(_currentOrder.getDescriptions()).get(--_currentDescIndex));
				setUpDownArrowsVisibility(Arrays.asList(_currentOrder.getDescriptions()));
			}});

		// 3. fill-up the panel with the stuff
		_orderDescriptionsPanel.setWidth("150px");
		_orderDescriptionsPanel.add(_descriptionLabel);
		_descriptionsArrowsPanel.add(_upArrow);
		_descriptionsArrowsPanel.add(_downArrow);

		// 4. set arrows visibility
		setUpDownArrowsVisibility(Arrays.asList(_currentOrder.getDescriptions()));
	}

	private void setUpDownArrowsVisibility(List<String> descriptionsList) {

		if(_currentDescIndex > 0 && _currentDescIndex < descriptionsList.size()-1)
		{
			_upArrow.setVisible(true);
			_downArrow.setVisible(true);
		}
		else if(_currentDescIndex == 0 && _currentDescIndex < descriptionsList.size()-1)
		{
			_upArrow.setVisible(true);
			_downArrow.setVisible(false);
		}
		else if(_currentDescIndex > 0 && _currentDescIndex == descriptionsList.size()-1)
		{
			_upArrow.setVisible(false);
			_downArrow.setVisible(true);
		}
		else if(_currentDescIndex == 0 && _currentDescIndex == descriptionsList.size()-1)
		{
			_upArrow.setVisible(false);
			_downArrow.setVisible(false);
		}

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
				}}});

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
			setupPayPalForm(); 

			// setup BuyNow image click handler


			_buyNowImage.addClickHandler(new ClickHandler(){

				public void onClick(ClickEvent event) 
				{	
					_paypalForm.submit();
				}

			});

			// setup BuyNow Icon if needed then make it visible.
			_buyNowImage.removeStyleName("gwt-Button");
			_buyNowImage.addStyleName("buyNowButton");
			_buyNowImage.setVisible(true);

			//if it has completed a review don't show this button (only one allowed for Step2!)
			if(!_currentOrder.hasCompletedReview())
			{

				_askRevisionImage.addClickHandler(new ClickHandler(){

					public void onClick(ClickEvent event) 
					{	
						OrderDTO orderCopy=new OrderDTO(_currentOrder);
						orderCopy.setRevisionCounter(orderCopy.getRevisionCounter()+1);
						notifyChanges(orderCopy);
						_buyNowImage.setVisible(false);
						_askRevisionImage.setVisible(false);
					}

				});

				// setup BuyNow Icon if needed then make it visible.
				_askRevisionImage.addStyleName("labelButton");
				_askRevisionImage.addStyleName("revisionButton");
				_askRevisionImage.setVisible(true);
			}


		}
		else
		{
			_buyNowImage.setVisible(true);
			if(!_currentOrder.hasCompletedReview())
			{
				_askRevisionImage.setVisible(true);
			}
		}
	}


	private void setupPayPalForm()
	{
		boolean isSandbox = getModule().isSandBox();
		//fill-up paypal form
		if(isSandbox)
		{
			_paypalForm.setAction(PayPalStrings.PAYPAL_SANDBOX_ACTION.getString());
		}
		else
		{
			_paypalForm.setAction(PayPalStrings.PAYPAL_ACTION.getString());	
		}	

		_paypalForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		_paypalForm.setMethod(FormPanel.METHOD_POST);

		VerticalPanel formPlaceHolder = new VerticalPanel();

		//setup input element for seller
		Hidden sellerInfo = new Hidden();

		sellerInfo.setName(PayPalStrings.PAYPAL_BUSINESS_NAME.getString());

		if(isSandbox)
		{
			sellerInfo.setValue(PayPalStrings.PAYPAL_SANDBOX_BUSINESS_VALUE.getString());
		}
		else
		{
			sellerInfo.setValue(PayPalStrings.PAYPAL_BUSINESS_VALUE.getString());	
		}		

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
		Hidden locale = new Hidden();

		//valuToPay sara il lordo(gross) da pagare
		Float valueToPay=TransactionType.BUYING_LOGO.getGrossToPay(_currentOrder.getTip());

		itemNameInfo.setName(PayPalStrings.PAYPAL_ITEMNAME_NAME.getString());
		itemNameInfo.setValue(TransactionType.BUYING_LOGO.getDescription());
		formPlaceHolder.add(itemNameInfo);

		amountInfo.setName(PayPalStrings.PAYPAL_AMOUNT_NAME.getString());
		amountInfo.setValue(TransactionType.BUYING_LOGO.getNet(valueToPay).toString());
		formPlaceHolder.add(amountInfo);

		taxInfo.setName(PayPalStrings.PAYPAL_TAX_NAME.getString());
		taxInfo.setValue(TransactionType.BUYING_LOGO.getTax(valueToPay).toString());
		formPlaceHolder.add(taxInfo);

		currencyInfo.setName(PayPalStrings.PAYPAL_CURRENCY_NAME.getString());
		currencyInfo.setValue(PayPalStrings.PAYPAL_CURRENCY_VALUE.getString());
		formPlaceHolder.add(currencyInfo);

		notifyInfo.setName(PayPalStrings.PAYPAL_NOTIFY_URL_NAME.getString());
		if(isSandbox)
		{
			notifyInfo.setValue(PayPalStrings.PAYPAL_NOTIFY_URL_SANDBOX_VALUE.getString());
		}
		else
		{
			notifyInfo.setValue(PayPalStrings.PAYPAL_NOTIFY_URL_VALUE.getString());

		}
		formPlaceHolder.add(notifyInfo);

		returnInfo.setName(PayPalStrings.PAYPAL_RETURN_NAME.getString());
		returnInfo.setValue(PayPalStrings.PAYPAL_RETURN_VALUE.getString());

		custom.setName(PayPalStrings.PAYPAL_CUSTOM_NAME.getString());
		custom.setValue(_currentOrder.getId().toString()+";"+TransactionType.BUYING_LOGO.toString());
		formPlaceHolder.add(custom);

		locale.setName(PayPalStrings.PAYPAL_LOCALE_NAME.getString());
		locale.setValue(PayPalStrings.PAYPAL_LOCALE_NAME.getString());
		formPlaceHolder.add(locale);

		formPlaceHolder.add(_buyNowImage);

		_paypalForm.add(formPlaceHolder);

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
	public void updateModulePart(OrderDTO selection) 
	{
		getOrdersForCurrentCustomer();
	}


}
