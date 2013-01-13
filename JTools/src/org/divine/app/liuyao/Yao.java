/* Yao.java 1.0 2010-2-2
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.event.EventListenerList;

import org.divine.app.liuyao.resolve.AbstractItem;
import org.divine.app.liuyao.resolve.Item;
import org.divine.app.liuyao.resolve.ItemEvent;
import org.divine.app.liuyao.resolve.ItemListener;
import org.divine.app.liuyao.resolve.Level;
import org.divine.app.liuyao.resolve.relationship.IRelationship;
import org.divine.app.liuyao.resolve.relationship.RelationshipFactory;

/**
 * <B>Yao</B>.
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-7 created
 * @since org.divine.app.liuyao Ver 1.0
 */
public class Yao extends AbstractItem {
	
	/** The branch. */
	protected EarthlyBranch branch;
	protected FourQuadrant fourQuadrant;
	/** The relative. */
	protected SixRelatives relative;
	
	protected List<IRelationship> relationships;
	
	/** 是否伏神  */
	private final boolean isHide;
	private LiuYao liuYao;
	protected int index;
	
	
	private final EventListenerList listenerList= new EventListenerList();
	
	/**
	 * 
	 */
	public Yao() {
		isHide=false;
		initStrength();
		setLevel(Level.YAO_LEVEL);
	}
	
	public Yao(LiuYao liuYao){
		this();
		this.liuYao=liuYao;
	}
	
	/**
	 * @param i
	 * @param fourQuadrant
	 */
	public Yao(int index, FourQuadrant fourQuadrant) {
		this();
		this.index=index;
		this.setFourQuadrant(fourQuadrant);
	}

	/**
	 * 
	 */
	protected void initStrength() {
		relationships=new ArrayList<IRelationship>(64);
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the branch
	 */
	@Override
	public EarthlyBranch getBranch() {
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch(EarthlyBranch branch) {
		this.branch = branch;
	}

	/**
	 * @return the relative
	 */
	public SixRelatives getRelative() {
		return relative;
	}

	/**
	 * @param relative the relative to set
	 */
	public void setRelative(SixRelatives relative) {
		this.relative = relative;
	}

	/**
	 * @param fourQuadrant the fourQuadrant to set
	 */
	public void setFourQuadrant(FourQuadrant fourQuadrant) {
		this.fourQuadrant = fourQuadrant;
	}

	/**
	 * @return the fourQuadrant
	 */
	public FourQuadrant getFourQuadrant() {
		return fourQuadrant;
	}

	/**
	 * @param yao
	 * @return
	 */
	public boolean isCoopration(Yao other) {
		return branch.isCoopration(other.getBranch());
	}

	/**
	 * @return
	 */
	public boolean isChange() {
		return fourQuadrant.isChanged();
	}

	/**
	 * @param yao
	 * @return
	 */
	public boolean isOpposition(Yao other) {
		return branch.isOpposition(other.getBranch());
	}

	/**
	 * @return
	 */
	public FourQuadrant getChangedFourQuadrant() {
		return fourQuadrant.getChanged();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return fourQuadrant.getLabel()+relative+branch;
	}

	/**
	 * @return
	 */
	public Yao getChangedYao() {
		Yao changed;
		if(isChange()){
			changed= new Yao(index,fourQuadrant.getChanged());
			changed.setLevel(Level.CHANGED_LEVEL);
			return changed;
		}
		return new Yao(index, fourQuadrant);
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getLevel()
	 */
//	public Level getLevel() {
//		if(isChange()){
//			return Level.CHANGE_LEVEL;
//		}
//		return Level.YAO_LEVEL;
//	}

	/**
	 * @param pillar
	 */
	@Override
	public boolean addRelation(Item item) {
		
		if(RelationshipFactory.getAllRelationship(item,this)){
			return true;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getFiveElement()
	 */
	@Override
	public FiveElement fiveElement() {
		return branch.getElement();
	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#addStrength(org.divine.app.liuyao.resolve.Wang)
	 */
	@Override
	public void addRelationship(IRelationship r) {
		relationships.add(r);
		
		fireRelationshipChanged(r);
	}

	/**
	 * @param r
	 */
	private void fireRelationshipChanged(IRelationship r) {
		ItemEvent e=new ItemEvent(this,r);
		Object[] listeners=listenerList.getListenerList();
		for(int i=0,j=listeners.length;i<j;i+=2){
			if(listeners[i] == ItemListener.class){
				((ItemListener)listeners[i+1]).relationshipChanged(e);
			}
		}
	}

	/**
	 * @return
	 */
	public String getRelationString() {
		StringBuffer sb=new StringBuffer();
		for (Iterator<IRelationship> i = relationships.iterator(); i.hasNext();) {
			IRelationship s = i.next();
			sb.append(s.getLabel());
			sb.append(",");
		}
		return sb.toString();
	}
	
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#strengthIteator()
	 */
	@Override
	public Iterator<IRelationship> strengthIteator() {
		return relationships.iterator();
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractItem#hasPower()
	 */
	@Override
	public boolean hasPower() {
		for(IRelationship r:relationships){
			if(!r.isLowAvailable()){
				return false;
			}
		}
		
		
		if(strength <0){
			return false;
//			hasPower= false;
		}
		
		return true;
		
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractItem#hasRelationship(java.lang.Class, org.divine.app.liuyao.resolve.Level)
	 */
	@Override
	public boolean hasRelationship(Class<? extends IRelationship> c, Level level) {
		return getRelationship(c, level) != null;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractItem#hasRelationship(java.lang.Class)
	 */
	@Override
	public boolean hasRelationship(Class<? extends IRelationship> c) {
			return getRelationship(c) != null;
	}
	
	public IRelationship getRelationship(Class<? extends IRelationship> c){
		for(IRelationship r:relationships){
			if (r.getClass() .equals( c)) {
				return r;
			}
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getRelationship(java.lang.Class, org.divine.app.liuyao.resolve.Level)
	 */
	@Override
	public IRelationship getRelationship(Class<? extends IRelationship> c, Level level) {
		for(IRelationship r:relationships){
			if ((r.getClass() .equals( c))   && 
					(r.getSource().getLevel().isEqual(level))) {
				return r;
			}
		}
		return null;	}

	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#addYaoListener(org.divine.app.liuyao.resolve.ItemListener)
	 */
	@Override
	public void addItemListener(ItemListener l) {
		listenerList.add(ItemListener.class, l);
	}
	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.AbstractItem#getLabel()
	 */
	@Override
	public String getLabel() {
		return super.getLabel()+"("+(index+1)+")"+getBranch();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj== null) {
			return false;
		}
		
		if(!(obj instanceof Yao)){
			return false;
		}
		
		Yao other=(Yao)obj;
		if(other.isHide == isHide && other.branch.equals(branch)
				&& other.fourQuadrant.equals(fourQuadrant) && other.index==index){
			return true;
		}
		return false;
		
	}

	
	/* (non-Javadoc)
	 * @see org.divine.app.liuyao.resolve.Item#getStrength()
	 */
//	public IRelationship getStrength() {
//		return strength;
//	}
	
}
