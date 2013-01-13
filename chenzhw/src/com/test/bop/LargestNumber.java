/**
 * 
 */
package com.test.bop;

import java.lang.annotation.Documented;
import java.util.Random;

import com.sun.org.apache.bcel.internal.generic.SWAP;

/**
 * 寻找最大K个数
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-1 新建
 * @since eclipse Ver 版本号
 * 
 */
public class LargestNumber {
	private int[] numberArray;

	private int K;

	public LargestNumber(int k) {
		Random random = new Random();
		for (int i = 0; i < 1000; i++) {
			numberArray[i] = random.nextInt(99999);
		}
		this.K = k;
	}

	public LargestNumber(int[] numberArray, int k) {
		this.numberArray = numberArray;
		if (k > 0) {
			this.K = k;
		} else {
			this.K = 0;
		}
	}

	/**
	 * 借用快速排序思想，寻找前K大个数
	 * 
	 * 将数组降序排列 注意事项:为了节省资源，数组只是部分有序，即k之前的数都比k大，k之后的数都比k小；但前后两部分不一定有序
	 * 
	 * @return k 数组下标
	 * @throws 异常类型
	 *             出现情况
	 * @throws exceptions
	 *             no exceptions throw
	 */
	public void findLargestK(int startI, int endI) {// 快速排序变形
		if (startI < endI && startI < K) {
			int localtionI = partition(startI, endI);
			if (localtionI == K)
				startI = numberArray.length;
			else if (localtionI > K) {
				findLargestK(startI, localtionI - 1);
			} else {
				findLargestK(localtionI + 1, endI);
			}
		}

	}

	public int partition(int startI, int endI) {// 一次比较
		int tempI = numberArray[startI];
		while (startI < endI) {
			while (startI < endI && numberArray[endI] <= tempI) {
				endI--;
			}
			numberArray[startI] = numberArray[endI];

			while (startI < endI && numberArray[startI] >= tempI) {
				startI++;
			}
			numberArray[endI] = numberArray[startI];
		}
		numberArray[startI] = tempI;
		printArray();
		return startI;
	}

	public static void main(String[] args) {
		int[] a = { 32, 10, 25, 64, 5, 78, 95, 25, 18, 25, 25,96,14,85,26,75,19 };
		System.out.println();

		LargestNumber ln = new LargestNumber(a, 5);
		ln.printArray();

		// ln.partition(0, 8);

		ln.findLargestK(0, ln.numberArray.length - 1);
		ln.printArray();
	}

	public void printArray() {
		for (int i = 0, n = numberArray.length; i < n; i++) {
			System.out.print(numberArray[i] + " ");
		}
		System.out.println();
	}

	/**
	 * 
	 * 作用: 使用方法: 注意事项:
	 * 
	 * @param start
	 * @param end
	 * @Documented
	 * @throws exceptions
	 *             no exceptions throw
	 */
	public void swap(int start, int end) {
		int temp = numberArray[end];
		numberArray[end] = numberArray[start];
		numberArray[start] = temp;
	}

}
