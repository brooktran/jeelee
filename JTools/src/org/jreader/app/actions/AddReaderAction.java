/* AddReaderAction.java 1.0 2010-2-2
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
package org.jreader.app.actions;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.jreader.app.JReader;
import org.jreader.gui.AddGuilder_0;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.app.guider.DefaultGuiderView;
import org.zhiwu.utils.AppResources;

/**
 * <B>AddReaderAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-3 created
 * @since org.jreader.app.actions Ver 1.0
 * 
 */
public class AddReaderAction extends AppAction {
	private static final long serialVersionUID = 1L;
	public static final String ID="add.reader";


	/**
	 * @param app
	 */
	public AddReaderAction(JReader jReader,Application app) {
		super(app);
		
		AppResources resource = AppResources.getResources("org.jreader.app.Labels");
		resource.configAction(this, ID);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
//		app.setEnable(false);
		
		DefaultGuiderView guiderView=new DefaultGuiderView(app);
		guiderView.setContent(AddGuilder_0.class);
		 
		
		guiderView.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent arg0) {
				if(arg0.getPropertyName().equals("guiderState")){
					if(arg0.getNewValue().equals("cancle") || arg0.getNewValue().equals("finish")){
						app.setEnable(true);
					}
				}
			}
		});
		
		guiderView.start();
		guiderView.setVisible(true);
		
		
		
	}
	@Override
	public String getID() {
		return ID;
	}
	
	
}
