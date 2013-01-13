/* ArrayPairList.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.zhiwu.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>ArrayPairList</B> <ObjectA> <ObjectB> A1			B1 A2			B2
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-12-12 created
 * @since  org.zhiwu.utils Ver 1.0
 */
public class LeftRightList <Left,Right> {
	private final static Object[] NULL_ARRAY=new Object[0];
	/**
	 * @uml.property  name="list"
	 */
	private transient Object[] list=NULL_ARRAY;
	
	/**
	 * @return
	 * @uml.property  name="list"
	 */
	public Object[] getList(){
		return list;
	}
	
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public  List<Right> getAllRight() {
		Object[] lList = list;
//		int n=getRightCount(lList, l);
		List<Right> result=new ArrayList<Right>();
//		Right[] result=(Right[])Array.newInstance( ,n);
		for(int i=lList.length-2;i>=0;i-=2){
				result.add((Right)lList[i+1]);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<Right> getAllRight(Left l){
		Object[] lList = list;
//		int n=getRightCount(lList, l);
		List<Right> result=new ArrayList<Right>();
//		Right[] result=(Right[])Array.newInstance( ,n);
		for(int i=lList.length-2;i>=0;i-=2){
			if(lList[i].equals(l)){
				result.add((Right)lList[i+1]);
			}
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public Right getRight(Left l){
		Object[] lList = list;
//		int n=getRightCount(lList, l);
		Right result=null;
//		Right[] result=(Right[])Array.newInstance( ,n);
		for(int i=lList.length-2;i>=0;i-=2){
			if(lList[i].equals(l)){
				result=(Right)lList[i+1];
				break;
			}
		}
		return result;
	}
	
	public int getRightCount(){
		return list.length/2;
	}
	
	public int getRightCount(Left l){
		Object[] lList=list;
		return getRightCount(lList, l);
	}
	
	
	public int getRightCount(Object[] list,Left l){
		int count = 0;
		for(int i=0;i<list.length;i+=2){
			if(l == list[i]){
				count++;
			}
		}
		return count;
	}
	
	public synchronized  void add(Left left,Right right ){
		if(right==null){
			return;
		}
		
		if(list.length == 0){
			// if this is the first listener added, 
		    // initialize the lists
			list = new Object[]{left,right};
		} else {	    // Otherwise copy the array and add the new listener
			int i=list.length;
			Object[] tmp=new Object[i+2];
			System.arraycopy(list, 0, tmp, 0, i);
			tmp[i]=left;
			tmp[i+1]=right;
			list=tmp;
		}
	}
	
	public synchronized <T,K> void remove(T t,K k){
		if(k==null){
			// In an ideal world, we would do an assertion here
		    // to help developers know they are probably doing
		    // something wrong
			return;
		}
		
		// Is k on the list?
		int index=-1;
		for(int i=list.length-2;i>=0;i-=2){
			if(list[i] == t && (list[i+1].equals(k))){
				index=i;
				break;
			}
		}
		
		if(index == -1){
			return;
		}
		// if so, remove it.
		Object[] tmp=new Object[list.length-2];
		// copy the list uup to index
		System.arraycopy(list, 0, tmp, 0, index);
		 // Copy from two past the index, up to
	    // the end of tmp (which is two elements
	    // shorter than the old list)
		if(index < tmp.length){
			System.arraycopy(list, index+2, tmp, index,tmp.length-index);
			list=(tmp.length == 0)? NULL_ARRAY:tmp;
		}
		
		
	}

	  @Override
	public String toString() {
		Object[] lList = list;
		String s = "ArrayPairList: ";
		s += lList.length / 2 + " Left: ";
		for (int i = 0; i <= lList.length - 2; i += 2) {
			s += " Left " + lList[i].getClass().getName();
			s += " right " + lList[i + 1];
		}
		return s;
	}
}


















