package com.test.test;

/**
 * 静态工厂类
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2009-1-2 新建
 * @since eclipse Ver 1.0
 * 
 */
public class DBConnectionFactory {
	public static Object factory(Class conn) {
		try{
			return conn.newInstance();
		}catch (InstantiationException e) {
			System.out.print("找不到类"+conn.getClass().getName());
		}catch (IllegalAccessException e) {
			System.out.println("类不正确，请检查"+conn.getClass().getName()+
					"是否属于 IDBConnection ");
		}
		return null;
	}
}
