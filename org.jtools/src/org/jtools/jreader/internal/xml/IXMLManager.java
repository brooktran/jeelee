package org.jtools.jreader.internal.xml;

import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;

/**
 * <B>IXMLManager</B> is a interface that define a framework
 * to provide a XML handle manager. Other classes extend 
 * this interface to provide data source as well as its control.
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-4-18 created
 * @since agenda Ver 1.0
 * 
 */
public interface IXMLManager {

	/**
	 * Evaluates an XPath expression and returns the list of selected items. 
	 * * <p>eg:<p>{@code
	 * @param context context the node to use as context for evaluating the XPath expression.
	 * @param path path the XPath expression to evaluate.
	 * 
	 * @return the node by x path
	 */
	public Object getSingleNodeByXPath(Object context, String path)
			throws DataSourceException;

	/**
	 * Evaluates an XPath expression and returns the list of selected items.
	 * <p>eg:<p>{@code
	 * List<Object> nodes = manager.getNodesByXPath(manager
					.getRootElement(), "/swc/libraries/library/script");// the swc element is the root element.
	 * @param context the node to use as context for evaluating the XPath expression.
	 * @param  path the XPath expression to evaluate.
	 * }
	 * @return the nodes by x path
	 */
	public List<Object> getNodesByXPath(Object context, String path)
			throws DataSourceException;

	/**
	 * Gets the element by the given element name and its attributeName.
	 * 
	 * @param elementName the full name of the element.
	 * @param parent the parent of the element to get.
	 * @param attributeName the attributes name
	 * 
	 * @return the element by attribute
	 */
	public Element getElementByAttribute(String elementName, Element parent,
			String[] attributeName, String[] attributeValue);

	/**
	 * Gets the element by the given element name.
	 * @param elementName
	 * @return
	 */
	public Element getElement(String elementName);

	/**
	 * Gets the value of the specified node name.
	 * 
	 * @param name
	 *          the full name of the node.
	 * 
	 * @return the value of the specified name.
	 */
	public String getElementValue(String name);

	/**
	 * Get all children elements of a parent element as a String array.
	 * 
	 * @param name
	 *         the name of the parent node.
	 * 
	 * @return the children of the specified name or an empty array if there are
	 *         no children aviliable.
	 */
	public List<Element> getChildren(String nodeName);

	/**
	 * Gets the value of all children elements by a given node name.
	 * 
	 * @param nodeName the node name
	 * 
	 * @return the children value
	 */
	public List<String> getChildrenName(String nodeName);

	/**
	 * Sets the value of the specified element. If the element doesn't
	 * currently exist, it will be automatically created.
	 *
	 * @param name the name of the element to set.
	 * @param value the new value for the element.
	 */
	public Element modifyElement(String elementName, String value);

	/**
	 * Delete children of the specified node.
	 * 
	 * @param nodeName
	 *            the node name
	 * 
	 * @return true, if successful
	 */
	public boolean deleteChildren(String nodeName);

	/**
	 * Deletes the specified element.
	 *
	 * @param name the element to delete.
	 */
	public boolean deleteChild(String nodeName);

	/**
	 * Get all the value of the specified attribute of the given node.
	 * 
	 * @return
	 */
	public String getNodeAttributeValue(String nodeName, String attributeName);

	/**
	 * Saves the xml file to disk as an XML document. A temporary file is
	 * used during the writing process for maximum safety.
	 */
	public void saveToFile(String fileName, boolean useSchema);

	/**
	 * Save to stream.
	 * 
	 * @param out the out
	 */
	public void saveToStream(OutputStream out);

	/**
	 * Save to stream.
	 * 
	 * @param writer the writer
	 */
	public void saveToStream(Writer writer);

	/**
	 * Sets the document of the manager.
	 * 
	 * @param document
	 *            the document
	 */
	public void saveToFile(boolean useSchema);

	/**
	 * Gets the jdom document of the manager.
	 * 
	 * @return the document
	 */
	public Document getDocument();

	/**
	 * Delete element.
	 * 
	 * @param element the element
	 * 
	 * @return true, if delete element
	 */
	public boolean deleteElement(Element parent, String elementName,
			String attributeName, String attributeValue);

	/**
	 * Gets the root element.
	 * 
	 * @return the root element
	 */
	public Element getRootElement();

}
