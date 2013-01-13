/* LoginDialog.java 1.0 2010-2-2
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
package org.medicine.server.app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.zhiwu.app.AbsDialog;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.action.AbsAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>LoginDialog</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-12 created
 * @since Jili Ver 1.0
 * 
 */
public class LoginDialog extends  AbsDialog{
	private static final AppResources r= AppManager.getResources();
	
	public LoginDialog() {
		initComponents();
	}
	
	private void initComponents() {
		setBounds(100, 100, 325, 191);
		getContentPane().setLayout(new BorderLayout());
		JPanel contentPanel = new JPanel();
		contentPanel.setBorder(new TitledBorder(null, r.getString("login"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblName = new JLabel("name");
		textField = new JTextField();
		textField.setColumns(10);
		JLabel lblPassword = new JLabel(r.getString("password"));
		passwordField = new JPasswordField();
		
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblPassword, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(29)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(passwordField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
						.addComponent(textField, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblName)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
				);
		
		contentPanel.setLayout(gl_contentPanel);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton(comfirmAction);
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton = new JButton(cancleAction);
		buttonPane.add(cancelButton);

		setTitle(r.getString("login"));
	}




	private  JTextField textField;
	private  JPasswordField passwordField;
	private JButton cancelButton;
	private JButton okButton ;
	
	
	private final Action comfirmAction = new AbsAction("comfirm") {
		@Override
		public void actionPerformed(ActionEvent e) {
			firePropertyChange(COMFIRM_OPTION, textField.getText(), passwordField.getPassword());
		}
	};
	
	private final Action cancleAction = new AbsAction("cancle") {
		@Override
		public void actionPerformed(ActionEvent e) {
			firePropertyChange(CANCEL_OPTION, null, null);
		}
	};

}
