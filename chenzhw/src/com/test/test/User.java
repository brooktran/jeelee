package com.test.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-5 新建
 * @since eclipse Ver 1.0
 * 
 */
public class User {
	// private String username;
	// private String password;

	private Map<String, String> userMap;

	public User() {
		 userMap = new HashMap<String, String>();
	}

	public boolean checkUser(String username) {
		if (null == username)
			return false;
		if (userMap.containsKey(username)) {
			return true;
		} else {
			return false;
		}
	}

	public boolean addUser(String username, String password) {
		if (null == username)
			return false;
		if (!checkUser(username)) {
			userMap.put(username, password);
			return true;
		}
		return false;
	}
	
	public boolean checkPassword(String username,String password){
		if (null == username)
			return false;
		Iterator<Map.Entry<String,String>> it=userMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry=(Map.Entry<String, String>)it.next();
			if(username==entry.getKey()&&password==entry.getValue()){
				return true;
			}
		}
		return false;
	}
	
	public void displayUser(){
		if(userMap.isEmpty()){
			System.out.println("there is no user in map ...");
		}
		Iterator<Map.Entry<String, String>> it=userMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry=(Map.Entry<String, String>)it.next();
			String username=entry.getKey();
			String password=entry.getValue();
			//String username=userMap.get(it.next());
			System.out.println(username+":"+password);
		}
	}

	public void modify(){
		Map<String, ArrayList<String>> testMap=new HashMap<String, ArrayList<String>>();
		ArrayList<String> s=new ArrayList<String>();
		
		s.add((String)"aa");
		s.add((String)"bb");
		s.add((String)"cc");
		s.add((String)"dd");
		s.add((String)"ee");
		s.add((String)"ff");
		
		
		testMap.put("aa", s);
		
		Iterator<Map.Entry<String,ArrayList<String>>> it=testMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, ArrayList<String>> entry=(Map.Entry<String, ArrayList<String>>)it.next();
			if("aa"==entry.getKey()){
				entry.getValue().add("asdfasdfasdfsdaf");
				ArrayList<String> ss=entry.getValue();
				for(String sss:ss){
					System.out.println(sss);
				}
			}
		}
		
	}
	
	public void getIndex(){
		Map<String, ArrayList<String>> testMap=new HashMap<String, ArrayList<String>>();
		ArrayList<String> s=new ArrayList<String>();
		
		s.add((String)"aa");
		s.add((String)"bb");
		s.add((String)"cc");
		s.add((String)"dd");
		s.add((String)"ee");
		s.add((String)"ff");
		
		testMap.put("aa", s);
		testMap.put("bb", s);
		testMap.put("cc", s);
		testMap.put("dd", s);
		testMap.put("ee", s);
		testMap.put("ff", s);
		testMap.put("gg", s);
		
		Iterator<Map.Entry<String,ArrayList<String>>> it=testMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, ArrayList<String>> entry=(Map.Entry<String, ArrayList<String>>)it.next();
			if("aa"==entry.getKey()){
				entry.getValue().add("asdfasdfasdfsdaf");
				
				
				
				
				ArrayList<String> ss=entry.getValue();
				for(String sss:ss){
					System.out.println(sss);
				}
			}
		}
		
	}

	/**
	 * 作用:删除用户。
	 * 
	 * @param username
	 *            要删除的用户名
	 * @return <tt>true</tt> 如果用户名不为{@code null}并且该用户名存在.
	 */
	public boolean deleteUser(String username) {
		if(null==userMap.remove(username)){
			return false;
		}
		return true;

	}
	
	
	public boolean testGetKey(String username){
		//if(userMap.containsKey(username))
		return true;
		//return userMap.get("zhangsan")=="893242";
	}
	
	public static void main(String[] args) {
		User user=new User();
		user.addUser("zhangsan","893242");
		user.addUser("lisi","23462");
		user.addUser("libai","qiang");
		user.displayUser();
		
		if(!user.addUser("zhangyi", "hao")){
			System.out.println("add error...");
		}
		if(!user.addUser("zhangyi", "hao")){
			System.out.println("add error...");
		}
		String usernameString="zhangsan";
		System.out.println(user.checkUser(usernameString));
		
		
		List<String> list=new ArrayList<String>(); 
		list.add("asdf");
		System.out.println(list);
		String string=list.get(0);
		System.out.println(string);
		//System.out.println(user.userMap.get("zhangsan"));
		//user.displayUser();
		
		
		
		
		
		/*
		if(user.checkPassword("zhangyi", "hao")){
			System.out.println("用户名和密码正确");
		}
		if(!user.checkPassword("zhangyi", "hao1")){
			System.out.println("用户名和密码错误");
		}
		user.deleteUser("zhangyi");
		user.deleteUser("lidsi");
		user.displayUser();
		
		
		user.modify();
		
		user.getIndex();
		*/

	}
}
