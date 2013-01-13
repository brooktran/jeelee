/* StAXXmlWriter.java 1.0 2010-2-2
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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.filechooser.FileFilter;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.mepper.editor.map.Layer;
import org.mepper.editor.map.Map;
import org.mepper.editor.tile.CompositeTile;
import org.mepper.editor.tile.CustomTile;
import org.mepper.editor.tile.DefaultTile;
import org.mepper.editor.tile.Tile;
import org.mepper.io.AbstractMapWriter;
import org.mepper.io.Storable;
import org.mepper.resources.LibraryResource;
import org.mepper.resources.ProjectResource;
import org.mepper.resources.PropertySupported;
import org.mepper.resources.StorableResource;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.ArrayUtils;
import org.zhiwu.utils.DefaultPair;
import org.zhiwu.utils.ImageUtils;
import org.zhiwu.utils.Pair;

/**
 * <B>StAXXmlWriter</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-23 created
 * @since org.mepper.io.xml Ver 1.0
 * 
 */
public class StAXXmlWriter extends AbstractMapWriter{
	
	public static void main(String[] args) throws IOException {
		StorableResource resource = new ProjectResource("myProject");
		StAXXmlWriter writer = new StAXXmlWriter();
		writer.writeResource(resource, "test.stax.xml");
	}	
	
	public static final String XMLSuffix = "xml";
	public static final String ELEMENT_NAME_LIBRARY="library";
	public static final String ELEMENT_NAME_PROJECT="project";
	public static final String ELEMENT_NAME_TILE="tile";
	public static final String ELEMENT_NAME_CUSTOM="custom";
	public static final String ELEMENT_NAME_MAP="map";
	public static final String ELEMENT_NAME_COMPOSITE_TILE="composite";
	
	
	private boolean saveResource;
	private int resourceIDCounter;
//	private List<Tile> tiles;
//	private List<Pair<String, Integer>> compositeTiles;
	
	@Override
	public void writeMap(Map map, OutputStream out) throws IOException {
		try {
//			tiles = new LinkedList<Tile>();
//			compositeTiles = new LinkedList<Pair<String,Integer>>();
			
			List<Pair<Integer, Tile>> tiles = new ArrayList<Pair<Integer, Tile>>();
			XMLStreamWriter writer = startWriter(out);
			writer.writeStartElement("mepper");/// start mepper
			
			writeMap(map,tiles, writer);
			writeTiles(tiles,writer);
			
			writer.writeEndElement();///   end mepper
			endWriter(writer,out);
		} catch (XMLStreamException e) {
			AppLogging.handleException(e);
			throw new IOException(e);
		}
	}
	
	@Override
	public void writeResource(StorableResource resource, OutputStream out)
			throws IOException {
		try {
			XMLStreamWriter writer = startWriter(out);
			writeResource(writer,resource);
			endWriter(writer, out);
		} catch (XMLStreamException e) {
			AppLogging.handleException(e);
			throw new IOException(e);
		}
	}

	private XMLStreamWriter startWriter(OutputStream out) throws XMLStreamException {
		resourceIDCounter=-1;
		
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter writer;
		writer = factory.createXMLStreamWriter(out);
		writer.writeStartDocument();
		writer.writeProcessingInstruction("mepper_resource", "version=\"1.2.1.2011112418\"");
		return writer;
	}

	private void endWriter(XMLStreamWriter writer, OutputStream out) throws XMLStreamException {
		try {
			writer.writeEndDocument();
			out.close();
			writer.close();
		} catch (IOException e) {
			AppLogging.handleException(e);
			throw new XMLStreamException(e);
		}
		
	}

	private void writeTiles(List<Pair<Integer, Tile>> tiles,
			XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("tiles");
		for(Pair<Integer, Tile> pair:tiles){
			writeTile(pair,writer);
		}
		writer.writeEndElement();
	}

	private void writeTile(Tile tile, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("tile");
		
		writer.writeStartElement("occupie");
		Dimension occupie = tile.getOccupie();
		writer.writeAttribute("width", occupie.width+"");
		writer.writeAttribute("height", occupie.height+"");
		writer.writeEndElement();
		
		writer.writeStartElement("startingPoint");
		Point start = tile.getStartingPoint();
		writer.writeAttribute("x", start.x+"");
		writer.writeAttribute("y", start.y+"");
		writer.writeEndElement();
		
		
		writer.writeStartElement("step");
		writer.writeAttribute("tileWidth",tile.getTileWidth()+"");
		writer.writeAttribute("tileHeight", tile.getTileHeight()+"");
		writer.writeEndElement();
		
		
		writeimage(tile.getImage(), writer);
		
//		writer.writeEndElement();
	}

	private void writeimage(BufferedImage image, XMLStreamWriter writer) throws XMLStreamException {
		int[] imageArray= ImageUtils.imageToArray(image);
		writer.writeStartElement("image");
		writer.writeAttribute("width", image.getWidth()+"");
		writer.writeAttribute("height", image.getHeight()+"");
		writer.writeCharacters(ArrayUtils.arrayToString(imageArray));
		writer.writeEndElement();
	}

	private void writeResource(XMLStreamWriter writer,
			StorableResource r) throws XMLStreamException {
		writeStartElement(r,writer);
		writeStorableObject(r.getSource(),writer);
		writeProperties(r,writer);
//		writeSource(r,writer);
		writer.writeEndElement();
	}
	
	private void writeMap(Map map, List<Pair<Integer, Tile>> tiles, XMLStreamWriter writer) throws XMLStreamException {
		writeStartElement(map,writer);
		writeStorableObject(map, writer);
		writeProperties(map, writer);
		
		Dimension ext =map.getLogicalSize();
		writer.writeAttribute("rows",ext.width+"");
		writer.writeAttribute("columns", ext.height+"");
		writer.writeAttribute("tileWidth",map.getTileWidth()+"");
		writer.writeAttribute("tileHeight",map.getTileHeight()+"");
		writer.writeAttribute("type",map.getClass().getName());
	
		// write layers 
		for(int i=0,j=map.getLayerCount();i<j;i++){
			Layer l =map.getLayer(i);
			writer.writeStartElement("layer");/// start layer
			writer.writeAttribute("index",i+"");
			writer.writeAttribute("name", l.getName());
			writeProperties(l, writer);
			writeLayer(l,writer,tiles);
			writer.writeEndElement();///    end layer
		}
		writer.writeEndElement();/// end map
		
		
		// write composite 
		writer.writeStartElement("composite");
		for(Pair<Integer, Tile> pair: tiles){
			Tile tile = pair.getValue();
			if(tile instanceof CompositeTile){
				writeCompositeTileReference((CompositeTile)tile,writer);
			}
		}
		writer.writeEndElement();
	}
	
	
	private void writeCompositeTileReference(CompositeTile tile,
			XMLStreamWriter writer) {
		
		
	}

	private void writeLayer(Layer l, XMLStreamWriter writer, List<Pair<Integer, Tile>> tiles) throws XMLStreamException {
		Dimension d = l.getOccupie();
		int[][] board = new int[d.width][d.height]; 
		for(int p=0;p<d.width;p++){
			for(int q=0;q<d.height;q++){
				Tile tile=l.getTileAt(p, q);
				int	id = prepareTileID(tile,tiles); 
				board[p][q] = id;
			}
		}
		writer.writeCharacters(ArrayUtils.arrayToString(board));
	}
	
	private int prepareTileID(Tile tile, List<Pair<Integer, Tile>> tiles) {
		int id ;
		if(tile instanceof Tile ){
			id=  tile.getID();
		}else {//composite tile
			id= --resourceIDCounter;//TODO check reduplicate
		}
		tiles.add(new DefaultPair<Integer, Tile>(id, tile));
		 return id;
	}
	
	private void writeTile(Pair<Integer, Tile> pair, XMLStreamWriter writer) throws XMLStreamException {
		Tile tile = pair.getValue();
		if(tile instanceof DefaultTile){
			writeTile(tile,writer);
			return;
		}
		if (tile instanceof CompositeTile) {
			writer.writeStartElement("composite");
			writeCompositeTile((CompositeTile) tile,writer);
			return;
		}
		
		
	}
	private void writeCompositeTile(CompositeTile tile, XMLStreamWriter writer) {
		
	}


	private void writeProperties(PropertySupported properties,
			XMLStreamWriter writer) throws XMLStreamException {
		writer.writeStartElement("properties");
		for(String name : properties.propertyNames()){
			writer.writeStartElement("property");
			writer.writeAttribute("name", name);
			writer.writeAttribute("value", properties.getProperty(name));
			writer.writeEndElement();
		}
		writer.writeEndElement();
	}


	private void writeStartElement(StorableResource r,
			XMLStreamWriter writer) throws XMLStreamException {
		if(r instanceof LibraryResource){
			writer.writeStartElement(ELEMENT_NAME_LIBRARY);
			return;
		}
		if(r instanceof ProjectResource){
			writer.writeStartElement(ELEMENT_NAME_PROJECT);
			return;
		}
		
		Storable source = r.getSource();
		writeStartElement(source, writer);
		
	}


	private void writeStartElement(Storable source, XMLStreamWriter writer) throws XMLStreamException {
		if (source instanceof DefaultTile) {
			writer.writeStartElement(ELEMENT_NAME_TILE);
			return;
		}
		if (source instanceof CustomTile) {
			writer.writeStartElement(ELEMENT_NAME_CUSTOM);
			return;
		}
		if(source instanceof CompositeTile){
			writer.writeStartElement(ELEMENT_NAME_COMPOSITE_TILE);
			return;
		}
		if (source instanceof Map) {
			writer.writeStartElement(ELEMENT_NAME_MAP);
			return;
		}
		writer.writeStartElement("unsupported_element_"+source.getClass().getName());
	}


	/**
	 * write the <I>id,name and description</I> of the storable object.
	 */
	private void writeStorableObject(Storable source, XMLStreamWriter writer) throws XMLStreamException {
		writer.writeAttribute("id", source.getID()+"");
		writer.writeAttribute("name", source.getName());
		writer.writeAttribute("description", source.getDescription());
	}
	
	//-----------------------------
	@Override
	public FileFilter[] getFileFilters() {
		return null;
	}

	@Override
	protected String getFileSuffix() {
		return XMLSuffix;
	}


	/**
	 * @return the saveResource
	 */
	public boolean isSaveResource() {
		return saveResource;
	}


	/**
	 * @param saveResource the saveResource to set
	 */
	public void setSaveResource(boolean saveResource) {
		this.saveResource = saveResource;
	}
	
	
	
	
}
