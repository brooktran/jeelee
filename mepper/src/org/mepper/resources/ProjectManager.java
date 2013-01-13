/* ProjectDirector.java 1.0 2010-2-2
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

import org.mepper.editor.map.Map;


/**
 * <B>ProjectDirector</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.editor.project Ver 1.0
 * 
 */
public interface ProjectManager extends ResourceManager{
	boolean existProject(String projectName);
	void addProject(ProjectResource project);
	ProjectResource getCurrentProject();
	boolean isSignificant(StorableResource r);
//	boolean existMap(String name);
	void addMap(Map map);
	void addLibrary(LibraryResource child);
}
