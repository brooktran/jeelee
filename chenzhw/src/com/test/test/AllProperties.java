package com.test.test;

import java.util.Enumeration;
import java.util.Properties;

/**
 * <B>AllProperties</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0.01 2009-4-11 created
 * @since chenzhw Ver 1.0
 * 
 */
public final class AllProperties {
	private static volatile AllProperties allProperties;
	private static Properties prop ;

	static{
		prop = System.getProperties();
	}
	
	private AllProperties() {
	}

	/**
     * Determines the current system properties.
     * <p>
     * First, if there is a security manager, its
     * <code>checkPropertiesAccess</code> method is called with no
     * arguments. This may result in a security exception.
     * <p>
     * The current set of system properties for use by the 
     * {@link #getProperty(String)} method is returned as a 
     * <code>Properties</code> object. If there is no current set of 
     * system properties, a set of system properties is first created and 
     * initialized. This set of system properties always includes values 
     * for the following keys: 
     * <table summary="Shows property keys and associated values">
     * <tr><th>Key</th>
     *     <th>Description of Associated Value</th></tr>
     * <tr><td><code>java.version</code></td>
     *     <td>Java Runtime Environment version</td></tr>
     * <tr><td><code>java.vendor</code></td>
     *     <td>Java Runtime Environment vendor</td></tr
     * <tr><td><code>java.vendor.url</code></td>
     *     <td>Java vendor URL</td></tr>
     * <tr><td><code>java.home</code></td>
     *     <td>Java installation directory</td></tr>
     * <tr><td><code>java.vm.specification.version</code></td>
     *     <td>Java Virtual Machine specification version</td></tr>
     * <tr><td><code>java.vm.specification.vendor</code></td>
     *     <td>Java Virtual Machine specification vendor</td></tr>
     * <tr><td><code>java.vm.specification.name</code></td>
     *     <td>Java Virtual Machine specification name</td></tr>
     * <tr><td><code>java.vm.version</code></td>
     *     <td>Java Virtual Machine implementation version</td></tr>
     * <tr><td><code>java.vm.vendor</code></td>
     *     <td>Java Virtual Machine implementation vendor</td></tr>
     * <tr><td><code>java.vm.name</code></td>
     *     <td>Java Virtual Machine implementation name</td></tr>
     * <tr><td><code>java.specification.version</code></td>
     *     <td>Java Runtime Environment specification  version</td></tr>
     * <tr><td><code>java.specification.vendor</code></td>
     *     <td>Java Runtime Environment specification  vendor</td></tr>
     * <tr><td><code>java.specification.name</code></td>
     *     <td>Java Runtime Environment specification  name</td></tr>
     * <tr><td><code>java.class.version</code></td>
     *     <td>Java class format version number</td></tr>
     * <tr><td><code>java.class.path</code></td>
     *     <td>Java class path</td></tr>
     * <tr><td><code>java.library.path</code></td>
     *     <td>List of paths to search when loading libraries</td></tr>
     * <tr><td><code>java.io.tmpdir</code></td>
     *     <td>Default temp file path</td></tr>
     * <tr><td><code>java.compiler</code></td>
     *     <td>Name of JIT compiler to use</td></tr>
     * <tr><td><code>java.ext.dirs</code></td>
     *     <td>Path of extension directory or directories</td></tr>
     * <tr><td><code>os.name</code></td>
     *     <td>Operating system name</td></tr>
     * <tr><td><code>os.arch</code></td>
     *     <td>Operating system architecture</td></tr>
     * <tr><td><code>os.version</code></td>
     *     <td>Operating system version</td></tr>
     * <tr><td><code>file.separator</code></td>
     *     <td>File separator ("/" on UNIX)</td></tr>
     * <tr><td><code>path.separator</code></td>
     *     <td>Path separator (":" on UNIX)</td></tr>
     * <tr><td><code>line.separator</code></td>
     *     <td>Line separator ("\n" on UNIX)</td></tr>
     * <tr><td><code>user.name</code></td>
     *     <td>User's account name</td></tr>
     * <tr><td><code>user.home</code></td>
     *     <td>User's home directory</td></tr>
     * <tr><td><code>user.dir</code></td>
     *     <td>User's current working directory</td></tr>
     * </table>
     * <p>
     * Multiple paths in a system property value are separated by the path
     * separator character of the platform.
     * <p>
     * Note that even if the security manager does not permit the
     * <code>getProperties</code> operation, it may choose to permit the
     * {@link #getProperty(String)} operation.
     *
     * @return     the system properties
     * @exception  SecurityException  if a security manager exists and its
     *             <code>checkPropertiesAccess</code> method doesn't allow access
     *              to the system properties.
     * @see        #setProperties
     * @see        java.lang.SecurityException
     * @see        java.lang.SecurityManager#checkPropertiesAccess()
     * @see        java.util.Properties
     */
	public static Enumeration<?> getSystemPropertyNames() {
		return prop.propertyNames();
	}

	public static void main(String[] args) {
		Enumeration<?> enumeration=getSystemPropertyNames();
		while (enumeration.hasMoreElements()) {
			String elm=(String)enumeration.nextElement();
			System.out.println(elm+"  ------->  "+prop.getProperty(elm));
		}
	}
	/*java.runtime.name  ------->  Java(TM) SE Runtime Environment
	sun.boot.library.path  ------->  D:\software\programFile\java\jre\bin
	java.vm.version  ------->  10.0-b22
	java.vm.vendor  ------->  Sun Microsystems Inc.
	java.vendor.url  ------->  http://java.sun.com/
	path.separator  ------->  ;
	java.vm.name  ------->  Java HotSpot(TM) Client VM
	file.encoding.pkg  ------->  sun.io
	user.country  ------->  CN
	sun.java.launcher  ------->  SUN_STANDARD
	sun.os.patch.level  ------->  Service Pack 1
	java.vm.specification.name  ------->  Java Virtual Machine Specification
	user.dir  ------->  G:\test\java\chenzhw
	java.runtime.version  ------->  1.6.0_06-b02
	java.awt.graphicsenv  ------->  sun.awt.Win32GraphicsEnvironment
	java.endorsed.dirs  ------->  D:\software\programFile\java\jre\lib\endorsed
	os.arch  ------->  x86
	java.io.tmpdir  ------->  D:\software\PROGRA~1\VISTAS~1\temp\
	line.separator  ------->  

	java.vm.specification.vendor  ------->  Sun Microsystems Inc.
	user.variant  ------->  
	os.name  ------->  Windows Vista
	sun.jnu.encoding  ------->  GBK
	java.library.path  ------->  D:\software\programFile\java\bin;.;C:\Windows\Sun\Java\bin;C:\Windows\system32;C:\Windows;D:/software/programFile/java/jre1.6.0._06/bin/client;D:/software/programFile/java/jre1.6.0._06/bin;C:\Program Files\PC Connectivity Solution\;C:\Windows\system32;C:\Windows;C:\Windows\System32\Wbem;D:\software\VistaMaster;D:\software\programFile\java\bin;D:\software\programFile\matlab\bin;D:\software\programFile\matlab\bin\win32;D:\player\Storm\Codec;D:\player\Storm
	java.specification.name  ------->  Java Platform API Specification
	java.class.version  ------->  50.0
	sun.management.compiler  ------->  HotSpot Client Compiler
	os.version  ------->  6.0
	user.home  ------->  C:\Users\root
	user.timezone  ------->  
	java.awt.printerjob  ------->  sun.awt.windows.WPrinterJob
	file.encoding  ------->  GBK
	java.specification.version  ------->  1.6
	user.name  ------->  root
	java.class.path  ------->  G:\test\java\chenzhw\bin;D:\software\eclipse3.4\plugins\org.junit_3.8.2.v20080602-1318\junit.jar;G:\test\java\chenzhw\vbjorb.jar;G:\test\java\chenzhw\jdom.jar;D:\software\eclipse3.4\plugins\org.junit4_4.3.1\junit.jar
	java.vm.specification.version  ------->  1.0
	sun.arch.data.model  ------->  32
	java.home  ------->  D:\software\programFile\java\jre
	java.specification.vendor  ------->  Sun Microsystems Inc.
	user.language  ------->  zh
	awt.toolkit  ------->  sun.awt.windows.WToolkit
	java.vm.info  ------->  mixed mode, sharing
	java.version  ------->  1.6.0_06
	java.ext.dirs  ------->  D:\software\programFile\java\jre\lib\ext;C:\Windows\Sun\Java\lib\ext
	sun.boot.class.path  ------->  D:\software\programFile\java\jre\lib\resources.jar;D:\software\programFile\java\jre\lib\rt.jar;D:\software\programFile\java\jre\lib\sunrsasign.jar;D:\software\programFile\java\jre\lib\jsse.jar;D:\software\programFile\java\jre\lib\jce.jar;D:\software\programFile\java\jre\lib\charsets.jar;D:\software\programFile\java\jre\classes
	java.vendor  ------->  Sun Microsystems Inc.
	file.separator  ------->  \
	java.vendor.url.bug  ------->  http://java.sun.com/cgi-bin/bugreport.cgi
	sun.cpu.endian  ------->  little
	sun.io.unicode.encoding  ------->  UnicodeLittle
	sun.desktop  ------->  windows
	sun.cpu.isalist  ------->  */
}
