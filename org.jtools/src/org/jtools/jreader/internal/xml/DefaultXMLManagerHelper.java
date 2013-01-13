package org.jtools.jreader.internal.xml;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.jdom.xpath.XPath;

/**
 * <B>DefaultXMLManagerHelper</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-4-18 created
 * @since agenda Ver 1.0
 * 
 */
public class DefaultXMLManagerHelper {

	private final static String CONSTRAINT = "http://apache.org/xml/features/validation/schema";
	private final static String SchemaProperty = "http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation";

	/**
	 * @see agenda.persistence.IXMLManager#getNodeByXPath(org.jdom.Content,
	 *      java.lang.String)
	 */
	public Object getSingleNodeByXPath(Object context, String path)
	throws JDOMException {
		return XPath.selectSingleNode(context, path);
	}

	/**
	 * @see agenda.persistence.IXMLManager#getNodesByXPath(org.jdom.Content,
	 *      java.lang.String)
	 */
	public List<Object> getNodesByXPath(Object context, String path)
	throws JDOMException {
		try {
			return XPath.selectNodes(context, path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Gets the element of the specified name.
	 * 
	 * @param name
	 *            the full name of the node.
	 * @param root
	 *            the root
	 * 
	 * @return the element.
	 */
	protected Element getElement(String name, final Element root) {
		String[] names = parseName(name);

		Element element = root;
		for (String name2 : names) {
			element = element.getChild(name2, element.getNamespace());
			// This node doesn't match the path of the document which indicates
			// this name doesn't exist so return null.
			if (element == null) {
				return null;
			}
		}
		return element;
	}

	/**
	 * Gets the value of the specified name.
	 * 
	 * @param name
	 *            the name of the node.
	 * 
	 * @return the value of the specified name.
	 */
	protected String getElementValue(String name, final Element root) {
		Element element = getElement(name, root);
		// Get the value.
		return element.getText();
	}

	/**
	 * Gets all children element of a parent element as a String array.
	 * 
	 * @param name
	 *            the name of the parent node.
	 * 
	 * @return the children of the specified name or an empty array if there are
	 *         no children aviliable.
	 */
	protected List<Element> getChildren(String name, final Element root) {
		// Positioning.Traversing down the XML file to get all the values.
		Element element = getElement(name, root);
		return element == null ? (new ArrayList<Element>()) : element
				.getChildren();
	}

	/**
	 * Gets the name of children node.
	 * 
	 * @param name
	 *            the name
	 * @param root
	 *            the root
	 * 
	 * @return the children name
	 */
	protected List<String> getChildrenName(String name, final Element root) {

		Iterator<Element> iterator = getChildren(name, root).iterator();
		List<String> valueList = new ArrayList<String>();
		while (iterator.hasNext()) {
			valueList.add(iterator.next().getName());
		}
		return valueList;
	}

	/**
	 * Sets the value of the specified element. If the element doesn't currently
	 * exist, it will be automatically created.
	 * 
	 * @param name
	 *            the name of the element to set.
	 * @param value
	 *            the new value for the element.
	 */
	protected Element modifyElementValue(String name, String value,
			final Element root) {
		// Search for this property by traversing down the XML heirarchy.
		Element element = getElement(name, root);
		// Set the value in this node.
		return element.setText(value);
	}


	/**
	 * Deletes the specified elements.
	 * 
	 * @param name
	 *            the element to delete.
	 */
	protected boolean deleteElements(String name, final Element root) {
		// Positioning
		Element targetElement = getElement(name, root);
		// Delete the element.
		return targetElement.getParentElement().removeChildren(
				targetElement.getName(), root.getNamespace());
	}

	/**
	 * Deletes the specified element.
	 * 
	 * @param name
	 *            the element to delete.
	 */
	protected boolean deleteElement(String name, final Element root) {
		// Positioning
		Element targetElement = getElement(name, root);
		// Delete the element.
		return targetElement.getParentElement().removeChild(
				targetElement.getName(), root.getNamespace());
	}

	/**
	 * Saves the xml file to disk as an XML document. A temporary file is used
	 * during the writing process for maximum safety.
	 * 
	 * @param fileName
	 *            name of the file
	 * @param document
	 *            the document
	 */
	protected synchronized void save(String fileName, final Document document,
			final String schemaFileName, boolean useSchema) {
		OutputStream out = null;
		boolean error = false;
		File file = new File(fileName);
		// Write data out to a temporary file first.
		File tempFile = null;
		try {
			tempFile = new File(file.getParentFile(), System
					.currentTimeMillis()
					+ "_" + file.getName());
			// Use JDOM's XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			XMLOutputter outputter = new XMLOutputter();
			out = new BufferedOutputStream(new FileOutputStream(tempFile));
			outputter.output(document, out);
			if (useSchema) {
				validateDocument(tempFile, schemaFileName);
			}
		} catch (Exception e) {
			e.printStackTrace(); // schema parse error;
			System.err.println();
			System.err.println("There were errors so abort "
					+ "replacing the old property file:schema parse error;");
			error = true;
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
				error = true;
			}
		}

		// No errors occured, so we should be safe in replacing the old
		if (!error) {
			// Delete the old file so we can replace it.
			file.delete();
			// Rename the temp file. The delete and rename won't be an
			// automic operation, but we should be pretty safe in general.
			// At the very least, the temp file should remain in some form.
			tempFile.renameTo(file);
		}
	}

	/**
	 * Save to stream.
	 * 
	 * @param out
	 *            the out
	 */
	protected synchronized void saveToStream(OutputStream out,
			final Document document, final String schemaFileName) {
		try {
			// Use XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(document, out);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Save to stream.
	 * 
	 * @param writer
	 *            the writer
	 */
	protected synchronized void saveToStream(Writer writer,
			final Document document, final String schemaFileName) {
		try {
			// Use XMLOutputter to do the writing and formatting. The
			// file should always come out pretty-printed.
			XMLOutputter outputter = new XMLOutputter();
			outputter.output(document, writer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets the node attribute value.
	 * 
	 * @param nodeName
	 *            the node name
	 * @param attributeName
	 *            the attribute name
	 * @param root
	 *            the root
	 * 
	 * @return the node attribute value
	 */
	protected String getNodeAttributeValue(String nodeName,
			String attributeName, final Element root) {
		Element element = getElement(nodeName, root);
		return element.getAttributeValue(attributeName);
	}

	/**
	 * Parses the element's name.
	 * <p>
	 * The name of the element may be formatted like
	 * <code>"element.child.child"</code>, which the element is the child of the
	 * <code>root</code>. This method return an array representation of the
	 * given name just like
	 * <code>new String[]={"element","child","child"};</code>.
	 * 
	 * @param name
	 *            the name of the element
	 * 
	 * @return the string[]
	 */
	private String[] parseName(String name) {
		// Use a StringTokenizer to tokenize the name String.
		StringTokenizer tokenizer = new StringTokenizer(name, "/");
		String[] names = new String[tokenizer.countTokens()];

		int counter = 0;
		while (tokenizer.hasMoreElements()) {
			names[counter++] = (String) tokenizer.nextElement();
		}

		return names;
	}

	protected Document validateDocument(final File file,
			final String schemaFileName) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(true);
		getValidate(builder, schemaFileName);
		return builder.build(file);
	}

	protected Document validateDocument(final InputStream inputStream,
			final String schemaFileName) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(true);
		getValidate(builder, schemaFileName);
		return builder.build(inputStream);
	}

	protected Document validateDocument(final Reader reader,
			final String schemaFileName) throws JDOMException, IOException {
		SAXBuilder builder = new SAXBuilder(true);
		getValidate(builder, schemaFileName);
		return builder.build(reader);
	}

	protected void getValidate(SAXBuilder builder, final String schemaFileName) {
		// Specifying constraint format.
		builder.setFeature(CONSTRAINT, true);
		// Import schema file.
		builder.setProperty(SchemaProperty, schemaFileName);
	}

	/**
	 * Gets the element by the given element name and its attributeName.
	 * 
	 * @param elementName
	 *            the full name of the element.
	 * @param parent
	 *            the parent of the element to get.
	 * @param attributeName
	 *            the attributes name
	 * 
	 * @return the fist element if found.
	 * @see agenda.persistence.IXMLManager#getElementByAttribute(java.lang.String,
	 *      org.jdom.Element, java.lang.String[])
	 */
	public Element getElementByAttribute(String elementName, Element parent,
			final String[] attributeName, final String[] attributeValue) {

		List<Element> childrenList = parent.getChildren();

		Iterator<Element> iterator = childrenList.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			try {
				if (element.getName().equals(elementName)) {
					for (int i = 0, j = attributeName.length; i < j; i++) {
						String attributeString = element
						.getAttributeValue(attributeName[i]);
						if (!attributeString.equals(attributeValue[i])) {
							break;
						}
						if ((i + 1) == j) {
							return element;
						}
					}
				}
			} catch (NullPointerException e) {
				continue;
			}
		}
		return null;
	}

	/**
	 * Delete element.
	 * 
	 * @param element
	 *            the element
	 * 
	 * @return true, if delete element
	 */
	public boolean deleteElement(Element parent, String elementName,
			String attributeName, String attributeValue) {
		// Positioning
		// Delete the element.
		List<Element> childrenList = parent.getChildren();
		int counter = 0;

		Iterator<Element> iterator = childrenList.iterator();
		while (iterator.hasNext()) {
			Element element = iterator.next();
			try {
				if (element.getName().equals(elementName)) {
					String attributeString = element
					.getAttributeValue(attributeName);
					if (attributeString.equals(attributeValue)) {
						// System.out.println(counter+1);
						// parent.remove;
						childrenList.remove(counter);
						counter = -1;
						break;
					}
				}
			} catch (NullPointerException e) {
				continue;
			}
			counter++;
		}
		return counter == -1;
	}

}
