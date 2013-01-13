package hw.db.exp3.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 类<B> MyRemote </B>是一个远程接口，是一个记号，不具有方法，它有特殊的意义。
 * MyRemote中所有方法都必须抛出RemoteException
 * 
 * 运行方法:
 * a: 在eclipse上先运行MyRemoteImpl，再运行Client
 * b：在dos或Linux上
 * 		1.启动服务 rmic hw.db.exp3.rmi.MyRemoteImpl //必须带包 ，路径设置在 src上，即项目src下
 * 		2.启动rmicregistry 直接输入 rmicregistry
 * 		3.运行客户端
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-3 新建
 * @since chenzhw Ver 1.0
 * 
 */
public interface MyRemote extends Remote{
	//返回值必须是原语(primitive)或者可序列化(serializable)类型
	/**
	 * 作用:.
	 * 
	 * @return the string
	 * 
	 * @throws RemoteException the remote exception
	 */
	public String sayHello() throws RemoteException;
}
