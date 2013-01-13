/* MapListener.java 1.0 2010-2-2
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

import java.util.EventListener;

/**
 * <B>MapListener</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-9 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public interface MapListener extends EventListener{
	/**
	 * Invoked when the property of the map changed. This method may not cause 
	 * to redraw the map.
	 * 
	 */
	void layerSelectionChanged(MapEvent e);
	
	/**
	 * Invoked when the layers of the map changed. Exactly, invoked when the
	 * visible of the layers or the number of the layers changed. <p>
	 * <B>Note</B>: In order to make decision of whether repaint of the map is needed.
	 * The selection of the layer changed will trigger the {@linkplain #mapChanged()}  but not
	 * {@linkplain #layerChanged()}.
	 */
	void layerVisibleChanged(MapEvent e);
	void tileAdded(MapEvent e);
	void tileRemoved(MapEvent e);
	void offsetChanged(MapEvent e);
//	void layerSelectionChanged(MapEvent e);
}
