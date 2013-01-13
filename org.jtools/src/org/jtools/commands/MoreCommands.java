package org.jtools.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

public class MoreCommands extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		System.out.println("MoreCommands.execute()");

		String viewId = event
				.getParameter("com.qualityeclipse.favorites.command.sourceView");
		if (viewId != null) {
			IWorkbenchWindow window = HandlerUtil
					.getActiveWorkbenchWindow(event);
			try {
				window.getActivePage().showView(viewId);
			} catch (PartInitException e) {
				e.printStackTrace();
			}
		}

		ISelection selection = HandlerUtil.getCurrentSelection(event);
		System.out.println(selection);
		return null;
	}

}
