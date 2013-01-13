package org.chenzhw.beans;

/**
 * <B> StallBeans </B>
 * <p>
 * The java bean of the Stall game.
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-4-6 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class StallBeans {
	
	/** The total income of one minute.*/
	private int perMinute;
	
	public StallBeans(int perMinute){
		this.perMinute=perMinute;
	}
	
	/** Get the total income of twelve hours.*/
	public int getTwelveHour(){
		return perMinute*60*12;
	}
	
	/** Get the total income of one hours.*/
	public int getOneHour(){
		return perMinute*60;
	}
	
	public int getTime(int expectMoney){
		return expectMoney/perMinute;
	}
}
