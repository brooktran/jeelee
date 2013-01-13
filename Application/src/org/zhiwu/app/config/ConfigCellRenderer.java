/* ConfigCellRenderer.java 1.0 2010-2-2
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
package org.zhiwu.app.config;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * <B>ConfigCellRenderer</B>
 * is a cell renderer with text and icon.
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-8 created
 * @since org.zhiwu.app.config Ver 1.0
 * 
 */
public class ConfigCellRenderer extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 450238025014097702L;
	private final Object[] data;
	
	/**
	 * 
	 */
	public ConfigCellRenderer(Object[] objects) {
		this.data =objects;
		setOpaque(true);
	}
	
	
	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		if (value != null) {
			setText(value.toString());
			Image image=getItem(value.toString()).getIcon();
			setIcon(new ImageIcon(image));
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}
	
	public ConfigItem getItem(Object key){
		for(Object o:data){
			ConfigItem item=(ConfigItem)o;
			if (item.getLabel().equals(key)) {
				return item;
			}
		}
		return null;
	}

}
