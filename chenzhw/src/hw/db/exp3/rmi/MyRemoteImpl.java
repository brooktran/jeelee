package hw.db.exp3.rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 类<B> MyRemoteImpl </B>是远程实现,必须实现远程接口。
 * 为了成为远程服务对象，必须继承UnicastRemoteObject以实现远程功能(这种方法最简单).
 * 由于UnicastRemoteObject的构造函数抛出RemoteException，所以必须声明一个构造函数以抛出这个异常.
 * 
 * 运行步骤：
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-3 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

	/**
	 * 作用:由于UnicastRemoteObject的构造函数抛出RemoteException， 所以必须声明一个构造函数以抛出这个异常
	 * 
	 * @since chenzhw
	 */
	public MyRemoteImpl() throws RemoteException {

	}

	// 注意：不要声明RemoteException
	public String sayHello() {
		return "Server says: Hey";
	}

	public static void main(String[] args) {
		try {
			MyRemote service = new MyRemoteImpl();// 产生远程对象
			/**
			 * 将上面的服务实例化，，然后放进RMI registry中，（确保RMI registry已经运行，否则注册会失败)
			 * .注册这个服务时，RMI其实注册的是Stub(桩)
			 */
			Naming.rebind("RemoteHello", service);// 为服务命名，客户端按此名称寻找。此时RMI会把service换成Stub，然后把Stub放在Registry中
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
