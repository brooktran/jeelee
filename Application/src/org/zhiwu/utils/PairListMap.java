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
import java.util.Iterator;
import java.util.List;

/**
 * <B>ArrayPairList</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-25 created
 * @since org.zhiwu.utils Ver 1.0
 * 
 */
public class PairListMap<K,V> {
	private final List<Pair<K, V>> data ;
	
	public PairListMap() {
		this(0);
	}
	
	
	public PairListMap(int size) {
		data = new ArrayList<Pair<K,V>>(size);
	}


	public V getValue (K key){
		Pair<K, V> p=get(key);
		return p==null?null:p.getValue();
	}
	
	public Pair<K, V> get(K key){
		for(Pair<K, V> pair:data){
			if(pair.getKey().equals(key)){
				return pair;
			}
		}
		return null;
	}
	
	public void add(int index, K k,V v){
		data.add(index, new DefaultPair<K, V>(k,v));
	}
	
	public Iterator<Pair<K, V>> iterator(){
		return data.iterator();
	}

	public void put(K key, V v) {
		get(key).setValue(v);
	}


	public int size() {
		return data.size();
	}

	public Pair<K, V> get(int index) {
		return data.get(index);
	}


	public void clear() {
		data.clear();
	}
}
