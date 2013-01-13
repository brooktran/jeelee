/* RectangleTranslator.java 1.0 2010-2-2
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
package org.mepper.editor.coordinate;

import java.awt.Point;

import org.mepper.editor.map.MapOffset;

/**
 * <B>RectangleTranslator</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-30 created
 * @since org.mepper.editor.coordinate Ver 1.0
 * 
 */
public class RectangleTranslator implements CoordinateTranslator {

	@Override
	public Point screenToMap(Point screen, MapOffset offset) {
		Point map=new Point();
		map.x = (screen.x-offset.offsetX)/offset.stepX;
		map.y = (screen.y-offset.offsetY)/offset.stepY;
		return map;
	}

	@Override
	public Point mapToScreen(Point map, MapOffset offset) {
		Point screen = new Point();
		screen.x = map.x*offset.stepX;
		screen.y = map.y * offset.stepY;
		return screen;
	}

}
