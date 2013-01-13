/* YaoTest.java 1.0 2010-2-2
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
package org.divine.app.liuyao;

import static org.junit.Assert.fail;

import org.divine.app.liuyao.EarthlyBranch;
import org.divine.app.liuyao.FourQuadrant;
import org.divine.app.liuyao.GanZhi;
import org.divine.app.liuyao.HeavenlyStem;
import org.divine.app.liuyao.Yao;
import org.divine.app.liuyao.resolve.Level;
import org.divine.app.liuyao.resolve.Pillar;
import org.divine.app.liuyao.resolve.relationship.RelationshipFactory;
import org.junit.Test;

/**
 * <B>YaoTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-10-12 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public class YaoTest {

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#addRelationship(org.divine.app.liuyao.resolve.IRelationship)}.
	 */
	@Test
	public final void testAddRelationship() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getBranch()}.
	 */
	@Test
	public final void testGetBranch() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getFiveElement()}.
	 */
	@Test
	public final void testGetFiveElement() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#strengthIteator()}.
	 */
	@Test
	public final void testStrengthIteator() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#addRelation(org.divine.app.liuyao.resolve.Item)}.
	 */
	@Test
	public final void testAddRelation() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#hasPower()}.
	 */
	@Test
	public final void testHasPower() {
		Yao yao=new Yao(0, FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG));
		yao.setBranch(EarthlyBranch.Chen);//辰土被月冲,墓在月,空,无权
		
		Pillar sunPillar=new Pillar(Level.DAY_LEVEL, 
				new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.YI), 
						EarthlyBranch.Wei));// 乙未日 
		Pillar monthPillar =new Pillar(Level.MONTH_LEVEL,
				new GanZhi(HeavenlyStem.getHeavenlyStem(HeavenlyStem.BING), 
						EarthlyBranch.Xu));//丙戌月 
		
		RelationshipFactory.pillarRelationship(sunPillar, yao);
		RelationshipFactory.getAllRelationship(monthPillar, yao);
		RelationshipFactory.getAllRelationship(sunPillar, yao);

		System.out.print(yao.getLevel().getName() + "("
				+ (yao.getIndex() + 1) + "爻" + ")"
				+ yao.getRelative() + yao.getBranch()
				+ yao.getRelationString() +
				(yao.hasPower()?"有":"无")+"权\n");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#hasRelationship(java.lang.Class, org.divine.app.liuyao.resolve.Level)}.
	 */
	@Test
	public final void testHasRelationshipClassOfQextendsIRelationshipLevel() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#hasRelationship(java.lang.Class)}.
	 */
	@Test
	public final void testHasRelationshipClassOfQextendsIRelationship() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#Yao()}.
	 */
	@Test
	public final void testYao() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#Yao(org.divine.app.liuyao.LiuYao)}.
	 */
	@Test
	public final void testYaoLiuYao() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#Yao(int, org.divine.app.liuyao.FourQuadrant)}.
	 */
	@Test
	public final void testYaoIntFourQuadrant() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#initStrength()}.
	 */
	@Test
	public final void testInitStrength() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getIndex()}.
	 */
	@Test
	public final void testGetIndex() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#setIndex(int)}.
	 */
	@Test
	public final void testSetIndex() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#setBranch(org.divine.app.liuyao.EarthlyBranch)}.
	 */
	@Test
	public final void testSetBranch() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getRelative()}.
	 */
	@Test
	public final void testGetRelative() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#setRelative(org.divine.app.liuyao.SixRelatives)}.
	 */
	@Test
	public final void testSetRelative() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#setFourQuadrant(org.divine.app.liuyao.FourQuadrant)}.
	 */
	@Test
	public final void testSetFourQuadrant() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getFourQuadrant()}.
	 */
	@Test
	public final void testGetFourQuadrant() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#isCoopration(org.divine.app.liuyao.Yao)}.
	 */
	@Test
	public final void testIsCoopration() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#isChange()}.
	 */
	@Test
	public final void testIsChange() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#isOpposition(org.divine.app.liuyao.Yao)}.
	 */
	@Test
	public final void testIsOpposition() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getChangedFourQuadrant()}.
	 */
	@Test
	public final void testGetChangedFourQuadrant() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#toString()}.
	 */
	@Test
	public final void testToString() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getChangedYao()}.
	 */
	@Test
	public final void testGetChangedYao() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link org.divine.app.liuyao.Yao#getStrengthLabel()}.
	 */
	@Test
	public final void testGetStrengthLabel() {
		fail("Not yet implemented");
	}

}
