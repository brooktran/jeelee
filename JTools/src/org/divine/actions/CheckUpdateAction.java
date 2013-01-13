/* CheckUpdateAction.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.divine.actions;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>CheckUpdateAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-19 created
 * @since org.divine.actions Ver 1.0
 * 
 */
public class CheckUpdateAction extends AppAction {
	private static final long serialVersionUID = 1L;
	public static final String ID = "update";

	public CheckUpdateAction(Application app) {
		super(app);
		
		AppResources resource = AppResources.getResources("org.jtools.app.Labels");
		resource.configAction(this, ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Desktop desktop=Desktop.getDesktop();
		if(Desktop.isDesktopSupported()&& desktop.isSupported(Desktop.Action.BROWSE)){
			try {
				desktop.browse(new URI("http://blog.sina.com.cn/kkliuyao"));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		}
	} 
	@Override
	public String getID() {
		return ID;
	}
	
}
