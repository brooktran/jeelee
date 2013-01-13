/* MapProject.java 1.0 2010-2-2
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
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppManager;
import org.zhiwu.utils.AppResources;


/**
 * <B>MapProject</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor.project Ver 1.0
 * 
 */
//FIXME remove this class, use LibraryResource instead(try use the "_type" property.
public class ProjectResource extends DefaultResource{ 
	public ProjectResource() {
		super(new StorablePropertySupporter());
	}
	
	public ProjectResource(String name) {
		this();
		setName(name);
	}


	public void initLibrary() {
		AppResources r=AppManager.getResources();
		LibraryResource mapSet=new LibraryResource(r.getString("map"));
		mapSet.setID(MepperConstant.MAP_SET_ID);
		addChild(mapSet);
		 
		
		LibraryResource libSet=new LibraryResource(r.getString("library"));
		libSet.setID(MepperConstant.LIBRARY_SET_ID);
		addChild(libSet);
	}

	public boolean existMap(String name) {
		StorableResource r=getChildByID(MepperConstant.MAP_SET_ID);
		for(int i=0,j=getChildCount();i<j;i++){
			r= getChild(i);
			if(r.getName().equals(name)){
				return true;
			}
		}
		return false;
	}
} 
