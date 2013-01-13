/*
 * ?寶擔婜 2005-7-8
 *
 * TODO 梫峏夵崯惗惉揑暥審揑柾斅丆??帄
 * 鈞岥 亅 庱?? 亅 Java 亅 戙??幃 亅 戙?柾斅
 */
package notepad;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author zhh
 *
 * TODO 梫峏夵崯惗惉揑?宆拲?揑柾斅丆??帄
 * 鈞岥 亅 庱?? 亅 Java 亅 戙??幃 亅 戙?柾斅
 */
public class JavaFileFilter extends FileFilter {
  private String ext;//后缀
  
  public JavaFileFilter(String ext) {
    this.ext = ext;
  }

  public boolean accept(File file) {
    
    if (file.isDirectory()) {
      
      return true;
    }
    
    String fileName = file.getName();
    int index = fileName.lastIndexOf('.');
    if (index > 0 && index < fileName.length() - 1) {
      String extension = fileName.substring(index + 1).toLowerCase();
      if (extension.equals(ext))
        return true;
    }
    return false;
  }

  public String getDescription() {
    if (ext.equals("java")) {
      return "JAVA Source File(*.java)";
    }
    if (ext.equals("html")) {
      return "HTML Source File(*.html)";
    }
    if (ext.equals("txt")) {
      return "Text File(*.txt)";
    }
    if(ext.equals("jsp")) {
      return "Java Server Pages File(*.jsp)";
    }
      return "";
  }
}
