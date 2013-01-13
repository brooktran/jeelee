package com.test.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * 类<B> GridLayout1 </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-7 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class GridLayout1 extends JFrame{
	public GridLayout1(){
		setLayout(new GridLayout(7,3));
		for(int i=0;i<20;i++){
			add(new JButton("Button"+i));
		}
		setSize(300,300);
		setVisible(true);
	}
	public static void main(String[] args) {
		new GridLayout1();
	}
}
