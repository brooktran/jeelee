/* ViewSwitchAction.java 1.0 2010-2-2
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

import java.awt.event.ActionEvent;

import org.zhiwu.app.Application;

/**
 * <B>ViewSwitchAction</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-11-11 created
 * @since  org.zhiwu.app Ver 1.0
 */
public class ViewSwitchAction extends AppAction {
	/**
	 * @uml.property  name="iD"
	 */
	public  String ID ;
	private final String viewPath;
	
	
	/**
	 * @param app
	 */
	public ViewSwitchAction(Application app,String viewPath) {
		super(app,viewPath+".title");
		ID= viewPath+".title";
		this.viewPath=viewPath;
	}
	

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		app.setView(viewPath);
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.action.AppAction#getID()
	 */
	/**
	 * @return
	 * @uml.property  name="iD"
	 */
//	@Override
//	public String getID() {
//		return ID;
//	}
	
	

}
