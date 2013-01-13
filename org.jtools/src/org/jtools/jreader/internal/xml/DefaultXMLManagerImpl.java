/* DefaultXMLManagerImpl.java 1.0 2010-2-2
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
package org.jtools.jreader.internal.xml;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.DOMBuilder;
import org.jdom.input.SAXBuilder;

/**
 * Class <B>DefaultXMLManagerImpl</B> provides the convenience to use simple XML database.
 * The XML file must with constraint by a Schema file.
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com<\a>
 * @version Ver 1.1.01 2009-4-17 modify
 * @since chenzhw Ver 1.0 <br>
 *        Ver 2009 add methods to handle a xml file with namespace. <br>
 *        Ver 2002 Jdon
 * 
 */
public class DefaultXMLManagerImpl implements IXMLManager {

	private Document document;
	private String fileName;
	private String schemaFileName;

	private final DefaultXMLManagerHelper helper = new DefaultXMLManagerHelper();

	/**
	 * Since parsing the XML file is time-consuming, we use a DefaultMap to cache some
	 * Notes that are used more than once.
	 */
	private final Map<String, String> xmlCache = new HashMap<String, String>();

	/**
	 * Create a new DefaultXMLManagerImpl.
	 * 
	 * @param filename the full path of the XML file which shoule be read from and
	 * written to.
	 * @param schemaFileName the schema file name
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public DefaultXMLManagerImpl(String filename, String schemaFileName)
			throws DataSourceException {
		this.fileName = filename;
		this.schemaFileName = schemaFileName;
		try {
			SAXBuilder builder = new SAXBuilder(true);
			File file = new File(filename);
			helper.getValidate(builder, schemaFileName);
			document = builder.build(file);
		} catch (Exception e) {
			throw new DataSourceException(
					"Cann't create the XML file with file name or Invalid content was found :"
							+ filename + " " + e.getMessage(), e);
		}
	}

	/**
	 * @param bais
	 */
	public DefaultXMLManagerImpl(InputStream inputStream) throws DataSourceException {
		this(inputStream, null);
	}
	
	/**
	 * The Constructor.
	 * 
	 * @param inputStream the input stream
	 * @param schemaFileName the schema file name
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public DefaultXMLManagerImpl(InputStream inputStream, String schemaFileName)
			throws DataSourceException {
		try {
			SAXBuilder builder = new SAXBuilder();
			if (schemaFileName!=null) {
				helper.validateDocument(inputStream, schemaFileName);
			}
			document = builder.build(inputStream);
		} catch (Exception e) {
//			e.printStackTrace();
			throw new DataSourceException(
					"Cann't create the XML file with InputStream "
							+ inputStream, e);
		}

	}

	/**
	 * The Constructor.
	 * 
	 * @param reader the reader
	 * @param schemaFileName the schema file name
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public DefaultXMLManagerImpl(Reader reader, String schemaFileName)
			throws DataSourceException {
		try {
			SAXBuilder builder = new SAXBuilder();
			helper.validateDocument(reader, schemaFileName);
			document = builder.build(reader);
		} catch (Exception e) {
			throw new DataSourceException("Cann't create the XML file with Reader "
					+ reader, e);
		}
	}

	/**
	 * Create a new DefaultXMLManagerImpl.
	 * 
	 * @param filename the full path of the XML file which shoule be read from and
	 * written to.
	 * @param schemaFileName the schema file name
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public DefaultXMLManagerImpl(String filename, boolean isCreate) throws DataSourceException {
		this.fileName = filename;
		try {
			SAXBuilder builder = new SAXBuilder();
			File file=new File(filename);
			if(isCreate &&!file.exists()){
				Element root=new Element("root");// TODO convert "root" to rootName param
				document=new Document(root);
				saveToFile(false);
				return;
			}
			document = builder.build(file);
		} catch (Exception e) {
			e.printStackTrace();
			throw new DataSourceException(
					"Cann't create the XML file with file name or Invalid content was found"
							+ filename, e);
		}
	}

	public DefaultXMLManagerImpl(org.w3c.dom.Document w3cDoc) {
		DOMBuilder builder=new DOMBuilder();
		document=builder.build(w3cDoc);
	}


	/**
	 * @param document
	 */
	public DefaultXMLManagerImpl(Document document) {
		this.document=document;
	}

	/** 
	 * Gets the value of the specified name.
	 * 
	 * @param name the path split with '/'
	 * 
	 * @return the value of the specified name.
	 * 
	 * @see agenda.persistence.IXMLManager#getNodeValue(java.lang.String)
	 */
	public String getElementValue(String name) {
		if (xmlCache.containsKey(name)) {// Return a existed value.
			return xmlCache.get(name);
		}
		return helper.getElementValue(name, document.getRootElement());// root ?
	}

	/** 
	 * @see agenda.persistence.IXMLManager#getElement(java.lang.String)
	 */
	public Element getElement(String elementName) {
		return helper.getElement(elementName, document.getRootElement());
	}

	/** 
	 * Gets all children element of a parent element as a String array.
	 * 
	 * @param name the name of the parent node.
	 * 
	 * @return the children of the specified name or an empty array 
	 * if there are no children aviliable.
	 * 
	 * @see agenda.persistence.IXMLManager#getChildren(java.lang.String)
	 */
	public List<Element> getChildren(String nodeName) {
		return helper.getChildren(nodeName, document.getRootElement());
	}


	/** 
	 * Sets the value of the specified element. If the element doesn't
	 * currently exist, it will be automatically created.
	 * 
	 * @param name the name of the element to set.
	 * @param value the new value for the element.
	 * @see agenda.persistence.IXMLManager#modifyElement(java.lang.String, java.lang.String)
	 */
	public Element modifyElement(String name, String value) {
		return helper
				.modifyElementValue(name, value, document.getRootElement());
	}

	/** 
	 * Deletes the children of the specified node.
	 *
	 * @param name the element to delete.
	 * @see agenda.persistence.IXMLManager#deleteChildren(java.lang.String)
	 */
	public boolean deleteChildren(String name) {
		return helper.deleteElements(name, document.getRootElement());
	}

	/** 
	 * Saves the xml file to disk as an XML document.
	 * @see agenda.persistence.IXMLManager#saveToFile()
	 */
	public synchronized void saveToFile(boolean useSchema) {
		helper.save(fileName, document, schemaFileName, useSchema);
	}

	/**
	 * Saves the xml file to disk as an XML document. A temporary file is
	 * used during the writing process for maximum safety.
	 * @see agenda.persistence.IXMLManager#saveToFile(java.lang.String)
	 */
	public synchronized void saveToFile(String fileName, boolean useSchema) {
		helper.save(fileName, document, schemaFileName, useSchema);
	}

	/** 
	 * Save to stream.
	 * 
	 * @param out the out
	 * @see agenda.persistence.IXMLManager#saveToStream(java.io.OutputStream)
	 */
	public synchronized void saveToStream(OutputStream out) {
		helper.saveToStream(out, document, schemaFileName);
	}

	/** 
	 * Save to stream.
	 * 
	 * @param writer the writer     
	 * @see agenda.persistence.IXMLManager#saveToStream(java.io.Writer)
	 * @see agenda.persistence.IXMLManager#saveToStream(java.io.Writer)
	 */
	public synchronized void saveToStream(Writer writer) {
		helper.saveToStream(writer, document, schemaFileName);
	}

	/** 
	 * @see agenda.persistence.IXMLManager#deleteChild(java.lang.String)
	 */
	public boolean deleteChild(String nodeName) {
		return helper.deleteElement(nodeName, document.getRootElement());
	}

	/**
	 * Gets the document.
	 * 
	 * @return the document
	 * 
	 * @see agenda.persistence.IXMLManager#getDocument()
	 */
	public Document getDocument() {
		return document;
	}

	/** 
	 * Gets value of the specified attribute.
	 * @see agenda.persistence.IXMLManager#getNodeAttributeValue(java.lang.String)
	 */
	public String getNodeAttributeValue(String nodeName, String attributeName) {
		return helper.getNodeAttributeValue(nodeName, attributeName, document
				.getRootElement());
	}

	/**
	 * Gets the value of all children elements by a given node name.
	 * 
	 * @param nodeName the node name
	 * 
	 * @return the children name
	 * 
	 * @see agenda.persistence.IXMLManager#getChildrenValue(java.lang.String)
	 */
	public List<String> getChildrenName(String nodeName) {
		return helper.getChildrenName(nodeName, document.getRootElement());
	}

	/** 
	 * @see agenda.persistence.IXMLManager#getNodeByXPath(org.jdom.Content, java.lang.String)
	 */
	public Object getSingleNodeByXPath(Object context, String path)
			throws DataSourceException {
		try {
			return helper.getSingleNodeByXPath(context, path);
		} catch (Exception e) {
			throw new DataSourceException(e);
		}
	}

	/** 
	 * @see agenda.persistence.IXMLManager#getNodesByXPath(org.jdom.Content, java.lang.String)
	 */
	public List<Object> getNodesByXPath(Object context, String path)
			throws DataSourceException {
		try {
			return helper.getNodesByXPath(context, path);
		} catch (Exception e) {
			throw new DataSourceException(e);
		}
	}

	/** 
	 * Gets the element by the given element name and its attributeName.
	 * 
	 * @param elementName the full name of the element.
	 * @param parent the parent of the element to get.
	 * @param attributeName the attributes name
	 * 
	 * @return the element by attribute
	 * @see agenda.persistence.IXMLManager#getElementByAttribute(java.lang.String, org.jdom.Element, java.lang.String[])
	 */
	public Element getElementByAttribute(String elementName, Element parent,
			String[] attributeName, String[] attributeValue) {
		return helper.getElementByAttribute(elementName, parent, attributeName,
				attributeValue);
	}

	/**
	 * Delete element.
	 * 
	 * @param element the element
	 * 
	 * @see agenda.persistence.IXMLManager#deleteElement(org.jdom.Element)
	 */
	public boolean deleteElement(Element parent, String elementName,
			String attribute, String attributeValue) {
		return helper.deleteElement(parent, elementName, attribute,
				attributeValue);
	}

	/**
	 * Gets the root element.
	 * 
	 * @return the root element
	 */
	public Element getRootElement() {
		return document.getRootElement();
	}

}
