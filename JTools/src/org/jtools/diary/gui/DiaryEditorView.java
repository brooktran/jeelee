/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DiaryEditorView.java
 *
 * Created on 2011-6-19, 22:53:19
 */

package org.jtools.diary.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Calendar;

import javax.swing.Action;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledEditorKit;
import javax.swing.undo.UndoManager;

import org.zhiwu.app.AbsView;
import org.zhiwu.app.config.AppPreference;
import org.zhiwu.app.config.PreferenceManager;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.DateUtils;

/**
 * <B>DiaryEditorView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-6-20 created
 * @since org.jtools.diary.gui Ver 1.0
 * 
 */
public class DiaryEditorView extends AbsView{
	
	private JTextPane editor;
	private Style DEFAULT_STYLE;
	private DefaultStyledDocument doc;
	
	public Action undoAction;
	public Action redoAction;
	
	private final UndoManager undoManager=new UndoManager(){
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
	
	protected void updateDocument() {
		jLabel2.setText(doc.getLength()+"");
		updateUndoAction();
		setUnsaveChanged(true);	
	}
	private void updateUndoAction() {
		undoAction.setEnabled(undoManager.canUndo());
		redoAction.setEnabled(undoManager.canRedo());
	}
	
    public DiaryEditorView() {
        initComponents();
        initEditor();
    }

	private void initEditor() {
		editor = new JTextPane();
		editor.setBackground(new Color(221,233,242));
		EditorPanel editorPanel = new EditorPanel();
		editorPanel.setEditor(editor);
		scrollPane.setViewportView(editorPanel);
		
		StyleContext sc= new StyleContext();
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
				Point p=getCaretDimension(e);
				jLabel1.setText(p.x+":"+p.y);
			}
		});
		
		scrollPane.getViewport().setBackground(editor.getBackground());
		scrollPane.getViewport().addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				editor.requestFocus();
				editor.setCaretPosition(doc.getLength());
			}
		});
		
		AppPreference pref=PreferenceManager.getInstance().getPreference(DiaryView.class.getName());
		editorPanel.setLineWrap(pref.getBoolean("line.wrap"));

		jPanel2.setPreferredSize(new Dimension(1, 400));
		setPreferredSize(new Dimension(600, 400));
		initContent();
		
	}
	
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
	
	protected void clearDocument() throws BadLocationException {
		doc.removeDocumentListener(dl);
		doc.removeUndoableEditListener(undoManager);
		
		if(doc.getLength() >0){
				doc.remove(0, doc.getLength()-1);
		}
	}
	
/////////////////////////////   caret ////////////////////////////////////////////////
	protected Point getCaretDimension(CaretEvent e) {
		JTextComponent r = (JTextComponent) e.getSource();
		try {
			int pos = r.getCaretPosition();
			int line = getLineOffset(r, pos);
			int lineStartOffset = getLineStartOffset(r, line);
			return new Point(line + 1, pos - lineStartOffset + 1);
		} catch (BadLocationException e1) {
			AppLogging.handleException(e1);
			return new Point(-1, -1);
		}
	}

	protected int getLineOffset(JTextComponent r, int offset)
			throws BadLocationException {
				Document doc = r.getDocument();
				if (offset < 0) {
					throw new BadLocationException("offset must be greater than 0",
							offset);
				} else if (offset > doc.getLength()) {
					throw new BadLocationException(
							"offset must be less than the length of the document",
							offset);
				} else {
					Element root = doc.getDefaultRootElement();
					return root.getElementIndex(offset);
				}
			}

	protected int getLineStartOffset(JTextComponent editor, int line)
			throws BadLocationException {
				Document doc = editor.getDocument();
				// int lineCount = getLineCount();
				if (line < 0) {
					throw new BadLocationException("offset must be greater than 0",
							line);
				}
				// else if (line > lineCount) {
				// throw new BadLocationException(
				// "offset must be less than the length of the document", line);
				// }
				else {
					Element root = doc.getDefaultRootElement();
					Element lineElement = root.getElement(line);
					return lineElement.getStartOffset();
				}
			}

/////////////////////////////   caret   end  //////////////////////////////
	
	
	

	/** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jComboBox1 = new javax.swing.JComboBox();
        jTextField2 = new javax.swing.JTextField();
        jComboBox2 = new javax.swing.JComboBox();
        jTextField1 = new javax.swing.JTextField();
        jToolBar3 = new javax.swing.JToolBar();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jLabel2 = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();

        setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "分类1", "分类2" }));
        jToolBar1.add(jComboBox1);

        jTextField2.setText("标题");
        jToolBar1.add(jTextField2);

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "私人", "公开" }));
        jToolBar1.add(jComboBox2);

        jTextField1.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        jTextField1.setText("2011-06-19 22:43:14");
        jTextField1.setMaximumSize(new java.awt.Dimension(130, 2147483647));
        jToolBar1.add(jTextField1);

        jPanel2.add(jToolBar1, java.awt.BorderLayout.NORTH);

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

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


	public void setEditor(JTextPane editor) {
		this.editor = editor;
	}

	public JTextPane getEditor() {
		return editor;
	}


	// Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JScrollPane scrollPane;
    // End of variables declaration//GEN-END:variables

}
