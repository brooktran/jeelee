/* TileSelectionTool.java 1.0 2010-2-2
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
package org.mepper.tool;

import java.awt.event.MouseEvent;

import javax.swing.SwingUtilities;

import org.mepper.editor.tile.Tile;


/**
 * <B>TileSelectionTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-14 created
 * @since org.mepper.tool Ver 1.0
 * 
 */
public class TileSelectionTool extends SelectionTool {
	protected Tile tile;
	
	@Override
	public void mousePressed(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			getTile();
			if(tile ==null){
				return;
			}
			getOptionComponent().setTile(tile);
		}	
	}
	
	@Override
	public TileCreationToolOption getOptionComponent() {
		if(component == null){
			component = TileCreationToolOption.getInstance(editor);
		}
		return (TileCreationToolOption) component;
	}
	
	
	@Override
	public String getID() {
		return "tool.selection";
	}

}
