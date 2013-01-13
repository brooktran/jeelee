/* JReaderItem.java 1.0 2010-2-2
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
package org.jtools.jreader.persistent;

import java.util.List;

import org.eclipse.core.runtime.IAdaptable;

/**
 * <B>JReaderItem</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-6 created
 * @since org.jreader.persistent Ver 1.0
 * 
 */
public interface JReaderItem extends IAdaptable{

	/**
	 * @param category
	 */
	void setParent(Category category);


	/**
	 * @return
	 */
	JReaderItem[] getChildren();

	/**
	 * @return
	 */
	boolean hasChildrenItems();

	/**
	 * @param category
	 */
	void addChildren(List<? extends JReaderItem> children);


	/**
	 * @return
	 */
	JReaderItem getParent();


	/**
	 * @return
	 */
	boolean hasParent();


	/**
	 * @return
	 */
	String getName();
	
}
