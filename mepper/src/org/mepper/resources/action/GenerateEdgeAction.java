/* GeneralEdgeAction.java 1.0 2010-2-2
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
package org.mepper.resources.action;

import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;

import org.mepper.editor.MapEditorView;
import org.mepper.resources.EdgeEditorView;
import org.mepper.resources.EdgeGenerator;
import org.mepper.resources.NameEdgeGenerator;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.AppResources;

/**
 * <B>GenerateEdgeAction</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-25 created
 * @since mepper Ver 1.0
 * 
 */
public class GenerateEdgeAction extends AppAction{
	public static final String ID = "generate.edge";
	private EdgeEditorView view ;
	
	public GenerateEdgeAction(Application app) {
		super(app, ID);
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		//TODO add a dialog to select the occupie of tile and edge.
		AppResources r=AppManager.getResources();
		try {
			EdgeGenerator edge = new NameEdgeGenerator();
			edge.setEdgeView(view);
			edge.generate();
		} catch (NullPointerException ne) {
			if (ne.getMessage()!=null &&ne.getMessage().equals("main.tile")) {
				JOptionPane.showMessageDialog(view.getComponent(),
						r.getString("main", "tile", "not.found"),
						r.getString("error"), JOptionPane.ERROR_MESSAGE);

				return;
			}
			AppLogging.handleException(ne);
		} catch (IllegalStateException ie) {
			JOptionPane.showMessageDialog(view.getComponent(),	r.getString("main","tile","property",":","priority","not.found"), 
					r.getString("error"), JOptionPane.ERROR_MESSAGE);
		}
		
	}

	public MapEditorView getView() {
		return view;
	}

	public void setView(EdgeEditorView view) {
		this.view = view;
	}

}
