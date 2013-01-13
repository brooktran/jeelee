/* DefaultCompositeTile.java 1.0 2010-2-2
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

import java.awt.Graphics2D;
import java.awt.Rectangle;

import org.mepper.editor.map.Map;


/**
 * <B>DefaultCompositeTile</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-5 created
 * @since org.mepper.editor.tile Ver 1.0
 * 
 */
public class DefaultCompositeTile extends AbstractCompositeTile{
	@Override
	public void draw(int x, int y, Rectangle bounds,Graphics2D g, Map map) {
		g.drawImage(image, x - startingPoint.x, y - startingPoint.y, null);
		for(int i=0;i<occupieArea.width;i++){
			for(int j=0;j<occupieArea.height;j++){
				Tile tile = getTileAt(i,j);
				if(tile instanceof CompositeTile){
					tile.draw(x, y, bounds,g,map);
				}
			}
		}
	}
}
