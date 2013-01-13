/* DocumentView.java 1.0 2010-2-2
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
package org.zhiwu.app;

import java.awt.Point;

import javax.swing.event.CaretEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;

import org.zhiwu.utils.AppLogging;

/**
 * <B>DocumentView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-11-15 created
 * @since org.zhiwu.app Ver 1.0
 * 
 */
public class DocumentView extends AbsView{
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

	public int getLineOffset(JTextComponent r, int offset)
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

	public int getLineStartOffset(JTextComponent editor, int line)
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
