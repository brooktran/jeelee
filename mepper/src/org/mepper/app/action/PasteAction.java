package org.mepper.app.action;

import java.awt.event.ActionEvent;

import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;


/**
 * <B>PasteAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-5 created
 * @since map editor Ver 1.0
 * 
 */
public class PasteAction  extends AppAction{
	public static final long serialVersionUID = 1L;
	public static final String ID="paste";

	/**
	 * 
	 * @since map editor
	 * @param app
	 */
	public PasteAction(Application app) {
		super(app,ID);
	}

	/** 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public String getID() {
//		return ID;
//	}
	
}
