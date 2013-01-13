/* RelationshipFactory.java 1.0 2010-2-2
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
import org.divine.app.liuyao.resolve.Level;
import org.divine.app.liuyao.resolve.Pillar;

/**
 * <B>RelationshipFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-8 created
 * @since org.divine.app.liuyao.resolve Ver 1.0
 * 
 */
public class RelationshipFactory {
	
	/**
	 * 临 值 空
	 * @param hight  the pillar
	 * @param low the yao
	 * @return
	 */
	public static void pillarRelationship(Item hight, Item low) {
		if(!hight.getLevel().isEqual(Level.Pillar_LEVEL)){
			return;
		}
		
		if(hight.fiveElement().equals(low.fiveElement())){
			if(hight.getBranch().equals(low.getBranch())){
				low.addRelationship(new Zhi(hight, low));
			}else {
				low.addRelationship(new Lin(hight, low));
			}
		}
		
		if(!hight.getLevel().equals(Level.DAY_LEVEL)){
			return ;
		}
		boolean empty= ((Pillar)hight).checkEmpty(low.getBranch());
		if(empty){
			low.addRelationship(new Empty(hight,low));
		}
	}
	
	/**
	 * 当找到第一个关系后便退出. 
	 * 冲合 生克 墓绝
	 * @param ch
	 * @param y
	 */
	public static boolean getFirstRelationship(Item hight, Item low) {
		if(  !checkLevel(hight,low)){
			return false;
		}



		// 旺相休囚死 冲合 生克 墓绝

		// if(toElement.isWangAt(from.getBranch())){// 旺
		// to.setStrength(new Wang(from,to.getStrength()));
		// }else if (fromElement.isGenerate(toElement)) {// 相
		// to.setStrength(new Xiang(from,to.getStrength()));
		// }else if (fromElement.isDead(toElement)) {// 死
		// to.setStrength(new Dead(from,to.getStrength()));
		// }else {//休囚
		// to.setStrength(new XiuQiu(from,to.getStrength()));
		// }

		if(getSixOppsition(hight,low)){// 冲
			return true;
		}else if (getSixCoopration(hight,low)) {// 合
			low.addRelationship(new SixCoopration(hight, low));
			return  true;
		}
		
		if(hasGenerate(hight,low)){// 贪生
			low.addRelationship(new Generate(hight, low));
			return true;
		}else if (hasRestricte(hight,low)) {// 克
			low.addRelationship(new Restricte(hight, low));
			return  true;
		}
		
		
//TODO 三合
		if (getMu(hight, low)) {// 墓
			low.addRelationship(new Mu(hight, low));
			return  true;
		}else if (getJue(hight, low)) {// 绝
			low.addRelationship(new Jue(hight, low));
			return  true;
		}

		return false;
			
	}

	/**
	 * 找两个Item之间存在的所有关系
	 * 冲合 生克 墓绝
	 * Gets the relationship.
	 * Note: the level of from must greater than(or equals) the to item.
	 * @param from the from
	 * @param to the to
	 * 
	 * @return the relationship
	 */
	public static boolean getAllRelationship(Item hight, Item low) {
		if(!checkLevel(hight,low)){
			return false;
		}


		boolean hasRelation = false;

		// 旺相休囚死 冲合 生克 墓绝

		// if(toElement.isWangAt(from.getBranch())){// 旺
		// to.setStrength(new Wang(from,to.getStrength()));
		// }else if (fromElement.isGenerate(toElement)) {// 相
		// to.setStrength(new Xiang(from,to.getStrength()));
		// }else if (fromElement.isDead(toElement)) {// 死
		// to.setStrength(new Dead(from,to.getStrength()));
		// }else {//休囚
		// to.setStrength(new XiuQiu(from,to.getStrength()));
		// }

		if(getSixOppsition(hight,low)){// 冲
			low.addRelationship(new SixOpposition(hight,low));
			hasRelation = true;
		}else if (getSixCoopration(hight,low)) {// 合
			low.addRelationship(new SixCoopration(hight, low));
			hasRelation = true;
		}
		
		if(hasGenerate(hight,low)){// 贪生
			low.addRelationship(new Generate(hight, low));
			hasRelation = true;
		}else if (hasRestricte(hight,low)) {// 克
			low.addRelationship(new Restricte(hight, low));
			hasRelation = true;
		}
		
		
//TODO 三合
		if (getMu(hight, low)) {// 墓
			low.addRelationship(new Mu(hight, low));
			hasRelation = true;
		}else if (getJue(hight, low)) {// 绝
			low.addRelationship(new Jue(hight, low));
			hasRelation = true;
		}

		return hasRelation;
			
	}

	/**
	 * hight 有权
	 * hight 的层次大于或等于low
	 */
	public static boolean checkLevel(Item hight, Item low) {
		if (!hight.hasPower() || low.getLevel().isGreater(hight.getLevel())) {
			return false;// hight 大于或等于low
		}	
		return true;
	}

	/**
	 * @param hight
	 * @param low
	 * @return
	 */
	public static boolean getMu(Item hight, Item low) {
		if (!low.fiveElement().isMu(hight.getBranch())) {
			return false;
		}
		// 墓
		if(!hight.getLevel().isGreater(low.getLevel())){//爻不入同层次爻的墓，只入它的高层次爻之墓。
			return false;
		}
	
		
		// 变爻只能让本位动爻入墓
		if ((hight.getLevel().equals(Level.CHANGED_LEVEL)) && // 判断是否为变爻
				(((Yao) low).getIndex() != ((Yao) hight).getIndex() || // 是否本位
				low.getLevel().equals(Level.CHANGE_LEVEL))) {// 是否动爻
			return false;
		}
		return true;
	}

	

	public static boolean getJue(Item hight, Item low) {
		if (hight.fiveElement().isJue(low.getBranch())) {// 绝
			return true;
		}
		return false;
	}
	
	/**
	 * @param hight
	 * @param low
	 * @return
	 */
	public static boolean hasRestricte(Item hight, Item low) {
		 if (hight.fiveElement().isRestricte(low.fiveElement())) {// 克
				return true;
			}
		 return false;
	}

	/**
	 * @param hight
	 * @param low
	 * @return
	 */
	public static boolean hasGenerate(Item hight, Item low) {
		if (hight.fiveElement().isGenerate(low.fiveElement())) {// 贪生
			return true;
		}
		return false;
	}

	/**
	 * @param hight
	 * @param low
	 * @return
	 */
	public static boolean getSixCoopration(Item hight, Item low) {
		  if (hight.getBranch().isCoopration(low.getBranch())) {// 合
				return  true;
			}
		 return false;
	}

	/**
	 * @param higthtBranch
	 * @param lowBranch
	 * @return
	 */
	public static boolean getSixOppsition(Item hight, Item low) {
		if (hight.getBranch().isOpposition(low.getBranch())) { // 冲
			return  true;
		}
		return false;
	}

	/**
	 * 五行是否相同
	 * @param yao
	 * @param y
	 * @return
	 */
	public static boolean isSameElement(Item hight, Item low) {
		if (hight.fiveElement().equals(low.fiveElement())) {
			return true;
		}
		return false;
	}

}
