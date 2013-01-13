/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * NewPackage.java
 *
 * Created on 2010-8-4, 16:00:31
 */

package org.jreader.gui;

import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.jreader.app.JReader;
import org.jreader.app.JReaderModel;
import org.jreader.persistent.Category;
import org.jreader.persistent.ReaderDAO;
import org.zhiwu.app.guider.AbsGuiderContent;
import org.zhiwu.app.guider.GuiderContentEvent;
import org.zhiwu.utils.AppResources;
import org.zhiwu.xml.DataSourceException;


/**
 *
 * @author root
 */
public class NewPackage extends AbsGuiderContent {
	private static final long serialVersionUID = 1L;
	private static final String ID="new.package";
	

    /** Creates new form NewPackage */
    public NewPackage() {
        initComponents();
        
        AppResources resources=AppResources.getResources("org.jreader.app.Labels");
        resources.configGuiderContent(this, ID);
        
        initListener();
    }
    

    /**
	 * 
	 */
	private void initListener() {
		ReaderDAO dao;
		try {
			dao = ReaderDAO.getInstance();
			
			final List<Category> categorys=dao.getCategory();

	        jTextField1.getDocument().addDocumentListener(new DocumentListener() {
				
				@Override
				public void removeUpdate(DocumentEvent e) {
					modify();
				}
				
				@Override
				public void insertUpdate(DocumentEvent e) {
					modify();
				}
				
				@Override
				public void changedUpdate(DocumentEvent e) {
					modify();
				}
				
				private void modify(){
					boolean isFinish=true;
					for(Category c:categorys){
						if(c.getName().equals(jTextField1.getText())){
							isFinish=false;
							break;
						}
					}
					fireGuiderStateChanged(GuiderContentEvent.FinishEnable, isFinish);
				}
			});
		} catch (DataSourceException e1) {
			e1.printStackTrace();
		}
				
	}


	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jLabel1.setText("name:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 346, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#getNext()
	 */
	@Override
	public Class getNext() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#prepare()
	 */
	@Override
	public void prepare() {
		// TODO Auto-generated method stub
		
	}


	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#finish()
	 */
	@Override
	public void finish() {
		JReaderModel model=(JReaderModel)app.getModel();
		JReader reader=model.getReader();
		try {
			reader.createCategory(jTextField1.getText());
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
	}

}