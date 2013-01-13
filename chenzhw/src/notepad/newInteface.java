/*
 * Copyright 2005 zhuhonghui. All Rights Reserved.
 *
 *
 */
package notepad;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.KeyStroke;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

/**
 * 子窗口
 * 
 *  
 * @author zhuhonghui
 * @version 2005-07-08,作成
 */
public class newInteface {
  
  Notepad_Frame1 notepad_Frame = null;
  JTextPane jTextPane = new JTextPane();
  InternalFrame internalFrame = null;
  File file = null;
  static Vector vectorNewInteface  = new Vector();
  private boolean chanageTextPane = false;       //false为不需要保存，true为需要保存
  JPopupMenu jPopupMenu1 = new JPopupMenu();
  JMenuItem jMenuPopCut = new JMenuItem();
  JMenuItem jMenuPopCopy = new JMenuItem();
  JMenuItem jMenuPopPaste = new JMenuItem();
  static String TITLE = null;
  boolean saveFlag = true;          //true为第一次保存，false不是第一次保存
  JavaFile javaFile = new JavaFile();

  public newInteface(Notepad_Frame1 notepad_Frame,String str,boolean flag) {
    this.notepad_Frame = notepad_Frame;
    this.chanageTextPane = flag;
    try {
      jbInit(str,flag);
    } catch(Exception e) {
      e.printStackTrace();
    }
  }
  private void jbInit(String str,boolean flag) throws Exception {
    TITLE = str;
    internalFrame = new InternalFrame(this,str,true,true,true,true,flag);
    vectorNewInteface.addElement(internalFrame);
    internalFrame.setLocation(20,20);
    internalFrame.setSize(600,500);
    internalFrame.setVisible(true);
    notepad_Frame.desktopPane.add(internalFrame);
    Container icontentPane = internalFrame.getContentPane();
    JScrollPane jScrollPane1 = new JScrollPane();
    icontentPane.add(jScrollPane1,BorderLayout.CENTER);
    jScrollPane1.getViewport().add(jTextPane, null);
    
    try {
      internalFrame.setSelected(true);
    } catch (java.beans.PropertyVetoException ex) {
      System.out.println("Exception while selecting");
    }
    
    //键盘单击事件
    jTextPane.addKeyListener(new newInteface_jTextPane_keyAdapter(this));
    
    //鼠标右击事件
    jTextPane.addMouseListener(new newInteface_jTextPane_mouseAdapter(this));
    
    //鼠标移动事件
    jTextPane.addMouseMotionListener(new NewInteface_jTextPane_mouseMotionAdapter(this));
    
    jMenuPopCut.setText("剪切");
    jMenuPopCut.setAccelerator(KeyStroke.getKeyStroke('X',KeyEvent.CTRL_MASK, false));
    jMenuPopCut.addActionListener(new newInteface_jMenuPopCut_actionAdapter(this));
    jMenuPopCopy.setText("复制");
    jMenuPopCopy.setAccelerator(KeyStroke.getKeyStroke('C',KeyEvent.CTRL_MASK, false));
    jMenuPopCopy.addActionListener(new newInteface_jMenuPopCopy_actionAdapter(this));
    jMenuPopPaste.setText("粘贴");
    jMenuPopPaste.setAccelerator(KeyStroke.getKeyStroke('V',KeyEvent.CTRL_MASK, false));
    jMenuPopPaste.addActionListener(new newInteface_jMenuPopPaste_actionAdapter(this));

    jPopupMenu1.add(jMenuPopCut);
    jPopupMenu1.add(jMenuPopCopy);
    jPopupMenu1.add(jMenuPopPaste);
  }

  void setTitle(String title) {
    Enumeration en = vectorNewInteface.elements();
    InternalFrame temp = null;
    while(en.hasMoreElements()) {
      temp =(InternalFrame)en.nextElement();
      if(temp.isSelected()) {
        temp.setTitle(title);
      }
    }
  }

  void setSaveFlag(boolean saveFlag) {
    this.saveFlag = saveFlag;
  }

  void setNewIntefaceTitle(String title) {
    internalFrame.setTitle(title);
  }

  String getNewIntefaceTitle() {
    return internalFrame.getTitle();
  }

  void jTextPane_keyTyped(KeyEvent e) {
    notepad_Frame.jMenuFileSave.setEnabled(true);
    notepad_Frame.jMenuFileSaveAs.setEnabled(true);
    notepad_Frame.jButtonSave.setEnabled(true);
    setChanageTextPane(true);
  }

  void jTextPane_mouseReleased(MouseEvent e) {
    if (e.isPopupTrigger()) {
      jPopupMenu1.show(e.getComponent(), e.getX(), e.getY());
    }
  }

  void jTextPane_mouseMoved(MouseEvent e) {
    notepad_Frame.statusBarXY.setText(getNewIntefaceTitle()+":   X:" + e.getX() + " Y:" + e.getY());
  }

  void close() {
    internalFrame.dispose();
  }

  void setChanageTextPane(boolean chanageTextPane) {
    this.chanageTextPane = chanageTextPane;
    Enumeration en = vectorNewInteface.elements();
    InternalFrame obj = null;
    while(en.hasMoreElements()) {
      obj =(InternalFrame)en.nextElement();
      if(obj.getTitle().equals(TITLE)) {
        obj.setChanageTextPane(chanageTextPane);
        System.out.println(obj.getTitle());/////////////////////////////////////////////////////////////////
      }
    }
  }

  boolean getChanageTextPane() {
    return chanageTextPane;
  }

 void setSelectTitle() {
   Enumeration en = vectorNewInteface.elements();
   InternalFrame temp = null;
   while(en.hasMoreElements()) {
     temp =(InternalFrame)en.nextElement();
     if(temp.isSelected()) {
       TITLE = temp.getTitle();
     }
   }
 }

 void removeClose(String title) {
   Enumeration en = vectorNewInteface.elements();
   InternalFrame obj = null;
   while(en.hasMoreElements()) {
     obj =(InternalFrame)en.nextElement();
     if(obj.getTitle().equals(title)) {
       vectorNewInteface.removeElement(obj);
     }
   }
 }

 void setJInternalFrameEnabled(String title) {
   Enumeration en = vectorNewInteface.elements();
   InternalFrame temp = null;
   while(en.hasMoreElements()) {
     temp = (InternalFrame)en.nextElement();
     if(temp.getTitle().equals(title)) {
       try {
         temp.setSelected(true);
       } catch(Exception e) {}
     }
   }
 }

 void jMenuPopCut_actionPerformed(ActionEvent e) {
   jTextPane.cut();
 }

 void jMenuPopCopy_actionPerformed(ActionEvent e) {
   jTextPane.copy();
 }

 void jMenuPopPaste_actionPerformed(ActionEvent e) {
   jTextPane.paste();
 }

  public void insert(String str,AttributeSet attrset) {
    Document docs=jTextPane.getDocument();//利用getDocument()方法取得JTextPane的Document instance.0
    try {
      docs.insertString(docs.getLength(),str,attrset);
    } catch(BadLocationException ble) {
       System.out.println("BadLocationException:"+ble);
    }
  }
  public void insertString(String str) {
    SimpleAttributeSet attrset=new SimpleAttributeSet();
    StyleConstants.setForeground(attrset,Color.blue);
    StyleConstants.setBold(attrset,true);
    insert(str,attrset);
  }
}

class newInteface_jTextPane_keyAdapter extends KeyAdapter {
  newInteface adaptee;
  newInteface_jTextPane_keyAdapter(newInteface adaptee) {
    this.adaptee = adaptee;
  }
  public void keyTyped(KeyEvent e) {
    adaptee.jTextPane_keyTyped(e);
  }
}

class newInteface_jTextPane_mouseAdapter extends MouseAdapter {
  newInteface adaptee;

  newInteface_jTextPane_mouseAdapter(newInteface adaptee) {
    this.adaptee = adaptee;
  }
  public void mouseReleased(MouseEvent e) {
    adaptee.jTextPane_mouseReleased(e);
  }
}
class NewInteface_jTextPane_mouseMotionAdapter extends MouseMotionAdapter {
  newInteface adaptee;

  NewInteface_jTextPane_mouseMotionAdapter(newInteface adaptee) {
    this.adaptee = adaptee;
  }

  public void mouseMoved(MouseEvent e) {
    adaptee.jTextPane_mouseMoved(e);
  }
}

class newInteface_jMenuPopCut_actionAdapter implements ActionListener {
  newInteface adaptee;

  newInteface_jMenuPopCut_actionAdapter(newInteface adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuPopCut_actionPerformed(e);
  }
}

class newInteface_jMenuPopCopy_actionAdapter implements ActionListener {
  newInteface adaptee;

  newInteface_jMenuPopCopy_actionAdapter(newInteface adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuPopCopy_actionPerformed(e);
  }
}

class newInteface_jMenuPopPaste_actionAdapter implements ActionListener {
  newInteface adaptee;

  newInteface_jMenuPopPaste_actionAdapter(newInteface adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuPopPaste_actionPerformed(e);
  }
}