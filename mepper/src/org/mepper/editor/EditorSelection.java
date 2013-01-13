/* DefaultSelection.java 1.0 2010-2-2
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
package org.mepper.editor;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import org.mepper.editor.map.Map;
import org.zhiwu.utils.AbstractBean;


/**
 * <B>DefaultSelection</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-1-9 created
 * @since org.map.editor Ver 1.0
 * 
 */
public class EditorSelection extends AbstractBean implements Selection{
	public static final String AREA_CHANGED = "area.changed";
	protected Area selection;
//	private MapType mapType;
	protected Map map;
	
	/** The size of the selection, representing by 
	 *  map coordinate system.
	 *  For example, {@code (3,4)} means there are 3*4=12 tiles selected
	 *  of the selection.
	 * 
	 */
//	protected Dimension size;
	/**eg: the bounds always be the bounds of the editor.*/
	protected Area clip;
	/** The size of the selection, representing by 
	 *  map coordinate system.
	 *  For example, {@code (3,4)} means there are 3*4=12 tiles selected
	 *  of the selection.
	 * 
	 */
	protected Dimension extension;
	
	private final AffineTransform transform;
	
	public EditorSelection() {
		selection = new Area();
		clip=new Area(new Rectangle(0, 0, 0, 0));
		transform =new AffineTransform();
	}
	
	@Override
	public void add(Shape area) {
		selection.add(new Area(area));
		firePropertyChange(AREA_CHANGED, null, null);
	}

    @Override
	public void add(Selection s) {
    	add(s.getExactSelection());
    }
	
	@Override
	public void reset() {
		selection.reset();	
//		bounds.setBounds(0, 0, 0, 0);
	}

	@Override
	public Dimension getExtension() {
		return extension;
	}

//	@Override
//	public Shape getSelection() {
//		Area newValue=new Area(selection);
//		newValue.intersect(new Area(bounds));
//		return newValue;
//	}
	
	@Override
	public Shape getExactSelection() {
		Area newValue=new Area(selection);
		newValue.transform(transform);
		return newValue;
	}

	@Override
	public boolean isEmpty() {
		return selection.isEmpty();
	}
	
//	@Override
//	public void setBounds(Rectangle bounds) {
//		this.bounds =bounds;
//	}

//	@Override
//	public Rectangle getBounds() {
//		return bounds;
//	}
	
	@Override
	public void setExtension(Dimension extension) {
		this.extension = extension;
	}

	@Override
	public void subtract(Selection other) {
		selection.subtract(new Area(other.getExactSelection()));
	}

	@Override
	public void subtract(Shape shape) {
		selection.subtract(new Area(shape));		
	}

	@Override
	public Shape getValidSelection() {
		Area newValue=new Area(selection);
		newValue.intersect(clip);
		newValue.transform(transform);
		return newValue;
	}

	@Override
	public boolean contains(Rectangle bounds) {
		return selection.contains(bounds.x, bounds.y, bounds.width, bounds.height);
	}

	@Override
	public boolean contains(int x, int y) {
		return selection.contains(new Point2D.Double(x,y));
	}

	@Override
	public Shape getClip() {
		return clip;
	}

	@Override
	public void setClip(Shape shape) {
		this.clip = new Area(shape);
	}
	
	@Override
	public AffineTransform getTransform() {
		return transform;
	}
	
//	@Override
//	public void translate(int x, int y) {
//		AffineTransform transform=new AffineTransform();
//		transform.translate(x, y);
//		
//		selection.transform(transform);
//		clip.transform(transform);
//	}

//	@Override
//	public void setMapType(MapType mapType) {
//		this.mapType=mapType;
//	}
//
//	@Override
//	public MapType getMapType() {
//		return mapType;
//	}
	
//	public Map getMap() {
//		return map;
//	}
//	
//	public void setMap(Map map) {
//		this.map = map;
//	}



}
