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
package org.jtools.jreader.internal;

import java.util.List;

import org.jtools.jreader.internal.rss.RssReader;
import org.jtools.jreader.internal.xml.DataSourceException;
import org.jtools.jreader.persistent.Category;
import org.jtools.jreader.persistent.JReaderItem;
import org.jtools.jreader.persistent.ReaderDAO;
import org.jtools.jreader.persistent.Subscriber;



/**
 * <B>JReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-6 created
 * @since org.jreader.app Ver 1.0
 * 
 */
public class JReader {
	private static final String CATEGORY_CREATE="CategoryCreate";
	private static JReader instance;
	private ReaderDAO dao;

	/**
	 * 
	 */
	private JReader() {
		try {
			dao=ReaderDAO.getInstance();
		} catch (DataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * @return
	 */
	public static JReader getInstance() {
		if(instance ==null){
			instance=new JReader();
		}
		return instance;
	}

	/**
	 * @return
	 */
	public Category[] getCategory() {
		try {
			List<Category> categorys=dao.getCategorys();
			Category[] result=new Category[categorys.size()];
			for(int i=0;i<categorys.size();i++){
				result[i]=categorys.get(i);
				
				List<Subscriber> subscribers = dao.getSubcribers(result[i]);

				for (Subscriber sub : subscribers) {
					result[i].add(sub);
				}
				
			}
			return result;
		} catch (DataSourceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Category[0];
		}
	}
	
	public Category getCategoryByName(String name){
		try {
			return dao.getCategory(name);
		} catch (DataSourceException e) {
			e.printStackTrace();
			return new Category("ERROR");
		}
	}
	/**
	 * @param category
	 * @param reader
	 */
	public void add(Category category, RssReader reader) throws DataSourceException {
		if(getCategoryByName(category.getName()) == null){
			dao.createCategory(category);
		}
	}
	/**
	 * @return
	 */
	public static Category getDefaultCategory() {
		return new Category("default");//TODO 
	}
	/**
	 * @param category
	 * @param subscriber
	 */
	public void addSubscribe(Category category, Subscriber subscriber) throws DataSourceException {
		category.add(subscriber);
		saveItem(category);
	}
	
	/**
	 * @param category
	 */
	private void saveItem(JReaderItem item) throws DataSourceException {
		dao.saveItem(item);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
