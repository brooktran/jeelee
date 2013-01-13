/* AppDialog.java 1.0 2010-2-2
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
package org.zhiwu.app;

import java.awt.Window;

/**
 * <B>AppDialog</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-3-28 created
 * @since  org.zhiwu.app Ver 1.0
 */
public class AppDialog extends AbsDialog {
	protected Application app;
	
	public AppDialog(Application app) {
		this.app=app;
		setLocale(getOwner().getLocale());
		setIconImage(app.getView().getIcon());
		init();
	}

	private void init() {
		Window parent=app.getWindow();
		setLocation(parent.getX()+parent.getWidth()/10, parent.getY()+parent.getHeight()/10);
	}

	public Application getApplication(){
		return app;
	}
}
