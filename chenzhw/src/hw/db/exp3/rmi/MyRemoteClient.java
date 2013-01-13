package hw.db.exp3.rmi;

import java.rmi.Naming;

/**
 * 类<B> MyRemoteClient </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-3 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class MyRemoteClient {
	public static void main(String[] args) {
		new MyRemoteClient().go();
	}
	public void go() {
		try {
			MyRemote service=(MyRemote)Naming.lookup("rmi://127.0.0.1/RemoteHello");
			String s=service.sayHello();
			System.out.println(s);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
