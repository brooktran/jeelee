package com.test.bean;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 类<B> Loan </B>是贷款类的 bean.
 * <p>
 * This class contains the User Interface that Earnest Bank uses to add new loan
 * types to the database. Whenever a new loan type is added, a property change
 * event is generated.
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-6 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class Loan extends JPanel implements ActionListener, Serializable {

	/* bean start */
	
	/** The changes. */
	protected PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private String loanType = new String("Personal Loan");// 个人贷款

	/**
	 * @return loanType
	 */
	public String getLoanType() {
		return loanType;
	}

	/**
	 * Sets the loan type.
	 * 
	 * @param newValue
	 *            要设置的 loanType
	 */
	public void setLoanType(String newValue) {
		String oldValue = loanType;
		this.loanType = newValue;
		changes.firePropertyChange("loanType", oldValue, newValue);
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();
		if (object == null) {

		}
	}
	
	private JLabel lblID,lblType;
	private JTextField txtID,txtType;
	private JButton btnSumbit;
	
	public Loan(){
		lblID=new JLabel("Enter Loan ID");
		txtID=new JTextField(8);
		lblType=new JLabel("Loan Type");
		txtType=new JTextField(15);
		btnSumbit=new JButton("Add New Loan");
		
		setLayout(new GridLayout(3,2));
		
		add(lblID);
		add(txtID);
		
		add(lblType);
		add(txtType);
		
		btnSumbit.addActionListener(this);
		add(btnSumbit);
		
	}
	
	public void addPropertyChangeListener(PropertyChangeListener listener){
		//System.out.println("asdfd");
		changes.addPropertyChangeListener(listener);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener listener){
		changes.removePropertyChangeListener(listener);
	}
}































