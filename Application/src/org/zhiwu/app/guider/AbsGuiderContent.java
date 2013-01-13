/* AbsGuiderContent.java 1.0 2010-2-2
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
package org.zhiwu.app.guider;

import java.awt.Component;

import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

import org.zhiwu.app.Application;

/**
 * <B>AbsGuiderContent</B>
 * @author  Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version  Ver 1.0.01 2010-8-3 created
 * @since  org.zhiwu.app Ver 1.0
 */
public abstract class AbsGuiderContent extends JPanel implements GuiderContent{
	/**
	 * @uml.property  name="guider"
	 * @uml.associationEnd  
	 */
	protected DefaultGuiderView guider;
	/**
	 * @uml.property  name="app"
	 * @uml.associationEnd  
	 */
	protected Application app;
	/**
	 * @uml.property  name="title"
	 */
	private String title;
	private String content;
    protected EventListenerList listenerList = new EventListenerList();


	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#setGuider(org.zhiwu.app.DefaultGuiderView)
	 */
	/**
	 * @param guider
	 * @uml.property  name="guider"
	 */
	@Override
	public void setGuider(DefaultGuiderView guider) {
		this.guider=guider;
	}

	/**
	 * @param  string
	 * @uml.property  name="title"
	 */
	public void setTitle(String title) {
		this.title=title;
		
	}

	/**
	 * @param  string
	 * @uml.property  name="content"
	 */
	public void setContent(String content) {
		this.content=content;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#getTitle()
	 */
	/**
	 * @return
	 * @uml.property  name="title"
	 */
	public String getTitle() {
		return title;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#getContentText()
	 */
	@Override
	public String getContentText() {
		return content;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#getComponent()
	 */
	@Override
	public Component getComponent() {
		// TODO Auto-generated method stub
		return this;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.GuiderContent#addGuiderContentListener(org.zhiwu.app.GuiderContentListener)
	 */
	@Override
	public void addGuiderContentListener(
			GuiderContentListener l) {
		listenerList.add(GuiderContentListener.class, l);
	}

	protected void fireGuiderStateChanged(String name, Object value) {
		GuiderContentEvent evt=new GuiderContentEvent(this, name, value);
		Object[] listeners=listenerList.getListenerList(); 
		// Process the listeners last to first, notifying
		// those that are interested in this event
		for(int i=0,j=listeners.length-1;i<j;i+=2){
			if (listeners[i] == GuiderContentListener.class) {
				((GuiderContentListener)listeners[i+1]).stateChanged(evt);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.guider.GuiderContent#setApplication(org.zhiwu.app.IApplication)
	 */
	@Override
	public void setApplication(Application app) {
		this.app=app;
	}

}
