/* DateUtils.java 1.0 2010-2-3
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <B>DateUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-2-3 created
 * @since Application Ver 1.0
 * 
 */
public class DateUtils {


	/**
	 * DateUtils.format(date, "yy:MM:dd HH:mm:ss")
	 * @param date
	 * @param string
	 * @return
	 */
	public static String format(Date date, String pattern) {
		DateFormat formater=new SimpleDateFormat(pattern);
		return formater.format(date);
	}

	/**
	 * 
	 * @param parseLong
	 * @return
	 */
	public static Date parse(long timeMillis) {
		Date d=new Date(timeMillis);
		return d;
	}



}
