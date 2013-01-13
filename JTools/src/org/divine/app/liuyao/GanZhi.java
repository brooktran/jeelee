/* GanZhi.java 1.0 2010-2-2
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
 * <B>GanZhi</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-27 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class GanZhi  {
	private HeavenlyStem stem;// 天干
	private EarthlyBranch branch;// 地支
	private final EarthlyBranch[] emptys = new EarthlyBranch[2];// 空亡
	
	/**
	 * @param cyclical
	 */
	public GanZhi(HeavenlyStem stem,EarthlyBranch branch) {
		this.stem = stem;
		this.branch = branch;
		
		setup();
	}
	
	/**
	 * 
	 */
	public GanZhi() {
	}

	/**
	 * 
	 */
	private void setup() {
		setEmpty();
	}

	/**
	 * 
	 */
	private void setEmpty() {
		int p=(branch.value+11-stem.value)%12;
		emptys[0]=EarthlyBranch.getbranch(p-1);
		emptys[1]=EarthlyBranch.getbranch(p);
	}

	public static GanZhi parseString(String stemAndBranch){
		if(stemAndBranch.length()!=2){
			throw new IllegalArgumentException(stemAndBranch+" must like 甲子.");
		}
		return new  GanZhi(HeavenlyStem.parseString(stemAndBranch.substring(0, 1)), 
						EarthlyBranch.parseString(stemAndBranch.substring(1)));
	}
	
	public GanZhi next(int i){
		return new GanZhi(stem.next(i), branch.next(i));
	}
	
	void setBranch(EarthlyBranch branch) {
		this.branch = branch;
	}

	/**
	 * @param next
	 */
	private void setStem(HeavenlyStem stem) {
		this.stem = stem;
	}

	public EarthlyBranch getBranch() {
		return branch;
	}

	/**
	 * @return
	 */
	private HeavenlyStem getStem() {
		return stem;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj instanceof GanZhi) {
			if (obj == this) {
				return true;
			}
			GanZhi other=(GanZhi)obj;
			if(other.getBranch().equals(this.branch)&&other.getStem().equals(this.stem)){
				return true;
			}
		}
		return false;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s="";
		if(stem!=null){
			s+=stem.getName();
		}
		return branch==null?s:s+branch.getName();
	}

	/**
	 * @return
	 */
	public EarthlyBranch[] getEmpty() {
		return emptys;
	}
	
	public boolean isEmpty(EarthlyBranch branch){
		for(int i=0,j=emptys.length;i<j;i++){
			if (branch.equals(emptys[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @return
	 */
	public HeavenlyStem getSterm() {
		return stem;
	}

	/**
	 * @param jia
	 */
	public void setSterm(HeavenlyStem sterm) {
		this.stem=sterm;
	}

	/**
	 * @return
	 */
	public FiveElement getFiveElement() {
		return branch.getElement();
	}
	
	

}
