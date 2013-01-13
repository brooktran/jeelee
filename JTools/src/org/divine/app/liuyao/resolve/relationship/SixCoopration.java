/* SixCoopration.java 1.0 2010-2-2
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
package org.divine.app.liuyao.resolve.relationship;

import java.util.Iterator;

import org.divine.app.liuyao.resolve.Item;
import org.divine.app.liuyao.resolve.Level;

/**
 * <B>SixCoopration</B>
 * 六合<br/>
 * 1．必须六合两个爻，三合三个爻都动或者一方是日、月或是变爻(变爻与日、月，变爻与本位动爻)形成三
合、六合局。
2．必须在日或月建上有化神，且日、月任何一方不得为化神之克神。
一合、六合局合而不化论绊住减力，暂时失去生克权。同层次爻作合，合而不化，双方均减力。如果作合一
方特别旺，另一方不旺，或是高层爻同低层次爻作合，则不旺相一方及低层次爻一方便失去生克权。
在六爻卦中，静爻与静爻只有合象没有合力，因双方都静而不动，这种合一般没有力量，不论合，但能体现
一种信息之象。
三合、六合合化成功后，以合化出之五行论生克，若合而不化，低层次爻或衰弱之爻暂时失去生克权，待上
一层次爻解合时，方能有生克权，上层爻解合就是遇到上层次爻冲开。
一、六合
六合成化，一、必须是卦中两个爻都动，或卦中动爻与本位变爻之合。二、必须日或月建为化神，且日、月
任何一方不能是克化神之五行。
例：①未月、午日 卦中戌爻、卯爻动，卯戌合化火成功。
例：②亥月 午日，卦中戌爻、卯爻动，卯戌合而不化，双方均减。
在六合中，凡日或月与卦中之爻相合都为合而不化。凡动爻逢日或月合，无论旺衰均论绊住，暂时失去生克
权。但用神爻动逢生合，不论绊住，为增力，而原、忌、仇、闲神无论生合、克合都为绊住，暂时失去生克权
相当于静爻。
凡静爻逢日、月合，有些书说为合起，笔者认为并非全是如此，静爻逢生合在静爻旺相之时，谓之合起，相
当于动爻。逢日、月与静爻为克合时，无论爻旺衰都不论合起。在测出行中，用神无论动静、无论旺衰、无论
file:///F|/文化/六爻詳真.txt[2010/5/12 16:58:36]
生合还是克合，无论合化成功与否，都有一种合住之象。这种时候，往往体现是一种信息之象，是因事绊住，
暂出不能出行之象，至于因何事绊住，看主合之五行为何爻，如用神被日月建临父母爻合住，是因父母、车
辆、文书之事绊住。
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class SixCoopration extends AbstractRelationship {

	public static final int Coopration_no=0;
	
	/** the hight item has no power to coopration other items.*/
	public static final int FAIL_CODE_NO_POWER=1;
	
	private int failCode;
	
	/** 合而化 成功 */
	private boolean isSuccess;// if available than check is success.
	
	/** 合化之五行  */
	private boolean successFiveElement;
	/**
	 * @param hight
	 * @param strength
	 */
	public SixCoopration(Item source, Item to) {
		super(source, to);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#init()
	 */
	@Override
	protected void init() {
		super.init();

		if(!hight.hasPower()){// 是否有能力去合别人
			//TODO
			setAvailable(false);
			failCode=FAIL_CODE_NO_POWER;
			//System.out.println("SixCoopration.init(): hight has no power to coopration other items.");
			return;
		}
		
		// set available
		IRelationship s;
		Item item;
		for (Iterator<IRelationship> i = low.strengthIteator(); i.hasNext();) {
			s = i.next();
			item = s.getSource();
			if (!s.getTarget().getLevel().isGreater(item.getLevel())) {// 上层次或同层次才可解开本层次冲合
				if (s.getClass() == SixOpposition.class) {// 合而未冲      冲而未合
					
					if(s.available()){// 有效  合而未冲
						s.setAvailable(false); 
						setAvailable(false);
					}else {// invaild, 
						setAvailable(false);
					}
					// s.set
				}else if (s.getClass() == SixCoopration.class) {// 二合一不为合 
					setAvailable(false);
				}
			}
		}
//		一、六合六合成化，
//		一、必须是卦中两个爻都动，或卦中动爻与本位变爻之合。
//		二、必须日或月建为化神，且日、月任何一方不能是克化神之五行。
//		例：①未月、午日 卦中戌爻、卯爻动，卯戌合化火成功。
//		例：②亥月 午日，卦中戌爻、卯爻动，卯戌合而不化，双方均减。
//		在六合中，凡日或月与卦中之爻相合都为合而不化。
//		凡动爻逢日或月合，无论旺衰均论绊住，暂时失去生克权。
		//必须在日或月建上有化神，且日、月任何一方不得为化神之克神。
		if(available){// 可以合
			if(hight.getLevel().getLevelIndex() ==1){// 第一层次,为年月日
				isSuccess=false;// 在六合中，凡日或月与卦中之爻相合都为合而不化。
			}else if (hight.getLevel().equals(Level.CHANGED_LEVEL)) {// 变爻合回头合
				if(!low.getLevel().equals(Level.CHANGE_LEVEL)){// 不是变爻与动爻合,
					throw new IllegalArgumentException();//"what is the low item"
				}
				// 回头合
				low.getLiuYao();
			}
		}
		
		// 合而不化论绊住减力，暂时失去生克权。
		// 同层次爻作合，合而不化，双方均减力。
		//如果作合一方特别旺，另一方不旺，或是高层爻同低层次爻作合，则不旺相一方及低层次爻一方便失去生克权。
		if (available) {
		}
	}

	@Override
	public String getLabel() {
//		if (available) {
			return "合于" + getSourceLabel();
//		}
//		return super.getLabel();
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.IRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		return false;
	}


}
