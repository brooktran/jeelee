/* ZipUtils.java 1.0 2010-2-2
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
package org.zhiwu.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * <B>ZipUtils</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-5-31 created
 * @since org.divine.persistance Ver 1.0
 * 
 */
public class ZipUtils {
	private final String source;
	private final int BUFFER = 1024 * 2;// the input buffer

	/**
	 * 
	 */
	public ZipUtils(String source) {
		this.source = source;
	}

	public void decompress() throws IOException {
		decompress(File.separator);
	}

	public void decompress(String descPath) throws IOException {
		File srcFile = new File(source);
		if (descPath.equals(File.separator)) {
			descPath = srcFile.getParent();
		}
		File descFile = new File(descPath);

		ZipInputStream zis = new ZipInputStream(new FileInputStream(srcFile));
		ZipEntry entry = null;
		while ((entry = zis.getNextEntry()) != null) {
			// one of the desc files
			String dir = descFile.getPath() + File.separator + entry.getName();
			File dirFile = new File(dir);

			// check if the parent file exist
			fileProber(dirFile);

			if (entry.isDirectory()) {
				dirFile.mkdir();
			} else {
				descompressFile(dirFile, zis);
			}
		}
		zis.close();
	}

	/**
	 * @param dirFile
	 * @param zis
	 */
	private void descompressFile(File dirFile, ZipInputStream zis)
			throws IOException {
		BufferedOutputStream bos = new BufferedOutputStream(
				new FileOutputStream(dirFile));
		int count;
		byte[] buf = new byte[BUFFER];
		while ((count = zis.read(buf, 0, BUFFER)) != -1) {
			bos.write(buf, 0, count);
		}
		bos.close();
	}

	/**
	 * @param dirfFile
	 */
	private void fileProber(File dirfFile) {
		File parentFile = dirfFile.getParentFile();
		if (!parentFile.exists()) {
			// recure
			fileProber(parentFile);
			parentFile.mkdir();
		}
	}

	/**
	 * @param zos
	 */
	public boolean compress(ZipOutputStream zos)  {
		File file = new File(source);
		// check if is empty
//		File[] files=file.listFiles();
//		if(files.length == 0){
//			return false;
//		}
		// come on, compress it.
		try {
			compress(file, zos);
		} catch (IOException e) {
			AppLogging.handleException(e);
			return false;
		}
		return true;
	}

	/**
	 * @param file
	 * @param zos
	 */
	private void compress(File file, ZipOutputStream zos) throws IOException {
		if (file.exists()) {
			File[] files = file.listFiles();
			for (File f : files) {
				if (f.isDirectory()) {
					ZipEntry entry=new ZipEntry(f.getName());
					zos.putNextEntry(entry);
					compress(file, zos);
				} else {
					ZipEntry entry = new ZipEntry(f.getName());
					zos.putNextEntry(entry);
					zos.write(FileUtils.getFileBytes(f));
				}
			}
		}
	}
}
