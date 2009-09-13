package com.brainz.wokhei.client.admin;


import com.google.gwt.core.client.JavaScriptObject;

public class JSStringArray {
	@SuppressWarnings("unused")
	private final JavaScriptObject a;

	public JSStringArray(JavaScriptObject a) {
		this.a = a;
	}

	public native String get(int index) /*-{
		return (this.@com.brainz.wokhei.client.admin.JSStringArray::a)[index];
	}-*/;

	public native String set(String s, int index) /*-{
		(this.@com.brainz.wokhei.client.admin.JSStringArray::a)[index] = s;
	}-*/;

	public native int length() /*-{
		return (this.@com.brainz.wokhei.client.admin.JSStringArray::a).length;
	}-*/;

	public static JSStringArray extract(String s, String regexStr) {
		return new JSStringArray(_extract(s, regexStr));
	}

	private static native JavaScriptObject _extract(String s, String regexStr) /*-{
		var regex = new RegExp(regexStr, "g");
		var result, lastIndex = 0, s2 = '', arr = new Array(), count = 0;
		while ((result = regex.exec(s)) != null) {
			s2 += s.substring(lastIndex, regex.lastIndex - result[0].length);
			lastIndex = regex.lastIndex;
			arr[++count] = result[1];
		}
		s2 += s.substring(lastIndex);
		arr[0] = s2;
		return arr;
	}-*/;

	public static JSStringArray split(String s, String regex) {
		return new JSStringArray(_split(s, regex));
	}

	private static native JavaScriptObject _split(String s, String regex) /*-{
		return s.split(new RegExp(regex));
	}-*/;

	public static JSStringArray split(String s, String regex, int limit) {
		return new JSStringArray(_split(s, regex, limit));
	}

	private static native JavaScriptObject _split(String s, String regex,
			int limit) /*-{
		return s.split(new RegExp(regex), limit);
	}-*/;

	static {
		fixSplit();
	}

	private static native void fixSplit() /*-{
		//
		//	Cross-Browser Split 0.2.1
		//	By Steven Levithan <http://stevenlevithan.com>
		//	MIT license
		//

		var nativeSplit = nativeSplit || String.prototype.split;

		String.prototype.split = function (s, limit) {
			// If separator is not a regex, use the native split method
			if (!(s instanceof RegExp))
				return nativeSplit.apply(this, arguments);

			//	Behavior for limit: If it's...
			//	 - Undefined: No limit
			//	 - NaN or zero: Return an empty array
			//	 - A positive number: Use limit after dropping any decimal
			//	 - A negative number: No limit
			//	 - Other: Type-convert, then use the above rules
			if (limit === undefined || +limit < 0) {
				limit = false;
			} else {
				limit = Math.floor(+limit);
				if (!limit)
					return [];
			}

			var	flags = (s.global ? "g" : "") + (s.ignoreCase ? "i" : "") + (s.multiline ? "m" : ""),
				s2 = new RegExp("^" + s.source + "$", flags),
				output = [],
				lastLastIndex = 0,
				i = 0,
				match;

			if (!s.global)
				s = new RegExp(s.source, "g" + flags);

			while ((!limit || i++ <= limit) && (match = s.exec(this))) {
				var zeroLengthMatch = !match[0].length;

				// Fix IE's infinite-loop-resistant but incorrect lastIndex
				if (zeroLengthMatch && s.lastIndex > match.index)
					s.lastIndex = match.index; // The same as s.lastIndex--

				if (s.lastIndex > lastLastIndex) {
					// Fix browsers whose exec methods don't consistently return undefined for non-participating capturing groups
					if (match.length > 1) {
						match[0].replace(s2, function () {
							for (var j = 1; j < arguments.length - 2; j++) {
								if (arguments[j] === undefined)
									match[j] = undefined;
							}
						});
					}

					output = output.concat(this.slice(lastLastIndex, match.index), (match.index === this.length ? [] : match.slice(1)));
					lastLastIndex = s.lastIndex;
				}

				if (zeroLengthMatch)
					s.lastIndex++;
			}

			return (lastLastIndex === this.length) ?
				(s.test("") ? output : output.concat("")) :
				(limit      ? output : output.concat(this.slice(lastLastIndex)));
		};
	}-*/;

	public String join() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length(); i++) {
			sb.append(get(i));
		}
		return sb.toString();
	}
}