/* ManualAction.java 1.0 2010-2-2
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
package org.divine.actions;

import java.awt.event.ActionEvent;

import org.divine.app.liuyao.LiuYao;
import org.divine.gui.DivineView;
import org.divine.gui.ManualDialog;
import org.zhiwu.app.AppDialog;
import org.zhiwu.app.DialogEvent;
import org.zhiwu.app.Application;
import org.zhiwu.app.AppDialogListener;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>ManualAction</B>
 * 手动起卦
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-28 created
 * @since org.divine.actions Ver 1.0
 * 
 */
public class ManualAction extends AppAction{
	private static final long serialVersionUID = 1L;
	public static final String ID="manual";
	
	/**
	 * @param app
	 */
	public ManualAction(Application app) {
		super(app);
		
		AppResources resource = app.getResource();
		resource.configAction(this, ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		app.setEnable(false);
		final AppDialog dialog=new ManualDialog(app);
		
		dialog.showDialog(new AppDialogListener() {
			@Override
			public void optionSelection(DialogEvent evt) {
				if(evt.getName().equals(AppDialog.COMFIRM_OPTION)){
					LiuYao liuYao=(LiuYao) evt.getData();
					DivineView view=(DivineView)app.getView();
					view.setData(liuYao);
				}
				dialog.dispose();
				
				app.setFoucs();
				app.setEnable(true);
			}
		});
		
		
		
		
	}

	@Override
	public String getID() {
		return ID;
	}
	
}
