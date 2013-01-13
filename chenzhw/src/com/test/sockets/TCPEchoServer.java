package com.test.sockets;

import java.net.*;  // for Socket, ServerSocket, and InetAddress
import java.io.*;   // for IOException and Input/OutputStream

public class TCPEchoServer {

  private static final int BUFSIZE = 32;   // Size of receive buffer

  public static void main(String[] args) throws IOException {

    if (args.length != 1){  // Test for correct # of args
    	args=new String[1];//~
    	args[0]="7";//~
    }
     // throw new IllegalArgumentException("Parameter(s): <Port>");

    int servPort = Integer.parseInt(args[0]);

    // 1. Create a server socket to accept client connection requests
    ServerSocket servSock = new ServerSocket(servPort);

    int recvMsgSize;   // Size of received message
    byte[] receiveBuf = new byte[BUFSIZE];  // Receive buffer

    while (true) { // Run forever, accepting and servicing connections
      // 2.
    	Socket clntSock = servSock.accept();     // Get client connection

      SocketAddress clientAddress = clntSock.getRemoteSocketAddress();
      System.out.println("Handling client at " + clientAddress+" "+clntSock.getPort());
      
      InputStream in = clntSock.getInputStream();//// 区别于 客户端 client
      OutputStream out = clntSock.getOutputStream();///

      // Receive until client closes connection, indicated by -1 return
      while ((recvMsgSize = in.read(receiveBuf)) != -1) {
        out.write(receiveBuf, 0, recvMsgSize);
        
        for (int i = 0,j= receiveBuf.length;i<j; i++) {//~
			System.out.print((char)receiveBuf[i]);
		}
      }
      

      clntSock.close();  // Close the socket.  We are done with this client!
    }
    /* NOT REACHED */
  }
}
