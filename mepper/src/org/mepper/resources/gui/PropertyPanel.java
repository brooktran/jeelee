/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PropertyPanelProxy.java
 *
 * Created on 2011-11-25, 10:28:16
 */
package org.mepper.resources.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.mepper.io.Storable;
import org.mepper.utils.MepperConstant;
import org.mepper.utils.Word;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.action.AbsAction;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.ArrayUtils;
import org.zhiwu.utils.DefaultPair;
import org.zhiwu.utils.Pair;
import org.zhiwu.utils.PairListMap;


public class PropertyPanel extends JPanel {
	private static final Color SYSTEM_PROPERTY_COLOR=new Color(230,230,230);
    protected static final AppResources r = AppManager.getResources();
    protected static final String differentValue = r.getString("different","value");
    
    private static final String description =  "_"+r.getString("description");
    private static final String idString  = "_"+r.getString("id");
    private static final String nameString = "_"+r.getString("name");
    private static final String[] SYSTEM_PROPERTIES={
    	"_id","_name","_description","_value",
    	nameString,description
    };
    
    protected Storable[] sources;
    private boolean isAutoSave = true;
    
    public PropertyPanel() {
        initComponents();
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				updateAction();
			}
		});
        updateAction();
    }
    
	protected void updateAction() {
		addPropertyAction.setEnabled(!hasNoSources());
		int row =table.getSelectedRow();
		if(row<0){
			deletePropertyAction.setEnabled(false);
			return;
		}
		String name =table.getValueAt(row,0).toString();
		deletePropertyAction.setEnabled(!isSystemPropertyName(name));
	}

	private boolean hasNoSources() {
		return sources == null || sources[0] ==null;
	}

	public void setAutoSave(boolean isAutoSave) {
		this.isAutoSave = isAutoSave;
	}

    protected void initFiled() {
    	clearRows() ;
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        PairListMap<String, Pair<String, Integer>> properties = new PairListMap<String, Pair<String, Integer>>(0);
        
		for (Storable s : sources) {
			addProperty(0,properties,description,s.getDescription());
			addProperty(0,properties,nameString,s.getName());
			addProperty(0,properties,idString,s.getID()+"");
			
			for (String key : s.propertyNames()) {
				String value = s.getProperty(key);
				addProperty(3,properties, key, value);
			}
		}
		
		for(int i=0,j=properties.size();i<j;i++){
			String name= properties.get(i).getKey();
			Pair<String, Integer> counter = properties.get(i).getValue();
			String value =counter.getKey();
			value = counter.getValue()==sources.length?value:differentValue;
			model.insertRow(i,
					new String[] { name, value});
		}
		updateAction();
        repaint(); 
    }

	private void addProperty(int index , PairListMap<String, Pair<String, Integer>> properties, String key, String value) {
		if (properties.getValue(key)== null) {
			properties.add(index,key, new DefaultPair<String, Integer>(value, 1));
		}else {
			Pair<String, Integer> counter = properties.getValue(key);
			String oldValue = counter.getKey();
			if(!value.equals(oldValue)){
				counter.setKey(differentValue);
				return;
			}
			counter.setValue(counter.getValue()+1);
		}
	}
	
    private void clearRows() {
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		while(model.getRowCount()>0){
			model.removeRow(0);
		}
	}
    
    protected void stopEditing(int editingRow, int editingColumn,
			String oldValue) {
    	valid(editingRow, editingColumn, oldValue);
    	if(isAutoSave){
    		save();
    	}
	}
    
	public void save() {
		//  id
		String idString = table.getValueAt(0, 1).toString();
		int id=idString.equals(differentValue)?0:Integer.parseInt(idString);
		String nameString=table.getValueAt(1, 1).toString();
		String descriptionString=table.getValueAt(2, 1).toString();
		if(!idString.equals(differentValue)){
			for (Storable s : sources) {
				if (!idString.equals(differentValue)) {
					s.setID(id);
				}
				if(!nameString.equals(differentValue)){
					s.setName(nameString);
				}
				if(!descriptionString.equals(differentValue)){
					s.setDescription(descriptionString);
				}
			}
		}
		
		for(int i=3,j=table.getRowCount();i<j;i++){
			String name = table.getValueAt(i, 0).toString();;
			String value =table.getValueAt(i, 1).toString();
			if(value.equals(differentValue)){
				continue;
			}
			for (Storable s : sources) {
				s.putProperty(name, value);
			}
		}

	}
	
	public void valid(int editingRow, int editingColumn,
			String oldValue) {
		if(!checkValid(editingRow, editingColumn)){
			table.setValueAt(oldValue, editingRow, editingColumn);
		} else if (isAutoSave) {
			save();
		}
		
	}
	
	public boolean checkValid(int editingRow, int editingColumn) {
		String newValue = table.getValueAt(editingRow, editingColumn).toString().trim();
		if (newValue.equals("")) {
			setMessage(r.getString("must.be.entered") + " " +(editingColumn==0?r.getString("name"): r.getString("value")));
			return false;
		}
		if(editingColumn==0 && tableContainsDouble(newValue)){
			messager.setText(newValue+" "+r.getString("already.existed"));
			return false;
		}
		if(editingColumn==0 && newValue.startsWith("_")){
			messager.setText(r.getString("error.data.format")+" "+newValue);
			return false;
		}
		messager.setText("");
		return true;
	}
	
	public boolean available() {
		return messager.getText().equals("");
	}
	
	private void setMessage(String message){
		messager.setText(message);//TODO add unsave hint.
	}

	private boolean tableContainsDouble(String key) {
		if(ArrayUtils.contains(SYSTEM_PROPERTIES, key)){
			return true;
		}
		int counter=0;
		for(int i=0,j=table.getRowCount();i<j;i++){
			if(table.getValueAt(i, 0).equals(key)){
				counter++;
			}
			if(counter>1){
				return true;
			}
		}
		return false;
	}

	public void setErrorMessage(String message){
    	messager.setText(message);
    }
    
    public Storable[] getSource() {
		return sources;
	}

	public void setSource(Storable ...sources) {
		this.sources = sources;
		TitledBorder border =(TitledBorder) jPanel2.getBorder();
		if(hasNoSources()){
			border.setTitle(r.getString("property"));
			clearRows();
			updateAction();
			repaint();
			return;
		}
		
		border.setTitle(
				sources.length==1?sources[0].getName():sources.length
				+" "+r.getString("property"));
		initFiled();
	}
	
	public String getTitle(){
		return ((TitledBorder) jPanel2.getBorder()).getTitle();
	}
	
	protected void addProperty(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		model.insertRow(table.getRowCount(), new Object[] { "", "" });
	}
	protected void deleteProperty(){
		DefaultTableModel model = (DefaultTableModel) table.getModel();
		int selectedRow = table.getSelectedRow();
		if (selectedRow < 0) {
			return;
		}
		model.removeRow(table.getSelectedRow());
		selectedRow += selectedRow==table.getRowCount()?-1:0;
		table.getSelectionModel().setSelectionInterval(selectedRow, selectedRow);
	}
	private final Action addPropertyAction = new AbsAction("add") {
		@Override
		public void actionPerformed(ActionEvent e) {
			addProperty();
		}
	};
	private final Action deletePropertyAction = new AbsAction("delete") {
		@Override
		public void actionPerformed(ActionEvent e) {
			deleteProperty();
		}
	};
	
    private void initComponents() {

        messager = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = getTable();

        setLayout(new java.awt.BorderLayout());

        messager.setForeground(new java.awt.Color(204, 0, 0));
        add(messager, java.awt.BorderLayout.SOUTH);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, r.getString("properties"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("宋体", 0, 12), new java.awt.Color(51, 51, 51))); // NOI18N
        jPanel2.setLayout(new java.awt.BorderLayout());

        jToolBar2.setFloatable(false);
        jToolBar2.setOrientation(1);
        jToolBar2.setRollover(true);

        jButton3.setAction(addPropertyAction);
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton3);

        jButton4.setAction(deletePropertyAction);
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton4);

        jPanel2.add(jToolBar2, java.awt.BorderLayout.LINE_END);

        jScrollPane1.setViewportView(table);

        jPanel2.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
	
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    protected javax.swing.JTable table;
    private javax.swing.JToolBar jToolBar2;
    protected javax.swing.JLabel messager;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private JTable getTable() {//TODO move avalid check to aspectj.
		table=new JTable(){
			@Override
			public void editingStopped(ChangeEvent e) {
				int editingRow = table.getEditingRow();
				int editingColumn = table.getEditingColumn();
				String oldValue=table.getValueAt(editingRow, editingColumn).toString();
				
				super.editingStopped(e);
				stopEditing(editingRow,editingColumn,oldValue);
			}
		};
		table.setAutoCreateRowSorter(false);
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                r.getString("name"), r.getString("value")
            }
        ) {
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

			
			@Override
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
            	String value =table.getValueAt(row, column).toString();
            	if(isSystemPropertyName(value)){
            		return false;
            	}
            	return super.isCellEditable(row, column);
            }
        });
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        
        TableColumn nameColumn=table.getColumn(r.getString("name"));
        nameColumn.setCellEditor(new DefaultCellEditor(getResourceNamesComboBox()));
        nameColumn.setCellRenderer(new DefaultTableCellRenderer(){
        	@Override
        	public Component getTableCellRendererComponent(JTable table,
        			Object value, boolean isSelected, boolean hasFocus,
        			int row, int column) {
        		if (isSystemPropertyName(table.getValueAt(row, column).toString())) {
					setBackground(SYSTEM_PROPERTY_COLOR);
				}else {
					setBackground(table.getBackground());
				}
        		return super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
        				row, column);
        	}
        });
        return table;
	}
	
	

	private boolean isSystemPropertyName (String name){
		return name.startsWith("_");
	}

	protected JComboBox getResourceNamesComboBox() {
		JComboBox comboBox =new JComboBox();
		comboBox.setEditable(true);
		
		Iterator<Word> it =MepperConstant.RECENT_PROPERTIES.iterator();
		for(int i=0; it.hasNext() && i<8;i++){
			comboBox.addItem(it.next().getWord());
		}
		return comboBox;
	}
}
