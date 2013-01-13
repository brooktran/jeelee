package com.test.bean.TextEditor;

import java.beans.BeanInfo;
import java.beans.SimpleBeanInfo;

/**
 * 类<B> TextEditorBeanInfo </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-7 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class TextEditorBeanInfo extends SimpleBeanInfo {
	public java.awt.Image getIcon(int iconType) {
        if (iconType == BeanInfo.ICON_COLOR_16x16) {
                        java.awt.Image img = loadImage("faces.gif");
                        return img;
        }
        if (iconType == BeanInfo.ICON_COLOR_32x32) {
                        java.awt.Image img = loadImage("ImageScaleIconColor32.gif");
                        return img;
        }
        if (iconType == BeanInfo.ICON_MONO_16x16) {
                        java.awt.Image img = loadImage("ImageScaleIconMono16.gif");
                        return img;
        }
        if (iconType == BeanInfo.ICON_MONO_32x32) {
                        java.awt.Image img = loadImage("ImageScaleIconMono32.gif");
                        return img;
        }
        return null;
}

}
