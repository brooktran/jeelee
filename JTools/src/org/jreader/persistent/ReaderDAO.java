/* ReaderDAO.java 1.0 2010-2-2
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

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.zhiwu.xml.DAO;
import org.zhiwu.xml.DataSourceException;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;

/**
 * <B>ReaderDAO</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-1 created
 * @since org.jreader.persistent Ver 1.0
 * 
 */
public class ReaderDAO implements DAO<Subscriber> {
	private static int instanceCounter = 0; // for debug
	
//	private static final Object locker=new Object();
	
	private static volatile ReaderDAO instance;
	
	private final XMLManager manager;
	private static final String DATA_PATH="saved.xml";

	/*
	 * *
	 */
	public ReaderDAO() throws DataSourceException {
		manager=new DefaultXMLManagerImpl(DATA_PATH, false);
		
		
		// for debug
		if (++instanceCounter > 1) {
			throw new RuntimeException("singleton error design...");
		}
	}

	public static ReaderDAO getInstance() throws DataSourceException {
		if(instance==null){
			synchronized (ReaderDAO.class) {
				if (instance ==null) {
					ReaderDAO dao=new ReaderDAO();
					instance=dao;
				}
			}
		}
		return instance; 
	}

	

	/* (non-Javadoc)
	 * @see org.jreader.persistent.DAO#getAll()
	 */
	@Override
	public List<Subscriber> getAll() throws DataSourceException {
		List<Subscriber> allSubscribers=new ArrayList<Subscriber>();//TODO init the size
		
		Subscriber subscriber;
		
		List<Object> nodes=manager.getNodesByXPath(manager.getRootElement(), "/reader/category/subscriber");
		for(Object o:nodes){
			subscriber = elementToSubscriber((Element)o);
			allSubscribers.add(subscriber);
		}
		return allSubscribers;
	}

	/**
	 * 
	 */
	public List<Category> getCategory() throws DataSourceException {
		List<Category> categorys=new ArrayList<Category>();//TODO init the size

		List<Object> nodes=manager.getNodesByXPath(manager.getRootElement(), "/reader/category");
//		String[] categorys=new String[nodes.size()];
		Category category;
		for(int i=0;i<nodes.size();i++){
			Element e=(Element)nodes.get(i);
			category=new Category(e.getAttributeValue("name"));
			categorys.add(category);
		}
		return categorys;
	}
	
	public static void main(String[] args) throws DataSourceException {
		System.out.println(getInstance().getSubcribers("文化"));
	}

	/**
	 * @param text
	 */
	public void createCategory(Category category) {
		Element e=new Element("categoty");
		e.setAttribute("name", category.getName());
		
		Element root=manager.getRootElement();
		int categoryCount=Integer.parseInt(root.getAttributeValue("count"))+1;
		e.setAttribute("id",categoryCount+"");
		root.addContent(e);
		
		root.setAttribute("count", categoryCount+"");
		
		manager.saveToFile(false);

	}

	/**
	 * @param s
	 */
	private List<Subscriber> getSubcribers(String category) throws DataSourceException {
List<Subscriber> allSubscribers=new ArrayList<Subscriber>();//TODO init the size
		
		Subscriber subscriber;
		
		List<Object> nodes=manager.getNodesByXPath(manager.getRootElement(), "/reader/category[@name='"+category+"']/subscriber");
		for(Object o:nodes){
			subscriber = elementToSubscriber((Element)o);
			allSubscribers.add(subscriber);
		}
		return allSubscribers;
	}

	/**
	 * @param e
	 * @return
	 */
	private Subscriber elementToSubscriber(Element e) {
		Subscriber subscriber;
		subscriber=new Subscriber(e.getChildText("title"),e.getChildText("password"));
		subscriber.setUri(e.getChildText("link"));
		return subscriber;
	}

	/**
	 * @param c
	 * @return
	 */
	public List<Subscriber> getSubcribers(Category c) throws DataSourceException {
		return getSubcribers(c.getName());
	}


}
