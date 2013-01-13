/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LayerPanel.java
 *
 * Created on 2011-4-8, 3:22:14
 */

package org.mepper.editor.gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.mepper.app.action.PropertyAction;
import org.mepper.editor.map.DefaultLayer;
import org.mepper.editor.map.Layer;
import org.mepper.editor.map.Map;
import org.mepper.editor.map.MapAdapter;
import org.mepper.editor.map.MapEvent;
import org.mepper.editor.map.MapListener;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.action.AbsAction;
import org.zhiwu.utils.AppOptionPanel;
import org.zhiwu.utils.AppResources;

/**
 * <B>LayerPanel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-9-24 created
 * @since org.mepper.editor.gui Ver 1.0
 * 
 */
public class LayerPanel extends javax.swing.JPanel {
	protected JTable layerTable;
	private Map map;
	private boolean isSingleSelection = true;
	private MapView mapView ;
	private final MapListener mapListener= new MapAdapter() {
		@Override
		public void layerVisibleChanged(MapEvent e) {//visible of the layers changed.
			updateMap();
		}
		@Override
		public void layerSelectionChanged(MapEvent e) {//selection of the layers changed.
			updateMap();
		}
	};
	
	protected final TableCellRenderer iconHeaderRenderer = new DefaultTableCellRenderer() {
		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// Inherit the colors and font from the header component
			if (table != null) {
				JTableHeader header = table.getTableHeader();
				if (header != null) {
					setForeground(header.getForeground());
					setBackground(header.getBackground());
					setFont(header.getFont());
				}
			}

			if (value instanceof TextAndIcon) {
				setIcon(((TextAndIcon) value).icon);
				setText(((TextAndIcon) value).text);
			} else {
				setText((value == null) ? "" : value.toString());
				setIcon(null);
			}
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	};
	
	protected class TextAndIcon {
		public TextAndIcon(Object text, Icon icon) {
			this.text = text.toString();
			this.icon = icon;
		}
		String text;
		Icon icon;
	}
	
	
	private final Action addLayerAction =new AbsAction("addLayer") {
		@Override
		public void actionPerformed(ActionEvent e) {
			for(int i=0,j=map.getLayerCount();i<j;i++){
				map.getLayer(i).setSelection(false);
			}
			
			int p = layerTable.getSelectedRow();
			p = p < 0 ? 0 : p;
			int q=tableToModel(p) +1;
			Layer l=new DefaultLayer(AppManager.getResources().getString("new.layer.name")+(layerTable.getRowCount()+1));
			map.addLayer(l, q);
			
			refreshRows(p, p);
			updateLayerAction();
		}
	};
	
	private final Action deleteLayerAction  = new AbsAction("deleteLayer") {
		@Override
		public void actionPerformed(ActionEvent e) {
			int q=-1 ;
			int p = layerTable.getSelectedRow();
			q = tableToModel(p);
			if(!AppOptionPanel.showConfirmDeleteDialog(LayerPanel.this,map.getLayer(q).toString())){
				return;
			}
			
			map.removeLayer(q);
			p= p<1? p:p-1;
			refreshRows(p,p);
			updateLayerAction();
		}
	};
	
	private final Action moveupLayerAction = new AbsAction("moveUpLayer") {
		@Override
		public void actionPerformed(ActionEvent e) {
			moveLayer(+1);
		}
	};
	
	private final Action movedownLayerAction = new AbsAction("moveDownLayer") {
		@Override
		public void actionPerformed(ActionEvent e) {
			moveLayer(-1);
		}
	};
	
	
    public LayerPanel() {
        initComponents();
        initLayerPane();
        initListeners();
        
        updateLayerAction();
    }
    
    
     
	protected void updateMap() {
//		map.removeMapListener(mapListener);
		int p = layerTable.getSelectedRow();
		p = p < 0 ? 0 : p;
		refreshRows(p, p);
		updateLayerAction();	
//		map.addMapListener(mapListener);
	}



	private void initListeners() {
		layerTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateLayerAction();
			}
		});
		
		layerTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(SwingUtilities.isRightMouseButton(e)){
					handlePopupmenu(e);
				}
			}
		});
	}
	
	public void init(MapView mapView) {
		this.mapView = mapView;
	}

	protected void handlePopupmenu(MouseEvent e) {
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(addLayerAction);
		popupMenu.add(deleteLayerAction);
		popupMenu.add(moveupLayerAction);
		popupMenu.add(movedownLayerAction);
		popupMenu.add(new PropertyAction(mapView.getApplication(),map.getSelectedLayer()));
		popupMenu.show(this, e.getX(), e.getY());
	}



	public void setMap(Map map) {
		this.map = map;
		clearRows();
		if (map == null) {
			return;
		}
		map.removeMapListener(mapListener);
		map.addMapListener(mapListener);
		addRows( map, 0, 0 );
	}

	protected void refreshRows(int index0, int index1){
		clearRows();
		addRows(map,index0, index1);
	}
	
	private void addRows(Map map,int index0, int index1) {
		DefaultTableModel model = (DefaultTableModel) layerTable.getModel();
		int layerCount=map.getLayerCount();
		for(int i=0;i<layerCount;i++){
			Layer l=map.getLayer(i);
			model.insertRow(0, new Object[]{l.isVisible(),l.isSelected(),l.getName()});
		}
		
		if(layerCount >0){
			layerTable.clearSelection();
			layerTable.getSelectionModel().setSelectionInterval(index0, index1);
		}
	}
	
	private void clearRows() {
		DefaultTableModel model = (DefaultTableModel) layerTable.getModel();
		while(model.getRowCount()>0){
			model.removeRow(0);
		}
		layerTable.repaint();
	}


	private int tableToModel(int p){
		int rows=layerTable.getRowCount();
		return rows - p -1;
	}
	
	private void initLayerPane() {
		jScrollPane3.setViewportView(layerTable=getLayerTable());

		layerToolBar.add(addLayerAction);
		layerToolBar.add(deleteLayerAction);
		layerToolBar.add(moveupLayerAction);
		layerToolBar.add(movedownLayerAction);
//		addLayerAction.setEnabled(false);
//		deleteLayerAction.setEnabled(false);
//		moveupLayerAction.setEnabled(false);
//		movedownLayerAction.setEnabled(false);
	}
	
	protected void updateLayers(int editingRow ,int editingColumn) {
		int rowCount = layerTable.getRowCount();
		if(rowCount <0){
			return;
		}
		
		int visibleColumn =0;
		int selectionColumn=1;
		int nameColumn = 2;
		for(int i=0,p;i<layerTable.getColumnCount();i++){
			p=layerTable.convertColumnIndexToModel(i);
			if( p== 0){// visible.
				visibleColumn = i;
			}else if(p == 1){// nameColumn.
				selectionColumn = i;
			}else if(p == 2){// selectionColumn.
				nameColumn = i;
			}
		}
		
		int p = tableToModel(editingRow);
		Layer layer = map.getLayer(p);
		
		if(editingColumn == selectionColumn){
			if (editingColumn == selectionColumn && isSingleSelection) {
				for(int i=0,j=map.getLayerCount();i<j;i++){
					map.getLayer(tableToModel(i)).setSelection(false);
				}
				map.getLayer(p).setSelection(true);
			}else {
				layer.setSelection( (Boolean) layerTable.getValueAt(editingRow, selectionColumn));
			}
		}else if (editingColumn == visibleColumn) {
			layer.setVisible((Boolean) layerTable.getValueAt(editingRow, visibleColumn));
		}else {
			layer.setName( layerTable.getValueAt(editingRow, nameColumn).toString());
		}
		
		rowCount = layerTable.getSelectedRow();
		refreshRows(rowCount, rowCount);
	}
	
	private void updateLayerAction(){
		int p=layerTable.getSelectedRow();
//		int[] rows=layerTable.getSelectedRows();
		if(p <0){
			addLayerAction.setEnabled(map != null);
			deleteLayerAction.setEnabled(false);
			moveupLayerAction.setEnabled(false);
			movedownLayerAction.setEnabled(false);
			return;
		}
		
		addLayerAction.setEnabled(true);
		deleteLayerAction.setEnabled(true);
		moveupLayerAction.setEnabled(true);
		movedownLayerAction.setEnabled(true);
		if( p ==0){
			moveupLayerAction.setEnabled(false);
		}
		if( p == (layerTable.getRowCount()-1)){
			movedownLayerAction.setEnabled(false);
		}
	}
	
	private void moveLayer(int orientation) {
		int q=-1 ;
		int p = layerTable.getSelectedRow();
		
		q = tableToModel(p);
		Layer l = map.removeLayer(q);
		q += orientation;
		map.addLayer(l, q );

		q = tableToModel(q);
		refreshRows(q, q);
		updateLayerAction();
	}
	
	private JTable getLayerTable() {
		AppResources r = AppManager.getResources();
		DefaultTableModel model = new javax.swing.table.DefaultTableModel(
				new Object[][] {}, new String[] {
						"map.view.layer.table.visible", 
						"map.view.layer.table.lock", 
						"map.view.layer.table.name"}) {
			
			Class<?>[] types = new Class[] { 
					java.lang.Boolean.class,
					java.lang.Boolean.class, 
					java.lang.String.class };

			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return types[columnIndex % 3];
			}
		};

		final JTable table = new JTable(model){
			@Override
			public void editingStopped(ChangeEvent e) {
				int editingRow = layerTable.getEditingRow();
				int editingColumn = layerTable.getEditingColumn();
				super.editingStopped(e);
				updateLayers(editingRow,editingColumn);//
			}
		};
		TableColumnModel tcm = table.getTableHeader().getColumnModel();
		for (int i = 0, j = tcm.getColumnCount(); i < j; i++) {
			tcm.getColumn(i).setHeaderRenderer(iconHeaderRenderer);
			TableColumn tc = tcm.getColumn(i);
			String columnName = tcm.getColumn(i).getHeaderValue() + "";
			TextAndIcon tai = new TextAndIcon(r.getString(columnName),
					r.getImageIcon(columnName));
			tc.setHeaderValue(tai);
		}
		table.getColumnModel().getColumn(0).setPreferredWidth(10);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.setPreferredScrollableViewportSize(new Dimension(150, 100));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		return table;
	}
	
	
//    /** This method is called from within the constructor to
//     * initialize the form.
//     * WARNING: Do NOT modify this code. The content of this method is
//     * always regenerated by the Form Editor.
//     */
//    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        layerToolBar = new javax.swing.JToolBar();
        jCheckBox2 = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        layerToolBar.setRollover(true);

        jCheckBox2.setSelected(true);
        jCheckBox2.setToolTipText(AppManager.getResources().getString("single.selecte"));
        jCheckBox2.setFocusable(false);
        jCheckBox2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jCheckBox2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            @Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });
        layerToolBar.add(jCheckBox2);

        add(layerToolBar, java.awt.BorderLayout.PAGE_END);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("层"));
        add(jScrollPane3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
    	isSingleSelection = !isSingleSelection;
    }//GEN-LAST:event_jCheckBox2ActionPerformed
//
//
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar layerToolBar;
    // End of variables declaration//GEN-END:variables



}

