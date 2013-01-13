/* MepperApplication.java 1.0 2010-2-2
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
package org.mepper.app;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JToolBar;

import org.mepper.app.action.ButtonFactory;
import org.mepper.app.action.CopyAction;
import org.mepper.app.action.CutAction;
import org.mepper.app.action.HelpAction;
import org.mepper.app.action.NewMapAction;
import org.mepper.app.action.PasteAction;
import org.mepper.app.action.TileManagerAction;
import org.zhiwu.app.ApplicationConstant;
import org.zhiwu.app.DefaultApplication;
import org.zhiwu.app.Model;
import org.zhiwu.app.View;
import org.zhiwu.app.action.AboutAction;
import org.zhiwu.app.action.ExitAction;
import org.zhiwu.app.action.SaveAction;
import org.zhiwu.app.action.VersionAction;
import org.zhiwu.utils.AppResources;

/**
 * <B>MepperApplication</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-8 created
 * @since org.mepper.app Ver 1.0
 * 
 */
public class MapApplication extends DefaultApplication {
	
	
	public MapApplication() {
	}
	
	@Override
	public void init() {
		initWelcome();
		super.init();
		// config.addItem(MapConfigItem.class);
		// config.addItem(ResourcesConfigItem.class);

		// / init resource
		
    }
	 
	private void initWelcome() {
		final WelcomeView welcome=new WelcomeView(10);
		welcome.launch();
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getNewValue().equals(ApplicationConstant.STATE_STARTED)) {
					welcome.dispose();
					MapApplication.this
							.removePropertyChangeListener(this);
				} else {
					String name=evt.getNewValue().toString();
					welcome.setText(getResource().getString(name));
//					for(int i=0;i<50000;i++){
//						String s=i+"";
//						System.out.println(s);
//					}
				}				
			}
		});
	}

	
	@Override
	protected void initModelActions() {
		Model model = getModel();

//		model.putAction(OpenAction.ID, new OpenAction(this));
//		model.putAction(CloseAction.ID, new CloseAction(this));
		model.putAction(ExitAction.ID, new ExitAction(this));

		model.putAction(CopyAction.ID, new CopyAction(this));
		model.putAction(CutAction.ID, new CutAction(this));
		model.putAction(PasteAction.ID, new PasteAction(this));

//		model.putAction(FillAction.ID, new FillAction(this));
//		model.putAction(PenAction.ID, new PenAction(this));

		model.putAction(SaveAction.ID, new SaveAction(this));
//		model.putAction(SaveAsAction.ID, new SaveAsAction(this));

//		model.putAction(AboutAction.ID, new AboutAction(this));
		model.putAction(HelpAction.ID, new HelpAction(this));

		model.putAction(TileManagerAction.ID, new TileManagerAction(this));
		model.putAction(AboutAction.ID, new AboutAction(this));
//		model.putAction(PropertyAction.ID, new PropertyAction(this));
	} 
	
	@Override
	protected void createToolBar() {
		if(toolbar != null){
			toolbar.removeAll();
			window.getContentPane().remove(toolbar);
		}
		
		View v=getView();
		if(v.getComponenet().getClientProperty("view.toolbar") instanceof JToolBar){
			toolbar=(JToolBar) v.getComponenet().getClientProperty("view.toolbar");
		}else {
			v.getComponenet().putClientProperty("view.toolbar", toolbar);
		}
//		if(v instanceof MapView){
//			MapView view=(MapView) getView();
			// craete toolbar for Tools
//			toolbar=(JToolBar) view.getClientProperty("view.toolbar");
//			toolbar=
//			super.createToolBar();;
//			view.putClientProperty("view.toolbar", toolbar);
//			return;
//		}
		List<JToolBar> list = new ArrayList<JToolBar>();
		list.addAll(ButtonFactory.createToolBar(this));
		list.add(v.getToolbar());
		for(JToolBar b:list){
			toolbar.add(b,0);
		}
		
		
	}
	
	
	@Override
	protected JMenu createFileMenu() {
		AppResources resource =getResource();
		Model model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "file");

		menu.add(model.getAction(NewMapAction.ID));
//		menu.add(model.getAction(OpenAction.ID));
		menu.addSeparator();
		menu.add(model.getAction(SaveAction.ID));
//		menu.add(model.getAction(SaveAsAction.ID));
//		menu.add(model.getAction(CloseAction.ID));
		menu.addSeparator();
		menu.add(model.getAction(ExitAction.ID));

		return menu;
	}
	
//	protected JMenu createToolMenu() {
//		AppResources resource = getResource();
//		JMenu menu=new JMenu();
//		resource.configMenu(menu, "tool");
//		
////		for(Iterator<String> i=model.viewIterator();i.hasNext();){
////			menu.add(new ViewSwitchAction(this,i.next()));
////		}
//		menu.add(model.getAction(ConfigAction.ID));
//		return menu;
//	}
	
	@Override
	protected JMenu createHelpMenu() {
		AppResources resource = getResource();
		Model model = getModel();
		JMenu menu = new JMenu();

		resource.configMenu(menu, "help");

		menu.add(getView().getAction(VersionAction.ID));
		menu.add(model.getAction(AboutAction.ID));
		menu.addSeparator();
		menu.add(model.getAction(HelpAction.ID));

		return menu;
	}
	
	
	@Override
	public MapModel getModel() {
		return (MapModel)super.getModel();
	}
	

	@Override
	public void exit() {
		super.exit();
	}

	
	
}