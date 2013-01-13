/* Pair.java 1.0 2010-2-2
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
 * <B>Pair</B> defines a simple key value pair.
 * @author   Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version   Ver 1.0.01 2010-4-21 created
 * @since   org.zhiwu.utils Ver 1.0
 */
public interface Pair<K,V> {
	
	public K getKey();
	public void setKey(K k);
	public V getValue();
	public void setValue(V v);
	
}
