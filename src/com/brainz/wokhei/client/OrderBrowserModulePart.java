/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.Arrays;
import java.util.List;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.OrderDTOUtils;
import com.brainz.wokhei.shared.Status;
import com.codelathe.gwt.client.Callback;
import com.codelathe.gwt.client.SlideShow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

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

	private final SlideShow slideShow=new SlideShow();

	private AsyncCallback<Boolean> _setOrderStatusCallback = null;

	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;


	@Override
	public void initModulePart(OrderServiceAsync service) 
	{
		if(RootPanel.get("ordersBrowser")!=null)
		{
			super.initModulePart(service);

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


			infoButton.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					infos.setVisible(!infos.isVisible());
					if(infos.isVisible())
					{
						infoButton.setStyleName("infoButtonClicked");
					}
					else
					{
						infoButton.setStyleName("infoButton");
					}
				}
			});

			previousOrderButton.setStyleName("leftArrow");
			nextOrderButton.setStyleName("rightArrow");
			orderNameLabel.setStyleName("logoNameLabel");
			orderTagsLabel.setStyleName("logoTagsDateLabel");
			orderDateLabel.setStyleName("logoTagsDateLabel");
			infoButton.setStyleName("infoButton");
			infos.setVisible(false);
			infos.setUrl(Images.INFOS.getImageURL());

			mainPanel.setHeight("600px");
			mainPanel.setWidth("900px");
			colour.setHeight("10px");
			colour.setWidth("10px");
			statusDescription.setHeight("150px");
			statusDescription.setWidth("250px");
			statusDescription.setStylePrimaryName("statusDescription");
			statusTitle.setStylePrimaryName("statusTitle");
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
			mainPanel.add(infoButton,425,220);
			mainPanel.add(ordersPanel,660,43);
			mainPanel.add(infos,490,20);

			RootPanel.get("ordersBrowser").add(getPanel());
		}
	}

	private void hookUpCallbacks() 
	{
		_setOrderStatusCallback = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				if(result)
					updatePanel();
			}

			public void onFailure(Throwable caught) {
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
				//TODO change the fake logo.png with the image that has been uploaded when the logo was made
				if(_currentOrder.getStatus().equals(Status.READY) || _currentOrder.getStatus().equals(Status.VIEWED))
					slideShow.showSingleImage("./images/logo.png", "Copyright\u00a9 2009 WOKHEI");
			}

		});

		slideShow.onFinish(new Callback(){
			public void execute() {
				if(_currentOrder.getStatus().equals(Status.READY))
				{
					if(_setOrderStatusCallback!=null)
					{
						_service.setOrderStatus(_currentOrder.getId(),Status.VIEWED, _setOrderStatusCallback);
					}
				}

			}});
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
		_service.getOrdersForCurrentUser(_getOrdersCallback);
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
		if(_currentOrder!=null)
		{
			orderNameLabel.setText(_currentOrder.getText());
			String list=Arrays.asList(_currentOrder.getTags()).toString();
			//DateFormat class is not supported on the client side. 
			//Using deprecated methods temporarily (this will never get changed).	
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
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL());
				break;
			case READY:
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL());
				break;
			case VIEWED:
				//TODO set the logo image itself
				orderImage.setUrl("./images/logo.png");
				break;
			}
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
