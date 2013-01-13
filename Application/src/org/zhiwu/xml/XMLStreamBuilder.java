/* AppXMLUtils.java 1.0 2010-2-2
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
package org.zhiwu.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.StreamFilter;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

/**
 * <B>AppXMLUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.zhiwu.xml Ver 1.0
 * 
 */
public class XMLStreamBuilder implements XMLStreamConstants {
	private XMLInputFactory factory;
	private final EscapeEmptyFilter escapeEmptyFilter ;
	private XMLStreamReader parser ;
	 
	public XMLStreamBuilder(){
		escapeEmptyFilter =  new EscapeEmptyFilter();
	}
	
	public void build(InputStream stream) throws XMLStreamException {
		parser = getStreamReader(stream);
	}

	public void build(String file) throws FileNotFoundException, XMLStreamException {
		build(new FileInputStream(file));
	}
	
	public boolean hasNextTag() throws XMLStreamException{
		int event;
		while(parser.hasNext()){
			event = parser.getEventType();
			if(!(	event ==XMLStreamReader.END_ELEMENT ||
					event == XMLStreamReader.SPACE ||
					event == XMLStreamReader.COMMENT )){
				return true;
			}
			
			event=parser.next();
		}
		return parser.hasNext();
	}
	
	public int nextTag() throws XMLStreamException{
		return parser.next();
	}
	
	public QName getName(){
		return parser.getName();
	}
	
	public boolean hasText(){
		return parser.hasText();
	}
	
	public String getElementText() throws XMLStreamException{
		return parser.getElementText();
	}
	
	public int getAttributeCount(){
		return parser.getAttributeCount();
	}
	
	public QName getAttributeName(int index){
		return parser.getAttributeName(index);
	}
	
	public String getAttributeValue(int index){
		return parser.getAttributeValue(index);
	}
	public String getAttributeType(int index){
		return parser.getAttributeType(index);
	}
	
	public int getEventType(){
		return parser.getEventType();
	}
	
	public Location getLocation(){
		return parser.getLocation();
	}
	
	public void close() throws XMLStreamException{
		if(parser != null){
			parser.close();
		}
	}
	
	
	public XMLEventReader getEventReader(String path) throws FileNotFoundException, XMLStreamException{
		return getEventReader(new FileInputStream(path));
	}
	public XMLEventReader getEventReader(InputStream stream) throws XMLStreamException{
		return getFactory().createXMLEventReader(stream);
	}

	public  XMLStreamReader getStreamReader(String path) throws FileNotFoundException, XMLStreamException{
		return getStreamReader(new FileInputStream(path));
	}
	public XMLStreamReader getStreamReader(InputStream stream) throws XMLStreamException {
		return getFactory().createXMLStreamReader(stream);
	}

	private XMLInputFactory getFactory() {
		return factory==null? factory=XMLInputFactory.newInstance(): factory;
	}

	
 
}

class EscapeEmptyFilter implements StreamFilter{

	@Override
	public boolean accept(XMLStreamReader reader) {
		try {
			while(reader.hasNext()){
				int event = reader.getEventType();
				if(!(	event ==XMLStreamReader.END_ELEMENT ||
						event == XMLStreamReader.SPACE ||
						event == XMLStreamReader.COMMENT )){
					return true;
				}
				reader.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
	
}
