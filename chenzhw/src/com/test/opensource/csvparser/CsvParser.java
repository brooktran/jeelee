package com.test.opensource.csvparser;

import java.util.StringTokenizer;



/**
 * 类<B> CsvParser </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-1-31 新建
 * @since eclipse Ver 1.0
 * 
 */
public class CsvParser {
	private String[] currentLine;//
	
	public void parse(String inputLine){
		StringTokenizer tokenizer=new StringTokenizer(inputLine);
		currentLine=new String[tokenizer.countTokens()];
		for (int i = 0; i < currentLine.length; i++) {
			currentLine[i]  = tokenizer.nextToken();			
		}
	}
	
	public String get (int columnIndex){
		return currentLine[columnIndex];
	}

}
