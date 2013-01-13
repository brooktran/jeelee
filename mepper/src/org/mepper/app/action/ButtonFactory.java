/* ButtonFactory.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.mepper.app.action;

import java.awt.AWTEvent;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JComponent;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import org.mepper.app.MapApplication;
import org.mepper.editor.Editor;
import org.mepper.resources.EdgeEditorView;
import org.mepper.resources.gui.TileEditorView;
import org.mepper.tool.HandTool;
import org.mepper.tool.MapTileSelection;
import org.mepper.tool.PaddingTool;
import org.mepper.tool.SelectionTool;
import org.mepper.tool.TileCreationTool;
import org.mepper.tool.Tool;
import org.mepper.tool.ZoomTool;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.Application;
import org.zhiwu.app.Model;
import org.zhiwu.app.View;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.AppResources;

/**
 * <B>ButtonFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-12-20 created
 * @since org.map.editor.action Ver 1.0
 * 
 */
public class ButtonFactory {


	public static List<JToolBar> createToolBar(Application app, View view) {
			return createMapViewBar(app,view);
	}

	private static List<JToolBar> createMapViewBar(Application app, View view) {
		JToolBar editBar = new JToolBar();
//		Model m=app.getModel();

		//		if(null!=(action =getAction(NewMapAction.ID))){
		//			button = editBar.add(action);
		//			button.setFocusable(false);
		//		}
//		editBar.add(view.getAction(NewMapAction.ID));
//		editBar.addSeparator();
//		editBar.add(view.getAction(UndoAction.ID));
//		editBar.add(view.getAction(RedoAction.ID));

//		JToolBar mapEditBar=new JToolBar();
//		mapEditBar.add(m.getAction(FillAction.ID));
//		mapEditBar.add(m.getAction(PenAction.ID));
//		mapEditBar.add(m.getAction(TileManagerAction.ID));

		List<JToolBar> list=new ArrayList<JToolBar>();
		list.add(editBar);
//		list.add(mapEditBar);
		return list;
	}

	public static List<JToolBar> createToolBar(MapApplication app) {
		JToolBar editBar = new JToolBar();
		Model m=app.getModel();

//		editBar.add(m.getAction(CutAction.ID));
//		editBar.add(m.getAction(CopyAction.ID));
//		editBar.add(m.getAction(PasteAction.ID));
//		
//		editBar.addSeparator();
//		editBar.add(m.getAction(OpenAction.ID));
//		editBar.add(m.getAction(SaveAction.ID));
//		editBar.add(m.getAction(ConfigAction.ID));
		editBar.add(m.getAction(ExitAction.ID));
		//// end test
		
		
		List<JToolBar> list=new ArrayList<JToolBar>();
		list.add(editBar);
		return list;
	}


	@SuppressWarnings("unchecked")
	public static void addToolbarToMapEditor(Editor e, JComponent v) {
		addToolbarTo(e,e.getClass().getName(), v, new Class[]{
				SelectionTool.class,
				PaddingTool.class,
				MapTileSelection.class,
				ZoomTool.class,
//				WalkableTool.class,
//				PenTool.class,
				HandTool.class,
//				PaletteTool.class
		});
	}
	
	@SuppressWarnings("unchecked")
	public static void addToolbarToTileEditor(Editor e, JComponent v) {
		addToolbarTo(e,TileEditorView.class.getName(), v, new Class[]{
//				TileSelectionTool.class,
				TileCreationTool.class,
//				ZoomTool.class,
//				WalkableTool.class,
//				PenTool.class,
				HandTool.class,
//				PaletteTool.class
		});
		addToolbarTo(e,EdgeEditorView.class.getName(), v, new Class[]{
			SelectionTool.class,
			PaddingTool.class,
			MapTileSelection.class,
			ZoomTool.class,
//			WalkableTool.class,
//			PenTool.class,
			HandTool.class,
//			PaletteTool.class
	});
	}
	
	private static void addToolbarTo(Editor e, String toolbarName,JComponent v, Class<? extends Tool>[] classes) {
		JToolBar toolbar= getClientToolbar(v,toolbarName,1);
		JToolBar actionBar=getOptionComponentContainerBar(v);
		ButtonGroup group =new ButtonGroup();
		Tool t;
		final Map<Character, JToggleButton> toolMap=new HashMap<Character, JToggleButton>(8);
		
		for(Class<? extends Tool> c:classes){
			try {
				t=c.newInstance();
				addToolTo(t,t.getID(),e,v,toolbar,actionBar,group ,toolMap);
			} catch (InstantiationException e1) {
				AppLogging.handleException(e1);
			} catch (IllegalAccessException e1) {
				AppLogging.handleException(e1);
			}
		}
		
		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {

			@Override
			public void eventDispatched(AWTEvent event) {
				if(!(event instanceof KeyEvent)){
					return;
				}
				KeyEvent keyEvent= (KeyEvent) event;
				if(keyEvent.getID() != KeyEvent.KEY_RELEASED){
					return;
				}
				JToggleButton button=toolMap.get(keyEvent.getKeyChar());
				if(button !=null){
					button.setSelected(true);
				}
			}
		}, AWTEvent.KEY_EVENT_MASK);
		
		
	}
	
	
	
	private static JToolBar getOptionComponentContainerBar(JComponent v) {
		return getClientToolbar(v, "view.toolbar",0);
	}

//	private static JToolBar getToolbar(Editor e, JComponent v) {
//		return getClientToolbar(v, e.getClass().getName(),1);
//	}
	private  static  JToolBar getClientToolbar(JComponent v,String name,int orientation){
		JToolBar toolbar;
		if (v.getClientProperty(name) instanceof JToolBar) {
			toolbar = (JToolBar) v.getClientProperty(name);
		} else {
			toolbar = new JToolBar(orientation);
			toolbar.setRollover(true);
			v.putClientProperty(name, toolbar);
		}
		return toolbar;
	}

	private static void addToolTo(Tool tool,String toolLabel,Editor e,
			JComponent component, JToolBar toolbar, 
			JToolBar optionComponentContainerBar,ButtonGroup group, Map<Character, JToggleButton> toolMap) {
		AppResources r=AppManager.getResources();

		JToggleButton t =new JToggleButton();
		r.configToolbarButton(t,toolLabel);
		ToolButtonListener tbListener=new ToolButtonListener(tool,optionComponentContainerBar,e);
		t.addItemListener(tbListener);
		toolMap.put(tool.getAccelerator(), t);
		group.add(t);
		toolbar.add(t);
	}

	

}
class ToolButtonListener implements ItemListener{
	private final Tool tool;
	private final Editor editor;
	private final JToolBar optionComponentContainerBar;
	public ToolButtonListener(Tool t,JToolBar actionBar,Editor e){
		this.tool=t;
		this.editor=e;
		this.optionComponentContainerBar=actionBar;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.SELECTED) {
			JComponent c;
			Tool t=editor.getTool();
			if(t!=null){
				optionComponentContainerBar.remove(t.getOptionComponent());
//				t.removeOptionComponent(actionBar);
			}
			
            editor.setTool(tool);
            tool.setEditor(editor);
            c=tool.getOptionComponent();
			if (c != null) {
				optionComponentContainerBar.add(tool.getOptionComponent());
			}
			optionComponentContainerBar.validate();
			optionComponentContainerBar.repaint();
		}
	}
	
}
