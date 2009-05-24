package com.brainz.wokhei.client;

import java.util.Arrays;
import java.util.List;

import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * @author John_Idol
 *
 */
public class AdminOrderBrowserModulePart extends AModulePart{

	public enum Columns {

		ID("ID"),
		USER("User"),
		LOGO_TEXT("Logo Text"),
		TAGS("Tagz"),
		STATUS("Status"),
		TIMER("Timer"),
		ACTIONS("Actions");

		String _columnText;

		private Columns()
		{

		}

		private Columns(String imageName)
		{
			_columnText=imageName;
		}

		public String getColumnText()
		{
			return _columnText;
		}
	}

	private final VerticalPanel mainPanel = new VerticalPanel();
	private final FlexTable ordersFlexTable = new FlexTable();

	private List<OrderDTO> _orders = null;

	private AsyncCallback<List<OrderDTO>> _getOrdersCallback = null;

	private AsyncCallback<Boolean> _setOrderStatusCallback = null;

	//related with status update chaching during async call
	private int _rowForClientStatusUpdate;
	private Status _statusForClientUpdate;

	/* 
	 * Init module part
	 */
	@Override
	public void initModulePart(OrderServiceAsync service) {
		if(RootPanel.get("adminConsole")!=null)
		{
			super.initModulePart(service);

			HookUpCallbacks();

			// Create table for order data.
			ordersFlexTable.setText(0, Columns.ID.ordinal(), Columns.ID.getColumnText());
			ordersFlexTable.setText(0, Columns.USER.ordinal(), Columns.USER.getColumnText());
			ordersFlexTable.setText(0, Columns.LOGO_TEXT.ordinal(), Columns.LOGO_TEXT.getColumnText());
			ordersFlexTable.setText(0, Columns.TAGS.ordinal(), Columns.TAGS.getColumnText());
			ordersFlexTable.setText(0, Columns.STATUS.ordinal(), Columns.STATUS.getColumnText());
			ordersFlexTable.setText(0, Columns.TIMER.ordinal(), Columns.TIMER.getColumnText());
			ordersFlexTable.setText(0, Columns.ACTIONS.ordinal(), Columns.ACTIONS.getColumnText());

			// set flexTable style
			ordersFlexTable.getRowFormatter().addStyleName(0, "orderListHeader");
			ordersFlexTable.addStyleName("orderList");

			populateOrdersTable();

			mainPanel.add(ordersFlexTable);

			// Associate the Main panel with the HTML host page.
			RootPanel.get("adminConsole").add(mainPanel);
		}
	}

	private void HookUpCallbacks() {

		// Set up the callback object
		_getOrdersCallback = new AsyncCallback<List<OrderDTO>>() {

			public void onSuccess(List<OrderDTO> result) {
				_orders = result; 
				UpdateTable();
			}

			public void onFailure(Throwable caught) {
				_orders = null;
			}
		};

		_setOrderStatusCallback = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				updateOrderStatusOnClient(result);
			}

			public void onFailure(Throwable caught) {
				//TODO - do something in case of failure
			}
		};

	}

	protected void updateOrderStatusOnClient(Boolean result) {

		// if successful remove row from orders table
		if(result)
		{
			// set new status
			ordersFlexTable.setText(_rowForClientStatusUpdate, Columns.STATUS.ordinal(), _statusForClientUpdate.toString());
			// disable buttons 
			//TODO --> decision to show or not row according to filters
			HorizontalPanel actionPanel = ((HorizontalPanel)ordersFlexTable.getWidget(_rowForClientStatusUpdate, Columns.ACTIONS.ordinal()));
			((Button)actionPanel.getWidget(0)).setEnabled(false);
			((Button)actionPanel.getWidget(1)).setEnabled(false);
		}
		else
		{
			Window.alert("status amend Operation Failed - try again, you may get lucky!");
		}

	}

	private void populateOrdersTable() {
		// get orders and update table
		getOrdersAndUpdateTable();
	}

	protected void getOrdersAndUpdateTable() {
		if (_getOrdersCallback != null)
			_service.getOrdersByUserAndStatus(null, null, _getOrdersCallback);
	}

	private void UpdateTable()
	{
		if (_orders!=null)
		{
			for(OrderDTO order : _orders)
			{
				final int row = ordersFlexTable.getRowCount();
				ordersFlexTable.setText(row, Columns.ID.ordinal(), order.getId().toString());
				ordersFlexTable.setText(row, Columns.USER.ordinal(), order.getCustomerEmail());
				ordersFlexTable.setText(row, Columns.LOGO_TEXT.ordinal(), order.getText());
				ordersFlexTable.setText(row, Columns.TAGS.ordinal(), Arrays.asList(order.getTags()).toString());
				ordersFlexTable.setText(row, Columns.STATUS.ordinal(), order.getStatus().toString());
				ordersFlexTable.setText(row, Columns.TIMER.ordinal(), "N/A");

				if(order.getStatus()==Status.INCOMING)
				{
					// Add a button to remove this stock from the table.
					Button rejectOrderButton = new Button("Reject");

					rejectOrderButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {

							//get ID from clicked row
							long rejectedId = Long.parseLong(ordersFlexTable.getText(row, Columns.ID.ordinal()));

							statusChangedSubHandler(row, rejectedId, Status.REJECTED);
						}
					});

					// Add a button to remove this stock from the table.
					Button acceptOrderButton = new Button("Accept");

					acceptOrderButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {

							//get ID from clicked row
							long acceptedId = Long.parseLong(ordersFlexTable.getText(row, Columns.ID.ordinal()));

							statusChangedSubHandler(row, acceptedId, Status.ACCEPTED);
						}
					});

					HorizontalPanel actionPanel = new HorizontalPanel();
					actionPanel.add(acceptOrderButton);
					actionPanel.add(rejectOrderButton);

					ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), actionPanel);
				}
				else if (order.getStatus()!=Status.READY && order.getStatus()!=Status.REJECTED)
				{
					// if is not incoming and it's not ready or rejected - arguably it's always possible to upload
					Button uploadLogoButton = new Button("Upload");

					uploadLogoButton.addClickHandler(new ClickHandler() {
						public void onClick(ClickEvent event) {
							//get ID from clicked row
							long orderId = Long.parseLong(ordersFlexTable.getText(row, Columns.ID.ordinal()));

							Window.alert("This feature is not implemented yet - stay tuned for good!");
						}
					});

					ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), uploadLogoButton);
				}
				else
				{
					ordersFlexTable.setText(row, Columns.ACTIONS.ordinal(), "N/A");
				}
			}
		}
	}

	private void statusChangedSubHandler(int row, long orderId, Status status)
	{
		//set row index - it will be used on callback success (let's be aware this is shit!)
		_rowForClientStatusUpdate = row;
		_statusForClientUpdate = status;

		//call setOrderStatus callback
		_service.setOrderStatus(orderId, status, _setOrderStatusCallback);
	}

	/*
	 * not implemented - this module part doesn't interact with any other
	 */
	@Override
	public void updateModulePart() {
		try {
			throw new Exception("Metti che il VP ci implementa tutto e usa questo metodo ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
