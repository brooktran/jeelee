package org.jtool.app.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import org.jtool.app.IApplication;
import org.jtool.utils.ResourceUtil;


/**
 * <B>AppAction</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-26 created
 * @since map editor Ver 1.0
 * 
 */
public abstract class AppAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	protected static final String Labels = "org.jtool.app.Labels";

	protected IApplication app;//TODO this is a bug

	private PropertyChangeListener appListener;


	public AppAction(IApplication app) {
		//		if(this.app == null ){
		//			this.app = app;
		//		}
		//		else if(this.app!=app){//TODO app.equals();
		//			throw new IllegalArgumentException();
		//		}
		this.app=app;
		installApplicationListeners(app);
		updateApplicationEnable();
	}

	public AppAction(IApplication app,String ID) {
		if (app == null) {
			throw new IllegalArgumentException();
		}
		//		if(this.app == null ){
		//			this.app = app;
		//		}
		//		else if(this.app!=app){//TODO app.equals();
		//			throw new IllegalArgumentException();
		//		}
		this.app=app;
		installApplicationListeners(app);
		updateApplicationEnable();

		initAction(ID);
	}

	/**
	 * 
	 */
	private void initAction(String ID) {
		ResourceUtil resource=
			ResourceUtil.getResourceBundleUtil(Labels);
		resource.configAction(this,ID);
	}

	/**
	 * 
	 * @param app
	 */
	private void installApplicationListeners(IApplication app) {
		if (appListener == null) {
			appListener = createAppListener();
		}
		app.addPropertyChangeListener(appListener);

	}

	/**
	 * 
	 * @return
	 */
	private PropertyChangeListener createAppListener() {
		return new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == "enabled") {
					updateApplicationEnable();
				}
			}
		};
	}
	/**
	 * 
	 */
	private void updateApplicationEnable() {
		firePropertyChange("enabled", 
				Boolean.valueOf(!isEnabled()), 
				Boolean.valueOf(isEnabled()));
	}
}
