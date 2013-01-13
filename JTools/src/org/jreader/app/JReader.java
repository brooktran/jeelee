/* JReader.java 1.0 2010-2-2
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
package org.jreader.app;

import javax.swing.event.EventListenerList;

import org.jreader.gui.ReaderItemEvent;
import org.jreader.persistent.Category;
import org.jreader.persistent.ReaderDAO;
import org.zhiwu.xml.DataSourceException;

/**
 * <B>JReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-6 created
 * @since org.jreader.app Ver 1.0
 * 
 */
public class JReader {
	private final EventListenerList listenerList;
	private static final String CATEGORY_CREATE="CategoryCreate";
	
	/**
	 * 
	 */
	public JReader() {
		listenerList=new EventListenerList();
	}

	/**
	 * @param text
	 */
	public void createCategory(String name) throws DataSourceException {
		ReaderDAO dao;
		dao = ReaderDAO.getInstance();
		Category category=new Category(name);
		dao.createCategory(category);
		
		fireCategoryCreated(category);
	}

	/**
	 * @param category
	 */
	private void fireCategoryCreated(Category category) {
		fireItemCreated(CATEGORY_CREATE,category);
	}


	/**
	 * @param jReader
	 * @param categoryCreate
	 * @param category
	 */
	private void fireItemCreated(String name,
			Category category) {
		ReaderItemEvent evt=new ReaderItemEvent(this,name,category);
		Object[] listeners=listenerList.getListenerList(); 
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i=0,j=listeners.length-1;i<j;i+=2){
			if (listeners[i] == ReaderItemChangedListener.class) {
				((ReaderItemChangedListener)listeners[i+1]).itemChanged(evt);
			}
		}
	}

	/**
	 * 
	 */
	public void addReaderChangedListener(ReaderItemChangedListener l) {
		listenerList.add(ReaderItemChangedListener.class, l);
	}

}
