package com.test.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 类<B> ObjectIOTest </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-14 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class ObjectIOTest {
	public static void main(String[] args) throws IOException, ClassNotFoundException {
		T t=new T();
		ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("TestObjectIO"));
		oos.writeObject(t);
		oos.flush();
		oos.close();
		
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("TestObjectIO"));
		T tReader=(T)ois.readObject();
		System.out.println(tReader.toString());
		
		ois.close();
		
		
		InputStreamReader isr=new InputStreamReader(new FileInputStream("TestObjectIO"));
		int i;
		while ((i=isr.read())!=-1) {
			System.out.print((char)i);
		}
		
		/*FileReader fr=new FileReader("TestObjectIO");
		BufferedReader br=new BufferedReader(fr);
		String string;
		do{
			string=br.readLine();
			System.out.print(string);
		}while(string!=null);
		
		
		FileInputStream fis=new FileInputStream("TestObjectIO");
		int i;
		while ((i=fis.read())!=-1) {
			System.out.print((char)i);
		}
		fis.close();*/
		
		
	}
}
class T implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4868490744930343003L;
	int aaaa=124;
	double ddddd=0.12345655555;
	String stringaaaa="sadf";
	boolean bbbbb=true;
	public String toString(){
		return aaaa +" "+ddddd+" "+stringaaaa+" "+bbbbb;
	}
	/*
	 * ---序列后的信息---GBK-----
	 * 
	 *      src com.test.test.TC    [  I aaaaZ bbbbD ddddL
	 *      stringaaaat Ljava/lang/String:xp   |?     t   sadf
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */
}