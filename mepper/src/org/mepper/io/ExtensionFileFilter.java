/* ExtendFileFilter.java 1.0 2010-2-2
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
import java.io.FileFilter;

/**
 * <B>ExtendFileFilter</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-3-11 created
 * @since org.map.editor.io Ver 1.0
 * 
 */
public class ExtensionFileFilter implements FileFilter{
	private final String extension;
	public ExtensionFileFilter(String extension) {
		this.extension=extension;
	}

	@Override
	public boolean accept(File file) {
		if (file.isDirectory()) {
			return false;
		}
		
		String filename=file.getAbsolutePath();
		int p=filename.lastIndexOf(".");
		String fileExtension = filename.substring(p, filename.length());
		
		return fileExtension.equals(extension);
	}
}
