package com.test.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <B>议程类<B>
 * <P>
 * 每个议程类定义了发起人、参与人、议程开始时间和结束时间.
 * <p>
 * 说明: 每个议程可以有多于两个人参加.
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-10 新建
 * @since agenda Ver 1.0
 * 
 */
public class AgendaData {

	private Date start;// 议程开始时间

	private Date end;// 议程结束时间

	private List<String> other;// 被预约人

	private String title;// 议程标签

	public AgendaData(String other,Date start, Date end,  String title) {
		this.other=new ArrayList<String>();
		this.start = start;
		this.end = end;
		this.other.add(other);
		this.title = title;
	}

	public AgendaData(String start,String end,String other,String title){
		
	}
	
	public void addOther(String other){
		this.other.add(other);
	}
	
	/**
	 * 获取议程结束时间
	 * 
	 * @return end 返回议程结束时间
	 */
	public Date getEnd() {
		return end;
	}
	/**
	 * 设置议程结束时间
	 * 
	 * @param end
	 *            议程结束时间按
	 */
	public void setEnd(Date end) {
		this.end = end;
	}

	/**
	 * @return other
	 */
	public List<String> getOther() {
		return other;
	}

	/**
	 * @param other 要设置的 other
	 */
	public void setOther(List<String> other) {
		this.other = other;
	}

	/**
	 * @return start
	 */
	public Date getStart() {
		return start;
	}

	/**
	 * @param start 要设置的 start
	 */
	public void setStart(Date start) {
		this.start = start;
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title 要设置的 title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	

	
	
}
