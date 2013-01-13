/*
 * ImageTransferable.java 1.0 2010-2-2
 * 
 * Copyright (c) 2010 by Brook Tran All rights reserved.
 * 
 * The copyright of this software is own by the authors. You may not use, copy
 * or modify this software, except in accordance with the license agreement you
 * entered into with the copyright holders. For details see accompanying license
 * terms.
 */
package org.mepper.resources;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * <B>ImageTransferable</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-11-23 created
 * @since org.mepper.resources Ver 1.0
 * 
 */
public class ImageTransferable implements Transferable {
	private final BufferedImage image;
	public final static DataFlavor IMAGE_PNG_FLAVOR;
	static {
		try {
			IMAGE_PNG_FLAVOR = new DataFlavor("image/png");
		} catch (Exception e) {
			InternalError error = new InternalError(
					"Unable to crate image/png data flavor");
			error.initCause(e);
			throw error;
		}
	}

	public ImageTransferable(BufferedImage image) {
		this.image = image;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(DataFlavor.imageFlavor)
				|| flavor.equals(IMAGE_PNG_FLAVOR);
	}

	@Override
	public Object getTransferData(DataFlavor flavor)
			throws UnsupportedFlavorException, IOException {
		if (flavor.equals(DataFlavor.imageFlavor)) {
			return image;
		} else {
			throw new UnsupportedFlavorException(flavor);
		}
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[] { DataFlavor.imageFlavor, IMAGE_PNG_FLAVOR };
	}

}
