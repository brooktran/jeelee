/* TileFactory.java 1.0 2010-2-2
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
package org.mepper.editor.tile;

import java.awt.Dimension;
import java.awt.Point;

import org.mepper.app.MapApplication;
import org.mepper.utils.MepperConstant;
import org.zhiwu.app.AppManager;
import org.zhiwu.app.config.AppPreference;

/**
 * <B>TileFactory</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-24 created
 * @since org.mepper.editor.tile Ver 1.0
 * 
 */
public class TileFactory {

	private static Tile tile;
	public static Tile getDefaultTileDefinition() {
		if(tile == null){
			tile = getTile();
		}
		return tile;
	}
	private synchronized static Tile getTile() {
		if(tile != null){
			return tile;
		}
		AppPreference pref = AppManager.getPreference(MapApplication.class
				.getName());
		Tile tile = new DefaultTile();
		tile.setOccupie(new Dimension(
				pref.getInteger("tile.occupie.width"), 
				pref.getInteger("tile.occupie.height")));
		tile.setStartingPoint(new Point(
				pref.getInteger("tile.starting.x"),
				pref.getInteger("tile.starting.y")));
		tile.setTileWidth(pref.getInteger("tile.width"));
		tile.setTileHeight(pref.getInteger("tile.height"));
		tile.setID(MepperConstant.DEFAULT_DEFINITION_TILE_ID);
//		tile.setTileStep(
//				pref.getInteger("tile.width"),
//				pref.getInteger("tile.height"));

		return tile;
	}
	
	
	
	///
	public static CandidateTile createCandidateTile(Tile tile) {
		CandidateTile newTile = new CandidateTile();
		cloneTile(tile, newTile);
		return newTile;
	}

	private static void cloneTile(Tile tile, Tile newTile) {
		newTile.setID(tile.getID());
		newTile.setStartingPoint((Point) tile.getStartingPoint().clone());
		newTile.setOccupie((Dimension) tile.getOccupie().clone());
		newTile.setImage(tile.getImage());
		newTile.setTileWidth(tile.getTileWidth());
		newTile.setTileHeight(tile.getTileHeight());
//		newTile.setTileStep(tile.getTileWidth(), tile.getTileHeight());
		newTile.setName(tile.getName());
	}

	public static DefaultTile createDefaultTile(Tile tile) {
		DefaultTile newTile = new DefaultTile();
		cloneTile(tile, newTile);
		return newTile;
	}

	public static CompositeTile createCompositeTile(Tile tile) {
		CompositeTile newTile = new DefaultCompositeTile();
		cloneTile(tile, newTile);
		return newTile;
	}
	public static Tile createDefaultTile() {
		return createCandidateTile(tile);
	}

}
