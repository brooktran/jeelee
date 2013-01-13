/*
 * DefaultResolver.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.divine.app.liuyao.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.divine.app.liuyao.EightDiagrams;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.SixRelatives;
import org.divine.app.liuyao.Yao;
import org.divine.app.liuyao.resolve.relationship.Generate;
import org.divine.app.liuyao.resolve.relationship.RelationshipFactory;
import org.divine.app.liuyao.resolve.relationship.Restricte;

/**
 * <B>DefaultResolver</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-9-23 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class DefaultResolver {

	private boolean isUseYear;
	private boolean isUseMonth;
	private boolean isUseDay;
	private boolean isUseHour;
	private boolean isUseMinute;
	
	

	/** 用神 */
	private SixRelatives useRelations;

	private final LiuYao liuyao;

	private final EventListenerList listenerList = new EventListenerList();

	/**
	 * @param liuyao
	 */
	public DefaultResolver(LiuYao liuyao) {
		this.liuyao = liuyao;
	}

	/**
	 * @param l
	 */
	public void addResolverListener(ResolverListener l) {
		listenerList.add(ResolverListener.class, l);
	}

	/**
	 * Start.
	 */
	public void start() {
		// 与日月(四柱)关系  最先看空、墓。
		basicPower();
		
		// 变爻与其它爻关系
		changedPower();
		
		
		// 动爻 与动爻/静爻 之间的关系
		changePower();
		
		// 静爻 与 静爻/伏神 的关系
		silentPower();
		
		// sent the resolve changed event;
		fireResolveChanged();
	}

	/**
	 * 静爻 与 静爻/伏神 的关系
	 */
	private void silentPower() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 动爻 与动爻/静爻 之间的关系
	 */
	private void changePower() {
		EightDiagrams diagrams = liuyao.getDiagrams();
		List<Yao> changeYaos=diagrams.getYaos(Level.CHANGE_LEVEL);	// 动爻
		
		//*****************  动爻与动爻关系  **************************//
		
		changeYaos=removeNopower(changeYaos); 
		
		int changeCounter=changeYaos.size();
		if(changeCounter == 0){
			return ;
		}
		if(changeCounter == 1){
			//TODO 动爻与静爻伏神的关系
			return ;
		}
		
		
		//TODO  先去掉因冲合而无权的爻?
		
		/*
		 * 动爻与动爻的作用思路:
		 * 1. 先找到关键爻(A): 最后一个/两个被生的; 最多只有两个;
		 * 2. 若关键爻(A)仅有一个, 从关键爻开始, 分析被关键爻克的动爻(B);
		 * 3. 接着分析被B生(若无生则分析克,若无退出)的爻;
		 * 4. 一直到所有动爻的分析完成;
		*/
		
		List<Yao> keyYaos = new ArrayList<Yao>(2);/*关键爻 最多有三个, 一般情况下仅有1~2个
		*	1. 相同六亲的情况:仅在存在两个 五行(六亲)相同关键爻时, 第二个变量才发生作用
		*	2. 不同六亲的情况: 关键爻可能有三个;
		*/

		boolean[] finish=new boolean[6];
		int currentIndex;
		for(int i=0,j=changeYaos.size();i<j;i++){// TODO  优化 
			if(finish[i])continue;
			Yao keyYao=changeYaos.get(i);
			currentIndex=i;
			
			for(int m=0,n=changeYaos.size();m<n;m++){
				Yao conditionYao=changeYaos.get(m);
				if (RelationshipFactory.hasGenerate(keyYao,conditionYao )) {// TODO 添加relationship 
					if(finish[m]){
						finish[currentIndex]=true;
						keyYao=null;
						break;
					}
					conditionYao.addRelationship(new Generate(keyYao, conditionYao));
					finish[currentIndex]=true;
					keyYao=conditionYao;
					currentIndex=m;
				}
			}
			
			if (keyYao == null) continue;

			if (!keyYaos.contains(keyYao))
				keyYaos.add(keyYao);

			// add the same five element yaos, if exist.
			for (int m = 0, n = changeYaos.size(); m < n; m++) {
				Yao conditionYao = changeYaos.get(m);
				if (RelationshipFactory.isSameElement(keyYao, conditionYao)) {// 用神两现时,
																				// 有可能二生一的情况
					if (keyYaos.contains(conditionYao))
						continue;
					keyYaos.add(conditionYao);
					finish[m] = true;
				}
			}
			
		}
		
		
		for(Yao y:keyYaos){
			System.out.println(y);
		}
		
		
		for(int i=0,j=keyYaos.size();i<j;i++){//  克
			Yao keyYao=keyYaos.get(i);
			for(int m=0,n=changeYaos.size();m<n;m++){
				Yao conditionYao=changeYaos.get(m);
				if (RelationshipFactory.hasRestricte(keyYao,conditionYao )) {
					conditionYao.addRelationship(new Restricte(keyYao, conditionYao));
				}
			}
			
		}
		
	}

	/**
	 * 删除无用之爻
	 * @param changeYaos
	 */
	private List<Yao> removeNopower(List<Yao> changeYaos) {
		List<Yao> newYaos=new ArrayList<Yao>();
		for (int i=0,j=changeYaos.size(); i<j;i++) {
			Yao y=changeYaos.get(i);
			if (y.hasPower()) {
				newYaos.add(y);
			}
		}
		return newYaos;
	}

	/**
	 *  与日月(四柱)关系  最先看空、墓。
	 */
	private void basicPower() {
		EightDiagrams diagrams = liuyao.getDiagrams();
		EightDiagrams changedDiagrams = liuyao.getChangeDiagrams();
		
		if (changedDiagrams != null) {// 变爻
			for (Yao y : changedDiagrams.getYaos(Level.CHANGED_LEVEL)) {
				resolveMonthDay(y);
			}
		}
		
		// 动爻 静爻 伏神
		for (Yao y:diagrams.getYaos(Level.All_LEVEL)) {
			resolveMonthDay(y);
		}
	}
	
	/**
	 * 变爻与其它爻关系
	 */
	private void changedPower() {
		EightDiagrams diagrams = liuyao.getDiagrams();
		EightDiagrams changedDiagrams = liuyao.getChangeDiagrams();
		if (changedDiagrams == null) {// 变爻
			return;
		}
		// 动爻 静爻 伏神
		List<Yao> orgin=diagrams.getYaos(Level.All_LEVEL);
		
		// 变爻
		List<Yao> changedYaos=changedDiagrams.getYaos(Level.CHANGED_LEVEL);
		
		for(Yao ch:changedYaos){
			if( !ch.hasPower() || RelationshipFactory.getAllRelationship(ch, diagrams.getYao(ch.getIndex()))){//// 但变爻对本位动爻有生克冲合作用时，不再与主卦中其它旁爻发生作用，
				continue;
			}
			
			//当变爻对本位动爻没有生克冲合作用时，变爻就与主卦中多其它旁爻发生作用。
			for (Yao y : orgin) {
				if(RelationshipFactory.getFirstRelationship(ch, y)){
					break;
				}
				// TODO 对它爻发生作用
			}
		}
		
	}
	
	
	
	private void resolveMonthDay(Yao yao){
		//  与日月(四柱)关系  最先看空、墓。
		for(Iterator<Pillar> i=liuyao.pillarIterator();i.hasNext();){
			Pillar p=i.next();
//			if(p.getLevel().equals(Level.DAY_LEVEL)){
				RelationshipFactory.pillarRelationship(p, yao);
//			}
			RelationshipFactory.getAllRelationship(p, yao);
		}
//		RelationshipFactory.getRelationship(
//				liuyao.getPillar(LiuYao.PillarDay), yao);
	}

	/**
	 * 
	 */
	private void fireResolveChanged() {

		EightDiagrams diagrams = liuyao.getDiagrams();
		EightDiagrams changedDiagrams = liuyao.getChangeDiagrams();

		if (changedDiagrams != null) {// 变爻
			// 变爻
			for (Yao y : changedDiagrams.getYaos(Level.CHANGED_LEVEL)) {
				fireResolveChanged(y);
			}
		}
		// 动爻 静爻 伏神
		for (Yao y:diagrams.getYaos(Level.All_LEVEL)) {
			fireResolveChanged(y);
		}
			
	}
	
	/**
	 * @param changedYao
	 */
	private void fireResolveChanged(Yao yao) {
		ResolveEvent e = new ResolveEvent(this, yao);
		Object[] listeners = listenerList.getListenerList();
		for (int i = 0, j = listeners.length; i < j; i += 2) {
			if (listeners[i] == ResolverListener.class) {
				((ResolverListener) listeners[i + 1]).outputResolve(e);
			}
		}
	}

	

	//
	// /** 伏吟 repeat=1 内卦 repeat=2 外挂 repeat=3 内外 repeat=0. */
	// private int repeat;
	//
	// /** 伏吟 repeat=1 内卦 repeat=2 外挂 repeat=3 内外 repeat=0. */
	// public static final int REPEAT_NON = 0;
	//
	// /** 伏吟 repeat=1 内卦 repeat=2 外挂 repeat=3 内外 repeat=0. */
	// public static final int REPEAT_IN = 1;
	//
	// /** 伏吟 repeat=1 内卦 repeat=2 外挂 repeat=3 内外 repeat=0. */
	// public static final int REPEAT_OUT = 2;
	//
	// /** 伏吟 repeat=1 内卦 repeat=2 外挂 repeat=3 内外 repeat=0. */
	// public static final int REPEAT_ALL = 3;
	//
	// /** 反吟 reverse=1 内卦 reverse=2 外挂 reverse=3 内外 reverse=0. */
	// private int reverse;
	//
	// /** 反吟 reverse=1 内卦 reverse=2 外挂 reverse=3 内外 reverse=0. */
	// public static final int REVERSE_NON = 0;
	//
	// /** 反吟 reverse=1 内卦 reverse=2 外挂 reverse=3 内外 reverse=0. */
	// public static final int REVERSE_IN = 0;
	//
	// /** 反吟 reverse=1 内卦 reverse=2 外挂 reverse=3 内外 reverse=0. */
	// public static final int REVERSE_OUT = 0;
	//
	// /** 反吟 reverse=1 内卦 reverse=2 外挂 reverse=3 内外 reverse=0. */
	// public static final int REVERSE_ALL = 0;

	// // init repeat and reverse 反吟 伏吟 TODO
	// boolean repIn = true, repOut = true, revIn = true, revOut = true;
	// if (changeDiagrams != null) {
	// for (int i = 0; i < 6; i++) {
	// if (!diagrams.getBranch(i).equals(changeDiagrams.getBranch(i))) {
	// if (i < 3) {
	// repIn = false;
	// } else {
	// repOut = false;
	// }
	// }
	// if (!diagrams.getBranch(i).isOpposition(
	// changeDiagrams.getBranch(i))) {
	// if (i < 3) {
	// revIn = false;
	// } else {
	// revOut = false;
	// }
	// }
	// }
	// }
	//
	// repeat = repIn ? 1 : 0;
	// repeat += repOut ? 2 : 0;
	//
	// reverse = revIn ? 1 : 0;
	// reverse += revOut ? 2 : 0;

	// TODO use new thread

	// create Pillars

	// handle changed yao
	// analyseFourSeasons(changeDiagrams, pillars);
	// analyseFourSeasons(diagrams, pillars);
	//
	// if (changeDiagrams != null) {
	// for (int i = 0; i < 6; i++) {
	// Level level = changeDiagrams.getYao(i).getLevel();
	// if (level.equals(Level.CHANGED_LEVEL)) {
	// if (diagrams.getYao(i)
	// .addRelation(changeDiagrams.getYao(i))) {
	//
	// } else {
	// // TODO action to other yaos
	// }
	// }
	// }
	// }
}
