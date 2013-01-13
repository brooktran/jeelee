/* FileUtilsTest.java 1.0 2010-2-2
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
package org.zhiwu.utils;

import java.io.File;
import java.util.Random;

import junit.framework.TestCase;


/**
 * <B>FileUtilsTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-3-4 created
 * @since org.zhiwu.utils Ver 1.0
 * 
 */
public class FileUtilsTest extends TestCase{
	
	public void testCreate() throws Exception {
		Random r=new Random();
		String filename = "test/data/"+r.nextInt(500)+"/"+r.nextInt(500);
		File f=new File(filename);
		assertFalse(f.exists());
		FileUtils.create(f);
		assertTrue(f.exists());
		f.delete();
	}
	

}
