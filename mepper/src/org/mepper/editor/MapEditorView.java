/* MapEditorView.java 1.0 2010-2-2
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
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.mepper.editor.map.Map;
import org.mepper.editor.map.MapAdapter;
import org.mepper.editor.map.MapEvent;
import org.mepper.editor.map.MapListener;
import org.mepper.utils.MepperConstant;



/**
 * <B>MapEditorView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-24 created
 * @since org.mepper.editor Ver 1.0
 * 
 */
public class MapEditorView  extends AbstractEditorView  {
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


	public MapEditorView(Map map) {
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
		
		g2.translate(bounds.x,bounds.y);
		g2.draw(selection.getExactSelection());
		g2.dispose();
	}
	
	@Override
	public void activate(Editor editor) {
		super.activate(editor);
		editor.setUserObject(MepperConstant.EDITOR_USER_OBJECT,map);
	}
	
} 
