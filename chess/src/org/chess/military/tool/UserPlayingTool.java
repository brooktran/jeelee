/* GameTool.java 1.0 2010-2-2
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
package org.chess.military.tool;

import java.awt.Point;
import java.awt.event.MouseEvent;

import org.chess.military.Military;
import org.chess.military.MilitaryPieceHelper;

/**
 * <B>GameTool</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-7 created
 * @since chess Ver 1.0
 * 
 */
public class UserPlayingTool extends AbstractTool{
	private Point selected;
	
	@Override
	protected void handleLeftMouseButton(MouseEvent e) {
		Military game=getGame();
		Point mapPoint = view.screenToMap(e.getPoint());
		int piece = game.getPieceAt(mapPoint.x, mapPoint.y);

		if(piece<0){
			return;
		}
		
		if (selected == null) {
			if (!MilitaryPieceHelper.isPlayerSide(piece) || piece==0) {
				return;
			}
			
			selected =mapPoint;
			view.select(mapPoint);
			return;
		}
		if(game.canMove(selected,mapPoint)){
			
		}
	}
		
	
	@Override
	protected Military getGame(){
		return (Military)view.getGame();
	}
	
}
