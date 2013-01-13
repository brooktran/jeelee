package hw.db.exp2.data_gram_socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * This example illustrates the basic method calls for connectionless datagram
 * socket.
 */
public class Example1Sender {

	// An application which sends a message using connectionless
	// datagram socket.
	// Three command line arguments are expected, in order:
	// <domain name or IP address of the receiver>
	// <port number of the receiver's socket>
	// <message, a string, to send>

	public static void main(String[] args) {
		if (args.length != 3)
			System.out
					.println("This program requires three command line arguments");
		else {
			try {
				InetAddress receiverHost = InetAddress.getByName(args[0]);
				/*
				 * This class represents an Internet Protocol (IP) address.
				 * 
				 * An IP address is either a 32-bit or 128-bit unsigned number
				 * used by IP, a lower-level protocol on which protocols like
				 * UDP and TCP are built. The IP address architecture is defined
				 * by RFC 790: Assigned Numbers, RFC 1918: Address Allocation
				 * for Private Internets, RFC 2365: Administratively Scoped IP
				 * Multicast, and RFC 2373: IP Version 6 Addressing
				 * Architecture. An instance of an InetAddress consists of an IP
				 * address and possibly its corresponding host name (depending
				 * on whether it is constructed with a host name or whether it
				 * has already done reverse host name resolution).
				 */
				int receiverPort = Integer.parseInt(args[1]);
				String message = args[2];

				// instantiates a datagram socket for sending the data
				DatagramSocket mySocket = new DatagramSocket();
				byte[] buffer = message.getBytes();
				DatagramPacket datagram = new DatagramPacket(buffer,
						buffer.length, receiverHost, receiverPort);
				mySocket.send(datagram);
				mySocket.close();
			} // end try
			catch (Exception ex) {
				ex.printStackTrace();
			}
		} // end else
	} // end main
} // end class
