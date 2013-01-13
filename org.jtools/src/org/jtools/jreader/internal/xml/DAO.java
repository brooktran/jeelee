/* DAO.java 1.0 2010-2-2
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
package org.jtools.jreader.internal.xml;

import java.util.List;


/**
 * <B>DAO</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-1 created
 * @since org.jreader.persistent Ver 1.0
 * 
 */
public interface DAO <T>{

	/**
	 * @return
	 */
	List<T> getAll() throws DataSourceException;


	
	

}
