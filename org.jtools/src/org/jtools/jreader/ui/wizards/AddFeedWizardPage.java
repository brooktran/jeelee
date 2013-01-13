/* AddFeedWizardPage.java 1.0 2010-2-2
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
package org.jtools.jreader.ui.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.jtools.jreader.internal.rss.RssReader;
import org.jtools.jreader.ui.wizards.lang.Message;

/**
 * <B>AddFeedWizardPage</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-12 created
 * @since org.jtools.jreader.ui.wizards Ver 1.0
 * 
 */
public class AddFeedWizardPage extends WizardPage {
	private Text uriText;
	private RssReader reader;
	
	/**
	 * @param pageName
	 */
	protected AddFeedWizardPage() {
		super(Message.getString("AddFeedWizardPage.name"));//$NON-NLS-1$
		setTitle(Message.getString("AddFeedWizardPage.title"));//$NON-NLS-1$
		setDescription(Message.getString("AddFeedWizardPage.description"));//$NON-NLS-1$
		// setImageDescriptor(imagek)
		
		setPageComplete(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets
	 * .Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		Composite c=new Composite(parent, SWT.FILL);
		setControl(c);
		
		GridLayout layout=new GridLayout();
		layout.numColumns=2;
		c.setLayout(layout);
		c.setFont(parent.getFont());
		
		
		final Label label1=new Label(c,SWT.NULL);
		label1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, 
				false, 2, 1));
		label1.setText(Message.getString("AddFeedWizardPage.help"));
		
		Label label2=new Label(c, SWT.NULL);
		label2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, 
				false, 1, 1));
		label2.setText(Message.getString("AddFeedWizardPage.link"));
//		
		uriText=new Text(c, SWT.BORDER);
		uriText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true
				, false, 1, 1));
		uriText.setText(Message.getString("AddFeedWizardPage.textUri"));//$NON-NLS-1$
		uriText.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				updatePageComplete();
			}
		});
		

	}

	
	

	/**
	 * 
	 */
	protected void updatePageComplete() {
		setPageComplete(false);
		try {
			reader=new RssReader(uriText.getText());
			setMessage(Message.getString("AddFeedWizardPage.description"),NONE);//$NON-NLS-1$
			AddFeedWizard wizard=(AddFeedWizard) getWizard();
			wizard.setReader(reader);
			setPageComplete(true);

		} catch (Exception e) {
			setMessage(Message.getString("AddFeedWizardPage.errorURI"),ERROR);//$NON-NLS-1$
			return;
		}
	}


	/**
	 * @return the reader
	 */
	public RssReader getReader() {
		return reader;
	}




}
