/* ResourceBuilder.java 1.0 2010-2-2
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

/**
 * <B>ResourceBuilder</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-11 created
 * @since org.mepper.resource Ver 1.0
 * 
 */
public interface ResourceBuilder {
	void addResourceChild(StorableResource child);
	void addResourceToParent(StorableResource parent,StorableResource child);
	void addResourceToSlibling(StorableResource slibling);
	
	<T extends StorableResource> T getResource(Class<T> clazz,int id);
	StorableResource getResourceChild(int index);
	
	void removeResource(StorableResource r);
	void removeResource(StorableResource parent,StorableResource child);

	
	void setCurrentNode(StorableResource r);
	StorableResource getCurrentNode();
}
