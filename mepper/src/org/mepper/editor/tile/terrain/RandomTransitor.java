/* RandomTransitor.java 1.0 2010-2-2
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

import java.util.Random;

import org.mepper.editor.map.Layer;
import org.mepper.editor.tile.Tile;
import org.mepper.resources.DefaultResource;
import org.mepper.resources.LibraryResource;
import org.mepper.resources.StorableResource;

/**
 * <B>RandomTransitor</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-28 created
 * @since org.mepper.editor.tile.terrain Ver 1.0
 * 
 */
public class RandomTransitor  extends DavidTransitor{
	private final Random random = new Random();
	
	@Override
	protected void prepareResourceTree() { // create tree by property
//		ResourceManager manager = new DefaultResourcesManager();
//		manager.setCurrentNode(root);
		
//		StorableResource systemRoot = DefaultResourcesManager.getInstance().getRoot();
//		addResource(systemRoot);
		addResource(lib);
	}
	
	private void addResource(StorableResource resource) {
		for(int i=0,j=resource.getChildCount();i<j;i++){
			StorableResource child = resource.getChild(i);
			if(child.isLibrary()){
				addResource(child);
			}else{
				int priority = getProperty(child, "priority");
				if(priority <0){
					continue;
				}
				
				int edge = getProperty(child,"edge");
				if(edge !=-1){
					addEdge(priority,edge,child);
				}else {
					addTile(priority,child);
				}
			}
		}
	}
	

	private void addTile(int priority, StorableResource child) {
		StorableResource priorityNode = getPriorityNode(priority);
		StorableResource tilesNode =priorityNode.getChildByName("tiles");
		StorableResource newTile = new DefaultResource();
		newTile.setSource(child.getSource());
		tilesNode.addChild(newTile);
	}

	private void addEdge(int priority, int edge,StorableResource child) {
		StorableResource priorityNode = getPriorityNode(priority);
		StorableResource edgesNode =priorityNode.getChildByName("edges");
		StorableResource edgeResource = edgesNode.getChildByName(edge+"");
		if(edgeResource == null){
			edgeResource = new LibraryResource(edge+"");
			edgesNode.addChild(edgeResource);
		}
		StorableResource newEdge = new LibraryResource();
		newEdge.setSource(child.getSource());
		edgeResource.addChild(newEdge);
	}

	private StorableResource getPriorityNode(int priority) {
		StorableResource priorityNode = root.getChildByName(priority+"");
		if(priorityNode != null){
			return priorityNode;
		}
		
		priorityNode =  new LibraryResource(priority+"");
		priorityNode.addChild(new LibraryResource("tiles"));
		priorityNode.addChild(new LibraryResource("edges"));
		root.addChild(priorityNode);
		return priorityNode;
	}
	
	
	
	
	
	@Override
	protected void transition(int[][] priorities, Layer retval,
			int currentPriority, int row, int column, int layerWidth,
			int layerHeight) {
		super.transition(priorities, retval, currentPriority, row, column, layerWidth,
				layerHeight);
		if(priorities[row][column] == 0){
			return;
		}
		Tile tile = layer.getTileAt(row, column);
		Tile newTile = getTile(getProperty(tile, "priority"));
		layer.add(newTile, row, column);
	}

	private Tile getTile(int priority) {
		StorableResource priorityResource = root.getChildByName(priority+"");
		if(priorityResource==null){
			return null;
		}
		StorableResource tilesNode =priorityResource.getChildByName("tiles");
		int p ;
		if(tilesNode==null || (p=tilesNode.getChildCount())==0){
			return null;
		}
		
		Tile tile = null;
		do {
			p = random.nextInt(p);
			tile =(Tile)tilesNode.getChild(p).getSource();
			p = getProperty(tile, "edge");
		} while (p >0);
		
		
		return tile;
	}

	@Override
	protected StorableResource getEdge(int priority, int orientate) {
		StorableResource priorityResource = root.getChildByName(priority+"");
		if(priorityResource==null){
			return null;
		}
		StorableResource edgeNode =priorityResource.getChildByName("edges").getChildByName(orientate+"");
		int p ;
		if(edgeNode==null || (p=edgeNode.getChildCount())==0){
			return null;
		}
		
		p = random.nextInt(p);
		return edgeNode.getChild(p);
	}
}


