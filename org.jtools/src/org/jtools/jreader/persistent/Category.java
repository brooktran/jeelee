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
package org.jtools.jreader.persistent;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>Category</B>.
 *
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-6 created
 * @since org.jreader.persistent Ver 1.0
 */
public class Category implements JReaderItem{
	
	/** The children. */
	private final List<JReaderItem> children;
	
	/** The name. */
	private String name;
	
	/** The parent. */
	private Category parent;
	
	

	/**
	 * Instantiates a new parent.
	 *
	 * @param name the name
	 */
	public Category(String name) {
		this.name=name;
		children=new ArrayList<JReaderItem>();
	}

	/**
	 * Adds the subscriber.
	 *
	 * @param item the item
	 */
	public void add(JReaderItem item){
		children.add(item);
		item.setParent(this);
	}
	
	/**
	 * Removes the child.
	 *
	 * @param item the item
	 */
	public void removeChild(JReaderItem item){
		children.remove(item);
		item.setParent(null);
	}

	/* (non-Javadoc)
	 * @see org.jreader.persistent.ReaderItem#childrenSize()
	 */
	/**
	 * Children size.
	 *
	 * @return the int
	 */
	public int childrenSize() {
		return children.size();
	}
	
	/**
	 * Gets the reader items.
	 *
	 * @return the reader items
	 */
//	public JReaderItem[] getReaderItems(){
//		return (JReaderItem[])children.toArray();
//	}

	/**
	 * Checks for children.
	 *
	 * @return true, if successful
	 */
	public boolean hasChildrenItems(){
		return children.size()>0;
	}


	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}



	/**
	 * Gets the name.
	 *
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

	/* (non-Javadoc)
	 * @see org.eclipse.core.runtime.IAdaptable#getAdapter(java.lang.Class)
	 */
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}
	
	/**
	 * Gets the parent.
	 *
	 * @return the parent
	 */
	public Category getParent() {
		return parent;
	}

	/**
	 * Sets the parent.
	 *
	 * @param parent the parent to set
	 */
	public void setParent(Category category) {
		this.parent = category;
	}


	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#getChildren()
	 */
	@Override
	public JReaderItem[] getChildren() {
		for(JReaderItem item:children){
			System.out.println(item);
		}
		return children.toArray(new JReaderItem[children.size()]);

//		for(int i=0;i<children.size();i++){
//			items[i]=children.get(i);
//		}
//		return items;
	}

	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#addChildren(java.util.List)
	 */
	@Override
	public void addChildren(List<? extends JReaderItem> children) {
		this.children.addAll(children);
	}

	/* (non-Javadoc)
	 * @see org.jtools.jreader.persistent.JReaderItem#hasParent()
	 */
	@Override
	public boolean hasParent() {
		return parent==null;
	}
}
