/* @(#)DefaultApplication.java 1.0 2009-11-18
 * 
 * Copyright (c) 2009 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.jtool.app;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jtool.app.action.ExitAction;
import org.jtool.sample.job.action.OpenRecentAction;
import org.jtool.utils.ResourceUtil;

/**
 * <B>DefaultApplication</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-11-18 created
 * @since JTool Ver 1.0
 * 
 */
public class DefaultApplication extends AbsApplicaton{
	private static final long serialVersionUID = 1L;

	private JFrame window; 

	public DefaultApplication() {
		System.out.println("DefaultApplication ");
	}

	/**
	 * 
	 * @return
	 */
	private JMenuBar createMenu() {
		System.out.println("DefaultApplication createMenu");

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());

		for(JMenu menu:getModel().createMenu()){
			menuBar.add(menu);
		}

		menuBar.add(createHelpMenu());

		return menuBar;
	}

	/**
	 * 
	 * @return
	 */
	private JMenu createFileMenu() {
		ResourceUtil resource = ResourceUtil.getResourceBundleUtil(Labels);
		IModel model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "file");

		//		menu.add(model.getAction(JLOpenAction.ID));
		final JMenu openRecentMenu;
		openRecentMenu = new JMenu();
		resource.configMenu(openRecentMenu, "openRecent");
		updateOpenRecentMenu(openRecentMenu);
		menu.add(openRecentMenu);

		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				String name = evt.getPropertyName();
				if (name .equals( RecentFileProperty)) {
					updateOpenRecentMenu(openRecentMenu);
				}
			}
		});


		menu.add(model.getAction(OpenRecentAction.ID));
		menu.add(model.getAction(ExitAction.ID));

		// recent change

		return menu;
	}

	/**
	 * 
	 * @param openRecentMenu
	 */
	private void updateOpenRecentMenu(JMenu openRecentMenu) {
		if (openRecentMenu.getItemCount() > 0) {
			JMenuItem clearRecentFilesItem = openRecentMenu.getItem(
					openRecentMenu.getItemCount() - 1);
			openRecentMenu.removeAll();
			for (File f : recentFiles()) {
				openRecentMenu.add(new OpenRecentAction(this, f));
			}
			if (recentFiles().size() > 0) {
				openRecentMenu.addSeparator();
			}
			openRecentMenu.add(clearRecentFilesItem);
		}
	}


	/**
	 * 
	 * @return
	 */
	private JMenu createHelpMenu() {
		ResourceUtil resource = ResourceUtil.getResourceBundleUtil(Labels);
		IModel model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "help");

		//		menu.add(model.getAction(AboutAction.ID));
		//		menu.addSeparator();
		//		menu.add(model.getAction(HelpAction.ID));

		return menu;

	}

	private void setToolBar() {
		if (getModel() == null) {
			return;
		}
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		for (JToolBar tb : getModel().createToolbars(this, null)) {
			toolBar.add(tb);
		}
		window.getContentPane().add(toolBar, BorderLayout.PAGE_START);

	}

	/**
	 * @see com.map.app.AbsApplicaton#getContainer()
	 */
	@Override
	public Container getContainer() {
		return window.getContentPane();
	}

	/**
	 * @see com.map.app.IApplication#getWindow()
	 */
	public Window getWindow() {
		return window;
	}

	/**
	 * @see com.map.app.AbsApplicaton#init()
	 */
	@Override
	public void init() {
		super.init();
		System.out.println("DefaultApplication init");
		setLAF();
		initModelActions();

		window = new JFrame(getName());
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		// window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				getModel().getAction(ExitAction.ID).actionPerformed(
						new ActionEvent(window, ActionEvent.ACTION_PERFORMED,
						"windowClosing"));
			}
		});

		window.setJMenuBar(createMenu());
		setToolBar();

		// ~
		// window.setLayout(new CardLayout());
		window.setContentPane(getContainer());

		window.setSize(1000, 1000);

		// GroupLayout layout = new GroupLayout(window.getContentPane());
		// window.getContentPane().setLayout(layout);
		// layout.setHorizontalGroup(
		// layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		// .addComponent(getContainer(),GroupLayout.DEFAULT_SIZE,679,Short.MAX_VALUE)
		// );
		// layout.setVerticalGroup(
		// layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		// .addComponent(getContainer(),GroupLayout.DEFAULT_SIZE,510,Short.MAX_VALUE)
		// );

		// / initWindow
		// / initApplicationAction

		// window.addWindowListener(new WindowAdapter(){
		// public void windowClosing(final WindowEvent e){
		// getModel().getAction(ExitAction.ID).actionPerformed(
		// new ActionEvent(window,ActionEvent.ACTION_PERFORMED,
		// "windowClosing"));
		// }
		// });
		//		

	}

	/**
	 * 
	 */
	private void initModelActions() {
		IModel model = getModel();
		//		model.putAction(NewMapAction.ID, new NewMapAction(this));
		//		model.putAction(NewLayerAction.ID, new NewLayerAction(this));
		//
		//		model.putAction(BEOpenAction.ID, new BEOpenAction(this));
		//		model.putAction(CloseAction.ID, new CloseAction(this));
		model.putAction(ExitAction.ID, new ExitAction(this));
		//
		//		model.putAction(CopyAction.ID, new CopyAction(this));
		//		model.putAction(CutAction.ID, new CutAction(this));
		//		model.putAction(PasteAction.ID, new PasteAction(this));
		//
		//		model.putAction(FillAction.ID, new FillAction(this));
		//		model.putAction(PenAction.ID, new PenAction(this));
		//
		//		model.putAction(RedoAction.ID, new RedoAction(this));
		//		model.putAction(UndoAction.ID, new UndoAction(this));
		//
		//		model.putAction(SaveAction.ID, new SaveAction(this));
		//		model.putAction(SaveAsAction.ID, new SaveAsAction(this));
		//
		//		model.putAction(AboutAction.ID, new AboutAction(this));
		//		model.putAction(HelpAction.ID, new HelpAction(this));

	}

	/**
	 * @see com.map.app.AbsApplicaton#setContainer(java.awt.Container)
	 */
	@Override
	public void setContainer(Container newValue) {
		Container oldValue = window.getContentPane();
		window.setContentPane(newValue);
		firePropertyChange("container", oldValue, newValue);
	}

	/**
	 * Sets the look and feel for this application .
	 */
	private void setLAF() {
		System.out.println("DefaultApplication setLAF");

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}

		if (UIManager.getString("OptionPane.css") == null) {
			UIManager.put("OptionPane.css", "");
		}

	}

	/**
	 * @see com.map.app.AbsApplicaton#start()
	 */
	@Override
	public void start() {
		super.start();

		// ~

		window.setVisible(true);
		window.toFront();
		window.pack();
	}

	public void setFoucs() {
		window.toFront();
	}

	public java.util.List<File> recentFiles() {
		return Collections.unmodifiableList(recentFiles);
	}

	public void setTitle(String title) {
		window.setTitle(title);
	}


}