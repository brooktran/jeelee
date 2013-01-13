/**
 * 
 */
package com.test.test;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;


/**
 * the basics of hashing
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-5 新建
 * @since eclipse Ver 版本号
 * 
 */
public class SimpleHashMap<K,V> extends AbstractMap<K, V>{
	//choose a prime number for the hash table
	//size, to acheve a uniform distribution
	static final int SIZE=997;
	
	//you can't have a physical array of generics,
	//but you can upcast to one;
	@SuppressWarnings("unchecked") 
	LinkedList<MapEntry<K,V>>[] buckets=new LinkedList[SIZE];
	
	@Override
	public V put(K key,V value){
		
		V oldValue=null;
		
		//实现哈希排序
		
		int index=Math.abs(key.hashCode())%SIZE;//计算hash地址
		if(buckets[index]==null){//表满？
			buckets[index]=new LinkedList<MapEntry<K,V>>();
		}		
		LinkedList<MapEntry<K, V>> bucket=buckets[index];//定义节点
		MapEntry<K, V> pair=new MapEntry<K, V>(key,value);//待插入节点
		boolean found=false;
		ListIterator<MapEntry<K, V>> it=bucket.listIterator();
		//从buckets[index]开始查找，若对应index位置不匹配，则继续查找下个节点
		while(it.hasNext()){
			MapEntry<K, V> ipair=it.next();//指示当前遍历到的节点
			if(ipair.getKey().equals(key)){
				oldValue=ipair.getValue();
				it.set(pair);//replace old with new
				found =true;
			}
		}
		if(!found){
			buckets[index].add(pair);
		}
		return oldValue;
	}	
	
	@Override
	public V get(Object key){
		int index=Math.abs(key.hashCode())%SIZE;
		if((buckets[index])==null) return null;
		for(MapEntry<K, V> iPair:buckets[index])//从buckets【index】开始遍历buckets
		{
			if(iPair.getKey().equals(key)){
				return iPair.getValue();
			}
		}
		return null;
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet(){//Returns a Set view of the mappings contained in this map. 
		Set<Map.Entry<K,V>> set =new HashSet<Map.Entry<K,V>>();
		for(LinkedList<MapEntry<K, V>> bucket:buckets){
			if(bucket==null)continue;
			for(MapEntry<K, V> mpair:bucket){
				set.add(mpair);
			}
		}
		return set;
	}
	public static void main(String[] args) {
		SimpleHashMap<String, String> m=new SimpleHashMap<String, String>();
		m.put("China", "00001");
		m.put("American","00002");
		m.put("China2", "00003");
		m.put("China3", "00003");
		m.put("China4", "00003");
		m.put("China5", "00003");
		m.put("China6", "00003");
		m.put("China7", "00003");
		m.put("China8", "00003");
		System.out.println(m);
		System.out.println(m.get("China5"));
		System.out.println(m.entrySet());
	}
}























	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

