/* EditorViewFactory.java 1.0 2010-2-2
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

import org.mepper.editor.map.Map;
import org.mepper.editor.tile.Tile;
import org.mepper.resources.EdgeEditorView;
import org.mepper.resources.ResourceManager;
import org.mepper.resources.StorableResource;
import org.mepper.resources.gui.TileEditorView;

/**
 * <B>EditorViewFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-10 created
 * @since mepper Ver 1.0
 * 
 */
public class EditorViewFactory {

	public static EditorView getMapEditorView(Map map) {
		return new MapEditorView(map); 
	}

	public static EdgeEditorView getEdgeEditorView(Map map, StorableResource resource, ResourceManager manager) {
		return new EdgeEditorView(map,resource,manager);
	}

	public static EditorView getTileEditorView(Map newMap, Tile tile,
			ResourceManager manager) {
		return new TileEditorView(newMap,tile,manager);
	}

}
