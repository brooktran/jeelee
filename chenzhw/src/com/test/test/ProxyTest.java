package com.test.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class ProxyTest implements InvocationHandler{
	
	Data data;
	
	public Data bind(Data data) {
		this.data=data;
		Class[] interfaces=data.getClass().getInterfaces();
		if(null==interfaces||0==interfaces.length){
			interfaces=new Class[1];
			interfaces[0]=InterfaceTest.class;
		}
		Data d=(Data)Proxy.newProxyInstance(data.getClass().getClassLoader(),
				interfaces, this);
		return d;
				
	}

	/**
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object object=null;
		if("testMethod".equals(method.getName())){
			System.out.println("proxy");
		}
		else {
			object=method.invoke(data, args);
		}
		return object;
	}

	
}
