/* DefaultResourceTransferHandler.java 1.0 2010-2-2
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

import javax.swing.TransferHandler;

/**
 * <B>DefaultResourceTransferHandler</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-23 created
 * @since org.mepper.resources Ver 1.0
 * 
 */
public class DefaultResourceTransferHandler extends TransferHandler{
	protected final ResourceManager manager;
	public DefaultResourceTransferHandler(ResourceManager manager) {
		this.manager = manager;
	}
	
	@Override
	public boolean canImport(TransferSupport support) {
		for(DataFlavor flavor:support.getDataFlavors()){
			if (flavor.isFlavorJavaFileListType()){
				return true;
//				Transferable t= support.getTransferable();
//				try {
//					Object data =t.getTransferData(DataFlavor.javaFileListFlavor);
//					List<File> files = (List<File>) data;
//					if(acceptFile((File[]) files.toArray())){
//						return true;
//					}
//				} catch (Exception e) {
//					AppLogging.handleException(e);
//				}
			}
			if(flavor.equals(DataFlavor.imageFlavor)
					|| flavor.equals(ImageTransferable.IMAGE_PNG_FLAVOR)){
				return true;
			}
		}
		return super.canImport(support);
	}

//	protected boolean acceptFile(File[] files) {
//		for(File f:files){
//			if (f.isFile()) {
//				if(imageFilter.accept(f)){
//					return true;
//				}
//			}else {
//				return acceptFile(f.listFiles());
//			}
//			
//		}
//		return false;
//	}
	
//	private static FileNameExtensionFilter imageFilter = 
//			new FileNameExtensionFilter("image", "jpg","png","psd","gif","jpeg","bmp");
	
	
	
	
	

}
