/* DavidTransitor.java 1.0 2010-2-2
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
package org.mepper.editor.tile.terrain;

import java.awt.Dimension;

import org.mepper.editor.map.Layer;
import org.mepper.editor.map.UnSelecteLayer;
import org.mepper.editor.tile.CompositeTile;
import org.mepper.editor.tile.Tile;
import org.mepper.editor.tile.TileFactory;
import org.mepper.io.Storable;
import org.mepper.resources.NameEdgeGenerator;
import org.mepper.resources.StorableResource;

/**
 * <B>DavidTransitor</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-5 created
 * @since org.mepper.editor.tile Ver 1.0
 * 
 */
public abstract class DavidTransitor extends  AbstractTerrainTransitor{
	protected int maxPriority;
	protected int minPriority ;
	public static final String LAYER_NAME = "david";
	
//	private static final int[][] orientate=new int[][]{
//		{1,-1},{-1,-1},{-1,1},{1,1},{0,-1},{-1,0},{0,1},{1,0}		};
	
	@Override
	public Layer transition() {
		prepareResourceTree();
		
		minPriority = Integer.MAX_VALUE;
		maxPriority= Integer.MIN_VALUE;
		
		Dimension d= layer.getOccupie();
		int[][] prioritys=new int[d.width][d.height];
		
		Layer retval =new UnSelecteLayer();
		retval.setName(LAYER_NAME);
		retval.setOccupie(d);
		
		for(int i=0;i<d.width;i++){
			for(int j=0;j<d.height;j++){
				Tile t=layer.getTileAt(i, j);
				if(t== null ){
					continue;
				}
				int p= getProperty(t,"priority");
				updateMaxMinPriority(p);
				prioritys[i][j] = p;
			}
		}
		transition(prioritys,retval);
		return retval;
	}

	private void updateMaxMinPriority(int p) { 
		minPriority =  minPriority>p ? p:minPriority;
		maxPriority = maxPriority<p? p:maxPriority;
	}

	protected void transition(int[][] priorities, Layer retval) {
		for (int currentPriority = minPriority; currentPriority <= maxPriority; currentPriority++) {// for every priority
			for (int i = 0, p = priorities.length; i < p; i++) {// for every tile
				for (int j = 0, q = priorities[0].length; j < q; j++) {
					if (priorities[i][j] == currentPriority) {//
						transition(priorities, retval, currentPriority, i,j, p,
								 q);
					}
				}
			}
		}
	}

	protected void transition(int[][] priorities, Layer retval,
			int currentPriority, int row, int column, int layerWidth, int layerHeight) {
		int x;
		int y;
		for (int orientate = 0; orientate < 8; orientate++) { // from 1 to 8
			x = row + NameEdgeGenerator.ORIENTATE[orientate][0];
			y = column + NameEdgeGenerator.ORIENTATE[orientate][1];
			if (x < 0 || y < 0 || x >= layerWidth || y >= layerHeight) {
				continue;
			}
			int otherPriority = priorities[x][y];
			if (otherPriority < currentPriority) {
				StorableResource edge = getEdge(currentPriority, orientate);
				if (edge == null) {
					continue;
				}
				Tile transTile = (Tile) edge.getSource();
				addToRetval(transTile, retval, x, y);
			}
		}
	}
	
	protected abstract StorableResource getEdge(int currentPriority, int orientate);
	protected abstract void prepareResourceTree() ;
//		
//		int x,y;
//		minPriority++;
//		for(;minPriority<=maxPriority;minPriority++){
//			for(int i=0,p=priorities.length;i<p;i++){
//				for(int j=0,q=priorities[0].length;j<q;j++){
//					if(priorities[i][j] == minPriority){
//						for(int o=128;o>0;o= o>>1){
//							int ori=NameEdgeGenerator.ORIENTATE.length-log2(o);
//							x =  i+NameEdgeGenerator.ORIENTATE[ori][0];
//							y =  j+ NameEdgeGenerator.ORIENTATE[ori][1];
//							if(x<0 || y<0 || x>=p || y>=q){
//								continue;
//							}
//							int otherPriority = priorities[x][y];
//							if (otherPriority < minPriority) {
//								String name = minPriority + "."
//										+ format(Integer.toBinaryString(o));
//								StorableResource r = ((LibraryResource)(root.getChild(0))).getChildByName(name);
//								if (r == null) {
//									continue;
//								}
//								Tile transTile = (Tile) r.getSource();
//								addToRetval(transTile, retval, x, y);
//							}
//							}
//						}
//					}
//				}
//			}
//	
//	private int log2(int p) {
//		int i=0;
//		for(;p>0;i++,p=p>>1);
//		return i;
//	}
//
//	private static String format(String s) {
//		String mat = "0";
//		StringBuffer sb = new StringBuffer();
//		if (s.length() < 8) {
//			int p = 8 - s.length();
//			for (int i = 0; i < p; i++) {
//				sb.append(mat);
//			}
//		}
//		sb.append(s);
//		return sb.toString();
//	}

	

	private void addToRetval(Tile transTile, Layer retval, int x, int y) {
		CompositeTile ct = (CompositeTile) retval.getTileAt(x, y);
		if (ct == null) {
			ct = TileFactory.createCompositeTile(transTile);
		}else {
			addTransTile(ct,transTile);
		}
		retval.add(ct, x,y);
	}

	private void addTransTile(CompositeTile parent, Tile transTile) {
		CompositeTile child= (CompositeTile) parent.getTileAt(0, 0);
		if(child == null){
			child = TileFactory.createCompositeTile(transTile);
			parent.add(child, 0, 0);
		}else{
			addTransTile(child, transTile);
		}
			
	}

	protected int getProperty(Storable r,String property){
		try{
			return Integer.parseInt(r.getProperty(property));
		}catch (Exception e) {
			return -1;
		}
		
	}
}
