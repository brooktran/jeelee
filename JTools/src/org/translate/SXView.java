/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * BinaryEditor.java
 *
 * Created on 2009-11-18, 16:44:38
 */
package org.translate;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Formatter;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.zhiwu.app.AbsView;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;



/**
 * 
 * @author Administrator
 */
public class SXView extends AbsView {

	private static final long serialVersionUID = 1L;

	class SXOpenAction extends AppAction {

		private static final long serialVersionUID = 1L;
		public static final String ID = "BEOpen";

		SXOpenAction(Application app) {
			super(app);
			AppResources resource = app.getResource();
			resource.configAction(this, ID);
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			clearTestArea();

			// 读取文件
			jFileChooser1 = new javax.swing.JFileChooser();
			List<File> recentFiles = app.recentFiles();

			if (recentFiles.size() > 0) {
				jFileChooser1.setCurrentDirectory(recentFiles.get(0));
			} else {
				jFileChooser1.setCurrentDirectory(new File("."));
			}
			jFileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
			jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter(
					"XML文件", "xml"));
			jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter(
					"SWF文件", "swf"));

			int result = jFileChooser1.showOpenDialog(null);
			jFileChooser1.setFileHidingEnabled(false);

			if (result == JFileChooser.APPROVE_OPTION) {

				String filePath = jFileChooser1.getSelectedFile()
				.getAbsolutePath();
				File file = new File(filePath);
				addRecentFile(file);
				if (filePath.endsWith("xml")) {
					XMLToSWF(file);
				} else if (filePath.endsWith("swf")) {
					SWFToXML(file);
				}
				getData(file);// 读取文件

				app.setTitle(filePath + "  " + app.getName());
			} else {
				System.out.println("你已取消并关闭了窗口！");
			}
		}

		/**
		 * 
		 * @param file
		 */
		private void SWFToXML(File file) {
//			try {
//				String output = changeSuffix(file, ".xml");
//				System.out.println("output " + output);
////				Transformer.toXML(new FileInputStream(file),
////						new FileOutputStream(new File(output)), true);
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}

		/**
		 * 
		 * @param file
		 */
		private void XMLToSWF(File file) {
//			try {
//				String output = changeSuffix(file, ".swf");
//				Transformer.toSWF(new FileInputStream(file),
//						new FileOutputStream(new File(output)));
//			} catch (FileNotFoundException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			}
		}

		private String changeSuffix(File file, String suffix) {
			String output = file.getAbsolutePath();
			return output.substring(0, output.lastIndexOf('.')) + suffix;
		}

		/**
		 *
		 */
		private void clearTestArea() {
			viewArea.setText("");
		}

		private void addRecentFile(File file) {
			app.addRecentFile(file);
		}

		/**
		 * 
		 * @param file
		 */
		private void getData(File f) {
			// handle xml
			String xmlPath = changeSuffix(f, ".xml");

//			SAXReader saxReader = new SAXReader();
//			Document doc;
//			try {
//				doc = saxReader.read(xmlPath);
//				String xpath = "//swfdocument/defineshape4";
//				List list = doc.selectNodes(xpath);
//				Iterator it = list.iterator();
//				while (it.hasNext()) {
//					Element e = (Element) it.next();
//
//				}
//			} catch (DocumentException e1) {
//				e1.printStackTrace();
//			}

			long length = f.length();
			InputStream is;
			msgLabel.setText("file length:  " + length);

			StringBuffer dataBuffer = new StringBuffer();
			Formatter dataFormatter = new Formatter(dataBuffer);

			int lineCounter = 0;
			StringBuffer lineBuffer = new StringBuffer();
			Formatter lineFormatter = new Formatter(lineBuffer);
			// byte[] buffer=new byte[16];

			try {
				is = new FileInputStream(f);
				int isOutput = 0;
				int ch = 0;
				while ((ch = is.read()) != -1) {
					dataFormatter.format("%02x ", ch);
					// System.out.printf("%02x ",ch);
					if ((++isOutput) % 16 == 0) {
						dataFormatter.out();
						dataBuffer.setLength(0);

						lineFormatter.format("%06x%n", lineCounter++);
						lineFormatter.out();
						lineBuffer.setLength(0);
					}
				}

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		@Override
		public String getID() {
			return ID;
		}
		
		private javax.swing.JFileChooser jFileChooser1;
	}

	/** Creates new form BinaryEditor */
	public SXView() {
		initComponents();
	}

	/**
	 * @see org.jtool.app.AbsView#start()
	 */
	@Override
	public void start() {
		super.start();
		jButton2.setAction(new SXOpenAction(getApplication()));
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jPanel2 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();
		jToolBar2 = new javax.swing.JToolBar();
		countLabel = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JToolBar.Separator();
		msgLabel = new javax.swing.JLabel();
		jPanel3 = new javax.swing.JPanel();
		jSplitPane1 = new javax.swing.JSplitPane();
		jSplitPane2 = new javax.swing.JSplitPane();
		jScrollPane3 = new javax.swing.JScrollPane();
		viewArea = new javax.swing.JTextArea();
		jScrollPane2 = new javax.swing.JScrollPane();
		jList1 = new javax.swing.JList();
		jPanel4 = new javax.swing.JPanel();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();

		setLayout(new java.awt.BorderLayout());

		jPanel2.setLayout(new java.awt.BorderLayout());

		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("选择文件所在目录"));
		jPanel1.setMinimumSize(new java.awt.Dimension(600, 400));
		jPanel1.setPreferredSize(new java.awt.Dimension(711, 500));

		jButton1.setText("保存");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("浏览");

		jToolBar2.setRollover(true);

		countLabel.setText("0 rows");
		jToolBar2.add(countLabel);

		jSeparator1.setPreferredSize(new java.awt.Dimension(8, 20));
		jToolBar2.add(jSeparator1);

		msgLabel.setText("当前没有文件");
		jToolBar2.add(msgLabel);

		jSplitPane1.setDividerLocation(100);
		jSplitPane1.setDividerSize(8);
		jSplitPane1.setAutoscrolls(true);
		jSplitPane1.setContinuousLayout(true);
		jSplitPane1.setLastDividerLocation(500);
		jSplitPane1.setOneTouchExpandable(true);
		jSplitPane1.setPreferredSize(new java.awt.Dimension(605, 400));

		jSplitPane2.setDividerLocation(500);

		viewArea.setColumns(32);
		viewArea.setRows(5);
		viewArea.setEnabled(false);
		jScrollPane3.setViewportView(viewArea);

		jSplitPane2.setRightComponent(jScrollPane3);

		jList1.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4",
			"Item 5" };

			public int getSize() {
				return strings.length;
			}

			public Object getElementAt(int i) {
				return strings[i];
			}
		});
		jScrollPane2.setViewportView(jList1);

		jSplitPane2.setRightComponent(jScrollPane2);

		javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(
				jPanel4);
		jPanel4.setLayout(jPanel4Layout);
		jPanel4Layout.setHorizontalGroup(jPanel4Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100,
						Short.MAX_VALUE));
		jPanel4Layout.setVerticalGroup(jPanel4Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 492,
						Short.MAX_VALUE));

		jSplitPane2.setLeftComponent(jPanel4);

		jSplitPane1.setRightComponent(jSplitPane2);

		jTextArea1.setColumns(20);
		jTextArea1.setLineWrap(true);
		jTextArea1.setRows(5);
		jTextArea1.setWrapStyleWord(true);
		jScrollPane1.setViewportView(jTextArea1);

		jSplitPane1.setLeftComponent(jScrollPane1);

		javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(
				jPanel3);
		jPanel3.setLayout(jPanel3Layout);
		jPanel3Layout.setHorizontalGroup(jPanel3Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 781,
						Short.MAX_VALUE).addGroup(
								jPanel3Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSplitPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE, 781,
												Short.MAX_VALUE)));
		jPanel3Layout.setVerticalGroup(jPanel3Layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 496,
						Short.MAX_VALUE).addGroup(
								jPanel3Layout.createParallelGroup(
										javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jSplitPane1,
												javax.swing.GroupLayout.DEFAULT_SIZE, 496,
												Short.MAX_VALUE)));

		javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(
				jPanel1);
		jPanel1.setLayout(jPanel1Layout);
		jPanel1Layout
		.setHorizontalGroup(jPanel1Layout
				.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
								.createSequentialGroup()
								.addComponent(jButton1)
								.addPreferredGap(
										javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jButton2)
										.addPreferredGap(
												javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(
														jTextField1,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														655, Short.MAX_VALUE))
														.addGroup(
																jPanel1Layout
																.createSequentialGroup()
																.addComponent(
																		jToolBar2,
																		javax.swing.GroupLayout.PREFERRED_SIZE,
																		750,
																		javax.swing.GroupLayout.PREFERRED_SIZE)
																		.addContainerGap()).addComponent(
																				jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE,
																				javax.swing.GroupLayout.DEFAULT_SIZE,
																				Short.MAX_VALUE));
		jPanel1Layout
		.setVerticalGroup(jPanel1Layout
				.createParallelGroup(
						javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(
								jPanel1Layout
								.createSequentialGroup()
								.addGroup(
										jPanel1Layout
										.createParallelGroup(
												javax.swing.GroupLayout.Alignment.BASELINE)
												.addComponent(jButton1)
												.addComponent(jButton2)
												.addComponent(
														jTextField1,
														javax.swing.GroupLayout.PREFERRED_SIZE,
														23,
														javax.swing.GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(
																javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																.addComponent(
																		jPanel3,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		Short.MAX_VALUE)
																		.addPreferredGap(
																				javax.swing.LayoutStyle.ComponentPlacement.RELATED)
																				.addComponent(
																						jToolBar2,
																						javax.swing.GroupLayout.PREFERRED_SIZE,
																						javax.swing.GroupLayout.DEFAULT_SIZE,
																						javax.swing.GroupLayout.PREFERRED_SIZE)));

		jPanel2.add(jPanel1, java.awt.BorderLayout.CENTER);

		add(jPanel2, java.awt.BorderLayout.CENTER);
	}// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
	}// GEN-LAST:event_jButton1ActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel countLabel;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JList jList1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JPanel jPanel3;
	private javax.swing.JPanel jPanel4;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JScrollPane jScrollPane3;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JSplitPane jSplitPane2;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JToolBar jToolBar2;
	private javax.swing.JLabel msgLabel;
	private javax.swing.JTextArea viewArea;
	// End of variables declaration//GEN-END:variables
}
