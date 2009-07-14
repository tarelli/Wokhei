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

	private final SlideShow slideShow = new SlideShow();

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
				if(_currentOrder.getStatus().equals(Status.READY) || _currentOrder.getStatus().equals(Status.VIEWED))
					slideShow.showSingleImage("/wokhei/getlogo?orderid="+_currentOrder.getId(), Messages.COPYRIGHT.getString());
			}

		});

		slideShow.onFinish(new Callback(){
			public void execute() {
				if(_currentOrder.getStatus().equals(Status.READY))
				{
					if(_setOrderStatusCallback!=null)
					{
						((OrderServiceAsync) getService(Service.ORDER_SERVICE)).setOrderStatus(_currentOrder.getId(),Status.VIEWED, _setOrderStatusCallback);
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
				//TODO set the logo image itself
				orderImage.addStyleName("labelButton");
				orderImage.setUrl("./images/logo.png");

				//TODO setup Buy Now Panel
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
