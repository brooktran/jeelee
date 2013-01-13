/* Word.java 1.0 2010-2-2
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
package org.mepper.utils;

/**
 * <B>Word</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-26 created
 * @since mepper Ver 1.0
 * 
 */
public class Word  implements Comparable<Word>{
	
	private int counter;
	private String word;
	
	public Word(String word, int counter) {
		this.word = word;
		this.counter =counter;
	}

	public Word(String word) {
		this(word, 0);
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	@Override
	public int compareTo(Word other) {
		return this.counter==other.counter?0:
			this.counter>other.counter?1:-1;
	}
	
	@Override
	public String toString() {
		return word+","+counter;
	}

	public void increase() {
		counter++;
	}
	
	
}
