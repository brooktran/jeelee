package com.test.bean.TextEditor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.TextArea;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * File         : TextEditor.java
 * Author       : Mahesh Rengaswamy
 * Revision     : $Revision$
 * Updated      : $Date$
 * Copyright: Northeast Parallel Architectures Center
 *            at Syracuse University 1997
 */

/**
 * 类<B> TextEditor </B>是
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-7 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class TextEditor extends TextArea {
	// /////////////////////////////////////////////////////
	// Private Data Fields (all Java Bean fields should be
	// private, stylistically speaking)
	// ////////////////////////////////////////////////////
	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private static Dimension zero = new Dimension(0, 0);

	private Color textColor, canvasColor;

	private Font textFont;

	private String fontName = "TimesRoman";

	private int fontSize = 12;

	private int fontStyle = Font.PLAIN;

	private boolean debug = false;

	// //////////////////////////////////////////////////
	// End of Bean-relevant Data Fields
	// //////////////////////////////////////////////////

	Graphics g;

	// //////////////////////////////////////////////////
	// // START OF JAVA BEAN RELATED METHODS
	// // FOR PROPERTIES & RESIZE OF
	// // THE TEXT-EDITOR
	// //////////////////////////////////////////////////

	/**
	 * In order to demonstrate another concept in Beans, the boolean property
	 * why don't we have a boolean flag for the debugging information.
	 * Essentially any System.out.println's should take place only if this flag
	 * is true.
	 * 
	 * @param b the b
	 */
	public void setDebug(boolean b){
		boolean oldVale=debug;
		debug=b;
		changes.firePropertyChange("Debug",new Boolean(oldVale),new Boolean(debug));
	}
	
	public boolean getDebug(){
		return debug;
	}
	/*  End of the boolean property */
	
	/**
	 * We want FontName to also apper in the Bean Property sheet, The getter
	 * and setter methods along with the firing of property changes
	 * ensures us that this property behaves as desired i.e. to change the FontName.
	 */
	public void setFontName(String newFontName){
		String oldFontName=fontName;
		fontName=newFontName;
		textFont=new Font(fontName,fontStyle,fontSize);
		this.setFont(textFont);
		changes.firePropertyChange("FontName", oldFontName, fontName);
	}
	
	public String getFontName(){
		return fontName;
	}
	/*End of getter/setter methods for the FontName property.... */
	/*We want FontStyle to also appear in the Bean Property sheet, The getter
    and setter methods along with the firing of property changes 
    ensures us that this property behaves as desired i.e. to change the
    FontStyle */
  
  /*Theres something you gotta remeber here,
    a) For plain styles ===>0
    b) for bold         ===>1
    c) for italics      ===>2
    d)for bold+italics  ===>3
  */
  
  public void setFontStyle(int fStyle) {
    int oldFontStyle = fontStyle;
    fontStyle = fStyle;
    textFont = new Font(fontName, fontStyle, fontSize);
    this.setFont(textFont);
    changes.firePropertyChange("FontStyle",  new Integer(oldFontStyle), 
                               new Integer(fontStyle));     
  }
  
  public int getFontStyle() {
    return fontStyle;    
  }
  
  /*End of getter/setter methods for the FontStyle Property */
  
  ///////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////
  
  /*We want FontSizee to also appear in the Bean Property sheet, The getter
    and setter methods along with the firing of property changes 
    ensures us that this property behaves as desired i.e. to change the
    FontSize */

  public void setFontSize(int fSize) {
      int oldFontSize = fontSize;
      fontSize = fSize;
      if(debug)
        System.out.println("FontSizes" + fontSize);
      textFont = new Font(fontName, fontStyle, fontSize);
      this.setFont(textFont);
      changes.firePropertyChange("FontSize", new Integer(oldFontSize), 
                                 new Integer(fontSize));   
  }

  public int getFontSize() {
    return fontSize;
  }
  
  /*End of getter/setter methods for the FontSize property */

  ///////////////////////////////////////////////////////////////
  ///////////////////////////////////////////////////////////////

  /**
    * Adds a PropertyListener object to a list of PropertyChangeListeners
    */     
  public void addPropertyChangeListener(PropertyChangeListener listen) {
    changes.addPropertyChangeListener(listen);
  }
  
  /** 
    * Removes a PropertyChangeListener from our listener list.
    */     
  public void removePropertyChangeListener(PropertyChangeListener listen) {
    changes.removePropertyChangeListener(listen);
  }

  public Dimension getPreferredSize() {
    Dimension current = getSize();
    /*
     * This method exists as a hack to ensure that when the TextArea is
     * resized by the user that it in fact does resize properly.
     */
    return (current.equals(zero) ? super.getPreferredSize() : current);    
  }

  ////////////////////////////////////////////////////////
  // END OF BEAN-REALTED METHODS AS FAR AS PROPERTIES
  //           ARE CONCERNED
  ////////////////////////////////////////////////////////


  public Insets insets() {
    return new Insets(5, 20, 5, 20);
  }


  /*Now since these methods donot take an argument, they would visible 
    when you try and connect another components event ---- lets say
    mouse clicked to this TextEditor */

  public void loadSerialized() {
    Frame frame0 = new Frame();
    FileDialog fd = new FileDialog(frame0, "Load Serialized", FileDialog.LOAD);
    fd.show();
    String filename = fd.getFile();
    if (filename != null) {
      try {
        FileInputStream fiss = new FileInputStream(filename);
        ObjectInputStream oiss = new ObjectInputStream(fiss);
        String str = (String)oiss.readObject();
        this.setText(str);
      }
      catch (Exception e) {}
    }
  }
  
  public void save() {
    Frame frame1=new Frame();
    //create a file dialog
    FileDialog fd = new FileDialog(frame1, "Save Scribble", FileDialog.SAVE);
    fd.show();
    String filename = fd.getFile();
    if (filename != null) {
      try {
        FileOutputStream fos = new FileOutputStream(filename);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        String str = this.getText();
        oos.writeObject(str);
        oos.flush();
        oos.close();
      }
      catch (IOException e) {
        System.out.println(e);
          }
    }
  }
  
  // Opens a file
  public void load() {
    Frame frame = new Frame();
    FileDialog fd = new FileDialog(frame, "Load Scribble", FileDialog.LOAD);
    fd.show();
    String filename = fd.getFile();
    File f = new File(filename);
    int size = (int)f.length();
    int bytes_read = 0;
    if (filename != null) {
      try {
        FileInputStream fis = new FileInputStream(filename);
        byte[] data = new byte[size];
        while (bytes_read < size) 
          bytes_read += fis.read(data, bytes_read, size-bytes_read);
        this.setText(new String(data));
      }
      catch (Exception e) {
        System.out.println(e);
      }
    }
  }
  
}































