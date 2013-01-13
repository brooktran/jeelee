package com.test.test;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * <B>Combination</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2009-5-15 created
 * @since chenzhw Ver 1.0
 * 
 */
public class Combination {

	/** The result string array. */
	private ArrayList<String> resultList ;
	
	private static final String SEPARATOR=" ";

	/**
	 * Start.
	 * 
	 * @param array
	 *            the array
	 * @param n
	 *            the n
	 */
	public void start(String[] array, int n) {
		resultList = new ArrayList<String>();
		
		int arrayLength = array.length;
		if (arrayLength < n)
			throw new IllegalArgumentException(
					"Error: The num n must smaller the length of the array. ");
		BitSet bitSet = new BitSet(arrayLength);
		for (int i = 0; i < n; i++) {// Sets the first true segment.
			bitSet.set(i, true);
		}
		do {
			addCombination(array, bitSet);
		} while (hasMoreCombination(bitSet, arrayLength));

	}

	/**
	 * 1、start 第一个true片段的起始位，end截止位 
	 * <p>
	 * 2、把第一个true片段都置false
	 * <p>
	 * 3、数组从0下标起始到以第一个true片段元素数量减一为下标的位置都置true 
	 * <p>
	 * 4、把第一个true片段end截止位置true.
	 * 
	 * @param bs
	 *            数组是否显示的标志位
	 * @param m
	 *            数组长度
	 * 
	 * @return boolean 是否还有其他组合
	 */
	/**
	 * 1、First, finds the first true segment which start at 'start' and end with
	 * 'end'.
	 * <p>
	 * 2、Second, sets the fist true segment to false.
	 * <p>
	 * 3、Third, sets ture from subscripting zero to 'end-1'.
	 * <p>
	 * 4、Last, sets the 'end' to true.
	 * 
	 * @param bitSet
	 *            a bit set to set which subscripting of the array want to add
	 *            to the combination.
	 * @param arrayLength
	 *            the length of the array
	 * 
	 * @return boolean true, if there have more combination, else return false.
	 */
	private boolean hasMoreCombination(BitSet bitSet, int arrayLength) {
		int start = -1;
		while (start < arrayLength) {
			// Find the first true.
			if (bitSet.get(++start)) {
				break;
			}
		}
		if (start >= arrayLength) {
			return false;
		}

		int end = start;
		while (end < arrayLength) {
			// And then, find first false label.
			if (!bitSet.get(++end)) {
				break;
			}
		}
		if (end >= arrayLength) {
			return false;
		}

		for (int i = start; i < end; i++) {
			// Revert the first true segment to false.
			bitSet.set(i, false);
		}

		// sets the last true to false.
		for (int i = 0; i < end - start - 1; i++) {
			bitSet.set(i);
		}
		bitSet.set(end);// adds a new true bit ( just set the subscripting end
		// to true).
		return true;
	}
	private void addCombination(String[] array, BitSet bitSet) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < array.length; i++)
			if (bitSet.get(i)) {
				sb.append(array[i]).append(SEPARATOR);
			}
		sb.setLength(sb.length() - 1);
		resultList.add(sb.toString());

	}

	public ArrayList<String> getResultList() {
		return resultList;
	}

	/**
	 * The main method.
	 * 
	 * @param args
	 *            the arguments
	 * 
	 * @throws Exception
	 *             the exception
	 */
	public static void main(String[] args) throws Exception {
		Combination comb = new Combination();
		comb.start(new String[] { "1", "2", "3", "4", "5", "6" }, 6);
		for (int i = 0; i < comb.getResultList().size(); i++) {
			System.out.println(comb.getResultList().get(i));
		
		}
	}
}
