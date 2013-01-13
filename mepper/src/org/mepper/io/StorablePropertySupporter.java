/* StorableProperty.java 1.0 2010-2-2
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
package org.mepper.io;

import org.mepper.utils.PropertySupporter;

/**
 * <B>StorableProperty</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-4-28 created
 * @since org.mepper.io Ver 1.0
 * 
 */
public class StorablePropertySupporter extends PropertySupporter implements Storable{
	protected int ID = 0;
	protected String name;
	protected String description="";
	
	
	@Override
	public int getID() {
		return ID;
	}

	@Override
	public void setID(int id) {
		this.ID=id;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void setName(String name) {
		this.name=name;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String desc) {
		this.description = desc;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == null){
			return false;
		}
		if( !(obj instanceof Storable)){
			return false;
		}
		Storable s= (Storable) obj;
		return s.getID()==ID ;
//		return super.equals(obj);
	}
}
