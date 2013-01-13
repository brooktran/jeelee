/* EarthlyBranch.java 1.0 2010-2-2
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

/**
 * <B>EarthlyBranch</B>
 * 地支.
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-18 created
 * @since org.jtools.app Ver 1.0
 */
public class EarthlyBranch extends Taiji {
	
	/** The five element. */
	private final FiveElement fiveElement;// 地支所属五行

	/**
	 * Instantiates a new earthly branch.
	 * 
	 * @param v the v
	 * @param n the n
	 * @param f the f
	 */
	private EarthlyBranch(int v, String n, FiveElement f) {
		super(v, n);
		fiveElement = f;
	}

	/** The Constant EarthlyBranchNames. */
	private static final String[] EarthlyBranchNames=
		{"子","丑","寅","卯","辰","巳","午","未","申","酉","戌","亥"};
	
	/** The Constant ZI. */
	public static final int ZI = 0;
	
	/** The Constant CHOU. */
	public static final int CHOU = 1;
	
	/** The Constant YIN. */
	public static final int YIN = 2;
	
	/** The Constant MAO. */
	public static final int MAO = 3;
	
	/** The Constant CHEN. */
	public static final int CHEN = 4;
	
	/** The Constant SI. */
	public static final int SI = 5;
	
	/** The Constant WU. */
	public static final int WU = 6;
	
	/** The Constant WEI. */
	public static final int WEI = 7;
	
	/** The Constant SHEN. */
	public static final int SHEN = 8;
	
	/** The Constant YOU. */
	public static final int YOU = 9;
	
	/** The Constant XU. */
	public static final int XU = 10;
	
	/** The Constant GAI. */
	public static final int GAI = 11;

	/** The Constant Zi. */
	public static final EarthlyBranch Zi = new EarthlyBranch(ZI, "子",
			FiveElementFactory.createElement(FiveElement.WATER));
	
	/** The Constant Chou. */
	public static final EarthlyBranch Chou = new EarthlyBranch(CHOU, "丑",
			FiveElementFactory.createElement(FiveElement.SOIL));
	
	/** The Constant Yin. */
	public static final EarthlyBranch Yin = new EarthlyBranch(YIN, "寅",
			FiveElementFactory.createElement(FiveElement.WOOD));
	
	/** 卯卦 */
	public static final EarthlyBranch Mao = new EarthlyBranch(MAO, "卯",
			FiveElementFactory.createElement(FiveElement.WOOD));
	
	/** 辰. */
	public static final EarthlyBranch Chen = new EarthlyBranch(CHEN, "辰",
			FiveElementFactory.createElement(FiveElement.SOIL));
	
	/** 巳. */
	public static final EarthlyBranch Si = new EarthlyBranch(SI, "巳",
			FiveElementFactory.createElement(FiveElement.FIRE));
	
	/** 午. */
	public static final EarthlyBranch Wu = new EarthlyBranch(WU, "午",
			FiveElementFactory.createElement(FiveElement.FIRE));
	
	/** The Constant Wei. */
	public static final EarthlyBranch Wei = new EarthlyBranch(WEI, "未",
			FiveElementFactory.createElement(FiveElement.SOIL));
	
	/** The Constant Shen. */
	public static final EarthlyBranch Shen = new EarthlyBranch(SHEN, "申",
			FiveElementFactory.createElement(FiveElement.GOLD));
	
	/** The Constant You. */
	public static final EarthlyBranch You = new EarthlyBranch(YOU, "酉",
			FiveElementFactory.createElement(FiveElement.GOLD));
	
	/** The Constant Xu. */
	public static final EarthlyBranch Xu = new EarthlyBranch(XU, "戌",
			FiveElementFactory.createElement(FiveElement.SOIL));
	
	/** The Constant Gai. */
	public static final EarthlyBranch Gai = new EarthlyBranch(GAI, "亥",
			FiveElementFactory.createElement(FiveElement.WATER));
	
	/** The Constant BRANCHS. */
	private static final EarthlyBranch[] BRANCHS=new EarthlyBranch[]{Zi,Chou,Yin,Mao,Chen,Si,Wu,Wei,Shen,You,Xu,Gai};
	
	/**
	 * Parses the string.
	 * 
	 * @param name the name
	 * 
	 * @return the earthly branch
	 */
	public static EarthlyBranch parseString(String name){
		if(name.length()!=1){
			throw new IllegalArgumentException(name+"  is illegal for the argument.");
		}
		for(int i=0,j=EarthlyBranchNames.length;i<j;i++){
			if (name .equals( EarthlyBranchNames[i] )) {
				return BRANCHS[i];
			}
		}
		return null;
	}

	/**
	 * Next.
	 * 
	 * @return the earthly branch
	 */
	public EarthlyBranch next() {
		return BRANCHS[(++value)%10];
	}

	/**
	 * Getbranch.
	 * 
	 * @param value the value
	 * 
	 * @return the branch
	 */
	public static EarthlyBranch getbranch(int value) {
		return BRANCHS[value%12];
	}

	/**
	 * Next.
	 * 
	 * @param i the i
	 * 
	 * @return the earthly branch
	 */
	public EarthlyBranch next(int i) {
		return BRANCHS[Math.abs((value+i+12)%12)];
	}

	/**
	 * Checks if is opposition.
	 * 是否六冲
	 * 
	 * @param other the other
	 * 
	 * @return true, if checks if is opposition
	 */
	public boolean isOpposition(EarthlyBranch other) {
		if (other.value ==((value+6)%12)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if is coopration.
	 * 
	 * @param other the other
	 * 
	 * @return true, if checks if is coopration
	 */
	public boolean isCoopration(EarthlyBranch other) {
		if((other.value+value)%12==1){
			return true;
		}
		return false;
	}

	/**
	 * Gets the element.
	 * 
	 * @return the element
	 */
	public FiveElement getElement() {
		return fiveElement;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.Taiji#toString()
	 */
	@Override
	public String toString() {
		return name+fiveElement.getName();
	}

}
