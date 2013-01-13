package com.chenzhw.util;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 * 类<B> UIToolkit </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-2-27 新建
 * @since chenzhw Ver 1.0
 * 
 */
public final class UIToolkit {
	//外观
	public static JFrame showNewFrm(String title,Class frmClass){
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Font font = new Font("Dialog", Font.PLAIN, 12);
			Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
			// Returns an enumeration of the keys in this hashtable.

			// 将所有字体都设置为font对象中定义的字体
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				if (UIManager.get(key) instanceof Font) {
					UIManager.put(key, font);
				}
			}
		} catch (Exception e) {
		}
		
		JFrame frm=null;
		try {
			frm = (JFrame)frmClass.newInstance();
			frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frm.setTitle(title);
			frm.setSize(800, 573);
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			frm.setLocation((dimension.width - frm.getSize().width) / 2,
					(dimension.height - frm.getSize().height) / 3);
			frm.setVisible(true);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return frm;
		
	}
}
