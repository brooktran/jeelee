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

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableColumn;

import org.mepper.io.Storable;
import org.mepper.utils.MepperConstant;

 
/**
 * <B>PropertyPanelProxy</B>
 * recent word counter
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-26 created
 * @since org.mepper.resources.gui Ver 1.0
 * 
 */
public class PropertyPanelProxy extends PropertyPanel {
	private List<String> words;
	
	@Override
	public void save() {
		super.save();
		
		for(int i=3,j=table.getRowCount();i<j;i++){
			String name = table.getValueAt(i, 0).toString();;
			String value =table.getValueAt(i, 1).toString();
			if(name.trim().equals("") || value.equals(differentValue)){
				continue;
			}
			if(words.contains(name)){
				continue;
			}
			words.add(name);
			MepperConstant.RECENT_PROPERTIES.put(name);
		}
		
		TableColumn nameColumn=table.getColumn(r.getString("name"));
        nameColumn.setCellEditor(new DefaultCellEditor(getResourceNamesComboBox()));
	}
	
	@Override
	public void setSource(Storable... sources) {
		super.setSource(sources);
		words = new ArrayList<String>();
	}

}
