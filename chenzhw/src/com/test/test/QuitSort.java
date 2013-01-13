/**
 * 
 */
package com.test.test;

import java.util.Random;


/**
 * 快速排序
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-2 新建
 * @since eclipse Ver 版本号
 */
public class QuitSort {
	private int[] number;

	public QuitSort() {
		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			number[i] = random.nextInt(100);
		}
		display();
	}

	public QuitSort(int[] number) {
		this.number = number;
	}

	public void display() {
		for (int i = 0, n = number.length; i < n; i++) {
			System.out.print(number[i] + " ");
		}
		System.out.println();

	}

	public int partition(int start, int end) {// 注意枢纽元素在一次partition过程中不能更变
		int tempI = number[start];
		while (start < end) {
			while (start < end && tempI >= number[end]) {//加上等号判断，否则容易出现死循环
				end--;
			}
			number[start] = number[end];
			// start++; 加上这句当最大值在start，循环完while start>end
			// 并返回start退出函数，由于返回的start比end大，导致无法继续排序。不能因节省资源而犯错

			while (start < end && number[start] >= tempI) {//加上等号判断，否则容易出现死循环
				start++;
			}
			number[end] = number[start];
			// end--;加上这句当最小值在start，循环完while start>end
			// 并退出函数，无法继续排序。不能因节省资源而犯错
		}
		number[start] = tempI;
		return start;
	}

	public void quickSort(int start, int end) {
		if (start < end) {// 递归结束条件。注意加上判断，否则死循环；
			int localI = partition(start, end);
			quickSort(localI + 1, end);
			quickSort(start, localI - 1);
		}
	}

	public static void main(String[] args) {
		int[] a = { 32, 10, 25, 64, 5, 78, 95, 25, 18 };// 相同元素 25
		int[] b = { 49, 38, 65, 97, 76, 13, 27 };
		QuitSort quitSort = new QuitSort(b);
		quitSort.display();
		System.out.println();
		/*
		 * System.out.println(quitSort.partition(0, 6)); quitSort.display();
		 */
		quitSort.quickSort(0, quitSort.number.length - 1);
		quitSort.display();
	}
}
