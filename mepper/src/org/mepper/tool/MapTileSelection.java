/* MapTileSelection.java 1.0 2010-2-2
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

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;
import javax.swing.JPopupMenu;

import org.mepper.editor.EditorView;
import org.mepper.editor.map.Layer;
import org.mepper.editor.map.Map;
import org.mepper.editor.tile.Tile;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.action.AbsAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>MapTileSelection</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-29 created
 * @since org.mepper.tool Ver 1.0
 * 
 */
public class MapTileSelection extends CoverableTool{
	
	/** The start point of the occupie area of the current selected tile  */
	private Point tileStartPoint ; // map point
	private Point currentMouseMapPoint;
	private int offsetX,offsetY;
	protected Tile tile;
	
	private static final Shape DEFAULT_SHAPE=new Rectangle();

	@Override
	protected void begin(MouseEvent e) {
		EditorView view = editor.getActivateView();
		Map map = view.getMap();
		Point mapPoint = view.screenToMap(e.getPoint());
		tile = map.getTileCover(mapPoint.x, mapPoint.y);
		editor.setUserObject(MepperConstant.EDITOR_USER_OBJECT, tile);
		if (tile == null) {
			startPoint=null;
			setSelection(DEFAULT_SHAPE);
			return;
		}
		extension = tile.getOccupie();
		startPoint = view.screenToMap(e.getPoint()); // current mouse point to map point.
		tileStartPoint =map.getTileStart(startPoint.x,startPoint.y);
		offsetX = startPoint.x - tileStartPoint.x;
		offsetY = startPoint.y - tileStartPoint.y;
		setSelection(map.getBounds(tileStartPoint, tile.getOccupie(), false));
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		super.mouseReleased(e);
//		Point endPoint = getMap().screenToMap(e.getPoint());
		if(popupTrigger || tile ==null ){
			return;
		}
		moveTile();
	}

	private void moveTile() {
		if(checkCover(currentMouseMapPoint.x,currentMouseMapPoint.y,
				tile.getOccupie().width ,tile.getOccupie().height )){
			return;
		}
		moveTile(tileStartPoint, currentMouseMapPoint, getMap());
	}
	
	private boolean checkCover(int x,int y,int width, int height){
		Tile t= getTileCover(x,y,width,height);
		if(t!=null){
			tile = t;
			return true;
		}
		return false;
	}
	

	
	@Override
	protected void finish(MouseEvent e) {
		if(tile == null ){
			return;
		}
		EditorView view = editor.getActivateView();
		Map map =view.getMap();
		currentMouseMapPoint = view.screenToMap(e.getPoint());
		currentMouseMapPoint.x -= offsetX;
		currentMouseMapPoint.y -= offsetY;
		setSelection(map.getBounds(currentMouseMapPoint, extension, false));
	}
	
	private void moveTile(Point tileStartPoint, Point currentMouseMapPoint,Map map ) {
		int[][] area=new int[][]{{tileStartPoint.x,tileStartPoint.y}};
		map.removeTile(area);
		
		area[0][0]=currentMouseMapPoint.x;
		area[0][1]=currentMouseMapPoint.y;
		map.addTile(area, new Tile[]{tile});
		
		setSelection(map.getBounds(currentMouseMapPoint, extension, false));
	}

	@Override
	public String getID() {
		return "tool.tile.selection";
	}
	
	@Override
	public Cursor getCursor() {
		return new Cursor(Cursor.MOVE_CURSOR);
	}

	//// popup menu
	@Override
	protected void handlePopupMenu(MouseEvent e) {
		Point screenPoint = e.getPoint();
		JPopupMenu menu = getSelectionPopupMenu(screenPoint);
//		menu.add(new JSeparator());
		addMoveMenu(menu,screenPoint);
        menu.show(editor.getActivateView().getComponent(), screenPoint.x, screenPoint.y);
	}

	private void addMoveMenu(JPopupMenu menu, Point screenPoint) {
		AppResources resources = AppManager.getResources();
		EditorView view=editor.getActivateView();
		final Map map =view.getMap();
		
		/// move to menu
		JMenu movetoMenu = new JMenu();
		resources.configMenu(movetoMenu, "moveto");
		for(int i=0,j=map.getLayerCount();i<j;i++){
			Layer layer = map.getLayer(i);
			MovetoAction movetoAction = new MovetoAction(layer);
			movetoMenu.add(movetoAction);
		}
		
		menu.add(movetoMenu);		
	}
	
	class MovetoAction extends AbsAction{
		Layer layer ;
		public MovetoAction(Layer layer) {
			super(layer.getName());
			setLayer(layer);
		}
		public void setLayer(Layer layer){ 
			this.layer = layer;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(tile == null ){
				return ;
			}
			Map map = getMap();
			int[][] area=new int[][]{{tileStartPoint.x,tileStartPoint.y}};
			
			
			boolean[] selectedLayers= new boolean[map.getLayerCount()];///backup selection
			for(int i=0,j=map.getLayerCount();i<j;i++){
				Layer layer = map.getLayer(i);
				selectedLayers[i] = layer.isSelected();
				layer.setSelection(false);
			}
			
			area[0][0]=currentMouseMapPoint.x;
			area[0][1]=currentMouseMapPoint.y;
			layer.setSelection(true);
			if(checkCover(currentMouseMapPoint.x,currentMouseMapPoint.y,
					tile.getOccupie().width,tile.getOccupie().height)){
				return ;
			}
			
			map.removeTile(area);
			map.addTile(area, new Tile[]{tile});
			
			for(int i=0,j=map.getLayerCount();i<j;i++){
				Layer layer = map.getLayer(i);
				layer.setSelection(selectedLayers[i]);
			}
			
			setSelection(map.getBounds(currentMouseMapPoint, extension, false));
			
			
		}
		
	}
	
	
	
	
	
	
}
