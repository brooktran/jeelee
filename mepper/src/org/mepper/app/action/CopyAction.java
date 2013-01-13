package org.mepper.app.action;

import java.awt.event.ActionEvent;

import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;


/**
 * <B>CopyAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-5 created
 * @since map editor Ver 1.0
 * 
 */
public class CopyAction  extends AppAction{
	public static final String ID="copy";

	public CopyAction(Application app) {
		super(app,ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

	}
	
//	@Override
//	public String getID() {
//		return ID;
//	}

}
