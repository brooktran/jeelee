/* Player.java 1.0 2010-2-2
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
package org.chess.game;

/**
 * <B>Player</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public abstract class AbstractPlayer implements Player{
	protected int ID;
	protected String name;

	public AbstractPlayer(int ID, String name) {
		this.ID =ID;
		this.name = name;
	}

	@Override
	public int getID() {
		return ID;
	}
	
	@Override
	public void setID(int iD) {
		ID = iD;
	}


}
