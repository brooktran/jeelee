package com.test.test;

import java.io.*;

/**
 * 类<B>Console</B>主要用来读取并检验键盘对原始数据(<TT>double</TT>,<TT>float</TT>，
 * <TT>int</TT>, 和 <TT>String</TT> )的输入. <I>使用该类时，须将该类放置在根目录下.</I>
 * <P>
 * 
 * @version v1.2
 * @author Brook Tran.
 * <P>
 * <B>简单示例</B>
 * <PRE>
 * System.out.println("请输入双精度(double)数: "); double x = Console.readDouble();
 * System.out.println("你输入的是：: " + x);
 * </PRE>
 */

public class Console {
	
	/**
	 * 从键盘读取一个字符串并将其返回，以回车为结束标志.
	 * 
	 * @return String
	 */
	public static String readString() {
		String str = new String();
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		try {
			str = in.readLine();

		} catch (IOException e) {
			System.err.println("输入错误!");
			System.exit(-1);
		}
		return str;
	}

	/**
	 * 从键盘读取一个双精度数并将其返回，若输入的不是双精度数则结束程序.
	 * 
	 * @return double
	 */
	public static double readDouble() {
		try {
			return Double.valueOf(readString().trim()).doubleValue();
		} catch (NumberFormatException ne) {
			System.err.println("输入错误!只能输入数字。");
			System.exit(-1);
			return 0.0;
		}
	}
	/**
	 * 从键盘读取一个单精度数并将其返回，若输入的不是单精度数则结束程序. 
	 * 
	 * @return float
	 */
	public static float readFloat() {
		try {
			return Float.parseFloat(readString().trim());
		} catch (NumberFormatException e) {
			System.err.println("输入错误!只能输入数字.");
			return 0.0f;
		}
	}

	/**
	 * 从键盘读取一个整数并将其返回，若输入的不是双精度数则结束程序. 
	 * 
	 * @return int
	 */
	public static int readInt() {
		try {
			return Integer.valueOf(readString().trim()).intValue();
		} catch (NumberFormatException e) {
			System.err.println("输入错误!只能输入整数。");
			System.exit(-1);
			return -1;
		}
	}

}
