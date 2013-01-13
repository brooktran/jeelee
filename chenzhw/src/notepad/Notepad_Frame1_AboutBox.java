/*
 * Copyright 2005 zhuhonghui. All Rights Reserved.
 *
 *
 */
package notepad;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

//********************************************************************
/*
 * 关于对话框
 * 
 *  
 * @author zhuhonghui
 * @version 2005-07-08,作成
 */
//********************************************************************

public class Notepad_Frame1_AboutBox extends JDialog implements ActionListener {

  JPanel panel1 = new JPanel();
  JPanel panel2 = new JPanel();
  JPanel insetsPanel1 = new JPanel();
  JPanel insetsPanel2 = new JPanel();
  JPanel insetsPanel3 = new JPanel();
  JButton button1 = new JButton();
  JLabel imageLabel = new JLabel();
  JLabel label1 = new JLabel();
  JLabel label2 = new JLabel();
  JLabel label3 = new JLabel();
  ImageIcon image1 = new ImageIcon();
  BorderLayout borderLayout1 = new BorderLayout();
  BorderLayout borderLayout2 = new BorderLayout();
  FlowLayout flowLayout1 = new FlowLayout();
  GridLayout gridLayout1 = new GridLayout();
  String product = "";
  String version = "1.0";
  String copyright = "Copyright 2005 zhuhonghui. All Rights Reserved.";
  String comments = "";
  JPanel jPanel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JLabel jLabel3 = new JLabel();
  JLabel jLabel4 = new JLabel();
  
  public Notepad_Frame1_AboutBox(Frame parent) {
    super(parent);
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  //Component initialization
  private void jbInit() throws Exception {
    image1 = new ImageIcon(notepad.Notepad_Frame1.class.getResource("about.png"));
    imageLabel.setIcon(image1);
    this.setTitle("About");
    panel1.setLayout(borderLayout1);
    panel2.setLayout(borderLayout2);
    insetsPanel1.setLayout(flowLayout1);
    insetsPanel2.setLayout(flowLayout1);
    insetsPanel2.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    gridLayout1.setRows(4);
    gridLayout1.setColumns(1);
    label1.setText(product);
    label2.setText("版本：1.0");
    label3.setText(copyright);
    insetsPanel3.setLayout(gridLayout1);
    insetsPanel3.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 10));
    insetsPanel3.setMinimumSize(new Dimension(160, 70));
    insetsPanel3.setPreferredSize(new Dimension(160, 70));
    button1.setText("Ok");
    button1.addActionListener(this);
    jPanel1.setLayout(null);
    jLabel1.setFont(new Font("Dialog", 0, 15));
    jLabel1.setText("如有问题请将问题发送于下面的信箱！");
    jLabel1.setBounds(new Rectangle(38, 0, 303, 34));
    jLabel3.setFont(new Font("Dialog", 1, 13));
    jLabel3.setForeground(new Color(198, 58, 255));
    jLabel3.setText("制作人：祝红辉");
    jLabel3.setBounds(new Rectangle(93, 24, 126, 27));
    jLabel4.setFont(new java.awt.Font("Dialog", 3, 13));
    jLabel4.setForeground(Color.blue);
    jLabel4.setDebugGraphicsOptions(0);
    jLabel4.setText("电子邮件：zhuhonghui@163.com");
    jLabel4.setBounds(new Rectangle(43, 47, 230, 29));
    panel1.setMinimumSize(new Dimension(350, 200));
    panel1.setPreferredSize(new Dimension(350, 200));
    insetsPanel2.add(imageLabel, null);
    panel2.add(insetsPanel2, BorderLayout.WEST);
    this.getContentPane().add(panel1, null);
    insetsPanel3.add(label1, null);
    insetsPanel3.add(label2, null);
    insetsPanel3.add(label3, null);
    panel1.add(jPanel1, BorderLayout.CENTER);
    jPanel1.add(jLabel1, null);
    jPanel1.add(jLabel3, null);
    jPanel1.add(jLabel4, null);
    panel2.add(insetsPanel3, BorderLayout.CENTER);
    insetsPanel1.add(button1, null);
    panel1.add(insetsPanel1, BorderLayout.SOUTH);
    panel1.add(panel2, BorderLayout.NORTH);
    setResizable(false);
  }
  
  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      cancel();
    }
    super.processWindowEvent(e);
  }
  
  //Close the dialog
  void cancel() {
    dispose();
  }
  
  //Close the dialog on a button event
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == button1) {
      cancel();
    }
  }
}