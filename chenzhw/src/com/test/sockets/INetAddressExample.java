package com.test.sockets;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * 类<B> INetAddressExample </B> The java.net.InetAddress abstraction represents
 * a network destination, encapsulating both names and numerical address
 * information. The class has two subclasses, Inet4Address and Inet6Address,
 * representing the two versions in use. Instances of InetAddress are immutable:
 * once created, each one always refers to the same address. We’ll demonstrate
 * the use of InetAddress with an example program that first prints out all the
 * addresses—IPv4 and IPv6, if any—associated with the local host, and then
 * prints the names and addresses associated with each host specified on the
 * command line.
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-9 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class INetAddressExample {
	public static void main(String[] args) {
		args=new String[3];
		args[0]="www.mkp.com";
		args[1]=" blah.blah";
		args[2]=" 129.35.69.7";
		/*www.mkp.com:
			www.mkp.com/63.125.146.200
		 blah.blah:
			Unable to find address for  blah.blah
		 129.35.69.7:
			Unable to find address for  129.35.69.7*/
		
		
		// Get the network interfaces and associated addresses for the host.
		try {
			Enumeration<NetworkInterface> interfaceList = NetworkInterface
					.getNetworkInterfaces();
			if (interfaceList == null) {
				System.out.println("__No interfaces found__");
			} else {
				while (interfaceList.hasMoreElements()) {
					NetworkInterface tmpInterface = interfaceList.nextElement();
					System.out.println("Interface "
							+ tmpInterface.getInterfaceAddresses());
					Enumeration<InetAddress> addrList = tmpInterface
							.getInetAddresses();
					if (!addrList.hasMoreElements()) {
						System.out
								.println("\t No addresses for this interface");
					}
					while (addrList.hasMoreElements()) {
						// represents a network destination,destination,
						// encapsulating both names and numerical address information.
						InetAddress address = addrList.nextElement();

						System.out
								.println("\t Address"
										+ ((address instanceof Inet4Address ? "(V4)"
												: (address instanceof Inet6Address ? "(V6)"
														: "(?)"))));
						System.out.println(": " + address.getHostAddress());
					}

				}
			}
		} catch (SocketException se) {
			// TODO: handle exception
			System.out.println("Error getting network interfaces:"
					+ se.getMessage());
		}
		for(String host:args){
			try {
		        System.out.println(host + ":");
		        InetAddress[] addressList = InetAddress.getAllByName(host);
		        for (InetAddress address : addressList) {
		          System.out.println("\t" + address.getHostName() + "/" + address.getHostAddress());
		        }
		      } catch (UnknownHostException e) {
		        System.out.println("\tUnable to find address for " + host);
		      }
		}
	}

}
