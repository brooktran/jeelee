/* JReaderModel.java 1.0 2010-2-2
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
package org.jreader.app;

import org.zhiwu.app.AbsModel;

/**
 * <B>JReaderModel</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-6 created
 * @since org.jreader.app Ver 1.0
 * 
 */
public class JReaderModel extends AbsModel{
	private final JReader reader;
	
	public JReaderModel(JReader reader){
		this.reader=reader;
	}

	/**
	 * @return the reader
	 */
	public JReader getReader() {
		return reader;
	}
}
