package org.divine.app.liuyao;

import java.util.ArrayList;
import java.util.List;

/**
 * <B>FourQuadrant</B> 四象
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-17 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class FourQuadrant extends Taiji{
	/** 太阳 太阴 少阳 少阴*/
	public static int SHAO_YANG = 0;
	/** 太阳 太阴 少阳 少阴*/
	public static int SHAO_YIN = 1;
	/** 太阳 太阴 少阳 少阴*/
	public static int TAI_YANG = 2;
	/** 太阳 太阴 少阳 少阴*/
	public static int TAI_YIN = 3;
	/**
	 * 	 太阳  太阴      少阴	
	 */

	
	public static final String Transform = "━＞";
	
	private static final List<FourQuadrant> QUADRANTS;
	static{
		QUADRANTS=new ArrayList<FourQuadrant>(4);
		QUADRANTS.add(new FourQuadrant(SHAO_YANG, "　▅　"));
		QUADRANTS.add(new FourQuadrant(SHAO_YIN,  "▅　▅"));
		QUADRANTS.add(new FourQuadrant(TAI_YANG,  "　◎　"));
		QUADRANTS.add(new FourQuadrant(TAI_YIN,   "　╳　"));
	}
	
	
	private FourQuadrant(int v, String name) {
		super(v, name);
	}
	
	

	// public enum Quadrant{
	// ShaoYang,ShaoYin,TaiYang,TaiYin
	// }
	// private Quadrant label;


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof FourQuadrant) {
			if (((FourQuadrant) obj).getValue() == value) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 阳动化为阳
	 * @param f
	 * @return
	 */
	public FourQuadrant toTwoForms() {
		if (value == TAI_YANG) {
			return getFourQuadrant(SHAO_YANG);
		} else if (value == TAI_YIN){
			return getFourQuadrant(SHAO_YIN);
		} else if (value == SHAO_YANG || value == SHAO_YIN) {
			return this;
		} else {
			return null;
		}
	}

	/**
	 * @param sHAOYIN
	 * @return
	 */
	public static FourQuadrant getFourQuadrant(int value) {
		return QUADRANTS.get(value);
	}



	/**
	 * 返回变爻:阳动返回少阴
	 * @return
	 */
	public FourQuadrant getChanged() {
		if(value ==TAI_YANG){
			return getFourQuadrant(SHAO_YIN);
		}
		if (value == TAI_YIN) {
			return getFourQuadrant(SHAO_YANG);
		}
		return new FourQuadrant(value, name);
	}

	/**
	 * @return
	 */
	public String getLabel() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * @return
	 */
	public boolean isChanged() {
		return (value==TAI_YANG ||value==TAI_YIN);
	}

	/**
	 * 阳转为阴,阴转为阳;
	 * @return
	 */
	public FourQuadrant reverse() {
		if(isChanged()){
			throw new IllegalArgumentException();
		}
		return getFourQuadrant((value+1)%2);
	}


}
