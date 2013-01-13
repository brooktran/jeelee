package org.jtool.app;

import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;


/**
 * <B>Model</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-16 created
 * @since map editor Ver 1.0
 * 
 */
public abstract class AbsModel implements IModel{

	protected String name;
	protected String copyright;
	protected String version;

	private String viewName;
	protected IView view;
	private Map<String, Action> actions;


	protected IApplication app;

	public AbsModel(){
		System.out.println("AbsModel ");
		actions=new HashMap<String, Action>();
	}

	/** 
	 * @see com.map.app.IModel#initView()
	 */
	public void initView() {
		System.out.println("AbsModel initView");
	}

	/** 
	 * @see com.map.app.IModel#init()
	 */
	public void init(IApplication app) { // init action
		System.out.println("AbsModel init");

		setApplication(app);

		initActions();
	}

	/**
	 * 
	 */
	protected abstract void initActions() ;

	/** 
	 * @see com.map.app.IModel#getName()
	 */
	public String getName() {
		System.out.println("AbsModel getName");
		return name;
	}

	/** 
	 * @see com.map.app.IModel#setName()
	 */
	public void setName(String name) {
		System.out.println("AbsModel setName");
		this.name=name;
	}


	/** 
	 * @see com.map.app.IModel#getVersion()
	 */
	public String getVersion() {
		System.out.println("AbsModel getVersion");
		return version;
	}


	/** 
	 * @see com.map.app.IModel#setVersion(java.lang.String)
	 */
	public void setVersion(String version) {
		System.out.println("AbsModel setVersion");
		this.version=version;
	}


	/** 
	 * @see com.map.app.IModel#getCopyright()
	 */
	public String getCopyright() {
		System.out.println("AbsModel getCopyright");
		return copyright;
	}

	/** 
	 * @see com.map.app.IModel#setCopyright(java.lang.String)
	 */
	public void setCopyright(String copyright) {
		System.out.println("AbsModel setCopyright");
		this.copyright = copyright;
	}




	/** 
	 * @see com.map.app.IModel#getViewClass()
	 */
	public String getViewName() {
		System.out.println("AbsModel getViewName");
		return viewName;
	}


	/** 
	 * @see com.map.app.IModel#setView(com.map.app.IView)
	 */
	public void setView(IView view) {
		System.out.println("AbsModel setView");
		this.view=view;
	}

	/** 
	 * @see com.map.app.IModel#setViewClass(java.lang.String)
	 */
	public void setViewName(String viewName) {
		System.out.println("AbsModel setViewName");
		this.viewName=viewName;
	}

	/** 
	 * @see com.map.app.IModel#createView()
	 */
	public IView createView() {
		System.out.println("AbsModel createView");
		if(view == null){
			if (viewName != null) {
				try {
					view=(IView) Class.forName(viewName).newInstance();
				} catch (Exception e) {
					//throw new MapException(e);
					e.printStackTrace();
				}
			}
		}
		return view;
	}

	/** 
	 * @see com.map.app.IModel#getView()
	 */
	public IView getView() {
		return view;
	}

	/** 
	 * @see com.map.app.IModel#putAction(java.lang.String, javax.swing.Action)
	 */
	public void putAction(String ID, Action action) {
		System.out.println("AbsModel putAction");
		if (action == null) {
			actions.remove(ID);
			return;
		}

		actions.put(ID, action);
	}

	/** 
	 * @see com.map.app.IModel#getAction(java.lang.String)
	 */
	public Action getAction(String ID) {
		System.out.println("AbsModel getAction");
		return actions.get(ID);
	}

	/** 
	 * @see com.map.app.IModel#setApplication()
	 */
	public void setApplication(IApplication app) {
		this.app=app;
	}

	/** 
	 * @see com.map.app.IModel#getApplication()
	 */
	public IApplication getApplication() {
		return app;
	}
}
