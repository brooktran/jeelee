/**
 * 
 */
package com.test.datetest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * 本类是 功能: 相关信息: 和其他类关系: 为什么选这个类: 使用方法: sample: 注意事项:
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-1 新建
 * @since eclipse Ver 版本号
 * 
 * @see Class,OtherClass
 * 
 */
public class Test {

	/**
	 * // 在默认时区下输出日期和时间值
	 * 
	 * @throws exceptions
	 *             no exceptions throw
	 * @since test Ver<1.0>
	 */
	public static void current() {
		Date d = new Date();
		System.out.println(d);

	}

	/**
	 * 作用:用before after compare 三种方法比较日期
	 * 
	 * @throws exceptions
	 *             no exceptions throw
	 */
	public static void testTime() {
		// 2008-08-08 20:00:00对应的毫秒数
		long time2008 = 12181968000000L;
		// 1900-01-01 20:00:00对应的毫秒数
		long time1900 = -220894592000L;

		// 指定毫秒数创建Date对象
		Date d2008 = new Date(time2008);
		// 使用系统默认时间创建Date对象
		Date d1900 = new Date();
		// 通过设置毫秒数改变日期和时间
		d1900.setTime(time1900);

		// 使用before()方法比较
		System.out.println("调用方法：d1900.before");
		System.out
				.print("比较结果：\"1900-01-01 20:00:00\"在\"2008-08-08 20:00:00\"");
		System.out.println(d1900.before(d2008) ? "之前" : "之后");

		// 使用after()方法比较
		System.out.println();
		System.out.println("调用方法：d2008.after(d1900)");
		System.out
				.print("比较结果：\"2008-08-08 20:00:00\"在\"1900-01-01 20:00:00\"");
		System.out.println(d1900.after(d2008) ? "之前" : "之后");
		System.out.println();

		// 使用compareTo()方法比较
		System.out.println("调用方法：d1900.compareTo(d2008)");
		System.out
				.print("比较结果：\"1900-01-01 20:00:00\"在\"2008-08-08 20:00:00\"");
		int i = d1900.compareTo(d2008);
		if (i == -1) {
			System.out.println("之前");
		} else if (i == 1) {
			System.out.println("之后");
		} else if (i == 0) {
			System.out.println("是同一时刻");
		}

	}

	/**
	 * 设置时间和日期格式，以一种友好的方式显示日期和时间 使用方法: 注意事项:
	 * 
	 * @param c
	 *            时间日期对象
	 * @throws exceptions
	 *             no exceptions throw
	 * @return 格式化后的时间日期字符串
	 */
	public static String FormateDateString(Calendar c) {
		if (c != null) {
			DateFormat dFormat = new SimpleDateFormat("yyyy年mm月dd日 HH:MM:ss");
			return dFormat.format(c.getTime());
		}
		return null;
	}

	/**
	 * <b>testFormateDateString</b>主要用于测试 FormateDateString()
	 * 在创建GregorianCalendar对象时，月份值都设定为8，但打印结果都是9月份。
	 * 这并不是我们的代码有问题，而是因为JAVA表示的月份是从0开始的， 也就是说它用来表示月份的数值总是比实际月份值小1。
	 * 因此我们要表示8月份，就是应该设置8-1=7这个值。 GregorianCalendar的小时数是24小时制的。
	 * 
	 * @throws exceptions
	 *             no exceptions throw
	 */
	public static void testFormateDateString() {
		Calendar c1 = Calendar.getInstance();
		System.out.println("创建方式：Calendar.getInstance()");
		System.out.println("时间日期：" + FormateDateString(c1));
		System.out.println();

		Calendar c2 = new GregorianCalendar();
		System.out.println("创建方式：new GregorianCalendar()");
		System.out.println("时间日期：" + FormateDateString(c2));
		System.out.println();

		// 参数含义依次为：年、月、日
		Calendar c3 = new GregorianCalendar(2008, 11, 1);
		System.out.println("创建方式：new GregorianCalendar(2008, 8, 8)");
		System.out.println("日期时间：" + FormateDateString(c3));
		System.out.println();

		// 参数含义依次为：年、月、日、时、分
		Calendar c4 = new GregorianCalendar(2008, 8, 8, 6, 10);
		System.out.println("创建方式：new GregorianCalendar(2008, 8, 8, 6, 10)");
		System.out.println("日期时间：" + FormateDateString(c4));
		System.out.println();

		// 参数含义依次为：年、月、日、时、分、秒
		Calendar c5 = new GregorianCalendar(2008, 8, 8, 18, 10, 5);
		System.out.println("创建方式：new GregorianCalendar(2008, 8, 8, 18, 10, 5)");
		System.out.println("日期时间：" + FormateDateString(c5));

	}

	/**
	 * 测试calendar.get方法
	 * @throws exceptions no exceptions throw
	 */
	public static void testCalendarGet(){
		Calendar c = Calendar.getInstance();   
        System.err.println("当前时刻：" + FormateDateString(c));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.AM_PM");   
        System.out.println("代表含义：上下午标识，上午返回Calendar.AM=0，下午返回Calendar.PM=1");   
        System.out.println("测试结果：" + c.get(Calendar.AM_PM));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.DATE");   
        System.out.println("代表含义：一个月中的第几天，同Calendar.DAY_OF_MONTH");   
        System.out.println("测试结果：" + c.get(Calendar.DATE));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.DAY_OF_MONTH");   
        System.out.println("代表含义：一个月中的第几天，同Calendar.DATE");   
        System.out.println("测试结果：" + c.get(Calendar.DAY_OF_MONTH));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.DAY_OF_WEEK");   
        System.out.println("代表含义：一周中的第几天，对应星期几，第一天为星期日，于此类推。");   
        System.out.println("星期日:Calendar.SUNDAY=1");   
        System.out.println("星期一:Calendar.MONDAY=2");   
        System.out.println("星期二:Calendar.TUESDAY=3");   
        System.out.println("星期三:Calendar.WEDNESDAY=4");   
        System.out.println("星期四:Calendar.THURSDAY=5");   
        System.out.println("星期五:Calendar.FRIDAY=6");   
        System.out.println("星期六:Calendar.SATURDAY=7");   
        System.out.println("测试结果：" + c.get(Calendar.DAY_OF_WEEK));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.DAY_OF_WEEK_IN_MONTH");   
        System.out.println("代表含义：这一天所对应的星期几在该月中是第几次出现");   
        System.out.println("测试结果：" + c.get(Calendar.DAY_OF_WEEK_IN_MONTH));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.DAY_OF_YEAR");   
        System.out.println("代表含义：一年中的第几天");   
        System.out.println("测试结果：" + c.get(Calendar.DAY_OF_YEAR));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.HOUR");   
        System.out.println("代表含义：12小时制下的小时数，中午和午夜表示为0");   
        System.out.println("测试结果：" + c.get(Calendar.HOUR));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.HOUR_OF_DAY");   
        System.out.println("代表含义：24小时制下的小时数，午夜表示为0");   
        System.out.println("测试结果：" + c.get(Calendar.HOUR_OF_DAY));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.MILLISECOND");   
        System.out.println("代表含义：毫秒数");   
        System.out.println("测试结果：" + c.get(Calendar.MILLISECOND));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.MINUTE");   
        System.out.println("代表含义：分钟");   
        System.out.println("测试结果：" + c.get(Calendar.MINUTE));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.MONTH");   
        System.err.println("代表含义：月份，从0到11表示12个月份，比实际月份值小1");   
        System.out.println("测试结果：" + c.get(Calendar.MONTH));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.SECOND");   
        System.out.println("代表含义：秒");   
        System.out.println("测试结果：" + c.get(Calendar.SECOND));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.WEEK_OF_MONTH");   
        System.out.println("代表含义：一个月中的第几个星期");   
        System.out.println("测试结果：" + c.get(Calendar.WEEK_OF_MONTH));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.WEEK_OF_YEAR");   
        System.out.println("代表含义：一年中的第几个星期");   
        System.out.println("测试结果：" + c.get(Calendar.WEEK_OF_YEAR));   
        System.out.println();   
  
        System.out.println("属性名称：Calendar.YEAR");   
        System.out.println("代表含义：年份");   
        System.out.println("测试结果：" + c.get(Calendar.YEAR));   
	}
	public static void main(String[] args) {
		// System.out.println("1-0.33="+(1-0.33)); //不要使用float double 做精确计算
		Calendar can=Calendar.getInstance();
		System.out.println(can.get(Calendar.MONTH));
		
		
		Scanner in = new Scanner(System.in);
		do {
			System.out.println();
			System.out
					.println("1.当前时间测试\n2.before after compare 三种方法\n3.格式 易错\n4.get方法\n0.退出");

			byte c = in.nextByte();
			switch (c) {
			case 1:
				current();
				break;
			case 2:
				testTime();
				break;
			case 3:
				testFormateDateString();
				break;
			case 4:
				testCalendarGet();
				break;
			case 0:
				System.out.println("已经退出");
				System.exit(-1);
				break;
			default:
				System.out.println("输入错误，请重新输入！");
				break;
			}

		} while (true);

	}

}
