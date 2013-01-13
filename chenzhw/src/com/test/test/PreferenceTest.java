package com.test.test;

import java.io.FileOutputStream;
import java.util.prefs.BackingStoreException;
import java.util.prefs.NodeChangeEvent;
import java.util.prefs.NodeChangeListener;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;

/**
 * 类<B> PreferenceTest </B>是.
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-9 新建
 * @since chenzhw Ver 1.0
 */
public class PreferenceTest {
	
	private static void testPreference() throws BackingStoreException{
		Preferences preferences=Preferences.userRoot();//
		Preferences myPrefs=preferences.node("PreferenceExample");
		//systemNode:HKEY_LOCAL_MACHINE\SOFTWARE\JavaSoft\Prefs
		//userNode:  HKEY_CURRENT_USER\Software\JavaSoft\Prefs
		myPrefs.put("A", "a");
	    myPrefs.put("B", "b");
	    myPrefs.put("C", "c");

	    System.out.println("Node's absolute path: " + myPrefs.absolutePath());
	    preferences.removeNode();
	}
	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 * @throws BackingStoreException 
	 */
	public static void main(String[] args) {
		PrepertyChangedListenerTest();
	}
	
	/**
	 * The Class Prefs. 注册一个 NodeChangeListener 或
	 * PreferenceChangeListener，而不考虑随之而来的后果。
	 * NodeChangeListener 负责通知您节点被添加和除去的时间，//**
	 * 而 PreferenceChangeListener 告诉您值的变化。这些都紧跟着基本 JavaBeans 组件事件用
	 * add/removeNodeChangeListener(NodeChangeListener) 和
	 * add/removePreferenceChangeListener()
	 * 方法处理结构之后发生。基本上，您先实现侦听器，然后注册侦听器，这样您会发现将来的变化。
	 * 
	 * 
	 */
	public static void PrepertyChangedListenerTest() {
		String[] denominations = { "One", "Two", "Five", "Ten", "Twenty" };// 名称
		String[] pictures = { "Washington", "Jefferson", "Lincoln", "Hamilton",
				"Jackson" };

		NodeChangeListener nodeChangeListener = new NodeChangeListener() {
			public void childAdded(NodeChangeEvent evt) {
				Preferences parent = evt.getParent();
				Preferences child = evt.getChild();
				System.out.println(parent.name() + " " + "has a new child "
						+ child.name());
			}

			public void childRemoved(NodeChangeEvent evt) {
				Preferences parent = evt.getParent();
				Preferences child = evt.getChild();
				System.out.println(parent.name() + " lost a child"
						+ child.name());
			}
		};

		PreferenceChangeListener preferenceChangeListener = new PreferenceChangeListener() {
			public void preferenceChange(PreferenceChangeEvent event) {
				String key = event.getKey();
				String value = event.getNewValue();
				Preferences node = event.getNode();
				System.out.println(node.name() + " now has a value of " + value
						+ " for " + key);
			}
		};

		// Look up user root
		Preferences preferences = Preferences.userRoot().node("/Prefs");

		// Add listeners
		preferences.addNodeChangeListener(nodeChangeListener);
		preferences.addPreferenceChangeListener(preferenceChangeListener);

		// Save a bunch of key-value pair
		for (int i = 0, n = denominations.length; i < n; i++) {
			preferences.put(denominations[i], pictures[i]);
		}

		// Display all the entries
		try {
			String keys[] = preferences.keys();
			for (int i = 0, n = keys.length; i < n; i++) {
				System.out.println(keys[i] + ": "
						+ preferences.get(keys[i], "Unknown"));
			}
		} catch (BackingStoreException e) {
			System.err.println("Unable to read backing store: " + e);
		}

		// Create child
		Preferences child = Preferences.userRoot()
				.node("/Prefs");

		// Save to XML file
		try {
			FileOutputStream fos = new FileOutputStream("prefs.out");
			preferences.exportNode(fos);
		} catch (Exception e) {
			System.err.println("Unable to export nodes: " + e);
		}

		// Clean up
		/*
		 * try { preferences.removeNode(); } catch (BackingStoreException e) {
		 * System.err.println("Unable to access backing store: " + e); }
		 */

	}

}






























