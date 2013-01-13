/* EdgeEditorView.java 1.0 2010-2-2
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

import org.mepper.editor.MapEditorView;
import org.mepper.editor.map.Map;

/**
 * <B>EdgeEditorView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-26 created
 * @since org.mepper.resources Ver 1.0
 * 
 */
public class EdgeEditorView extends MapEditorView{
	private static final Dimension DEFAULT_TILE_DIMENSION=new Dimension(1,1);
//	private static final Dimension[] DEFAULT_EDGE_DIMENSIONS=new Dimension[]{
//		DEFAULT_TILE_DIMENSION,DEFAULT_TILE_DIMENSION,DEFAULT_TILE_DIMENSION,DEFAULT_TILE_DIMENSION,
//		DEFAULT_TILE_DIMENSION,DEFAULT_TILE_DIMENSION,DEFAULT_TILE_DIMENSION,DEFAULT_TILE_DIMENSION
//	};
	
	private final Dimension tileDimension ;
	private final Dimension edgeDimension;
	
	private StorableResource lib;
	
//	private final MapListener mapListener = new MapAdapter(){
//		@Override
//		public void tileAdded(org.mepper.editor.map.MapEvent e) {
//			updateLibrary(e.getArea());
//		}
//		
//		@Override
//		public void tileRemoved(org.mepper.editor.map.MapEvent e) {
//			updateLibrary(e.getArea());
//		}
//	};
	private ResourceManager manager;
	public EdgeEditorView(Map map, StorableResource lib, ResourceManager manager) {
		this(map, DEFAULT_TILE_DIMENSION, DEFAULT_TILE_DIMENSION);
//		map.addMapListener(mapListener);
		this.lib = lib;
		this.manager =manager;
	}
	
	public EdgeEditorView(Map map,Dimension tileDimension,Dimension edgeDimension){
		super(map);
		this.tileDimension = tileDimension;
		this.edgeDimension = edgeDimension;
	}
	
//	protected void updateLibrary(int[][] area) {
//		if(!areaContains(area,edgeDimension.width,edgeDimension.height)){
//			return;
//		}
//		Tile t = map.getLayer(0).getTileAt(edgeDimension.width,edgeDimension.height);
//		if(t == null){
//			lib = null;
//			return;
//		}
//		lib = (StorableResource) editor.getUserobject(MepperConstant.EDITOR_USER_OBJECT);
//		if(lib == null){
//			return;
//		}
//		lib = lib.isLibrary()?lib:lib.getParentResource();
//	}
//
//	private boolean areaContains(int[][] area, int x, int y) {
//		for(int i=0;i<area.length;i++){
//			if(area[i][0] == x    &&    area[i][1]==y){
//				return true;
//			}
//		}
//		return false;
//	}

	public StorableResource getLib() {
		return lib;
	}

	public Dimension getEdgeDimension() {
		return edgeDimension;
	}

//	public void setResourceManager(ResourceManager manager) {
//		this.manager =manager;
//	}
//	
	public ResourceManager getManager() {
		return manager;
	}

}
