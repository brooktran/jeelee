/* @(#)DefaultModel.java 1.0 2009-11-18
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
package org.jtool.app;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JToolBar;

import org.jtool.app.action.ExitAction;
import org.jtool.sample.binaryEdit.BEViewAction;
import org.jtool.sample.job.JLViewAction;
import org.jtool.sample.translate.SXViewAction;
import org.jtool.utils.ResourceUtil;

/**
 * <B>DefaultModel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-18 created
 * @since JTool Ver 1.0
 * 
 */
public class DefaultModel extends AbsModel {

    private static final String Labels = "org.jtool.app.Labels";

    /**
     * @see com.map.app.AbsModel#createToolbars(com.map.app.IApplication, com.map.app.IView)
     */
    public List<JToolBar> createToolbars(IApplication app, IView view) {
        List<JToolBar> toolbarList = new ArrayList<JToolBar>(0);
        return toolbarList;
    }

    /**
     * @see com.map.app.IModel#createMenu()
     */
    public List<JMenu> createMenu() {
        ResourceUtil resource = ResourceUtil.getResourceBundleUtil(Labels);
        List<JMenu> list = new ArrayList<JMenu>();

        JMenu editMenu = new JMenu();
        resource.configMenu(editMenu, "edit");

        //		editMenu.add(getAction(UndoAction.ID));
        //		editMenu.add(getAction(RedoAction.ID));
        editMenu.addSeparator();
        //		editMenu.add(getAction(CopyAction.ID));
        //		editMenu.add(getAction(CutAction.ID));
        //		editMenu.add(getAction(PasteAction.ID));
        list.add(editMenu);



        JMenu toolMenu = new JMenu();
        resource.configMenu(toolMenu, "tool");
        toolMenu.add(getAction(BEViewAction.ID));
        toolMenu.add(getAction(JLViewAction.ID));
         toolMenu.add(getAction(SXViewAction.ID));
        //		mapMenu.add(getAction(NewLayerAction.ID));

        list.add(toolMenu);

        return list;
    }

    /**
     * @see com.map.app.AbsModel#initActions()
     */
    @Override
    protected void initActions() {
        putAction(ExitAction.ID, new ExitAction(app));
        putAction(BEViewAction.ID, new BEViewAction(app));
        putAction(JLViewAction.ID, new JLViewAction(app));
        putAction(SXViewAction.ID, new SXViewAction(app));
        //		putAction(JLOpenAction.ID, new JLOpenAction(app));
    }
}
