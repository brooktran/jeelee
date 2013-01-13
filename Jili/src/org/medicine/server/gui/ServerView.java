/* ServerView.java 1.0 2010-2-2
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
package org.medicine.server.gui;

import java.awt.FlowLayout;

import javax.swing.JButton;

import org.zhiwu.app.AbsView;

/**
 * <B>ServerView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-12 created
 * @since Jili Ver 1.0
 * 
 */
public class ServerView extends AbsView {
	
	public ServerView() {
		initComponents();
	}
	
	private void initComponents(){
		FlowLayout flowLayout = new FlowLayout(FlowLayout.LEFT, 5, 20);
		setLayout(flowLayout);
		
		JButton btnNewButton = new JButton("销售");
		add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("New button");
		add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("New button");
		add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("New button");
		add(btnNewButton_3);
		
		JButton btnNewButton_4 = new JButton("New button");
		add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		add(btnNewButton_5);
	}
	
	
	
}
