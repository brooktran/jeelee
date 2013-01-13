/* RenjuApplication.java 1.0 2010-2-2
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

import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenu;

import org.chess.app.actions.NewGameAction;
import org.chess.app.actions.SaveGameAction;
import org.zhiwu.app.Application;
import org.zhiwu.app.DefaultApplication;
import org.zhiwu.app.Model;
import org.zhiwu.app.action.AboutAction;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.app.action.SaveAction;
import org.zhiwu.utils.AppLogging;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.SystemTrayProxy;

/**
 * <B>RenjuApplication</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-16 created
 * @since org.renju.app Ver 1.0
 * 
 */
public class ChessApplication extends DefaultApplication{
	
	
	public ChessApplication( ) {
		setCreateToolbar(false);
	}
	
	@Override
	public void start() {
		super.start();
		startTray();
	}

	private void startTray() {
		SystemTrayProxy tray = new SystemTrayProxy();
		AppResources r=getResource();
		r.configTray(tray);
		
		PopupMenu popup=new PopupMenu();
		MenuItem item=new MenuItem();
		item.setLabel(r.getString("exit"));
		item.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				getModel().getAction(ExitAction.ID).actionPerformed(null);
			}
		});
		popup.add(item);
		tray.setPopup(popup);
		
		tray.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1
						&& e.getClickCount() == 1) {
					Application app = ChessApplication.this;
					app.setVisible(!app.isVisible());
				}
			}
		});

		try {
			tray.start();
		} catch (Exception e1) {
			AppLogging.handleException(e1);
		}		
	}
	
	@Override
	protected void initModelActions() {
		Model model = getModel();

//		model.putAction(OpenAction.ID, new OpenAction(this));
//		model.putAction(CloseAction.ID, new CloseAction(this));
		model.putAction(ExitAction.ID, new ExitAction(this));

//		model.putAction(FillAction.ID, new FillAction(this));
//		model.putAction(PenAction.ID, new PenAction(this));

		model.putAction(SaveAction.ID, new SaveAction(this));
//		model.putAction(SaveAsAction.ID, new SaveAsAction(this));

//		model.putAction(VersionAction.ID,new VersionAction(this));
//		model.putAction(HelpAction.ID, new HelpAction(this));
		model.putAction(AboutAction.ID, new AboutAction(this));
		
		
		model.putAction(NewGameAction.ID, new NewGameAction(this));
		model.putAction(SaveGameAction.ID, new SaveGameAction(this));
	} 
	 
	
	
	@Override
	protected JMenu createFileMenu() {
		AppResources resource =getResource();
		Model model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "file");

//		menu.add(model.getAction(OpenAction.ID));
//		menu.addSeparator();
		menu.add(model.getAction(NewGameAction.ID));
		menu.add(model.getAction(SaveGameAction.ID));
		menu.addSeparator();
		menu.add(model.getAction(ExitAction.ID));

		return menu;
	}
	
	@Override
	protected JMenu createHelpMenu() {
		AppResources resource = getResource();
		Model model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "help");

//		menu.add(getView().getAction(VersionAction.ID));
		menu.add(model.getAction(AboutAction.ID));
//		menu.addSeparator();
//		menu.add(model.getAction(HelpAction.ID));

		return menu;
	}
	
	
}
