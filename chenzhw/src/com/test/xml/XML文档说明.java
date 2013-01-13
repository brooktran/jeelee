package com.test.xml;

/**
 * <B>XML文档说明</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0.01 2009-4-17 created
 * @since chenzhw Ver 1.0
 * 
 */
public class XML文档说明 {
	/*
	 * 文档开头 《note.xsd》
	 * 
	 * <?xml version="1.0" encoding="UTF-8"?>
	 * 
	 * <xs:schema xmlns:xs="http://www.w3.org/2001/XMLSchema" 这个不能更改 。	
	 * 是string等的来源，xs是这个命名空间的前缀。 如果要更改xs 则全部的xs都必须更改。
	 * 
	 * targetNamespace="org.chen" 这个可以随便定义 但要和下面的xmlns一样 是note等element的来源
	 * 
	 * xmlns="org.chen" 默认的命名空间
	 * 
	 * elementFormDefault="qualified"> 任何声明的元素都必须被限定至相应命名空间
	 * 
	 * <xs:element name="note">
	 * 
	 * <xs:complexType>
	 * 
	 * <xs:sequence>
	 * 
	 * <xs:element name="to" type="xs:string"/>
	 * 
	 * <xs:element name="from" type="xs:string"/>
	 * 
	 * <xs:element name="heading" type="xs:string"/>
	 * 
	 * <xs:element name="body" type="xs:string"/>
	 * 
	 * </xs:sequence>
	 * 
	 * </xs:complexType>
	 * 
	 * </xs:element> </xs:schema>
	 * 
	 * 
	 * ------------------------------------------------
	 * ************************************************
	 * ------------------------------------------------
	 * 《note.xml》
	 * 
	 * <?xml version="1.0"?> 
	 * 
	 * <note xmlns="org.chen"		因为要引用Note To From等上面targetNamespace定义的元素
	 * 
	 * xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"	固定。有两个属性（下面的Location）。
	 * 
	 * xsi:schemaLocation="org.chen note.xsd">					命名空间 空格 文件名
	 * 
	 * <to>Tove</to> <from>Jani</from> <heading>Reminder</heading> <body>Don't
	 * forget me this weekind!</body>
	 * 
	 * </note>
	 */

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
