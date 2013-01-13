/*
 * JLView.java
 *
 * Created on 2009-11-10, 17:47:20
 */
package org.jtool.sample.job;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JFileChooser;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import org.jtool.app.AbsView;
import org.jtool.app.IApplication;
import org.jtool.app.action.AppAction;
import org.jtool.utils.DateUtil;
import org.jtool.utils.LogUtils;
import org.jtool.utils.ResourceUtil;

/**
 * 
 * @author Administrator
 */
public class JLView extends AbsView {

	/**
	 * <B>BEOpenAction</B>
	 * 
	 * @author Brook Tran. Email: <a
	 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
	 * @version Ver 1.0.01 2009-9-26 created
	 * @since map editor Ver 1.0
	 * 
	 */
	class JLOpenAction extends AppAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public static final String ID = "JLOpen";
		private String path = "";

		/**
		 * 
		 * @since map editor
		 * @param app
		 */
		public JLOpenAction(IApplication app) {
			super(app, ID);
			ResourceUtil resource = ResourceUtil.getResourceBundleUtil(Labels);
			resource.configAction(this, ID);
			System.out.println(getName());
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// 读取文件
			jFileChooser1 = new javax.swing.JFileChooser();
			List<File> recentFiles = app.recentFiles();

			if (recentFiles.size() > 0) {
				jFileChooser1.setCurrentDirectory(recentFiles.get(0));
			} else {
				jFileChooser1.setCurrentDirectory(new File("."));
			}
			jFileChooser1.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser1.addChoosableFileFilter(new FileNameExtensionFilter(
					"文本文件", "txt"));

			int result = jFileChooser1.showOpenDialog(null);
			jFileChooser1.setFileHidingEnabled(false);

			if (result == JFileChooser.APPROVE_OPTION) {
				DefaultTableModel model = (DefaultTableModel) jTable1
				.getModel();
				while (model.getRowCount() > 0) {
					model.removeRow(0);
				}

				path = jFileChooser1.getSelectedFile().getAbsolutePath();
				File file = new File(path);
				addMessage("\n==打开" + file.getName() + "==");
				addRecentFile(file);
				workList = new ArrayList<Item>();
				getData(file);// 读取文件
				addToLabel();
				setWorkList(workList);
				addMessage("===========================\n");
				app.setTitle(path + "  " + app.getName());
			} else {

				System.out.println("你已取消并关闭了窗口！");
			}
		}

		private void addRecentFile(File file) {
			app.addRecentFile(file);
		}

		private void addToLabel() {
			DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
			for (Item item : workList) {
				try {
					// "姓名", "任务", "开始", "预计", "修改", "完成", "完成", "备注"
					model.insertRow(0, new Object[] { item.getAuthor(),
							item.getGameName(),
							DateUtil.formatDate(item.getStartDate().getTime()),
							DateUtil.formatDate(item.getEndDate().getTime()),
							item.isModify(), item.isFinish(),
							getdate(item.getFinishDate()), item.getMeno() });
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(item);
				}

			}
			if (jTable1.getSelectedColumn() > 0) {
				jTable1
				.removeRowSelectionInterval(0,
						jTable1.getRowCount() - 1);
			}

		}

		/**
		 * 
		 * @return
		 */
		private Object getdate(Calendar calendar) {
			if (calendar != null) {
				return DateUtil.formatDate(calendar.getTime());
			}
			return null;
		}

		/**
		 * 
		 * @param file
		 */
		private void getData(File file) {
			String dateString = "";
			dateString = dateString + file.getName();
			File f;
			for (String fileName : file.list()) {
				f = new File(file.getAbsolutePath() + File.separator + fileName);
				if (f.isFile()) {
					if (fileName.startsWith("【") || fileName.startsWith("[")
							|| (!fileName.endsWith("txt"))) {
						addMessage(fileName);
						continue;
					}
					addItem(fileName);
				} else {
					getData(f);
				}
			}

		}

		/**
		 * 关键方法
		 * 
		 * @param fileName
		 */
		private void addItem(String fileName) {// TODO 关键方法
			// System.out.println(fileName);
			StringTokenizer st = new StringTokenizer(fileName, ".");
			String token;
			Item item = new Item();
			try {
				// f.[实际完成日期].m.[姓名].[新任务名称].[开始日期].[预计结束日期].(备注说明).txt
				token = st.nextToken();
				if (token.equals("f")) {// f.[实际完成日期]
					item.setFinish(true);
					item.setFinishDate(getDate(st.nextToken()));
					token = st.nextToken();
				}

				if (token.equals("m")) {// m.
					item.setModify(true);
					token = st.nextToken();
				}

				item.setAuthor(token); // [姓名].
				item.setGameName(st.nextToken()); // [新任务名称].
				Calendar c = getDate(st.nextToken());
				item.setStartDate(c); // [开始日期].
				token = st.nextToken();

				try {// [预计结束日期].
					item.setEndDate(getDate(token));
				} catch (Exception e) {
					addMessage("已修正: " + fileName + "\n    " + e.getMessage());
					item.setEndDate(c);
				}

				if (st.hasMoreTokens()) { // (备注说明).txt
					token = st.nextToken();
					if (!token.equals("txt")) {
						item.setMeno(token);
					}

				}

				workList.add(item);
			} catch (Exception e) {
				// e.printStackTrace();
				addMessage(fileName + "\n    " + e.getMessage());
			}

		}

		/**
		 * 
		 * @param nextToken
		 * @return
		 */
		private Calendar getDate(String dateString) {
			Calendar c = Calendar.getInstance();
			StringTokenizer st = new StringTokenizer(dateString, "-");
			c.set(c.get(Calendar.YEAR), Integer.parseInt(st.nextToken()) - 1,
					Integer.parseInt(st.nextToken()));
			return c;
		}

		private javax.swing.JFileChooser jFileChooser1;
		private List<Item> workList;

	}

	private static final long serialVersionUID = 1L;
	private List<Item> workList;// 所有数据保存于此

	/** Creates new form JLView */
	public JLView() {
		initComponents();

		jTable1.getColumnModel().getColumn(0).setPreferredWidth(55);
		jTable1.getColumnModel().getColumn(1).setPreferredWidth(80);
		jTable1.getColumnModel().getColumn(2).setPreferredWidth(30);
		jTable1.getColumnModel().getColumn(3).setPreferredWidth(120);
		jTextArea1.setLineWrap(true);

		// jButton1.addActionListener(new ActionListener(){
		//
		// public void actionPerformed(ActionEvent e) {
		// jButton1ActionPerformed(e);
		// }
		//
		// });
		init();
	}

	/**
	 * @see org.jtool.app.AbsView#start()
	 */
	@Override
	public void start() {
		if (getApplication() == null) {
			throw new IllegalArgumentException();
		}
		jButton2.setAction(new JLOpenAction(getApplication()));
		jTable1.getModel().addTableModelListener(new TableModelListener() {

			public void tableChanged(TableModelEvent e) {
				DefaultTableModel model = (DefaultTableModel) e.getSource();
				countLabel.setText(model.getRowCount() + " rows    ");
				msgLabel.setText("未保存");
			}
		});

		jTable1.addMouseListener(new MouseAdapter() {
			/**
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);

				int row = jTable1.getSelectedRow();
				jTextField1.setText(rowToFilename(row));
			}

		});
		super.start();
	}

	private String rowToFilename(int row) {
		StringBuffer buffer = new StringBuffer();
		DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

		boolean isFinish = (Boolean) model.getValueAt(row, 5);
		if (isFinish) {
			buffer.append("f.");
			String fDate = (String) model.getValueAt(row, 6);
			buffer.append(simpleDateString(fDate) + ".");
		}

		boolean modify = (Boolean) model.getValueAt(row, 4);
		if (modify) {
			buffer.append("m.");
		}

		String author = (String) model.getValueAt(row, 0);
		buffer.append(author + ".");
		String task = (String) model.getValueAt(row, 1);
		buffer.append(task + ".");
		String start = (String) model.getValueAt(row, 2);
		buffer.append(simpleDateString(start) + ".");
		String end = (String) model.getValueAt(row, 3);
		buffer.append(simpleDateString(end) + ".");

		String meno = (String) model.getValueAt(row, 7);
		if (meno.length() != 0) {
			buffer.append(meno + ".");
		}
		buffer.append("txt");

		return buffer.toString();

	}

	/**
	 * 
	 * @param date
	 *            yy-mm-dd
	 * @return mm-dd
	 */
	private String simpleDateString(String date) {
		int p = date.indexOf('-');
		return date.substring(p + 1, date.length());
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {

		jFileChooser1 = new javax.swing.JFileChooser();
		jPanel2 = new javax.swing.JPanel();
		jPanel1 = new javax.swing.JPanel();
		jButton1 = new javax.swing.JButton();
		jTextField1 = new javax.swing.JTextField();
		jButton2 = new javax.swing.JButton();
		jSplitPane1 = new javax.swing.JSplitPane();
		jScrollPane2 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTable1 = new javax.swing.JTable();
		jToolBar2 = new javax.swing.JToolBar();
		countLabel = new javax.swing.JLabel();
		jSeparator1 = new javax.swing.JToolBar.Separator();
		msgLabel = new javax.swing.JLabel();

		setAutoscrolls(true);
		setLayout(new java.awt.BorderLayout());

		jPanel2.setLayout(new java.awt.BorderLayout());

		jPanel1.setBorder(javax.swing.BorderFactory
				.createTitledBorder("选择文件所在目录"));
		jPanel1.setMinimumSize(new java.awt.Dimension(600, 400));
		jPanel1.setPreferredSize(new java.awt.Dimension(711, 500));

		jButton1.setText("保存至数据库");
		jButton1.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jButton1ActionPerformed(evt);
			}
		});

		jButton2.setText("浏览");

		jSplitPane1.setDividerLocation(500);
		jSplitPane1.setDividerSize(8);
		jSplitPane1.setAutoscrolls(true);
		jSplitPane1.setContinuousLayout(true);
		jSplitPane1.setLastDividerLocation(500);
		jSplitPane1.setOneTouchExpandable(true);
		jSplitPane1.setPreferredSize(new java.awt.Dimension(605, 400));

		jScrollPane2.setBorder(javax.swing.BorderFactory
				.createTitledBorder("     Console    "));
		jScrollPane2.setToolTipText("错误信息");

		jTextArea1.setColumns(20);
		jTextArea1.setEditable(false);
		jTextArea1.setForeground(new java.awt.Color(153, 0, 51));
		jTextArea1.setRows(5);
		jScrollPane2.setViewportView(jTextArea1);

		jSplitPane1.setRightComponent(jScrollPane2);

		jTable1.setAutoCreateRowSorter(true);
		jTable1.setModel(getTableModel());
		jScrollPane1.setViewportView(jTable1);

		jSplitPane1.setLeftComponent(jScrollPane1);

		jToolBar2.setRollover(true);

		countLabel.setText("0 rows");
		jToolBar2.add(countLabel);

		jSeparator1.setPreferredSize(new java.awt.Dimension(8, 20));
		jToolBar2.add(jSeparator1);

		msgLabel.setText("当前没有文件");
		jToolBar2.add(msgLabel);

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
														586, Short.MAX_VALUE))
														.addComponent(jSplitPane1,
																javax.swing.GroupLayout.Alignment.TRAILING,
																javax.swing.GroupLayout.DEFAULT_SIZE, 760,
																Short.MAX_VALUE)
																.addGroup(
																		jPanel1Layout
																		.createSequentialGroup()
																		.addComponent(
																				jToolBar2,
																				javax.swing.GroupLayout.PREFERRED_SIZE,
																				750,
																				javax.swing.GroupLayout.PREFERRED_SIZE)
																				.addContainerGap()));
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
																javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
																.addComponent(
																		jSplitPane1,
																		javax.swing.GroupLayout.DEFAULT_SIZE,
																		464, Short.MAX_VALUE)
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

	private DefaultTableModel getTableModel() {
		return new javax.swing.table.DefaultTableModel(getTableModelData(),
				getTableModelColumnNames()) {

			Class[] types = getColumnClass();

			@Override
			public Class getColumnClass(int columnIndex) {
				return types[columnIndex];
			}

			private Class[] getColumnClass() {
				return new Class[] { java.lang.String.class,
						java.lang.String.class, java.lang.String.class,
						java.lang.String.class, java.lang.Boolean.class,
						java.lang.Boolean.class, java.lang.String.class,
						java.lang.String.class };
			}
		};
	}

	private Object[][] getTableModelData() {
		return new Object[][] {
				{ null, null, null, null, null, null, null, null },
				{ null, null, null, null, null, null, null, null } };
	}

	private Object[] getTableModelColumnNames() {
		return new String[] { "姓名", "任务", "开始", "预计", "修改", "完成", "完成", "备注" };
	}

	private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton3ActionPerformed
	}// GEN-LAST:event_jButton3ActionPerformed

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton4ActionPerformed
		if (workList == null) {
			addMessage("当前没有数据!!");
			return;
		}
		List<File> recentFiles = getApplication().recentFiles();
		String path = recentFiles.get(0).getAbsolutePath();
		try {
			if (path.equals("")) {
				addMessage("unknow error occur");
				return;
			}
			// 文件名/ 【小游戏】周进度登记表 2009-11-16
			int p = path.lastIndexOf(File.separator);
			// LogUtils log = new LogUtils(path + "/" + path.substring(p) +
			// ".csv");
			Calendar c = Calendar.getInstance();
			StringBuffer saveName = new StringBuffer(); // 新文件名
			saveName.append(path);
			saveName.append(File.separator);
			saveName.append("【小游戏】周进度登记表 ");
			saveName.append(c.get(Calendar.MONTH) + 1);
			saveName.append("-");
			saveName.append(c.get(Calendar.DATE));
			saveName.append(".csv");

			LogUtils log = new LogUtils(saveName.toString());
			// Content
			log.log("姓名,任务,开始时间,预计完成,是否完成,完成时间,修改,备注");
			Iterator<Item> iterator = workList.iterator();
			while (iterator.hasNext()) {
				Item item = iterator.next();
				log.log(item.toString());
			}
			addMessage("保存成功:   " + saveName.toString());
			msgLabel.setText("保存成功");
		} catch (IOException e) {
			e.printStackTrace();
			msgLabel.setText("无法保存，请查看日志文件");
			addMessage("Error: " + e.getMessage());
		}
	}// GEN-LAST:event_jButton4ActionPerformed

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JLabel countLabel;
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JFileChooser jFileChooser1;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JToolBar.Separator jSeparator1;
	private javax.swing.JSplitPane jSplitPane1;
	private javax.swing.JTable jTable1;
	private javax.swing.JTextArea jTextArea1;
	private javax.swing.JTextField jTextField1;
	private javax.swing.JToolBar jToolBar2;
	private javax.swing.JLabel msgLabel;

	// End of variables declaration//GEN-END:variables


	/**
	 * @see org.jtool.app.IView#addMessage(java.lang.String)
	 */
	public void addMessage(String string) {
		jTextArea1.append("**" + string + "\n");
	}

	/**
	 * @see org.jtool.app.IView#setWorkList(java.util.List)
	 */
	public void setWorkList(List<Item> workList) {
		this.workList = workList;
		// jTextArea1.setText("");
	}
}
