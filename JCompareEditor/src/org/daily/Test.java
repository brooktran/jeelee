package org.daily;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Test {
	
	public static void main(String[] args) throws IOException {
		InputStream is=new FileInputStream(new File("binnaryFile"));
		byte[] bs="我是一个测试jjjjjjjjjj. ".getBytes();
		byte[] bss=new byte[bs.length+1];
		for(int i=0,n=bss.length-1;i<n;i++){
			bss[i+1]= bs[i];
		}
		
		
		
		
		OutputStream os= new FileOutputStream(new File("output"));
		os.write(bss);
		
	}

}
