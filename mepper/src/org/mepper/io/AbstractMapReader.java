/* AbstractMapReader.java 1.0 2010-2-2
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.mepper.editor.map.Map;
import org.mepper.resources.StorableResource;

/**
 * <B>AbstractMapReader</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-22 created
 * @since mepper Ver 1.0
 * 
 */
public abstract class AbstractMapReader implements MapReader{
	@Override
	public Map readMap(String filename) throws IOException {
		return readMap(new FileInputStream(new File(filename)));
	}
	
	@Override
	public StorableResource readResource(String filename) throws IOException {
		return readResource(new FileInputStream(new File(filename)));
	}
}
