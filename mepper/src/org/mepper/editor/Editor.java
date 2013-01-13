/* Editor.java 1.0 2010-2-2
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

import java.util.List;

import org.mepper.tool.Tool;


/**
 * <B>Editor</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public interface Editor {

	void add(EditorView v);
	void remove(EditorView v);
	
	EditorView getActivateView();
	void setActivateView(EditorView v);
	
	List<EditorView> getEditorViews();

	void setTool(Tool tool);
	Tool getTool();
	
	boolean isEnable();
	void setEnable(boolean b);
	
	void setUserObject(String key, Object value);
	Object getUserobject(String key);
	
	void addEditorListener(EditorListener l);
	void removeEditorhangeListener(EditorListener l);

	boolean canUndo();
	boolean canRedo();
	void undo();
	void redo();
}