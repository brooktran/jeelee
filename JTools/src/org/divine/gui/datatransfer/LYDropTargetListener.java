/* LYDropTargetListener.java 1.0 2010-2-2
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
package org.divine.gui.datatransfer;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import org.jtools.app.persistent.Compresser;

/**
 * <B>LYDropTargetListener</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-6-2 created
 * @since org.divine.gui.datatransfer Ver 1.0
 * 
 */
public class LYDropTargetListener implements DropTargetListener {


	/**
	 * @param jTextPane1
	 */
	public LYDropTargetListener() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent
	 * )
	 */
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent
	 * )
	 */
	@Override
	public void dragOver(DropTargetDragEvent dtde) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	@Override
	public void drop(DropTargetDropEvent dtde) {
		if (!isDropAcceptable(dtde)) {
			dtde.rejectDrop();
			return;
		}
		dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
		
		try {
			Transferable t = dtde.getTransferable();
			DataFlavor[] flavors = t.getTransferDataFlavors();
			for (int i = 0, j = flavors.length; i < j; i++) {
				DataFlavor d = flavors[i];
				if (d.equals(DataFlavor.javaFileListFlavor)) {
					List<File> files = (List<File>) t
							.getTransferData(d);
					for (File f : files) {
						ByteArrayOutputStream baos=new ByteArrayOutputStream();
						byte[] buf=new byte[2048];
						FileInputStream fis=new FileInputStream(f);
						for(int c;(c=fis.read(buf))!=-1;){
							baos.write(buf, 0, c);
						}
						buf=Compresser.decompression(baos.toByteArray());
						baos.close();
						
						FileOutputStream fos=new FileOutputStream(new File(f.getPath()+".xml"));
						fos.write(buf);
						fos.flush();
						fos.close();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param dtde
	 * @return
	 */
	private boolean isDragAcceptable(DropTargetDragEvent dtde) {
		return (dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE)!=0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seejava.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.
	 * DropTargetDragEvent)
	 */
	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {
		System.out.println("LYDropTargetListener.dropActionChanged()");
		if (!isDragAcceptable(dtde)) {
			dtde.rejectDrag();
			return;
		}
	}

	/**
	 * @param dtde
	 * @return
	 */
	private boolean isDropAcceptable(DropTargetDropEvent dtde) {
		return (dtde.getDropAction() & DnDConstants.ACTION_COPY_OR_MOVE)!=0;
	}

}
