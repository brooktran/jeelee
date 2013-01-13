/* @(#)Item.java 1.0 2009-11-10
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.jtool.sample.job;

import java.util.Calendar;

/**
 * <B>Item</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-10 created
 * @since JobLister Ver 1.0
 * 
 */
public class Item {

    private String author;
    private Calendar startDate;
    private boolean isModify = false;
    private String gameName;
    private String meno = "";
    private boolean isFinish = false;
    private Calendar endDate;
    private Calendar finishDate;

    /**
     *
     * @since JobLister
     */
    //	public Item(String author,Calendar startDate,
    //			boolean isModify,String gameName,String meno) {
    //		this.author=author;
    //		this.startDate=startDate;
    //		this.isModify=isModify;
    //		this.gameName=gameName;
    //		this.meno=meno;
    //
    //	}
    /**
     *
     * @since JobLister
     */
    public Item() {
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * @return the calendar
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * @param calendar the calendar to set
     */
    public void setStartDate(Calendar calendar) {
        startDate = calendar;
    }

    /**
     * @return the isModify
     */
    public boolean isModify() {
        return isModify;
    }

    /**
     * @param isModify the isModify to set
     */
    public void setModify(boolean isModify) {
        this.isModify = isModify;
    }

    /**
     * @return the gameName
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * @param gameName the gameName to set
     */
    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    /**
     * @param meno the meno to set
     */
    public void setMeno(String meno) {
        this.meno = meno;
    }

    /**
     * @return the meno
     */
    public String getMeno() {
        return meno;
    }

    /**
     * @param isFinish the isFinish to set
     */
    public void setFinish(boolean isFinish) {
        this.isFinish = isFinish;
    }

    /**
     * @return the isFinish
     */
    public boolean isFinish() {
        return isFinish;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Calendar endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the endDate
     */
    public Calendar getEndDate() {
        return endDate;
    }

    /**
     * @param finishDate the finishDate to set
     */
    public void setFinishDate(Calendar finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * @return the finishDate
     */
    public Calendar getFinishDate() {
        return finishDate;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(author + ",");
        sb.append(gameName + ",");
        sb.append((startDate.get(Calendar.MONTH) + 1) + "-" + startDate.get(Calendar.DATE) + ",");
        sb.append((endDate.get(Calendar.MONTH) + 1) + "-" + endDate.get(Calendar.DATE) + ",");
      
        sb.append((isFinish ? "是" : "") + ",");

        if(finishDate == null){
            sb.append( ",");
        }else{
            sb.append((getFinishDate().get(Calendar.MONTH) + 1) + "-" + getFinishDate().get(Calendar.DATE) + ",");
        }
          sb.append((isModify ? "是" : "") + ",");
        sb.append(meno);

        return sb.toString();
    }
}
