package com.brainz.wokhei.client;

import java.util.Arrays;

import org.gwtwidgets.client.ui.pagination.Column;
import org.gwtwidgets.client.ui.pagination.DataProvider;
import org.gwtwidgets.client.ui.pagination.DefaultPaginationBehavior;
import org.gwtwidgets.client.ui.pagination.PaginationBehavior;
import org.gwtwidgets.client.ui.pagination.PaginationParameters;
import org.gwtwidgets.client.ui.pagination.RowRenderer;

import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
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
	private final FlexTable pagingControlsTable = new FlexTable();
	private DefaultPaginationBehavior paginationBehavior;

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

			hookUpCallbacks();

			setPaginator();

			//set orders flexTable style - header picks up headeRow style bcs of paginationBehavior
			ordersFlexTable.addStyleName("orderList");
			ordersFlexTable.setWidth("800px");

			//set mainPanel Style
			mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			mainPanel.add(ordersFlexTable);
			mainPanel.add(pagingControlsTable);

			paginationBehavior.showPage(1,Columns.ID._columnText, true);

			// Associate the Main panel with the HTML host page.
			RootPanel.get("adminConsole").add(mainPanel);
		}
	}

	/**
	 * Set the paginator
	 */
	private void setPaginator() 
	{
		paginationBehavior = new DefaultPaginationBehavior(pagingControlsTable,ordersFlexTable,15) {

			@Override
			protected RowRenderer getRowRenderer() {
				return new RowRenderer(){

					@Override
					public void populateRow(PaginationBehavior pagination, int row,
							Object object) {
						OrderDTO order=(OrderDTO)object;
						//The header row will be added afterward (apparently, it's 2am we might be wrong)
						final int frow = row +1 ;
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
									long rejectedId = Long.parseLong(ordersFlexTable.getText(frow, Columns.ID.ordinal()));

									statusChangedSubHandler(frow, rejectedId, Status.REJECTED);
								}
							});

							// Add a button to remove this stock from the table.
							Button acceptOrderButton = new Button("Accept");

							acceptOrderButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									//get ID from clicked row
									long acceptedId = Long.parseLong(ordersFlexTable.getText(frow, Columns.ID.ordinal()));

									statusChangedSubHandler(frow, acceptedId, Status.ACCEPTED);
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
									//long orderId = Long.parseLong(ordersFlexTable.getText(frow, Columns.ID.ordinal()));

									Window.alert("This feature is not implemented yet - stay tuned for good!");
								}
							});

							ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), uploadLogoButton);
						}
						else
						{
							ordersFlexTable.setText(row, Columns.ACTIONS.ordinal(), "N/A");
						}
					}};


			}

			@Override
			protected DataProvider getDataProvider() {
				return new DataProvider(){
					@Override
					public void update(PaginationParameters parameters,
							AsyncCallback updateTableCallback) {
						_service.getOrdersByUserAndStatus(
								null, 
								null,
								parameters.getOffset(), 
								parameters.getMaxResults(), 
								updateTableCallback);
					}};
			}

			@Override
			protected Column[] getColumns() {

				return new Column[] { new Column(Columns.ID.getColumnText()),
						new Column(Columns.USER.getColumnText()),
						new Column(Columns.LOGO_TEXT.getColumnText()),
						new Column(Columns.TAGS.getColumnText()),
						new Column(Columns.STATUS.getColumnText()),
						new Column(Columns.TIMER.getColumnText()),
						new Column(Columns.ACTIONS.getColumnText())	};
			}
		};

		paginationBehavior.setNextPageText("Next >>");
		paginationBehavior.setPreviousPageText("<< Prev");
	}

	private void hookUpCallbacks() 
	{

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
