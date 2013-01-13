package org.jtools.jreader.ui.views;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;
import org.jtools.jreader.persistent.JReaderItem;

public class ArticleListView extends ViewPart {
	private ListViewer viewer;
	
	public ArticleListView() {
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer=new ListViewer(parent);
		viewer.setContentProvider(new ArticleListContentProvider());
		viewer.setLabelProvider(new ArticlelistLabelProvider());
		viewer.setInput(null);
		
		getSite().setSelectionProvider(viewer);
		
		
		getSite().getWorkbenchWindow().getSelectionService()
		.addSelectionListener(new ISelectionListener() {
			
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				System.out.println(part.getSite().getId());
				if(! part.getSite().getId().equals(JReaderView.ID)){
					return;
				}
				ISelection s=part.getSite().getSelectionProvider().getSelection();
				if(! (s instanceof TreeSelection)){
					return;
				}
				
				TreeSelection ts=(TreeSelection)s;
				IAdaptable firstElement=(IAdaptable)ts.getFirstElement();
				JReaderItem item=(JReaderItem)firstElement;
				viewer.setInput(item);
//				System.out.println(item.getClass());
			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}

















