/* EditorFactory.java 1.0 2010-2-2
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
package org.mepper.editor;

import java.beans.PropertyChangeListener;

import org.mepper.gui.AbstractEditableView;

/**
 * <B>EditorFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public class EditorFactory {

	public static Editor addToEditor(AbstractEditorView panel, PropertyChangeListener l, AbstractEditableView view) {
//		Editor e;
//		if(panel instanceof DefaultEditorView){
//			e=view.getEditor();
//			if(e == null){
//				e=new ImportImageEditor();
//				e.addPropertyChangeListener(l);
//				ButtonFactory.addToolbarTo(e,view);
////				view.addEditor(e);
//			}
//			e.add(panel); 
//			return e;
//		}
		return null;
	}



}
