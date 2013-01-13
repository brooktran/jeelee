/* DefaultPair.java 1.0 2010-2-2
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


/**
 * <B>DefaultPair</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-4-21 created
 * @since  org.zhiwu.utils Ver 1.0
 */
public class DefaultPair<K,V> implements Pair<K,V>{
	private K key;
	private V value;
	
	public DefaultPair(K key,V value) {
		this.key=key;
		this.value=value;
	}

	@Override
	public K getKey() {
		return key;
	}
	
	@Override
	public void setKey(K k) {
		this.key =k;
	}

	@Override
	public V getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return key+","+value;
	}

	@Override
	public void setValue(V v) {
		this.value=v;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj==null){
			return false;
		}
		if(obj instanceof DefaultPair){
			DefaultPair<?, ?> other=(DefaultPair<?, ?>)obj;
			return other.key.equals(key) && other.value.equals(value);
		}
		return false;
	}

}
