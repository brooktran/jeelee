/* TerrainAction.java 1.0 2010-2-2
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

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.mepper.app.MapApplication;
import org.mepper.editor.Editor;
import org.mepper.editor.EditorView;
import org.mepper.editor.map.Layer;
import org.mepper.editor.map.Map;
import org.mepper.editor.tile.terrain.DavidTransitor;
import org.mepper.editor.tile.terrain.TerrainTransitor;
import org.mepper.resources.LibraryResource;
import org.mepper.resources.ProjectManager;
import org.mepper.resources.ProjectResource;
import org.mepper.resources.StorableResource;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.utils.AppLogging;

/**
 * <B>TerrainAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-26 created
 * @since org.mepper.app.action Ver 1.0
 * 
 */
public class TerrainAction extends AppAction{
	public static final String ID="terrain.handle";
	private final Editor editor ;
	private final ProjectManager projectManager;

	public TerrainAction(Application app,Editor editor,ProjectManager projectManager) {
		super(app,ID);
		this.editor =editor;
		this.projectManager = projectManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		
		EditorView v=  editor.getActivateView();
		if(v == null){
			return;
		}
		
		ProjectResource project= projectManager.getCurrentProject();
		if(project == null){
			JOptionPane.showMessageDialog(app.getView().getComponenet(), 
					AppManager.getResources().getString("please.select.a","project"));
			return;
		}
		
		Map map =v.getMap();
		int index = map.getSelectedIndex();
		if(index == -1){
			JOptionPane.showMessageDialog(app.getView().getComponenet(), 
					AppManager.getResources().getString("please.select.a","layer"));
			return;
		}
		Layer l = map.getLayer(index);
		
		TerrainTransitor t=getTransitor();
		t.setLayer(l);
		StorableResource lib = project.getChildByID(MepperConstant.LIBRARY_SET_ID);
		t.addLibrary((LibraryResource) lib);
//		for(int i=0,j=lib.getChildCount();i<j;i++){
//			t.addLibrary((LibraryResource)lib.getChild(i)) ;
//		}
		
		int p = map.getLayer(DavidTransitor.LAYER_NAME);
		if(p!= -1){
			map.removeLayer(p);
			p--;
		}
		map.addLayer(t.transition(), index+1);
	}

	private TerrainTransitor getTransitor() {
		AppPreference pref = AppManager.getPreference(MapApplication.class.getName());
		String transitorName = pref.get("terrain.transitor");
		try {
			TerrainTransitor transitor = (TerrainTransitor) Class.forName(transitorName).newInstance();
			return transitor;
		} catch (Exception e) {
			AppLogging.handleException(e);
			return null;
		} 
	}
	
//	@Override
//	public String getID() {
//		return ID;
//	}

}
