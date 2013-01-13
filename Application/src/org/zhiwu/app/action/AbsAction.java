/* AbsAction.java 1.0 2010-2-2
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
package org.zhiwu.app.action;

import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import org.zhiwu.app.AppManager;
import org.zhiwu.utils.SwingResources;

/**
 * <B>AbsAction</B> can get ID from the resouces of the application.
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2011-1-17 created
 * @since  org.zhiwu.app.action Ver 1.0
 */
public abstract class AbsAction extends AbstractAction{
	protected PropertyChangeListener listener;
	protected final String ID="action";
	
	public AbsAction(String ID) {
		initAction(ID);
	}
	
	protected void initAction(String ID) {
		SwingResources r = AppManager.getResources();
		r.configAction(this,ID);
	}
	public String getID() {
		return this.ID;
	}
	
}
