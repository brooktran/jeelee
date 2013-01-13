/* ResourceLibrary.java 1.0 2010-2-2
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

import org.mepper.io.StorablePropertySupporter;

/**
 * <B>ResourceLibrary</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-11 created
 * @since org.mepper.resource Ver 1.0
 * 
 */
public class LibraryResource extends DefaultResource {
	
//	public class Library extends StorablePropertySupporter{
//	}
	 
	public LibraryResource() {
		super(new StorablePropertySupporter());
	}
	
	
	public LibraryResource(String name) {
		this();
		setName(name);
	}


	@Override
	public boolean isLibrary() {
		return true;
	}


	public StorableResource getChildByName(String name) {
		for(int i=0,j=getChildCount();i<j;i++){
			 StorableResource s= getChild(i);
			if(s.getName().equals(name)){
				return s;
			}
			if(s.isLibrary()){
				s = ((LibraryResource) s).getChildByName(name);
				if(s!= null){
					return s;
				}
			}
		}
		return null;
	}
	
	
}
