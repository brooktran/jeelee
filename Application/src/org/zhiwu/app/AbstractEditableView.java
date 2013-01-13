/* AbstractEditableView.java 1.0 2010-2-2
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


/**
 * <B>AbstractEditableView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-8-2 created
 * @since org.zhiwu.app Ver 1.0
 * 
 */
public class AbstractEditableView extends AbsView  implements Editable{
	protected boolean isChanged;
	
	public AbstractEditableView() {
		isChanged = false;
	}
	
	@Override
	public boolean hasUnsavChanged() {
		return isChanged;
	}

	@Override
	public void save() {
	}

	@Override
	public void setUnsaveChanged(boolean b) {
		boolean oldValue=isChanged;
		isChanged=b;
		firePropertyChange(ApplicationConstant.PROPERTY_SAVE, oldValue, isChanged);
	}
	
	@Override
	public boolean canUndo() {
		return false;
	}
	
	@Override
	public void undo() {
		throw new UnsupportedClassVersionError();
	}
	
	@Override
	public boolean canRedo() {
		return false;
	}
	
	@Override
	public void redo() {
		throw new UnsupportedClassVersionError();
	}
	
}


