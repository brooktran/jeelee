package org.jtools.jreader.handles;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.PlatformUI;
import org.jtools.jreader.ui.wizards.AddFeedWizard;

public class AddFeedHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		AddFeedWizard wizard=new AddFeedWizard();
		wizard.init(PlatformUI.getWorkbench(), null);
		WizardDialog dialog=new WizardDialog(PlatformUI.getWorkbench().
				getActiveWorkbenchWindow().getShell(), wizard);
		dialog.open();
		
		return null;
	}

}
