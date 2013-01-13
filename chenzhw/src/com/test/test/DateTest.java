package com.test.test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-10 新建
 * @since eclipse Ver 1.0
 * 
 */
public class DateTest {
	static void testDate(){
		try{
			//Date date=new Date();
			//System.out.println(FormateDate(date));
			SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//ParsePosition pos=new ParsePosition(0);
			//Scanner in=new Scanner(System.in);//含有空格 不能用Scanner
			
			String str=Console.readString();
			if(!str.matches("[0~9]{4}-[0,1][0~9]-[0~9]{2} [0~9]{2}:[0~9]{2}:[0~9]{2}"))
				System.out.println("输入错误 嘿嘿");
			Date date=dateFormat.parse(str);
			//System.out.println(date);
			Date date2=dateFormat.parse(str);
			System.out.println(date.after(date2));
			
			System.out.println(FormateDate(date));
			Calendar c=Calendar.getInstance();
			c.setTime(date);
			System.out.println(FormateDateString(c));
		}
		catch(Exception  e){
			System.err.println("日期输入错误");
		}
		
		
		/*Calendar c1 = Calendar.getInstance();
		System.out.println("创建方式：Calendar.getInstance()");
		System.out.println("时间日期：" + FormateDateString(c1));
		Date date=new Date();
		c1.setTime(date);
		System.out.println("时间日期：" + FormateDateString(c1));
		*/
	}
	
	public static String FormateDate(Date c) {
		if (c != null) {
			DateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			return dFormat.format(c);
		}
		return null;
	}
	
	
	public static String FormateDateString(Calendar c) {
		if (c != null) {
			DateFormat dFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
			return dFormat.format(c.getTime());
		}
		return null;
	}
	
	public static void main(String[] args) {
		while (true){
		testDate();
		
		//Calendar c=Calendar.getInstance();
		//System.out.println(FormateDateString(c));
		}
	}

}
