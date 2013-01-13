/* MapResource.java 1.0 2010-2-2
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

import javax.swing.tree.MutableTreeNode;

import org.mepper.io.Storable;

/**
 * <B>MapResource</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-11 created
 * @since org.mepper.resource Ver 1.0
 * 
 */
public interface StorableResource extends MutableTreeNode,Storable{
	
	void setSource(Storable source);
	Storable getSource();
	
	@Override
	int getChildCount();
	
	StorableResource getChild(int index);
	StorableResource getChildByID(int id);
	StorableResource getChildByName(String name);
	
	void addChild(StorableResource r);
	void removeChild(int index);
	void removeChild(StorableResource r);
	
	boolean isLibrary();
	
	StorableResource getParentResource();
	void setParentResource(StorableResource r);
	
	
}
