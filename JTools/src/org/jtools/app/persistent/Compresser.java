/* Compresser.java 1.0 2010-2-2
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
package org.jtools.app.persistent;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * <B>Compresser</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-27 created
 * @since org.divine.persistance Ver 1.0
 * 
 */
public class Compresser {

	public static byte[] compression(byte[] data) throws DataException {
		Deflater compresser = new Deflater(Deflater.BEST_COMPRESSION);
		compresser.reset();
		compresser.setInput(data);
		compresser.finish();

		byte[] buf = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream(data.length);
		try {
			while (!compresser.finished()) {
				int p = compresser.deflate(buf);
				baos.write(buf, 0, p);
			}
			return baos.toByteArray();
		} catch (Exception e) {
			throw new DataException( e);
		} finally {
			compresser.end();
			try {
				baos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param data
	 * @return
	 */
	public static byte[] decompression(byte[] data) throws DataException {
		Inflater inflater = new Inflater();
		inflater.reset();
		inflater.setInput(data);
		inflater.finished();

		byte[] buf = new byte[1024];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while (!inflater.finished()) {
				i = inflater.inflate(buf);
				baos.write(buf, 0, i);
			}
			return baos.toByteArray();
		} catch (DataFormatException e) {
			throw new DataException(e);
		}
	}
}
