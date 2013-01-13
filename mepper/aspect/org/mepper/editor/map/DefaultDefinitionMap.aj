/* DefaultDefinitionMap.aj 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Chen Zhiwu
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.mepper.editor.map;
import org.mepper.resources.gui.MapTypeDialog;
import org.zhiwu.utils.AppLogging;

/**
 * <B>DefaultDefinitionMap</B>
 * 
 * @author Zhi-Wu Chen. Email: <a href="mailto:c.zhiwu@gmail.com">c.zhiwu@gmail.com</a>
 * @version Ver 1.0.01 2011-11-26 created
 * @since org.mepper.editor.map Ver 1.0
 * 
 */
public aspect DefaultDefinitionMap {

	pointcut definition(Map map) : 
		call(protected void MapTypeDialog.fireOptionSelected(..))
				&& args(*,map);
	
	before(Map map) : definition(map) {
		Map oldValue = MapFactory.getDefaultDefinitionMap();
		if(! map.getClass().equals(oldValue.getClass())){
			try { 
				Map newValue =MapFactory.refreshDefaultDefinitionMapType(map);
				newValue.setTileStep(map.getTileWidth(), map.getTileHeight());
			} catch (Exception e) { 
				AppLogging.handleException(e);
			} 
		}
	}
}
