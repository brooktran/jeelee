package org.jtools.jreader.ui.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;
import org.jtools.jreader.internal.JReader;
import org.jtools.views.NameSorter;

public class JReaderView extends ViewPart {
	public static final String ID="org.jtools.views.JReaderView";
	
	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	
	

	public JReaderView() {
		getViewSite();
	}

	@Override
	public void createPartControl(Composite parent) {
		viewer=new TreeViewer(parent, SWT.MULTI | SWT.H_SCROLL |SWT.V_SCROLL);
		drillDownAdapter=new DrillDownAdapter(viewer);
		viewer.setContentProvider(new JReaderViewContentProvider());
		viewer.setLabelProvider(new JReaderTreeLabelProvider());
		viewer.setSorter(new NameSorter());
		viewer.setInput(JReader.getInstance());//TODO 
		
		getSite().setSelectionProvider(viewer);
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
