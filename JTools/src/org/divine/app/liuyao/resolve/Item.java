/* Item.java 1.0 2010-2-2
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
package org.divine.app.liuyao.resolve;

import java.util.Iterator;

import org.divine.app.liuyao.EarthlyBranch;
import org.divine.app.liuyao.FiveElement;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.resolve.relationship.IRelationship;

/**
 * <B>Item</B>
 * 爻,日,月
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-7 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public interface Item {
	public Level getLevel();
	

	/**
	 * @return
	 */
	public FiveElement fiveElement();


	/**
	 * @return
	 */
	public int getStrength();


	/**
	 * @param wang
	 */
	public void addRelationship(IRelationship iRelationship);


	/**
	 * @return
	 */
	public EarthlyBranch getBranch();


	/**
	 * @return
	 */
	public String getLabel();


	/**
	 * @return
	 */
	public Iterator<IRelationship> strengthIteator();


	/**
	 * @param month
	 */
	public boolean addRelation(Item month);


	/**
	 * 是否有生克权
	 * @return
	 */
	public boolean hasPower();


	/**
	 * 
	 */
	public LiuYao getLiuYao();


	/**
	 * @param i
	 */
	public void setStrength(int strength);


	/**
	 * @param class1
	 * @param pillarLevel
	 * @return
	 */
	public boolean hasRelationship(Class<? extends IRelationship> c, Level hightLevel);


	/**
	 * @param class1
	 * @return
	 */
	public boolean hasRelationship(Class<? extends IRelationship> c);


	/**
	 * @param yaoListener
	 */
	public void addItemListener(ItemListener l);


	/**
	 * @param class1
	 * @return
	 */
	public IRelationship getRelationship(Class<? extends IRelationship> c);


	/**
	 * @param class1
	 * @param pillarLevel
	 * @return
	 */
	public IRelationship getRelationship(Class<? extends IRelationship> c, Level level);


	
	
	
}
