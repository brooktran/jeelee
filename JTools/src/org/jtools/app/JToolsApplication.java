/* DivineApplication.java 1.0 2010-2-2
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
package org.jtools.app;

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.channels.FileLock;
import java.util.Locale;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.daily.DailyConfigItem;
import org.daily.DailyView;
import org.divine.gui.WelcomeView;
import org.jtools.app.persistent.DataManager;
import org.zhiwu.app.AppMnanger;
import org.zhiwu.app.Application;
import org.zhiwu.app.ApplicationConstant;
import org.zhiwu.app.DefaultApplication;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.ConfigItemEvent;
import org.zhiwu.app.config.ConfigListener;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.SystemTrayProxy;

/**
 * <B>DivineApplication</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-28 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class JToolsApplication extends DefaultApplication {
	private static final long serialVersionUID = -7896194827964317868L;
	private static String lockPath="resources/.lock";
	private FileLock lock;
	private SystemTrayProxy tray;
	private final DataManager manager=new DataManager();

	public JToolsApplication() {
		AppMnanger.setLabels("org.jtools.app.Labels");
		checkOneInstance();
		addStateListener();
		initWelcome();
	}
	
	@Override
	public void init() {
		super.init();
		initConfig();
		initLocale();
	}

	private void initLocale() {
		Locale l;
		AppPreference prep=PreferenceManager.getInstance().getPreference(Application.class.getName());
		String localeString=prep.get("locale");
		int p=localeString.indexOf("_");
		l=new Locale(localeString.substring(0, p),localeString.substring(p+1));
		AppMnanger.put("locale",l);
	}

	private void initConfig() {
		Class<DailyConfigItem> c=DailyConfigItem.class;
		addConfigItem(c);
		addConfigListener(c, new ConfigListener() {
			@Override
			public void itemChanged(ConfigItemEvent e) {
				DailyView v=(DailyView) getView(DailyView.class);
				if(v==null){
					return;
				}
				String name=e.getItemName();
				if(name.equals(DailyView.PROPERTY_LINE_WRAP)){
					v.setLineWrap(Boolean.parseBoolean(e.getNewValue().toString()));
				}
			}
		} );		
	}

	private void initWelcome() {
		final WelcomeView	welcome = new WelcomeView();
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue().equals(ApplicationConstant.STATE_STARTED)) {
					welcome.dispose();
					// welcome.setVisible(false);
					JToolsApplication.this
							.removePropertyChangeListener(this);
				} else {
					String name=evt.getNewValue().toString();
					welcome.setText(getResource().getString(name));
				}				
			}
		});
		welcome.launch();
	}
	
	@Override
	public void start() {
		super.start();
		startTray();
	}

	private void startTray() {
		tray = new SystemTrayProxy();
		AppResources r=getResource();
		r.configTray(tray);
		
		PopupMenu popup=new PopupMenu();
		MenuItem item=new MenuItem();
		item.setLabel(r.getString("exit"));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getModel().getAction(ExitAction.ID).actionPerformed(null);
			}
		});
		popup.add(item);
		tray.setPopup(popup);
		
		try {
			tray.start(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1 &&
							e.getClickCount() ==1) {
						Application app = JToolsApplication.this;
						app.setVisible(!app.isVisible());
					}
				}
			});
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}


	/**
	 * 
	 */
	private boolean checkOneInstance() {
		if(!isLocked()){// no application was run.
			return true;
		}
		
		// there is a application runing.
		// so, attention it.
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					JOptionPane.showMessageDialog(null,
							"application already running...","",JOptionPane.WARNING_MESSAGE);
				}
			});
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
		return false;
	}

	/**
	 * @return true if one or more application have run.
	 */
	private boolean isLocked() {
		try {// if the lock was null, anthor program was locking the file.
			lock=new FileOutputStream(new File(lockPath)).getChannel().tryLock();
		} catch (FileNotFoundException e) {
			return false;
		} catch (IOException e) {
			return false;
		}
		// do not close stream or file will be unlocked
		return lock==null;
	}

	private void addStateListener() {
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(ApplicationConstant.STATE_PROPERTY)) {
					if (evt.getNewValue().equals(ApplicationConstant.STATE_INIT)) {
						try {
							// prepare data...
							manager.prepare();
//							if(!LiuyaoDataManager.getInstance().prepare()){
//								//TODO 无法解压数据
//							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals(ApplicationConstant.STATE_PROPERTY)) {
					if (evt.getNewValue().equals("exit")) {
						manager.close();
					}
				}
			}
		});

	}

	@Override
	public void exit() {
		try {
			lock.release();
		} catch (IOException e) {
			e.printStackTrace();
		}
		super.exit();
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.DefaultApplication#initAppManager()
	 */
//	@Override
//	protected void initAppManager() {
//		AppMnanger.setLabels("org.jtools.app.Labels");
//		super.initAppManager();
//	}
	
}
