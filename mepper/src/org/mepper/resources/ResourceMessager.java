/* ResourceMessager.java 1.0 2010-2-2
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
package org.mepper.resources;

import java.awt.Dimension;
import java.awt.Point;

import org.mepper.editor.map.Map;
import org.mepper.editor.map.MapFactory;
import org.mepper.editor.tile.Tile;
import org.mepper.io.Storable;

/**
 * <B>ResourceMessager</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-24 created
 * @since org.mepper.resource Ver 1.0
 * 
 */
public class ResourceMessager {

	public static String getMessage(StorableResource r) {
		StringBuilder sb=new StringBuilder();
		sb.append(r.getID());
		sb.append(",");
		sb.append(r.getName());
		sb.append(",");
		sb.append(r.getDescription());
		
		Storable s=r.getSource();
		if(s instanceof Tile){
			Tile t=(Tile) r.getSource();
			sb.append(" 起:(");
			
			Point p=t.getStartingPoint();
			sb.append(p.x);
			sb.append(",");
			sb.append(p.y);
			sb.append(")");
			
			
			Dimension d=t.getOccupie();
			sb.append(" 占:(");
			sb.append(d.width);
			sb.append(",");
			sb.append(d.height);
			sb.append(")");
		}else if (s instanceof Map) {
			Map map= (Map)r.getSource();
			
//			sb.append(map.getName());
			sb.append(" ,");
			sb.append(MapFactory.getMapTypeDisplayName(map));
			
			Dimension e=map.getLogicalSize();
			sb.append(", 大小:(");
			sb.append(e.width);
			sb.append(",");
			sb.append(e.height);
			sb.append("), ");
			
			sb.append(" 区块:(");
			sb.append(map.getTileWidth());
			sb.append(",");
			sb.append(map.getTileHeight());
			sb.append(")");
		}
		
		
		return sb.toString();
	}

}
