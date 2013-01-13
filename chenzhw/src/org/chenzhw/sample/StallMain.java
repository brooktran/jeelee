package org.chenzhw.sample;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.chenzhw.beans.StallBeans;

/**
 * <B> StallMain </B>
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-4-6 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class StallMain {


	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("输入:");
		BufferedReader bfReader=new BufferedReader(new InputStreamReader(System.in));
		StallBeans stallBeans;
		while (true) {
			stallBeans=new StallBeans(Integer.parseInt(bfReader.readLine()));
			System.out.println(stallBeans.getOneHour());
			System.out.println(stallBeans.getTwelveHour());
		}
	}
}
