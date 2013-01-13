/* JReaderViewContentProvider.java 1.0 2010-2-2
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
package org.jtools.jreader.ui.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.jtools.jreader.internal.JReader;
import org.jtools.jreader.internal.xml.DataSourceException;
import org.jtools.jreader.persistent.Category;
import org.jtools.jreader.persistent.JReaderItem;
import org.jtools.jreader.persistent.ReaderDAO;

/**
 * <B>JReaderViewContentProvider</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-8-9 created
 * @since org.jtools.jreader.ui.views Ver 1.0
 * 
 */
public class JReaderViewContentProvider implements IStructuredContentProvider,
		ITreeContentProvider {

	private JReaderItem invisibleRoot;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.viewers.IContentProvider#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IContentProvider#inputChanged(org.eclipse.jface
	 * .viewers.Viewer, java.lang.Object, java.lang.Object)
	 */
	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getChildren(java.lang.
	 * Object)
	 */
	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof JReaderItem){
			return ((JReaderItem)parentElement).getChildren();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#getParent(java.lang.Object
	 * )
	 */
	@Override
	public Object getParent(Object element) {
		if (element instanceof JReaderItem) {
			return ((JReaderItem) element).getParent();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.ITreeContentProvider#hasChildren(java.lang.
	 * Object)
	 */
	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof JReaderItem){
			return ((JReaderItem)element).hasChildrenItems();
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.jface.viewers.IStructuredContentProvider#getElements(java
	 * .lang.Object)
	 */
	@Override
	public Object[] getElements(Object inputElement) {
		if(inputElement instanceof JReader){
			return ((JReader)inputElement).getCategory();
		}else if(inputElement instanceof Category){
			((Category)inputElement).getChildren();
		}
		return new Object[0];
	}

	/**
	 * @return
	 */
	private Object getViewSite() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 
	 */
	private void initialize() {
		invisibleRoot=new Category("reader");
		try {
			invisibleRoot.addChildren(ReaderDAO.getInstance().getCategorys());
		} catch (DataSourceException e) {
			e.printStackTrace();
		}
	}
	 
	public static void main(String[] args) throws DataSourceException {
		ReaderDAO.getInstance().getCategorys();
	}

}
