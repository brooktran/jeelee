package org.jtool.utils;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

/**
 * <B>ResourceUtil</B> is a convenience wrapper for accessing resource
 * store in a <I>ResourceBundle<I>.
 * 
 * @author Brook Tran. Email: <a
 * href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2009-4-13 modify
 * @since agenda Ver 1.1 <br>
 * 
 * 1.0 keeping only the getString and getInteger method. <br>
 * 1.0 JHotDraw7
 */
public class ResourceUtil {

	/** The wrapped resource bundle. */
	private ResourceBundle resource;

	/**
	 * Instantiates a new resourcebundleutil which wraps the provided resource
	 * bundle.
	 * <br>
	 * Prevent new instance creation.
	 * 
	 * @param r the ResourceBundle
	 */
	public ResourceUtil(ResourceBundle r) {
		resource = r;
	}

	/**
	 * Gets a string from the ResourceBundles.
	 * <br> Convenience method to save casting.
	 * 
	 * @param key the key of the properties.
	 * 
	 * @return the value of the property. Return the key if the value is not found.
	 */
	public String getString(String key){
		try {
			return resource.getString(key);
		} catch (MissingResourceException e) {
			return key;
		}
	}

	/**
	 * Gets the integer from the properties.
	 * 
	 * @param key the key of the property.
	 * 
	 * @return the value of the key. return -1 if the value is not found.
	 */
	public Integer getInteger(String key){
		try {
			return Integer.valueOf(resource.getString(key));
		} catch (MissingResourceException e) {
			return new Integer(-1);
		}
	}

	/**
	 * Gets the int.
	 * 
	 * @param key the key
	 * 
	 * @return the int
	 */
	public int getInt(String key){
		try {
			return Integer.parseInt(resource.getString(key));
		} catch (NumberFormatException e) {
			return -1;
		}
	}

	/**
	 * Gets the resource bundle util.
	 * 
	 * @param boundleName the full package name.
	 * <p>
	 * note: if the name wasn't a full name, the Local would be loaded.
	 * 
	 * @return the resource bundle util
	 * 
	 * @throws MissingResourceException the missing resource exception
	 */
	public static ResourceUtil getResourceBundleUtil(String boundleName)throws MissingResourceException{
		return new ResourceUtil(ResourceBundle.getBundle(boundleName,Locale.getDefault()));

	}

	/**
	 * Gets the resource bundle util.
	 * 
	 * @param boundleName the boundle name
	 * @param locale the locale
	 * 
	 * @return the resource bundle util
	 * 
	 * @throws MissingResourceException the missing resource exception
	 */
	public static ResourceUtil getResourceBundleUtil(String boundleName,Locale locale)throws MissingResourceException{
		return new ResourceUtil(ResourceBundle.getBundle(boundleName,locale));
	}

	/**
	 * Gets the bundle.
	 * 
	 * @return the bundle
	 */
	public ResourceBundle getBundle(){
		return resource;
	}

	/**
	 * Gets the keys.
	 * 
	 * @return the keys
	 */
	public Enumeration<String> getKeys(){
		return resource.getKeys();
	}

	/**
	 * Gets a resource string formatted with MessageFormat.
	 * 
	 * @param key the key
	 * @param argument the argument
	 * 
	 * @return Formatted stirng.
	 */
	public String getFormatted(String key, String argument) {
		return MessageFormat.format(resource.getString(key), new Object[] {argument});
	}

	/**
	 * Gets a resource string formatted with MessageFormat.
	 * 
	 * @param key the key
	 * @param arguments the arguments
	 * 
	 * @return Formatted stirng.
	 */
	public String getFormatted(String key, Object... arguments) {
		return MessageFormat.format(resource.getString(key), arguments);
	}

	/** 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString()+"["+resource+"]";
	}

	/**
	 * Config menu.
	 * 
	 * @param menu the menu
	 * @param name the name
	 */
	public void configMenu(JMenuItem menu, String name) {
		menu.setText(getString(name));
		if(!(menu instanceof JMenu)){// items
			menu.setAccelerator(getAcc(name));
		}
		menu.setMnemonic(getMnem(name));
		menu.setIcon(getImageIcon(name,getClass()));

		//		jMenu1.setText("File");
		//		 jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
		//        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/resources/fileNew.png"))); // NOI18N
		//        jMenuItem1.setText("New");
		//        jMenu1.add(jMenuItem1);
		//        jMenu1.add(jSeparator1);


	}


	/**
	 * Gets the image icon.
	 * 
	 * @param key the key
	 * 
	 * @return the image icon
	 */
	public Icon getImageIcon(String key){
		return getImageIcon(key,getClass());
	}

	/**
	 * Gets the image icon.
	 * 
	 * @param clazz the clazz
	 * @param key the key
	 * 
	 * @return the image icon
	 */
	private Icon getImageIcon(String key,
			Class<?> clazz) {
		String src="";

		try {
			src=resource.getString(key+".icon");
		} catch (MissingResourceException e) {
			return null;
		}

		if (src.equals("")) {
			return null;
		}

		//if((src.startsWith("/"))){
		String imageDir;
		imageDir = resource.getString("$iconDir");
		if(!(imageDir.endsWith("/"))){
			imageDir+="/";
		}
		src = imageDir+src;
		//	}



		//		File f=new File(src);
		//		System.out.println(f.getAbsolutePath()+" "+f.exists());

		//URL url=clazz.getResource(src);
		//return url==null ? null:new ImageIcon(url);
		return new ImageIcon(src);
	}

	/**
	 * Gets the mnem.
	 * 
	 * @param key the key
	 * 
	 * @return the mnem
	 */
	private char getMnem(String key) {
		String string=null;
		try {
			string=resource.getString(key+".mnem");;
		} catch (MissingResourceException e) {
		}
		return (string==null||string.length()==0)?'\0':string.charAt(0);
	}

	/**
	 * Gets the shortcut key.
	 * 
	 * @param key the key
	 * 
	 * @return the acc
	 */
	private KeyStroke getAcc(String key) {
		KeyStroke keyStroke=null;

		String string=null;
		try {
			string=resource.getString(key+".acc");
		} catch (MissingResourceException e) {
		}
		keyStroke = (string==null)?(KeyStroke)null:KeyStroke.getKeyStroke(string);

		return keyStroke;
	}

	/**
	 * Config action.
	 * 
	 * @param id the id
	 * @param action the action
	 */
	public void configAction(Action action, String id) {
		configAction(action, id,getClass());
	}

	/**
	 * Config action.
	 * 
	 * @param action the action
	 * @param id the id
	 * @param clazz the clazz
	 */
	private void configAction(Action action, String id,
			Class<?> clazz) {
		action.putValue(Action.NAME, getString(id));
		action.putValue(Action.ACCELERATOR_KEY, getAcc(id));
		action.putValue(Action.MNEMONIC_KEY, new Integer(getMnem(id)));
		action.putValue(Action.SMALL_ICON, getImageIcon(id, clazz));

	}
}


































