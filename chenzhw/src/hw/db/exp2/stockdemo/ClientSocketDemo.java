package hw.db.exp2.stockdemo;

import java.io.*; 
import java.net.*; 


/** 
 * Demonstrates how to write a Java client 
 * 
 * @see ServerSocketDemo 
 ***/ 
public class ClientSocketDemo 
{ 
    public static void main(String args[]) throws Exception 
    { 
        Socket s = new Socket("localhost", 6000); 
        PrintWriter pw = new PrintWriter(s.getOutputStream()); 
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream())); 
         
        System.out.println(">> Connection with server established"); 
        pw.println("Hello World!"); 
        pw.flush(); 
         
        // Print input 
        System.out.println(">> Reading output from server"); 
        System.out.print(br.readLine()); 
        //System.out.println(">> Done!"); 
         
        // close sockets 
        pw.close(); 
        br.close(); 
        s.close(); 
    } 
} 