/* PaintingTool.java 1.0 2010-2-2
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

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import org.mepper.editor.map.Map;
import org.mepper.editor.tile.Tile;

/**
 * <B>PaintingTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-18 created
 * @since org.mepper.tool Ver 1.0
 * 
 */
public class PaddingTool extends CoverableTool{//填充工具
	private Tile tile ;
	private boolean isPadding;
	

	@Override
	public void mousePressed(MouseEvent e) {//鼠标按下
		isPadding=false;
		tile =getTile();
		if(tile != null){
//			tile = (Tile) editor.getUserobject(TOOL_USER_OBJECT_STRING);
			setExtensionStep((Dimension) tile.getOccupie().clone());
			isPadding = true;
		}
		
		super.mousePressed(e);
		if(popupTrigger){
			return;
		}
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
		if( isPadding){
			painting();
		}
	}

	private void painting() {//完成填充动作
		Map map =getMap();
		if(map.getLayerCount()==0){
			return;
		}
		int[][] drawArea = getDrawArea();
		
		if(checkCover(drawArea)){
			return ;
		}
		
		Tile[] tiles = new Tile[drawArea.length];
		for(int i=0,j=tiles.length;i<j;i++){
			tiles[i] = tile;
		}
		map.addTile(drawArea,tiles);
	}
	
	private boolean checkCover(int[][] area ){
		Tile t = null;
		for(int i=0,j=area.length;i<j;i++){
			t = getTileCover(area[i][0],area[i][1],tile.getOccupie().width,tile.getOccupie().height);
			if(t!=null){
				tile=t;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(! e.isControlDown()){
			return;
		}
		// padding all
		if(e.getKeyCode() == KeyEvent.VK_A){
			Map map=getMap();
			startPoint =new Point(0,0);
			extension = map.getLogicalSize();
			setSelection(map.getBounds(startPoint, extension, false));
			painting();
		}
	}
	
	@Override
	public String getID() {
		return "tool.padding";
	}
	
}
