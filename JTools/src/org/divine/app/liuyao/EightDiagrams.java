/* EightDiagrams.java 1.0 2010-2-2
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.divine.app.liuyao.resolve.Level;
import org.divine.persistance.LiuyaoDataManager;
import org.jtools.app.persistent.DataException;

/**
 * <B>EightDiagrams</B> 六爻八卦
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-17 created
 * @since org.jtools.app Ver 1.0
 */
public class EightDiagrams {

	/** 是否存在变卦*/
	// protected boolean isChange = false;
	/** 是否游魂*/
	protected boolean isLoaf = false;
	/** 是否归魂*/
	protected boolean isReturn = false;

//	private List<Yao> hideYaos;

	/** 世爻*/
	protected int self;
	// private final EarthlyBranch[] branchs;
	private final List<Yao> yaos=new ArrayList<Yao>();
	// private final SixRelatives[] sixRelatives;
	/** 本宫. */
	private int orgin;

	/**
	 * You must invoke the setup() method after you create a new EightDiagram instance.
	 * @param sixYaos the six yaos
	 */
	public EightDiagrams(FourQuadrant[] sixYaos) {
		orgin = -1;
		setYaos(sixYaos);
	}

	/**
	 * You must invoke the setup() method after you create a new EightDiagram instance.
	 * @param yaos the yaos
	 */
	public EightDiagrams(Yao[] yaos) {
		for(Yao y:yaos){
			this.yaos.add(y);
		}
	}

	/**
	 * @param attributeValue
	 */
	public EightDiagrams(String yaos) {
		
//		if(yaos.length()!=6){ //TODO 考虑伏神
//			throw new IllegalArgumentException();
//		}
		FourQuadrant[] quadrants=new FourQuadrant[6];
		for(int i=0;i<6;i++){
			quadrants[i]=FourQuadrant.getFourQuadrant(Integer.parseInt(yaos.substring(i, i+1)));
		}
		setYaos(quadrants);
		
	}

	/**
	 * @param sixYaos
	 */
	private void setYaos(FourQuadrant[] sixYaos) {
//		yaos = new Yao[sixYaos.length];// TODO 将伏神添加到数组末端
		Yao y;
		for (int i = 0, j = sixYaos.length; i < j; i++) {
			y=new Yao(i, sixYaos[i]);
			yaos.add(y);
			if(y.isChange()){
				y.setLevel(Level.CHANGE_LEVEL);
			}
		}
	}

	
	/**
	 * Gets the diagram from the special yaos.
	 * 
	 */
	public void setup() {
		// EightDiagrams eightDiagrams = new EightDiagrams(yaos);

		FourQuadrant[] newSixYaos = new FourQuadrant[6];
		for (int i = 0; i < 6; i++) {
			newSixYaos[i] = yaos.get(i).getFourQuadrant().toTwoForms();
		}

		AncientDiagrams outDiagrams = null;
		AncientDiagrams inDiagrams = null;

		outDiagrams = AncientDiagrams.parseDiagram(Arrays.copyOfRange(
				newSixYaos, 3, 6));// 外挂
		outDiagrams.setOut(true);
		inDiagrams = AncientDiagrams.parseDiagram(Arrays.copyOfRange(
				newSixYaos, 0, 3));// 内挂
		inDiagrams.setOut(false);

		// 世应
		// 寻世歌
		// 天同二世 天变五
		// 地同四世 地变初
		// 本宫六世 三世异
		// 人同游魂4世    人变归3世

		// 天地人
		boolean heaven, earth, people;
		heaven = outDiagrams.getYao(2).equals(inDiagrams.getYao(2));
		people = outDiagrams.getYao(1).equals(inDiagrams.getYao(1));
		earth = outDiagrams.getYao(0).equals(inDiagrams.getYao(0));

		if (heaven && !earth && !people) {// 天同二世
			self = 1;
		} else if (!heaven && earth && people) {// 天变五
			self = 4;
		} else if (!heaven && earth && !people) {// 地同四世
			self = 3;
		} else if (heaven && !earth && people) {// 地变初
			self = 0;
		} else if (heaven && earth && people) {// 本宫六世
			self = 5;
		} else if (!heaven && !earth && !people) {// 三世异
			self = 2;
		} else if (!heaven && !earth && people) {// 人同游魂
			isLoaf = true;
			self = 3;
		} else if (heaven && earth && !people) {// 人变归
			isReturn = true;
			self = 2;
		}

		if (!hasOrgin()) {
			// 本宫 orgin
			// 认宫歌
			// 一二三六外卦宫
			// 四五游魂内变更
			// 归魂内卦是本宫
			if (isReturn) {
				orgin = inDiagrams.getValue();
			} else if (self == 0 || self == 1 || self == 2 || self == 5) {
				orgin = outDiagrams.getValue();
			} else if (self == 3 || self == 4) {
				orgin = inDiagrams.opposite().getValue();
			}
		}

		/***装地支
		乾震子午坎寅申 艮上辰戌顺行真 
		巽木丑未离卯酉 坤未丑兑巳亥循  */

		// 内卦
		int flag = inDiagrams.getTwoForm() == FourQuadrant.SHAO_YANG ? 1 : -1;
		EarthlyBranch baseBranch = inDiagrams.getSetupBranch();
		for (int i = 0; i < 3; i++) {
			setbranchs(i, baseBranch.next(2 * flag * i));
		}

		// 外卦
		flag = outDiagrams.getTwoForm() == FourQuadrant.SHAO_YANG ? 1 : -1;
		baseBranch = outDiagrams.getSetupBranch();
		for (int i = 0; i < 3; i++) {
			setbranchs(i + 3, baseBranch.next(2 * flag * i));
		}

		setSixRelationShip();
		setHideYaos();
	}

	/**
	 * @return
	 */
	private boolean hasOrgin() {
		return orgin != -1;
	}

	/**
	 * 装六亲
	 */
	private void setSixRelationShip() {
		// 装六亲
		FiveElement orginElement = getElement();
		for (int i = 0; i < 6; i++) {
			setRelationship(i, orginElement.getRelative(yaos.get(i).getBranch()
					.getElement()));
		}
	}

	/**
	 * Sets the Relative.
	 * 
	 * @param Relative the Relative
	 * @param index the index
	 */
	private void setRelationship(int i, SixRelatives relative) {
		yaos.get(i).setRelative(relative);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	public FiveElement getElement() {
		return AncientDiagrams.getDiagrams(orgin).getElement();
	}

	/**
	 * @param i
	 * @param next
	 */
	private void setbranchs(int i, EarthlyBranch branch) {
		yaos.get(i).setBranch(branch);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#toString()
	 */
	@Override
	public String toString() {
		String[] strings = new String[6];
		for (int i = 0; i < 6; i++) {
			strings[i] = yaos.get(i).toString();
		}
		StringBuffer sb = new StringBuffer();
		for (int i = strings.length - 1; i >= 0; i--) {
			sb.append(strings[i]);
			if (i == self) {
				sb.append("　世");
			}
			if (i == (self + 3) % 6) {
				sb.append("　应");
			}
			
			for(int j=6;j<yaos.size();j++){
				if(yaos.get(j).index == i){
					sb.append("\t伏神:"+yaos.get(j));
				}
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * @return
	 */
	public String getName() throws DataException, IOException {
		LiuyaoDataManager db = LiuyaoDataManager.getInstance();
		return db.getEightDiagramName(getTwoFormLabels());
	}

	/**
	 * Gets the labels. <p>
	 * eg: 000000 means Qian
	 * @return the labels
	 */
	private String getTwoFormLabels() {
		String labels = "";
		for (int i = 5; i >= 0; i--) {
			labels += yaos.get(i).getFourQuadrant().toTwoForms().getValue();
		}
		return labels;

	}
	
	/**
	 * The label of the Diagram.
	 * <p>
	 * eg: 032103, 3 means 阴动
	 * @return the yao labels
	 */
	public String getLabels(){
		String labels = "";
		for (int i = yaos.size() - 1; i >= 0; i--) {
			labels += yaos.get(i).getFourQuadrant().getValue();
		}
		return labels;
	}

	/**
	 * @return
	 */
	public boolean isChanged() {
		for (int i = 0, j = yaos.size(); i < j; i++) {
			if (yaos.get(i).isChange()) {
				return true;
			}
		}
		return false;
	}

	public int getOrgin() {
		return orgin;
	}

	/**
	 * @param i
	 * @return
	 */
	public EarthlyBranch getBranch(int i) {
		return yaos.get(i).getBranch();
	}

	/**
	 * @param i
	 * @return
	 */
	public SixRelatives getSixRelation(int i) {
		return yaos.get(i).getRelative();
	}

	/**
	 * @return
	 */
	public boolean isReturn() {
		return isReturn;
	}

	/**
	 * @return
	 */
	public boolean isLoaf() {
		return isLoaf;
	}

	/**
	 * Checks if is six coopration.
	 * note: the method can only invoked after the Diagram has init(has branchs).
	 * @return true, if is six coopration
	 */
	public boolean isSixCoopration() {
		for (int i = 0; i < 3; i++) {
			if (!yaos.get(i).isCoopration(yaos.get(i+3))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if is six opposition.
	 * note: the method can only invoked after the Diagram has init(has branchs).
	 * @return true, if is six opposition
	 */
	public boolean isSixOpposition() {
		for (int i = 0; i < 3; i++) {
			if (!yaos.get(i).isOpposition(yaos.get(i + 3))) {
				return false;
			}
		}
		return true;
	}

	public String getBelongName() {
		return AncientDiagrams.EightDiagramsName[orgin];
	}

	public void setSelf(int self) {
		this.self = self;
	}

	public int getSelf() {
		return self;
	}

	/**
	 * 获取变卦
	 * gets the changed diagrams.
	 * @return
	 */
	public EightDiagrams getChangedDiagrams() {
		EightDiagrams changed = null;
		if (isChanged()) {
			Yao[] newYaos = new Yao[6];
			// FourQuadrant[] newYaos=new FourQuadrant[6];
			for (int i = 0; i < 6; i++) {
				newYaos[i] = yaos.get(i).getChangedYao();
			}
			changed = new EightDiagrams(newYaos);
			changed.setOrgin(orgin);
			changed.setup();
		}
		return changed;
	}

	/**
	 * @param orgin
	 */
	private void setOrgin(int orgin) {
		this.orgin = orgin;
	}

	/**
	 * @param i
	 * @return
	 */
	public Yao getYao(int i) {
		return yaos.get(i);
	}

	/**
	 * 获取伏神
	 * 
	 * @return the hide yaos
	 */
	private void setHideYaos() {
		List<Yao> hideYaos;
		List<SixRelatives> hideRelatives = getHideRelatives();
		if (hideRelatives.size() == 0) {
			hideYaos = new ArrayList<Yao>(0);
			return;
		}
		hideYaos = new ArrayList<Yao>(2);

		// get the yaos from orgin Gong;
		AncientDiagrams diagrams = AncientDiagrams.getDiagrams(orgin);
		FourQuadrant[] newYaos = new FourQuadrant[6];
		for (int i = 0; i < 6; i++) {
			newYaos[i] = diagrams.getYao(i % 3);
		}
		// this is the orgin Gong
		EightDiagrams eightDiagrams = new EightDiagrams(newYaos);
		eightDiagrams.setup();

		for (int i = 0; i < 6; i++) {
			Yao yao = eightDiagrams.getYao(i);
			for (Iterator it = hideRelatives.iterator(); it.hasNext();) {
				SixRelatives sixRelatives = (SixRelatives) it.next();
				if (yao.getRelative().equals(sixRelatives)) {
					yao.setLevel(Level.HIDE_LEVEL);
					hideYaos.add(yao);
				}
			}
		}
		yaos.addAll(hideYaos);
	}

	/**
	 * @return
	 */
	public boolean hasHideYao() {
		return yaos.size() >6;
	}

	/**
	 * 获取没有出现的六亲
	 * Note: this method must be invoke after the diagram has setup.
	 * @return true, if successful
	 */
	private List<SixRelatives> getHideRelatives() {
		List<SixRelatives> hideRelatives = new ArrayList<SixRelatives>();
		for (Iterator<SixRelatives> iterator = SixRelatives.iterator(); iterator
				.hasNext();) {
			boolean flag=true;
			SixRelatives relatives = iterator.next();
			for (int i = 0; i < yaos.size(); i++) {
				if (relatives.equals(yaos.get(i).getRelative())) {
					flag=false;
					break;
				}
			}
			if(flag)
			hideRelatives.add(relatives);
		}
		if (hideRelatives.size() == 0) {
			hideRelatives = new ArrayList<SixRelatives>(0);
		}
		return hideRelatives;
	}

	
	public List<Yao> getYaos(Level l){
		List<Yao> yaoList=new ArrayList<Yao>();
		for(Yao y:yaos){
			if (y.getLevel().equals(l)) {
				yaoList.add(y);
			}
		}
		
		
		return yaoList;
	}

//	public static void main(String[] args) throws DataException {
//		EightDiagrams diagrams  = new EightDiagrams(new FourQuadrant[] {// 兑宫：水山蹇   2爻妻财伏
//				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
//				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
//				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
//		diagrams.setup();
//		System.out.println(diagrams);
//		// String[] strings = LiuYao.parseDiagram(diagrams);
//		// for (int i = strings.length - 1; i >= 0; i--) {
//		// System.out.println(strings[i]);
//		// }
//
//		List<Yao> yaos = diagrams.getHideYaos();
//		for (Iterator<Yao> iterator = yaos.iterator(); iterator.hasNext();) {
//			Yao yao = iterator.next();
//			System.out.println(yao.getIndex()+"爻 "+yao);
//		}
//	}

}
