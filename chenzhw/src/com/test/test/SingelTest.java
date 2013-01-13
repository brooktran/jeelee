/*package com.test.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle;

*//**
 * 单选题面板
 * 已过时，由TestPanel代替
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-1-4 修改
 * @since OnlineTest Ver 1.01
 * 
 *//*
@Deprecated
public class SingelTest extends JPanel {
	*//**
	 * 版本号
	 *//*
	private static final long serialVersionUID = 1851810174853346937L;
	
	*//**
	 * "上一题"按钮
	 *//*
	private JButton btnFormer;

	*//**
	 * "下一题"按钮
	 *//*
	private JButton btnNext;

	*//**
	 * "提交"按钮
	 *//*
	private JButton btnSummit;

	*//**
	 * "重做"按钮
	 *//*
	private JButton btnCancel;

	*//**
	 * 放置提干的面板
	 *//*
	private JPanel jPanel1;

	*//**
	 * 放置"上一题"按钮和"下一题"按钮的面板
	 *//*
	private JPanel jPanel2;
	
	*//**
	 * 放置"提交"按钮和"重做"按钮的面板
	 *//*
	private JPanel jPanel3;

	*//**
	 * radio按钮组
	 *//*
	private ButtonGroup buttonGroup1;
	
	*//**
	 * 选项按钮组
	 *//*
	private JRadioButton jRadioButton1;

	private JRadioButton jRadioButton2;

	private JRadioButton jRadioButton3;

	private JRadioButton jRadioButton4;

	*//**
	 * 显示题干的文本框
	 *//*
	private JTextArea txtContext;

	private JScrollPane jScrollPane1;

	*//**
	 * 题目映射类
	 *//*
	private ItemMapping itemMapping;

	*//**
	 * 当前题目
	 *//*
	private Item item = new Item();
	
	*//**
	 * 当前各题分数，数组下标对应题号
	 *//*
	private int[] score;
	
	*//**
	 * 实际各题得分
	 *//*
	private int[] realScore;
	
	*//**
	 * 当前题号
	 *//*
	private int currentItem=0;
	
	*//**
	 * 作用:构建答题面板
	 * 
	 * @since OnlineTest
	 *//*
	public SingelTest() {
		//获取item映射
		try {
			itemMapping = new ItemMapping();
			itemMapping.queryByType(itemMapping.SINGLE_ITEMS);
		}  catch (SQLException e) {
			JOptionPane.showMessageDialog(this,"找不到数据库");
			e.printStackTrace();
		}
		
		init();//初始化组件
		
		//初始化分数
		score=new int[itemMapping.getSize()];
		realScore=new int[itemMapping.getSize()];
		for (int i = 0; i < score.length; i++) {
			score[i]=0;
			realScore[i]=0;
		}
		
		//显示题目
		item=itemMapping.getItem(currentItem);
		if (null!=item) {
			setText();
			score[0]=item.score;
		}
	}

	*//**
	 * 作用:初始化面板所有组件(包括该面板所有按钮和文本框)
	 * 
	 *//*
	private void init() {
		buttonGroup1 = new ButtonGroup();
		jRadioButton1 = new JRadioButton();
		jRadioButton2 = new JRadioButton();
		jRadioButton3 = new JRadioButton();
		jRadioButton4 = new JRadioButton();
		btnFormer = new JButton();
		btnNext = new JButton();
		btnSummit = new JButton();
		btnCancel = new JButton();
		txtContext = new JTextArea();
		jScrollPane1 = new JScrollPane();
		jPanel1 = new JPanel();
		jPanel2 = new JPanel();
		jPanel3 = new JPanel();

		setPreferredSize(new java.awt.Dimension(614, 550));

        jPanel1.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255, 153, 51)));

        btnSummit.setText("提交");
       // btnSummit.setEnabled(false);
        btnSummit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnSummitActionPerformed(evt);
            }
        });

        btnCancel.setText("重做");
        //btnCancel.setEnabled(false);
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(btnCancel, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSummit, GroupLayout.Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addComponent(btnSummit, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(186, Short.MAX_VALUE))
        );

        jPanel2.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255, 204, 102)));

        txtContext.setColumns(20);
        txtContext.setRows(5);
        txtContext.setEnabled(false);
        jScrollPane1.setViewportView(txtContext);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("jRadioButton1");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("jRadioButton2");

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("jRadioButton3");

        buttonGroup1.add(jRadioButton4);
        jRadioButton4.setText("jRadioButton4");

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jRadioButton1)
                            .addComponent(jRadioButton2)
                            .addComponent(jRadioButton3)
                            .addComponent(jRadioButton4)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 581, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addComponent(jRadioButton1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton4)
                .addGap(34, 34, 34))
        );

        btnNext.setText("下一题");
        btnNext.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnFormer.setText("上一题");
       // btnFormer.setEnabled(false);
        btnFormer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnFormerActionPerformed(evt);
            }
        });

        GroupLayout jPanel3Layout = new GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addComponent(btnFormer, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                .addGap(95, 95, 95)
                .addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 84, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(jPanel3Layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(btnFormer, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNext, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jPanel2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel1, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addContainerGap(144, Short.MAX_VALUE))
        );

	}

	*//**
	 * 作用:按钮"上一题"的事件
	 * 
	 * @param evt
	 *//*
	private void btnFormerActionPerformed(ActionEvent evt) {
		//getPriorItem();
		getScore();
		if (!getItem(currentItem-1)) {
			JOptionPane.showMessageDialog(this, "已经是第一道题");
		}
		else {
			currentItem--;
			setText();
			this.repaint();
		}
		
	}

	*//**
	 * 作用:按钮"下一题"的事件
	 * 
	 * @param evt
	 *//*
	private void btnNextActionPerformed(ActionEvent evt) {
		getScore();
		if (!getItem(currentItem+1)) {
			JOptionPane.showMessageDialog(this, "已经是最后一道题");
		}
		else {
			currentItem++;
			setText();
			this.repaint();
		}
	}

	*//**
	 * 作用:"提交"按钮的事件
	 * 
	 * @param evt
	 *//*
	private void btnSummitActionPerformed(ActionEvent evt) {
		getScore();
		int tmpScore=0;
		int tmpRealScore=0;
		for (int i = 0; i < score.length; i++) {
			tmpScore += score[i];
		}
		for (int i = 0; i < realScore.length; i++) {
			tmpRealScore += realScore[i];
			
		}
		try {
			JOptionPane.showMessageDialog(this, "本次一共有"+score.length+"道题目:"+"\n" +
					"您的实际分数是:\t"+tmpRealScore+"\n" +
					"所有题目总分数:\t" +tmpScore+"\n" +					
					"      答对率:\t"+(int)(100*tmpRealScore/tmpScore)+"%");
		} catch (ArithmeticException e) {//总分为0时的异常
			JOptionPane.showMessageDialog(this, "本次一共有"+score.length+"道题:"+"\n" +
					"   总的分数是:" +tmpScore+"\n" +
					"您的实际分数是:"+tmpRealScore+"\n" +
					"      答对率:0%");
		}
	}

	private void btnCancelActionPerformed(ActionEvent evt) {
		
	}

	*//**
	 * 作用:显示题目内容
	 *//*
	public void setText() {
		String[] str = item.context.split("@@");
		txtContext.setText(str[0]);
		str = str[1].split("##");
		jRadioButton1.setText(str[0]);
		jRadioButton2.setText(str[1]);
		jRadioButton3.setText(str[2]);
		jRadioButton4.setText(str[3]);
		refreshRadioButton();
	}
	*//**
	 * 作用:获取一道题目
	 * 
	 * @param index
	 * @return true 如果成功获取一道题目
	 *//*
	public boolean getItem(int index){
		Item tmpItem=itemMapping.getItem(index);
		if (null==tmpItem) {
			return false;
		}
		item=tmpItem;
		return true;
	}
	
	
	*//**
	 * 作用:获取下一个题目。从Ver1.01开始，此方法已经被getItem()取代
	 *//*
	@Deprecated
	public void getNextItem() {
		if (itemMapping.hasNext()) {
			item = itemMapping.getNextItem();
			setText();
			//setEnable();
		}
		else {
			//btnNext.setEnabled(false);
			JOptionPane.showMessageDialog(this, "已经是最后一道题");
		}
	}
	
	*//**
	 * 作用:获取上一个题目.从Ver1.01开始，此方法已经被getItem()取代
	 *//*
	@Deprecated
	public void getPriorItem() {
		if (itemMapping.hasPrior()) {
			item = itemMapping.getPriorItem();
			setText();
			//setEnable();
		}
		else {
			JOptionPane.showMessageDialog(this, "已经是第一道题");
		}
	}
	
	*//**
	 * 作用:存储分数，将该题目实际分数暂存于realScore[]数组中，score[]记录该题目的分数
	 * 
	 *//*
	public void getScore() {
		int answer=Integer.parseInt(item.answer);
		realScore[currentItem]=//判断答案是否正确，针对答案添加分数
			jRadioButton1.isSelected()?(answer==1?item.score:0):
				jRadioButton2.isSelected()?(answer==2?item.score:0):
					jRadioButton3.isSelected()?(answer==3?item.score:0):
						jRadioButton4.isSelected()?(answer==4?item.score:0):0;
		score[currentItem]=item.score;
		System.out.println(answer==3);
		System.out.println(jRadioButton1.isSelected());
		System.out.println(jRadioButton2.isSelected());
		System.out.println(jRadioButton3.isSelected());
		System.out.println(jRadioButton4.isSelected());
		System.out.println(realScore[currentItem]);
	}
	
	*//**
	 * 作用:重置选项按钮
	 *//*
	public void refreshRadioButton() {
		buttonGroup1.clearSelection();
	}

}
*/