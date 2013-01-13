/* ResourceImportTransferHandler.java 1.0 2010-2-2
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
package org.mepper.resources;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JTree;

import org.mepper.resources.gui.LibraryPanel;
import org.zhiwu.utils.AppLogging;

/**
 * <B>ResourceImportTransferHandler</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-24 created
 * @since org.mepper.resources Ver 1.0
 * 
 */
public class ResourceImportTransferHandler extends DefaultResourceTransferHandler{
	private final LibraryPanel libraryPanel;
	
	public ResourceImportTransferHandler(LibraryPanel libraryPanel) {
		super(libraryPanel.getManager());
		this.libraryPanel = libraryPanel;
	}
	
	@Override
	public boolean importData(TransferSupport support) {
		JTree tree = (JTree) support.getComponent();	
		manager.setCurrentNode((StorableResource) tree.getDropLocation().getPath().getLastPathComponent());
		
		List<File> files = new LinkedList<File>();
		for(DataFlavor flavor:support.getDataFlavors()){
			if (flavor.isFlavorJavaFileListType()){
				Transferable t= support.getTransferable();
				try {
					Object data =t.getTransferData(DataFlavor.javaFileListFlavor);
					@SuppressWarnings("unchecked")
					List<File> fileList = (List<File>) data;
					files.addAll(fileList);
				} catch (Exception e) {
					AppLogging.handleException(e);
				}
			}
//			if(flavor.equals(DataFlavor.imageFlavor)
//					|| flavor.equals(ImageTransferable.IMAGE_PNG_FLAVOR)){
//				return true;
//			}
		}
		libraryPanel.importTiles(listToArray(files));
		return true;
	}

	private File[] listToArray(List<File> files) {
		File[] array = new File[files.size()];
		for(int i=0;i<files.size();i++){
			array[i]=files.get(i);
		}
		return array;
	}
	
	
}
