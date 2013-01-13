/* GameModeFactory.java 1.0 2010-2-2
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

import org.chess.military.MilitaryMode;
import org.chess.renju.color.ColorRenjuMode;
import org.chess.renju.five.FiveRenjuMode;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;

/**
 * <B>GameModeFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-6 created
 * @since chess Ver 1.0
 * 
 */
public class GameModeFactory {

	public static GameMode getGameMode(String modeName) {
		AppPreference pref = AppManager.getPreference(modeName);
		GameMode mode =null;
		if(modeName.equals(FiveRenjuMode.NAME)){
			mode = new FiveRenjuMode(pref);
		}else if (modeName.equals(MilitaryMode.NAME)) {
			mode = new MilitaryMode(pref);
		}else {
			mode = new ColorRenjuMode(pref);
		}
		
		mode.init();
		
		if(modeName.equals(ColorRenjuMode.RANDOM)){
			((ColorRenjuMode)mode).random(); 
		}
		
		return mode;
	}

}
