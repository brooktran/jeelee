package com.test.xml;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * Class <B>XMLManager</B> provides the convenience to use simple XML database.
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.1.01 2009-4-17 modify
 * @since chenzhw Ver 1.0 <br>
 *        Ver 2009 add methods to handle a xml file with namespace. <br>
 *        Ver 2002 Jdon
 * 
 */
public class XMLManager {

	private Document document;
	/**
	 * Since parsing the XML file is time-consuming, we use a Map to cache some
	 * Notes that are used more than once.
	 */
	private Map<String, String> xmlCache = new HashMap<String, String>();

	/**
	 * Create a new XMLManager.
	 * 
	 * @param filename
	 *            the full path of the XML file which shoule be read from and
	 *            written to.
	 * 
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public XMLManager(String filename) throws IOException {
		try {
			SAXBuilder builder = new SAXBuilder();
			document = builder.build(new File(filename));
		} catch (Exception e) {
			throw new IOException("Cann't create the XML file with file name "
					+ filename, e);
		}

	}

	/**
	 * The Constructor.
	 * 
	 * @param inputStream the input stream
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public XMLManager(InputStream inputStream) throws IOException {
		try {
			SAXBuilder builder = new SAXBuilder();
			document = builder.build(inputStream);
		} catch (Exception e) {
			throw new IOException("Cann't create the XML file with InputStream "+
					inputStream, e);
		}

	}
	
	/**
	 * The Constructor.
	 * 
	 * @param reader the reader
	 * 
	 * @throws IOException Signals that an I/O exception has occurred.
	 * 
	 * @since chenzhw
	 */
	public XMLManager(Reader reader)throws IOException {
		try {
			SAXBuilder builder = new SAXBuilder();
			document = builder.build(reader);
		} catch (Exception e) {
			throw new IOException("Cann't create the XML file with Reader "+
					reader, e);
		}
	}
	
	
	/**
	 * Gets the value of the specified name.
	 * 
	 * @param name the name of the node.
	 * 
	 * @return the value of the specified name.
	 */
	public String getValue(String name){
		if (xmlCache.containsKey(name)) {//Return a existed value.
			return xmlCache.get(name);
		}
		
		String[] names=parseName(name);
		
		Element element=document.getRootElement();		
		for (int i = 0,j=names.length; i <j; i++) {
			element=element.getChild(names[i], element.getNamespace());
			
			// This node doesn't match the path of the document which indicates
			// this name doesn't exist so return null.
			if (element==null) {
				return null;
			}
		}
		
		//Get the value.
		String value=element.getText();
		if (value==null||value.equals("")) {// Do not add null element to the cache. 
			return value;
		}
		//Add to cache so that getting property next time is faster.
		xmlCache.put(name, value);
		return value;
	}

	
	/**
	 * Parses the propery name.
	 * <p>
	 * The name of the element may be formatted like
	 * <code>"element.child.child"</code>, which the element is the child of the
	 * <code>root</code>. This method return an array representation of the
	 * given name just like <code>new String[]={"element","child","child"};</code>.
	 * 
	 * @param name
	 *            the name of the element
	 * 
	 * @return the string[]
	 */
	private String[] parseName(String name){	
		// Use a StringTokenizer to tokenize the name String.
		StringTokenizer tokenizer=new StringTokenizer(name,".");
		String[] names=new String[tokenizer.countTokens()];

		int counter=0;
		while (tokenizer.hasMoreElements()) {
			names[counter++] = (String)tokenizer.nextElement();
		}
		
		return names;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
