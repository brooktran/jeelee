/*
 * Copyright 2005 zhuhonghui. All Rights Reserved.
 *
 *
 */
package notepad;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JWindow;

/**
 * 启动画面的设置
 *  
 * @author zhuhonghui
 * @version 2005-07-08,作成
 */
public class SplashWindow extends JWindow { 
  
  /**
   * 构造方法
   *
   * @param  imagePath   画面路径
   * @param  argFrame    寄存框架
   * 
   */
  public SplashWindow(String imagePath, JFrame argFrame) {
    
    super(argFrame);
    //JLabel l = new JLabel(new ImageIcon(notepad.SplashWindow.class.getResource(imagePath)));
    JLabel l=new JLabel();
    getContentPane().add(l, BorderLayout.CENTER);
    pack();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    Dimension labelSize = l.getPreferredSize();
    setLocation(screenSize.width / 2 - (labelSize.width / 2),screenSize.height / 2 - (labelSize.height /2));
    setVisible(true);
  }
  
  /**
   * 关闭显示画面
   * 
   */
  public void close() {
    setVisible(false);
  }
}