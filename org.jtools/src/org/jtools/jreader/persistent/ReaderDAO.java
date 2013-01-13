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
package org.jtools.jreader.persistent;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;
import org.jtools.jreader.internal.xml.DAO;
import org.jtools.jreader.internal.xml.DataSourceException;
import org.jtools.jreader.internal.xml.DefaultXMLManagerImpl;
import org.jtools.jreader.internal.xml.IXMLManager;


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
	
	private final IXMLManager manager;
	public static final String DATA_PATH="F:/test/java/org.jtools/saved.xml";

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
	public List<Category> getCategorys() throws DataSourceException {
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
	public void createCategory(Category category) throws DataSourceException {
		if(getCategory(category.getName()) != null){
			return ;
		}
		
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
		List<Subscriber> allSubscribers = new ArrayList<Subscriber>();//TODO init the size
		
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
		Subscriber sub;
		sub=new Subscriber(e.getChildText("link"));
		sub.setName(e.getChildText("title"));
//TODO 		
		return sub;
	}

	/**
	 * @param c
	 * @return
	 */
	public List<Subscriber> getSubcribers(Category c) throws DataSourceException {
		return getSubcribers(c.getName());
	}

	/**
	 * @param name
	 * @return
	 */
	public Category getCategory(String name) throws DataSourceException {
		Element e=(Element) manager.getSingleNodeByXPath(manager.getRootElement(), "/reader/category[@name='"+name+"']");
		return new Category(e.getAttributeValue(name));
	}


	/**
	 * @param item
	 */
	public void saveItem(JReaderItem item) throws DataSourceException {
		Category parent = null;
		if(item.hasParent()){
			parent=(Category) item.getParent();
			if(getCategory(parent.getName()) == null){
				createCategory(parent);
			}
			
			Element e=(Element) manager.getSingleNodeByXPath(manager.getRootElement(), "/reader/category[@name='"+parent.getName()+"']");
			
			Subscriber sub=(Subscriber)item;
			Element subElement=new Element("subscriber");
			
			subElement.addContent(new Element("user").setText(sub.getUser()));//$NON-NLS-1$
			subElement.addContent(new Element("password").setText(sub.getPassword()));//$NON-NLS-1$
			subElement.addContent(new Element("title").setText(sub.getTitle()));//$NON-NLS-1$
			subElement.addContent(new Element("copyright").setText(sub.getCopyright()));//$NON-NLS-1$
			subElement.addContent(new Element("language").setText(sub.getLanguage()));//$NON-NLS-1$
			subElement.addContent(new Element("publicshed").setText(sub.getPublished()));//$NON-NLS-1$
			subElement.addContent(new Element("link").setText(sub.getLink()));//$NON-NLS-1$
			subElement.addContent(new Element("author").setText(sub.getAuthor()));//$NON-NLS-1$
			
			Element entryElement=new Element("entry");
			subElement.addContent(entryElement);
			
			for(ArticleEntry entry:sub.getEntries()){
				entryElement.addContent(new Element("title").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("link").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("descirption").setText(entry.getDescirption()));//$NON-NLS-N$
				entryElement.addContent(new Element("title").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("author").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("category").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("comments").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("published").setText(entry.getTitle()));//$NON-NLS-N$
				entryElement.addContent(new Element("update").setText(entry.getTitle()));//$NON-NLS-N$

			
			
			
			
			}
		}

		
		
//		List<JReaderItem> items=new ArrayList<JReaderItem>();
//		JReaderItem copyItem=item;
//		while(copyItem.hasParent()){
//			items.add(copyItem.getParent());
//			copyItem=copyItem.getParent();
//		}
//		
//		StringBuffer sb=new StringBuffer();
//		Element parent=manager.getRootElement();
//		for(int i=items.size()-1;i>=0;i--){
//			copyItem=items.get(i);
//			if(copyItem instanceof Category){
//				List list=parent.getChildren();
//				for(Object o:list){
//					Element e=(Element)o;
//					if(e.getAttributeValue("name").equals(copyItem.getName())){//$NON-NLS-1$
//						
//					}
//				}
//			}
//			
//		}
		
		
		
		
	}


}
