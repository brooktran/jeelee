/* AbstractEditorView.java 1.0 2010-2-2
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
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.Collections;

import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.undo.UndoManager;

import org.mepper.editor.map.Map;

/**
 * <B>AbstractEditorView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public abstract class AbstractEditorView extends JPanel implements EditorView{
	protected Rectangle bounds;
	protected BufferedImage bufferImage;//用于缓存的图片
	protected Graphics2D bufferGraphics;
	
	protected Editor editor;
	protected Selection selection;
	protected  Map map;
	protected boolean grid = true;
	protected boolean coordinate;
	
	protected Rectangle mapBounds;
	
	private final ComponentListener resizeListener = new ComponentAdapter() {
		@Override
		public void componentResized(ComponentEvent e) {
			updateBounds();
		}
	};
	
	protected final UndoManager undoManager = new UndoManager(){
		@Override
		public void undoableEditHappened(javax.swing.event.UndoableEditEvent e) {
			this.addEdit(e.getEdit());
			fireUndoHappen();
		};
	};
	
	public AbstractEditorView(Map map) {
		setMap(map);
		
		selection= new EditorSelection();
		bounds=new Rectangle();
		Dimension d=map.getSize();
		bounds.width = d.width;
		bounds.height = d.height;
		
		initBufferImage();
		addComponentListener(resizeListener);
		
		
//		setDoubleBuffered(true);
	}
	
	protected abstract void updateBufferImage() ;

	protected void updateBounds() {
    	Dimension mapSize=map.getSize();
    	Dimension panelSize=getSize();
    	
    	bounds.x = ( mapSize.width >= panelSize.width)?
    			0:(panelSize.width-mapSize.width)/2;;
    	bounds.y= ( mapSize.height >= panelSize.height)?
    			0:(panelSize.height-mapSize.height)/2;
    	
    	bounds.width = mapSize.width;
    	bounds.height = mapSize.height;
    	
    	repaint();
    	
	}
	

	private void initBufferImage() {
		bufferImage=createImage();
		bufferGraphics = bufferImage.createGraphics();
		bufferGraphics.setBackground(getBackground());
		setViewRenderingHints(bufferGraphics);//设置抗齿锯等属性		
	}

	private BufferedImage createImage() {
		Dimension d=map.getSize();
		return new BufferedImage(d.width+1, d.height+1, BufferedImage.TYPE_4BYTE_ABGR);
	}
	
	@Override
	public BufferedImage getImage() {
		return bufferImage;
	}

	protected void setViewRenderingHints(Graphics2D g) {
		// Set rendering hints for speed
		g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		g.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_SPEED);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
	}
	@Override
	public JComponent getComponent() {
		return this;
	}

	@Override
	public Rectangle getMapBounds() {
		return bounds;
	}
	
	@Override
	public Editor getEditor() {
		return editor;
	}
	
	@Override
	public Map getMap() {
		return map;
	}
	
	@Override
	public void setMap(Map map) {
		this.map = map;
		setPreferredSize(map.getSize());	//Don't remove it.
		setSize(map.getSize());        		//Don't remove it. use to SnapshotPanel 
		map.addUndoableEditListener(undoManager);
		mapBounds =new Rectangle();
		mapBounds.setSize(map.getLogicalSize());
	}

	@Override
    public boolean equals(Object obj) {
    	if(obj == null){
    		return false;
    	}
    	if(! ( obj instanceof EditorView ) ||  (!obj.getClass().equals(this.getClass()))){
    		return false;
    	}
    	
    	AbstractEditorView other = (AbstractEditorView)obj;
    	return map.equals(other.map);
    }

	@Override
	public Selection getSelection() {
		return selection;
	}
	
	@Override
	public void activate(Editor editor) {
		this.editor=editor;
	}
	
	@Override
	public Point mapToScreen(Point mapPoint) {
		Point p = map.mapToScreen(mapPoint);
		p.x += bounds.x;
		p.y += bounds.y;
		return p;
	}
	
	@Override
	public Point screenToMap(Point mousePoint) {
		Point p = new Point();
		p.x= mousePoint.x - bounds.x;
		p.y = mousePoint.y -bounds.y;
		return map.screenToMap(p);
	}
	
	
	@Override
	public Collection<? extends Action> getActions() {
		return Collections.emptyList();
	}
	
	///   undo redo
	@Override
	public UndoManager getUndoManager() {
		return undoManager;
	}
	
	
	
	@Override
	public void setGrid(boolean b) {
		grid =b;
		updateBufferImage();
	}
	
	@Override
	public boolean isGrid() {
		return grid;
	}
	
	@Override
	public void setCoordinate(boolean coordinate) {
		this.coordinate = coordinate;
		updateBufferImage();
	}
	
	@Override
	public boolean isCoordinate() {
		return coordinate;
	}
	/// draw
	
	protected void fillBounds(Graphics2D g) {
		g.setColor(Color.WHITE);//XXX read from preference file.
		g.fillRect(0, 0, bounds.width, bounds.height);
	}
 
	protected void drawBounds(Graphics2D g) {
		g.setColor(Color.BLACK);//XXX read from preference file.
		g.drawRect(0, 0, bounds.width, bounds.height);
	}

	protected void drawMap(Graphics2D g) {
		map.draw(0, 0 ,mapBounds,g);
	}

	protected void drawGrid(Graphics2D g) {
		g.setColor(new Color(0,255,255,90));//XXX read from preference file.
		map.drawGrid(0, 0,mapBounds, g);
	}

	protected void drawCoordinate(Graphics2D g) {
		g.setColor(new Color(0,0,0)); //XXX read from preference file.
		map.drawCoordinate(0,0,mapBounds,g);
	}

	protected void drawValidSelection(Graphics2D g) {
	}

	protected void fillSelectionShadow(Graphics2D g) {
	}
	/// end draw
	
	protected void fireUndoHappen() {
		firePropertyChange(UNDO_HAPENED, null, null);
	}
	
}
