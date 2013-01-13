/* MilitaryMode.java 1.0 2010-2-2
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

import org.chess.game.AbstractGameMode;
import org.zhiwu.app.config.AppPreference;

/**
 * <B>MilitaryMode</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class MilitaryMode extends AbstractGameMode{

	public static String NAME ="military";

	public MilitaryMode(AppPreference pref) {
		super(pref);
	}

}