/* @(#)JLModel.java 1.0 2009-11-10
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.jtool.sample.job;

import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JToolBar;

import org.jtool.app.AbsModel;
import org.jtool.app.IApplication;
import org.jtool.app.IView;
import org.jtool.app.action.ExitAction;
import org.jtool.utils.ResourceUtil;

/**
 * <B>JLModel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-10 created
 * @since JobLister Ver 1.0
 * 
 */
public class JLModel  extends AbsModel{
	private static final String Labels = "org.jtool.app.Labels";

	/** 
	 * @see com.map.app.AbsModel#createToolbars(com.map.app.IApplication, com.map.app.IView)
	 */
	public List<JToolBar> createToolbars(IApplication app, IView view) {
		JToolBar editBar = new JToolBar();
		Action action;
		JButton button;

		//		if(null!=(action =getAction(NewMapAction.ID))){
		//			button = editBar.add(action);
		//			button.setFocusable(false);
		//		}
		//		editBar.add(getAction(NewMapAction.ID));
		//		editBar.addSeparator();
		//		editBar.add(getAction(UndoAction.ID));
		//		editBar.add(getAction(RedoAction.ID));
		//		editBar.add(getAction(CutAction.ID));
		//		editBar.add(getAction(CopyAction.ID));
		//		editBar.add(getAction(PasteAction.ID));

		JToolBar mapEditBar=new JToolBar();
		//		mapEditBar.add(getAction(FillAction.ID));
		//		mapEditBar.add(getAction(PenAction.ID));

		List<JToolBar> list=new ArrayList<JToolBar>();
		list.add(editBar);
		list.add(mapEditBar);
		return list;
	}

	/** 
	 * @see com.map.app.IModel#createMap(com.map.app.IMap)
	 */
	//	public void setEditor(IMap map) {
	////		IEditor editor=new Editor(map);
	//		//editor.init();
	//		view.setEditor(new Editor(map));
	//		//		editor.init();
	//		//		view.setMap(map);
	//	}

	/** 
	 * @see com.map.app.IModel#createMenu()
	 */
	public List<JMenu> createMenu() {
		ResourceUtil resource = ResourceUtil.getResourceBundleUtil(Labels);
		List<JMenu> list=new ArrayList<JMenu>();

		JMenu editMenu = new JMenu();
		resource.configMenu(editMenu, "edit");
		//		editMenu.add(getAction(UndoAction.ID));
		//		editMenu.add(getAction(RedoAction.ID));
		editMenu.addSeparator();
		//		editMenu.add(getAction(CopyAction.ID));
		//		editMenu.add(getAction(CutAction.ID));
		//		editMenu.add(getAction(PasteAction.ID));
		list.add(editMenu);



		JMenu mapMenu = new JMenu();
		resource.configMenu(mapMenu, "map");
		//		mapMenu.add(getAction(NewLayerAction.ID));

		list.add(mapMenu);

		return list;
	}



	/** 
	 * @see com.map.app.IModel#setEditor(com.map.app.IMap)
	 */
	//	public void setEditor(IMap data) {
	//		// TODO Auto-generated method stub
	//		throw new UnsupportedOperationException();
	//	}

	/** 
	 * @see com.map.app.AbsModel#initActions()
	 */
	@Override
	protected void initActions() {
		putAction(ExitAction.ID, new ExitAction(app));
		//		putAction(JLOpenAction.ID, new JLOpenAction(app));
	}
}
