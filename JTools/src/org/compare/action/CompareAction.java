package org.compare.action;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.compare.CompareEditor;
import org.compare.CompareView;
import org.compare.Dictate;
import org.compare.Levenshtein;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>CompareAction</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-1-28 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class CompareAction extends AppAction {
	private static final long serialVersionUID = 1L;
	public static final String ID = "compare";

	private Style defaultStyle ;
	private final Style correctStyle ;
	private final Style wrongStyle;
	private final StyleContext sc ;

	/**
	 * 
	 * @since JCompareEditor
	 * @param app
	 */
	public CompareAction(Application app) {
		super(app);

		AppResources resource = AppResources.getResources("org.compare.Labels");
		resource.configAction(this, ID);

		sc = new StyleContext();

		defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		defaultStyle = sc.addStyle("MainStyle", defaultStyle);
		StyleConstants.setFontFamily(defaultStyle, "宋体");
		StyleConstants.setLeftIndent(defaultStyle, 16);
		StyleConstants.setRightIndent(defaultStyle, 16);
		StyleConstants.setFirstLineIndent(defaultStyle, 16);
		StyleConstants.setFontFamily(defaultStyle, "serif");
		StyleConstants.setFontSize(defaultStyle, 12);

		correctStyle = sc.addStyle("ConstantWidth", null);
		StyleConstants.setFontFamily(correctStyle, "宋体");
		//		StyleConstants.setForeground(correctStyle, Color.RED);
		StyleConstants.setFontSize(correctStyle, 16);
		StyleConstants.setBackground(correctStyle, new Color(178,230,142));
		//		StyleConstants.setBold(correctStyle, true);

		// Create and add the heading style
		wrongStyle = sc.addStyle("Heanding2", null);
		StyleConstants.setForeground(wrongStyle, Color.RED);
		StyleConstants.setFontSize(wrongStyle, 16);
		StyleConstants.setFontFamily(wrongStyle, "宋体");// ????
		//		StyleConstants.setLeftIndent(wrongStyle, 8);// 左缩进
		StyleConstants.setFirstLineIndent(wrongStyle, 0);
		StyleConstants.setStrikeThrough(wrongStyle, true);
	}

	/**
	 * generation instructions
	 * <p>
	 * string A string B
	 * <p>
	 * ------------ -------- --------
	 * <p>
	 * match (A) A A
	 * <p>
	 * match (C) C C
	 * <p>
	 * insert(G) G
	 * <p>
	 * match (T) T T
	 * <p>
	 * delete(A) A
	 * <p>
	 * match (C) C C
	 * <p>
	 * change(G,C) G C
	 * <p>
	 * match (T) T T
	 * <p>
	 * 
	 * An Example.
	 * 
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		CompareView view = (CompareView) app.getView();
		CompareEditor editor=view.getEditor();
		view.setEnabled(false);
		Dictate d=editor.getDictate();
		try {
			// TODO 去掉多余空格
			// TODO 判断是否太短
			// TODO 不判断标点符号

			Levenshtein ls=new Levenshtein();
			String[] source=d.source.split(" ");
			String[] target=d.target.split(" ");

			if(source.length == 1 || target.length == 1){
				JOptionPane.showMessageDialog(view, "source or target in null");
				return;
			}

			int[][] result= ls.getOperationPath(ls.getGraph(source,target));

			DefaultStyledDocument doc = new DefaultStyledDocument(sc);
			doc.setLogicalStyle(0, defaultStyle);
			for(int i=result.length-1;i>1;i--){
				int op=result[i][0];
				if(op == -1){// right
					doc.insertString(0, target[result[i][1] -1], defaultStyle);
					doc.insertString(0, " ", defaultStyle);
				}else if (op == 1) {// replace
					doc.insertString(0, target[result[i][1] -1], wrongStyle);
					//					doc.insertString(0, " ", defaultStyle);
					doc.insertString(0, source[result[i][2] -1], correctStyle);
					doc.insertString(0, " ", defaultStyle);
				}else if (op == 2) {// insert
					doc.insertString(0, source[result[i][2] -1], correctStyle);
					doc.insertString(0, " ", defaultStyle);
				}else if (op == 3) {// remove
					doc.insertString(0, target[result[i][1] -1], wrongStyle);
					doc.insertString(0, " ", defaultStyle);
				}else {// zero , do nothing
					System.out.println("unknow operation: "+op);
					break;
				}
			}
			view.setDocument(doc);
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	@Override
	public String getID() {
		return ID;
	}
}
