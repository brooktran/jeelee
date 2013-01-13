/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.compare;

import java.awt.EventQueue;

import org.zhiwu.app.AbsModel;
import org.zhiwu.app.DefaultApplication;
import org.zhiwu.app.IApplication;
import org.zhiwu.app.IModel;
import org.zhiwu.app.IView;


/**
 * <B>Comparer</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-3 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class Comparer {

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		IModel model =new AbsModel(); 
		model.setName("Dictate");
		model.setVersion("0.0.1");
		model.setCopyright("2010 all right reserve");

		final IApplication app=new DefaultApplication();
		app.setModel(model);

		IView view=new CompareView();
		model.setView(view);

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				app.launch();
			}
		});


	}
}
