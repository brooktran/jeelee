/* WordList.java 1.0 2010-2-2
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

/**
 * <B>WordList</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-26 created
 * @since mepper Ver 1.0
 * 
 */
public class WordList {
//	private Word[] words;
	private final List<Word> words = new ArrayList<Word>(16);
	private final int length =31;

	/**
	 * @param string  word,1;word2,3;
	 */
	public static WordList parse(String string) {
		try{
			WordList list = new WordList();
			StringTokenizer st = new StringTokenizer(string, ";");
			while(st.hasMoreTokens()){
				String[] s= st.nextToken().split(",");
				list.add(new Word(s[0],Integer.parseInt(s[1])));
			}
			return list;
		}catch (Exception e) {
			return null;
		}
		
	}

	private void add(Word word) {
		words.add(word);
		sort();
		if(words.size()>length){
			words.remove(words.size()-1);
		}
	}
	private void sort(){
		Collections.sort(words);
	}

	public Iterator<Word> iterator() {
		return words.iterator();
	}

	public void put(String word) {
		for(Word w:words){
			if (w.getWord().equals(word)) {
				w.increase();
				sort();
				return;
			}
		}
		add(new Word(word));
	}

}
