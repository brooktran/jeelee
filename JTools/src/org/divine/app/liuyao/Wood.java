/* Wood.java 1.0 2010-2-2
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

/**
 * <B>Wood</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-3-19 created
 * @since org.jtools.app Ver 1.0
 * 
 */
public final class Wood extends FiveElement {
	public Wood() {
		super(2, "木");
	}

	@Override
	public boolean isChangSheng(EarthlyBranch branch) {
		if (branch.value == EarthlyBranch.GAI) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isDiWang(EarthlyBranch branch) {
		if (branch.value == EarthlyBranch.MAO) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isJue(EarthlyBranch branch) {
		if (branch.value == EarthlyBranch.SHEN) {
			return true;
		}
		return false;
	}

	@Override
	public boolean isMu(EarthlyBranch branch) {
		if (branch.value == EarthlyBranch.WEI) {
			return true;
		}
		return false;
	}
}
