package org.zhiwu.xml;

/**
 * <B>DataSourceException</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-4-19 created
 * @since agenda Ver 1.0
 * 
 */
public class DataSourceException extends Exception{
	/**
	 * @see java.lang.Exception#Exception()
	 * @since agenda
	 */
	public DataSourceException(){
		super();
	}

	/**
	 * @see java.lang.Exception#Exception(String)
	 * @since agenda
	 * @param message
	 */
	public DataSourceException(String message){
		super(message);
	}

	/**
	 * The Constructor.
	 * @see java.lang.Exception#Exception(Throwable)
	 * @param nestedThrowable the nested throwable
	 * 
	 * @since agenda
	 */
	public DataSourceException(Throwable nestedThrowable){
		super(nestedThrowable);
	}

	/**
	 * The Constructor.
	 * @see java.lang.Exception#Exception(String, Throwable)
	 * @param message the message
	 * @param nestedThrowable the nested throwable
	 * 
	 * @since agenda
	 */
	public DataSourceException(String message,Throwable nestedThrowable){
		super(message,nestedThrowable);
	}
}
