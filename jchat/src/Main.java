/*
 *  Simple Chat room Server written by Aviad Golan...
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {
	private static JTalkServer jts;
	public static void main(String[] args) {
		//Run Server
		
		jts = new JTalkServer("JavaTheChat Server By Aviad Golan...");
		
		//Enable Windows x button
		jts.addWindowListener(
			new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					exit();
				}
			}
		);
	}
	
	static void exit() {
		try {
			jts.finalize();
		}
		catch (Throwable e) {
			System.out.println("Error closing application...\n");
		}
		System.exit(0);
	}
}