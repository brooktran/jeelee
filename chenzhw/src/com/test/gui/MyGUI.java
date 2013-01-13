package com.test.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class MyGUI extends JFrame implements ActionListener {
	/**菜单栏*/
	private JMenuBar systeMenuBar = new JMenuBar();
	/**左侧群组*/
	private JGroupPanel groupPanel=new JGroupPanel();
	/**右侧信息页*/
	private JPanel inforPanel=new JPanel();

	/**
	 * 作用:
	 * 
	 * @since eclipse
	 */
	public MyGUI() {
		createMenu();
		createGroupPanel();
		initInforPanel();
	}
	
	public void initInforPanel() {
		JLabel label=new JLabel("欢迎使用在线联机测试系统");
		label.setFont(new Font("Dialog", Font.PLAIN, 30));
		inforPanel.add(label,BorderLayout.CENTER);
		this.add(inforPanel, BorderLayout.CENTER);
	}

	private void createMenu() {
		
		JMenu menuBasic = new JMenu();

		JMenuItem itemBasic1 = new JMenuItem();

		JMenuItem itemBasic2 = new JMenuItem();

		JMenuItem itemExit = new JMenuItem();

		JMenu menuExam = new JMenu();

		JMenuItem itemExam2 = new JMenuItem();
		
		
		
		// setJMenuBar
		this.setJMenuBar(systeMenuBar);

		// setText
		menuBasic.setText("文件");
		itemBasic1.setText("测试1");
		itemBasic2.setText("测试2");
		itemExit.setText("exit");

		menuExam.setText("视图");
		itemExam2.setText("测试3");

		// add
		systeMenuBar.add(menuBasic);
		systeMenuBar.add(menuExam);

		menuBasic.add(itemBasic1);
		menuBasic.add(itemBasic2);
		menuBasic.add(itemExit);

		menuExam.add(itemExit);

		// addActionListener
		itemBasic1.addActionListener(this);
		itemBasic2.addActionListener(this);
		itemExam2.addActionListener(this);
		itemExit.addActionListener(this);
	}

	public void createGroupPanel() {
		//		添加组
		groupPanel.insertGroup(0, "联机测试", Color.white);//new Color(174,213,166)
		groupPanel.insertGroup(1, "题库维护", Color.white);//单色比较恶心
		//添加组成员
		JButton btn1=new JButton("测试1");
		JButton btn2=new JButton("测试2");
		JButton btn3=new JButton("测试3");
		
		btn1.addActionListener(this);
		btn2.addActionListener(this);
		btn3.addActionListener(this);
		
		groupPanel.addGroupComponent(0, btn1);
		groupPanel.addGroupComponent(0, btn2);
		groupPanel.addGroupComponent(1, btn3);
		
		groupPanel.setPreferredSize(new Dimension(140,0));
		
		groupPanel.getGroup(0).getTitleBtn().setPreferredSize(new Dimension(100,23));
		groupPanel.getGroup(0).getTitleBtn().setBackground(new Color(174,213,166));
		groupPanel.getGroup(1).getTitleBtn().setPreferredSize(new Dimension(100,23));
		groupPanel.getGroup(1).getTitleBtn().setBackground(new Color(174,213,166));
		
		btn1.setPreferredSize(new Dimension(110,50));
		btn1.setOpaque(false);//擦除border
		btn1.setForeground(Color.black);
		
		btn2.setPreferredSize(new Dimension(110,50));
		btn2.setOpaque(false);//擦除border
		btn2.setForeground(Color.black);
		
		btn3.setPreferredSize(new Dimension(110,50));
		btn3.setOpaque(false);//擦除border
		btn3.setForeground(Color.black);
		
		groupPanel.getGroup(0).setComponentGap(5, 8);
		groupPanel.getGroup(0).setForeground(Color.white);
		groupPanel.expandGroup(0);
		
		this.getContentPane().add(groupPanel,BorderLayout.WEST);
	}
	
	/*
	 * （非 Javadoc）
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object object = e.getSource();// The object on which the Event
		System.out.println(object.getClass().getName());
		// initially occurred.
		/*if (object==" ") {
			JOptionPane.showMessageDialog((Component) e.getSource(), "dfas");
		}*/
	}
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			// UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			Font font = new Font("Dialog", Font.PLAIN, 12);
			Enumeration keys = UIManager.getLookAndFeelDefaults().keys();
			//Returns an enumeration of the keys in this hashtable. 
			
			 /* UIDefaults javax.swing.UIManager.getLookAndFeelDefaults() Returns
			 * the UIDefaults from the current look and feel, that were obtained
			 * at the time the look and feel was installed.
			 * 
			 * In general, developers should use the UIDefaults returned from
			 * getDefaults(). As the current look and feel may expect certain
			 * values to exist, altering the UIDefaults returned from this
			 * method could have unexpected results.
			 */ 
			 
			//// 将所有字体都设置为font对象中定义的字体
			while (keys.hasMoreElements()) {
				Object key=keys.nextElement();
				if(UIManager.get(key) instanceof Font){
					UIManager.put(key, font);
				}
			}
		} catch (Exception e) {
		}
		JFrame frame=new MyGUI();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("联机测试系统");
		frame.setSize(800,573);
		Dimension dimension=Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((dimension.width-frame.getSize().width)/2,
				(dimension.height-frame.getSize().height)/3);
		
		frame.setVisible(true);
	}
}


















