/* ServerApplication.java 1.0 2010-2-2
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
package org.medicine.server.app;

import org.medicine.security.Authorizater;
import org.zhiwu.app.DefaultApplication;

/**
 * <B>ServerApplication</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-12 created
 * @since Jili Ver 1.0
 * 
 */
public class ServerApplication extends DefaultApplication{
	protected  Authorizater authorizater;
	
	public ServerApplication() {
	}
	
	public void setAuthorizater(Authorizater authorizater) {
		this.authorizater = authorizater;
	}
	
	public Authorizater getAuthorizater(){
		return authorizater;
	}
	
}
