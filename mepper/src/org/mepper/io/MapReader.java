/* MapReader.java 1.0 2010-2-2
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
package org.mepper.io;

import java.io.FileFilter;
import java.io.IOException;
import java.io.InputStream;

import org.mepper.editor.map.Map;
import org.mepper.resources.StorableResource;

/**
 * <B>MapReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-3-5 created
 * @since org.map.editor.io Ver 1.0
 * 
 */
public interface MapReader extends Pluginable{
	
	Map readMap(String filename) throws IOException;
	Map readMap(InputStream in) throws IOException;
	StorableResource readResource(String filename) throws IOException;
	StorableResource readResource(InputStream in) throws IOException;
	
	FileFilter[] getFilters();
}