/* DataException.java 1.0 2010-2-2
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
package org.jtools.app.persistent;

/**
 * <B>DataException</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-27 created
 * @since org.divine.persistance Ver 1.0
 * 
 */
public class DataException extends Exception{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7433438461656751338L;

	/**
	 * @see java.lang.Exception#Exception()
	 * @since agenda
	 */
	public DataException(){
		super();
	}

	/**
	 * @see java.lang.Exception#Exception(String)
	 * @since agenda
	 * @param message
	 */
	public DataException(String message){
		super(message);
	}

	/**
	 * The Constructor.
	 * @see java.lang.Exception#Exception(Throwable)
	 * @param nestedThrowable the nested throwable
	 * 
	 * @since agenda
	 */
	public DataException(Throwable nestedThrowable){
		super(nestedThrowable);
	}

	/**
	 * The Constructor.
	 * 
	 * @param message the message
	 * @param nestedThrowable the nested throwable
	 * 
	 * @see java.lang.Exception#Exception(String, Throwable)
	 * @since agenda
	 */
	public DataException(String message,Throwable nestedThrowable){
		super(message,nestedThrowable);
	}
}
