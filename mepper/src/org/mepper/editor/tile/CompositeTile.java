/* CompositeTile.java 1.0 2010-2-2
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
package org.mepper.editor.tile;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

/**
 * <B>CompositeTile</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-9 created
 * @since org.mepper.editor.tile Ver 1.0
 * 
 */
public interface CompositeTile extends Tile{
//	public void draw(int x, int y, Rectangle bounds,Graphics2D g, Map map);

	/**
	 * Adds the.
	 * 
	 * @return if the current point not null, then return the tile's bounds of
	 *         the point, or the new tile's bound to add.
	 */
	void add(Tile tile,int x, int y); 
	Tile remove(int x, int y);
	void removeAll(int x, int y,Dimension size);
	
	Tile[][] getTiles();
	Tile getTileAt(int x, int y);
	
	/**
	 * Gets the tile at the specific {@code map point (x,y)}.
	 */
	Tile getTileCover(int x, int y);
	
	/**
	 * Gets the start point of the tile at the specific position (x,y).
	 */
	Point getTileStart(int x, int y);
	/**
	 * Gets the location and the dimension of the tile
	 */
	Rectangle getTileBounds(int x, int y);
	/**
	 * Checks if there is any conflicting area exist while put a tile with <I>width</I> Width and <I>height</I> Height at the 
	 * specific point(x,y). 
	 * @return the bounds of the conflicting area.
	 */
	Rectangle getTileCover(int x, int y,int width,int height) ;
	
	Point[] indexOf(Tile tile);
}
