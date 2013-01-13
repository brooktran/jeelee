/* NameEdgeGenerator.java 1.0 2010-2-2
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
import java.util.LinkedList;
import java.util.List;

import org.mepper.editor.map.Layer;
import org.mepper.editor.tile.Tile;

/**
 * <B>NameEdgeGenerator</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-26 created
 * @since org.mepper.resources Ver 1.0
 * 
 */
public class NameEdgeGenerator implements EdgeGenerator {
	public static final int[][] ORIENTATE=new int[][]{
		{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1},{0,1},{1,1},{1,0}		};
//	private Dimension tileDimension ;
//	private final Dimension[] edgeDimensions = new Dimension[8];
//	private Layer layer ;
//	private StorableResource lib;
	private EdgeEditorView view;

	@Override
	public void generate() {
		Dimension edgeDimension =view.getEdgeDimension();
		int mainX = edgeDimension.width, mainY = edgeDimension.height;
		
		Layer layer = view.getMap().getLayer(0);
		Tile t = layer.getTileAt(mainX,mainY);
		
		StorableResource lib = view.getLib();
		List<StorableResource> condidateEdges = new LinkedList<StorableResource>();
		ResourceManager manager = view.getManager();
		manager.setCurrentNode(lib);
		
		if(t == null){
			throw new NullPointerException("main.tile");
		}
		
		StorableResource  tileResource = lib.getChildByID(t.getID());
		int priority = getPriority(tileResource);
		if(priority<0){
			throw new IllegalStateException();
		}
		
		for(int i=0;i<8;i++){
			int x= mainX+ORIENTATE[i][0];
			int y= mainY+ORIENTATE[i][1];
			
			x += edgeDimension.width - 1;
			y += edgeDimension.height - 1;
			
			Tile edge = layer.getTileAt(x, y);
			if(edge == null){
				continue;
			}
			
			StorableResource sr = lib.getChildByID(edge.getID());
			StorableResource edgeResource = copy(sr);
			edgeResource.setName(tileResource.getName()+"."+i);
			tileResource.addChild(edgeResource);
			condidateEdges.add(sr);
		}
		
		
		for(StorableResource r:condidateEdges){
			try {
				manager.removeResource(r);
//				lib.removeChild(r);
			} catch (Exception e) {
			}
			
		}
	}
	
	
	
	private StorableResource copy(StorableResource r){
		try {
			StorableResource other = r.getClass().newInstance();
			other.setSource(r.getSource());
			return other;
		} catch (Exception e) {
			return null;
		} 
	}

//	@Override
//	public void setLayer(Layer layer) {
//		this.layer = layer;
//	}
//
//	@Override
//	public void setTileOccupie(Dimension d) {
//		tileDimension =d;
//	}
//
//	@Override
//	public void setEdgeOccupie(Dimension... dimensions) {
//		if(dimensions.length ==1){
//			for(int i=0;i<edgeDimensions.length;i++){
//				edgeDimensions[i] = dimensions[0];
//			}
//		}else if(dimensions.length == 8){
//			edgeDimensions=dimensions;
//		}else {
//			throw new IllegalArgumentException();
//		}
//	}
//
//	@Override
//	public void setLibrary(StorableResource lib) {
//		if (!lib.isLibrary()) {
//			lib = lib.getParentResource();
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	private int getPriority(PropertySupported p){
		try{
			return Integer.parseInt(p.getProperty("priority"));
		}catch (Exception e) {
			return -1;
		}
	}

	@Override
	public void setEdgeView(EdgeEditorView view) {
		this.view = view;
	}
	

}
