/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DailyView.java
 *
 * Created on 2010-2-8, 22:56:48
 */

package org.daily;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.swing.Action;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledEditorKit;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.undo.UndoManager;

import org.daily.actions.DeleteAction;
import org.daily.actions.LineWrapAction;
import org.divine.actions.AboutAction;
import org.jtools.app.persistent.DataException;
import org.zhiwu.app.ApplicationConstant;
import org.zhiwu.app.DocumentView;
import org.zhiwu.app.Model;
import org.zhiwu.app.action.ConfigAction;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.app.action.NewAction;
import org.zhiwu.app.action.RedoAction;
import org.zhiwu.app.action.SaveAction;
import org.zhiwu.app.action.UndoAction;
import org.zhiwu.app.action.VersionAction;
import org.zhiwu.app.action.ViewSwitchAction;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.DateUtils;
import org.zhiwu.xml.DataSourceException;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class DailyView extends DocumentView {
	public static final String PROPERTY_LINE_WRAP="line.wrap";

	private DailyManager manager;
	private JTextPane editor;
	private EditorPanel editorViewport;
	private DefaultStyledDocument doc;
	private Style DEFAULT_STYLE;
	private Daily daily;
	
	public Action undoAction;
	public Action redoAction;
	public Action deleteAction;
	
	private JTree jTree1;
	private JScrollPane jScrollPane4;
	
	
	private final UndoManager undoManager=new UndoManager(){
		private static final long serialVersionUID = -499954525136930182L;

		@Override
		public void undoableEditHappened(javax.swing.event.UndoableEditEvent e) {
			this.addEdit(e.getEdit());
		};
	};

	private final DocumentListener dl=new DocumentListener() {
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			updateDocument();
			
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			updateDocument();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			updateDocument();
		}
	};
    public DailyView() {
    	prepareData();
    	initPrep();
        initComponents();
        
        initTree();
        initTable();
        initManager();
        initEditor();
    }
    
	@SuppressWarnings("serial")
	private void initTree() {
		HandleNode node=new HandleNode("personality") ;
		HandleNode dailyNode=new HandleNode("日志"){
			@Override
			public void handle() {
				try {
					((DailyTableModel)jTable1.getModel()).setData(manager.getAllDaily());
				} catch (DataSourceException e) {
					e.printStackTrace();
				} catch (DataException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		node.add(dailyNode);
		
		dailyNode=new HandleNode("回收站"){
			@Override
			public void handle() {
				try {
					((DailyTableModel)jTable1.getModel()).setData(manager.getDelete());
				} catch (DataSourceException e) {
					e.printStackTrace();
				} catch (DataException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		node.add(dailyNode);
		
		
		jTree1=new JTree(new DefaultTreeModel(node));
		jTree1.setRootVisible(false);
		jScrollPane4=new JScrollPane();
		jSplitPane2.add(jScrollPane4, JSplitPane.LEFT);
		jScrollPane4.setViewportView(jTree1);
		jTree1.setSelectionRow(0);
		jTree1.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				TreePath p = e.getPath();
				HandleNode node = (HandleNode) jTree1
						.getLastSelectedPathComponent();
				if (!node.isLeaf()) {
					return;
				}
				node.handle();
			}
		});
	        
	}

	protected void prepareData() {
		initDaily();
		manager.checkPrepare();
	}
    
	private void initePopupMenu() {
		// editor menu.
		final JPopupMenu editorMenu=new JPopupMenu();
		editorMenu.add(getAction(NewAction.ID));
		editorMenu.add(getAction(SaveAction.ID));
		editor.add(editorMenu);
		editor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()){
					editorMenu.show(editor, e.getX(), e.getY());
				}
			}
		});
		
		// table menu
		final JPopupMenu tableMenu=new JPopupMenu();
		tableMenu.add(getAction(DeleteAction.ID));
		jTable1.add(tableMenu);
		jTable1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(e.isPopupTrigger()){
					tableMenu.show(jTable1, e.getX(), e.getY());
				}
			}
		});
		
	}

	/**
	 * 
	 */
	protected void updateDocument() {
		jLabel2.setText(doc.getLength()+"");
		updateUndoAction();
		setUnsaveChanged(true);	
	}

	/**
	 * 
	 */
	private void updateUndoAction() {
		undoAction.setEnabled(undoManager.canUndo());
		redoAction.setEnabled(undoManager.canRedo());
	}

	/**
	 * 
	 */
	private void initDaily() {
		try {
			manager=new DailyManager();
		} catch (DataException e) {
			e.printStackTrace();
		}
		setDaily(new Daily());
	}

	/**
	 * 
	 */
	private void initPrep() {
	}

	/**
	 * 
	 */
	private void initEditor() {
		editor=new JTextPane();
		editor.setBackground(new Color(221, 233, 242));
		editorViewport=new EditorPanel();
		editorViewport.setEditor(editor);
		jScrollPane3.setViewportView(editorViewport);
		
		StyleContext sc = new StyleContext();
		DEFAULT_STYLE = sc.getStyle(StyleContext.DEFAULT_STYLE);
		DEFAULT_STYLE = sc.addStyle("MainStyle", DEFAULT_STYLE);
        StyleConstants.setFontFamily(DEFAULT_STYLE, "宋体");
		StyleConstants.setLeftIndent(DEFAULT_STYLE, 16);
		StyleConstants.setRightIndent(DEFAULT_STYLE, 16);
		StyleConstants.setFirstLineIndent(DEFAULT_STYLE, 16);
		StyleConstants.setFontFamily(DEFAULT_STYLE, "serif");
		StyleConstants.setFontSize(DEFAULT_STYLE, 14); 
		// StyleConstants.setForeground(DEFAULT_STYLE, Color.RED);
		doc = new DefaultStyledDocument(sc);
		doc.setLogicalStyle(0, DEFAULT_STYLE);
		doc.setParagraphAttributes(0, 1, ((StyledEditorKit) editor.getEditorKit()).getInputAttributes(), true);
		editor.setDocument(doc);
		
		editor.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				DailyView.this.updateCaret(e);
			}
		});
		
		
		
		jScrollPane3.getViewport().setBackground(editor.getBackground());
		jScrollPane3.getViewport().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				editor.requestFocus();
				editor.setCaretPosition(doc.getLength());
			}
		});
		AppPreference pref=PreferenceManager.getInstance().getPreference(getClass().getName());
		editorViewport.setLineWrap(pref.getBoolean(PROPERTY_LINE_WRAP));

		
		jPanel2.setPreferredSize(new Dimension(1, 400));
		setPreferredSize(new Dimension(600, 400));
		initContent();
		
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if(! evt.getPropertyName().equals(ApplicationConstant.VIEW_DATA)){
					return;
				}
				try {
					clearDocument();
					
					if(daily.getContent() .length() > 0){
						daily = (Daily) ((DailyTableModel) jTable1.getModel())
								.getObject(getTableSelectedRow());
						byte[] docBytes = new BASE64Decoder()
								.decodeBuffer(daily.getContent());
						ObjectInputStream ois = new ObjectInputStream(
								new ByteArrayInputStream(docBytes));
						doc = (DefaultStyledDocument) ois.readObject();
					}
					
					setStyleDocument();
				} catch (BadLocationException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}

			
		});
	}

	
	private void setStyleDocument() {
		doc.addDocumentListener(dl);
		doc.addUndoableEditListener(undoManager);	
		editor.setDocument(doc);
		
		jLabel2.setText(doc.getLength()+"");
	}
	/**
	 * 
	 */
	protected void clearDocument() throws BadLocationException {
		doc.removeDocumentListener(dl);
		doc.removeUndoableEditListener(undoManager);
		
		if(doc.getLength() >0){
				doc.remove(0, doc.getLength()-1);
		}
	}

	/**
	 * 
	 */
	private void initContent() {
		try {
			clearDocument();
			doc.insertString(0,
					"\n\n"
							+ DateUtils.format(
									Calendar.getInstance().getTime(),
									"yyyy-MM-dd "), DEFAULT_STYLE);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
		undoManager.discardAllEdits();
		doc.addDocumentListener(dl);
		doc.addUndoableEditListener(undoManager);
		editor.setCaretPosition(0);
		setUnsaveChanged(false);
	}

	/**
	 * @param e
	 */
	protected void updateCaret(CaretEvent e) {
		Point p=getCaretDimension(e);
		jLabel1.setText(p.x+":"+p.y);
		
	}
 
	/**
	 * 
	 */
	private void initManager() {
	}
	
	
	/* (non-Javadoc)
     * @see org.zhiwu.app.AbsView#initActions()
     */
    @Override
    protected void initActions() {
    	putAction(NewAction.ID, new NewAction(app));
    	putAction(SaveAction.ID, new SaveAction(app));
    	undoAction=new UndoAction(app);
    	redoAction=new RedoAction(app);
    	putAction(UndoAction.ID, undoAction);
    	putAction(RedoAction.ID, redoAction);
    	putAction(DeleteAction.ID, new DeleteAction(app));
    	putAction(VersionAction.ID, new VersionAction(app));
    	
    	initePopupMenu();
    }
    
    /* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#createMenus()
	 */
	@Override
	public JMenuBar createMenus() {
		JMenuBar menuBar=new JMenuBar();
		AppResources r = app.getResource();
		Model model=app.getModel();

		// file
		JMenu fileMenu=new JMenu();
		r.configMenu(fileMenu, "file");
		fileMenu.add(getAction(NewAction.ID));
		fileMenu.add(getAction(SaveAction.ID));
		fileMenu.add(model.getAction(ExitAction.ID));
		menuBar.add(fileMenu);
		
		//edit
		JMenu editMenu=new JMenu();
		r.configMenu(editMenu, "edit");
		editMenu.add(undoAction);
		editMenu.add(redoAction);
		editMenu.addSeparator();
		editMenu.add(getAction(DeleteAction.ID));
		menuBar.add(editMenu);
		
		// tool
		JMenu toolMenu=new JMenu();
		r.configMenu(toolMenu, "tool");
		for(Iterator<String> i=model.viewIterator();i.hasNext();){
			toolMenu.add(new ViewSwitchAction(app,i.next()));
		}
		toolMenu.add(model.getAction(ConfigAction.ID));
		menuBar.add(toolMenu);
		
		// help
		JMenu helpMenu=new JMenu();
		r.configMenu(helpMenu, "help");
		helpMenu.add(getAction(VersionAction.ID));
		helpMenu.add(model.getAction(AboutAction.ID));
		menuBar.add(helpMenu);

		return menuBar;
	}
	

    /**
	 * 
	 */
	private void initTable() {
		String[] title=new String[]{"星级","标题","时间","标签"};
		
		try {
			jTable1=new JTable(new DailyTableModel<Daily> (manager.getAllDaily(),title));
			jTable1.setAutoCreateRowSorter(true);
			TableColumnModel tcm=jTable1.getColumnModel();
			tcm.getColumn(ColumnStar).setPreferredWidth(1);// star level
			tcm.getColumn(ColumnTitle).setPreferredWidth(200);// title
			tcm.getColumn(ColumnDate).setPreferredWidth(55);// date
		} catch (DataSourceException e) {
			e.printStackTrace();
		} catch (DataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		jTable1.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent e) {
				int r=getTableSelectedRow();
				if(r == -1){
					return;
				}
//				if (e.getClickCount() ==2) {
					DailyTableModel<Daily> m = (DailyTableModel<Daily>) jTable1
							.getModel();
					setDaily((Daily)m.getObject(r));
//				}
			}
		});
		
		jTable1.getModel().addTableModelListener(new TableModelListener() {
			
			@Override
			public void tableChanged(TableModelEvent e) {
				System.out.println(e);
			}
		});
		
		jScrollPane2.setViewportView(jTable1);
	}

	/**
	 * @return
	 */
	protected int getTableSelectedRow() {
		try {
			return jTable1.convertRowIndexToModel(jTable1.getSelectedRow());
		} catch (Exception e) {
			return -1;
		}
	}
	
	/**
	 * @return
	 */
	private int[] getTableSelectedRows() {
		int[] rows=jTable1.getSelectedRows();
		int[] result=new int[rows.length];
		for(int i=0,j=rows.length;i<j;i++){
			result[i]=jTable1.convertRowIndexToModel(rows[i]);
		}
		
		return result;
	}
	

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTree2 = new javax.swing.JTree();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(150, 425));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jTextField1.setText("搜索");
        jToolBar1.add(jTextField1);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        jPanel1.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jToolBar3.setRollover(true);
        jToolBar3.setPreferredSize(new java.awt.Dimension(400, 17));

        jLabel1.setText("光标位置");
        jLabel1.setMaximumSize(new java.awt.Dimension(100, 15));
        jLabel1.setMinimumSize(new java.awt.Dimension(100, 15));
        jLabel1.setPreferredSize(new java.awt.Dimension(100, 15));
        jToolBar3.add(jLabel1);
        jToolBar3.add(jSeparator1);

        jLabel2.setText("总字数");
        jLabel2.setPreferredSize(new java.awt.Dimension(100, 15));
        jToolBar3.add(jLabel2);

        jPanel2.add(jToolBar3, java.awt.BorderLayout.PAGE_END);

        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jPanel2.add(scrollPane, java.awt.BorderLayout.CENTER);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "分类1", "分类2" }));
        jPanel3.add(jComboBox1, java.awt.BorderLayout.WEST);

        jTextField2.setText("标题");
        jPanel3.add(jTextField2, java.awt.BorderLayout.CENTER);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "私人", "公开" }));
        jPanel3.add(jComboBox2, java.awt.BorderLayout.EAST);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);

        jScrollPane1.setViewportView(jPanel2);

        jSplitPane1.setRightComponent(jScrollPane1);

        jSplitPane2.setRightComponent(jSplitPane1);

        jScrollPane3.setViewportView(jTree2);

        jSplitPane2.setLeftComponent(jScrollPane3);

        add(jSplitPane2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JTree jTree2;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

    
    /* (non-Javadoc)
     * @see org.zhiwu.app.AbsView#create()
     */
    @Override
    public void create() {
    	if (hasUnsavChanged()) {
			save();
		}
    	initContent();
    	daily=new Daily();
    	
    }
    
    
    /**
	 * @param daily2
	 */
	private void setDaily(Daily newValue) {
		firePropertyChange(ApplicationConstant.VIEW_DATA, daily, daily=newValue);
	}

	/* (non-Javadoc)
     * @see org.zhiwu.app.AbsView#getToolbar()
     */
    @Override
    public JToolBar getToolbar() {
		AppResources r = app.getResource();
    	JToolBar bar=new JToolBar();
    	bar.add(getAction(NewAction.ID));
    	
    	final JCheckBox lineWrap=new JCheckBox(getAction(LineWrapAction.ID));
    	lineWrap.setHorizontalAlignment(SwingConstants.RIGHT);
    	lineWrap.setVerticalTextPosition(SwingConstants.CENTER);
    	lineWrap.setText(r.getString(LineWrapAction.ID));
    	lineWrap.setSelected(editorViewport.getLineWrap());
    	lineWrap.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				editorViewport.setLineWrap(lineWrap.isSelected());
			}
		});
    	bar.add(lineWrap);
    	
    	return bar;
    }

	/**
	 * @return
	 */
	public boolean getLineWrap() {
		return editorViewport.getLineWrap();
	}

	/**
	 * @param b
	 */
	public void setLineWrap(boolean b) {
		editorViewport.setLineWrap(b);
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return undoManager.canUndo();
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#canRedo()
	 */
	@Override
	public boolean canRedo() {
		return undoManager.canRedo();
	}
    
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#undo()
	 */
	@Override
	public void undo() {
		undoManager.undo();
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#redo()
	 */
	@Override
	public void redo() {
		undoManager.redo();
	}
	
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#save()
	 */
	@Override
	public void save() {
		prepareDaily();
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream oos;
		
		doc.removeDocumentListener(dl);
		doc.removeUndoableEditListener(undoManager);
		
		try {
			oos=new ObjectOutputStream(baos);
			oos.writeObject(doc);
			baos.flush();
			baos.close();
			oos.flush();
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		daily.setContent(new BASE64Encoder().encode(baos.toByteArray()));
		
		try {
			
			String  text=doc.getText(0, doc.getLength()-1);
			String[] strings=text.split("\n");
			String title="";
			for(String s:strings){
				if(s.trim().length()>0){
					title +=s;
					break;
				}
			}
			daily.setTitle(title);
			manager.saveObject(daily);
			manager.saveToFile(daily,text);
			updateView();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (BadLocationException e) {
			e.printStackTrace();
		}

		setUnsaveChanged(false);
		doc.addDocumentListener(dl);
		doc.addUndoableEditListener(undoManager);
	}

	

	/**
	 * 
	 */
	private void updateView() {
		((DailyTableModel )jTable1.getModel()).add(daily);//save to model
		jTable1.repaint();	
	}

	/**
	 * 
	 */
	private void prepareDaily() {
		if(daily.getTitle() == null){
			String title;
			try {
				for(int i=1;;i++){
					title=doc.getText(0, getLineStartOffset(editor, i));
					if(!title.equals("")){
						daily.setTitle(title);
					break;
					}
				}
			} catch (BadLocationException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 */
	public void delete() {
		int[] selectRows=getTableSelectedRows();
		if(selectRows.length ==0){
			return;
		}
		@SuppressWarnings("unchecked")
		DailyTableModel<Daily> m=(DailyTableModel<Daily>) jTable1.getModel();
		final List<Daily> selectDailies=new ArrayList<Daily>(selectRows.length);
		
		for(int r:selectRows){
			Daily d=(Daily) m.getObject(r);
			if(d==null){
				break;
			}
			selectDailies.add(d);
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				manager.delete(selectDailies);
			}
		});
		m.remove(selectDailies);
		jTable1.repaint();
	}
	
	public static int ColumnID=5;
	public static final  int ColumnStar=0;
	public static final int ColumnTitle=1;
	public static final  int ColumnDate=2;
	public static final int ColumnTags=3;
}

class EditorPanel extends JPanel implements Scrollable{
	private static final long serialVersionUID = -8501742801859531924L;
	private JTextComponent editor;
	private boolean isLineWrap;
	
	public void setEditor(JTextComponent newValue){
		editor=newValue;
		removeAll();
		setLayout(new BorderLayout());
		add(editor);
		setBackground(UIManager.getColor("TextField.background"));
		setOpaque(true);
	}
	
	public void setLineWrap(boolean newValue){
		isLineWrap=newValue;
		editor.revalidate();
		editor.repaint();
	}

	public boolean getLineWrap(){
		return isLineWrap;
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return editor.getPreferredScrollableViewportSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return editor.getScrollableUnitIncrement(visibleRect, orientation,
				direction);
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		return editor.getScrollableBlockIncrement(visibleRect, orientation,
				direction);
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return isLineWrap;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return editor.getScrollableTracksViewportHeight();
	}
}

class DailyTableModel<T extends IStorable> extends AbstractTableModel{
	private static final long serialVersionUID = 3586728234537132988L;
	private List<T> data;
	private final String[] columns;
	
	public DailyTableModel(List<T> data,String[] columns){
		this.data=new ArrayList<T>(data);
		this.columns=columns;
	}
	
	public void setData(List<T> data){
		this.data=new ArrayList<T>(data);
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	public void remove(List<T> value) {
		for(T t:value){
			remove(t);
		}
	}

	public void remove(T t) {
		data.remove(t);
	}


	public Object getObject(int r) {
		String id=getValueAt(r, DailyView.ColumnID).toString();
		for(T t:data){
			if(t.getID().equals(id)){
				return t;
			}
		}
		return null;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}
	
	
	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(rowIndex<0 || rowIndex>=data.size()){
			return null;
		}
		Daily d=(Daily) data.get(rowIndex);
		
		switch (columnIndex) {
		case DailyView.ColumnStar:
			return d.getStar();
		case DailyView.ColumnTitle:
			return d.getTitle();
		case DailyView.ColumnDate:
			return d.getDate();
		case DailyView.ColumnTags:
			return d.getTags();
		default:
			return d.getID();
		}
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		Daily d=(Daily) data.get(rowIndex);

		switch (columnIndex) {
		case DailyView.ColumnStar:
			d.setStar(aValue.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;
			
		case DailyView.ColumnTitle:
			d.setTitle(aValue.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;

		case DailyView.ColumnDate:
			d.setDate(aValue.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;

		case DailyView.ColumnTags:
			d.setTags(aValue.toString());
			fireTableCellUpdated(rowIndex, columnIndex);
			break;

		default:
			break;
		}
	}
	
	public void add(T t){
		for(int i=0,j=data.size();i<j;i++){
			T d=data.get(i);
			if(d.getID().equals(t.getID())){
				data.remove(d);
				data.add(t);
				return;
			}
		}
		data.add(0,t);
		fireTableDataChanged();
	}
}

