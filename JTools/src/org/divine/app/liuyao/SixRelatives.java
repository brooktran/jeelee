package org.divine.app.liuyao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <B>SixRelatives</B> is the relationship of two FiveElement.
 * 六亲
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-18 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public class SixRelatives  extends Taiji{
	public static final int FATHER=0;
	public static final int BROTHER=1;
	public static final int SON=2;
	public static final int WIFE=3;
	public static final int GHOST=4;
	private static final List<SixRelatives> SIX_RELATIVES;
	static{
		SIX_RELATIVES=new ArrayList<SixRelatives>(8);
		SIX_RELATIVES.add(new SixRelatives(0, "父母"));
		SIX_RELATIVES.add(new SixRelatives(1, "兄弟"));
		SIX_RELATIVES.add(new SixRelatives(2, "子孙"));
		SIX_RELATIVES.add(new SixRelatives(3, "妻财"));
		SIX_RELATIVES.add(new SixRelatives(4, "官鬼"));
	}

	private SixRelatives(int v,String n){
		super(v, n);
	}
	
	public static Iterator<SixRelatives> iterator(){
		return SIX_RELATIVES.iterator();
	}
	
	public static SixRelatives getRelatives(int value){
		return SIX_RELATIVES.get(value);
	}
}
