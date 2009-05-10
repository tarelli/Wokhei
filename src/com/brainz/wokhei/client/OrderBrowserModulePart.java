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
import com.google.gwt.user.client.ui.Button;
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


	private final VerticalPanel mainPanel = new VerticalPanel();

	private final VerticalPanel orderPanel = new VerticalPanel();

	private final HorizontalPanel buttonsPanel = new HorizontalPanel();

	private final Label orderNameLabel = new Label();

	private final Label orderTagsLabel = new Label();

	private final Image orderImage = new Image();

	private final Button previousOrderButton = new Button("Show previous order");

	private final Button nextOrderButton = new Button("Show next order");

	private OrderDTO _currentOrder=null;

	private final Label orderDateLabel = new Label();

	private final HorizontalPanel imagePanel = new HorizontalPanel();



	@Override
	public void initModulePart(HomeModuleServiceAsync service) {
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

		imagePanel.add(orderImage);
		orderNameLabel.setStyleName("logoNameLabel");
		orderTagsLabel.setStyleName("logoTagsDateLabel");
		orderDateLabel.setStyleName("logoTagsDateLabel");
		orderPanel.add(orderNameLabel);
		orderPanel.add(orderTagsLabel);
		orderPanel.add(orderDateLabel);
		buttonsPanel.add(previousOrderButton);
		buttonsPanel.add(nextOrderButton);
		mainPanel.add(orderPanel);

		RootPanel.get("ordersBrowser").add(getLablesPanel());
		RootPanel.get("ordersBrowserButtons").add(getButtonsPanel());
		RootPanel.get("ordersBrowserImage").add(getImageStatusPanel());
	}



	/**
	 * @return
	 */
	private Widget getLablesPanel() 
	{
		return mainPanel;
	}

	/**
	 * @return
	 */
	private Widget getButtonsPanel() 
	{
		return buttonsPanel;
	}

	/**
	 * @return
	 */
	private Widget getImageStatusPanel() 
	{
		return imagePanel;
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
			orderTagsLabel.setText(Arrays.asList(_currentOrder.getTags()).toString());
			orderDateLabel.setText(_currentOrder.getDate().toString());
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
