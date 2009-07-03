/**
 * 
 */
package com.brainz.wokhei.client.index;

import com.brainz.wokhei.client.common.AModulePart;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * @author matteocantarelli
 *
 */
public class IndexModulePart extends AModulePart {

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#loadModulePart()
	 */
	@Override
	public void loadModulePart() 
	{
		if(RootPanel.get("indexBodyPart")!=null)
		{
			//			AbsolutePanel mainPanel= new AbsolutePanel();
			//			SWFWidget swfWidget = new SWFWidget("./videos/Wokhei.m4v");
			//			swfWidget.setWidth("100%");
			//			swfWidget.setHeight("100%");
			//			RootPanel.get("indexBodyPart").add(swfWidget);
			//
			//			applyCufon();

		}
		//		<div class="takeTour">Take a tour!</div>
		//		<div class="signin"><a href="<%= userService.createLoginURL(request.getRequestURI()) %>"><a1>Login</a1></a></div>

		//		<div class="join">Join Wokhei now!</div>
		//		<div class="screen">
		//			<div class="logos">
		//			</div>
		//		</div>
		//
		//		<div class="hat"></div>
		//
		//		<div class="clock"></div>
		//
		//		<div class="ready"></div>
	}

	/* (non-Javadoc)
	 * @see com.brainz.wokhei.client.common.AModulePart#updateModulePart()
	 */
	@Override
	public void updateModulePart() {	

	}

}
