/* MapEvent.java 1.0 2010-2-2
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

import java.util.EventObject;

/**
 * <B>MapEvent</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-18 created
 * @since org.mepper.editor.map Ver 1.0
 * 
 */
public class MapEvent extends EventObject{
	
	private int[][] area;
//	private Layer layer;
//	private int layerIndex;
	
	public MapEvent(Object source) {
		super(source);
	}

//	public MapEvent(Object source, int layerIndex, int[][] area) {
//		super(source);
//		this.layerIndex = layerIndex;
//		this.area =area;
//	}
	
	public MapEvent(Object source,  int[][] area) {
		super(source);
		this.area =area;
//		this.layer = l;
	}

	public int[][] getArea() {
		return area;
	}
	
//	public Layer getLayer() {
//		return layer;
//	}
	
//	public int getLayerIndex() {
//		return layerIndex;
//	}

}
