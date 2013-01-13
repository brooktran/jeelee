/* StringUtils.java 1.0 2010-2-2
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
package org.jreader.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * <B>StringUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-7-25 created
 * @since org.jreader.utils Ver 1.0
 * 
 */
public class StringUtils {

	/**
	 * 
	 * 
	 * @param linkText
	 * 
	 * @return
	 */
	public static String removeHtmlSymbol(String content) {
		content = content.trim().replaceAll("&nbsp;", " ");
		content = content.trim().replaceAll("&amp;", "");
//		 content=content.replaceAll("<p>", "\n");
		// content=content.replaceAll("</TD>", "");
		// content=content.replaceAll("</div>", "");
		// content=content.replaceAll("</a>", "");
		// content=content.replaceAll("<a href=.*>", "");
		return content;
	}
	
	
	public static String htmlToString(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
System.out.println(htmlStr);
        return htmlStr; //返回文本字符串 
        
    } 

}
