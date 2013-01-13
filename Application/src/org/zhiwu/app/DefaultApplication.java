/* @(#)DefaultApplication.java 1.0 2009-11-18
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.zhiwu.app;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileLock;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import org.zhiwu.app.action.AboutAction;
import org.zhiwu.app.action.ConfigAction;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.app.action.HelpAction;
import org.zhiwu.app.action.OpenRecentAction;
import org.zhiwu.app.action.SaveAction;
import org.zhiwu.app.action.VersionAction;
import org.zhiwu.app.action.ViewSwitchAction;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.SwingResources;
import org.zhiwu.utils.SystemTrayProxy;


/**
 * <B>DefaultApplication</B> extends this class must override the
 * {@link #initAppManager()} method to set the path of the ResourceBundle of the
 * AppManager.
 * 
 * @see org.zhiwu.app.AbsApplicaton#initAppManager()
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-18 created
 * @since JTool Ver 1.0
 */
public class DefaultApplication extends AbsApplicaton {
	protected static final String RecentFileProperty = "RecentFiles";
	protected List<File> recentFiles = new LinkedList<File>();
	protected JFrame window; 
	protected JToolBar toolbar;// the toolbar of the application.
	protected boolean isCreateToolbar = true;
	
	private static String lockPath="resources/.lock";
	private FileLock lock;
	

	public DefaultApplication() {
		setLAF();// 2010.01.28 , move setLAF() here to prevent some applications 
		// initialization their views before the Applicaiton has been created.
		// to avoid that ,you can create a app first, and then create your own views.
		
//		initAppManager(labels);
	}

	@Override
	public void init() {
		super.init();
		checkOneInstance();
		toolbar=new JToolBar();
		createWindow();
	}
	
	@Override
	public void setupView(View view) {
		super.setupView(view);
		setTitle(view.getTitle());
		setIconImage(view.getIcon());
	}

	@Override
	public void start() {
		super.start();
		initModelActions();
		setupMenuToolbar();
		setWindowSizeLocation();
		startWindow();
		startTray();
	}

	private void startWindow() {
		window.setVisible(true);
		window.toFront();		
	}

	protected void initModelActions() {

		Model model = getModel();

//		model.putAction(OpenAction.ID, new OpenAction(this));
//		model.putAction(CloseAction.ID, new CloseAction(this));
		model.putAction(ExitAction.ID, new ExitAction(this));


//		model.putAction(FillAction.ID, new FillAction(this));
//		model.putAction(PenAction.ID, new PenAction(this));

		model.putAction(SaveAction.ID, new SaveAction(this));
//		model.putAction(SaveAsAction.ID, new SaveAsAction(this));

		model.putAction(AboutAction.ID, new AboutAction(this));
	
	}

	@Override
	public Window getWindow() {
		return window;
	}

	@Override
	public Container getContainer() {
		return window.getContentPane();
	}

	private void createWindow() {
		window = new JFrame(getName());
		window.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		window.setLocale(AppManager.getLocale());
		window.setContentPane(getContainer());		
	}

	protected void setIconImage(Image image){
		window.setIconImage(image);
	}


	@Override
	public void setContainer(Container newValue) {
		Container oldValue = window.getContentPane();
		window.setContentPane(newValue);
		firePropertyChange("container", oldValue, newValue);
	}

	private void setWindowSizeLocation() {
		AppPreference pref=getPreference();
		Dimension size;
		Point location;
		int extendedState=0;
		try {
			String[] s = pref.get("window.size").split(" ");
			size = new Dimension(Integer.parseInt(s[0]), Integer.parseInt(s[1]));
			String[] l = pref.get("window.location").split(" ");
			location = new Point(Integer.parseInt(l[0]), Integer.parseInt(l[1]));
			extendedState = pref.getInteger("window.extended.state");
		} catch (Exception e){// can not found the configuration.
			Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
			size = new Dimension(dimension.width / 2, dimension.height / 2);
			location=new Point((dimension.width - window.getSize().width) / 2,
					(dimension.height - window.getSize().height) / 3);
		}
		window.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				AppPreference p=getPreference();
				int es=window.getExtendedState();
				p.put("window.extended.state",es==Frame.ICONIFIED?Frame.NORMAL:es);
				if(es == Frame.NORMAL){
					Dimension d=window.getSize();
					p.put("window.size", d.width+" "+d.height);
				}
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {
				AppPreference pref=getPreference();
				int es=window.getExtendedState();
				pref.put("window.extended.state",es==Frame.ICONIFIED?Frame.NORMAL:es);
				if(es == Frame.NORMAL){
					Dimension d=window.getSize();
					pref.put("window.size", d.width+" "+d.height);
				}
				Point p=window.getLocation();
				pref.put("window.location", p.x+" "+p.y);
			}
		});
		window.setSize(size);
		window.setPreferredSize(size);
		window.setLocation(location);
		window.setExtendedState(extendedState);// do nothing when add this : window.pack(); 
	}

	@Override
	protected void setupMenuToolbar() {
		window.setJMenuBar(createMenu());
		if(isCreateToolbar){
			createToolBar();
			window.getContentPane().add(toolbar, BorderLayout.PAGE_START);
		}
		window.validate();
	}
	protected JMenuBar createMenu() {
		JMenuBar menuBar =getView().createMenus();
		if(menuBar ==null){
			menuBar=new JMenuBar();
			menuBar.add(createFileMenu());
			for (JMenu menu : getModel().createMenu()) {
				menuBar.add(menu);
			}
			menuBar.add(createToolMenu());
			menuBar.add(createHelpMenu());
		}
		return menuBar;
	}

	protected JMenu createToolMenu() {
		SwingResources resource = getResource();
		JMenu menu=new JMenu();
		resource.configMenu(menu, "tool");
		
		for(Iterator<String> i=model.viewIterator();i.hasNext();){
			menu.add(new ViewSwitchAction(this,i.next()));
		}
		menu.add(model.getAction(ConfigAction.ID));
		return menu;
	}

	protected JMenu createFileMenu() {
		SwingResources resource = getResource();
		JMenu menu = new JMenu();
	
		resource.configMenu(menu, "file");
	
		//		menu.add(model.getAction(JLOpenAction.ID));
		final JMenu openRecentMenu;
		openRecentMenu = new JMenu();
		resource.configMenu(openRecentMenu, "openRecent");
		updateOpenRecentMenu(openRecentMenu);
		menu.add(openRecentMenu);
	
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (name .equals( RecentFileProperty)) {
					updateOpenRecentMenu(openRecentMenu);
				}
			}
		});
	
	
		menu.add(model.getAction(OpenRecentAction.ID));//TODO
		menu.add(model.getAction(ExitAction.ID));
	
		// recent change 
	
		return menu;
	}

	private void updateOpenRecentMenu(JMenu openRecentMenu) {
		if (openRecentMenu.getItemCount() > 0) {
			JMenuItem clearRecentFilesItem = openRecentMenu.getItem(
					openRecentMenu.getItemCount() - 1);
			openRecentMenu.removeAll();
			for (File f : recentFiles()) {
				openRecentMenu.add(new OpenRecentAction(this, f));
			}
			if (recentFiles().size() > 0) {
				openRecentMenu.addSeparator();
			}
			openRecentMenu.add(clearRecentFilesItem);
		}
	}

	protected JMenu createHelpMenu() {
		SwingResources resource = getResource();
		// IModel model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "help");
		menu.add(getView().getAction(VersionAction.ID));
		menu.add(model.getAction(HelpAction.ID));
		menu.addSeparator();
		menu.add(model.getAction(AboutAction.ID));

		return menu;

	}

	protected void createToolBar() {
		if (toolbar != null) {
			toolbar.removeAll();
			window.getContentPane().remove(toolbar);
		}

		toolbar = new JToolBar();
		if (getModel() != null) {
			for (JToolBar tb : getModel().createToolbars(this, getView())) {
				toolbar.add(tb);
			}
		}
		// return toolbar;
	}

	@Override
	public void setFoucs() {
		window.toFront();
	}

	@Override
	public java.util.List<File> recentFiles() {
		return Collections.unmodifiableList(recentFiles);
	}

	@Override
	public void setTitle(String title) {
		window.setTitle(title);
	}

	@Override
	public SwingResources getResource() {
		return AppManager.getResources();
	}

	/**
	 * Sets the look and feel for this app .
	 */
	protected void setLAF() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			//
			// AppResources r=AppManager.getResources();
			// UIManager.put("tree.collapsedIcon",
			// r.getImageIcon("tree.collapsedIcon"));
			// UIManager.put("tree.expandedIcon",
			// r.getImageIcon("tree.expandedIcon"));

		} catch (Exception e) {
			AppLogging.handleException(e);
		}

		if (UIManager.getString("OptionPane.css") == null) {
			UIManager.put("OptionPane.css", "");
		}

	}
	
//	/**
//	 * Init the locale and language of the applicatin.
//	 * @see org.zhiwu.app.AbsApplicaton#initAppManager()
//	 */
//	@Override
//	protected void initAppManager() {
//		initLocale();
//	}
//
	
//	private void initAppManager(String labels) {
//		AppManager.setLabels(labels);
//		setLocale();
//	}
	/**
	 * Inits the locale of the application.
	 */
	protected void initLocale() {
		Locale l;
		AppPreference prep=PreferenceManager.getInstance().getPreference(Application.class.getName());
		String localeString=prep.get("locale");
		int p=localeString.indexOf("_");
		l=new Locale(localeString.substring(0, p),localeString.substring(p+1));
		AppManager.setLocale(l);
	}
	
	
	public boolean isCreateToolbar() {
		return isCreateToolbar;
	}

	public void setCreateToolbar(boolean isCreateToolbar) {
		this.isCreateToolbar = isCreateToolbar;
	}
	
	
	private void startTray() {
		AppPreference pref = AppManager.getPreference(Application.class.getName());
		boolean isTray = pref.getBoolean("tray");//$NON-NLS-1$
		if(!isTray){
			return;
		}
		
		try {
			SwingResources r=getResource();
			SystemTrayProxy tray = new SystemTrayProxy(this);
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
			
			tray.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					if (e.getButton() == MouseEvent.BUTTON1 ||
							e.getClickCount() ==1) {
						Application app = DefaultApplication.this;
						app.setVisible(!app.isVisible());
					}
				}
			});
			tray.start();
		} catch (Exception e1) {
			AppLogging.handleException(e1);
		}		
	}
	
	
	@Override
	public void exit() {
		// release lock
		
		AppPreference pref = AppManager.getPreference(Application.class.getName());
		if(pref.getBoolean("one.instance")){
			try {
				lock.release();
			} catch (IOException e) {
				AppLogging.handleException(e);
			}
		}
		super.exit();
	}
	
	
	///////////   checkOneInstance
	
	protected boolean checkOneInstance() {
		AppPreference pref = AppManager.getPreference(Application.class.getName());
		if(!pref.getBoolean("one.instance")){
			return true;
		}
		
		if(!isLocked()){// no application was run.
			return true;
		}
		
		// there is a application runing.
		// so, attention it.
//		try {
//			SwingUtilities.invokeAndWait(new Runnable() {
				
//				@Override
//				public void run() {
					JOptionPane.showMessageDialog(null,
							"application already running...","",JOptionPane.WARNING_MESSAGE);
//				}
//			});
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			e.printStackTrace();
//		}finally{
			System.exit(0);
//		}
		return false;
	}
	
	
	private boolean isLocked() {
		try {// if the lock was null, anthor program was locking the file.
			lock=new FileOutputStream(new File(lockPath)).getChannel().tryLock();
		} catch (FileNotFoundException e) {
			AppLogging.handleException(e);
			return false;
		} catch (IOException e) {
			AppLogging.handleException(e);
			return false;
		}
		// do not close stream or file will be unlocked
		return lock==null;
	}
	
	
}