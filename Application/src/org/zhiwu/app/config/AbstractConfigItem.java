/* AbstractConfigItem.java 1.0 2010-2-2
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
package org.zhiwu.app.config;

import java.awt.Component;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.zhiwu.app.AppManager;
import org.zhiwu.utils.SwingResources;

/**
 * <B>AbstractConfigItem</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-12-7 created
 * @since  org.zhiwu.app.config Ver 1.0
 */
public abstract class AbstractConfigItem extends JPanel implements ConfigItem{
	private static final long serialVersionUID = -5398215742467644866L;
	/**
	 * @uml.property  name="label"
	 */
	protected String label;
	/**
	 * @uml.property  name="icon"
	 */
	protected Image icon;
	protected boolean hasUnsaveChanged;
//	protected IApplication app;
	private final List<ConfigHandle> handles;
	/**
	 * @uml.property  name="pref"
	 * @uml.associationEnd  
	 */
	private final AppPreference pref;
	protected EventListenerList listenerList=new EventListenerList();
	
	/**
	 * 
	 */
	public AbstractConfigItem(String label,AppPreference pref) {
		this.label=label;
		this.pref=pref;
		handles=new ArrayList<ConfigHandle>();
		
		SwingResources r=AppManager.getResources();
		r.configConfigItem(this);
	}
	
	/**
	 * @param string
	 * @return
	 */
	protected String getPreference(String key) {
		return pref.get(key);
	}
	
	/**
	 * @param string
	 * @return
	 */
	protected boolean getBooleanPreference(String key) {
		return pref.getBoolean(key);
	}
	
	protected void putPreference(Object key, Object value) {
		fireItemChanged(key.toString(), pref.get(key.toString()),value);
		pref.put(key, value);
	}

	protected void fireItemChanged(String name, Object target) {
		fireItemChanged(name, null, target);
	}
	
	protected void fireItemChanged(String key, Object oldValue, Object newValue) {
		fireItemChanged(new ConfigItemEvent(this, key, oldValue, newValue));
	}

	private void fireItemChanged(ConfigItemEvent e) {
		Object[] listeners=listenerList.getListenerList();
		for(int i=0,j=listeners.length;i<j;i+=2){
			if(listeners[i] == ConfigListener.class){
				((ConfigListener)listeners[i+1]).itemChanged(e);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#getName()
	 */
	/**
	 * @return
	 * @uml.property  name="label"
	 */
	@Override
	public String getLabel() {
		return label;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#setIcon(java.lang.String)
	 */
	/**
	 * @param icon
	 * @uml.property  name="icon"
	 */
	@Override
	public void setIcon(Image icon) {
		this.icon = icon;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#setName(java.lang.String)
	 */
	/**
	 * @param label
	 * @uml.property  name="label"
	 */
	@Override
	public void setLabel(String label) {
		this.label = label;
	}

	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#getIcon()
	 */
	/**
	 * @return
	 * @uml.property  name="icon"
	 */
	@Override
	public Image getIcon() {
		return icon;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.Component#toString()
	 */
	@Override
	public String toString() {
		return label;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#getCompenent()
	 */
	@Override
	public Component getCompenent() {
		return this;
	}
	
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#hasUnsaveChanged()
	 */
	@Override
	public boolean hasUnsaveChanged() {
		return hasUnsaveChanged;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#handleChanged()
	 */
	@Override
	public void handleChanged() {
		for(ConfigHandle h:handles){
			h.handle();
		}
//		pref.save();
	}

	

	protected void addHandle(ConfigHandle h) {
		removeHandle(h);
		handles.add(h);
	}

	/**
	 * @param h
	 */
	public void removeHandle(ConfigHandle h) {
		int index=-1;
		for (int i = 0,j=handles.size();i<j;i++) {
			if(handles.get(i).getName().equals(h.getName())){
				index=i;
			}
		}
		if(index != -1){
			handles.remove(index);
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#addConfigListener(org.zhiwu.app.config.ConfigListener)
	 */
	@Override
	public void addConfigListener(ConfigListener l) {
		listenerList.add(ConfigListener.class, l);
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#addConfigListener(org.zhiwu.app.config.ConfigListener[])
	 */
	@Override
	public void addConfigListener(ConfigListener[] listeners) {
		for(ConfigListener l:listeners){
			addConfigListener(l);
		}
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.config.ConfigItem#addConfigListener(java.util.List)
	 */
	@Override
	public void addConfigListener(List<ConfigListener> listeners) {
		for(ConfigListener l:listeners){
			addConfigListener(l);
		}
	}
}
