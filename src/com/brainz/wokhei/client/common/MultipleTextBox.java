/**
 * 
 */
package com.brainz.wokhei.client.common;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.InputElement;
import com.google.gwt.user.client.ui.TextBoxBase;
import com.google.gwt.user.client.ui.impl.TextBoxImpl;

/**
 * @author matteocantarelli
 *
 */
public class MultipleTextBox extends TextBoxBase {
	private static final String SEPARATOR = " ";
	private static TextBoxImpl impl = GWT.create(TextBoxImpl.class);

	/**
	 * Creates an empty multiple text box.
	 */
	public MultipleTextBox() {
		this(Document.get().createTextInputElement(), "gwt-TextBox");
	}

	/**
	 * This constructor may be used by subclasses to explicitly use an existing
	 * element. This element must be an <input> element whose type is
	 * 'text'.
	 *
	 * @param element
	 * the element to be used
	 */
	protected MultipleTextBox(Element element) {
		super(element);
		assert InputElement.as(element).getType().equalsIgnoreCase("text");
	}

	MultipleTextBox(Element element, String styleName) {
		super(element);
		if (styleName != null) {
			setStyleName(styleName);
		}
	}

	@Override
	public String getText() {
		String wholeString = super.getText();
		String lastString = wholeString;
		if (wholeString != null && !wholeString.trim().equals("")) {
			int lastComma = wholeString.lastIndexOf(SEPARATOR);
			if (lastComma > 0) {
				lastString = wholeString.substring(lastComma + 1);
			}
		}
		return lastString;
	}

	@Override
	public void setText(String text) {
		String wholeString = super.getText();
		if (text != null && text.equals("")) {
			super.setText(text);
		} else {
			// Clean last text, to replace with new value, for example, if new
			// text is v.zaprudnevd@gmail.com:
			// "manuel@we-r-you.com, v" need to be replaced with:
			// "manuel@we-r-you.com, v.zaprudnevd@gmail.com, "

			if (wholeString != null) {
				int lastComma = wholeString.trim().lastIndexOf(SEPARATOR);
				if (lastComma > 0) {
					wholeString = wholeString.trim().substring(0, lastComma);
				} else {
					wholeString = "";
				}

				if (!wholeString.trim().endsWith(SEPARATOR)
						&& !wholeString.trim().equals("")) {
					wholeString = wholeString + SEPARATOR;
				}

				wholeString = wholeString + text ;//+ ", ";
				super.setText(wholeString);
			}
		}
	}

	/**
	 * @param text
	 */
	public void setWholeText(String text)
	{
		super.setText(text);
	}

	/**
	 * @return
	 */
	public String getWholeText() 
	{
		return super.getText();
	}

	/**
	 * Sets the range of text to be selected.
	 * 
	 * This will only work when the widget is attached to the document and not
	 * hidden.
	 * 
	 * @param pos the position of the first character to be selected
	 * @param length the number of characters to be selected
	 */
	@Override
	public void setSelectionRange(int pos, int length) {
		// Setting the selection range will not work for unattached elements.
		if (!isAttached()) {
			return;
		}

		if (length < 0) {
			throw new IndexOutOfBoundsException(
					"Length must be a positive integer. Length: " + length);
		}
		if ((pos < 0) || (length + pos > getWholeText().length())) {
			throw new IndexOutOfBoundsException("From Index: " + pos + "  To Index: "
					+ (pos + length) + "  Text Length: " + getWholeText().length());
		}
		impl.setSelectionRange(getElement(), pos, length);
	}

	/**
	 * Selects all of the text in the box.
	 * 
	 * This will only work when the widget is attached to the document and not
	 * hidden.
	 */
	@Override
	public void selectAll() {
		int length = getWholeText().length();
		if (length > 0) {
			setSelectionRange(0, length);
		}
	}
}