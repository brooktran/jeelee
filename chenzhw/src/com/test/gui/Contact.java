package com.test.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileFilter;

/**
 * 类<B> WindowTest </B>是程序主界面
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-8 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class Contact extends JFrame {

	/** JAVA风格 */
	private static final String metal = "javax.swing.plaf.metal.MetalLookAndFeel";

	/** motif风格 */
	private static final String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";

	/** windows风格 */
	private static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

	/** 定义窗口风格变量 */
	private static String currentLookAndFeel = metal;

	private JButton btnOpen;

	private JTextArea textArea;

	private File file;

	public Contact() {
		init();
	}

	private void init() {
		btnOpen = new JButton("Open");
		btnOpen.setSize(100,50);
		textArea = new JTextArea("sd");
		textArea.setSize(200,100);
		textArea.setDragEnabled(true);
		

		btnOpen.addActionListener(new OpenAction() );

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

		} catch (ClassNotFoundException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		Container container=getContentPane();
		GroupLayout groupLayout=new GroupLayout(container);
		container.setLayout(groupLayout);
		
		groupLayout.setAutoCreateContainerGaps(true);
		groupLayout.setAutoCreateGaps(true);
		//左对齐
		GroupLayout.ParallelGroup p1=groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		p1.addComponent(btnOpen);
		p1.addComponent(textArea);
		//水平
		groupLayout.setHorizontalGroup(groupLayout.createSequentialGroup().addGroup(p1));
		
		GroupLayout.ParallelGroup v1=groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		v1.addComponent(btnOpen);
		
		GroupLayout.ParallelGroup v2=groupLayout.createParallelGroup(GroupLayout.Alignment.LEADING);
		v2.addComponent(textArea);
		
		GroupLayout.SequentialGroup vGroup=groupLayout.createSequentialGroup();
		vGroup.addGroup(v1).addGroup(v2);
		
		//垂直
		groupLayout.setVerticalGroup(groupLayout.
				createSequentialGroup().addGroup(vGroup));
		
		
		
		
		
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("通讯录");
		setSize(300,500);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((dimension.width - getSize().width) / 2,
				(dimension.height - getSize().height) / 3);
		
		//pack();
		setVisible(true);
	}
	
	class OpenAction extends AbstractAction{
		private void openFile(){
			FileReader fileReader=null;
			StringBuffer stringBuffer=new StringBuffer();
			try {
				fileReader=new FileReader(file);
				int content=0;
				while ((content=fileReader.read())!=-1) {
					String str=String.valueOf((char)content);
					stringBuffer.append(content);
				}
				
			} catch (FileNotFoundException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			} catch (IOException e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}finally{
				if (fileReader!=null) {
					try {
						fileReader.close();
					} catch (IOException e) {
						// TODO 自动生成 catch 块
						e.printStackTrace();
					}
				}
			}
			//~
			textArea.setText(stringBuffer.toString());
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setSelectedFile(file);
			fileChooser.addChoosableFileFilter(new JavaFileFilter("jsp"));
			fileChooser.addChoosableFileFilter(new JavaFileFilter("html"));
			fileChooser.addChoosableFileFilter(new JavaFileFilter("txt"));
			fileChooser.addChoosableFileFilter(new JavaFileFilter("java"));
			fileChooser.addChoosableFileFilter(new JavaFileFilter("vcf"));
			// filechooser.setFileView(new FileIcon());
			int returnValue = fileChooser.showOpenDialog(Contact.this);// ?

			// 如果在弹出的对话框中选择yes或ok的场合
			if (returnValue == JFileChooser.APPROVE_OPTION) {
				file=fileChooser.getSelectedFile();
				if (file!=null) {
					openFile();
				}
			}
		}
	}
	

	public static void main(String[] args) {
		new Contact();
	}

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
			if (ext.equals("vcf")) {
				return "csv File(*.vcf)";
			}
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
		  
}
