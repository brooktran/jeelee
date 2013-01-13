package org.jtool.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <B>DateUtil</B>.
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-4-20 created
 * @since agenda Ver 1.0
 */
public class DateUtil {

	/**
	 * Instantiates a new date util.
	 */
	private DateUtil() {
	}


	/**
	 * Check conflict.
	 * 
	 * @param start the start
	 * @param end the end
	 * @param otherStart the other start
	 * @param otherEnd the other end
	 * 
	 * @return true, if conflict
	 */
	public static boolean checkConflict(Date start, Date end, 
			Date newStart,Date newEnd) {
		if (!start.before(end)) {// throw ?
			//System.out.println(1);
			return true;
		}
		if (!newStart.before(newEnd)) {// throw ?
			//System.out.println(2);
			return true;
		}
		if ((start.after(newEnd))||end.before(newStart)) {
			//System.out.println(3);
			return false;
		}
		if (start.equals(newStart)||end.equals(newEnd)) {
			//System.out.println(4);
			return true;
		}
		if((end.after(newStart)&&end.before(newEnd))||
				(start.after(newStart)&&end.before(newEnd))||		//
				start.after(newStart)&&start.before(newEnd)||
				start.before(newStart)&&end.after(newEnd)){
			//System.out.println(5);
			return true;
		}
		return false;
	}

	/**
	 * Format date.
	 * 
	 * @param d
	 *            the d
	 * 
	 * @return the string
	 */
	public static String formatXMLDate(Date d) {
		if (null != d) {

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(d).replace(" ", "T");

			// String tmpString;
			// DateFormat formater=DateFormat.getDateTimeInstance();
			//			
			// tmpString=formater.format(d);
			// return tmpString.replace(" ", "T");

		}
		return null;
	}

	/**
	 * Format string.
	 * 
	 * @param s
	 *            the s
	 * 
	 * @return the date
	 */
	public static Date formatXMLString(String s) {
		Date date = new Date();
		try {
			s = s.replace("T", " ");
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(s);
			return date;
		} catch (ParseException pe) {
			System.err.println("Date format error:(" + formatDate(date) + ")");
		}
		return date;
	}

	public static String formatDate(Date d) {
		if (null != d) {
			//			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			return df.format(d);
		}
		return null;
	}

	public static Date formatString(String s) {
		Date date = new Date();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			date = df.parse(s);
			return date;
		} catch (ParseException pe) {
			System.err.println("Date format error:(" + formatDate(date) + ")");
		}
		return date;
	}

}
