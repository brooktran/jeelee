package org.chenzhw.sample;

import java.awt.Label;
import java.security.acl.Permission;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * <B> StallView </B>
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-4-6 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class StallView extends JFrame{
	
	private JLabel label1;
	private JTextField perMinuTextField;
	private JTable resulTable;
	private JButton getResult;
	
	public StallView(){
		init();
	}
	
	public void init(){
		label1=new JLabel("输入每分钟的收入");
		perMinuTextField=new JTextField();
		resulTable=new JTable();
		getResult=new JButton("确定");
		
		
	}
	
	
}
