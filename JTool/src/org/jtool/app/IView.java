package org.jtool.app;

import java.awt.Component;

import javax.swing.Action;

/**
 * <B>IView</B>.
 * 
 * @author Brook Tran.Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-16 created
 * @since map editor Ver 1.0
 */
public interface IView {


	/**
	 * Gets the action.
	 * 
	 * @param ID the iD
	 * 
	 * @return the action
	 */
	public Action getAction(String ID);


	/**
	 * Inits the.
	 */
	public void init();

	/**
	 * Checks if is showing.
	 * 
	 * @return true, if is showing
	 */
	public boolean isShowing();

	/**
	 * Put action.
	 * 
	 * @param ID the iD
	 * @param action the action
	 */
	public void putAction(String ID, Action action);

	/**
	 * Sets the enabled.
	 * 
	 * @param b the b
	 */
	public void setEnabled(boolean b);

	/**
	 * Sets the map.
	 * 
	 * @param b the b
	 */
	//	public void setMap(IMap map);

	/**
	 * Sets the showing.
	 * 
	 * @param b the new showing
	 */
	public void setVisible(boolean b);

	/**
	 * Sets the title.
	 * 
	 * @param title the new title
	 */
	public void setTitle(String title);


	/**
	 * Show.
	 */
	public void show();

	/**
	 * Start.
	 */
	public void start();
	/**
	 * Gets the application.
	 * 
	 * @return the application
	 */
	public IApplication getApplication();

	public void initApplication(IApplication app);


	/**
	 * 
	 * @return
	 */
	public Component getComponenet();


	/**
	 * 
	 * @return
	 */
	public String getViewName();

























}
