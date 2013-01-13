/* AbsDialog.java 1.0 2010-2-2
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

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.event.EventListenerList;

/**
 * <B>AbsDialog</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-12 created
 * @since Application Ver 1.0
 * 
 */
public class AbsDialog extends JDialog{
	/** The Constant CLOSE_OPTION. */
	public static final String CLOSE_OPTION="CLOSE_OPTION";

	/** The Constant COMFIRM_OPTION. */
	public static final String COMFIRM_OPTION="COMFIRM_OPTION";

	/** The Constant CANCEL_OPTION. */
	public static final String CANCEL_OPTION="CANCEL_OPTION";

	/** The listener list. */
	protected EventListenerList listenerList = new EventListenerList();

	
	
	public AbsDialog() {
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent we){
				fireOptionSelected(CLOSE_OPTION);
			}
		});
	}
	public void addDialogListener(AppDialogListener l){
		listenerList.add(AppDialogListener.class, l);
	}
	
	protected void fireOptionSelected(String name) {
		fireOptionSelected(name, null);
	}
	
	protected void fireOptionSelected(String name,Object data){
		// Guaranteed to return a non-null array
		Object[] listeners=listenerList.getListenerList(); 
		DialogEvent evt = new DialogEvent(this,name,data);
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i=0,j=listeners.length-1;i<j;i+=2){
			if (listeners[i] == AppDialogListener.class) {
				((AppDialogListener)listeners[i+1]).optionSelection(evt);
			}
		}
	}
	public void showDialog(AppDialogListener l){
		addDialogListener(l);
		//TODO 设置初始位置  x=parent.width-width)/5
		Dimension d =Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(d.width/3, d.height/3);
		super.setVisible(true);
	}
}
