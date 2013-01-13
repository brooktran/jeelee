package com.test.bop;

/**
 * @author Administrator
 * 
 */
public class BinaryTest {

	/**
	 * UTF编码的二进制输出
	 * 
	 * @param str
	 */
	public static void print_UTF_binary(String str) {
		try {
			byte[] c = str.getBytes("UTF-8");
			System.out.println("UTF-8:");
			for (int i = 0; i < c.length; ++i) {
				String bin = Integer.toBinaryString(c[i]);
				bin = bin.substring(str.length() - 8);
				System.out.println(" " + bin);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void example_of_thinking_in_java() {
		int i1 = 2;// 小写
		System.out.println("i1 0x2f:" + Integer.toBinaryString(i1));
		int i2 = 0X2F;// 大写
		System.out.println("i2 0X2F:" + Integer.toBinaryString(i2));
		int i3 = 0177;// 0
		System.out.println("i3 0177:" + Integer.toBinaryString(i3));
		// /////////////////////
		char c = 0xff;
		System.out.println("c 0xff:" + Integer.toBinaryString(c));
		byte b = 0x7f;
		System.out.println("b 0x7f:" + Integer.toBinaryString(b));
		short s = 0x7fff;
		System.out.println("short 0x7fff:" + Integer.toBinaryString(s));
		// /////////////////////
		long l1 = 200L;
		System.out.println("l1 200L:" + Long.toBinaryString(l1));
		long l2 = 200l;
		System.out.println("l2 200l:" + Long.toBinaryString(l2));
		long l3 = 200;
		System.out.println("l3 200:" + Long.toBinaryString(l3));
		// /////////////////////
		float f1 = 1;
		// System.out.println("f1:"+Float.toBinaryString(f1));
		float f2 = 1F;
		// System.out.println("f2:"+Float.toBinaryString(f2));
		float f3 = 1f;
		// System.out.println("f3:"+Float.toBinaryString(f3));
		// /////////////////////
		double d1 = 1d;
		// System.out.println("d1:"+Double.toBinaryString(d1));
		double d2 = 2D;
		// System.out.println("d2:"+Integer.toBinaryString(d2));
	}

	public static void main(String[] args) {
	}
}
