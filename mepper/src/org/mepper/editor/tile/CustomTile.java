/* DataTile.java 1.0 2010-2-2
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;

import org.mepper.editor.map.Map;

/**
 * <B>DataTile</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-27 created
 * @since org.mepper.editor.tile Ver 1.0
 * 
 */
public class CustomTile extends TileAdapter{
	private transient Map map ;
	private Color color = Color.CYAN;
	private  boolean isUsedColor = true;
	private static final Point ZERO_POINT=new Point(0,0);
	private static final Dimension DEFAULT_EXTENSION=new Dimension(1,1);
	
	public CustomTile() {
		tileWidth =48;
		tileHeight =24;
	}

	public void setValue(String value) {
//		this.value = value;
		putProperty("_value", value);
	}
	
	public String getValue() {
		return getProperty("_value");
//		return value;
	}
	
	public void setMap(Map map) {
		this.map = map;
	}

	public Map getMap() {
		return map;
	}

	public void setColor(Color c) {
		this.color = new Color(c.getRed(),c.getGreen(),c.getBlue(),200);
		
		image = new BufferedImage(tileWidth, tileHeight, BufferedImage.TYPE_4BYTE_ABGR);
		Graphics2D g=image.createGraphics();
		g.setColor(color);
		g.fillRect(0, 0, tileWidth, tileHeight);
	}

	public Color getColor() {
		return color;
	}
	
	@Override
	public void setImage(BufferedImage image) {
		super.setImage(image);
		isUsedColor =false;
	}

	public void setUsedColor(boolean isUsedColor) {
		this.isUsedColor = isUsedColor;
	}

	public boolean isUsedColor() {
		return isUsedColor;
	}
	
	
	@Override
	public void draw(int x, int y, Rectangle bounds,Graphics2D g2,Map map) {
		Graphics2D g= (Graphics2D) g2.create();
		g.setColor(color);
		Shape shape = map.getBounds(ZERO_POINT, DEFAULT_EXTENSION, false);
		g.translate(x, y);
		g.fill(shape);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof CustomTile)) {
			return false;
		}
		AbstractTile other = (CustomTile) obj;
		return other.getID() == ID;
	}

}
