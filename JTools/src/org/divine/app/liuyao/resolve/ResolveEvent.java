/* ResolveEvent.java 1.0 2010-2-2
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
package org.divine.app.liuyao.resolve;

import java.util.EventObject;

import org.divine.app.liuyao.Yao;

/**
 * <B>ResolveEvent</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-10-10 created
 * @since org.divine.app.liuyao Ver 1.0
 * 
 */
public class ResolveEvent extends EventObject{

	private final Yao yao;
	/**
	 * @param defaultResolver
	 * @param yao
	 */
	public ResolveEvent(DefaultResolver defaultResolver, Yao yao) {
		super(defaultResolver);
		this.yao=yao;
	}
	/**
	 * @return
	 */
	public Yao getYao() {
		return yao;
	}

}
