/*
 * Copyright 2005 zhuhonghui. All Rights Reserved.
 *
 *
 */
package notepad;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

//********************************************************************
/*
 * 编辑器主程序
 * 
 *  
 * @author zhuhonghui
 * @version 2005-07-08,作成
 */
//********************************************************************
public class Notepad
{
  boolean packFrame = false;

  //Construct the application
  public Notepad() {
    Notepad_Frame1 frame = new Notepad_Frame1();
    
    //Validate frames that have preset sizes
    //Pack frames that have useful preferred size info, e.g. from their layout
    if (packFrame) {
      frame.pack();
    } else {
      frame.validate();
    }
    
    //Center the window
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension frameSize = frame.getSize();
    if (frameSize.height > screenSize.height) {
      frameSize.height = screenSize.height;
    } 
    if (frameSize.width > screenSize.width) {
      frameSize.width = screenSize.width;
    }
    frame.setLocation(0,0);
    
/*    frame.setLocation( (screenSize.width - frameSize.width) / 2,
                      (screenSize.height - frameSize.height) / 2);*/
    frame.setVisible(true);
  }

  //Main method
  public static void main(String[] args) {
    SplashWindow OpenInterface = new SplashWindow("welcome.gif", new JFrame());
    OpenInterface.setVisible(true);
    new Notepad();
    OpenInterface.close();
  }
}