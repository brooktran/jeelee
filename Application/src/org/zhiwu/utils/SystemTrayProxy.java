/* SystemTrayProxy.java 1.0 2010-2-2
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
package org.zhiwu.utils;

import java.awt.Image;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.zhiwu.app.Application;

/**
 * <B>SystemTrayProxy</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-11-30 created
 * @since  org.zhiwu.app Ver 1.0
 */
public class SystemTrayProxy {
	private final Application app;
	private TrayIcon trayIcon;
	private String toolTip;
	private PopupMenu popup;
	private SystemTray tray;
	private MouseListener mouseListener;
	
	private final PropertyChangeListener appListener=new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String property = evt.getPropertyName();
			if(property.equals("visible") || property.equals("enable")){
				boolean b = (Boolean)evt.getNewValue();
				setEnable(b);
			}
			
		}
	};
	
	public SystemTrayProxy(Application app) {
		this.app = app;
	}

	public void start() throws Exception  {
		if (!SystemTray.isSupported())
			throw new Exception("Systray not supported");
		
		trayIcon.setImageAutoSize(true);
		trayIcon.setToolTip(toolTip);
		trayIcon.setPopupMenu(popup);
		trayIcon.addMouseListener(mouseListener);
		tray=SystemTray.getSystemTray();
		tray.add(trayIcon);
		
		app.addPropertyChangeListener(appListener);
	} 
	
	public void setEnable(boolean b){
		trayIcon.removeMouseListener(mouseListener);
		if(b){
			trayIcon.addMouseListener(mouseListener);
			trayIcon.setPopupMenu(popup);
		}
	}
	

	public void setImageName(String imageName) throws IOException {
		setImage(ImageIO.read(new File(imageName)));
	}
	
	public void setImage(Image image) {
		trayIcon = new TrayIcon(image);
	}


	public void setToolTip(String toolTip) {
		this.toolTip = toolTip;
	}

	public void setPopup(PopupMenu popup) {
		this.popup = popup;
	}


	public void addMouseListener(MouseListener l) {
		if(trayIcon!= null){
			trayIcon.removeMouseListener(mouseListener);
//			trayIcon.addMouseListener(l);
		}
		this.mouseListener = l;
	}
	
	
}
