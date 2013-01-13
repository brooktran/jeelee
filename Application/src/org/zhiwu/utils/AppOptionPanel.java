/* AppOptionPanel.java 1.0 2010-2-2
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
package org.zhiwu.utils;

import java.awt.Component;

import javax.swing.JOptionPane;

import org.zhiwu.app.AppManager;


/**
 * <B>AppOptionPanel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-29 created
 * @since org.zhiwu.utils Ver 1.0
 * 
 */
public class AppOptionPanel {

	public static boolean showConfirmDeleteDialog(Component parentComponent,Object deleteObject) {
		AppResources r= AppManager.getResources();
		return  JOptionPane.showConfirmDialog(parentComponent, 
				r.getString("want.to.delete")+": "+deleteObject, r.getString("delete"), 
				JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
		
	}
	

}
