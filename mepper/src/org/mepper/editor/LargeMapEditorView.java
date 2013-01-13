/* MapEditorViewProxy.java 1.0 2010-2-2
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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.mepper.app.MapApplication;
import org.mepper.editor.map.Map;
import org.mepper.editor.map.MapAdapter;
import org.mepper.editor.map.MapEvent;
import org.mepper.editor.map.MapListener;
import org.mepper.editor.map.MapOffset;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppManager;

/**
 * <B>MapEditorViewProxy</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-1 created
 * @since mepper Ver 1.0
 * 
 */
public class LargeMapEditorView extends AbstractEditorView{
	private static final int MAX_TILE_EXTENSION=AppManager.getPreference(MapApplication.class.getName()).getInteger("max.tile.extension");
	private JScrollBar HBar ;
	private JScrollBar VBar;
	
	
	
	private final ComponentListener resizeListener = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			updateBufferImage();
		}
	};
	
	private final PropertyChangeListener selectionListener=new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			repaint();
		}
	};
	
	private final MapListener mapListener = new MapAdapter() {
		@Override
		public void tileAdded(MapEvent e) {
			updateBufferImage();
		}
		@Override
		public void tileRemoved(MapEvent e) {
			updateBufferImage();
		}
		@Override
		public void layerVisibleChanged(MapEvent e) {
			updateBufferImage();
		}
		@Override
		public void offsetChanged(MapEvent e) {
			updateBufferImage();
		}

	};
	
	
	@Override
	protected void updateBounds() {
		if(!isLargeMap(getSize())){
			super.updateBounds();
			return;
		}
		/// deal with large map.....
		
		JScrollPane parent = (JScrollPane) getParent().getParent();
		Rectangle paneBounds = parent.getViewportBorderBounds();//FIXME set viewport border =0
		Dimension mapSize=map.getSize();
    	Dimension panelSize=getSize();
		HBar = parent.getHorizontalScrollBar();
		VBar = parent.getVerticalScrollBar();
		
		
		Point p1 = getAdjustPoint(paneBounds.getLocation(),false,false);
		Point p2 = getAdjustPoint(new Point(paneBounds.x+paneBounds.width,paneBounds.y), true, false);
		Point p3 = getAdjustPoint(new Point(paneBounds.x+paneBounds.width,paneBounds.y+paneBounds.height), true, true);
		Point p4 = getAdjustPoint(new Point(paneBounds.x,paneBounds.y+paneBounds.height), false, true);
		
		
		bounds.x = ( mapSize.width >= panelSize.width)?
				paneBounds.x:(panelSize.width-mapSize.width)/2;;
    	bounds.y= ( mapSize.height >= panelSize.height)?
    			paneBounds.y:(panelSize.height-mapSize.height)/2;
		
		
    	bounds.width = mapSize.width;
    	bounds.height = mapSize.height;
    	
    	repaint();
    	
    	
    	
    	
	}
	
	private Point getAdjustPoint(Point screen, boolean directionX, boolean directionY) {
		Point p = map.screenToMap(screen);
		p.x += directionX?1:-1 * MAX_TILE_EXTENSION;
		p.y += directionY?1:-1 * MAX_TILE_EXTENSION;
		return p;
	}

	private boolean isLargeMap(Dimension  panelSize){
		return map.getSize().width > 1.5*panelSize.width ||  map.getSize().height>1.5*panelSize.height;
	}
	
	
	public LargeMapEditorView(Map map) {
		super(map);
		map.addMapListener(mapListener);
//		initImages();
		updateBufferImage();
    	selection.addPropertyChangeListener(selectionListener);
    	grid =true;
	}

	@Override
    protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	g.drawImage(bufferImage, bounds.x, bounds.y, this);//绘制缓存图片
    
    	drawExactSelection((Graphics2D) g.create());
	}

	@Override
	protected void updateBufferImage() {
		fillBounds(bufferGraphics);
		drawBounds(bufferGraphics);
		drawMap(bufferGraphics);
		if (grid) {
			drawGrid(bufferGraphics);
		}
		if(coordinate){
			drawCoordinate(bufferGraphics);
		}
		
//		drawExactSelection(bufferGraphics);
		repaint();
		firePropertyChange(IMAGE_CHANGED_LISTENER, null, bufferImage);
	}

	protected void drawExactSelection (Graphics2D g2) {
		g2.setColor(Color.RED);
		
		MapOffset offset=map.getOffset();
		g2.translate(bounds.x+offset.offsetX,bounds.y+offset.offsetY);
		g2.draw(selection.getExactSelection());
		g2.dispose();
	}
	
	@Override
	public void activate(Editor editor) {
		super.activate(editor);
		editor.setUserObject(MepperConstant.EDITOR_USER_OBJECT,map);
	}
	
}
