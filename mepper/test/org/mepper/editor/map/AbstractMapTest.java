/*
 * AbstractMapTest.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.mepper.editor.map;

import junit.framework.TestCase;

/**
 * <B>AbstractMapTest</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-5-30 created
 * @since org.mepper.editor.map Ver 1.0
 * 
 */
public class AbstractMapTest extends TestCase {

	public void testCreate() throws Exception {
		try {
			DiamondMap map = new DiamondMap();
			map.addLayer(new DefaultLayer(), 0);
			Map other = map.create();
			assertFalse(map == other);
			assertFalse(map.equals(other));
			assertTrue(map.getClass().getName()
					.equals(other.getClass().getName()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
