/*
 * ?寶擔婜 2005-7-8
 *
 * TODO 梫峏夵崯惗惉揑暥審揑柾斅丆??帄
 * 鈞岥 亅 庱?? 亅 Java 亅 戙??幃 亅 戙?柾斅
 */
package notepad;

import java.io.File;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileView;

/**
 * @author zhh
 *
 * TODO 梫峏夵崯惗惉揑?宆拲?揑柾斅丆??帄
 * 鈞岥 亅 庱?? 亅 Java 亅 戙??幃 亅 戙?柾斅
 */
public class FileIcon extends FileView {
    
    //********************************************************************
    /**
     * 返回值为null的话,java look and feel功能会处理掉这个项目,<BR>
     * 并取得相关值来加以设置.一般而言可以使用f.getName()当返回值.
     * 
     * @param argFile 
     */
    //********************************************************************
    
    public String getName(File argFile) {
      
  	  return null; 
                      
    }

    //********************************************************************
    /**
     * 返回值为null的话,java look and feel功能会处理掉这个项目,并取得相关<BR>
     * 值来加以设置.你也可以自己设置对此图片的描素,如这是一张风景图片等等.
     * 
     * @param argFile 
     */
    //********************************************************************
    public String getDescription(File argFile) {
      
  	  return null; 
  	                 
    }

    public String getTypeDescription(File f) {
      
      String extension = getExtensionName(f);
        
      if(extension.equals("java")) {
        return "JAVA Source File";
      }
      if(extension.equals("html")) {
        return "HTML File";
      }
      if(extension.equals("txt")) {
        return "Text File";
      }
      if(extension.equals("jsp")) {
        return "Java Server Pages File";
      }
      return "";
    }

    public Icon getIcon(File f) {
      
      String extension = getExtensionName(f);
      if(extension.equals("java")) {
        
        return new ImageIcon(notepad.Notepad_Frame1.class.getResource("java.gif"));
      }
      if(extension.equals("html")) {
        return new ImageIcon(notepad.Notepad_Frame1.class.getResource("html.gif"));
      }
      if(extension.equals("txt")) {
        return new ImageIcon(notepad.Notepad_Frame1.class.getResource("txt.gif"));
      }
      if(extension.equals("jsp")) {
        return new ImageIcon(notepad.Notepad_Frame1.class.getResource("jsp.gif"));
      }
      return null;
    }
    
    //********************************************************************
    /**
     * 返回值为null的话,java look and feel功能会处理掉这个项目,并取得相关<BR>
     * 值来加以设置.若你不希望某个目录被浏览,则返回值可以设为Boolean.FALSE.
     * 
     * @param argFile 
     */
    //********************************************************************
    public Boolean isTraversable(File argFile) {
    	return null; 
                       
    }

    //********************************************************************
    /**
     * 在FileIcon类中我们增加一个getExtensionName()方法,用来返回文件的扩展名<BR>
     * 名称.
     * 
     * @param argFile 
     */
    //********************************************************************
    public String getExtensionName(File argFile) {
     	String extension ="";
  	  String fileName = argFile.getName();
          int index = fileName.lastIndexOf('.');

          if (index > 0 && index < fileName.length()-1) {
              extension = fileName.substring(index+1).toLowerCase();
          }
          return extension;
      }
  }
