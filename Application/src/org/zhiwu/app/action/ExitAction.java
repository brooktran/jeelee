package org.zhiwu.app.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.zhiwu.app.Application;
import org.zhiwu.app.View;


/**
 * <B>ExitAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-26 created
 * @since Application Framework Ver 1.0
 * 
 */
public class ExitAction extends AppAction{
	public static final String ID = "exit";

	public ExitAction(Application app){
		super(app,ID);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		View v=app.getView();
		if(v.hasUnsavChanged()){
			int op=JOptionPane.showConfirmDialog(// TODO I18N
					v.getComponenet(), 
					"是否保存?",
					v.getTitle(), 
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE);
			if (op==JOptionPane.OK_OPTION) {
				v.save();
			}
		}

		app.stop();
		app.exit();
	}
//	@Override
//	public String getID() {
//		return ID;
//	}
}
