/* AbsApplicaton.java 1.0 2010-2-2
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
package org.zhiwu.app;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import javax.swing.SwingUtilities;

import org.zhiwu.app.config.AppConfigItem;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.Config;
import org.zhiwu.app.config.ConfigItem;
import org.zhiwu.app.config.ConfigItemEvent;
import org.zhiwu.app.config.ConfigListener;
import org.zhiwu.app.config.DefaultConfig;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AbstractBean;
import org.zhiwu.utils.AppLogging;


/**
 * <B>AbsApplicaton</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-28 created
 * @since org.zhiwu.app Ver 1.0
 * 
 */
public abstract class AbsApplicaton extends AbstractBean implements Application {
	protected List<File> recentFiles = new LinkedList<File>();// recent open paths 
	protected static final String RecentFileProperty="RecentFiles";
	private static final int MAX_RESENT_FILE_SIZE = 10;//TODO set the num by munual

	private String state;

	protected Model model;
	private final List<View> views;
	protected Config config;
	

	public AbsApplicaton() {
//		initAppManager();
		setState(ApplicationConstant.STATE_BEGIN);
		views = new ArrayList<View>();
		config=new DefaultConfig();
	}
	
	@Override
	public Config getConfig() {
		return config;
	} 

	/**
	 * Launch the app.
	 * 
	 * @see com.map.app.IApplication#launch()
	 */
	@Override
	public void launch() {
		setState(ApplicationConstant.STATE_LAUNCH);
		init();
		start();
	}

	/**
	 * Inits the application.
	 * in this state, config item is adds.
	 * 
	 * @see com.map.app.IApplication#init()
	 */
	@Override
	public void init() {
		setState(ApplicationConstant.STATE_INIT);
		addConfigItem(AppConfigItem.class);
		addConfigListener(AppConfigItem.class,new ConfigListener() {
			@Override
			public void itemChanged(ConfigItemEvent e) {
				if (e.getItemName().equals(AppConfigItem.HANDLE_NAME_LOCALE)) {
					AppManager.setLocale((Locale) e.getNewValue());
				}
			}
		});
	}

	@Override
	public void addConfigListener(Class<? extends ConfigItem> c,
			ConfigListener l) {
		config.addConfigListener(c,l);
	}

	@Override
	public void addConfigItem(Class<? extends ConfigItem> c) {
		config.addItem(c);
	}

	@Override
	public void start() {
		setState(ApplicationConstant.STATE_STARTIING);
		// model initialization must after the view has created/initied.
		model.init(this);
		
		View view =  model.createView();
		initView(view);
		setupView(view);
		
		setState(ApplicationConstant.STATE_STARTED);
	}

	private void initView(View view ) {
		view.initApplication(this);
		view.init();
		addView(view);
		model.initView(view);
	}
	
	@Override
	public void setView(String viewPath) {
		View v;
		if((v=viewExist(viewPath)) == null){
			try {
				v=(View) Class.forName(viewPath).newInstance();
			} catch (Exception e) {
				AppLogging.handleException(e);
			}
			initView(v);
		}
		
		if(! v.isShowing()){
			firePropertyChange(ApplicationConstant.PROPERTY_VIEW_CHANGED, model.getView(), v);
			model.setView(v);
		}
		
		setupView(v);
	}

	/**
	 * @param class1
	 * @return
	 */
	protected View getView(Class<? extends View> c) {
		for(View v:views){
			if(v.getClass().equals(c)){
				return v;
			}
		}
		return null;
	}

	/**
	 * @return
	 */
	private View viewExist(String viewPath) {
		for(View v:views){
			if(v.getClass().getName().equals(viewPath)){
				return v;
			}
		}
		return null;
	}

	@Override
	public void setupView(View view) {
		if (view.isShowing()) {
			return;
		}
		
		setEnable(false);
		showView(view);
		setEnable(true);
		setupMenuToolbar();
	}
	
	abstract protected void setupMenuToolbar();


	@Override
	public void setModel(Model model) {
		if (model == null) {
			throw new IllegalArgumentException("model cann't be null");
		}
		this.model = model;
	}

	/**
	 * Gets the model.
	 * @return  the model
	 * @see  com.map.app.IApplication#getModel()
	 * @uml.property  name="model"
	 */
	@Override
	public Model getModel() {
		return model;
	}

	@Override
	public void addView(final View view) {
		views.add(view);
	}

	/**
	 * You can show only one view at the same time.
	 * @see com.map.app.IApplication#show(com.map.app.IView)
	 */
	@Override
	public void showView(View view) {
		if (view.isShowing()) {
			return;
		}

		for (View v : views) {
			if (v != view && v.isShowing()) {
				v.close();
				hideView(v);
				//				views.remove(v);
			}
		}
		
		getContainer().add(view.getComponenet());
		view.getComponenet().requestFocusInWindow();
		view.start();
		getContainer().repaint();
	}

	@Override
	public void hideView(View view) {
		if (view.isShowing()) {
			view.setShowing(false);
			Container container = SwingUtilities.getRootPane(
					view.getComponenet()).getParent();
			container.setVisible(false);
			container.remove(view.getComponenet());
			container.repaint();
			container.setVisible(true);
		}

	}

	@Override
	public View getView(){
		return model.getView();
	}

	public abstract void setContainer(Container newValue);

	/**
	 * @return   the mainPanel
	 * @uml.property  name="container"
	 */
	public abstract Container getContainer();


	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public void stop() {
		setEnable(false);
		setState(ApplicationConstant.STATE_STOP);
		for (View v : new LinkedList<View>(views)) {
			v.close();
		}

	}

	@Override
	public String getVersion() {
		return model.getVersion();
	}

	@Override
	public boolean isEnable() {
		return getWindow().isEnabled();
	}

	@Override
	public void setEnable(boolean b) {
		boolean oldValue = getWindow().isEnabled();
		for(View view:new LinkedList<View>(views)){
			view.setEnabled(b);
		}
		getWindow().setEnabled(b);
		
		firePropertyChange("enable", oldValue, b);
	}


	@Override
	public void exit() {
		setState(ApplicationConstant.STATE_EXIT);
		getView().exit();
		
		PreferenceManager.getInstance().save();
		System.exit(0);
	}
	

	@Override
	public void setState(String state) {
		String oldValue=this.state;
		firePropertyChange(ApplicationConstant.STATE_PROPERTY, oldValue, this.state=state);
	}
	@Override
	public String getState(){
		return state;
	}
	
	@Override
	public void addRecentFile(File file) {
		java.util.List<File> oldValue = new LinkedList<File>( recentFiles);
		if (recentFiles.contains(file)) {
			recentFiles.remove(file);
		}
		recentFiles.add(0,file);
		if (recentFiles.size() > MAX_RESENT_FILE_SIZE) {//TODO maxRecentFilesCount
			recentFiles.remove(recentFiles.size()-1);
		}

		//		prefs.putInt(getView().getViewName()+RecentFileProperty, recentFiles.size());
//		prefs.putInt(RecentFileProperty, recentFiles.size());
//		int i=0;
//		for (File f : recentFiles) {
//			prefs.put(RecentFileProperty+"."+i, f.getPath());
//			i++;
//		}

		firePropertyChange(RecentFileProperty, oldValue, 0);
		firePropertyChange(RecentFileProperty,
				Collections.unmodifiableList(oldValue),
				Collections.unmodifiableList(recentFiles)
		);

	}
	
	
	@Override
	public void addviewClass(Class<? extends View> c) {
		throw new UnsupportedClassVersionError();
	}
	
	@Override
	public boolean isVisible() {
		return getWindow().isVisible();
	}
	@Override
	public void setVisible(boolean b) {
		boolean oldValue = getWindow().isVisible();
		getWindow().setVisible(b);
		firePropertyChange("visible", oldValue, b);
	}
	
//	/**
//	 * it's need to set the path of the resouse bundle path of the
//	 * application before start the application and
//	 * inits the locale and language and other appliation attributes.
//	 * 
//	 * @deprecated init the AppManager before the application instance created(new)
//	 */
//	@Deprecated
//	protected void initAppManager(){
//	}

	public AppPreference getPreference() {
		return PreferenceManager.getInstance().getPreference(Application.class.getName());
	}
}
