package org.jtool.app;

import java.awt.Container;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.SwingUtilities;

import org.jtool.beans.AbstractBean;
import org.jtool.utils.ResourceUtil;

/**
 * <B>AbsApplicaton</B>.
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-9-16 created
 * @since map editor Ver 1.0
 */
public abstract class AbsApplicaton extends AbstractBean implements
IApplication {

	private static final long serialVersionUID = 1L;
	protected List<File> recentFiles = new LinkedList<File>();// 最近打开路径
	protected Preferences prefs;
	protected static final String Labels = "org.jtool.app.Labels";
	protected static final String RecentFileProperty="RecentFiles";
	private static final int MAX_RESENT_FILE_SIZE;

	/** The model. */
	private IModel model;

	private boolean isEnable;

	private List<IView> views; // List<IView>


	static{
		ResourceUtil ru=ResourceUtil.getResourceBundleUtil(Labels);
		MAX_RESENT_FILE_SIZE=ru.getInt("MAX_RESENT_FILE_SIZE");
	}

	/**
	 * Instantiates a new applicaton.
	 * 
	 * @param model
	 *            the model
	 */
	public AbsApplicaton() {
		System.out.println("AbsApplicaton ");
		views = new ArrayList<IView>();
	}

	/**
	 * Launch the application.
	 * 
	 * @see com.map.app.IApplication#launch()
	 */
	public void launch() {
		System.out.println("AbsApplicaton launch");
		init();
		start();
	}

	/**
	 * Inits the.
	 * 
	 * @see com.map.app.IApplication#init()
	 */
	public void init() {
		System.out.println("AbsApplicaton init");

		// createMainPanel();

		// 获取最近打开文件
		// init recent files
		prefs = Preferences.userNodeForPackage(getClass());//HKEY_CURRENT_USER\Software\JavaSoft\Prefs
		int count = prefs.getInt("recentFileCount", 0);// 大写字母在注册表中前面加/,如 "F" 表示为 "/F"
		if (count == 0) {
			prefs.putInt("recentFileCount", 10);
		}
		for (int i = 0; i < count; i++) {
			String pathString = prefs.get(RecentFileProperty+"." + i, null);
			if (pathString != null) {
				recentFiles.add(new File(pathString));
			}
		}

		// inits model
		model.init(this);
	}

	public void start() {
		System.out.println("AbsApplicaton start");
		final IView view = createView();
		setupView(view);
	}

	/**
	 * 
	 * @return
	 */
	private IView createView() {
		System.out.println("AbsApplicaton createView");
		IView view = model.createView();
		view.initApplication(this);
		view.init();
		return view;
	}

	/**
	 * @see org.jtool.app.IApplication#setView(org.jtool.app.IView)
	 */
	public void setupView(IView view) {// TODO how to use the old view
		System.out.println("AbsApplicaton setupView");
		addView(view);
		setEnable(false);
		showView(view);
		setEnable(true);
		//		
		//		setEnable(false);
		//		model.setView(view);
		//		view.initApplication(this);
		//		view.init();
		//		showView(view);
		//		setEnable(true);
	}

	/**
	 * @see com.map.app.IApplication#addView()
	 */
	public void addView(final IView view) {// TODO
		System.out.println("AbsApplicaton addView");
		views.add(view);
	}

	/**
	 * You can showing only one view at the same time.
	 * 
	 * @see com.map.app.IApplication#show(com.map.app.IView)
	 */
	public void showView(IView view) {
		System.out.println("AbsApplicaton showView");
		if (view.isShowing()) {
			System.out.println("AbsApplicaton view is showing:"+view.getViewName());
			return;
		}

		for (IView v : views) {
			if (v != view && v.isShowing()) {
				hideView(v);
				//				views.remove(v);
			}
		}
		getContainer().add(view.getComponenet());
		view.getComponenet().requestFocusInWindow();
		view.start();
		getContainer().repaint();
	}

	/**
	 * @see com.map.app.IApplication#hideView(com.map.app.IView)
	 */
	public void hideView(IView view) {
		System.out.println("AbsApplicaton hideView");
		System.out.println("  " + view.getViewName() + "  " + view.isShowing());
		if (view.isShowing()) {
			view.setVisible(false);
			// get fram or window
			Container container = SwingUtilities.getRootPane(
					view.getComponenet()).getParent();
			container.setVisible(false);
			container.remove(view.getComponenet());
			container.repaint();
			container.setVisible(true);
		}

	}

	public IView getView() {
		return model.getView();
	}

	/**
	 * Sets the model.
	 * 
	 * @param model
	 *            the model
	 * 
	 * @see com.map.app.IApplication#setModel()
	 */
	public void setModel(IModel model) {
		System.out.println("AbsApplicaton setModel");
		if (model == null) {
			throw new IllegalArgumentException("model cann't be null");
		}
		this.model = model;
	}

	/**
	 * Gets the model.
	 * 
	 * @return the model
	 * 
	 * @see com.map.app.IApplication#getModel()
	 */
	public IModel getModel() {
		System.out.println("AbsApplicaton getModel");
		return model;
	}

	/**
	 * @param mainPanel
	 *            the mainPanel to set
	 */
	public abstract void setContainer(Container newValue);

	/**
	 * @return the mainPanel
	 */
	public abstract Container getContainer();

	/**
	 * @see com.map.app.IApplication#getName()
	 */
	public String getName() {
		System.out.println("AbsApplicaton getName");
		return model.getName();
	}

	/**
	 * @see com.map.app.IApplication#stop()
	 */
	public void stop() {
		// for (View p : new LinkedList<View>(views())) {
		// dispose(p);
		// }
	}

	/**
	 * @see com.map.app.IApplication#getVersion()
	 */
	public String getVersion() {
		return model.getVersion();
	}

	/**
	 * @see com.map.app.IApplication#isEnable()
	 */
	public boolean isEnable() {
		return isEnable;
	}

	/**
	 * @see com.map.app.IApplication#setEnable(boolean)
	 */
	public void setEnable(boolean b) {
		if (isEnable == b) {
			return;
		}

		for (IView view : new LinkedList<IView>(views)) {
			view.setEnabled(b);
		}
		getWindow().setEnabled(b);
		isEnable = b;
	}

	/** 
	 * @see org.jtool.app.IApplication#addRecentFile(java.io.File)
	 */
	public void addRecentFile(File file) {
		java.util.List<File> oldValue = new LinkedList<File>( recentFiles);
		if (recentFiles.contains(file)) {
			recentFiles.remove(file);
		}
		recentFiles.add(0,file);
		if (recentFiles.size() > MAX_RESENT_FILE_SIZE) {//TODO maxRecentFilesCount
			recentFiles.remove(recentFiles.size()-1);
		}

		//		prefs.putInt(getView().getViewName()+RecentFileProperty, recentFiles.size());
		prefs.putInt(RecentFileProperty, recentFiles.size());
		int i=0;
		for (File f : recentFiles) {
			prefs.put(RecentFileProperty+"."+i, f.getPath());
			i++;
		}

		firePropertyChange(RecentFileProperty, oldValue, 0);
		firePropertyChange(RecentFileProperty,
				Collections.unmodifiableList(oldValue),
				Collections.unmodifiableList(recentFiles)
		);

	}

}
