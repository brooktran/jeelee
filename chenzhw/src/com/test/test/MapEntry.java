/**
 * 
 */
package com.test.test;

import java.util.Map;

/**
 * a simple Map.Entry for sample Map implementations.
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-5 新建
 * @since eclipse Ver 版本号
 * 
 */
public class MapEntry<K, V> implements Map.Entry<K, V> {
	private K key;

	private V value;

	public MapEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MapEntry))
			return false;
		MapEntry mEntry = (MapEntry) o;
		return (key == null ? mEntry.getKey() == null : key.equals(mEntry.getKey()))
			&& (value == null ? mEntry.getValue() == null : value.equals(mEntry.getValue()));
		// 若key为空，则判断o的key是否也为空；若不为空，则比较
	}
	
	@Override
	public String toString(){
		return key+"="+value;
	}
	
	@Override
	public int hashCode() {
		return (key == null ? 0 : key.hashCode())
				^ (value == null ? 0 : key.hashCode());
	}

	/**
	 * @see java.util.Map.Entry#getKey()
	 */
	public K getKey() {
		return key;
	}

	/**
	 * @see java.util.Map.Entry#getValue()
	 */
	public V getValue() {
		return value;
	}

	/**
	 * @see java.util.Map.Entry#setValue(java.lang.Object)
	 */
	public V setValue(V value) {
		V OldValue = this.value;
		this.value = value;
		return OldValue;
	}

}
