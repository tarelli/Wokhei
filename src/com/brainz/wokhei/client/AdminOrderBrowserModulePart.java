package com.brainz.wokhei.client;

import java.util.Arrays;
import java.util.Date;
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
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

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

	// add admin stuff
	private final HorizontalPanel _addAdminPanel = new HorizontalPanel();
	private final TextBox _addAdminTextBox = new TextBox();
	private final Button _addAdminButton = new Button("Add Admin!");
	private final Label _addAdminMsgLabel = new Label("");

	// contains all the different filters needed
	private final HorizontalPanel _filteringPanel = new HorizontalPanel();
	// status filter controls
	private final VerticalPanel _statusFilter = new VerticalPanel();
	private final ListBox _statusFilterBox = new ListBox();
	//user filter controls
	private final VerticalPanel _userFilter = new VerticalPanel();
	private final TextBox _userFilterBox = new TextBox();
	// date filter controls
	private final VerticalPanel _dateRangeFilter = new VerticalPanel();
	private final HorizontalPanel _dateBoxesPanel = new HorizontalPanel();
	private final DateBox _startDateBox = new DateBox();
	private final DateBox _endDateBox = new DateBox();
	// filter button
	private final Button _filterButton = new Button("Filter!");

	private AsyncCallback<Boolean> _setOrderStatusCallback = null;
	private AsyncCallback<Boolean> _addAdminCallback = null;

	//related with status update chaching during async call
	private int _rowForClientStatusUpdate;
	private Status _statusForClientUpdate;

	/* 
	 * Init module part
	 */
	@Override
	public void initModulePart(OrderServiceAsync orderService, UtilityServiceAsync utilityService, AdminServiceAsync adminService) {
		if(RootPanel.get("adminConsole")!=null)
		{
			super.initModulePart(orderService, utilityService, adminService);

			hookUpCallbacks();

			setFilters();

			setPaginator();

			setupAddAdminPanel();

			//set orders flexTable style - header picks up headeRow style bcs of paginationBehavior
			_ordersFlexTable.addStyleName("orderList");
			_ordersFlexTable.setWidth("800px");

			//set mainPanel Style
			_mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			_mainPanel.add(_filteringPanel);
			_mainPanel.add(_ordersFlexTable);
			_mainPanel.add(_pagingControlsTable);
			_mainPanel.add(_addAdminPanel);
			_mainPanel.add(_addAdminMsgLabel);

			_paginationBehavior.showPage(1,Columns.ID._columnText, true);

			// Associate the Main panel with the HTML host page.
			RootPanel.get("adminConsole").add(_mainPanel);
		}
	}

	private void setupAddAdminPanel() {
		Label addAdminLbl = new Label("Add Admin");

		_addAdminTextBox.setText(Messages.ADMIN_ADD_ADMIN_DEFAULT_TXT.getString());

		_addAdminButton.addClickHandler(new ClickHandler() {
			//prepararsi alla porcata mondiale
			public void onClick(ClickEvent event) {
				if(	_addAdminTextBox.getText() != "" 
					&& !_addAdminTextBox.getText().contains(" ") 
					&& !(_addAdminTextBox.getText() == Messages.ADMIN_ADD_ADMIN_DEFAULT_TXT.getString())
					&& _addAdminTextBox.getText().contains("@wokhei.com"))
				{
					_adminService.addAdmin(_addAdminTextBox.getText(), _addAdminCallback);
				}
				else
					_addAdminMsgLabel.setText("Please insert a valid wokhei.com email");
			}
		});

		_addAdminPanel.add(addAdminLbl);
		_addAdminPanel.add(_addAdminTextBox);
		_addAdminPanel.add(_addAdminButton);
	}

	/**
	 * Set the filters
	 */
	private void setFilters() {

		setStatusFilter();
		setUserFilter();
		setDateRangeFilter();

		_filterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//--> reload grid!
				_paginationBehavior.showPage(1,Columns.ID._columnText, true);
			}
		});

		// add stuff to filtering panel
		_filteringPanel.add(_statusFilter);
		_filteringPanel.add(_userFilter);
		_filteringPanel.add(_dateRangeFilter);
		_filteringPanel.add(_filterButton);
	}

	private void setUserFilter() {
		Label userFilterLbl = new Label(Messages.ADMIN_USER_FILTER_LABEL.getString());

		_userFilterBox.setText(Messages.ADMIN_USER_FILTER_BOX.getString());

		_userFilter.add(userFilterLbl);
		_userFilter.add(_userFilterBox);
	}

	private void setDateRangeFilter() {
		Label dateFilterLbl = new Label(Messages.ADMIN_DATE_FILTER_LABEL.getString());

		//initialize datePickers
		// Set the value in the text box when the user selects a date
		_startDateBox.setTitle("Start Date");
		_startDateBox.getTextBox().setText("Pick Start Date");
		_endDateBox.setTitle("End Date");
		_endDateBox.getTextBox().setText("Pick End Date");

		_dateBoxesPanel.add(_startDateBox);
		_dateBoxesPanel.add(_endDateBox);

		_dateRangeFilter.add(dateFilterLbl);
		_dateRangeFilter.add(_dateBoxesPanel);
	}

	private void setStatusFilter() {

		Label statusFilterLbl = new Label(Messages.ADMIN_STATUS_FILTER_LABEL.getString()); 
		_statusFilterBox.setHeight("30px");
		_statusFilterBox.addItem("All");

		//retrieve all possible statuses
		List<Status> Statuses = Arrays.asList(Status.values());

		// loop through items and generate checkboxes for status
		for (Status status : Statuses)
		{
			String statusStr = status.toString().toLowerCase();

			_statusFilterBox.addItem(statusStr);
		}

		//set default ("All")
		_statusFilterBox.setItemSelected(0, true);

		//add label and checkbox panel to filterPanel
		_statusFilter.add(statusFilterLbl);
		_statusFilter.add(_statusFilterBox);
	}

	private Status getStatusFromStatusFilter()
	{
		String statusAsStr = _statusFilterBox.getItemText(_statusFilterBox.getSelectedIndex());

		Status status = null;

		if(!statusAsStr.equals("All"))
		{
			status = Status.valueOf(statusAsStr.toUpperCase());
		}

		return status;
	}

	private String getUserEmailFilter()
	{
		String userEmail = null;

		if(_userFilterBox.getText()!= Messages.ADMIN_USER_FILTER_BOX.getString() && 
				!_userFilterBox.getText().contains(" "))
		{
			userEmail = _userFilterBox.getText();
		}

		return userEmail;
	}

	private Date getStartDateFilter()
	{
		Date startDate = null;

		if(_startDateBox.getValue()!= null)
		{
			startDate = _startDateBox.getValue();
		}

		return startDate;
	}

	private Date getEndDateFilter()
	{
		Date endDate = null;

		if(_endDateBox.getValue()!= null)
		{
			endDate = _endDateBox.getValue();
		}

		return endDate;
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
					@SuppressWarnings("unchecked")
					public void update(PaginationParameters parameters,
							AsyncCallback updateTableCallback) {

						Status status = getStatusFromStatusFilter();
						String userEmail = getUserEmailFilter();
						Date startDate = getStartDateFilter();
						Date endDate = getEndDateFilter();

						_orderService.getOrdersByUserAndStatus(
								status, 
								userEmail,
								startDate,
								endDate,
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

		_addAdminCallback = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				//update msgLbl
				updateAddAdminMessageLbl(result);
			}

			public void onFailure(Throwable caught) {
				//TODO - do something in case of failure
			}
		};

	}

	protected void updateAddAdminMessageLbl(Boolean result) {
		if(result)
		{
			_addAdminMsgLabel.setText("All good - admin has been added!");	
		}
		else
		{
			_addAdminMsgLabel.setText("Uh-Oh ... some problem has occured - admin not added!");
		}
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
		_orderService.setOrderStatus(orderId, status, _setOrderStatusCallback);
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
