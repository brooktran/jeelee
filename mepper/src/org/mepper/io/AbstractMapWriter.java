/* AbstractMapWriter.java 1.0 2010-2-2
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
import java.io.FileOutputStream;
import java.io.IOException;

import org.mepper.editor.map.Map;
import org.mepper.resources.StorableResource;

/**
 * <B>AbstractMapWriter</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-23 created
 * @since org.mepper.io Ver 1.0
 * 
 */
public abstract class AbstractMapWriter implements MapWriter{
	
	@Override
	public void writeMap(Map map, String path) throws IOException {
		writeMap(map, new FileOutputStream(path));
	}

	@Override
	public void writeResource(StorableResource resource, String path) throws java.io.IOException {
		File f=new File(getFileName(resource,path));
		FileOutputStream fos = new FileOutputStream(f);
		writeResource(resource,fos) ;
		fos.close();
		
	}
	
	private String getFileName(StorableResource resource, String path) {
		StringBuilder sb=new StringBuilder(path);
		if(path.endsWith(File.separator)){
			sb.append(File.separator);
		}
		sb.append(resource.getName());
		sb.append(".");
		sb.append(System.currentTimeMillis()+"");
		sb.append(".");
		sb.append(resource.getID());
		sb.append(".");
		sb.append(getFileSuffix());
		return sb.toString();
	}

	protected abstract String getFileSuffix() ;

}
