/* AbstractMapResource.java 1.0 2010-2-2
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

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

import org.mepper.io.Storable;

/**
 * <B>AbstractMapResource</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-23 created
 * @since org.mepper.resource Ver 1.0
 * 
 */
public class DefaultResource extends DefaultMutableTreeNode implements StorableResource{
	private Storable source;
	
	public DefaultResource() {
	}
	
	public DefaultResource(Storable source) {
		this.source =source;
	}

	@Override
	public StorableResource getChild(int index) {
		return (StorableResource) super.getChildAt(index);
	}
	
	@Override
	public StorableResource getChildByID(int id){
		for(int i=0,j=getChildCount();i<j;i++){
			StorableResource r= getChild(i);
			if(r.getID() == id){
				return r;
			}
		}
		return null;
	}
	
	@Override
	public StorableResource getChildByName(String name) {
		for(int i=0,j=getChildCount();i<j;i++){
			StorableResource child= getChild(i);
			if(child.getName().equals(name)){
				return child;
			}
		}
		return null;
	}

	@Override
	public void addChild(StorableResource r) {
		add(r);
	}

	@Override
	public void removeChild(int index) {
		remove(index);
	}

	@Override
	public void removeChild(StorableResource r) {
		remove(r);
	}

	@Override
	public boolean isLibrary() {
		return false;
	}

	@Override
	public StorableResource getParentResource() {
		return (StorableResource) getParent();
	}

	@Override
	public void setParentResource(StorableResource r) {
		setParent(r);
	}



	@Override
	public void setSource(Storable source) {
		this.source =source;
	}


	@Override
	public Storable getSource() {
		return source;
	}
	
//	@Override
//	public boolean equals(Object obj) {
//		return source.equals(obj);
//	}

	@Override
	public int getID() {
		return source.getID();
	}

	@Override
	public void setID(int id) {
		source.setID(id);
	}

	@Override
	public String getName() {
		return source.getName();
	}

	@Override
	public void setName(String name) {
		source.setName(name);		
	}

	@Override
	public String getDescription() {
		return source.getDescription();
	}

	@Override
	public void setDescription(String desc) {
		source.	setDescription(desc);	
	}

	@Override
	public String getProperty(String key) {
		return source.getProperty(key);
	}

	@Override
	public void putProperty(String key, String value) {
		source.	putProperty(key,value);	
	}

	@Override
	public void removeProperty(String key) {
		source.removeProperty(key);
	}

	@Override
	public List<String> propertyNames() {
		return source.propertyNames();
	}
	
	@Override
	public String toString() {
		return getName();
	}

}
