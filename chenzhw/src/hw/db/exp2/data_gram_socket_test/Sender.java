package hw.db.exp2.data_gram_socket_test;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 类<B> DataGramPacket_Test </B>是一个DataGramPacker的测试类:
 * 
 * 1.基本概念： a.DatagramPacket与DatagramSocket位于java.net包中
 * b.DatagramPacket表示存放数据的数据报，DatagramSocket表示接受或发送数据报的套接字
 * c.由这两个类所有构成的网络链接是基于UDP协议，是一种不可靠的协议。
 * 之所以不可靠是因为发送方不负责数据是否发送成功，接收方收到数据时也不会向发送方反馈成功消息，容易导致信息的丢失。
 * 但是这种协议却是快捷的，比如CS(半条命)游戏就是基于UDP协议，否则我们估计要玩一次游戏后就会把机子砸了，所以我们可以看到游戏有时会失帧。
 * 
 * 2.使用方法： 要建立基于UDP协议的链接，我们应该先建立套接字<DatagramSocket>(即发送站或接收站),之后通过套接字发送或接受数据<DatagramPacket>。
 * 我们先了解一下DatagramSocket和DatagramPacket类
 * 
 * DatagramSocket类：
 * 
 * 构造方法： DatagramSocket(): 表示创建一个默认的套接字，并绑定到本地地址和一个随机的端口号
 * 
 * DatagramSocket(int port): 与上面不同的是，绑定到特定端口号，其它不变
 * 
 * DatagramSocket(int port, InetAddress iad): 表示创建一个套接字，绑定到特定的端口号及指定地址
 * 
 * DatagramSocket(SocketAddress sad); 表示创建一个套接字，绑定到特定的套接字地址
 * 小提示：所谓的本地地址我们可以这样来理解，我们通过“控制面板-本地连接-支持”中可以看到一个IP地址，这就是本地的IP地址，也暂且理解为本地地址。
 * 
 * 基本方法：
 * 
 * close(): 关闭套接字
 * 
 * recevie(DatagramPacket dp): 接受数据报
 * 
 * send(DatagramPacket dp): 发送数据报
 * 
 * 这里触及了数据报，下面介绍数据报：
 * 
 * DatagramPacket类： 构造方法：(只列出主要的)
 * 
 * 接受类型： DatagramPacket(byte[] buf, int length):
 * 用来接受长度为length的buf数据(即数据存于字节数组buf中)
 * 
 * 发送类型： DatagramPacket(byte[] buf, int length, InetAddress address, int port);
 * 将length长的buf数据发送到指定的地址的端口号处
 * 
 * DatagramPacket(byte[] buf, int length, SocketAddress address);
 * 将length长的buf数据发送到指定的套接字地址处
 * 
 * 有上面可知，我们发送数据时构造的数据报应该是发送类型的，而接受数据报时应该是接受类型的 开始书写代码 代码意图： 1.一个接收方，一个发送方
 * 
 * 2.当接收方收到发送方发送的消息后，打印发送的消息及发送方的地址和端口号，之后向发送反馈一条信息“接受方：我收到了！” 3.发送方打印出接收方反馈的消息
 * 
 * 测试已通过 先启动接收方，然后启动发送方即可
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-2 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class Sender{
	
	public static void main(String[] args) {
		try {
			
		// 创建发送方的套接字，IP默认为本地，端口号随机
		DatagramSocket sendSocket = new DatagramSocket();

		for(int i=0;i<50;i++){
//			 确定要发送的消息：
			String mes = "你好！接收方！ "+i;

			// 由于数据报的数据是以字符数组传的形式存储的，所以传转数据
			byte[] buf = mes.getBytes();

			// 确定发送方的IP地址及端口号，地址为本地机器地址
			int port = 8888;
			InetAddress ip = InetAddress.getLocalHost();

			// 创建发送类型的数据报：
			DatagramPacket sendPacket = new DatagramPacket(buf,buf.length,ip,port);

			// 通过套接字发送数据：
			sendSocket.send(sendPacket);

			// 确定接受反馈数据的缓冲存储器，即存储数据的字节数组
			byte[] getBuf = new byte[1024];

			// 创建接受类型的数据报
			DatagramPacket getPacket = new DatagramPacket(getBuf,getBuf.length);

			// 通过套接字接受数据
			sendSocket.receive(getPacket);

			// 解析反馈的消息，并打印
			String backMes = new String(getBuf,0,getPacket.getLength());
			System.out.println("接受方返回的消息："+backMes);
		}
		

		// 关闭套接字
		sendSocket.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
