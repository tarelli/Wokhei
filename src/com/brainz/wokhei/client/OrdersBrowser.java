/**
 * 
 */
package com.brainz.wokhei.client;

import java.util.Arrays;
import java.util.List;

import com.brainz.wokhei.shared.OrderDTO;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author matteocantarelli
 *
 */
public class OrdersBrowser implements EntryPoint {

	private OrdersBrowserServiceAsync ordersBrowserService =GWT.create(OrdersBrowserService.class);

	private final VerticalPanel mainPanel = new VerticalPanel();

	private final HorizontalPanel orderPanel = new HorizontalPanel();

	private final Label orderNameLabel = new Label();

	private final Label orderTagsLabel = new Label();

	private final Button previousOrderButton = new Button("Show previous order");

	private final Button nextOrderButton = new Button("Show next order");

	private OrderDTO _currentOrder=null;

	private final Label orderDateLabel = new Label();

	private final List<OrderDTO> _orders=null;

	/* (non-Javadoc)
	 * @see com.google.gwt.core.client.EntryPoint#onModuleLoad()
	 */
	public void onModuleLoad() {

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

		orderPanel.add(orderNameLabel);
		orderPanel.add(orderTagsLabel);
		orderPanel.add(orderDateLabel);
		orderPanel.add(previousOrderButton);
		orderPanel.add(nextOrderButton);
		mainPanel.add(orderPanel);

		RootPanel.get("ordersBrowser").add(mainPanel);

	}

	protected void getLatestOrder() {
		if (ordersBrowserService==null)
		{
			ordersBrowserService = GWT.create(OrdersBrowserService.class);
		}


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

		ordersBrowserService.getLatestOrder(callback);
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

		ordersBrowserService.getNextOrder(_currentOrder, callback);
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

		ordersBrowserService.getPreviousOrder(_currentOrder, callback);

	}

	/**
	 * 
	 */
	private void updateLabels() {
		orderNameLabel.setText(_currentOrder.getText());
		orderTagsLabel.setText(Arrays.asList(_currentOrder.getTags()).toString());
		orderDateLabel.setText(_currentOrder.getDate().toString());
	}

}
