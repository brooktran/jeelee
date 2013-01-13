/* Category.java 1.0 2010-2-2
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
package org.jreader.persistent;

import java.util.List;

/**
 * <B>Category</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-6 created
 * @since org.jreader.persistent Ver 1.0
 * 
 */
public class Category implements ReaderItem{
	private List<ReaderItem> children;
	private String name;
	
	
	
	/**
	 * @param attributeValue
	 */
	public Category(String name) {
		this.name=name;
	}



	/* (non-Javadoc)
	 * @see org.jreader.persistent.ReaderItem#childrenSize()
	 */
	public int childrenSize() {
		return children.size();
	}



	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return name;
	}
}
