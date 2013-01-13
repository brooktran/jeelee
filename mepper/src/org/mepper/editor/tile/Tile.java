/* Tile.java 1.0 2010-2-2
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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.mepper.editor.map.Map;
import org.mepper.io.Storable;
import org.mepper.resources.ImageObject;

/**
 * <B>Tile</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor.tile Ver 1.0
 * 
 */
public interface Tile extends  ImageObject, Storable{
	int MAX_EXTENSION = 20;
	
	int getTileWidth();
	void setTileWidth(int tileWidth);
	int getTileHeight();
	void setTileHeight(int tileHieght);
//	void setTileStep(int width,int height);

	@Override
	BufferedImage getImage();
	@Override
	void setImage(BufferedImage image);
	
//	String getName();
//	void setName(String name);
	
//	Dimension getSize();
	/**
	 * Sets width and height of the tile.
	 */
//	void setSize(Dimension d);
//	int getTileWidth();
//	void setTileWidth(int tileWidth);
//	
//	int getTileHeight();
//	void setTileHeight(int tileHieght);
	
//	int getWalkable(Point position);
//	void setWalkable(Point p,int value);
	
	Point getStartingPoint();
	void setStartingPoint(Point p);
	
	Dimension getOccupie();
	void setOccupie(Dimension occupie);
//	void addOccupieArea(Point p);
	
	void draw(int screenX,int screenY,Rectangle bounds, Graphics2D g,Map map);
	
//	void draw(int screenX,int screenY,int x,int y,int width,int height, Graphics2D g,Map map);
	void addTileListener(TileListener l);
	void removeTileListener(TileListener l);
	
}
