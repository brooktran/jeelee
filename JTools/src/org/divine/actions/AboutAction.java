package org.divine.actions;

import java.awt.event.ActionEvent;

import org.divine.gui.AboutView;
import org.zhiwu.app.AppDialog;
import org.zhiwu.app.DialogEvent;
import org.zhiwu.app.Application;
import org.zhiwu.app.AppDialogListener;
import org.zhiwu.app.action.AppAction;


/**
 * <B>AboutAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-5 created
 * @since map editor Ver 1.0
 * 
 */
public class AboutAction extends AppAction {
	private static final long serialVersionUID = 1L;
	public static final String ID = "about";

	public AboutAction(Application app) {
		super(app,ID);
	} 

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		//System.out.println(" CloseAction  ");

		AppDialog dialog=new AboutView(app);
		dialog.showDialog(new AppDialogListener() {
			
			@Override
			public void optionSelection(DialogEvent evt) {
			}
		});
	}

	@Override
	public String getID() {
		return ID;
	}
	
}
