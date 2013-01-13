package org.zhiwu.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JToolBar;
import javax.swing.event.EventListenerList;

import org.zhiwu.app.action.ConfigAction;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.SwingResources;

/**
 * <B>AbsModel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-28 created
 * @since org.zhiwu.app Ver 1.0
 * 
 */
public class AbsModel implements Model {

	protected String name;
	protected String copyright;
	protected String version;
	protected View view;
	private final Map<String, Action> actions;

	/**
	 * store the views's class path. To use the this variable, see
	 * {@link AbsModel#addViewClass(String)}
	 */
	private final List<String> viewsName;

	/** A list of event listeners for this component. */
	protected EventListenerList listenerList = new EventListenerList();
	protected Application app;

	private final List<Object> userObjects = new LinkedList<Object>();

	public AbsModel() {
		viewsName = new ArrayList<String>();
		actions = new HashMap<String, Action>();
	}

	public void initView() {
	}

	@Override
	public void init(Application app) {
		setApplication(app);
		initActions();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String getCopyright() {
		return copyright;
	}

	@Override
	public void setCopyright(String copyright) {
		// System.out.println("AbsModel setCopyright");
		this.copyright = copyright;
	}

	@Override
	public String getViewName() {
		// System.out.println("AbsModel getViewName");
		return viewsName.get(0);
	}

	@Override
	public void setView(View newValue) {
		this.view = newValue;
	}

	/**
	 * add the view name. Note: the model use a array list to save the views's
	 * name. when adds a new view to the model, the name was add in the index of
	 * 0.
	 * 
	 * @see org.zhiwu.app.IModel#addViewClass(java.lang.String)
	 */
	@Override
	public void addView(String viewName) {
		viewsName.add(0, viewName);
	}
	

	@Override
	public View createView() {
		if (view == null && viewsName.size() > 0) {
			try {
				view = (View) Class.forName(viewsName.get(0)).newInstance();
			} catch (Exception e) {
				AppLogging.handleException(e);
			}
		}
		return view;
	}

	@Override
	public View getView() {
		return view;
	}

	@Override
	public void putAction(String ID, Action action) {
		if (action == null) {
			actions.remove(ID);
			return;
		}

		actions.put(ID, action);
	}

	@Override
	public Action getAction(String ID) {
		return actions.get(ID);
	}

	@Override
	public void setApplication(Application app) {
		this.app = app;
	}

	@Override
	public Application getApplication() {
		return app;
	}

	@Override
	public void initView(View view) {
	}

	@Override
	public List<JToolBar> createToolbars(Application app, View view) {
		List<JToolBar> toolbarList = new ArrayList<JToolBar>();
		toolbarList.add(view.getToolbar());
		addTool(toolbarList, actions.entrySet().iterator());
		return toolbarList;
	}

	private void addTool(List<JToolBar> toolList,
			Iterator<Entry<String, Action>> i) {
		JToolBar toolBar = new JToolBar();
		for (; i.hasNext();) {
			Entry<String, Action> entry = i.next();
			toolBar.add(entry.getValue());
		}
		toolList.add(toolBar);
	}

	@Override
	public List<JMenu> createMenu() {
		List<JMenu> list = new ArrayList<JMenu>();
		SwingResources resource = app.getResource();

		JMenu viewMenu = new JMenu();
		resource.configMenu(viewMenu, app.getView().getTitle());
		for (Iterator<Entry<String, Action>> iterator = view.getActions(); iterator
				.hasNext();) {
			Entry<String, Action> e = iterator.next();
			viewMenu.add(e.getValue());
		}
		list.add(viewMenu);

		return list;
	}

	/**
	 * init actions. Note: actions initial in {@link IView#initActions
	 * IViews.initActions } and Model.initAction. model.initAction hold actions
	 * for all view. init actions before the view was create.
	 */
	protected void initActions() {
//		putAction(ExitAction.ID, new ExitAction(app));
		// putAction(AboutAction.ID, new AboutAction(app));
		// putAction(PreferencesAction.ID, new PreferencesAction(app));
		putAction(ConfigAction.ID, new ConfigAction(app));

	}

	protected void removeAction(String id) {
		actions.remove(id);
	}

	@Override
	public Iterator<String> viewIterator() {
		return viewsName.iterator();
	}

	@Override
	public Object getUserObject(Class<? extends Object> c) {
		for (int i = 0, j = userObjects.size(); i < j; i++) {
			if (userObjects.get(i).getClass().equals(c)) {
				return userObjects.get(i);
			}
		}

		return null;
	}

	@Override
	public void addUserObject(Object o) {
		for (int i = 0, j = userObjects.size(); i < j; i++) {
			if (userObjects.get(i).getClass().equals(o.getClass())) {
				throw new IllegalArgumentException("class already exist...");
			}
		}

		userObjects.add(o);
	}

	

}
