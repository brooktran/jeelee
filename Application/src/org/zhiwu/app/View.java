package org.zhiwu.app;

import java.awt.Image;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JMenuBar;
import javax.swing.JToolBar;


/**
 * <B>View</B>.
 * @author   Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version   Ver 1.0.01 2009-9-16 created
 * @since Application Ver 1.0
 */
public interface View {
	public void addComponent(View component);
	public Action getAction(String ID);
	public JComponent getComponenet();

	public String getTitle();
	public void setTitle(String title);
	
	public void init();
	public void start();
	public void exit();
	public void create();
	public void close();
	public void setEnabled(boolean b);
	
	public boolean isShowing();
	public void setShowing(boolean b);
	public void show();
	
	public Application getApplication();
	public void initApplication(Application app);
	public void putAction(String ID, Action action);
	public Iterator<Entry<String, Action>> getActions();

	
	public JToolBar getToolbar();
	public JMenuBar createMenus();

	public Image getIcon();
	
	
	///begin: editable
	public boolean hasUnsavChanged();
	public void save();
	public void setUnsaveChanged(boolean b);

	public boolean canUndo();
	public void undo();
	public boolean canRedo();
	public void redo();
	///end: editable
}
