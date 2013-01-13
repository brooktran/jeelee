/* TileCreationTool.java 1.0 2010-2-2
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
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.Action;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import org.mepper.editor.Editor;
import org.mepper.editor.EditorView;
import org.mepper.editor.map.Map;
import org.mepper.editor.map.MapOffset;
import org.mepper.editor.tile.Tile;


/**
 * <B>TileCreationTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-12 created
 * @since org.mepper.tool Ver 1.0
 * 
 */
public class TileCreationTool extends SelectionTool{
	private Tile tile;
	private static final Dimension defaultExt =  new Dimension(1,1);
	private Point tileStrartingPoint;
	
	
	@Override
	public void activate(Editor editor) {
		super.activate(editor);
		getTile();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isRightMouseButton(e)){
			handlePopupMenu(e);
			return;
		}
		if (SwingUtilities.isLeftMouseButton(e)) {
			getTile();
			if(tile ==null){
				return;
			}
			getOptionComponent().setTile(tile);
			begin(e);
		}
	}
	
	@Override
	protected void begin(MouseEvent e) {
		Point mousePoint = e.getPoint();
		EditorView view=editor.getActivateView();
		view.getComponent().requestFocus();
		Map map=editor.getActivateView().getMap(); 
		Rectangle mapBounds=view.getMapBounds();
		Point p=new Point();
		p.x= mousePoint.x - mapBounds.x;
		p.y= mousePoint.y - mapBounds.y;
		tile.setStartingPoint(p);
		tileStrartingPoint = p ;
		
		
		MapOffset offset=map.getOffset();
		offset.offsetX = p.x;
		offset.offsetY = p.y;
			
		startPoint = new Point(0,0);
		setSelection(map.getBounds(startPoint, defaultExt,false));
	}
	
	@Override
	protected void finish(MouseEvent e) {
		Point mousePoint = e.getPoint();
		EditorView view=editor.getActivateView();
		Map map=view.getMap(); 
		
		Point finishPoint=view.screenToMap(mousePoint);
		Dimension newExtension = map.getExtension(
				startPoint,finishPoint);
		
		if (newExtension.equals(extension)) {
			return;
		}
		extension =newExtension;
		reviseOccupie(newExtension);
		getOptionComponent().setTile(tile);
		setSelection(map.getBounds(startPoint, extension,false));
	}
	
	
	private void reviseOccupie(Dimension e) {
		EditorView view=editor.getActivateView();
		Map map=view.getMap();
		
		Point oldStartPoint = tileStrartingPoint;
		int width= e.width<0? e.width : 0;
		int height = e.height<0? e.height: 0;
		Point newStartPoint= map.mapToScreen(new Point(width,height));
		
		newStartPoint.x += oldStartPoint.x;
		newStartPoint.y += oldStartPoint.y;
		
		
		e.width = Math.abs(e.width);
		e.height = Math.abs(e.height);
		setSelection(map.getBounds(new Point(0,0), e, false));
		
		tile.setStartingPoint(newStartPoint);
		tile.setOccupie(extension = e);
		getOptionComponent().setTile(tile);
	}

	
	@Override
	public TileCreationToolOption getOptionComponent() {
		if(component == null){
			component = TileCreationToolOption.getInstance(editor);
		}
		return (TileCreationToolOption) component;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(tile == null){
			return;
		}
		int key=e.getKeyCode() ;
		switch (key) {
		case KeyEvent.VK_UP:
			move(0,-1);
			break;
		case KeyEvent.VK_DOWN:
			move(0,1);
			break;
		case KeyEvent.VK_LEFT:
			move(-1,0);
			break;
		case KeyEvent.VK_RIGHT:
			move(1,0);
			break;
		default:
			break;
		}
		getOptionComponent().setTile(tile);
	}
	
	private void move(int x, int y) {
		Point p= tile.getStartingPoint();	
		p.x +=x;
		p.y +=y;
		tile.setStartingPoint(p);
	}
	
	

	@Override
	protected void handlePopupMenu(MouseEvent e) {
		Point p=e.getPoint();
		EditorView view=editor.getActivateView();
        LinkedList<Action> popupActions = new LinkedList<Action>();
        
        LinkedList<Action> editorActions = new LinkedList<Action>(
        		view.getActions());
        popupActions.addAll(editorActions);
        
        JPopupMenu menu=new JPopupMenu();
        for (Action a : popupActions) {
        	if(a!=null){
        		menu.add(a);
        	}
        }
        
        menu.show(view.getComponent(), p.x, p.y);
	}
	
	@Override
	public String getID() {
		return "tool.tile.creation";
	}
}
