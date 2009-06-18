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

import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.OrderDTO;
import com.brainz.wokhei.shared.Status;
import com.codelathe.gwt.client.SlideShow;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteHandler;
import com.google.gwt.user.client.ui.FormPanel.SubmitEvent;
import com.google.gwt.user.client.ui.FormPanel.SubmitHandler;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * @author John_Idol
 *
 */
public class AdminOrderBrowserModulePart extends AModulePart{

	public enum Columns {

		ID("ID"),
		USER("User"),
		LOGO_TEXT("Logo name"),
		TAGS("Tags"),
		COLOUR("Colour"),
		DATE("Date"),
		STATUS("Status"),
		TIMER("Timer"),
		ACTIONS("");

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

	private final HorizontalPanel _toolbarPanel = new HorizontalPanel();

	//upload stuff
	private final PopupPanel _uploadPopupPanel=new PopupPanel(true);

	// add admin stuff
	private final VerticalPanel _addAdminPanel = new VerticalPanel();
	private final PopupPanel _optionsPopupPanel = new PopupPanel(true);
	private final TextBox _addAdminTextBox = new TextBox();
	private final Button _addAdminButton = new Button("Add Admin!");
	private final Label _addAdminMsgLabel = new Label("");

	// contains all the different filters needed
	private final VerticalPanel _filteringPanel = new VerticalPanel();
	// status filter controls
	private final Image _filteringImage=new Image(Images.FILTER.getImageURL());
	private final Image _optionsImage=new Image(Images.OPTIONS.getImageURL());
	private final PopupPanel _filteringPopupPanel = new PopupPanel(true);
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

	private AsyncCallback<Long> _setOrderStatusCallback = null;
	private AsyncCallback<Boolean> _addAdminCallback = null;
	private AsyncCallback<Boolean> _setLogoCallback=null;
	private AsyncCallback<Boolean> _hasImageCallBack=null;

	//related with status update chaching during async call
	private int _rowForClientStatusUpdate;
	private Status _statusForClientUpdate;
	private final Button _clearFiltersButton=new Button(Messages.ADMIN_CLEAR_FILTERS.getString());

	//the order id when click to the upload button
	private final FormPanel _rasterizedForm = new FormPanel();
	private final FormPanel _vectorialForm = new FormPanel();

	private final SlideShow slideShow = new SlideShow();




	/* 
	 * Init module part
	 */
	@Override
	public void initModulePart(OrderServiceAsync orderService, UtilityServiceAsync utilityService, AdminServiceAsync adminService) {
		if(RootPanel.get("adminConsole")!=null)
		{
			super.initModulePart(orderService, utilityService, adminService);

			hookUpCallbacks();

			setupToolbarPanel();

			setPaginator();

			setupUploadPanel();

			//set orders flexTable style - header picks up headeRow style bcs of paginationBehavior
			_ordersFlexTable.addStyleName("orderList");
			_ordersFlexTable.setWidth("800px");

			//set mainPanel Style
			_mainPanel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);

			_mainPanel.add(_toolbarPanel);
			_mainPanel.add(_ordersFlexTable);
			_mainPanel.add(_pagingControlsTable);

			_paginationBehavior.showPage(1,Columns.ID._columnText, true);

			// Associate the Main panel with the HTML host page.
			RootPanel.get("adminConsole").add(_mainPanel);
		}
	}

	private void setupToolbarPanel() {
		setupFiltersPanel();
		setupAddAdminPanel();

		_filteringPopupPanel.setStyleName("adminPopup");
		_optionsPopupPanel.setStyleName("adminPopup");

		_filteringPopupPanel.setWidget(_filteringPanel);
		_optionsPopupPanel.setWidget(_addAdminPanel);

		_filteringImage.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_filteringPopupPanel.showRelativeTo(_filteringImage);
			}});

		_optionsImage.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				_optionsPopupPanel.showRelativeTo(_optionsImage);
			}});

		_toolbarPanel.add(_filteringImage);
		_toolbarPanel.add(_optionsImage);

	}

	private void setupAddAdminPanel() {
		Label addAdminLbl = new Label("Add Admin");
		addAdminLbl.setStylePrimaryName("label");

		_addAdminTextBox.setText(Messages.ADMIN_ADD_ADMIN_DEFAULT_TXT.getString());

		_addAdminButton.addClickHandler(new ClickHandler() {
			//prepararsi alla porcata mondiale
			public void onClick(ClickEvent event) {
				if(	_addAdminTextBox.getText() != "" 
					&& !_addAdminTextBox.getText().contains(" ") 
					&& !(_addAdminTextBox.getText() == Messages.ADMIN_ADD_ADMIN_DEFAULT_TXT.getString())
					&& _addAdminTextBox.getText().endsWith("@wokhei.com"))
				{
					_adminService.addAdmin(_addAdminTextBox.getText(), _addAdminCallback);
					_optionsPopupPanel.hide();
				}
				else
					_addAdminMsgLabel.setText("Please insert a valid wokhei.com email");
			}
		});
		_addAdminPanel.setSpacing(10);
		_addAdminPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		_addAdminPanel.add(addAdminLbl);
		_addAdminPanel.add(_addAdminTextBox);
		_addAdminPanel.add(_addAdminButton);
	}

	/**
	 * Set the filters
	 */
	private void setupFiltersPanel() {

		setStatusFilter();
		setUserFilter();
		setDateRangeFilter();

		_clearFiltersButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_statusFilterBox.setSelectedIndex(0);
				_userFilterBox.setText("");
				_startDateBox.setValue(null);
				_endDateBox.setValue(null);
				_paginationBehavior.showPage(1,Columns.ID._columnText, true);
				_filteringPopupPanel.hide();
			}
		});

		_filterButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//--> reload grid!
				_paginationBehavior.showPage(1,Columns.ID._columnText, true);
				_filteringPopupPanel.hide();
			}
		});
		_filteringPanel.setVerticalAlignment(VerticalPanel.ALIGN_BOTTOM);
		_filteringPanel.setSpacing(10);

		// add stuff to filtering panel

		_filteringPanel.add(_statusFilter);
		_filteringPanel.add(_userFilter);
		_filteringPanel.add(_dateRangeFilter);
		_filteringPanel.add(_clearFiltersButton);
		_filteringPanel.add(_filterButton);
	}

	private void setUserFilter() {
		Label userFilterLbl = new Label(Messages.ADMIN_USER_FILTER_LABEL.getString());
		userFilterLbl.setStyleName("label");
		_userFilterBox.setText(Messages.ADMIN_USER_FILTER_BOX.getString());

		_userFilterBox.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				_userFilterBox.selectAll();
			}
		});

		_userFilter.add(userFilterLbl);
		_userFilter.add(_userFilterBox);
	}

	private void setDateRangeFilter() {
		Label dateFilterLbl = new Label(Messages.ADMIN_DATE_FILTER_LABEL.getString());
		dateFilterLbl.setStyleName("label");
		//initialize datePickers
		// Set the value in the text box when the user selects a date
		_startDateBox.setTitle("Start Date");
		_startDateBox.getTextBox().setText("Pick Start Date");
		_endDateBox.setTitle("End Date");
		_endDateBox.getTextBox().setText("Pick End Date");

		_dateBoxesPanel.add(_startDateBox);
		_dateBoxesPanel.add(getNewWhiteSpace());
		_dateBoxesPanel.add(getNewWhiteSpace());
		_dateBoxesPanel.add(_endDateBox);


		_dateRangeFilter.add(dateFilterLbl);
		_dateRangeFilter.add(_dateBoxesPanel);
	}

	private void setStatusFilter() {

		Label statusFilterLbl = new Label(Messages.ADMIN_STATUS_FILTER_LABEL.getString()); 
		statusFilterLbl.setStyleName("label");
		//_statusFilterBox.setHeight("30px");
		_statusFilterBox.addItem("All");

		//retrieve all possible statuses
		List<Status> Statuses = Arrays.asList(Status.values());

		// loop through items and generate checkboxes for status
		for (Status status : Statuses)
		{
			String statusStr = Messages.valueOf(status.toString()).getString();

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
				!_userFilterBox.getText().contains(" ") && !(_userFilterBox.getText().length()==0))
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
	 * @param username
	 * @return
	 */
	private String processUsername(String username)
	{
		if(username.contains("@"))
			return (username.substring(0, username.indexOf('@')));
		else
			return username;
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
						_ordersFlexTable.setText(row, Columns.USER.ordinal(), processUsername(order.getCustomerEmail()));
						_ordersFlexTable.setWidget(row,Columns.LOGO_TEXT.ordinal(), getNameLabel( order.getText()));
						String list=Arrays.asList(order.getTags()).toString().replace(",","");
						_ordersFlexTable.setWidget(row,Columns.TAGS.ordinal(),  getTagsLabel(list.substring(1, list.length()-1)));
						_ordersFlexTable.setWidget(row,Columns.COLOUR.ordinal(),  getColourPanel(order.getColour().toString()));
						DateTimeFormat fmt = DateTimeFormat.getFormat("dd/mm/yy");
						_ordersFlexTable.setText(row,Columns.DATE.ordinal(),  fmt.format(order.getDate()));


						_ordersFlexTable.setWidget(row, Columns.STATUS.ordinal(), getStatusImage(order.getStatus().toString(),order.getId()));
						_ordersFlexTable.setText(row, Columns.TIMER.ordinal(), "N/A");

						if(order.getStatus()==Status.INCOMING)
						{
							// Add a button to remove this stock from the table.
							Button rejectOrderButton = new Button("Reject");

							rejectOrderButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									if(Window.confirm(Messages.ADMIN_CONFIRM_REJECT_TXT.getString()))
									{
										//get ID from clicked row
										long rejectedId = Long.parseLong(_ordersFlexTable.getText(frow, Columns.ID.ordinal()));
										statusChangedSubHandler(frow, rejectedId, Status.REJECTED);
									}
								}
							});

							// Add a button to remove this stock from the table.
							Button acceptOrderButton = new Button("Accept");

							acceptOrderButton.addClickHandler(new ClickHandler() {
								public void onClick(ClickEvent event) {

									if(Window.confirm(Messages.ADMIN_CONFIRM_ACCEPT_TXT.getString()))
									{
										//get ID from clicked row
										long acceptedId = Long.parseLong(_ordersFlexTable.getText(frow, Columns.ID.ordinal()));

										statusChangedSubHandler(frow, acceptedId, Status.ACCEPTED);
									}
								}
							});

							VerticalPanel actionPanel = new VerticalPanel();
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
									updateUploadPanel(Long.parseLong(_ordersFlexTable.getText(frow, Columns.ID.ordinal())));
									_uploadPopupPanel.center();
									_uploadPopupPanel.show();
								}


							});

							_ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), uploadLogoButton);
						}


					}


				};
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
						new Column(Columns.COLOUR.getColumnText()),
						new Column(Columns.DATE.getColumnText()),
						new Column(Columns.STATUS.getColumnText()),
						new Column(Columns.TIMER.getColumnText()),
						new Column(Columns.ACTIONS.getColumnText())	};
			}
		};

		_paginationBehavior.setNextPageText("Next >>");
		_paginationBehavior.setPreviousPageText("<< Prev");
	}

	/**
	 * 
	 */
	private void setupUploadPanel() 
	{
		_uploadPopupPanel.setStyleName("adminPopup");
		VerticalPanel uploadPanel=new VerticalPanel();

		_rasterizedForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		_rasterizedForm.setMethod(FormPanel.METHOD_POST);

		HorizontalPanel rasterizedPanel=new HorizontalPanel();
		rasterizedPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		rasterizedPanel.setSpacing(10);
		Label rasterizedLbl=new Label(Messages.RASTERIZED_LBL.getString());
		FileUpload rasterizedUpload=new FileUpload();
		rasterizedUpload.setName("uploadFormElement");
		Button rasterizedUploadBtn=new Button(Messages.UPLOAD.getString());
		rasterizedPanel.add(rasterizedLbl);
		rasterizedPanel.add(rasterizedUpload);
		rasterizedPanel.add(rasterizedUploadBtn);


		_rasterizedForm.addSubmitHandler(new SubmitHandler(){

			public void onSubmit(SubmitEvent event) {
				//VALIDATION

			}
		});

		_rasterizedForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			public void onSubmitComplete(SubmitCompleteEvent event)
			{
				//UPDATE

			}
		});


		rasterizedUploadBtn.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{
				_rasterizedForm.submit();
			}
		});


		_vectorialForm.setAction("/wokhei/uploadfile");
		_vectorialForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		_vectorialForm.setMethod(FormPanel.METHOD_POST);

		_vectorialForm.addSubmitHandler(new SubmitHandler(){

			public void onSubmit(SubmitEvent event) {
				//VALIDATION

			}
		});

		_vectorialForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			public void onSubmitComplete(SubmitCompleteEvent event)
			{
				//UPDATE

			}
		});

		HorizontalPanel vectorialPanel=new HorizontalPanel();
		vectorialPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		vectorialPanel.setSpacing(10);
		Label vectorialLbl=new Label(Messages.VECTORIAL_LBL.getString());
		FileUpload vectorialUpload=new FileUpload();
		vectorialUpload.setName("uploadFormElement");
		Button vectorialUploadBtn=new Button(Messages.UPLOAD.getString());

		vectorialUploadBtn.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{
				_vectorialForm.submit();
			}
		});

		vectorialPanel.add(vectorialLbl);
		vectorialPanel.add(vectorialUpload);
		vectorialPanel.add(vectorialUploadBtn);

		_rasterizedForm.setWidget(rasterizedPanel);
		_vectorialForm.setWidget(vectorialPanel);

		uploadPanel.add(_rasterizedForm);
		uploadPanel.add(_vectorialForm);
		_uploadPopupPanel.add(uploadPanel);
	}

	/**
	 * @param orderId 
	 * 
	 */
	private void updateUploadPanel(long orderId) 
	{
		_rasterizedForm.setAction("/wokhei/uploadfile?file=1&orderid="+orderId);
		_vectorialForm.setAction("/wokhei/uploadfile?file=2&orderid="+orderId);
	}

	/**
	 * @return
	 */
	private Widget getNewWhiteSpace() 
	{
		Label whiteSpace=new Label();
		whiteSpace.setWidth("5px");
		return whiteSpace;
	}

	/**
	 * @param substring
	 * @return
	 */
	private Widget getColourPanel(String substring) 
	{
		HorizontalPanel cPanel=new HorizontalPanel();
		Label pantone=new Label(substring.replace("PANTONE",""));
		Label colour=new Label();
		colour.setStyleName("colour"+substring);
		colour.setHeight("20px");
		colour.setWidth("20px");

		cPanel.add(colour);
		cPanel.add(getNewWhiteSpace());
		cPanel.add(pantone);
		return cPanel;
	}

	/**
	 * @param substring
	 * @return
	 */
	private Widget getStatusImage(final String substring, final Long orderId) 
	{
		final Status status=Status.valueOf(substring);
		Image statusImage=new Image(Images.valueOf(substring).getSmallImageURL());
		statusImage.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				switch(status)
				{
				case ACCEPTED:
				case ARCHIVED:
				case BOUGHT:
				case IN_PROGRESS:
				case QUALITY_GATE:
				case READY:
					slideShow.showSingleImage("\\wokhei\\getlogo?orderid="+orderId, Messages.COPYRIGHT.getString());
					break;
				case REJECTED:
				case INCOMING:
				}

			}});
		return statusImage;
	}

	/**
	 * @param substring
	 * @return
	 */
	private Widget getTagsLabel(String substring) 
	{
		Label tags=new Label(substring);
		tags.setStyleName("adminTags");
		return tags;
	}

	/**
	 * @param text
	 * @return
	 */
	private Widget getNameLabel(String text) 
	{
		Label tags=new Label(text);
		tags.setStyleName("adminLogoName");
		return tags;
	}

	/**
	 * 
	 */
	private void hookUpCallbacks() 
	{

		_setLogoCallback= new AsyncCallback<Boolean>()
		{

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub

			}

		};

		_setOrderStatusCallback = new AsyncCallback<Long>() {

			public void onSuccess(Long result) {
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

		_hasImageCallBack = new AsyncCallback<Boolean>()
		{

			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}

			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub

			}

		};

	}

	/**
	 * @param result
	 */
	protected void updateAddAdminMessageLbl(Boolean result) 
	{
		if(result)
		{
			_addAdminMsgLabel.setText("All good - admin has been added!");	
		}
		else
		{
			_addAdminMsgLabel.setText("Uh-Oh ... some problem has occured - admin not added!");
		}
	}

	protected void updateOrderStatusOnClient(Long orderId) {

		// if successful remove row from orders table
		if(orderId!=null)
		{
			// set new status
			_ordersFlexTable.setWidget(_rowForClientStatusUpdate, Columns.STATUS.ordinal(), getStatusImage(_statusForClientUpdate.toString(),orderId));
			// disable buttons 
			//TODO --> decision to show or not row according to filters
			VerticalPanel actionPanel = ((VerticalPanel)_ordersFlexTable.getWidget(_rowForClientStatusUpdate, Columns.ACTIONS.ordinal()));
			((Button)actionPanel.getWidget(0)).setEnabled(false);
			((Button)actionPanel.getWidget(1)).setEnabled(false);
		}
		else
		{
			Window.alert("status amend Operation Failed - try again, you may get lucky!");
		}

	}	

	/**
	 * @param row
	 * @param orderId
	 * @param status
	 */
	private void statusChangedSubHandler(int row, long orderId, Status status)
	{
		//set row index - it will be used on callback success (let's be aware this is shit!)
		_rowForClientStatusUpdate = row;
		_statusForClientUpdate = status;

		//call setOrderStatus callback
		_orderService.setOrderStatus(orderId, status, _setOrderStatusCallback);
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() 
	{
		try {
			throw new Exception("Metti che il VP ci implementa tutto e usa questo metodo ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
