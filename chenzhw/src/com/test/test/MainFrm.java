package com.test.test;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.util.Enumeration;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.chenzhw.IOFileControl.FileControlView;

/**
 * 类<B> Main </B>是
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-2-27 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class MainFrm extends JFrame {
	private JPanel centerPnl=new JPanel();
	
	private static final long serialVersionUID = -4612877543895294020L;

	public MainFrm() {
		initComponents();
	}

	private void initComponents() {

		jToolBar1 = new javax.swing.JToolBar();
		jButton1 = new javax.swing.JButton();
		jMenuBar1 = new javax.swing.JMenuBar();
		jMenu1 = new javax.swing.JMenu();
		jMenu2 = new javax.swing.JMenu();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jToolBar1.setRollover(true);

		jButton1.setText("文件转移");
		jButton1.setFocusable(false);
		jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});
		jToolBar1.add(jButton1);

		jMenu1.setText("File");
		jMenuBar1.add(jMenu1);

		jMenu2.setText("Edit");
		jMenuBar1.add(jMenu2);

		setJMenuBar(jMenuBar1);

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addComponent(
				jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 563,
				Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup().addComponent(jToolBar1,
						javax.swing.GroupLayout.PREFERRED_SIZE, 25,
						javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(303, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
		FileControlView.main(null);
	}

	// Variables declaration - do not modify
	private javax.swing.JButton jButton1;

	private javax.swing.JMenu jMenu1;

	private javax.swing.JMenu jMenu2;

	private javax.swing.JMenuBar jMenuBar1;

	private javax.swing.JToolBar jToolBar1;

	// End of variables declaration

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			Font font = new Font("Dialog", Font.PLAIN, 12);
			Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
			// Returns an enumeration of the keys in this hashtable.

			// 将所有字体都设置为font对象中定义的字体
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				if (UIManager.get(key) instanceof Font) {
					UIManager.put(key, font);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		JFrame frm = new MainFrm();
		frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frm.setTitle("测试软件");
		frm.setSize(800, 573);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		frm.setLocation((dimension.width - frm.getSize().width) / 2,
				(dimension.height - frm.getSize().height) / 3);
		frm.setVisible(true);
	}

}
