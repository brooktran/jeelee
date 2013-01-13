/* SHACoder.java 1.0 2010-2-2
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
package org.medicine.security;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

/**
 * <B>SHACoder</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-12-16 created
 * @since Jili Ver 1.0
 * 
 */
public class SHACoder {
	public static byte[] encode(byte[] data) throws GeneralSecurityException{
		//Provider provider = Security.getProvider("BC");
		MessageDigest md = MessageDigest.getInstance("SHA");
		return digest(md,data);
	}
	
	public static byte[] encode256(byte[] data)throws GeneralSecurityException{
		// Provider provider = Security.getProvider("BC");
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		return digest(md,data);
	}
	
	public static byte[] encode384(byte[] data)throws GeneralSecurityException{
		MessageDigest md = MessageDigest.getInstance("SHA-384");
		return digest(md,data);
	}
	
	public static byte[] encode512(byte[] data)throws GeneralSecurityException{
		// Provider provider = Security.getProvider("BC");
		MessageDigest md = MessageDigest.getInstance("SHA-512");
		return digest(md,data);
	}
	
	private static byte[] digest(MessageDigest digests ,byte[] data){
		byte[] retval =null ;
		for(int i=0;i<10000;i++){
			retval = digests.digest();
		}
		return retval;
	}
}
