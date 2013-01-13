/* AbstractTerrainTransitor.java 1.0 2010-2-2
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

import org.mepper.editor.map.Layer;
import org.mepper.resources.DefaultResource;
import org.mepper.resources.LibraryResource;
import org.mepper.resources.StorableResource;

/**
 * <B>AbstractTerrainTransitor</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-29 created
 * @since org.mepper.editor.tile.terrain Ver 1.0
 * 
 */
public abstract class AbstractTerrainTransitor implements TerrainTransitor {
//	protected ResourceManager manager;
	protected Layer layer;
	protected StorableResource root = new DefaultResource();
	protected StorableResource lib =new LibraryResource("root");
//	@Override
//	public void setManager(ResourceManager manager) {
//		this.manager = manager;
//	}
	
	@Override
	public void setLayer(Layer l) {
		this.layer =l;
	}
	
	@Override
	public void addLibrary(LibraryResource lib) {
		addLibrary(this.lib, lib);
//		StorableResource child = new LibraryResource();
//		child.setSource(lib.getSource());
//		root.addChild(child);
	}
	
	private void addLibrary(StorableResource parent,StorableResource child){
		StorableResource newChild = new LibraryResource();
		newChild.setSource(child.getSource());
		parent.addChild(newChild);
		
		for(int i=0,j=child.getChildCount();i<j;i++){
			StorableResource resource = child.getChild(i);
			if(resource.isLibrary()){
				addLibrary(newChild,resource);
			}else {
				StorableResource s = new DefaultResource(resource.getSource());
				newChild.addChild(s);
			}
		}
	}

}
