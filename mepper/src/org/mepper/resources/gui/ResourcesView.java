/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ResourcesView.java
 *
 * Created on 2011-4-8, 15:26:57
 */

package org.mepper.resources.gui;
 
import java.awt.BorderLayout;
import java.awt.Point;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import org.mepper.app.action.ButtonFactory;
import org.mepper.app.action.HelpAction;
import org.mepper.app.action.ShowCoordinateAction;
import org.mepper.app.action.ShowGridAction;
import org.mepper.editor.DefaultEditor;
import org.mepper.editor.EditorAdapter;
import org.mepper.editor.EditorEvent;
import org.mepper.editor.EditorListener;
import org.mepper.editor.EditorView;
import org.mepper.gui.AbstractEditableView;
import org.mepper.gui.ClosableTabbedPane;
import org.mepper.io.Storable;
import org.mepper.resources.EdgeEditorView;
import org.mepper.resources.action.GenerateEdgeAction;
import org.mepper.resources.action.NewCustomTileAction;
import org.mepper.resources.action.NewLibraryAction;
import org.mepper.resources.action.OpenImageAction;
import org.mepper.tool.Tool;
import org.mepper.tool.ToolEvent;
import org.mepper.tool.ToolListener;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.Model;
import org.zhiwu.app.action.AboutAction;
import org.zhiwu.app.action.ConfigAction;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.app.action.SaveAction;
import org.zhiwu.app.action.VersionAction;
import org.zhiwu.utils.AppResources;


/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class ResourcesView extends AbstractEditableView {
private JToolBar currentToolbar;
	
	private final PropertyChangeListener messageListener=new PropertyChangeListener() {
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("message")) {
				messager.setText(evt.getNewValue().toString());
			}
		}
	};
	
	private final PropertyChangeListener selectionListener=new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals(ImportImagePanel.SELECTION_CHANGED_PROPERTY)) {
//				Map map=(Map) evt.getNewValue();
//				Tile tile= map.getLayer(0).getTileAt(0,0);
//				EditorView view=new TileEditorView(map,tile,libraryPanel1.getManager());
//				editor.add(view);
//				closableTabbedPane1.add(map.getName(), view.getComponent());
				updateAction();
				updateToolbar();
			}
		}
	};
	
	private final ToolListener toolListener=new ToolListener() {
		@Override
		public void toolDone(ToolEvent e) {
		}
		
		@Override
		public void toolChanged(ToolEvent e) {
			StringBuilder sb= new StringBuilder();
			Point p;
			
			sb.append("[");
			p=e.getMapPoint();
			sb.append(p.x+","+p.y);
			
			sb.append("],\t[");
			p=e.getScreenPoint();
			sb.append(p.x+","+p.y+"]");
			
			messager.setText(sb.toString());
		}
	};
	
	private final EditorListener editorListener=new EditorAdapter() {
		@Override
		public void toolChanged(org.mepper.editor.EditorEvent evt) {
			Tool tool;
			tool = evt.getOldTool();
			if(tool !=null){
				tool.removeToolListener(toolListener);
			}
			
			tool=evt.getNewTool();
			tool.addToolListener(toolListener);
		}
		
		@Override
		public void viewChanged(EditorEvent e) {
			updateAction();
			updateToolbar();
		}
		
		@Override
		public void userObjectChanged(EditorEvent e) {
			Object obj = editor.getUserobject(MepperConstant.EDITOR_USER_OBJECT);
//			if(obj instanceof Storable){
				propertyPanelProxy.setSource((Storable)obj);
				repaint();
//			}
		}

	};
	
 
    public ResourcesView() {
        initComponents();
        initListener();
        initEditor();
    }
    
	private void initEditor() {
		editor = new DefaultEditor();
		editor.addEditorListener(editorListener);
		ButtonFactory.addToolbarToTileEditor(editor, this);
		
		closableTabbedPane1.setEditor(editor);
	}

	private void initListener() {
		importImagePanel1.addPropertyChangeListener(selectionListener);
		importImagePanel1.addPropertyChangeListener(messageListener);
		
		libraryPanel1.addPropertyChangeListener(selectionListener);
		libraryPanel1.addPropertyChangeListener(messageListener);
	}

	protected void updateAction() {
		GenerateEdgeAction edgeAction =(GenerateEdgeAction) getAction(GenerateEdgeAction.ID);
		Action coordinateAction = getAction(ShowCoordinateAction.ID);
		Action gridAction = getAction(ShowGridAction.ID);
		EditorView v= editor.getActivateView();
		if(v instanceof EdgeEditorView){
			edgeAction.setEnabled(true);
			edgeAction.setView((EdgeEditorView) v);
			
			coordinateAction.setEnabled(true);
			gridAction.setEnabled(true);
			return;
		}
		edgeAction.setEnabled(false);
		coordinateAction.setEnabled(false);
		gridAction.setEnabled(false);
	}

	protected void updateToolbar() {
		if(currentToolbar != null){
			remove(currentToolbar);
		}
		EditorView v= editor.getActivateView();
		if(v == null){
			return;
		}
		
		Object obj=getClientProperty(v.getClass().getName());
		if(obj instanceof JToolBar){
			JToolBar toolbar = (JToolBar)obj;
			add(currentToolbar=toolbar,BorderLayout.WEST);
		}
		
		validate();
		repaint();
	}

	@Override
    protected void initActions() {
    	putAction(OpenImageAction.ID, new OpenImageAction(app));
    	putAction(NewLibraryAction.ID, new NewLibraryAction(app, libraryPanel1.getManager()));
    	putAction(NewCustomTileAction.ID, new NewCustomTileAction(app, libraryPanel1.getManager()));

    	GenerateEdgeAction edge =new GenerateEdgeAction(app);
    	edge.setEnabled(false);
    	putAction(GenerateEdgeAction.ID, edge);
    	
    	ShowGridAction gridAction = new ShowGridAction(app, editor);
    	gridAction.setEnabled(false);
    	putAction(ShowGridAction.ID, gridAction);
    	
    	ShowCoordinateAction coordinateAction = new ShowCoordinateAction(app);
    	coordinateAction.setEnabled(false);
    	putAction(ShowCoordinateAction.ID, coordinateAction);
    }
    
    @Override
    public JMenuBar createMenus() {
    	JMenuBar menuBar=new JMenuBar();
		AppResources r = app.getResource();
		Model model=app.getModel();

		// file
		JMenu fileMenu=new JMenu();
		r.configMenu(fileMenu, "file");
//		fileMenu.add(getAction(NewMapAction.ID));
		fileMenu.add(model.getAction(OpenImageAction.ID));
		fileMenu.addSeparator();
		fileMenu.add(model.getAction(SaveAction.ID));
//		fileMenu.add(model.getAction(CloseAction.ID));
		fileMenu.addSeparator();
		fileMenu.add(model.getAction(ExitAction.ID));
//		fileMenu.add(getAction(NewMapAction.ID));
//		fileMenu.add(getAction(SaveAction.ID));
//		fileMenu.add(model.getAction(ExitAction.ID));
		menuBar.add(fileMenu);
		
		//edit
		JMenu editMenu=new JMenu();
		r.configMenu(editMenu, "edit");
//		editMenu.add(undoAction);
//		editMenu.add(redoAction);
		editMenu.addSeparator();
//		editMenu.add(getAction(DeleteAction.ID));
		menuBar.add(editMenu);
		
		// tool
		JMenu toolMenu=new JMenu();
		r.configMenu(toolMenu, "tool");
		for(Iterator<String> i=model.viewIterator();i.hasNext();){
			toolMenu.add(model.getAction(i.next()));
		}
		toolMenu.add(model.getAction(ConfigAction.ID));
		menuBar.add(toolMenu);
		
		// help
		JMenu helpMenu=new JMenu();
		r.configMenu(helpMenu, "help");
		helpMenu.add(getAction(VersionAction.ID));
		helpMenu.add(model.getAction(AboutAction.ID));
		helpMenu.addSeparator();
		helpMenu.add(model.getAction(HelpAction.ID));
		menuBar.add(helpMenu);

		return menuBar;
    }
    
    @Override
	public JToolBar getToolbar() {
		JToolBar mapViewBar = new JToolBar();

		mapViewBar.add(getAction(OpenImageAction.ID));
		mapViewBar.add(getAction(NewLibraryAction.ID));
		mapViewBar.add(getAction(NewCustomTileAction.ID));
		
//		mapViewBar.add(new JSeparator(1));
		mapViewBar.add(getAction(GenerateEdgeAction.ID));
		mapViewBar.add(getAction(ShowGridAction.ID));
		mapViewBar.add(getAction(ShowCoordinateAction.ID));
		
		return mapViewBar;
	}
    
    public void open(File[] files) {
    	importImagePanel1.open(files);
	}
    
    @Override
    public void close() {
    	super.close();
    	libraryPanel1.getManager().save();
    }
    
    
//    public void addEditor(Editor e) {
//		editors.add(e);
//	}
    
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        messager = new javax.swing.JLabel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        closableTabbedPane1 = new ClosableTabbedPane();
        importImagePanel1 = new org.mepper.resources.gui.ImportImagePanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        libraryPanel1 = new org.mepper.resources.gui.LibraryPanel();
        propertyPanelProxy=new PropertyPanelProxy();
        
        setLayout(new java.awt.BorderLayout());
        this.setPreferredSize(new java.awt.Dimension(834, 412));

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        messager.setText("未选择工具");
        jToolBar1.add(messager);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setDividerSize(8);
        jSplitPane1.setResizeWeight(0.3);
        jSplitPane1.setOneTouchExpandable(true);

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setDividerSize(8);
        jSplitPane2.setResizeWeight(0.7);
        jSplitPane2.setOneTouchExpandable(true);
        jSplitPane2.setLeftComponent(closableTabbedPane1);
        jSplitPane2.setRightComponent(jSplitPane3);
        
        jSplitPane2.setDividerLocation(200);
        jSplitPane3.setResizeWeight(0.5);
        jSplitPane3.setOneTouchExpandable(true);
        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setLeftComponent(importImagePanel1);
        jSplitPane3.setRightComponent(propertyPanelProxy);

        jSplitPane1.setRightComponent(jSplitPane2);

        libraryPanel1.init(this);
        jScrollPane1.setViewportView(libraryPanel1);

        jSplitPane1.setLeftComponent(jScrollPane1);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ClosableTabbedPane closableTabbedPane1;
    private org.mepper.resources.gui.ImportImagePanel importImagePanel1;
    private PropertyPanelProxy propertyPanelProxy;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JToolBar jToolBar1;
    private org.mepper.resources.gui.LibraryPanel libraryPanel1;
    private javax.swing.JLabel messager;
    // End of variables declaration//GEN-END:variables

	

}
