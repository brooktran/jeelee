package com.test.test;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-12-21 新建
 * @since eclipse Ver 1.0
 * 
 */
public class TextFileReader {
	public static void main(String[] args) {
		System.out.println("input filename:");
		
		try{
			File file=new File("test.txt");
			BufferedInputStream bfInputStream=new BufferedInputStream(new FileInputStream(file));
			BufferedReader reader=new BufferedReader(new InputStreamReader(bfInputStream),5*1024*1024);
			String str="";
			while((str=reader.readLine())!=null){
				System.out.println(str);
			}
		}
		catch (FileNotFoundException e) {
			System.err.println("文件不存在");
		}
		catch (IOException e) {
			// 读取文件出错
		}
	}
}/*
static String calculate()   
{   
    StringBuffer sb=new StringBuffer("");   
    try{   
        FileReader reader = new FileReader("words.txt");   
        BufferedReader br = new BufferedReader(reader);   
        String s = null;   
        while((s = br.readLine()) != null) {   
            sb.append(s+'\n');   
        }   
        br.close();   
        reader.close();   
   }catch(Exception e){   
        e.printStackTrace();   
   }   
    return sb.toString();   
}   */