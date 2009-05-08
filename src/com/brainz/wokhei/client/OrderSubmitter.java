package com.brainz.wokhei.client;

import java.util.Arrays;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class OrderSubmitter implements EntryPoint {

	private static final int MAX_TAGS = 5;

	private final VerticalPanel mainPanel = new VerticalPanel();

	// logotext controls
	private final VerticalPanel logoTextPanel = new VerticalPanel();
	private final TextBox logoTextBox = new TextBox();
	private final Label logoTextLabel = new Label("Logo name");
	private final Label logoHintLabel = new Label("e.g. Franco Restaurant");

	// TODO: Add color-picker
	private final VerticalPanel colorPanel = new VerticalPanel();
	//private final TextBox logoTextBox = new TextBox();
	private final Label colorLabel = new Label("Main color");
	private final Label colorHintLabel = new Label("a hint for what main colour you would like for your logo");

	// logotags controls
	private final VerticalPanel logoTagsPanel = new VerticalPanel();
	private final TextBox logoTagsBox = new TextBox();
	private final Label logoTagsLabel = new Label("5 Tags");
	private final Label tagsHintLabel = new Label("e.g. #FoodIndustry #Restaurant #Fancy #FrenchCuisine");

	// a pretty self-explanatory submit button
	private final Button submitOrder = new Button("Send request!");
	private final Label messageLabel = new Label("");

	private SubmitOrderServiceAsync submitOrderSvc = GWT.create(SubmitOrderService.class);


	public void onModuleLoad() {

		mainPanel.setSpacing(10);

		logoTextLabel.addStyleName("label");

		logoHintLabel.setStyleName("hintLabel");

		logoTextBox.setText("the text in your logo");
		logoTextBox.setWidth("255px");
		logoTextBox.setStyleName("textBox");

		logoTextBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(logoTextBox.getText().equals("the text in your logo"))
				{
					logoTextBox.selectAll();
				}
			}});

		logoTagsLabel.addStyleName("label");
		tagsHintLabel.addStyleName("hintLabel");

		// prepare my motherfuckin' logoText vertical panel

		logoTextPanel.setSpacing(1);
		logoTextPanel.add(logoTextLabel);
		logoTextPanel.add(logoHintLabel);
		logoTextPanel.add(logoTextBox);


		// prepare my cock-fuckerin' tags vertical panel
		logoTagsBox.setWidth("255px");
		logoTagsBox.setText("describe what the logo is for!");
		logoTagsBox.setStyleName("textBox");
		logoTagsBox.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				if(logoTagsBox.getText().equals("describe what the logo is for!"))
				{
					logoTagsBox.selectAll();
				}
			}});

		logoTagsPanel.setSpacing(1);
		logoTagsPanel.add(logoTagsLabel);
		logoTagsPanel.add(tagsHintLabel);
		logoTagsPanel.add(logoTagsBox);

		colorLabel.addStyleName("label");
		colorHintLabel.addStyleName("hintLabel");

		colorPanel.setSpacing(1);
		colorPanel.add(colorLabel);
		colorPanel.add(colorHintLabel);

		messageLabel.addStyleName("errorLabel");

		// Fill up that son of a bitch of a mainPanel
		mainPanel.add(logoTextPanel);
		mainPanel.add(logoTagsPanel);
		mainPanel.add(colorPanel);
		mainPanel.add(messageLabel);
		mainPanel.add(submitOrder);

		// Associate the feckin' Main panel with the HTML element on the host page.
		RootPanel.get("orderSubmitter").add(mainPanel);

		// Move goddamned cursor focus to the logoText input box.
		logoTextBox.setFocus(true);

		// Listen for mouse events on the Add button.
		submitOrder.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				submitOrder();
			}
		});

	}

	protected void submitOrder() {

		if (this.logoTagsBox.getText().length()!=0)
		{		
			if(logoTagsBox.getText().split(" ").length>MAX_TAGS)
			{
				messageLabel.setText("Sorry, our Chefs gets confused with more than 5 tags!");
			}
			else
			{
				if (submitOrderSvc==null)
				{
					submitOrderSvc = GWT.create(SubmitOrderService.class);
				}

				// Set up the callback object.
				AsyncCallback<Boolean> callback = new AsyncCallback<Boolean>() {
					public void onFailure(Throwable caught) {
						messageLabel.setText("Fuck-up: " + caught.getMessage());
					}

					public void onSuccess(Boolean result) {
						updateMessage(result);
					}
				};

				// Make the call to the stock price service.
				submitOrderSvc.submitOrder(this.logoTextBox.getText(), Arrays.asList(logoTagsBox.getText().split(" ")), callback);			
			}
		}
		else
		{
			messageLabel.setText("You need to put at least some tags!");
		}
	}

	protected void updateMessage(Boolean result) {
		if(result)
		{
			messageLabel.setText("It's all good - Your request has been sent to the kitchen!");
		}
		else
		{
			messageLabel.setText("Error: A bit of a Fuck-up!");
		}
	}

}
