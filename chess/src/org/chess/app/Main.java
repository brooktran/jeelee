/* Main.java 1.0 2010-2-2
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
package org.chess.app;

import org.zhiwu.app.Application;
import org.zhiwu.app.ApplicationRunner;
import org.zhiwu.app.DefaultModel;
import org.zhiwu.app.Model;

/**
 * <B>Main</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.app Ver 1.0
 * 
 */
public class Main {
	public static void main(String[] args) {
		Model model = new DefaultModel("renju","0.1.0","2011 all right reserve");
		final Application app = new ChessApplication();
		ApplicationRunner.runApplication(app,model);
	}
}
