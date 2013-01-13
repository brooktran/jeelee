package org.zhiwu.app;

import java.awt.Window;
import java.beans.PropertyChangeListener;
import java.io.File;

import org.zhiwu.app.config.Config;
import org.zhiwu.app.config.ConfigItem;
import org.zhiwu.app.config.ConfigListener;
import org.zhiwu.utils.SwingResources;


/**
 * <B>IApplication</B>.
 * @author   Brook Tran. Email: <a  href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version   Ver 1.0.01 2009-9-16 created
 * @since   Application Framework Ver 1.0
 */
public interface Application { // facade of the app

	/**
	 * Launch.
	 */
	public void launch();

	public void init();

	public void start();

	public void stop();



	/**
	 * Sets the model.
	 * @uml.property  name="model"
	 */
	public void setModel(Model model);

	/**
	 * Gets the model.
	 * @uml.property  name="model"
	 * @uml.associationEnd  
	 */
	public Model getModel();




	public void addView(final View view);
	public void addviewClass(Class<? extends View> c);

	public void showView(final View view);

	public void hideView(final View view);


	public java.util.List<File> recentFiles();
	public void addRecentFile(File file);

	public String getName();

	public void addPropertyChangeListener(PropertyChangeListener appListener);

	/**
	 * @uml.property  name="enable"
	 */
	public boolean isEnable();

	/**
	 * @uml.property  name="enable"
	 */
	public void setEnable(boolean b);

	public Window getWindow();

	public String getVersion();

	public void setFoucs();

	public View getView();
	public void setTitle(String string);
	/**
	 * Exits the app.
	 * You may invoke the stop() menthod before execute this fanction. 
	 */
	public void exit();


	public void setState(String state);
	public String getState();
	public void setupView(View view);


	public SwingResources getResource();

	public void setView(String viewPath);

	/**
	 * @uml.property  name="visible"
	 */
	public boolean isVisible();
	/**
	 * @uml.property  name="visible"
	 */
	public void setVisible(boolean b);

	

	
	public Config getConfig();
	public void addConfigListener(Class<? extends ConfigItem> c,
			ConfigListener l) ;
	public void addConfigItem(Class<? extends ConfigItem> c) ;

	
	

}
