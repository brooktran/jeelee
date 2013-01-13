/* Main.java 1.0 2010-2-2
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
package org.jreader.app;

import java.awt.EventQueue;

import org.jreader.gui.JReaderView;
import org.zhiwu.app.DefaultApplication;
import org.zhiwu.app.Application;

/**
 * <B>Main</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-29 created
 * @since org.jreader.app Ver 1.0
 * 
 */
public class Main {
	
	public static void main(String[] args) {
		JReaderModel model=new JReaderModel(new JReader());
		model.setName("Main");
		model.setVersion("0.1.1.100519238772");
		model.setCopyright("2010 all rights reserve");
		
		
		final Application app=new DefaultApplication();
		app.setModel(model);
		model.setView(new JReaderView());
		
		EventQueue.invokeLater(new Runnable(){
			public void run(){
				app.launch();
			}
		});
		
	}

}
