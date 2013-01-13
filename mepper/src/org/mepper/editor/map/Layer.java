/* Layer.java 1.0 2010-2-2
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

import org.mepper.editor.tile.CompositeTile;
import org.mepper.editor.tile.Tile;

/**
 * <B>Layer</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-9 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public interface Layer extends CompositeTile{
	boolean isVisible();
	void setVisible(boolean b);
	
	boolean canSelecte();
	boolean isSelected();
	void setSelection(boolean b);
	
	void insertTile(int[][] area,Tile[] tile);
	void removeTile(int[][] area);
	
	void addLayerListener(LayerListener l);
	void removeLayerListener(LayerListener l);
	
//	void addLinkedLayer(int index,Layer l);
//	void removeLinkedLayer(int index);
//	void getLinkedLayer(int index);
//	void getLinkedLayerCount();
}
