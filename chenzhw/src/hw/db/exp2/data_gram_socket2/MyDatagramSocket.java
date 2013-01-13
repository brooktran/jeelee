package hw.db.exp2.data_gram_socket2;

import java.net.*;
import java.io.*;

/**
 * A subclass of DatagramSocket which contains 
 * methods for sending and receiving messages
 */
public class MyDatagramSocket extends DatagramSocket {
   static final int MAX_LEN = 100;  
   MyDatagramSocket(int portNo)  throws SocketException{
     super(portNo);
   }
   public void sendMessage(InetAddress receiverHost, int receiverPort,                  
                           String message) throws IOException {
   		          	
         byte[ ] sendBuffer = message.getBytes( );                                     
         DatagramPacket datagram = 
            new DatagramPacket(sendBuffer, sendBuffer.length, 
                               receiverHost, receiverPort);
         this.send(datagram);
   } // end sendMessage

   public String receiveMessage()
		  throws IOException {		
      byte[ ] receiveBuffer = new byte[MAX_LEN];
      DatagramPacket datagram =
        new DatagramPacket(receiveBuffer, MAX_LEN);
      this.receive(datagram);
      String message = new String(receiveBuffer);
      return message;
   } //end receiveMessage
} //end class
