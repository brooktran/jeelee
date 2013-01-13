/* AncientDiagrams.java 1.0 2010-2-2
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

import java.util.Arrays;

/**
 * <B>AncientDiagrams</B>
 * 后天八卦
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-29 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public abstract class AncientDiagrams {
	/** 是否外挂 */
	protected boolean isOut = false;

	private int ID;
	private String name;
	
	protected FourQuadrant[] yaos;
	protected int orgin;
	protected EarthlyBranch[] branchs;
	protected static final String[] EightDiagramsName = { "乾", "坎", "艮", "震",
			"巽", "离", "坤", "兑" };
	public static final int QIAN = 0;
	public static final int KAN = 1;
	public static final int GEN = 2;
	public static final int ZHEN = 3;
	public static final int XUN = 4;
	public static final int LI = 5;
	public static final int KUN = 6;
	public static final int DUI = 7;
	protected static final AncientDiagrams[] EIGHTDIAGRAM_DIAGRAMS = new AncientDiagrams[] {
			new Qian(), new Kan(), new Gen(), new Zhen(), new Xun(), new Li(),
			new Kun(), new Dui() };

	/**
	 * 
	 */
	public AncientDiagrams(FourQuadrant[] sixYaos) {
		setValues(sixYaos);
	}
	
	public static AncientDiagrams getDiagrams(int value){
		return EIGHTDIAGRAM_DIAGRAMS[value];
	}

	public static AncientDiagrams parseDiagram(FourQuadrant[] diagram) {
		for (int i = 0, j = EIGHTDIAGRAM_DIAGRAMS.length; i < j; i++) {
			if (Arrays.equals(diagram, EIGHTDIAGRAM_DIAGRAMS[i].yaos)) {
				Class<AncientDiagrams> diaClass=(Class<AncientDiagrams>) EIGHTDIAGRAM_DIAGRAMS[i].getClass();
				try {
					return diaClass.newInstance();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
//				return EIGHTDIAGRAM_DIAGRAMS[i];
			}
		}
		return null;
	}

	public FourQuadrant getYao(int i) {
		return yaos[i];
	}


	
	protected void setValues(FourQuadrant[] sixYaos) {
		this.yaos = sixYaos;
	}
public void setOut(boolean b) {
		isOut = b;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
//		if (branchs!=null) {
//			for(int i=0;i<branchs.length;i++){
//				sb.append(branchs[i].getName()+"\n");
//			}
//		}
		for(int i=yaos.length-1;i>=0;i--){
			sb.append(yaos[i].getName()+"\n");
		}
		
		return sb.toString();
	}

	/** 装地支
	 * 
	 * 乾震子午坎寅申 艮上辰戌顺行真 
	 * 巽木丑未离卯酉 坤未丑兑巳亥循 
	 * @return
	 */
	public abstract EarthlyBranch getSetupBranch();
	
	/**
	 * Opposite.
	 * revert every form of the yaos
	 * @return the abs eight diagrams
	 */
	public AncientDiagrams opposite(){
		FourQuadrant[] yaos=new FourQuadrant[3];
		for(int i=0;i<3;i++){
			yaos[i]=getYao(i).reverse();
		}
		
		return AncientDiagrams.parseDiagram(yaos);
	}
	public abstract int getValue();
	/** return FourQuadrant.SHAO_YANG or SHAO_YIN
	 * @return
	 * @see org.divine.app.liuyao.TwoForm
	 */
	public abstract int getTwoForm();

	/**
	 * @return the five_element of this diagram.
	 */
	public abstract FiveElement getElement();

}


/** 乾*/
class Qian extends AncientDiagrams {

	/**
	 * 
	 */
	public Qian() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.Wu;
		}
		return EarthlyBranch.Zi;
	}

	@Override
	public int getValue() {
		return AncientDiagrams.QIAN;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YANG;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Gold();
	}

}

/** 坎*/
class Kan extends AncientDiagrams {
	public Kan() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.Shen;
		}
		return EarthlyBranch.Yin;

	}

	@Override
	public int getValue() {
		return AncientDiagrams.KAN;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YANG;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Water();
	}

}

/** 艮*/
class Gen extends AncientDiagrams {

	/**
	 * 
	 */
	public Gen() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.Xu;
		}
		return EarthlyBranch.Chen;
	}


	@Override
	public int getValue() {
		return AncientDiagrams.GEN;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YANG;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Soil();
	}

}

/** 震*/
class Zhen extends AncientDiagrams {

	/**
	 * 
	 */
	public Zhen() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.Wu;
		}
		return EarthlyBranch.Zi;
	}

	@Override
	public int getValue() {
		return AncientDiagrams.ZHEN;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YANG;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Wood();
	}

}

/** 离*/
class Li extends AncientDiagrams {
	/**
	 * 
	 */
	public Li() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.You;
		}
		return EarthlyBranch.Mao;
	}

	@Override
	public int getValue() {
		return AncientDiagrams.LI;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YIN;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Fire();
	}
}

/** 坤*/
class Kun extends AncientDiagrams {

	/**
	 * 
	 */
	public Kun() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.Chou;
		}
		return EarthlyBranch.Wei;
	}


	@Override
	public int getValue() {
		return AncientDiagrams.KUN;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YIN;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Soil();
	}
}

/** 兑*/
class Dui extends AncientDiagrams {

	/**
	 * 
	 */
	public Dui() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if(isOut){
			return EarthlyBranch.Gai;
		}
		return EarthlyBranch.Si;
	}

	@Override
	public int getValue() {
		return AncientDiagrams.DUI;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YIN;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Gold();
	}

}

/** 巽*/
class Xun extends AncientDiagrams {

	/**
	 * 
	 */
	public Xun() {
		super(new FourQuadrant[] { FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG),
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG) });
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.AncientDiagrams#inBranch()
	 */
	@Override
	public EarthlyBranch getSetupBranch() {
		if (isOut) {
			return EarthlyBranch.Wei;
		}
		return EarthlyBranch.Chou;
	}

	@Override
	public int getValue() {
		return AncientDiagrams.XUN;
	}


	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getTwoForm()
	 */
	@Override
	public int getTwoForm() {
		return FourQuadrant.SHAO_YIN;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.AncientDiagrams#getElement()
	 */
	@Override
	public FiveElement getElement() {
		return new Wood();
	}

}