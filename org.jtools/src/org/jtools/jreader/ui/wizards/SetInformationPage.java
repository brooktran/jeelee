/* SetInformationPage.java 1.0 2010-2-2
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

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IViewPart;
import org.jtools.Activator;
import org.jtools.jreader.internal.JReader;
import org.jtools.jreader.internal.rss.RssReader;
import org.jtools.jreader.persistent.Category;
import org.jtools.jreader.persistent.JReaderItem;
import org.jtools.jreader.persistent.Subscriber;
import org.jtools.jreader.ui.views.JReaderView;
import org.jtools.jreader.ui.wizards.lang.Message;

/**
 * <B>SetInformationPage</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-14 created
 * @since org.jtools.jreader.ui.wizards Ver 1.0
 * 
 */
public class SetInformationPage extends WizardPage {

	private Combo combo;
	private Category category;
	private Text titleText;

	/**
	 * @param pageName
	 */
	protected SetInformationPage() {
		super(Message.getString("AddFeedWizardPage.name"));//$NON-NLS-1$
		setTitle(Message.getString("AddFeedWizardPage.title"));//$NON-NLS-1$
		setDescription(Message.getString("AddFeedWizardPage.description"));//$NON-NLS-1$

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
		Composite c = new Composite(parent, SWT.NONE);
		setControl(c);

		GridLayout gl = new GridLayout();
		gl.numColumns = 2;
		c.setLayout(gl);
		c.setFont(parent.getFont());

		final Label label1 = new Label(c, SWT.NULL);
		label1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		label1.setText(Message.getString("SetInformationPage.nameLabel"));//$NON-NLS-1$

		titleText = new Text(c, SWT.BORDER);
		titleText.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		final Label label3 = new Label(c, SWT.NULL);
		label3.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false,
				1, 1));
		label3.setText(Message.getString("SetInformationPage.category"));//$NON-NLS-1$

		// Text t2=new Text(c, SWT.NULL);

		final JReader jReader = JReader.getInstance();

		combo = new Combo(c, SWT.NONE);
		final GridData gd_combo = new GridData(SWT.FILL, SWT.CENTER, true,
				false);
		// gd_combo.widthHint = 96;
		combo.setLayoutData(gd_combo);

		Category[] categories = jReader.getCategory();
		for (Category ca : categories) {
			combo.add(ca.getName());
		}
		combo.setText(getViewSelectCategory()+"");

		combo.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				updateComboComplete();
			}
		});
		combo.addSelectionListener(new SelectionListener() {
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				Combo combo = (Combo) e.getSource();
				String str = combo.getText();
				category = jReader.getCategoryByName(str);
				updateComboComplete();
			}
		});

	}

	private Category getViewSelectCategory() {
		if(Activator.getDefault().getWorkbench().getActiveWorkbenchWindow() ==null){
			return new Category("null ha ha...");
		}
		
		
		IViewPart viewPart = Activator.getDefault().getWorkbench()
				.getActiveWorkbenchWindow().getActivePage()
				.findView(JReaderView.ID);
		ISelection selection = viewPart.getSite().getSelectionProvider()
				.getSelection();
		System.out.println("SetInformationPage.getViewSelectCategory()");
		aa: {
			if (selection instanceof TreeSelection) {
				TreeSelection ts = (TreeSelection) selection;
				JReaderItem item = (JReaderItem) ts.getFirstElement();

				if (item == null) {
					break aa;
				}

				if (item instanceof Category) {
					return (Category) item;
				} else if (item instanceof Subscriber) {
					return (Category) (item.getParent());
				}
			}
		}
		return JReader.getDefaultCategory();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.dialogs.DialogPage#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible) {
			AddFeedWizardPage prePage = (AddFeedWizardPage) getPreviousPage();
			RssReader reader = prePage.getReader();
			titleText.setText(reader.getTitle());

			updateComboComplete();
		}
	}

	/**
	 * 
	 */
	protected void updateComboComplete() {
		if (combo.getText().equals("")) {
			setMessage(Message.getString("AddFeedWizardPage.selecteCategory"),//$NON-NLS-1$
					WARNING);
		} else {
			setMessage(Message.getString("AddFeedWizardPage.description"), NONE);//$NON-NLS-1$
		}

		if (titleText.getText().equals("")) {
			setPageComplete(false);
		} else {
			setPageComplete(true);
		}
	}

	/**
	 * @return
	 */
	public Category getCategory() {
		return category==null?getViewSelectCategory():category;
	}
}
