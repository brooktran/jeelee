package org.jtool.app;

import java.awt.Window;
import java.beans.PropertyChangeListener;
import java.io.File;

/**
 * <B>IApplication</B>.
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-16 created
 * @since map editor Ver 1.0
 */
public interface IApplication { // facade of the application

	/**
	 * Launch.
	 */
	public void launch();

	public void init();

	public void start();

	public void stop();



	/**
	 * Sets the model.
	 * 
	 * @param model
	 *            the new model
	 */
	public void setModel(IModel model);

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 */
	public IModel getModel();




	public void addView(final IView view);

	public void showView(final IView view);

	public void hideView(final IView view);



	public java.util.List<File> recentFiles();
	public void addRecentFile(File file);


	public String getName();

	/**
	 * 
	 * @param appListener
	 */
	public void addPropertyChangeListener(PropertyChangeListener appListener);

	/**
	 * 
	 * @return
	 */
	public boolean isEnable();

	public void setEnable(boolean b);

	/**
	 * 
	 * @return
	 */
	public Window getWindow();

	/**
	 * 
	 * @return
	 */
	public String getVersion();

	public void setFoucs();

	/**
	 * 
	 * @return
	 */
	public IView getView();

	public void setTitle(String string);

	/**
	 * 
	 * @param view
	 */
	public void setupView(IView view);

}
