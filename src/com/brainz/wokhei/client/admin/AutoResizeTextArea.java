package com.brainz.wokhei.client.admin;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;

public class AutoResizeTextArea extends TextArea {

	private static Label measurer;

	static {
		measurer = new Label();
		measurer.setStyleName("measurer");
		RootPanel.get().add(measurer, -1000, -1000);
	}

	private int minWidth = 0;
	private int maxWidth = Integer.MAX_VALUE;
	private int minHeight = 0;
	private int maxHeight = Integer.MAX_VALUE;
	private boolean resizeSupressed = false;

	public AutoResizeTextArea() {
		sinkEvents(Event.ONKEYDOWN);
	}

	@Override
	public void onBrowserEvent(Event event) {
		super.onBrowserEvent(event);
		if (DOM.eventGetType(event) == Event.ONKEYDOWN) {
			resize(DOM.eventGetKeyCode(event) == 13
					&& !DOM.eventGetCtrlKey(event) ? 1 : 0);
		}
	}

	@Override
	public void setText(String text) {
		super.setText(text);
		resize();
	}

	private void resize() {
		resize(0);
	}

	private void resize(int lineAdjust) {
		if (resizeSupressed) {
			return;
		}
		JSStringArray lines = JSStringArray.split(getText(), "\r?\n");

		int width = 0;
		for (int i = 0; i < lines.length(); i++) {
			measurer.setText(lines.get(i));
			int lineWidth = measurer.getOffsetWidth() + 30;
			if (width < lineWidth) {
				width = lineWidth;
			}
		}
		if (width < minWidth) {
			width = minWidth;
		}
		if (width > maxWidth) {
			width = maxWidth;
		}
		setWidth(width + "px");

		setVisibleLines(lines.length() + lineAdjust);
		while (getOffsetHeight() > maxHeight) {
			setVisibleLines(getVisibleLines() - 1);
		}
		while (getOffsetHeight() < minHeight) {
			setVisibleLines(getVisibleLines() + 1);
		}
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
		resize();
	}

	public int getMaxWidth() {
		return maxWidth;
	}

	public void setMaxWidth(int maxWidth) {
		this.maxWidth = maxWidth;
		resize();
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
		resize();
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public void setMaxHeight(int maxHeight) {
		this.maxHeight = maxHeight;
		resize();
	}

	public boolean isResizeSupressed() {
		return resizeSupressed;
	}

	public void setResizeSupressed(boolean resizeSupressed) {
		this.resizeSupressed = resizeSupressed;
	}

}