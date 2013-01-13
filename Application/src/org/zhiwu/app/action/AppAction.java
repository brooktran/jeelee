package org.zhiwu.app.action;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractAction;

import org.zhiwu.app.Application;
import org.zhiwu.utils.SwingResources;



/**
 * <B>AppAction</B>
 * @author Brook.Tran.C Email: <a  href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2009-9-26 created
 * @since  Application Framework Ver 1.0
 */
public abstract class AppAction extends AbstractAction {
	protected Application app;
	private PropertyChangeListener appListener;
	public final static String ID="app.action";

	public AppAction(Application app) {
		init(app);
	}

	public AppAction(Application app,String ID) {
		if (app == null) {
			throw new IllegalArgumentException();
		}
		
		init(app);
		initAction(ID);
	}

	private void init(Application app) {
		this.app=app;
		installApplicationListeners(app);
		updateApplicationEnable();
//		AppManager.addPropertyChangeListener(new PropertyChangeListener() {
//			@Override
//			public void propertyChange(PropertyChangeEvent evt) {
//				if (evt.getPropertyName().equals(ApplicationConstant.PROPERTY_LOCALE)) {// locale changed then changed the icon and label
//					initAction(getID());
//				}
//			}
//		});
	}

	protected void initAction(String ID) {
		SwingResources r = app.getResource();
		r.configAction(this,ID);
	}

	private void installApplicationListeners(Application app) {
		if (appListener == null) {
			appListener = createAppListener();
		}
		app.addPropertyChangeListener(appListener);

	}

	private PropertyChangeListener createAppListener() {
		return new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName() == "enabled") {
					updateApplicationEnable();
				}
			}
		};
	}
	private void updateApplicationEnable() {
		firePropertyChange("enabled", 
				Boolean.valueOf(!isEnabled()), 
				Boolean.valueOf(isEnabled()));
	}
}
