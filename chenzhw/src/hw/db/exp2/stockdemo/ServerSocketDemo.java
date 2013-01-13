package hw.db.exp2.stockdemo;

import java.io.*; 
import java.net.*; 


/** 
 * Demonstrates how to write a Java Server 
 * 
 * @see ClientSocketDemo 
 ***/ 
public class ServerSocketDemo 
{ 
    public static void main(String args[]) throws Exception 
    { 
        // setup server on port 6000 
        ServerSocket ss = new ServerSocket(6000); 
        System.out.println(">> Waiting for client connection..."); 
         
        // This can be placed in a while loop to serve multiple clients 
        Socket s = ss.accept(); 
         
        // Print input 
        System.out.println(">> Connection received, reading data..."); 
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream())); 
        PrintWriter pw = new PrintWriter(s.getOutputStream()); 
         
        System.out.println(br.readLine()); 
         
        System.out.println(">> Sending message back"); 
        pw.println("Good Bye!"); 
        pw.flush(); 
         
        // close sockets 
        pw.close(); 
        br.close(); 
        ss.close(); 
         
        System.out.println(">> Done!"); 
    } 
} 