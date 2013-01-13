/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewCustomTileDialog.java
 *
 * Created on 2011-4-27, 18:08:48
 */

package org.mepper.resources.gui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.JPanel;

import org.mepper.io.Storable;
import org.zhiwu.app.AppDialog;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AbsAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>NewCustomTileDialog</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-27 created
 * @since org.mepper.resource.gui Ver 1.0
 * 
 */
public class PropertyDialog extends AppDialog {
	private final AppResources r = AppManager.getResources();
	private final Storable[] source;

	public PropertyDialog(Application app, Storable[] source) {
		super(app);
		this.source = source;
		initComponents();
		setTitle(propertyPanelProxy.getTitle());
	}

	protected void confirm() {
		if(propertyPanelProxy.available()){
			propertyPanelProxy.save();
			fireOptionSelected(COMFIRM_OPTION);
		}
	}

	private final Action confirmAction =new AbsAction("confirm") {
		@Override
		public void actionPerformed(ActionEvent e) {
			confirm();
		}
	};
	

    private void initComponents() {
        propertyPanelProxy = new PropertyPanelProxy();
        propertyPanelProxy.setSource(source);
        propertyPanelProxy.setAutoSave(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(propertyPanelProxy,BorderLayout.CENTER);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        
        jButton3 = new javax.swing.JButton();
        jButton3.setAction(confirmAction);
        jButton3.setText(r.getString("confirm"));
        buttonPanel.add(jButton3);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        pack();
    }

    private javax.swing.JButton jButton3;
    private PropertyPanelProxy propertyPanelProxy;
}
