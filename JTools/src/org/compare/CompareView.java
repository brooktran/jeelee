/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CompareView.java
 *
 * Created on 2010-1-28, 15:26:40
 */

package org.compare;

import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.compare.action.CompareAction;
import org.compare.action.NewAction;
import org.compare.action.PlayAcion;
import org.compare.action.RetryAction;
import org.compare.action.SaveAction;
import org.zhiwu.app.DocumentView;
import org.zhiwu.utils.DateUtils;

/**
 * <B>CompareView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-2 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class CompareView extends DocumentView {
	private CompareEditor editor;
	private final JTextPane targetPane;
	private final JTextArea sourceArea;
	private final JTextArea translatedArea;
	private final JTextArea commentArea;
	private final JTextField titleField;

	/** Creates new form CompareView */
	public CompareView() {
		initComponents();

		titleField = new JTextField();

		sourceArea = new JTextArea();
		sourceArea.setLineWrap(true);

		translatedArea = new JTextArea();
		translatedArea.setLineWrap(true);

		commentArea = new JTextArea();
		commentArea.setLineWrap(true);

		StyleContext sc = new StyleContext();
		final DefaultStyledDocument doc = new DefaultStyledDocument(sc);

		targetPane = new JTextPane(doc);

		// Create and add the main docment style
		Style defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		final Style mainStyle = sc.addStyle("MainStyle", defaultStyle);
		StyleConstants.setLeftIndent(mainStyle, 16);
		StyleConstants.setRightIndent(mainStyle, 16);
		StyleConstants.setFirstLineIndent(mainStyle, 16);
		StyleConstants.setFontFamily(mainStyle, "serif");
		StyleConstants.setFontSize(mainStyle, 12);

		// set the logical style
		doc.setLogicalStyle(0, mainStyle);
	}

	public CompareEditor getEditor() {
		return editor;
	}

	/**
	 * 
	 * @param l
	 */
	public void addCaretListener(CaretListener l) {
		targetPane.addCaretListener(l);
		sourceArea.addCaretListener(l);
		commentArea.addCaretListener(l);
		translatedArea.addCaretListener(l);
	}

	/**
	 * @see org.zhiwu.app.AbsView#init()
	 */
	@Override
	public void init() {
		super.init();

		editor = new CompareEditor();
		jList1.setModel(new XMLListModel(editor.getData()));
		jList1.setCellRenderer(new XMLCellRender());

		CaretListener l = new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				CompareView.this.caretUpdate(e);
			}
		};
		addCaretListener(l);
		jTabbedPane1.add("target", targetPane);
		jTabbedPane1.add("source", sourceArea);
		jTabbedPane1.add("translatedArea", translatedArea);
		jTabbedPane1.add("commentArea", commentArea);

		jPanel3.add(titleField);
		setSize(500, 500);

		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("enabled")) {
					updateView((Boolean) evt.getNewValue());
				}
			}
		});

		final DocumentListener dl = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				refreshEditor();
				isChanged = true;
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				refreshEditor();
				isChanged = true;
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				refreshEditor();
				isChanged = true;
			}
		};
		addDocumentListener(dl);

		jList1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount() == 2){
					Dictate d=(Dictate) jList1.getModel().getElementAt(jList1.getSelectedIndex());
					removeDocumentListener(dl);
					editor.setDictate(d);
					titleField.setText(d.title);
					targetPane.setText(d.target);
					sourceArea.setText(d.source);
					translatedArea.setText(d.translated);
					commentArea.setText(d.comment);
					addDocumentListener(dl);
					//CompareView.this.isChanged=false;
				}
			}
		});
	}

	/**
	 * @param e
	 */
	protected void caretUpdate(CaretEvent e) {
		Point p=getCaretDimension(e);
		caretLabel.setText(p.x+":"+p.y);
	}

	/**
	 * 
	 * @param dl
	 */
	private void removeDocumentListener(DocumentListener dl) {
		targetPane.getDocument().removeDocumentListener(dl);
		sourceArea.getDocument().removeDocumentListener(dl);
		translatedArea.getDocument().removeDocumentListener(dl);
		commentArea.getDocument().removeDocumentListener(dl);
		titleField.getDocument().removeDocumentListener(dl);
	}

	/**
	 * 
	 * @param dl
	 */
	private void addDocumentListener(DocumentListener dl) {
		targetPane.getDocument().addDocumentListener(dl);
		sourceArea.getDocument().addDocumentListener(dl);
		translatedArea.getDocument().addDocumentListener(dl);
		commentArea.getDocument().addDocumentListener(dl);
		titleField.getDocument().addDocumentListener(dl);
	}

	private void refreshEditor() {
		Dictate d = editor.getDictate();

		d.title = titleField.getText();
		//		d.date = new Date(System.currentTimeMillis());
		d.target = targetPane.getText();
		d.source = sourceArea.getText();
		d.translated = translatedArea.getText();
		d.comment = commentArea.getText();
	}

	/**
	 * 
	 */
	private void updateView(boolean b) {
		JTextComponent c;
		for (int i = 0, n = jTabbedPane1.getComponentCount(); i < n; i++) {
			c=(JTextComponent)jTabbedPane1.getComponent(i);
			c.setEditable(b);
		}
		if (b) {
			targetPane.setText(editor.getDictate().target);
		}
	}

	/**
	 * 
	 * @param doc
	 */
	public void setDocument(Document doc) {
		targetPane.setDocument(doc);
	}

	/**
	 * @see org.zhiwu.app.AbsView#initActions()
	 */
	@Override
	protected void initActions() {
		putAction(CompareAction.ID, new CompareAction(app));
		putAction(NewAction.ID, new NewAction(app));
		putAction(PlayAcion.ID, new PlayAcion(app));
		putAction(SaveAction.ID, new SaveAction(app));
		putAction(RetryAction.ID, new RetryAction(app));
	}

	public void newDictate() {
		Dictate d=new Dictate();
		d.date=new Date();
		editor.setDictate(d);
		titleField.setText(d.title);
		targetPane.setText(d.target);
		sourceArea.setText(d.source);
		translatedArea.setText(d.translated);
		commentArea.setText(d.comment);
		CompareView.this.isChanged=false;
	}

	/**
	 * @see org.zhiwu.app.AbsView#save()
	 */
	@Override
	public void save() {
		if (titleField.getText().length() == 0) {
			JOptionPane.showMessageDialog(null, "Title cannot be null.");
			return;
		}
		editor.save();
		setUnsaveChanged(false);
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        caretLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        caretLabel.setText("0:0");
        jToolBar1.add(caretLabel);

        add(jToolBar1, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setResizeWeight(0.6);
        jSplitPane1.setAutoscrolls(true);
        jSplitPane1.setContinuousLayout(true);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel3.setLayout(new javax.swing.BoxLayout(jPanel3, javax.swing.BoxLayout.LINE_AXIS));

        jLabel1.setText("标题:");
        jPanel3.add(jLabel1);

        jPanel2.add(jPanel3, java.awt.BorderLayout.PAGE_START);
        jPanel2.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel2);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder("我的听写"));

        jScrollPane1.setViewportView(jList1);

        jSplitPane1.setRightComponent(jScrollPane1);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel caretLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables
	private static final long serialVersionUID = -1420113503869151648L;

	class XMLListModel extends AbstractListModel {
		private static final long serialVersionUID = 1L;
		private final List<Dictate> data;

		/**
		 * 
		 * @since JCompareEditor
		 */
		public XMLListModel(List<Dictate> data) {
			this.data = data;
		}

		/** 
		 * @see javax.swing.ListModel#getElementAt(int)
		 */
		@Override
		public Object getElementAt(int index) {
			return data.get(index);
		}

		/**
		 * @see javax.swing.ListModel#getSize()
		 */
		@Override
		public int getSize() {
			return data.size();
		}


	}
}

class XMLCellRender extends JLabel implements ListCellRenderer {
	private static final long serialVersionUID = 8248251936296552910L;

	/**
	 * 
	 * @since JCompareEditor
	 */
	public XMLCellRender() {
		setOpaque(true);
	}

	/**
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		try {
			Dictate d = (Dictate) value;
			setText(d.title + "  " + DateUtils.format(d.date, "yy:MM:dd HH"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		return this;
	}

}
