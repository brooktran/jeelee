package com.test.test;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-12 新建
 * @since agenda Ver 1.0
 * 
 */
public class IllegalCommand extends RuntimeException{
	private String message;
	public IllegalCommand(String message){
		this.message=message;
	}
	
	
	/* （非 Javadoc）
	 * @see agenda.exception.AgendaException#toStirng()
	 */
	public String toStirng() {
		return this.message;
	}
	

}
