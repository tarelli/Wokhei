package com.brainz.wokhei.client.admin;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.gwtwidgets.client.ui.pagination.Column;
import org.gwtwidgets.client.ui.pagination.DataProvider;
import org.gwtwidgets.client.ui.pagination.DefaultPaginationBehavior;
import org.gwtwidgets.client.ui.pagination.PaginationBehavior;
import org.gwtwidgets.client.ui.pagination.PaginationParameters;
import org.gwtwidgets.client.ui.pagination.RowRenderer;

import com.brainz.wokhei.client.common.AModulePart;
import com.brainz.wokhei.client.common.OrderServiceAsync;
import com.brainz.wokhei.client.common.Service;
import com.brainz.wokhei.client.common.UtilityServiceAsync;
import com.brainz.wokhei.resources.Images;
import com.brainz.wokhei.resources.Messages;
import com.brainz.wokhei.shared.Colour;
import com.brainz.wokhei.shared.DateDifferenceCalculator;
import com.brainz.wokhei.shared.FileType;
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
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
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

		ID("Prog"),
		USER("User"),
		LOGO_TEXT("Name"),
		DESC("Desc"),
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

	public enum UploadType {

		PRESENTATION_PNG("Presentation"),
		RASTERIZED_PNG("Rasterized"),
		VECTORIAL_PDF("Vectorial"),
		NONE("None");

		String _text;

		private UploadType()
		{

		}

		private UploadType(String name)
		{
			_text= name;
		}

		public String getColumnText()
		{
			return _text;
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
	private final VerticalPanel _configPanel = new VerticalPanel();
	private final PopupPanel _optionsPopupPanel = new PopupPanel(true);
	private final TextBox _addAdminTextBox = new TextBox();
	private final Button _addAdminButton = new Button("Add Admin!");
	private final Label _addAdminMsgLabel = new Label("");
	private final Label _orderKillSwitchLbl = new Label("Order Killswitch");
	private final Button _orderKillswitchButton = new Button("");
	private final String ON = new String("OFF-->ON");
	private final String OFF = new String("ON-->OFF");

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

	// callbacks
	private AsyncCallback<Long> _setOrderStatusCallback = null;
	private AsyncCallback<Boolean> _addAdminCallback = null;
	private AsyncCallback<Date> _getServerTimestampCallback = null;
	private AsyncCallback<Boolean> _getOrderKillswitchCallback = null;
	private AsyncCallback<Boolean> _setOrderKillswitchCallback = null;
	final Button _sandBoxButton= new Button();

	//related with status update chaching during async call
	private int _rowForClientStatusUpdate;
	private Status _statusForClientUpdate;
	private final Button _clearFiltersButton=new Button(Messages.ADMIN_CLEAR_FILTERS.getString());

	//the order id when click to the upload button
	private final FormPanel _rasterizedForm = new FormPanel();
	private final FormPanel _presentationForm = new FormPanel();
	private final FormPanel _vectorialForm = new FormPanel();

	private final SlideShow slideShow = new SlideShow();
	private final Image _isRasterizedImageUploaded=new Image(Images.NOK.getImageURL());
	private final Image _isPresentationImageUploaded=new Image(Images.NOK.getImageURL());
	private final Image _isVectorialImageUploaded=new Image(Images.NOK.getImageURL());
	private long _uploadPanelOrderId=-1;
	private final Label _uploadLogoName=new Label();
	private final Label _uploadTags=new Label();

	private Date _serverTimeStamp = null;

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#initModulePart(com.brainz.wokhei.client.OrderServiceAsync, com.brainz.wokhei.client.UtilityServiceAsync, com.brainz.wokhei.client.AdminServiceAsync)
	 */
	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("adminConsole")!=null)
		{
			hookUpCallbacks();

			getServerTimeStamp();

			setupToolbarPanel();

			// the rest of the stuff is in initModulePartCore
			// callled from the callback result of getServertimeStamp (the page depends on that)
			// note: anche noto come METODO PUERCIS
		}
	}

	// bienvenuti nel paese dei puerca
	private void initModulePartCore()
	{
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

	/**
	 * 
	 */
	private void getServerTimeStamp() 
	{
		//get server time stamp - sets variable for populating timer field
		((UtilityServiceAsync) getService(Service.UTILITY_SERVICE)).getServerTimestamp(_getServerTimestampCallback);
	}

	/**
	 * 
	 */
	private void setupToolbarPanel() 
	{
		setupFiltersPanel();
		setupConfigPanel();

		_filteringPopupPanel.setStyleName("adminPopup");
		_optionsPopupPanel.setStyleName("adminPopup");

		_filteringPopupPanel.setWidget(_filteringPanel);
		_optionsPopupPanel.setWidget(_configPanel);
		_filteringImage.addStyleName("labelButton");
		_optionsImage.addStyleName("labelButton");
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

	/**
	 * 
	 */
	private void setupConfigPanel() 
	{
		// setup add admin stuff
		Label addAdminLbl = new Label("Add Admin");
		addAdminLbl.setStylePrimaryName("label");

		_addAdminTextBox.setText(Messages.ADMIN_ADD_ADMIN_DEFAULT_TXT.getString());
		_addAdminButton.addStyleName("fontAR");
		_addAdminButton.addClickHandler(new ClickHandler() {
			// prepararsi alla porcata mondiale
			public void onClick(ClickEvent event) {
				if(	_addAdminTextBox.getText() != "" 
					&& !_addAdminTextBox.getText().contains(" ") 
					&& !(_addAdminTextBox.getText() == Messages.ADMIN_ADD_ADMIN_DEFAULT_TXT.getString()))
				{
					((AdminServiceAsync) getService(Service.ADMIN_SERVICE)).addAdmin(_addAdminTextBox.getText(), _addAdminCallback);
					_optionsPopupPanel.hide();
				}
				else
					_addAdminMsgLabel.setText("Please insert a valid email");
			}
		});

		// setup order Killswitch stuff
		_orderKillSwitchLbl.setStylePrimaryName("label");
		// the async handler of this will set correct text on the button
		((OrderServiceAsync) getService(Service.ORDER_SERVICE)).getOrderKillswitch(_getOrderKillswitchCallback);
		// click handler will call service to set killswitch 
		_orderKillswitchButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean setToOn;

				// call set orderKillswitch on order service
				if(_orderKillswitchButton.getText().equals(ON))
				{
					// if the button says OFF-->ON it means it's off
					// so we need to set it to on
					setToOn = true;
				}
				else
				{
					setToOn = false;
				}

				// the async handler of this will then set correct text on the button
				((OrderServiceAsync) getService(Service.ORDER_SERVICE)).setOrderKillswitch(setToOn, _setOrderKillswitchCallback);
			}
		});



		// setup order Killswitch stuff
		Label sandBoxLabel=new Label("Sandbox");
		sandBoxLabel.setStylePrimaryName("label");
		// the async handler of this will set correct text on the button
		((UtilityServiceAsync) getService(Service.UTILITY_SERVICE)).isSandBox(new AsyncCallback<Boolean>() {

			@Override
			public void onSuccess(Boolean result) {
				// TODO Auto-generated method stub
				updateSandBoxButton(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub

			}
		});

		// click handler will call service to set killswitch 
		_sandBoxButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				boolean setToOn;

				// call set orderKillswitch on order service
				if(_sandBoxButton.getText().equals(ON))
				{
					// if the button says OFF-->ON it means it's off
					// so we need to set it to on
					setToOn = true;
				}
				else
				{
					setToOn = false;
				}

				// the async handler of this will then set correct text on the button
				((UtilityServiceAsync) getService(Service.UTILITY_SERVICE)).setSandBox(setToOn, new AsyncCallback<Boolean>() {

					@Override
					public void onSuccess(Boolean result) {
						boolean textIsOn;

						if(_sandBoxButton.getText().equals(ON))
						{
							textIsOn = true;
						}
						else
						{
							textIsOn = false;
						}

						updateSandBoxButton(textIsOn);

					}

					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub

					}
				});
			}
		});



		_configPanel.setSpacing(10);
		_configPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		_configPanel.add(addAdminLbl);
		_configPanel.add(_addAdminTextBox);
		_configPanel.add(_addAdminButton);
		_configPanel.add(_orderKillSwitchLbl);
		_configPanel.add(_orderKillswitchButton);
		_configPanel.add(sandBoxLabel);
		_configPanel.add(_sandBoxButton);

	}

	/**
	 * Set the filters
	 */
	private void setupFiltersPanel() 
	{
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

	/**
	 * 
	 */
	private void setUserFilter() 
	{
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

	/**
	 * 
	 */
	private void setDateRangeFilter() 
	{
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
	private AutoResizeTextArea getUsernameWidget(String username)
	{
		if(username.contains("@"))
			username= username.substring(0, username.indexOf('@'));

		final AutoResizeTextArea name=new AutoResizeTextArea();

		name.setText(username);
		name.setReadOnly(true);
		name.setWidth("100px");
		name.setHeight("40px");
		name.setStyleName("adminUsernameBox");

		name.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				name.selectAll();
			}
		});

		return name;
	}

	/**
	 * Set the paginator
	 */
	private void setPaginator() 
	{
		_paginationBehavior = new DefaultPaginationBehavior(_pagingControlsTable,_ordersFlexTable,15) {

			@Override
			protected void onUpdateSuccess(Object result) {
				super.onUpdateSuccess(result);
				applyCufon();
			}

			@Override
			protected RowRenderer getRowRenderer() {
				return new RowRenderer(){

					public void populateRow(PaginationBehavior pagination, int row,
							Object object) {
						OrderDTO order=(OrderDTO)object;

						//The header row will be added afterwards (apparently, it's 2am we might be wrong)
						final int frow = row +1 ;
						_ordersFlexTable.getCellFormatter().setAlignment(row, Columns.ID.ordinal(),HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
						_ordersFlexTable.setWidget(row, Columns.ID.ordinal(), getProgressiveLabeL(String.valueOf(order.getProgressive()), order.getId()));
						_ordersFlexTable.setWidget(row, Columns.USER.ordinal(), getUsernameWidget(order.getCustomerEmail()));
						_ordersFlexTable.setWidget(row,Columns.LOGO_TEXT.ordinal(), getNameWidget( order.getText()));
						List<String> list=Arrays.asList(order.getDescriptions());
						_ordersFlexTable.setWidget(row,Columns.DESC.ordinal(),  getDescriptionsWidget(list, order.getText()));
						_ordersFlexTable.setWidget(row,Columns.COLOUR.ordinal(),  getColourPanel(order.getColour()));
						DateTimeFormat fmt = DateTimeFormat.getFormat("dd.MM.yy");
						_ordersFlexTable.setText(row,Columns.DATE.ordinal(),  fmt.format(order.getDate()));
						_ordersFlexTable.getCellFormatter().setAlignment(row, Columns.STATUS.ordinal(),HasHorizontalAlignment.ALIGN_CENTER, HasVerticalAlignment.ALIGN_MIDDLE);
						_ordersFlexTable.setWidget(row, Columns.STATUS.ordinal(), getStatusImage(order.getStatus().toString(),order.getId()));

						if((order.getStatus() != Status.REJECTED) && (order.getStatus() != Status.BOUGHT))
						{
							_ordersFlexTable.setWidget(row, Columns.TIMER.ordinal(), getTimerLabel(order));
						}
						if(order.getStatus()==Status.INCOMING)
						{
							_ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), getActionPanel(frow, order.getId()));
						}
						else if (order.getStatus()!=Status.REJECTED)
						{
							_ordersFlexTable.setWidget(row, Columns.ACTIONS.ordinal(), getUploadLogoButton(order.getId(), order.getText(), order.getDescriptions()));
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

						((OrderServiceAsync) getService(Service.ORDER_SERVICE)).getOrdersByUserAndStatus(
								status, 
								userEmail,
								startDate,
								endDate,
								parameters.getOffset(), 
								parameters.getMaxResults(),
								true,
								updateTableCallback);
					}};
			}

			@Override
			protected Column[] getColumns() {

				return new Column[] { new Column(Columns.ID.getColumnText()),
						new Column(Columns.USER.getColumnText()),
						new Column(Columns.LOGO_TEXT.getColumnText()),
						new Column(Columns.DESC.getColumnText()),
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

		HorizontalPanel titlePanel=new HorizontalPanel();
		titlePanel.setSpacing(10);
		titlePanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		titlePanel.setHorizontalAlignment(HorizontalPanel.ALIGN_CENTER);
		_uploadLogoName.setStyleName("adminLogoName");
		_uploadTags.setStyleName("adminTags");
		titlePanel.add(_uploadLogoName);
		titlePanel.add(_uploadTags);

		HorizontalPanel rasterizedPanel=new HorizontalPanel();
		rasterizedPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		rasterizedPanel.setSpacing(10);
		Label rasterizedLbl=new Label(Messages.RASTERIZED_LBL.getString());
		final FileUpload rasterizedUpload=new FileUpload();
		rasterizedUpload.setName("uploadFormElement");
		Button rasterizedUploadBtn=new Button(Messages.UPLOAD.getString());
		_isRasterizedImageUploaded.addStyleName("labelButton");
		_isRasterizedImageUploaded.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(_isRasterizedImageUploaded.getUrl().endsWith(Images.OK.getImageURL().substring(1)))
				{
					slideShow.showSingleImage("/wokhei/getfile?fileType="+FileType.PNG_LOGO.toString()+"&orderid="+_uploadPanelOrderId, Messages.COPYRIGHT.getString());
				}

			}});
		rasterizedPanel.add(_isRasterizedImageUploaded);
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
				updateUploadPanel(_uploadPanelOrderId, null, null, UploadType.RASTERIZED_PNG);

			}
		});


		rasterizedUploadBtn.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{
				// check if ends with png (and not null at the same time)
				if(rasterizedUpload.getFilename().endsWith(".png"))
				{
					_rasterizedForm.submit();
				}
				else
				{
					Window.alert("Cannot upload - pick a valid .png file!");
				}
			}
		});


		_presentationForm.setEncoding(FormPanel.ENCODING_MULTIPART);
		_presentationForm.setMethod(FormPanel.METHOD_POST);

		HorizontalPanel presentationPanel=new HorizontalPanel();
		presentationPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		presentationPanel.setSpacing(10);
		Label presentationLbl=new Label(Messages.PRESENTATION_LBL.getString());
		final FileUpload presentationUpload=new FileUpload();
		presentationUpload.setName("uploadFormElement");
		Button presentationUploadBtn=new Button(Messages.UPLOAD.getString());
		_isPresentationImageUploaded.addStyleName("labelButton");
		_isPresentationImageUploaded.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(_isPresentationImageUploaded.getUrl().endsWith(Images.OK.getImageURL().substring(1)))
				{
					slideShow.showSingleImage("/wokhei/getfile?fileType="+FileType.PNG_LOGO_PRESENTATION.toString()+"&orderid="+_uploadPanelOrderId, Messages.COPYRIGHT.getString());
				}

			}});
		presentationPanel.add(_isPresentationImageUploaded);
		presentationPanel.add(presentationLbl);
		presentationPanel.add(presentationUpload);
		presentationPanel.add(presentationUploadBtn);


		_presentationForm.addSubmitHandler(new SubmitHandler(){

			public void onSubmit(SubmitEvent event) {
				//VALIDATION

			}
		});

		_presentationForm.addSubmitCompleteHandler(new SubmitCompleteHandler() {

			public void onSubmitComplete(SubmitCompleteEvent event)
			{
				//UPDATE
				updateUploadPanel(_uploadPanelOrderId, null, null, UploadType.PRESENTATION_PNG);
			}
		});


		presentationUploadBtn.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{
				// check if ends with png (and not null at the same time)
				if(presentationUpload.getFilename().endsWith(".png"))
				{
					_presentationForm.submit();
				}
				else
				{
					Window.alert("Cannot upload - pick a valid .png file!");
				}
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
				updateUploadPanel(_uploadPanelOrderId, null, null, UploadType.VECTORIAL_PDF);
			}
		});

		HorizontalPanel vectorialPanel=new HorizontalPanel();
		vectorialPanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
		vectorialPanel.setSpacing(10);
		Label vectorialLbl=new Label(Messages.VECTORIAL_LBL.getString());
		final FileUpload vectorialUpload=new FileUpload();
		vectorialUpload.setName("uploadFormElement");
		Button vectorialUploadBtn=new Button(Messages.UPLOAD.getString());

		vectorialUploadBtn.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) 
			{
				// check if ends with png (and not null at the same time)
				if(vectorialUpload.getFilename().endsWith(".pdf"))
				{
					_vectorialForm.submit();
				}
				else
				{
					Window.alert("Cannot upload - pick a valid .pdf file!");
				}
			}
		});

		_isVectorialImageUploaded.addStyleName("labelButton");
		_isVectorialImageUploaded.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				if(_isVectorialImageUploaded.getUrl().endsWith(Images.OK.getImageURL().substring(1)))
				{
					Window.open("/wokhei/getfile?fileType="+FileType.PDF_VECTORIAL_LOGO.toString()+"&orderid="+_uploadPanelOrderId, "_blank", "");
				}

			}});
		vectorialPanel.add(_isVectorialImageUploaded);
		vectorialPanel.add(vectorialLbl);
		vectorialPanel.add(vectorialUpload);
		vectorialPanel.add(vectorialUploadBtn);

		_rasterizedForm.setWidget(rasterizedPanel);
		_vectorialForm.setWidget(vectorialPanel);
		_presentationForm.setWidget(presentationPanel);

		uploadPanel.add(titlePanel);
		uploadPanel.add(_rasterizedForm);
		uploadPanel.add(_presentationForm);
		uploadPanel.add(_vectorialForm);
		_uploadPopupPanel.add(uploadPanel);
	}

	/**
	 * @param orderId 
	 * @param Tags 
	 * @param logoName 
	 * 
	 */
	private void updateUploadPanel(long orderId, String logoName, String tags, UploadType upload) 
	{
		final UploadType uploadType = upload;

		_uploadPanelOrderId=orderId;
		_rasterizedForm.setAction("/wokhei/uploadfile?fileType="+FileType.PNG_LOGO.toString()+"&orderid="+orderId);
		_vectorialForm.setAction("/wokhei/uploadfile?fileType="+FileType.PDF_VECTORIAL_LOGO.toString()+"&orderid="+orderId);
		_presentationForm.setAction("/wokhei/uploadfile?fileType="+FileType.PNG_LOGO_PRESENTATION.toString()+"&orderid="+orderId);

		if(logoName!=null)
			_uploadLogoName.setText(logoName);
		if(tags!=null)
			_uploadTags.setText(tags);

		((OrderServiceAsync) getService(Service.ORDER_SERVICE)).hasFileUploaded(orderId, FileType.PNG_LOGO, new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				_isRasterizedImageUploaded.setUrl(Images.NOK.getImageURL());
			}

			public void onSuccess(Boolean result) {
				_isRasterizedImageUploaded.setUrl(getImageUrl(result));
				if(uploadType == UploadType.RASTERIZED_PNG)
				{
					if (result)
						Window.alert("Upload Successful!");
					else
						Window.alert("Upload Error!");
				}
			}});

		((OrderServiceAsync) getService(Service.ORDER_SERVICE)).hasFileUploaded(orderId, FileType.PDF_VECTORIAL_LOGO, new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				_isVectorialImageUploaded.setUrl(Images.NOK.getImageURL());
			}

			public void onSuccess(Boolean result) {
				_isVectorialImageUploaded.setUrl(getImageUrl(result));
				if(uploadType == UploadType.VECTORIAL_PDF)
				{
					if (result)
						Window.alert("Upload Successful!");
					else
						Window.alert("Upload Error!");
				}
			}});

		((OrderServiceAsync) getService(Service.ORDER_SERVICE)).hasFileUploaded(orderId, FileType.PNG_LOGO_PRESENTATION, new AsyncCallback<Boolean>(){

			public void onFailure(Throwable caught) {
				_isPresentationImageUploaded.setUrl(Images.NOK.getImageURL());
			}

			public void onSuccess(Boolean result) {
				_isPresentationImageUploaded.setUrl(getImageUrl(result));
				if(uploadType == UploadType.PRESENTATION_PNG)
				{
					if (result)
						Window.alert("Upload Successful!");
					else
						Window.alert("Upload Error!");
				}
			}

		});
	}

	/**
	 * @param result
	 * @return
	 */
	private Label getTimerLabel(OrderDTO order) 
	{
		Float missingTime = 0f;

		Label timerLabel=new Label();

		switch(order.getStatus())
		{
		case ACCEPTED:
		case BOUGHT:
		case IN_PROGRESS:
		case QUALITY_GATE:
			if(order.getAcceptedDate() != null)
			{
				float diffHours = DateDifferenceCalculator.getDifferenceInHours(_serverTimeStamp,order.getAcceptedDate());
				missingTime = (24f+diffHours);
				missingTime=Float.valueOf((int)((missingTime*-1+0.005f)*10.0f)/10.0f);
			}
			timerLabel.setText(missingTime+ " hrs");
			if(missingTime<-6f)
			{
				timerLabel.addStyleName("timerGreen");
			}
			else if(missingTime<-2f)
			{
				timerLabel.addStyleName("timerOrange");
			}
			else 
			{
				timerLabel.addStyleName("timerRed");
			}
			break;
		case REJECTED:
			break;
		case INCOMING:
			if(order.getDate() != null)
			{
				float diffHours = DateDifferenceCalculator.getDifferenceInHours(_serverTimeStamp,order.getDate());
				missingTime = diffHours;
				missingTime=Float.valueOf((int)((missingTime*-1+0.005f)*10.0f)/10.0f);
			}
			timerLabel.setText(missingTime+ " hrs");
			if(missingTime<2f)
			{
				timerLabel.addStyleName("timerGreen");
			}
			else if(missingTime<6f)
			{
				timerLabel.addStyleName("timerOrange");
			}
			else
			{
				timerLabel.addStyleName("timerRed");
			}
			break;
		case READY:
			if(order.getAcceptedDate() != null)
			{
				float diffHours = DateDifferenceCalculator.getDifferenceInHours(_serverTimeStamp,order.getAcceptedDate());
				missingTime = (24f+diffHours);
				missingTime=Float.valueOf((int)((missingTime*-1+0.005f)*10.0f)/10.0f);
				timerLabel.addStyleName("timerGrey");
				timerLabel.setText(missingTime+ " hrs");
			}
			break;
		case REVIEWING:
			if(order.getReviewingDate() != null)
			{
				float diffHours = DateDifferenceCalculator.getDifferenceInHours(_serverTimeStamp,order.getReviewingDate());
				missingTime = (24f+diffHours);
				missingTime=Float.valueOf((int)((missingTime*-1+0.005f)*10.0f)/10.0f);
			}
			timerLabel.setText(missingTime+ " hrs");
			if(missingTime<-6f)
			{
				timerLabel.addStyleName("timerGreen");
			}
			else if(missingTime<-2f)
			{
				timerLabel.addStyleName("timerOrange");
			}
			else 
			{
				timerLabel.addStyleName("timerRed");
			}
			break;
		}


		return timerLabel;
	}

	/**
	 * @param result
	 * @return
	 */
	private String getImageUrl(Boolean result) 
	{
		if(result)
			return Images.OK.getImageURL();
		else
			return Images.NOK.getImageURL();
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
	 * @param colour
	 * @return
	 */
	private Widget getColourPanel(Colour colour) 
	{
		HorizontalPanel cPanel=new HorizontalPanel();
		Label pantone=new Label(colour.toString().replace("PANTONE",""));
		Label colourLbl=new Label();
		colourLbl.setStyleName("colour"+colour.toString());
		colourLbl.setHeight("20px");
		colourLbl.setWidth("20px");

		if(colour.equals(Colour.SurpriseMe))
		{
			colourLbl.setText("?");
			colourLbl.addStyleName("fontAR");
			colourLbl.addStyleName("colourSurpriseMeHomeAdmin");
		}

		cPanel.add(colourLbl);
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
		final Image statusImage=new Image(Images.valueOf(substring).getSmallImageURL());
		switch(status)
		{
		case ACCEPTED:
		case BOUGHT:
		case IN_PROGRESS:
		case QUALITY_GATE:
		case READY:
		case VIEWED:
		case REVIEWING:
			statusImage.addStyleName("labelButton");
			break;
		}
		statusImage.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				switch(status)
				{
				case ACCEPTED:
				case BOUGHT:
				case IN_PROGRESS:
				case QUALITY_GATE:
				case READY:
				case VIEWED:
				case REVIEWING:
					((OrderServiceAsync) getService(Service.ORDER_SERVICE)).hasFileUploaded(orderId, FileType.PNG_LOGO, new AsyncCallback<Boolean>(){
						public void onFailure(Throwable caught) 
						{
						}

						public void onSuccess(Boolean result) {
							if(result)
							{
								slideShow.showSingleImage("/wokhei/getfile?fileType="+FileType.PNG_LOGO.toString()+"&orderid="+orderId, Messages.COPYRIGHT.getString());
							}
						}});

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
	private Widget getDescriptionsWidget(List<String> descList, String logoText) 
	{
		Label logoTextLbl = new Label(logoText);
		logoTextLbl.setStyleName("adminLogoName");

		//create a button that when clicked shows a popup panel with the descriptions
		//each description after the first one is a revision
		final Button btn = new Button("Show");

		final PopupPanel popup = new PopupPanel(true);
		popup.setStyleName("adminPopup");

		VerticalPanel descriptionsHolder = new VerticalPanel();
		descriptionsHolder.add(logoTextLbl);

		int i = 0;
		//fill pop-up panel with the descriptions
		for (String elem : descList)
		{
			Label titleLbl = new Label();
			titleLbl.addStyleName("h3");
			titleLbl.addStyleName("fontAR");

			if (i>0)
			{
				titleLbl.setText("Revision " + i);
			}
			else
			{
				titleLbl.setText("Original Description");
			}

			final TextArea desc = new TextArea();
			desc.setReadOnly(true);
			desc.setWidth("250px");
			desc.setHeight("120px");
			desc.setStyleName("adminCopyBox");
			desc.setText(elem.trim());

			desc.addClickHandler(new ClickHandler() {
				public void onClick(ClickEvent event) {
					desc.selectAll();
				}
			});

			// add to vertical panel
			descriptionsHolder.add(titleLbl);
			descriptionsHolder.add(desc);

			i++;
		}

		popup.add(descriptionsHolder);

		btn.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				popup.showRelativeTo(btn);
				popup.show();
			}
		});

		return btn;
	}

	private Widget getUploadLogoButton(final Long id, final String text,
			final String[] tags) {
		// if is not incoming and it's not ready or rejected - arguably it's always possible to upload
		Button uploadLogoButton = new Button("Upload");

		uploadLogoButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//get ID from clicked row
				updateUploadPanel(id,text,Arrays.asList(tags).toString(),UploadType.NONE);
				_uploadPopupPanel.center();
				_uploadPopupPanel.show();
			}


		});
		return uploadLogoButton;
	}

	private Widget getProgressiveLabeL(final String progValue, final Long id) {
		Label nameLbl=new Label(progValue);
		nameLbl.setStyleName("adminLogoProg");
		nameLbl.addStyleName("fontAR");
		nameLbl.addStyleName("labelButton");
		nameLbl.addClickHandler(new ClickHandler(){

			public void onClick(ClickEvent event) {
				Window.alert("The progressive for this order is: "+progValue+" the real ID is: "+id);

			}});
		return nameLbl;
	}

	private Widget getActionPanel(final Integer frow,final Long id) {
		// Add a button to remove this stock from the table.
		Button rejectOrderButton = new Button("Reject");

		rejectOrderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if(Window.confirm(Messages.ADMIN_CONFIRM_REJECT_TXT.getString()))
				{
					statusChangedSubHandler(frow, id, Status.REJECTED);
				}
			}
		});

		// Add a button to remove this stock from the table.
		Button acceptOrderButton = new Button("Accept");

		acceptOrderButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if(Window.confirm(Messages.ADMIN_CONFIRM_ACCEPT_TXT.getString()))
				{
					statusChangedSubHandler(frow, id, Status.ACCEPTED);
				}
			}
		});

		VerticalPanel actionPanel = new VerticalPanel();
		actionPanel.add(acceptOrderButton);
		actionPanel.add(rejectOrderButton);
		return actionPanel;
	}

	/**
	 * @param text
	 * @return
	 */
	private Widget getNameWidget(String text) 
	{
		final AutoResizeTextArea name=new AutoResizeTextArea();

		name.setText(text);
		name.setReadOnly(true);
		name.setStyleName("adminNameBox");
		name.setWidth("150px");
		name.setHeight("40px");

		name.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				name.selectAll();
			}
		});

		return name;
	}

	/**
	 * 
	 */
	private void hookUpCallbacks() 
	{
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

		_getServerTimestampCallback = new AsyncCallback<Date>() {
			public void onSuccess(Date result) {
				//set server time stamp
				_serverTimeStamp = result;

				initModulePartCore();

			}

			public void onFailure(Throwable caught) {
				//TODO - do something in case of failure
				//need to load the rest of the page anyway
				initModulePartCore();
			}
		};

		_getOrderKillswitchCallback = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				//update killswitch button
				updateKillswitchButton(result);
			}

			public void onFailure(Throwable caught) {
				//TODO - do something in case of failure
			}
		};

		_setOrderKillswitchCallback = new AsyncCallback<Boolean>() {

			public void onSuccess(Boolean result) {
				// in this case the result is indicating if the operation was successful
				// if operation was successful --> update killswitch button
				if(result)
				{
					boolean textIsOn;

					if(_orderKillswitchButton.getText().equals(ON))
					{
						textIsOn = true;
					}
					else
					{
						textIsOn = false;
					}

					updateKillswitchButton(textIsOn);
				}
			}

			public void onFailure(Throwable caught) {
				//TODO - do something in case of failure
			}
		};
	}

	protected void updateKillswitchButton(Boolean result) {
		if(result)
		{
			_orderKillswitchButton.setText(OFF);
		}
		else
		{
			_orderKillswitchButton.setText(ON);
		}

	}

	protected void updateSandBoxButton(Boolean result) {
		if(result)
		{
			_sandBoxButton.setText(OFF);
		}
		else
		{
			_sandBoxButton.setText(ON);
		}

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
			//TODO --> va cabiata al classe css perche' non si capisce piu che se sono disabilitati
			((Button)actionPanel.getWidget(0)).setVisible(false);
			((Button)actionPanel.getWidget(1)).setVisible(false);
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
		((OrderServiceAsync) getService(Service.ORDER_SERVICE)).setOrderStatus(orderId, status, _setOrderStatusCallback);
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart(OrderDTO selection) 
	{
		try {
			throw new Exception("Metti che il VP ci implementa tutto e usa questo metodo ...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
