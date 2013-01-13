package org.jtool.app;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;

/**
 * <B>AbsView</B>.
 * 
 * @author Brook Tran. Email: <a
 * href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-16 created
 * @since map editor Ver 1.0
 */
public abstract class AbsView extends JPanel implements IView {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The title. */
	private String title;

	/** The actions. */
	private Map<String, Action> actions;

	protected IApplication application;



	/**
	 * The Constructor.
	 * 
	 * @since map editor
	 */
	public AbsView() {
		actions = new HashMap<String, Action>();
	}

	/**
	 * Gets the action.
	 * 
	 * @param ID the iD
	 * 
	 * @return the action
	 * 
	 * @see com.map.app.IView#getAction(java.lang.String)
	 */
	public Action getAction(String ID) {
		return actions.get(ID);
	}

	/**
	 * Gets the componenet.
	 * 
	 * @return the componenet
	 * 
	 * @see com.map.app.IView#getComponenet()
	 */
	public JComponent getComponenet() {
		return this;
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 * 
	 * @see com.map.app.IView#getTitle()
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Inits the.
	 * 
	 * @see com.map.app.IView#init()
	 */
	public void init() {
		System.out.println("AbsView init");
		// initComponents();
		setVisible(false);
	}

	/**
	 * Put action.
	 * 
	 * @param ID the iD
	 * @param action the action
	 * 
	 * @see com.map.app.IView#putAction(java.lang.String, javax.swing.Action)
	 */
	public void putAction(String ID, Action action) {
		actions.put(ID, action);
	}

	/**
	 * Sets the title.
	 * 
	 * @param title the title
	 * 
	 * @see com.map.app.IView#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Start.
	 * 
	 * @see com.map.app.IView#start()
	 */
	public void start() {
		setVisible(true);
	}





	public IApplication getApplication(){
		return application;
	}

	public void initApplication(IApplication app){
		application=app;
	}

	/** 
	 * @see org.jtool.app.IView#getViewName()
	 */
	public String getViewName() {
		return this.getClass().getName();
	}
}
