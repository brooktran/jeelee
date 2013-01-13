/*
 * MilitarySuperviser.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chess.military;

import org.chess.game.BoardView;
import org.chess.game.GameSuperviser;
import org.chess.game.Player;
import org.chess.military.gui.MilitaryBoardView;
import org.chess.military.tool.ComputerPlayingTool;
import org.chess.military.tool.DefaultEditor;
import org.chess.military.tool.Editor;
import org.chess.military.tool.NonTool;
import org.chess.military.tool.PrepareTool;
import org.chess.military.tool.Tool;
import org.chess.military.tool.ToolEvent;
import org.chess.military.tool.ToolListener;
import org.chess.military.tool.UserPlayingTool;

/**
 * <B>MilitarySuperviser</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-7 created
 * @since chess Ver 1.0
 * 
 */
public class MilitarySuperviser implements GameSuperviser {
	private final Military game;
	private final Editor editor;
	private BoardView view;
	private final Tool computerTool = new ComputerPlayingTool();
	private final Tool playingTool = new UserPlayingTool();
	private final Tool prepareTool = new PrepareTool();
	private final Tool nonTool = new NonTool();

	private final ToolListener playingHandler = new ToolListener() {
		@Override
		public void toolDone(ToolEvent e) {
			if (!game.isGameOver()) {
				play(nextPlayer());
			} else {
				editor.setTool(nonTool);
			}
		}

		@Override
		public void toolChanged(ToolEvent e) {
		}
	};

	private final Player[] players;
	private int currentPlayer;

	public MilitarySuperviser() {
		players = new Player[4];
		currentPlayer = 0;
		game = new Military();
		editor = new DefaultEditor();
		playingTool.addToolListener(playingHandler);
		computerTool.addToolListener(playingHandler);
	}

	@Override
	public void register(int index, Player player) {
		players[index] = player;
		game.addPlayer(index);

		// prepare(index,player);
	}

	private void prepare(int index, Player player) {
		if (player instanceof UserPlayer) {
			editor.setTool(prepareTool);
		} else {
			editor.setTool(computerTool);
		}

		// if(player instanceof UserPlayer){
		//
		// }
	}

	@Override
	public Player currentPlayer() {
		return players[currentPlayer];
	}

	@Override
	public Player nextPlayer() {
		currentPlayer = (++currentPlayer) % players.length;
		return players[currentPlayer];
	}

	protected boolean checkGameOver() {
		return game.isGameOver();
	}

	@Override
	public int currentPlayerSide() {
		return currentPlayer;
	}

	@Override
	public void startGame() {
		editor.setTool(nonTool);
		play(currentPlayer());
	}

	private void play(Player player) {
		if (player instanceof UserPlayer) {
			editor.setTool(playingTool);
		} else {
			editor.setTool(computerTool);
		}
	}

	@Override
	public void prepare() {
		game.reset();

		view = new MilitaryBoardView(game);
		editor.setView(view);
		editor.setTool(prepareTool);
	}

	@Override
	public BoardView getBoardView() {
		return view;
	}

}
