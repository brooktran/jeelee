/* SwingResources.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Chen Zhiwu
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
import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;

import org.zhiwu.app.AppManager;
import org.zhiwu.app.View;
import org.zhiwu.app.config.ConfigItem;
import org.zhiwu.app.guider.AbsGuiderContent;

/**
 * <B>SwingResources</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2012-7-24 created
 * @since Application Ver 2.0
 * 
 */
public class SwingResources extends AppResources{
	
	
	protected SwingResources(ResourceBundle r) {
		super(r);
	}

	/**
	 * Gets the resource bundle util.
	 * 
	 * @param boundleName the full package name.
	 * <p>
	 * note: if the name wasn't a full name, the Local would be loaded.
	 * 
	 * @return the resource bundle util
	 * 
	 * @throws MissingResourceException the missing resource exception
	 */
	public static SwingResources getResources(String boundleName)throws MissingResourceException{
		return new SwingResources(ResourceBundle.getBundle(boundleName,AppManager.getLocale()));// you can use Locale.getDefault() instead of AppManager... 
	}

	/**
	 * Gets the resource bundle util.
	 * 
	 * @param boundleName the boundle name
	 * @param locale the locale
	 * 
	 * @return the resource bundle util
	 * 
	 * @throws MissingResourceException the missing resource exception
	 */
	public static SwingResources getResources(String boundleName,Locale locale)throws MissingResourceException{
		return new SwingResources(ResourceBundle.getBundle(boundleName,locale));
	}
	
	public void configMenu(JMenuItem menu, String name) {
		menu.setText(getString(name));
		if(!(menu instanceof JMenu)){// items
			menu.setAccelerator(getAcc(name));
		}
		menu.setMnemonic(getMnem(name));
		menu.setIcon(getImageIcon(name,getClass()));
	}

	public Icon getImageIcon(String key){
		return getImageIcon(key,getClass());
	} 

	private Icon getImageIcon(String key,
			Class<?> clazz) {
		String src="";

		try {
			src=resource.getString(key+".icon");
		} catch (MissingResourceException e) {
			return null;
		}

		if (src.equals("")) {
			return null;
		}

		String imageDir=getImageDir();
		if(!(imageDir.endsWith("/"))){
			imageDir+="/";
		}
		src = imageDir+src;
		return new ImageIcon(src);
	}
	
	private String getImageDir(){
		String imageDir = resource.getString("icon.dir");
		if(!(imageDir.endsWith("/"))){
			imageDir+="/";
		}
		return imageDir;
	}
	
	public Image getImage(String key) {
		String src="";
		try {
			src=resource.getString(key+".icon");
			String imageDir=getImageDir();
			src = imageDir+src;
			return ImageIO.read(new File(src)); 
		} catch (MissingResourceException e) {
		} catch (IOException e) {
		}	
		return null;
	}
	

	private char getMnem(String key) {
		String string=null;
		try {
			string=resource.getString(key+".mnem");;
		} catch (MissingResourceException e) {
		}
		return (string==null||string.length()==0)?'\0':string.charAt(0);
	}

	/**
	 * Gets the shortcut key.
	 */
	private KeyStroke getAcc(String key) {
		KeyStroke keyStroke=null;

		String string=null;
		try {
			string=resource.getString(key+".acc");
		} catch (MissingResourceException e) {
		}
		keyStroke = (string==null)?(KeyStroke)null:KeyStroke.getKeyStroke(string);

		return keyStroke;
	}

	/**
	 * config swing action
	 */
	public void configAction(Action action, String id) {
		configAction(action, id,getClass());
	}

	private void configAction(Action action, String id,
			Class<?> clazz) {
		action.putValue(Action.NAME, getString(id));
		action.putValue(Action.ACCELERATOR_KEY, getAcc(id));
		action.putValue(Action.MNEMONIC_KEY, new Integer(getMnem(id)));
		action.putValue(Action.SMALL_ICON, getImageIcon(id, clazz));
		action.putValue(Action.SHORT_DESCRIPTION, getString(id));
	}

	public void configDialog(JDialog dialog, String name) {
		dialog.setTitle(getString(name));
		dialog.setIconImage(getImage(name));
	}

	public void configWindow(JFrame frame, String id) {
		frame.setTitle(getString(id+".title"));
	}

	/**
	 * Config JLabel.
	 */
	public void configLabel(JLabel label, String id) {
		label.setText(getString(id+".label.text"));//$NON-NLS-1$
	}

	
	public void configToolbarButton(JToggleButton t,String id) {
		Icon icon=getImageIcon(id);
		if(icon ==null){
			t.setText(getString(id));
		}else{
			t.setIcon(icon);
			t.setText(null);
		}
		t.setToolTipText(getString(id+".tips"));//$NON-NLS-1$
		
	}
	public void configTray(SystemTrayProxy tray) {
		tray.setToolTip(getString("system"));//$NON-NLS-1$
		tray.setImage(getImage("system"));
	}

	
	
	
	///  --------- for Application
	
	/**
	 * Config view.
	 */
	public void configView(View view, String id) {
		view.setTitle(getString(id+".title"));//$NON-NLS-1$
	}
	public void configConfigItem(ConfigItem item) {
		String name=item.getLabel()+".config"; //$NON-NLS-1$
		item.setIcon(getImage(name));
		item.setLabel(getString(name));
	}
	public void configGuiderContent(AbsGuiderContent content, String id) {
		content.setTitle(getString(id+".title"));//$NON-NLS-1$
		content.setContent(getString(id+".content"));//$NON-NLS-1$
	}
}
