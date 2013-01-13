package com.test.gui;

//: gui/FileChooserTest.java
// Demonstration of File dialog boxes.
import javax.swing.*;
import sun.reflect.generics.tree.Tree;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class FileChooserTest extends JFrame {
  private JTextField
    fileName = new JTextField(),
    dir = new JTextField();
  private JButton
    open = new JButton("Open"),
    save = new JButton("Save");
  public FileChooserTest() {
    JPanel p = new JPanel();
    open.addActionListener(new OpenL());
    p.add(open);
    save.addActionListener(new SaveL());
    p.add(save);
    add(p, BorderLayout.SOUTH);
    dir.setEditable(false);
    fileName.setEditable(false);
    p = new JPanel();
    p.setLayout(new GridLayout(2,1));
    p.add(fileName);
    p.add(dir);
    add(p, BorderLayout.NORTH);
  }
  class OpenL implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser c = new JFileChooser();
      // Demonstrate "Open" dialog:
      int rVal = c.showOpenDialog(FileChooserTest.this);
      if(rVal == JFileChooser.APPROVE_OPTION) {
        fileName.setText(c.getSelectedFile().getName());
        dir.setText(c.getCurrentDirectory().toString());
      }
      if(rVal == JFileChooser.CANCEL_OPTION) {
        fileName.setText("You pressed cancel");
        dir.setText("");
      }
    }
  }
  class SaveL implements ActionListener {
    public void actionPerformed(ActionEvent e) {
      JFileChooser c = new JFileChooser();
      // Demonstrate "Save" dialog:
      int rVal = c.showSaveDialog(FileChooserTest.this);
      if(rVal == JFileChooser.APPROVE_OPTION) {
        fileName.setText(c.getSelectedFile().getName());
        dir.setText(c.getCurrentDirectory().toString());
      }
      if(rVal == JFileChooser.CANCEL_OPTION) {
        fileName.setText("You pressed cancel");
        dir.setText("");
      }
    }
  }
  public static void main(String[] args) {
	  FileChooserTest fileChooserTest=new FileChooserTest();
	  fileChooserTest.setSize(500,500);
	  fileChooserTest.setVisible(true);
  }
  
  
  
  public class ExtensionFileFilter extends javax.swing.filechooser.FileFilter {
	    private String description;
	    private HashSet<String> extensions;
	    private String defaultExtension;
	    
	    /**
	     * Creates a new instance.
	     * @param description A human readable description.
	     * @param extension The filename extension. This will be converted to
	     * lower-case by this method.
	     */
	    public ExtensionFileFilter(String description, String extension) {
	        this.description = description;
	        this.extensions = new HashSet<String>();
	        extensions.add(extension.toLowerCase());
	        defaultExtension = extension;
	    }
	    /**
	     * Creates a new instance.
	     * @param description A human readable description.
	     * @param extensions The filename extensions. These will be converted to
	     * lower-case by this method.
	     */
	    public ExtensionFileFilter(String description, String[] extensions) {
	        this.description = description;
	        this.extensions = new HashSet<String>();
	        
	        String[] extlc = new String[extensions.length];
	        for (int i=0; i < extlc.length; i++) {
	            extlc[i] = extensions[i].toLowerCase();
	        }
	        
	        this.extensions.addAll(Arrays.asList(extlc));
	        defaultExtension = extensions[0];
	    }
	    
	    /**
	     * Returns an unmodifiable set with the filename extensions.
	     * All extensions are lower case.
	     */
	    public Set<String> getExtensions() {
	        return Collections.unmodifiableSet(extensions);
	    }
	    
	    public boolean accept(File pathname) {
	        if (pathname.isDirectory()) {
	            return true;
	        } else {
	            String name = pathname.getName();
	            int p = name.lastIndexOf('.');
	            if (p == -1 || p == name.length() - 1) {
	                return extensions.contains("");
	            } else {
	                return extensions.contains(name.substring(p + 1).toLowerCase());
	            }
	        }
	    }
	    
	    /**
	     * Appends the extension to the filename, in case it is missing.
	     */
	    public File makeAcceptable(File pathname) {
	        if (accept(pathname)) {
	            return pathname;
	        } else {
	            return new File(pathname.getPath()+'.'+defaultExtension);
	        }
	    }
	    
	    public String getDescription() {
	        return description;
	    }
  }
} ///:~
