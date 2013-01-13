package org.chenzhw.swing;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JApplet;

/**
 * <B>ImageBufferedTest</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2009-11-4 created
 * @since chenzhw Ver 1.0
 * 
 */
public class ImageBufferedTest extends JApplet {
	/*
	 * GraphicsConfiguration 类描述图形目标（如打印机或监视器）的特征。有许多与单一图形设备关联的
	 * GraphicsConfiguration 对象，表示不同的绘图模式或能力。相应的本机结构也将因平台而异。例如，在 X11
	 * 窗口系统上，每个可视组件都是一个不同的 GraphicsConfiguration。在 Microsoft Windows
	 * 系统上，GraphicsConfiguration 表示当前分辨率和颜色深度下可用的 PixelFormat。
	 */
	private GraphicsConfiguration gc;
	private BufferedImage im;

	@Override
	public void init() {

		// get this device's graphics configuration.
		GraphicsEnvironment ge = GraphicsEnvironment
		.getLocalGraphicsEnvironment();
		/*
		 * GraphicsEnvironment 类描述了 Java(tm) 应用程序在特定平台上可用的 GraphicsDevice 对象和
		 * Font 对象的集合。此 GraphicsEnvironment
		 * 中的资源可以是本地资源，也可以位于远程机器上。GraphicsDevice 对象可以是屏幕、打印机或图像缓冲区，并且都是
		 * Graphics2D 绘图方法的目标。每个 GraphicsDevice 都有许多与之相关的 GraphicsConfiguration
		 * 对象。这些对象指定了使用 GraphicsDevice 所需的不同配置。
		 */
		gc = ge.getDefaultScreenDevice().getDefaultConfiguration();
		im=loadImage("英国地图.gif");

	}

	/**
	 * Load the image from <name>, returning it as a BufferedImage
	 * which is compatible with the graphics device being used.
	 * Uses ImageIO.
	 * 
	 * @param name the name
	 * 
	 * @return the buffered image
	 */
	public BufferedImage loadImage(String name){
		try {
			BufferedImage im=ImageIO.read(getClass().getResource(name));
			int transparency=im.getColorModel().getTransparency();//透明度
			BufferedImage copy=gc.createCompatibleImage(im.getWidth(), 
					im.getHeight(),transparency);

			Graphics2D g2d=copy.createGraphics();
			g2d.drawImage(im, 0, 0, null);
			g2d.dispose();
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void paint(Graphics g){
		g.drawImage(im, 0, 0, this);
	}

}
