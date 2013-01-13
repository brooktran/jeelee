package org.zhiwu.app.action;

import java.awt.event.ActionEvent;

import org.zhiwu.app.AppDialog;
import org.zhiwu.app.AppDialogListener;
import org.zhiwu.app.Application;
import org.zhiwu.app.DialogEvent;

/**
 * <B>AboutAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-29 created
 * @since Application Framwork Ver 1.0
 * 
 */
public class AboutAction extends AppAction {
	public static final String ID = "about";

	public AboutAction(Application app) {
		super(app,ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		final AppDialog aboutDialog = new AboutDialog(app);
		aboutDialog.showDialog(new AppDialogListener() {
			@Override
			public void optionSelection(DialogEvent evt) {
				aboutDialog.dispose();				
			}
		});
	}

//	@Override
//	public String getID() {
//		return ID;
//	}
	
}
