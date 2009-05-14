/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.Arrays;

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
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

	private final Button previousOrderButton = new Button();

	private final Button nextOrderButton = new Button();

	private OrderDTO _currentOrder=null;

	private final Label orderDateLabel = new Label();



	@Override
	public void initModulePart(OrderServiceAsync service) {
		super.initModulePart(service);

		getLatestOrder();

		previousOrderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getPreviousOrder();
			}
		});

		nextOrderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				getNextOrder();
			}
		});


		previousOrderButton.setStyleName("leftArrow");
		nextOrderButton.setStyleName("rightArrow");
		orderNameLabel.setStyleName("logoNameLabel");
		orderTagsLabel.setStyleName("logoTagsDateLabel");
		orderDateLabel.setStyleName("logoTagsDateLabel");
		mainPanel.setHeight("600px");
		mainPanel.setWidth("400px");
		mainPanel.add(orderImage, 12, 0);

		ordersPanel.add(orderNameLabel);
		ordersPanel.add(orderTagsLabel);
		ordersPanel.add(orderDateLabel);
		mainPanel.add(ordersPanel,206,45);
		mainPanel.add(previousOrderButton,220,120);
		mainPanel.add(nextOrderButton,270,120);

		RootPanel.get("ordersBrowser").add(getPanel());
	}



	/**
	 * @return
	 */
	private Widget getPanel() 
	{
		return mainPanel;
	}




	protected void getLatestOrder() {
		// Set up the callback object
		AsyncCallback<OrderDTO> callback = new AsyncCallback<OrderDTO>() {

			public void onSuccess(OrderDTO result) {
				_currentOrder=result;
				updateLabels();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		};

		_service.getLatestOrder(callback);
	}


	protected void getNextOrder() {
		// Set up the callback object.
		AsyncCallback<OrderDTO> callback = new AsyncCallback<OrderDTO>() {

			public void onSuccess(OrderDTO result) {
				_currentOrder=result;
				updateLabels();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		};

		_service.getNextOrder(_currentOrder, callback);
	}

	/**
	 * 
	 */
	protected void getPreviousOrder() {
		// Set up the callback object.
		AsyncCallback<OrderDTO> callback = new AsyncCallback<OrderDTO>() {

			public void onSuccess(OrderDTO result) {
				_currentOrder=result;
				updateLabels();
			}

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		};

		_service.getPreviousOrder(_currentOrder, callback);

	}

	/**
	 * 
	 */
	private void updateLabels() {
		if(_currentOrder!=null)
		{
			orderNameLabel.setText(_currentOrder.getText());
			String list=Arrays.asList(_currentOrder.getTags()).toString();
			//DateFormat class is not supported on the client side. 
			//Using deprecated methods temporarily (this will never get changed).
			String date=
				((Integer)_currentOrder.getDate().getMonth()).toString()+"/"+
				((Integer)_currentOrder.getDate().getDay()).toString()+"/"+
				((Integer)_currentOrder.getDate().getYear()).toString()+" "+
				((Integer)_currentOrder.getDate().getHours()).toString()+":"+
				((Integer)_currentOrder.getDate().getMinutes()).toString();
			orderTagsLabel.setText(list.substring(1, list.length()-1));
			orderDateLabel.setText(date);
			switch(_currentOrder.getStatus())
			{
			case INCOMING:
			case COOKING:
			case TASTING:
			case READY:
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL());
				break;
			default:
				//			todo set the logo image itself
				orderImage.setUrl(Images.valueOf(_currentOrder.getStatus().toString()).getImageURL());	
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() 
	{
		getLatestOrder();
	}


}
