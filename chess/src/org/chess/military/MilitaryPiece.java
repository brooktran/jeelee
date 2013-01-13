/* MilitrayPiece.java 1.0 2010-2-2
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
package org.chess.military;

import org.chess.game.Piece;

/**
 * <B>MilitrayPiece</B>.
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 */
public class MilitaryPiece extends Piece{

	public MilitaryPiece(int id, String name) {
		setID(id);
		setName(name);
	}
	
	
	/** 雷 */
	public static final int VALUE_MINE							=1;
	/**  兵*/
	public static final int VALUE_ENGINEER						=2;
	/** 排 */
	public static final int VALUE_PLATOON_COMMANDER				=3;
	/** 连 */
	public static final int VALUE_COMPANY_COMMANDER				=4;
	/** 营 */
	public static final int VALUE_BATTALION_COMMANDER			=5;
	/** 团 */
	public static final int VALUE_REGIMENT_COMMANDER			=6;
	/** 旅 */
	public static final int VALUE_BRIGADIER_COMMANDER			=7;
	/** 师 */
	public static final int VALUE_DIVISION_COMMANDER			=8;
	/** 军 */
	public static final int VALUE_ARMY_COMMANDER				=9;
	/** 司 */
	public static final int VALUE_HEAD_COMMANDER				=10;
	/** 炸 */
	public static final int VALUE_BOMB							=11;
	/** 旗 */
	public static final int VALUE_FLAG							=12;
	
	public static final MilitaryPiece MINE					=new MilitaryPiece(VALUE_MINE,"雷");
	public static final MilitaryPiece ENGINEER				=new MilitaryPiece(VALUE_ENGINEER,"兵");
	public static final MilitaryPiece PLATOON_COMMANDER		=new MilitaryPiece(VALUE_PLATOON_COMMANDER,"排");
	public static final MilitaryPiece COMPANY_COMMANDER		=new MilitaryPiece(VALUE_COMPANY_COMMANDER,"连");
	public static final MilitaryPiece BATTALION_COMMANDER	=new MilitaryPiece(VALUE_BATTALION_COMMANDER,"营");
	public static final MilitaryPiece REGIMENT_COMMANDER	=new MilitaryPiece(VALUE_REGIMENT_COMMANDER,"团");
	public static final MilitaryPiece BRIGADIER_COMMANDER	=new MilitaryPiece(VALUE_BRIGADIER_COMMANDER,"旅");
	public static final MilitaryPiece DIVISION_COMMANDER	=new MilitaryPiece(VALUE_DIVISION_COMMANDER,"师");
	public static final MilitaryPiece ARMY_COMMANDER		=new MilitaryPiece(VALUE_ARMY_COMMANDER,"军");
	public static final MilitaryPiece HEAD_COMMANDER		=new MilitaryPiece(VALUE_HEAD_COMMANDER,"司");
	public static final MilitaryPiece BOMB					=new MilitaryPiece(VALUE_BOMB,"炸");
	public static final MilitaryPiece FLAG					=new MilitaryPiece(VALUE_FLAG,"旗");
	
	
}
