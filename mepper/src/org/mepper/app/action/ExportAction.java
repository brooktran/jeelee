/* ExportAction.java 1.0 2010-2-2
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;

import org.mepper.editor.map.Map;
import org.mepper.io.MapIOHelper;
import org.mepper.io.MapWriter;
import org.mepper.io.Storable;
import org.mepper.resources.ProjectManager;
import org.zhiwu.app.Application;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.FileUtils;

/** 
 * <B>ExportAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-26 created
 * @since org.mepper.app.action Ver 1.0
 * 
 */
public class ExportAction extends MapEditorAction{
	public final static String ID="export";
	private final ProjectManager manager;
	public ExportAction(Application app,ProjectManager manager) {
		super(app,ID);
		this.manager = manager;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Storable s = manager.getCurrentNode().getSource();
		if(!(s instanceof Map)){
			return;//TODO show message to user.
		}
		Map map = (Map) s;
		
		MapWriter writer = MapIOHelper.getWriter();
		File  f=FileUtils.saveAs(getView().getComponenet(),JFileChooser.FILES_ONLY ,writer.getFileFilters());
		if(f == null){
			return;
		}
		try {
			writer.writeMap(map, new FileOutputStream(f));
		} catch (IOException e1) {
			AppLogging.handleException(e1);
		} 
	}
	
//	@Override
//	public String getID() {
//		return ID;
//	}

}
