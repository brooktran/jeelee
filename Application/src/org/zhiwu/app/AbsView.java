package org.zhiwu.app;

import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;

import org.zhiwu.utils.SwingResources;


/**
 * <B>AbsView</B>.
 * @author  Brook Tran. Email: <a  href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version  Ver 1.0.01 2009-9-16 created
 * @since  Application Framework Ver 1.0
 */
public abstract class AbsView extends JComponent implements View {
	private String title;
	private boolean isShowing;
	private final Map<String, Action> actions;
	private final List<View> components; 
	protected Application app;
	protected boolean isChanged;
//	private EditableManager editable;

	public AbsView(){
		isChanged = false;
		
		actions = new HashMap<String, Action>();
		components = new ArrayList<View>();
	}

	/**
	 * Adds the component.
	 * 
	 * 
	 * @see com.map.app.IView#addComponent(com.map.app.IView)
	 */
	@Override
	public void addComponent(View component) {
		components.add(component);
		add(component.getComponenet());

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
	@Override
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
	@Override
	public JComponent getComponenet() {
		//System.out.println("AbsView.getComponenet()");
		return this;
	}

	/**
	 * Gets the title.
	 * @return  the title
	 * @see  com.map.app.IView#getTitle()
	 * @uml.property  name="title"
	 */
	@Override
	public String getTitle() {
		return title;
	}

	/**
	 * Inits the.
	 * 
	 * @see com.map.app.IView#init()
	 */
	@Override
	public void init() {
		app.setState(ApplicationConstant.STATE_INIT_VIEW);
		setShowing(false);
		initActions();
		
	}
	/**
	 * Checks if is showing.
	 * @return  true, if checks if is showing
	 * @see  com.map.app.IView#isShowing()
	 * @uml.property  name="isShowing"
	 */
	@Override
	public boolean isShowing() {
		return isShowing;
	}

	/**
	 * Put action.
	 * 
	 * @param ID the iD
	 * @param action the action
	 * 
	 * @see com.map.app.IView#putAction(java.lang.String, javax.swing.Action)
	 */
	@Override
	public void putAction(String ID, Action action) {
		actions.put(ID, action);
	}

	/**
	 * Sets the showing.
	 * @param b  the b
	 * @see  com.map.app.IView#setShowing(boolean)
	 * @uml.property  name="isShowing"
	 */
	@Override
	public void setShowing(boolean b) {
		//System.out.println("AbsView.setShowing()");
		isShowing = b;
		setVisible(b);
	}

	/**
	 * Sets the title.
	 * @param title  the title
	 * @see  com.map.app.IView#setTitle(java.lang.String)
	 * @uml.property  name="title"
	 */
	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Start.
	 * 
	 * @see com.map.app.IView#start()
	 */
	@Override
	public void start() {
		//System.out.println("AbsView.start()");
		setShowing(true);
	}

	@Override
	public Application getApplication(){
		//System.out.println("AbsView.getApplication()");
		return app;
	}

	@Override
	public void initApplication(Application app){
		this.app=app;
		// init the title when the app avaliable.
		SwingResources r = app.getResource();
		r.configView(this, this.getClass().getName());
	}


	protected void initActions() {
	}

	@Override
	public Iterator<Entry<String, Action>> getActions() {
		return actions.entrySet().iterator();
	}

	@Override
	public boolean hasUnsavChanged() {
		return isChanged;
	}

	@Override
	public void save() {
	}

	@Override
	public void setUnsaveChanged(boolean b) {
		boolean oldValue=isChanged;
		isChanged=b;
		firePropertyChange(ApplicationConstant.PROPERTY_SAVE, oldValue, isChanged);
	}
	
	
	@Override
	public void create() {
		System.out.println("AbsView.create()");
	}
	
	@Override
	public JToolBar getToolbar() {
		JToolBar toolBar=new JToolBar();
		for(Iterator<Entry<String, Action>> i=getActions();i.hasNext();){
			Entry<String, Action> entry=i.next();
			toolBar.add(entry.getValue());
		}
		
		return toolBar;
	}
	
	@Override
	public JMenuBar createMenus() {
		return null;
	}
	
	
	@Override
	public void close() {
		// save the preferences.
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void undo() {
		throw new UnsupportedClassVersionError();
	}
	
	@Override
	public boolean canRedo() {
		return false;
	}
	
	@Override
	public void redo() {
		throw new UnsupportedClassVersionError();
	}
	
    @Override
    public Image getIcon() {
    	SwingResources r = app.getResource();
		Image image =r.getImage(getClass().getName());
		image = image == null ? r.getImage("system"):image;
    	return image;
    }
    
    
    @Override
    public void exit() {
    }

}
