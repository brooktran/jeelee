/* DiamondMapTest.java 1.0 2010-2-2
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
package org.mepper.editor.map;

import java.awt.Point;

import junit.framework.TestCase;

/**
 * <B>DiamondMapTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-25 created
 * @since org.mepper.editor.map Ver 1.0
 * 
 */
public class DiamondMapTest extends TestCase {

	public void testScreenToMap() throws Exception {
		Map map=createMap(3,4,48,24);
		
		System.out.println(map.screenToMap(new Point(70,25)));
		assertEquals(new Point(1,1),map.screenToMap(new Point(70,25)));
		assertEquals(new Point(1,2),map.screenToMap(new Point(87,46)));
		assertEquals(new Point(2,3),map.screenToMap(new Point(96,60)));
		
		
		MapOffset offset = map.getOffset();
		offset.offsetX = 139+72;
		offset.offsetY= 128;
		map.setOffset(offset);
		assertEquals(new Point(1,1),map.screenToMap(new Point(209,153)));
	}
	
	
	public void testMapToScreen()throws Exception {
		Map map=createMap(3,4,48,24);
		
		System.out.println(map.mapToScreen(new Point(1,2)));
		assertEquals(new Point(72,24),map.mapToScreen(new Point(1,1)));
		assertEquals(new Point(96,36),map.mapToScreen(new Point(1,2)));
		assertEquals(new Point(96,60),map.mapToScreen(new Point(2,3)));
		
		
		MapOffset offset = map.getOffset();
		offset.offsetX = 139+72;
		offset.offsetY= 128;
		map.setOffset(offset);
		assertEquals(new Point(209,153),map.screenToMap(new Point(1,1)));
	}
	

//	public void testConvertToTile() throws Exception {
//		Map map=createMap(3,4,48,24);
//		Point p;
//		MapOffset offset = map.getOffset();
//		
//		p=new Point(96,10);
//		assertEquals(new Point(72,0), map.convertToTile(p));
//		
//		p=new Point(118,10);
//		assertEquals(new Point(96,12), map.convertToTile(p));
//		
//		p=new Point(118,25);
//		assertEquals(new Point(120,24), map.convertToTile(p));
//		
//		offset.offsetX=296;
//		offset.offsetY=200;
//		map.setOffset(offset);
//		
//		p=new Point(318,225);
//		assertEquals(new Point(320,212), map.convertToTile(p));
//	}

//	public void testClone() throws Exception {
//		Map map=createMap(84,84,48,24);
//		Map other=map.create(); 
//		assertEquals(map, other);
////		assertFalse(map.getType()==other.getType());
//	}
	
//	public void testCreate() throws Exception {
//		Map map=createMap(84,84,20, 50);
//		Map other= map.create();
//
//		assertEquals(map,other);
//		assertNotSame(map,other);
//		assertEquals(map.getOffset() , other.getOffset());
//		assertNotSame(map.getOffset(),other.getOffset());
//		
////		other.setDimension(20, 20);
////		assertFalse(map.getDimension().equals( other.getDimension()));
//	}
	
	public void testOffset() throws Exception {
		Map map=createMap(3, 4, 48, 24);
		MapOffset offset;
		
		// init test
		assertEquals(new Point(0,0), map.screenToMap(new Point(72, 10)));
		
		// translate
		offset=map.getOffset();
		offset.offsetX=272;
		offset.offsetY=200;
		map.setOffset(offset);
		assertEquals(new Point(0,0), map.screenToMap(new Point(272,210)));
	}
	
	private Map createMap(int row, int column, int tileWidth, int tileHeight) {
		DiamondMap map=new DiamondMap();
		map.setTileStep(tileWidth, tileHeight);
		map.setLogicalSize(row, column);
		return map;
	}
	
	
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(DiamondMapTest.class);
		
		
		
	}

}
