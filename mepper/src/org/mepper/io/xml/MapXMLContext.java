/* MapXMLContext.java 1.0 2010-2-2
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
package org.mepper.io.xml;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.xml.sax.Locator;

/**
 * <B>MapXMLContext</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-22 created
 * @since mepper Ver 1.0
 * 
 */
public class MapXMLContext {
	 private Locator locator;
	 private final Map<String, List<String>> prefixMapping = new HashMap<String, List<String>>();
	 private final Vector<RuntimeConfigurable> wrappers=new Vector<RuntimeConfigurable>();
	 
	public Locator getLocator() {
		return locator;
	}
	

	public void setLocator(Locator locator) {
		this.locator = locator;
	}


	public void startPrefixMapping(String prefix, String uri) {
		List<String> list = prefixMapping.get(prefix);
		if(list == null){
			list = new ArrayList<String>();
			prefixMapping.put(prefix, list);
		}
		list.add(uri);
	}


	public void endPrefixMapping(String prefix) {
		List<String> list = prefixMapping.get(prefix);
		if(list==null || list.size()==0){
			return;
		}
		list.remove(list.size()-1);
	}


	public RuntimeConfigurable currentWrapper() {
		if(wrappers.size()<1){
			return null;
		}
		return wrappers.elementAt(wrappers.size()-1);
	}

}
