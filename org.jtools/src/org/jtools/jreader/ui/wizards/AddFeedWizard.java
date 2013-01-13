package org.jtools.jreader.ui.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.jtools.Activator;
import org.jtools.jreader.internal.JReader;
import org.jtools.jreader.internal.rss.RssReader;
import org.jtools.jreader.internal.xml.DataSourceException;
import org.jtools.jreader.persistent.Category;
import org.jtools.jreader.persistent.Subscriber;
import org.jtools.jreader.ui.wizards.lang.Message;

import com.sun.syndication.io.FeedException;

public class AddFeedWizard extends Wizard implements INewWizard {
	private AddFeedWizardPage addFeedWizardPage;
	private SetInformationPage setInformationPage;
	private RssReader reader;

	public AddFeedWizard() {
		setWindowTitle(Message.getString("AddFeedWizardPage.name"));
		
		IDialogSettings jreaderSetting =
			Activator.getDefault().getDialogSettings();
		IDialogSettings wizardSettings =
			jreaderSetting.getSection("AddFeedWizard");
		if(wizardSettings == null)
			wizardSettings = jreaderSetting.addNewSection("AddFeedWizard");
		setDialogSettings(jreaderSetting);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {

	}

	@Override
	public boolean performFinish() {
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {
				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException,
						InterruptedException {
					doFinish(monitor);
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			return false;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
		return true;
		
	}
	
	/** 
	 * @param monitor
	 */
	protected void doFinish(IProgressMonitor monitor) {
		JReader reader=JReader.getInstance();
		
		Category category=setInformationPage.getCategory();
		
		
		
		RssReader rssReader=addFeedWizardPage.getReader();
		Subscriber subscriber=new Subscriber(rssReader.getLink());
		try {
			rssReader.save(subscriber);
		} catch (FeedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DataSourceException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		
//		try {
//			reader.addSubscribe(category,subscriber);
//		} catch (DataSourceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */ 
	@Override
	public void addPages() {
		addPage(addFeedWizardPage=new AddFeedWizardPage());
		addPage(setInformationPage=new SetInformationPage());
//		editListsConfigWizarPage=new EditListsConfigWizarPage();
//		addPage(editListsConfigWizarPage);
	}

	/**
	 * @param reader
	 */
	public void setReader(RssReader reader) {
		this.reader=reader;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		if( super.canFinish()){
			if(setInformationPage.isPageComplete()){
				return true;
			}
		}
		return false;
	}
	
}
