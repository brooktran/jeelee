/* Map.java 1.0 2010-2-2
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
package org.mepper.editor.map;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.util.List;

import javax.swing.event.UndoableEditListener;

import org.mepper.editor.coordinate.CoordinateTranslator;
import org.mepper.editor.tile.Tile;
import org.mepper.io.Storable;


/**
 * <B>Map</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public interface Map  extends Storable{
	int getTileWidth();
	int getTileHeight();
	/**
	 * Sets the width and the height of the tile.
	 */
	void setTileStep(int tileWidth,int tileHeight);
	
	MapOffset getOffset();
	void setOffset(MapOffset offset);
//	MapOffset createOffset();
	
	void addTile(int[][] area,Tile[] t); //TODO use Rectangle instead of int[][]
	void removeTile(int[][] area);	//TODO use Rectangle instead of int[][]
	
	void addLayer(Layer layer,int index);
	Layer getLayer(int index);
	int getLayer(String name);
	Layer removeLayer(int index);
	void removeLayer (Layer layer);
	int getLayerCount();
	Layer getSelectedLayer();
	List<Layer> getAllSelectedLayers();
	List<Layer> getLayers(Object key,Object value);
	int getSelectedIndex();
	
	/**
	 * Sets the row and column of the map.
	 * Note: Invoke this method will changes the <code>size</code> of the map.
	 * 
	 *  Sets the dimension of the map.
	 * Some maps will not use the rows and columns of the map,
	 * they use size instead. In this case,
	 * the rows and the columns can be less than 0. 
	 * Then the Dimension of
	 * the map will not work.
	 * 
	 * 
	 * @param rows
	 *            the rows
	 * @param columns
	 *            the columns
	 */
	void setLogicalSize(int rows,int columns);
//	void setExtension(Dimension d);
	/**
	 * Gets the dimension, the row and the column, of the map.
	 * 
	 * @return the dimension
	 */
	Dimension getLogicalSize();
	
	
	
//	void setSize(Dimension size);
	void setSize(int width, int height);
	Dimension getSize();
	
	/**
	 * Draw this map start at point {@code (x,y)}.
	 */
	void draw(int x,int y,Rectangle bounds,Graphics2D g);
	/**
	 * Paint grid for the map at point {@code (x,y)}.
	 * 
	 * @param x
	 *            the x coordinate of the starting point
	 * @param y
	 *            the y coordinate of the starting point
	 */
	void drawGrid(int x, int y, Rectangle bounds,Graphics2D g);
	/**
	 *  Paint coordinate for the map.
	 * 
	 * @param x
	 *            the offset of the x coordinate 
	 * @param y
	 *            the offset of the y coordinate 
	 */
	void drawCoordinate(int x, int y, Rectangle bounds,Graphics2D g);
	void setCoordinateTranslator(CoordinateTranslator t);

	// use offset
	Point screenToMap(Point screen);
	Point mapToScreen(Point map);

	/**
	 * copy the map with no layer.
	 */
	Map create();
	
	
	/**
	 * Gets the tile cover(the occupie area contains) the specific map point(x,y) of the selected layer.
	 * @return null if no tile has found.
	 * 
	 */
	Tile getTileCover(int x, int y);
	
	/**
	 * Gets the tile in the area(x,y,width,height) of the selected layer.
	 */
//	Tile getTileCover(int x, int y, int width, int height);
	
	/**
	 * Gets the first ocupie area of the tile.
	 */
	Point getTileStart(int x, int y);
	Rectangle getTileBounds(int x, int y);
//	Point getTilePosition(Point mapPoint);
	
	
	/**
	 * Gets the bounds.
	 * 
	 * @param startPoint
	 *            map point
	 * @param extension
	 *            the extension
	 * @param adjust
	 *            the adjust
	 * @return the bounds
	 */
	Shape getBounds(Point startPoint, Dimension extension,boolean adjust);//XXX how to remove the adjust
	
	/**
	 * Gets the extension.
	 * 
	 * @param startPoint
	 *           map point
	 * @param finishPoint
	 *            map point
	 * @return the extension
	 */
	Dimension getExtension(Point startPoint, Point finishPoint);
	
	void addMapListener(MapListener l);
	void removeMapListener(MapListener l);
	void addUndoableEditListener(UndoableEditListener l);
	void removeUndoableEditListener(UndoableEditListener l);
	/**
	 * Checks if there is any conflict area exist while put a tile with <I>width</I> Width and <I>height</I> Height at the 
	 * specific point(x,y). 
	 */
	Rectangle isCover(int x, int y, int width, int height);

	
	


	
}
