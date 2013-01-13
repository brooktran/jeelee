package org.jtool.app.action;

import java.awt.event.ActionEvent;

import org.jtool.app.IApplication;

/**
 * <B>ExitAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-26 created
 * @since map editor Ver 1.0
 * 
 */
public class ExitAction extends AppAction{
	private static final long serialVersionUID = 1L;
	public static final String ID = "exit";

	public ExitAction(IApplication app){
		super(app,ID);

		//		ResourceUtil resource=
		//			ResourceUtil.getResourceBundleUtil(Labels);
		//		resource.configAction(this,ID);
	}

	/** 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

		// 保存用户程序设置

		// 保存地图文件

		//  exit
		// app.stop();
		System.exit(0);

	}

}
