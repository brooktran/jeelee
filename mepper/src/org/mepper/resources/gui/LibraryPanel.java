/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LibraryPanel.java
 *
 * Created on 2011-4-8, 15:28:44
 */

package org.mepper.resources.gui;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import org.mepper.app.action.PropertyAction;
import org.mepper.editor.Editor;
import org.mepper.editor.EditorView;
import org.mepper.editor.EditorViewFactory;
import org.mepper.editor.map.DefaultLayer;
import org.mepper.editor.map.Map;
import org.mepper.editor.map.MapFactory;
import org.mepper.editor.tile.CustomTile;
import org.mepper.editor.tile.DefaultTile;
import org.mepper.editor.tile.Tile;
import org.mepper.editor.tile.TileFactory;
import org.mepper.gui.EditableView;
import org.mepper.gui.ResourcesTreeCellRenderer;
import org.mepper.io.Storable;
import org.mepper.resources.DefaultResource;
import org.mepper.resources.DefaultResourcesManager;
import org.mepper.resources.EdgeEditorView;
import org.mepper.resources.LibraryResource;
import org.mepper.resources.ResourceImportTransferHandler;
import org.mepper.resources.ResourceManager;
import org.mepper.resources.ResourceMessager;
import org.mepper.resources.ResourcesEvent;
import org.mepper.resources.ResourcesListener;
import org.mepper.resources.StorableResource;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppDialog;
import org.zhiwu.app.AppDialogListener;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.Application;
import org.zhiwu.app.DialogEvent;
import org.zhiwu.app.action.AbsAction;
import org.zhiwu.utils.AppOptionPanel;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.ImageUtils;


public class LibraryPanel extends javax.swing.JPanel {
	protected static final String SELECTION_CHANGED_PROPERTY = "selection.changed";
	public static final String CURRENT_NODE_CHANGED = "current changed";
	private final AppResources r=AppManager.getResources();
	private ResourceManager manager;
	private EditableView view;
	private boolean isUpdateSelectionPath;
	
	private final TreeSelectionListener treeSelectionListener =new TreeSelectionListener() {
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			if(!isUpdateSelectionPath){
				return;
			}
			StorableResource  newValue = (StorableResource) e.getPath().getLastPathComponent();
			manager.setCurrentNode(newValue);
			messageChanged(newValue);
		}
	};

    public LibraryPanel() {
    }
    
    public void init(EditableView view) {
    	this.view =view;
    	
		initResourceManager();
		initComponents();
		initListeners();
		selectionNode();
		
    	managerTree.setTransferHandler(new ResourceImportTransferHandler(this));
	}

	private void selectionNode() {
		StorableResource r=getRoot().getChild(0);
		if(r != null){
			manager.setCurrentNode(r); 
		}
	}

	private void initListeners() {
		managerTree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
		managerTree.getCellEditor().addCellEditorListener(new CellEditorListener() {
			@Override
			public void editingStopped(ChangeEvent e) {
				cellValueChanged((DefaultCellEditor) e.getSource());
			}
			
			@Override
			public void editingCanceled(ChangeEvent e) {
				cellValueChanged((DefaultCellEditor) e.getSource());
			}
		});
		managerTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(e.isControlDown()||e.isShiftDown()){
					isUpdateSelectionPath=false;
					return;
				}
				isUpdateSelectionPath=true;
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() ==2) {
					StorableResource r= (StorableResource) managerTree.getSelectionPath().getLastPathComponent();
//					Storable s=r.getSource();
					Editor editor =view.getEditor();
//					if(s instanceof Tile){
						editor.setUserObject(MepperConstant.EDITOR_USER_OBJECT,r);
//						editor.setUserObject(SelectionTool.TOOL_USER_OBJECT_STRING, s);
//					}
					return;
				}
					
				
				if (SwingUtilities.isRightMouseButton(e)) {
					TreePath path= managerTree.getSelectionPath();
					if(path == null){
						return;
					}
					handlePopupMenu(e);
				}
			}
		});
		
		managerTree.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if (e.isControlDown() || e.isShiftDown()) {
					isUpdateSelectionPath = false;
					return;
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) ) {
					StorableResource r= manager.getCurrentNode();
					if(r.isLibrary()){
						return;
					}
					view.getEditor().setUserObject(MepperConstant.EDITOR_USER_OBJECT,r);
//					view.getEditor().setUserObject(SelectionTool.TOOL_USER_OBJECT_STRING,r.getSource());
				}
			}
		
		});
	}
	private void handlePopupMenu(MouseEvent e) {
		Point p=e.getPoint();
		JPopupMenu menu = new JPopupMenu();
		final StorableResource child = manager.getCurrentNode();
		final Application app = view.getApplication();
		final TreePath[] paths =managerTree.getSelectionPaths();
		final StorableResource[] selectedResources=new StorableResource[paths.length];
		for(int i=0;i<paths.length;i++){
			selectedResources[i] = (StorableResource) paths[i].getLastPathComponent();
		}
		
		
		Action editAction = new AbsAction("edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				Storable sp =child.getSource();
				
				if( sp instanceof DefaultTile){
					Tile t=  TileFactory.createCandidateTile((Tile) sp);
					BufferedImage image = t.getImage();
			 		
					Map map= MapFactory.getDefaultMap();
					map.setName(child.getName());
					map.setTileStep(t.getTileWidth(),t.getTileHeight());
					map.setLogicalSize(1, 1);
					map.setSize(image.getWidth(), image.getHeight());
					map.addLayer(new DefaultLayer(), 0);
							
					map.addTile(new int[][]{{0,0}},new Tile[]{t});
					
					EditorView editorView = EditorViewFactory.getTileEditorView(map,t,manager);
					view.getEditor().add(editorView);
					firePropertyChange(SELECTION_CHANGED_PROPERTY, null, map);
					return;
				}
				if(sp instanceof CustomTile){
					final Application app=view.getApplication();
					app.setEnable(false);
					final AppDialog dialog=new NewCustomTileDialog(app,manager,(CustomTile) sp);
					dialog.showDialog(new AppDialogListener(){
						@Override
						public void optionSelection(DialogEvent evt) {
							dialog.dispose();// other option
							app.setFoucs(); 
							app.setEnable(true);
						}
					}); 
				}
			}
		};
		editAction.setEnabled(!child.isLibrary());
		menu.add(editAction);
		
		Action edgeAction =new AbsAction("edge") {
			@Override
			public void actionPerformed(ActionEvent e) {
				final AppDialog dialog =new  MapTypeDialog(app);
				app.setEnable(false);
				dialog.showDialog(new AppDialogListener() {
					@Override
					public void optionSelection(DialogEvent evt) {
						if(evt.getName().equals(AppDialog.COMFIRM_OPTION)){
							Storable sp =child.getSource();
							if( sp instanceof DefaultTile){
								Tile t = (Tile) sp;
								Map map = (Map) evt.getData();
								map.setName(sp.getName());
								map.setLogicalSize(3,3);//TODO change the size in case the dimension of the tile not (1,1)
								map.addLayer(new DefaultLayer(), 0);
								map.addTile(new int[][]{{1,1}},new Tile[]{t});
								EdgeEditorView editorView= EditorViewFactory.getEdgeEditorView(map,child.getParentResource(),manager);
								Editor editor = view.getEditor();
								editor.add(editorView);
								firePropertyChange(SELECTION_CHANGED_PROPERTY, null, map);
							}
						}
						managerTree.validate();
						managerTree.repaint();
						dialog.dispose();
						app.setEnable(true);
					}
				});
				app.setEnable(true);
				
				
			}
		};
		edgeAction.setEnabled(paths.length==1 && !hasLibrary(paths));
		menu.add(edgeAction);
		

		Action deleteAction = new AbsAction("delete") {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(AppOptionPanel.showConfirmDeleteDialog(LibraryPanel.
						this, paths.length==1?
								paths[0].getLastPathComponent().toString():paths.length + "")){
					for(TreePath path : paths){
						manager.setCurrentNode((StorableResource) path.getLastPathComponent());
						manager.removeResource((StorableResource) path.getLastPathComponent());
					}
				}
			}
		};
		deleteAction.setEnabled(child.getParentResource() != null);
		menu.add(deleteAction);
		
		
		Action propertyAction = new PropertyAction(app, selectedResources);
		propertyAction.setEnabled(child != null);
		menu.add(propertyAction);
		
		
		
        menu.show(managerTree, p.x, p.y);
	}
	

	private boolean hasLibrary(TreePath[] paths) {
		for(TreePath path:paths){
			StorableResource r=(StorableResource) path.getLastPathComponent();
			if(r.isLibrary()){
				return true;
			}
		}
		return false;
	}

	private void cellValueChanged(DefaultCellEditor e){
		TreePath path=managerTree.getSelectionPath();
		StorableResource r=(StorableResource) path.getLastPathComponent();
		r.setName(e.getCellEditorValue().toString());
	}

	private void initResourceManager() {
		manager = DefaultResourcesManager.getInstance();
		manager.addResourceListener(new ResourcesListener() {
			@Override
			public void resourceRemoved(ResourcesEvent e) {
				managerChanged(e);
			}
			
			@Override
			public void resourceChanged(ResourcesEvent e) {
				managerChanged(e);
			}
			
			@Override
			public void resourceAdded(ResourcesEvent e) {
				nodeStructureChanged(getRoot());
				DefaultTreeModel model=(DefaultTreeModel) managerTree.getModel();
				TreeNode[] nodes=model.getPathToRoot(e.getReousrce());
				TreePath path=new TreePath(nodes);
				managerTree.setSelectionPath(path);
				
				messageChanged((StorableResource) path.getLastPathComponent());
			}
		});
	}
	private void managerChanged(ResourcesEvent e) {
		managerTree.getSelectionModel().removeTreeSelectionListener(treeSelectionListener);
		
		StorableResource r=e.getReousrce();
		nodeStructureChanged(r);
		messageChanged(r);
		
		managerTree.getSelectionModel().addTreeSelectionListener(treeSelectionListener);
	}

	
	protected void nodeStructureChanged(MutableTreeNode node) {
		DefaultTreeModel treeModel = (DefaultTreeModel) managerTree.getModel();
		treeModel.nodeStructureChanged(node);
		TreePath path = new TreePath(treeModel.getPathToRoot(manager.getCurrentNode()));
		managerTree.expandPath(path);
		managerTree.setSelectionPath(path);
	}
	public ResourceManager getManager() {
		return manager;
	}
	
	 

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        managerTree = new javax.swing.JTree();
        jPanel1 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        managerTree.setBorder(javax.swing.BorderFactory.createTitledBorder(r.getString("tile.manager.view.tree.tiles")));
        managerTree.setModel(new DefaultTreeModel(getRoot()));
        managerTree.setCellRenderer(new ResourcesTreeCellRenderer());
        managerTree.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        managerTree.setEditable(true);
        managerTree.setRootVisible(false);
        managerTree.setShowsRootHandles(true);
        jScrollPane1.setViewportView(managerTree);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
        add(jPanel1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTree managerTree;
    // End of variables declaration//GEN-END:variables

	public boolean existLibrary(StorableResource parent,String libraryName) {
		for(int i=0,j=parent.getChildCount();i<j;i++){
			StorableResource  node=(StorableResource ) parent.getChildAt(i);
			if(node.getName().equals(libraryName)){
				return true;
			}
		}
		return false;
	}


	public StorableResource  getRoot() {
		return manager.getRoot();
	}

	public void addLibrary(String libraryName) {
		manager.addResourceChild(new LibraryResource(libraryName));
	}
	
	protected void messageChanged(StorableResource r) {
		firePropertyChange("message", null, ResourceMessager.getMessage(r));
	}

	public void importTiles(final File[] files) {
		final Application app = view.getApplication();
		final AppDialog dialog = new TilePropertyDialog(app); 
		app.setEnable(false);
		dialog.showDialog(new AppDialogListener() {
			@Override
			public void optionSelection(DialogEvent evt) {
				if(evt.getName().equals(AppDialog.COMFIRM_OPTION)){
					importFile(files,(Tile)evt.getData());
				}
				dialog.dispose();// other option
				app.setFoucs();
				app.setEnable(true);
			}
		});
	}
	
	private boolean importFile(File[] files,Tile tile) {
		for(File f:files){
			if(f.isDirectory()){
				StorableResource library = new LibraryResource(f.getName());
				manager.addResourceChild(library);
				importFile(f.listFiles(),tile);
				manager.setCurrentNode(library.getParentResource());
			}else {
				if(!imageFilter.accept(f)){
					continue;  
				}
				Tile newTile = TileFactory.createDefaultTile(tile);
				newTile.setImage(ImageUtils.readImage(f));
				StorableResource resource= new DefaultResource(newTile);
				resource.setName(getNameFromFile(f));
				manager.createResourceID(resource);
				manager.addResourceChild(resource);
			}
		}
		return true;
	}
	
	private String getNameFromFile(File f) {
		String name = f.getName();
		int p = name.lastIndexOf(".");
		if(p==-1){
			return name;
		}
		return name.substring(0,p);
	}


	private static FileNameExtensionFilter imageFilter = 
		new FileNameExtensionFilter("image", "jpg","png","psd","gif","jpeg","bmp");
	
	
}

