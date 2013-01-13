package com.test.headfirst.starbuzz;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 装饰者模式练习
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class LowerCaseInputStream extends FilterInputStream {
	//首先扩展filterInputStream，它是所有InputStream的抽象装饰者
	
	public LowerCaseInputStream(InputStream in){
		super(in);
	}
	
	
	//必须实现两个read（）方法，一个针对字节，一个针对字节数组，把每个大写字符的字节转换成小写
	@Override
	public int read() throws IOException{
		int c=super.read();
		return (c==-1?c:Character.toLowerCase((char)c));
	}
	
	@Override
	public int read(byte[] b,int offset,int len)throws IOException{
		int result=super.read();
		for(int i=offset;i<offset+result;i++){
			b[i]=(byte)Character.toLowerCase((char)b[i]);
		}
		return result;
	}
	
	public static void main(String[] args) {
		int c;
		try{
			//设置FileInputstream，先用BufferedInputStream装饰它，再用LowerCaseInputStream装饰它
			InputStream in=new LowerCaseInputStream(new BufferedInputStream(new FileInputStream("test.txt")));
			while((c=in.read())>=0){
				System.out.print((char)c);
			}
			in.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

}
