/* XMLReader.java 1.0 2010-2-2
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.jdom.CDATA;
import org.jdom.Element;
import org.mepper.editor.map.DefaultLayer;
import org.mepper.editor.map.Layer;
import org.mepper.editor.map.Map;
import org.mepper.editor.tile.CompositeTile;
import org.mepper.editor.tile.CustomTile;
import org.mepper.editor.tile.DefaultCompositeTile;
import org.mepper.editor.tile.DefaultTile;
import org.mepper.editor.tile.Tile;
import org.mepper.editor.tile.TileFactory;
import org.mepper.io.ExtensionFileFilter;
import org.mepper.io.MapReader;
import org.mepper.io.Storable;
import org.mepper.resources.DefaultResource;
import org.mepper.resources.LibraryResource;
import org.mepper.resources.ProjectResource;
import org.mepper.resources.StorableResource;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.ArrayUtils;
import org.zhiwu.utils.ImageUtils;
import org.zhiwu.xml.DataSourceException;
import org.zhiwu.xml.DefaultXMLManagerImpl;
import org.zhiwu.xml.XMLManager;
 
/**
 * <B>XMLReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-3-5 created
 * @since org.map.editor.io Ver 1.0
 * 
 */
public class XMLReader implements MapReader{
	@Override
	public Map readMap(String filename) throws IOException {
		return readMap(new FileInputStream(new File(filename)));
	}

	@Override
	public Map readMap(InputStream in) throws IOException {
		XMLManager manager;
		try { 
			manager = new DefaultXMLManagerImpl(in);
			Element root=manager.getRootElement();
			Map map = readMap(root.getChild("map"));
			return map;
		} catch (Exception e) {
			AppLogging.handleException(e);
		} 
		
		return null;
	}

	@Override
	public StorableResource readResource(String filename) throws IOException {
		return readResource(new FileInputStream(new File(filename)));
	}

	@Override
	public StorableResource readResource(InputStream in) throws IOException {
		try {
			XMLManager manager=new DefaultXMLManagerImpl(in);
			Element root=manager.getRootElement();
			StorableResource lib= readResource(root);
			
			return lib;
		} catch (DataSourceException e) {
			AppLogging.handleException(e);
		}
		return null;
	}
	
	private StorableResource readResource(Element e) {  
		String name= e.getName();
		if (name.equals(XMLWriter.ELEMENT_NAME_LIBRARY)) {
			LibraryResource lib=new LibraryResource();
			getResource( e,lib);
			readChildren(e, lib);
			return lib;
		}
		if (name.equals(XMLWriter.ELEMENT_NAME_TILE)) {
			Tile tile=getTile(e);
			StorableResource resource=new DefaultResource(tile);
			return resource;
		}
		if(name.equals(XMLWriter.ELEMENT_NAME_CUSTOM)){
			CustomTile tile = (CustomTile) getTile(e);
			StorableResource ctr=new DefaultResource(tile);
			return ctr;
		}
		if(name.equals(XMLWriter.ELEMENT_NAME_PROJECT)){
			ProjectResource pro=new ProjectResource();
			getResource( e,pro);
			readChildren(e, pro);
			return pro;
			
		}
		if(name.equals(XMLWriter.ELEMENT_NAME_MAP)){
			try {
				Map map = readMap(e);
				StorableResource mapResource= new DefaultResource(map);
				return mapResource;
			} catch (Exception e1) {
				AppLogging.handleException(e1);
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private Map readMap(Element e) throws InstantiationException,
			IllegalAccessException, ClassNotFoundException {
		String clazz=e.getAttributeValue("type");
		Map map =(Map) Class.forName(clazz).newInstance();
		map.setTileStep(getIntAttribute(e, "tileWidth"), 
						getIntAttribute(e, "tileHeight"));
		map.setLogicalSize(getIntAttribute(e, "rows"), 
				 getIntAttribute(e, "columns"));
		getResource( e,map);
		
		Element resourceElement =e.getChild("resources");
		List<Element> resources = resourceElement.getChildren();
		List<Tile> tiles = new LinkedList<Tile>();//resources.size()
		for(Element r:resources){
			Tile t=getTile(r);
			if(t!= null){
				tiles.add(t);
			}
		}
		
		// layers
		for(int i=0;;i++){
			Element layerElement = getElementChild(e,"layer","index",i+"");
			if(layerElement == null){
				break;
			}
			Layer l = new DefaultLayer();
			l.setName(layerElement.getAttributeValue("name"));
			l.setSelection(false);
			map.addLayer(l, i);
			getProperties(layerElement,l);
			
			readTilePosition(l, layerElement, tiles);
			
		}
		return map;
	}
	
	
	private void readTilePosition(CompositeTile compositeTile,Element compositeTileElement,List<Tile> tiles){
		Element tilesElement = compositeTileElement.getChild("tiles");
		@SuppressWarnings("unchecked")
		List<Element> tilesElements=tilesElement.getChildren();
		for(int p=0,q=tilesElements.size();p<q;p++){
			Element tileElement= tilesElements.get(p);
			int tileID = getIntAttribute(tileElement, "id");
			Tile t=getTileByID(tiles,tileID);
			Tile child = createTile(tileElement.getName(),t);
			compositeTile.add(child, getIntAttribute(tileElement, "x"), getIntAttribute(tileElement, "y"));
			
			if (child instanceof CompositeTile ) {
				readTilePosition((CompositeTile)child, tileElement, tiles);
			}
		}
	}

	private Tile createTile(String name, Tile tile) {
		if(name.equals(XMLWriter.ELEMENT_NAME_COMPOSITE_TILE)){
			return TileFactory.createCompositeTile(tile);
		}
		return tile;
	}

	private Tile getTileByID(List<Tile> tiles, int tileID) {
		for(Tile t:tiles){
			if(t.getID() == tileID){
				return t;
			}
		}
		return null;
	}

	/**
	 * Gets the child elements from the specific element.
	 * 
	 * @param e
	 *            parent
	 * @param name
	 *            the name of the child
	 * @param attributeName
	 *            the attribute name
	 * @param attributeValue
	 *            the attribute value
	 * @return the element child
	 */
	private Element getElementChild(Element parent, String name, String attributeName,
			String attributeValue) {
		@SuppressWarnings("unchecked")
		List<Element> elements = parent.getChildren(name);
		for(Element child:elements){
			if(attributeValue.equals(child.getAttributeValue(attributeName))){
				return child;
			}
		}
		return null;
	}

	private Tile getTile(Element e) {
		String name= e.getName();
		if (name.equals(XMLWriter.ELEMENT_NAME_TILE)) {
			Tile tile= new DefaultTile();
			readTile(e, tile);
			getResource(e,tile);
			return tile;
		}
		if (name.equals(XMLWriter.ELEMENT_NAME_COMPOSITE_TILE)) {
			CompositeTile tile = new DefaultCompositeTile();
			readTile(e, tile);
			getResource(e, tile);
			return tile;
		}
		if(name.equals(XMLWriter.ELEMENT_NAME_CUSTOM)){
			CustomTile tile= new CustomTile(); 
			getResource(e,tile);
			
			Color color=getColor(e);
			if(color == null){
				tile.setImage(getImage(e));
			}else {
				tile.setColor(color);
			}
			return tile;
		}
		
		return null;
	}

	private void readTile(Element e, Tile tile) {
		tile.setImage(getImage(e));
		
		Element occupieElement=e.getChild("occupie");
		Dimension d= new Dimension();
		d.width = getIntAttribute(occupieElement, "width");
		d.height = getIntAttribute(occupieElement, "height");
		tile.setOccupie(d);
		
		Element startElement = e.getChild("starting_point");
		Point p=new Point();
		p.x= getIntAttribute(startElement, "x");
		p.y = getIntAttribute(startElement, "y");
		tile.setStartingPoint(p);
		
		Element stepElement = e.getChild("step");
		tile.setTileWidth(getIntAttribute(stepElement, "tileWidth"));
		tile.setTileHeight(getIntAttribute(stepElement, "tileHeight"));
//		tile.setTileStep(getIntAttribute(stepElement, "tileWidth"), 
//				getIntAttribute(stepElement, "tileHeight"));
		
	}

	private void readChildren(Element e, StorableResource lib) {
		@SuppressWarnings("unchecked")
		List<Element> children=e.getChildren();
		for(Element child:children){
			StorableResource r=readResource(child);
			if(r  != null){
				lib.addChild(r);
			}
		}
	}

	private BufferedImage getImage(Element e) {
		Element imageElement= e.getChild("image");
		int width = getIntAttribute(imageElement, "width");
		int height =getIntAttribute(imageElement, "height");
		CDATA imageData= (CDATA) imageElement.getContent(0);
		String data = imageData.getText();
		int[] pixels= ArrayUtils.toIntArray(data);
		return ImageUtils.createImage(pixels, width, height);
	}

	private Color getColor(Element e) {
		Element colorElement =e.getChild("color");
		if(colorElement == null){
			return null;
		}
		
		int r= getIntAttribute(colorElement, "r");
		int g = getIntAttribute(colorElement, "g");
		int b = getIntAttribute(colorElement, "b");
		Color color =new Color(r,g,b);
		return color;
	}

	private void getResource(Element e,Storable r) {
		r.setID(getIntAttribute(e,"id"));
		r.setName(e.getAttributeValue("name"));
		r.setDescription(e.getAttributeValue("description"));
		
		getProperties(e,r);
	}
	
	private void getProperties( Element e,Storable r) {
		Element child=e.getChild("properties");
		@SuppressWarnings("unchecked")
		List<Element> properties = child.getChildren("property");
		for (Element prop : properties) {
			r.putProperty(prop.getAttributeValue("name"),
					prop.getAttributeValue("value"));
		}

	}

	private int getIntAttribute(Element e, String name) {
		return Integer.parseInt(e.getAttributeValue(name));
	}

	@Override
	public FileFilter[] getFilters() {
		return new FileFilter[]{new ExtensionFileFilter(".xml")};
	}

}
