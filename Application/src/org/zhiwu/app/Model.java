package org.zhiwu.app;

import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JToolBar;


/**
 * <B>IModel</B>.
 * @author   Brook Tran  Email: <a  href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version   Ver 1.0.01 2009-9-16 created
 * @since   Application Framework Ver 1.0
 */
public interface Model {

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
	 * Sets the activity view of the app.
	 * @param view   the new view
	 * @uml.property  name="view"
	 */
	public void setView(View view);

	/**
	 * Gets the current activity view.
	 * @return   the view
	 * @uml.property  name="view"
	 * @uml.associationEnd  
	 */
	public View getView();

	/**
	 * Creates the view and activity it.
	 * 
	 * @return the view
	 */
	public View createView();

	/**
	 * Sets the view class.
	 * 
	 * @param viewName the new view class
	 */
	public void addView(String viewName);


	/**
	 * Gets the view class.
	 * 
	 * @return the view class
	 */
	public String getViewName();

	/**
	 * Inits the.
	 */
	public void init(Application app);

	// public IMapView createView();
	//	
	// public void initView();

	/**
	 * Gets the name of the app.
	 * @return   the name
	 * @uml.property  name="name"
	 */
	public String getName();

	/**
	 * Sets the name of the app.
	 * @param name   the name
	 * @uml.property  name="name"
	 */
	public void setName(String name);

	/**
	 * Gets the version.
	 * @return   the version
	 * @uml.property  name="version"
	 */
	public String getVersion();

	/**
	 * Sets the version.
	 * @param version   the version
	 * @return   the string
	 * @uml.property  name="version"
	 */
	public void setVersion(String version);

	/**
	 * Gets the copyright.
	 * @return   the copyright
	 * @uml.property  name="copyright"
	 */
	public String getCopyright();

	/**
	 * Sets the copyright.
	 * @param copyright   the new copyright
	 * @uml.property  name="copyright"
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
	public List<JToolBar> createToolbars(Application app,View view);


	/**
	 * Sets the editor.
	 * 
	 * @param data the data
	 */
	//	public void setEditor(IMap data);

	/**
	 * Sets the editor.
	 * @param data  the data
	 * @uml.property  name="application"
	 * @uml.associationEnd  
	 */
	//	public void setEditor(IMap map);



	public Application getApplication();

	/**
	 * @param  app
	 * @uml.property  name="application"
	 */
	public void setApplication(Application app);

	/**
	 * 
	 * @param view
	 */
	public void initView(View view);

	/**
	 * @return
	 */
	public Iterator<String> viewIterator();

	/**
	 * @return
	 */
	public Object getUserObject(Class<? extends Object> c);

	/**
	 * @param jReader
	 */
	public void addUserObject(Object o);


}
