/* EditableView.java 1.0 2010-2-2
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
package org.mepper.gui;

import org.mepper.editor.Editor;
import org.zhiwu.app.View;

/**
 * <B>EditableView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-24 created
 * @since org.mepper.gui Ver 1.0
 * 
 */
public interface EditableView extends View{
	Editor getEditor();
}
