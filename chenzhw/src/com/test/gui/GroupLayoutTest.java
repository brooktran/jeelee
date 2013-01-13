package com.test.gui;

import java.awt.Component;
import java.awt.Container;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 类<B> GroupLayoutTest </B>是
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-7 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class GroupLayoutTest extends JFrame{
	public GroupLayoutTest(){
		super("Find");
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //String laf = UIManager.getCrossPlatformLookAndFeelClassName();
	    String laf = UIManager.getSystemLookAndFeelClassName();
	    try {
	         UIManager.setLookAndFeel(laf);
	    } catch (UnsupportedLookAndFeelException exc) {
	     System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
	    } catch (Exception exc) {
	     System.err.println("Error loading " + laf + ": " + exc);
	    }
	    JLabel label1 = new JLabel("Find What:");
	    JTextField textField1 = new JTextField();
	    JCheckBox caseCheckBox = new JCheckBox("Match Case");
	    JCheckBox wholeCheckBox = new JCheckBox("Whole Words");
	    JCheckBox wrapCheckBox = new JCheckBox("Warp Around");
	    JCheckBox backCheckBox = new JCheckBox("Search Backwards");
	    JButton findButton = new JButton("Find");
	    JButton cancelButton = new JButton("Cancel");
	    JButton buttonTmp=new JButton("test");/////////////////////
	    JButton buttonTmp2=new JButton("test");///////////////////
	    buttonTmp2.setSize(20,20);
	    Container c = getContentPane();
	    GroupLayout layout = new GroupLayout(c);
	    c.setLayout(layout);
	    
	    

		// 自动设定组件、组之间的间隙
	    layout.setAutoCreateGaps(true);
	    layout.setAutoCreateContainerGaps(true);
	    
		// LEADING -- 左对齐 BASELINE -- 底部对齐 CENTER -- 中心对齐
	    GroupLayout.ParallelGroup hpg2a = layout
				.createParallelGroup(GroupLayout.Alignment.LEADING);
	    hpg2a.addComponent(caseCheckBox);
	    hpg2a.addComponent(wholeCheckBox);
	    hpg2a.addComponent(buttonTmp2);////////////////////////////
	    
	    GroupLayout.ParallelGroup hpg2b = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
	    hpg2b.addComponent(wrapCheckBox);
	    hpg2b.addComponent(backCheckBox);
	    
	    GroupLayout.SequentialGroup hpg2H = layout.createSequentialGroup();
	    hpg2H.addGroup(hpg2a).addGroup(hpg2b);

	    GroupLayout.ParallelGroup hpg2 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
	    hpg2.addComponent(textField1);
	    hpg2.addGroup(hpg2H);
	  
	    GroupLayout.ParallelGroup hpg3 = layout.createParallelGroup(GroupLayout.Alignment.LEADING);
	    hpg3.addComponent(findButton);
	    hpg3.addComponent(cancelButton);
	    hpg3.addComponent(buttonTmp);//////////////////////////////////

	   //水平
	    layout.setHorizontalGroup(layout.createSequentialGroup()
	        .addComponent(label1).addGroup(hpg2).addGroup(hpg3));    
	    
	    //设定两个Button在水平方向一样宽
	    layout.linkSize(SwingConstants.HORIZONTAL,new Component[] { findButton, cancelButton,buttonTmp });
	    //layout.linkSize(SwingConstants.HORIZONTAL,new Component[] { caseCheckBox, wholeCheckBox, wrapCheckBox, backCheckBox});

	    layout.linkSize(SwingConstants.HORIZONTAL,new Component[]{wholeCheckBox,buttonTmp2});
	    
	    
	    GroupLayout.ParallelGroup vpg1 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
	    vpg1.addComponent(label1);
	    vpg1.addComponent(textField1);
	    vpg1.addComponent(findButton);
	    
	    GroupLayout.ParallelGroup vpg2 = layout.createParallelGroup(GroupLayout.Alignment.CENTER);
	    vpg2.addComponent(caseCheckBox);
	    vpg2.addComponent(wrapCheckBox);
	    vpg2.addComponent(cancelButton);

	    GroupLayout.ParallelGroup vpg3 = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
	    vpg3.addComponent(wholeCheckBox);
	    vpg3.addComponent(backCheckBox);
	    vpg3.addComponent(buttonTmp);////////////////////////////
	    
	    GroupLayout.ParallelGroup vpg4=layout.createParallelGroup(GroupLayout.Alignment.BASELINE);
	    vpg4.addComponent(buttonTmp2);/////////////////////////

//	垂直
	   layout.setVerticalGroup(layout.createSequentialGroup()
	        .addGroup(vpg1).addGroup(vpg2).addGroup(vpg3).addGroup(vpg4));
	   
	    setLocation(200,200);
	    pack();
	    setVisible(true);


	}
	
	public static void main(String[] args)
	{
	   new GroupLayoutTest();
	}

}





























