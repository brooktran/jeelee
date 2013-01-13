/* StaxReader.java 1.0 2010-2-2
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
package org.mepper.io.xml;

import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.mepper.editor.map.Map;
import org.mepper.io.AbstractMapReader;
import org.mepper.io.ExtensionFileFilter;
import org.mepper.resources.StorableResource;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.zhiwu.utils.AppLogging;

/**
 * <B>StaxReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-22 created
 * @since mepper Ver 1.0
 * 
 */
public class SAXXMLReader extends AbstractMapReader  {
	public static void main(String[] args) throws IOException {
		SAXXMLReader reader = new SAXXMLReader();
		reader.readResource(new String("resources/config/database/project/city.1321893808694.7.xml"));
	}
	
	private static MapHandler mainHandler = new MainHandler();
//	private static MapHandler projectHandler = new ProjectHandler();
	private static MapHandler mapHandler = new MapHandler();
	private static MapHandler resourceHandler = new ResourceHandler();
	private static MapHandler elementHandler = new ElementHandler();
	
	@Override
	public Map readMap(InputStream in) throws IOException {
		return null;
	}

	@Override
	public StorableResource readResource(InputStream in) throws IOException {
		try {
			XMLReader parser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			RootHandler handler = new RootHandler(new MapXMLContext(), mainHandler);
			parser.setContentHandler(handler);
			parser.setEntityResolver(handler);
			parser.setErrorHandler(handler);
			parser.setDTDHandler(handler);
			parser.parse(new InputSource(in));
		
		} catch (ParserConfigurationException e) {
			AppLogging.handleException(e);
		} catch (SAXException e) {
			AppLogging.handleException(e);
		}finally{
			in.close();
		}
		
		
		
		return null;
	}

	@Override
	public FileFilter[] getFilters() {
		return new FileFilter[]{new ExtensionFileFilter(".xml")};
	}
	
	
	
	
	
	
	
	public static class MapHandler {
		public void onStartElement(String uri, String tag, String qname,
				Attributes attrs, MapXMLContext context)
				throws SAXParseException {
		}

		public MapHandler onStartChild(String uri, String tag, String qname,
				Attributes attrs, MapXMLContext context) throws SAXParseException {
			throw new SAXParseException("", context.getLocator());
		}

		public void onEndChild(String uri, String tag, String qname, MapXMLContext context) throws SAXParseException {
		}

		public void onEndElement(String uri, String tag, MapXMLContext context) {
		}

		public void characters(char[] buf, int start, int count , MapXMLContext context) throws SAXParseException {
			String s = new String(buf, start, count).trim();
			if (s.length() > 0) {
				throw new SAXParseException("Unexpected text \"" + s + "\"",
						context.getLocator());
			}
		}

		protected void checkNamespace(String uri) {
		}
	}
	
	public static class RootHandler extends DefaultHandler{
		private final Stack<SAXXMLReader.MapHandler> handlers=new Stack<SAXXMLReader.MapHandler>();
		private MapHandler currentHandler=null;
		private final MapXMLContext context;
		
		public RootHandler (MapXMLContext context, MapHandler mapHandler){
			currentHandler=mapHandler;
			handlers.push(currentHandler);
			this.context= context;
		}
		
		public MapHandler getCurrentHandler() {
			return currentHandler;
		}
		
		@Override
		public void startElement(String uri, String tag, String qname,
				Attributes attrs) throws SAXException {
			MapHandler next = currentHandler.onStartChild(uri, tag, qname, attrs, context);
			handlers.push(currentHandler);
			currentHandler=next;
			currentHandler.onStartElement(uri, tag, qname, attrs, context);
		}
		
		@Override
		public void setDocumentLocator(Locator locator) {
			context.setLocator(locator);
		}
		
		@Override
		public void endElement(String uri, String tag, String qName)
				throws SAXException {
			currentHandler.onEndElement(uri, tag, context);
			MapHandler prev = handlers.pop();
			currentHandler = prev;
			if(currentHandler!=null){
				currentHandler.onEndChild(uri, tag, qName, context);
			}
		}
		
		@Override
		public void characters(char[] buf, int start, int length)
				throws SAXException {
			currentHandler.characters(buf, start, length, context);
		}
		
		@Override
		public void startPrefixMapping(String prefix, String uri)
				throws SAXException {
			context.startPrefixMapping(prefix,uri);
		}
		
		
		@Override
		public void endPrefixMapping(String prefix) throws SAXException {
			context.endPrefixMapping(prefix);
		}
		
		
		
		
	}
	
	public static class MainHandler extends MapHandler{
		@Override
		public MapHandler onStartChild(String uri, String name, String qname,
				Attributes attrs, MapXMLContext context) throws SAXParseException {
			return name.equals("project")
					||name.equals("library")
					||name.equals("map")
							? SAXXMLReader.resourceHandler:SAXXMLReader.elementHandler;
			
		}
	}
	
	
	
	public static class ResourceHandler extends MapHandler{
		@Override
		public void onStartElement(String uri, String tag, String qname,
				Attributes attrs, MapXMLContext context)
				throws SAXParseException {
			RuntimeConfigurable parentWrapper=context.currentWrapper();
			Object parent = null;
			if(parentWrapper != null){
//				parent = parentWrapper.getProxy();
			}
			
			StorableResource resource = ResourceHelper.createResource(qname);
			
			for(int i=0;i<attrs.getLength();i++){
				String attrUri = attrs.getURI(i);
//				if(attrUri != null&& !attrUri.equals("") && !attrUri.equals(uri)){
//					continue; // Ignore attributes from unknown uris
//				}
				String key = attrs.getLocalName(i);
				String value = attrs.getValue(i);
				
//				if(key )
				
				
			}
			
			System.out.println(uri);
			System.out.println(qname);
			System.out.println(tag);
			
			
			
			
			
		}
	}
	
	public static class ElementHandler extends MapHandler{
		
	}
}

























