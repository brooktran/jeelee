/* Yi.java 1.0 2010-2-2
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
package org.divine.app.liuyao;

public class Yi extends HeavenlyStem {
	public Yi() {
		super(1, "乙", FiveElementFactory.createElement(FiveElement.WOOD));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.divine.app.liuyao.HeavenlyStem#getSixBeings()
	 */
	@Override
	public SixBeings getSixBeings() {
		return SixBeings.getBeings(SixBeings.QING_LONG);
	}

}