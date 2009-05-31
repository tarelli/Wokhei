package com.brainz.wokhei.client;

import java.util.Arrays;
import java.util.List;

import org.gwtwidgets.client.ui.pagination.Column;
import org.gwtwidgets.client.ui.pagination.DataProvider;
import org.gwtwidgets.client.ui.pagination.DefaultPaginationBehavior;
import org.gwtwidgets.client.ui.pagination.PaginationBehavior;
import org.gwtwidgets.client.ui.pagination.PaginationParameters;
import org.gwtwidgets.client.ui.pagination.RowRenderer;

import com.brainz.wokhei.resources.Messages;
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
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
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

	private final VerticalPanel _mainPanel = new VerticalPanel();
	private final FlexTable _ordersFlexTable = new FlexTable();
	private final FlexTable _pagingControlsTable = new FlexTable();
	private DefaultPaginationBehavior _paginationBehavior;

	// contains all the different filters needed
	private final HorizontalPanel _filteringPanel = new HorizontalPanel();
	// filter controls
	private final VerticalPanel _statusFilter = new VerticalPanel();
	private final ListBox _statusFilterBox = new ListBox();
	private final Button _filterButton = new Button("Filter!");


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

			setFilters();

			setPaginator();

			//set orders flexTable style - header picks up headeRow style bcs of paginationBehavior
			_ordersFlexTable.addStyleName("orderList");
			_ordersFlexTable.setWidth("800px");

			//set mainPanel Style
			_mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			_mainPanel.add(_filteringPanel);
			_mainPanel.add(_ordersFlexTable);
			_mainPanel.add(_pagingControlsTable);

			_paginationBehavior.showPage(1,Columns.ID._columnText, true);

			// Associate the Main panel with the HTML host page.
			RootPanel.get("adminConsole").add(_mainPanel);
		}
	}

	/**
	 * Set the filters
	 */
	private void setFilters() {

		setStatusFilter();

		_filterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//TODO --> reload!
				_paginationBehavior.showPage(1,Columns.ID._columnText, true);
			}
		});

		// add stuff to filtering panel
		_filteringPanel.add(_statusFilter);
		_filteringPanel.add(_filterButton);
	}

	private void setStatusFilter() {

		Label statusFilterLbl = new Label(Messages.ADMIN_STATUS_FILTER_LABEL.getString()); 
		_statusFilterBox.setHeight("30px");

		//retrieve all possible statuses
		List<Status> Statuses = Arrays.asList(Status.values());

		// loop through items and generate checkboxes for status
		for (Status status : Statuses)
		{
			String statusStr = status.toString().toLowerCase();

			_statusFilterBox.addItem(statusStr);
		}

		//set default (accepted --> maybe should be incoming)
		_statusFilterBox.setItemSelected(1, true);

		//add label and checkbox panel to filterPanel
		_statusFilter.add(statusFilterLbl);
		_statusFilter.add(_statusFilterBox);
	}

	private Status getStatusFromStatusFilter()
	{
		String statusAsStr = _statusFilterBox.getItemText(_statusFilterBox.getSelectedIndex());

		Status status = Status.valueOf(statusAsStr.toUpperCase());


		return status;
	}

	/**
	 * Set the paginator
	 */
	private void setPaginator() 
	{
		_paginationBehavior = new DefaultPaginationBehavior(_pagingControlsTable,_ordersFlexTable,15) {

			@Override
			protected RowRenderer getRowRenderer() {
				return new RowRenderer(){

					@Override
					public void populateRow(PaginationBehavior pagination, int row,
							Object object) {
						OrderDTO order=(OrderDTO)object;
						//The header row will be added afterward (apparently, it's 2am we might be wrong)
						final int frow = row +1 ;
						_ordersFlexTable.setText(row, Columns.ID.ordinal(), order.getId().toString());
						_ordersFlexTable.setText(row, Columns.USER.ordinal(), order.getCustomerEmail());
						_ordersFlexTable.setText(row, Columns.LOGO_TEXT.ordinal(), order.getText());
						_ordersFlexTable.setText(row, Columns.TAGS.ordinal(), Arrays.asList(order.getTags()).toString());
						_ordersFlexTable.setText(row, Columns.STATUS.ordinal(), order.getStatus().toString());
						_ordersFlexTable.setText(row, Columns.TIMER.ordinal(), "N/A");

						if(order.getStatus()==Status.INCOMING)
						{
							// Add a button to remove this stock from the table.
							Button rejectOrderButton = new Button("Reject");

							rejectOrderButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									//get ID from clicked row
									long rejectedId = Long.parseLong(_ordersFlexTable.getText(frow, Columns.ID.ordinal()));

									statusChangedSubHandler(frow, rejectedId, Status.REJECTED);
								}
							});

							// Add a button to remove this stock from the table.
							Button acceptOrderButton = new Button("Accept");

							acceptOrderButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									//get ID from clicked row
									long acceptedId = Long.parseLong(_ordersFlexTable.getText(frow, Columns.ID.ordinal()));

									statusChangedSubHandler(frow, acceptedId, Status.ACCEPTED);
								}
							});

							HorizontalPanel actionPanel = new HorizontalPanel();
							actionPanel.add(acceptOrderButton);
							actionPanel.add(rejectOrderButton);

							_ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), actionPanel);
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

							_ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), uploadLogoButton);
						}
						else
						{
							_ordersFlexTable.setText(row, Columns.ACTIONS.ordinal(), "N/A");
						}
					}};


			}

			@Override
			protected DataProvider getDataProvider() {
				return new DataProvider(){
					@Override
					public void update(PaginationParameters parameters,
							AsyncCallback updateTableCallback) {

						Status status = getStatusFromStatusFilter();

						_service.getOrdersByUserAndStatus(
								status, 
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

		_paginationBehavior.setNextPageText("Next >>");
		_paginationBehavior.setPreviousPageText("<< Prev");
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
			_ordersFlexTable.setText(_rowForClientStatusUpdate, Columns.STATUS.ordinal(), _statusForClientUpdate.toString());
			// disable buttons 
			//TODO --> decision to show or not row according to filters
			HorizontalPanel actionPanel = ((HorizontalPanel)_ordersFlexTable.getWidget(_rowForClientStatusUpdate, Columns.ACTIONS.ordinal()));
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
