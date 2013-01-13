/* Mu.java 1.0 2010-2-2
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

import org.divine.app.liuyao.Yao;
import org.divine.app.liuyao.resolve.Item;
import org.divine.app.liuyao.resolve.ItemEvent;
import org.divine.app.liuyao.resolve.ItemListener;
import org.divine.app.liuyao.resolve.Level;

/**
 * <B>Mu</B>
 * 墓
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class Mu extends AbstractRelationship {

	/**
	 * @param hight
	 * @param strength
	 */
	public Mu(Item hight, Item low) {
		super(hight,low);
	}
	
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#init()
	 */
	@Override
	protected void init() {
		super.init();
		
		if(hight.getLevel().equals(Level.MONTH_LEVEL)){
			available=false;
		}
		
		checkEffective();
		
		if (! hight.getLevel().isEqual(Level.Pillar_LEVEL)){// 日月为墓不能冲墓
			hight.addItemListener(new ItemListener() {
				
				@Override
				public void relationshipChanged(ItemEvent e) {//合墓爻可解
					IRelationship r=e.getRelationsip();
					if ((r.getClass() != SixOpposition.class )){
						return;
					}
				}
			});
		}
		
		
		low.addItemListener(new ItemListener(){

			@Override
			public void relationshipChanged(ItemEvent e) {
				IRelationship r=e.getRelationsip();
				if(r.getClass() != SixOpposition.class  ){
					return;
				}
				

				if ((!hight.getLevel().isEqual(Level.Pillar_LEVEL)) // 日月为墓不能冲
						&& hight.hasRelationship(SixOpposition.class)) {// 冲墓，都可解墓
					available=false;
				}
			}
			
		});
		
	}
	
	
	/**
	 * 是否被解
	 */
	private void checkEffective() {
		IRelationship r;
		
		// 冲爻
		if( ((r=low.getRelationship(SixOpposition.class, Level.Pillar_LEVEL))!=null) &&
				r.available() && r.getHight().getLevel().isGreater(hight.getLevel())){//可解墓冲爻: 解墓的力量必须比墓大,
			available = false;//静爻、动爻、变爻入日或月墓的，只能冲爻来解。特殊情况是：日或月墓入卦，可以冲墓来解。
		}

		if ((!hight.getLevel().isEqual(Level.Pillar_LEVEL))) {// 冲墓 合墓，都可解墓
			if ( ((r=hight.getRelationship(SixOpposition.class))==null)// 冲墓
					&& r.available()){
				available = false;
			}
			if ( ((r=hight.getRelationship(SixCoopration.class))==null)//  合墓
					&& r.available()) {
				available = false;
			}
		}
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#validate()
	 */
	@Override
	protected void validate() {
		super.validate();
		
		if ((!available)  || (! hight.getLevel().isGreater(low.getLevel())) ) {
			return ;
		}
		
		
		if(hight.getLevel().equals(Level.CHANGED_LEVEL) //动爻只入本位变爻之墓，不入旁爻变出之墓， 静爻不入变爻之墓，无论是本位还是异位之墓都不入。
				&& ((Yao) hight).getIndex() != ((Yao) low).getIndex()) {
			available = false;
		}
		
		
		
		
//		low.setPower(false);
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "墓在" + hight;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#getLabel()
	 */
	@Override
	public String getLabel() {
		return "墓在" +getSourceLabel();
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractRelationship#isLowAvailable()
	 */
	@Override
	public boolean isLowAvailable() {
		return ! available;
	}
}
