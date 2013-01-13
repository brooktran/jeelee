package org.jtool.app;

import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JToolBar;

/**
 * <B>IModel</B>.
 * 
 * @author Brook Tran. Email: <a
 * href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-16 created
 * @since map editor Ver 1.0
 */
public interface IModel {

	/**
	 * Put action.
	 * 
	 * @param ID the iD
	 * @param action the action
	 */
	public void putAction(String ID,Action action);

	/**
	 * Gets the action.
	 * 
	 * @param ID the iD
	 * 
	 * @return the action
	 */
	public Action getAction(String ID);


	/**
	 * Sets the activity view of the application.
	 * 
	 * @param view the new view
	 */
	public void setView(IView view);

	/**
	 * Gets the current activity view.
	 * 
	 * @return the view
	 */
	public IView getView();

	/**
	 * Creates the view and activity it.
	 * 
	 * @return the view
	 */
	public IView createView();

	/**
	 * Sets the view class.
	 * 
	 * @param viewName the new view class
	 */
	public void setViewName(String viewName);


	/**
	 * Gets the view class.
	 * 
	 * @return the view class
	 */
	public String getViewName();

	/**
	 * Inits the.
	 */
	public void init(IApplication app);

	// public IMapView createView();
	//	
	// public void initView();

	/**
	 * Gets the name of the application.
	 * 
	 * @return the name
	 */
	public String getName();

	/**
	 * Sets the name of the application.
	 * 
	 * @param name the name
	 */
	public void setName(String name);

	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	public String getVersion();

	/**
	 * Sets the version.
	 * 
	 * @param version the version
	 * 
	 * @return the string
	 */
	public void setVersion(String version);

	/**
	 * Gets the copyright.
	 * 
	 * @return the copyright
	 */
	public String getCopyright();

	/**
	 * Sets the copyright.
	 * 
	 * @param copyright the new copyright
	 */
	public void setCopyright(String copyright);



	/**
	 * Creates the menu.
	 * 
	 * @return the list< j menu>
	 */
	public  List<JMenu>  createMenu();

	/**
	 * Creates the toolbars.
	 * 
	 * @param app the app
	 * @param view the view
	 * 
	 * @return the list< j tool bar>
	 */
	public List<JToolBar> createToolbars(IApplication app,IView view);

	/**
	 * Sets the editor.
	 * 
	 * @param data the data
	 */
	//	public void setEditor(IMap data);

	/**
	 * Creates the map.
	 * 
	 * @param map the map
	 */
	//	public void setEditor(IMap map);



	public IApplication getApplication();

	public void setApplication(IApplication app);
}
