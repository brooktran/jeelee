/* CompareEditor.java 1.0 2010-2-2
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
package org.compare;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jdom.Attribute;
import org.jdom.Element;
import org.zhiwu.utils.DateUtils;
import org.zhiwu.utils.AppResources;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;
import org.zhiwu.xml.DataSourceException;

/**
 * <B>CompareEditor</B>
 * 
 * @author Brook Tran.  Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-2-2 created
 * @since JCompareEditor Ver 1.0
 * 
 */
public class CompareEditor  {
	private static final long serialVersionUID = -1383280611210950136L;
	private Dictate dictate;
	private XMLManager manager;

	public CompareEditor() {
		AppResources resource= 
			AppResources.getResources("org.compare.Labels");
		try {
			manager=new DefaultXMLManagerImpl( resource.getString("data"),false);
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		dictate = new Dictate("",new Date(),"","","","");
	}


	public Dictate getDictate(){
		return dictate;
	}

	/**
	 * 
	 * @param text
	 */
	public void setDictate(Dictate dictate) {
		this.dictate=dictate;
	}

	/**
	 * 
	 */
	public void save() {
		long label= dictate.date.getTime();
		Element e=exist(label);
		if (e == null) {
			create();
		}else {
			modify(e);
		}
		manager.saveToFile(false);
	}

	/**
	 * 
	 * @param label
	 */
	private void modify(Element e) {
		e.setAttribute("title", dictate.title);

		Element source = e.getChild("source");
		source.setText(dictate.source);

		Element target = e.getChild("target");
		target.setText(dictate.target);

		Element translated = e.getChild("translated");
		translated.setText(dictate.translated);

		Element comment = e.getChild("comment");
		comment.setText(dictate.comment);
	}


	/**
	 * 
	 * @param dictate
	 */
	private void create() {
		Element root = manager.getRootElement();
		Element child = new Element("cmp");

		child.setAttribute(new Attribute("title", dictate.title));
		child.setAttribute(new Attribute("date", dictate.date.getTime()+""));

		Element source = new Element("source");
		source.setText(dictate.source);

		Element target = new Element("target");
		target.setText(dictate.target);

		Element translated = new Element("translated");
		translated.setText(dictate.translated);

		Element comment = new Element("comment");
		comment.setText(dictate.comment);

		child.addContent(source);
		child.addContent(target);
		child.addContent(translated);
		child.addContent(comment);

		root.addContent(child);
	}


	/**
	 * 
	 * @param label
	 * @return
	 */
	private Element exist(long label) {
		Object o=null;
		System.out.println(manager.getRootElement().getName());
		try {
			o=manager.getSingleNodeByXPath(manager.getRootElement(), 
					"//cmp[@date='"+label+"']");// the same as "/root/cmp[@date='"+label+"']");
			System.out.println(o == null);
			System.out.println("/cmp[@date='" + label + "']");
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return (Element)o;
	}


	public List<Dictate> getData() {
		AppResources resource = AppResources.getResources("org.compare.Labels");
		List<Dictate> list = new ArrayList<Dictate>();
		try {
			XMLManager manager = new DefaultXMLManagerImpl(  resource
					.getString("data"),false);
			org.jdom.Element root = manager.getRootElement();
			for (Object o : root.getChildren("cmp")) {
				org.jdom.Element e = (org.jdom.Element) o;
				Date d = DateUtils.parse(Long.parseLong(e
						.getAttributeValue("date")));
				list.add(new Dictate(e.getAttributeValue("title"), d, e
						.getChildText("source"), e.getChildText("target"),
						e.getChildText("translated"), e
						.getChildText("comment")));
			}
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
		return list;
	}


}







