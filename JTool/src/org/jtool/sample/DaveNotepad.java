/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DaveNotepad.java
 *
 * Created on 2010-1-4, 13:18:16
 */
package org.jtool.sample;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.awt.print.PrinterException;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.*;
import javax.swing.undo.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.undo.UndoManager;

/**
 *
 * @author root
 */
public class DaveNotepad extends javax.swing.JFrame implements ActionListener, DocumentListener, MouseListener, Transferable {

    private File file = null;//当前文件
    private boolean isNewfile = true;
    private String oldValue;////存放编辑区原来的内容，用于比较文本是否有改动
    private JFileChooser filechooser = new JFileChooser();//文件选择框
    private UndoManager undo = new UndoManager(); // 撤消管理器
    private Font defaultFont = new Font("宋体", Font.PLAIN, 15);//默认字体
    String fontname; //用于工具栏中文字的字体设置选项，获取用户选择的字体型
    int fontsize; //用于工具栏中文字的字号设置选项，获取用户选择的字号大小
    //创建系统剪贴板 
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    public JButton fontOkButton; //字体设置里的"确定"按钮
    JMenu mf, me, mo, mh;//菜单文件、编辑、格式、帮助
    JMenuItem mi1, mi2, mi3, mi4, mi5, mi6, mi7; //文件菜单项
    JMenuItem me1, me12, me2, me3, me4, me5, me6, me7, me8, me9, me10, me11;//编辑菜单项
    JMenuItem mo1, mo2; //格式菜单项
    JMenuItem mh1, mh2;//帮助菜单项
    JButton bt_save, bt_new, bt_open, bt_cut, bt_copy, bt_paste, bt_undo,
            bt_redo, bt_bold, bt_underline, bt_italic; //工具条上的按钮
    JLabel fontlabel;//工具条上的字体标签
    JLabel sizelabel;//工具条上的字号标签
    JLabel statusLabel;//状态栏标签
    JPopupMenu popupMenu;//右键菜单
    JMenuItem popupMenu_Undo, popupMenu_Redo, popupMenu_Cut, popupMenu_Copy,
            popupMenu_Paste, popupMenu_Delete, popupMenu_SelectAll;

    /** Creates new form DaveNotepad */
    public DaveNotepad() {
        super("Dave's Notepad");
        initComponents();


        init();
    }

    private void init() {
        tex.setFont(defaultFont);

//$$$$$$$$$$$$$$以下为菜单的建立$$$$$$$$$$$$$$$$$
        mb.setOpaque(false); //设置透明

        mb.setBorderPainted(true);//设置边框的可见性
        mf = new JMenu("文件(F)", true); //文件菜单
        mf.setMnemonic(KeyEvent.VK_F); //设置快捷键按下ALT+F打开本菜单

        mi1 = new JMenuItem("新建(N)"); //“新建”菜单项
        mi1.setMnemonic('N'); //设置激活键为N
        mi1.addActionListener(this); //添加监听器
        mi1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                InputEvent.CTRL_MASK));//设置快捷键CTRL+N激活本项

        mf.add(mi1); //添加到菜单

        //以下为上面同样的重复
        mi2 = new JMenuItem("打开(O)...");
        mi2.addActionListener(this);
        mi2.setMnemonic('O');
        mi2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,
                InputEvent.CTRL_MASK));
        mf.add(mi2);

        mi3 = new JMenuItem("保存(S)");
        mi3.addActionListener(this);
        mi3.setMnemonic('S');
        mi3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
                InputEvent.CTRL_MASK));
        mf.add(mi3);

        mi4 = new JMenuItem("另存为(A)...");
        mi4.addActionListener(this);
        mi4.setMnemonic('A');
        mf.add(mi4);
        mf.addSeparator(); //添加分隔线
        mi5 = new JMenuItem("页面设置(U)...");
        mi5.addActionListener(this);
        mi5.setMnemonic('U');
        mi5.setEnabled(false);//本功能暂无
        mf.add(mi5);

        mi6 = new JMenuItem("打印(P)...");
        mi6.addActionListener(this);
        mi6.setMnemonic('P');
        mi6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
                InputEvent.CTRL_MASK));
        mf.add(mi6);
        mf.addSeparator(); //添加分割线
        mi7 = new JMenuItem("退出(X)");
        mi7.setMnemonic('X');
        mi7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                InputEvent.CTRL_MASK));
        mi7.addActionListener(this);
        mf.add(mi7);
        mb.add(mf);
        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$文件菜单建立完毕$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$以下为编辑菜单$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
        me = new JMenu("编辑(E)"); //编辑菜单
        me.setMnemonic(KeyEvent.VK_E);
        me1 = new JMenuItem("撤消(Z)");
        me1.addActionListener(this);
        me1.setMnemonic('Z');
        me1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z,
                InputEvent.CTRL_MASK));
        me.add(me1);

        me12 = new JMenuItem("恢复撤销(B)");
        me12.addActionListener(this);
        me12.setMnemonic('B');
        me12.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B,
                InputEvent.CTRL_MASK));
        me.add(me12);
        me.addSeparator();

        me2 = new JMenuItem("剪切(T)");
        me2.addActionListener(this);
        me2.setMnemonic('T');
        me2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
                InputEvent.CTRL_MASK));
        me.add(me2);

        me3 = new JMenuItem("复制(C)");
        me3.addActionListener(this);
        me3.setMnemonic('C');
        me3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
                InputEvent.CTRL_MASK));
        me.add(me3);

        me4 = new JMenuItem("粘贴(P)");
        me4.addActionListener(this);
        me4.setMnemonic('P');
        me4.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,
                InputEvent.CTRL_MASK));
        me.add(me4);

        me5 = new JMenuItem("删除(L)");
        me5.addActionListener(this);
        me5.setMnemonic('L');
        me5.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
                InputEvent.CTRL_MASK));
        me.add(me5);
        me.addSeparator();

        me6 = new JMenuItem("查找(F)...");
        me6.addActionListener(this);
        me6.setMnemonic('F');
        me6.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F,
                InputEvent.CTRL_MASK));
        me.add(me6);

        me7 = new JMenuItem("查找下一个(N)");
        me7.addActionListener(this);
        me7.setMnemonic('N');
        me7.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3,
                InputEvent.CTRL_MASK));

        me.add(me7);

        me8 = new JMenuItem("替换(R)...");
        me8.addActionListener(this);
        me8.setMnemonic('R');
        me8.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
                InputEvent.CTRL_MASK));
        me.add(me8);

        me9 = new JMenuItem("转到(G)...");
        me9.addActionListener(this);
        me9.setMnemonic('G');
        me9.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G,
                InputEvent.CTRL_MASK));
        me.add(me9);
        me.addSeparator();

        me10 = new JMenuItem("全选(A)");
        me10.addActionListener(this);
        me10.setMnemonic('A');
        me10.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                InputEvent.CTRL_MASK));
        me.add(me10);

        me11 = new JMenuItem("时间/日期(D)");
        me11.addActionListener(this);
        me11.setMnemonic('D');
        me11.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5,
                InputEvent.CTRL_MASK));
        me.add(me11);
        mb.add(me);
        //$$$$$$$$$$$$$$$$$$$编辑菜单完毕$$$$$$$$$$$$$$$$$$$$$

        //$$$$$$$$$$$$$$$$$$$一下为格式菜单$$$$$$$$$$$$$$$$$$$
        mo = new JMenu("格式(O)"); //格式菜单
        mo.setMnemonic(KeyEvent.VK_O);
        mo1 = new JMenuItem("自动换行(W)");
        mo1.addActionListener(this);
        mo1.setMnemonic('W');
        mo.add(mo1);

        mo2 = new JMenuItem("字体(F)");
        mo2.addActionListener(this);
        mo2.setMnemonic('F');
// mo2.setEnabled(false);
        mo.add(mo2);
        mb.add(mo);
        //$$$$$$$$$$$$$$$格式菜单完毕$$$$$$$$$$$$$$$$$$$$$$

        //$$$$$$$$$$$$$$以下为帮助菜单$$$$$$$$$$$$$$$$$$$$$
        mh = new JMenu("帮助(H)"); //帮助菜单
        mh.setMnemonic(KeyEvent.VK_H);
        mh1 = new JMenuItem("帮助主题(H)");
        mh1.addActionListener(this);
        mh1.setMnemonic('H');
        mh1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1,
                InputEvent.CTRL_MASK));
        mh.add(mh1);
        mh2 = new JMenuItem("关于本记事本(A)");
        mh2.addActionListener(this);
        mh2.setMnemonic('A');
        mh.add(mh2);
        mb.add(mh);
        //$$$$$$$$$$$$$帮助菜单完毕$$$$$$$$$$$$$$$$$$$$$$$$

        //$$$$$$$$$$$$$$$$$$$以下为工具条的建立$$$$$$$$$$$$$
        Icon new_icon = new ImageIcon("icons/new.gif"); //设置工具条上按钮的图标
        Icon open_icon = new ImageIcon("icons/open.gif");
        Icon save_icon = new ImageIcon("icons/save.gif");
        Icon cut_icon = new ImageIcon("icons/cut.gif");
        Icon copy_icon = new ImageIcon("icons/copy.gif");
        Icon paste_icon = new ImageIcon("icons/paste.gif");
        Icon undo_icon = new ImageIcon("icons/undo.gif");
        Icon redo_icon = new ImageIcon("icons/redo.gif");
        Icon bold_icon = new ImageIcon("icons/bold.gif");
        Icon ltalic_icon = new ImageIcon("icons/italic.gif");
        Icon underline_icon = new ImageIcon("icons/underline.gif");

        tbar.setFloatable(true); //设置工具条为可浮动
        FlowLayout flayout = new FlowLayout(FlowLayout.LEADING, 0, 0); //布局方式
        tbar.setLayout(flayout);
        flayout.setAlignOnBaseline(true);

        bt_new = new JButton(new_icon); //新建文件
        bt_open = new JButton(open_icon); //打开文件
        bt_save = new JButton(save_icon); //保存文件
        bt_undo = new JButton(undo_icon); //撤销
        bt_redo = new JButton(redo_icon); //重做
        bt_cut = new JButton(cut_icon); //剪切
        bt_copy = new JButton(copy_icon); //复制
        bt_paste = new JButton(paste_icon); //粘贴
        bt_bold = new JButton(bold_icon); //粗体
        bt_italic = new JButton(ltalic_icon); //斜体
        bt_underline = new JButton(underline_icon); //下划线

        bt_new.setToolTipText("新建文件");//设置提示文字
        bt_new.addActionListener(this);
        bt_open.setToolTipText("打开文件");
        bt_open.addActionListener(this);
        bt_save.setToolTipText("保存文件");
        bt_save.addActionListener(this);
        bt_undo.setToolTipText("撤销");
        bt_undo.addActionListener(this);
        bt_redo.setToolTipText("恢复撤销");
        bt_redo.addActionListener(this);
        bt_cut.setToolTipText("剪切");
        bt_cut.addActionListener(this);
        bt_copy.setToolTipText("复制");
        bt_copy.addActionListener(this);
        bt_paste.setToolTipText("粘贴");
        bt_paste.addActionListener(this);
        bt_bold.setToolTipText("粗体");
        bt_bold.addActionListener(this);
        bt_italic.setToolTipText("斜体");
        bt_italic.addActionListener(this);
        bt_underline.setToolTipText("下划线");
        //       bt_underline.addActionListener(this);

        tbar.add(bt_new);
        tbar.add(bt_open);
        tbar.add(bt_save);
        tbar.addSeparator();
        tbar.add(bt_undo);
        tbar.add(bt_redo);
        tbar.addSeparator();
        tbar.add(bt_cut);
        tbar.add(bt_copy);
        tbar.add(bt_paste);
        tbar.addSeparator();
        tbar.add(bt_bold);
        tbar.add(bt_italic);
        tbar.add(bt_underline);
        bt_underline.setEnabled(false);

        fontlabel = new JLabel("字体");
        tbar.addSeparator();
        tbar.add(fontlabel);
        //字体标签
        String[] ComboStr1 = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();//获取本地的本地语言的字体
        JComboBox typecombo = new JComboBox(ComboStr1);
        typecombo.setActionCommand("Type");
        typecombo.addActionListener(this);
        typecombo.addItemListener(new ItemListener() {//添加监听器获取选择用户的字体类型

            public void itemStateChanged(ItemEvent e) {
                fontname = (String) e.getItem();
            }
        });
        tbar.add(typecombo);
        //字号标签
        JLabel sizelabel = new JLabel("大小");
        tbar.addSeparator();
        tbar.add(sizelabel);
        String[] ComboStr2 = {"8", "9", "10", "12", "14", "15", "16", "18",
            "20", "21", "22", "24", "26", "28", "30", "36", "48", "54",
            "72", "89"};
        JComboBox sizecombo = new JComboBox(ComboStr2);
        sizecombo.setActionCommand("Size");
        sizecombo.addActionListener(this);
        sizecombo.addItemListener(new ItemListener() {//添加监听器获取选择用户的字号大小

            public void itemStateChanged(ItemEvent e) {
                fontsize = (new Integer((String) e.getItem()).intValue());
            }
        });
        tbar.add(sizecombo);
        //$$$$$$$$$$$$$$工具条完毕$$$$$$$$$$$$$$$$$$$$$$$$

        //$$$$$$$$$$$$$$右键菜单$$$$$$$$$$$$$$$$$$$$$$$$$$
        popupMenu = new JPopupMenu();//右键菜单
        tex.add(popupMenu);
        tex.addMouseListener(this);

        popupMenu_Undo = new JMenuItem("撤销(Z)");
        popupMenu_Redo = new JMenuItem("恢复撤销(B)");
        popupMenu_Cut = new JMenuItem("剪切(X)");
        popupMenu_Copy = new JMenuItem("复制(C)");
        popupMenu_Paste = new JMenuItem("粘贴(V)");
        popupMenu_Delete = new JMenuItem("删除(D)");
        popupMenu_SelectAll = new JMenuItem("全选(A)");

        popupMenu_Undo.setMnemonic('Z');
        popupMenu_Redo.setMnemonic('B');
        popupMenu_Cut.setMnemonic('X');
        popupMenu_Copy.setMnemonic('C');
        popupMenu_Paste.setMnemonic('V');
        popupMenu_Delete.setMnemonic('D');
        popupMenu_SelectAll.setMnemonic('A');

        popupMenu.add(popupMenu_Undo);
        popupMenu.add(popupMenu_Redo);
        popupMenu.addSeparator();
        popupMenu.add(popupMenu_Cut);
        popupMenu.add(popupMenu_Copy);
        popupMenu.add(popupMenu_Paste);
        popupMenu.addSeparator();
        popupMenu.add(popupMenu_Delete);
        popupMenu.addSeparator();
        popupMenu.add(popupMenu_SelectAll);

        popupMenu_Undo.addActionListener(this);
        popupMenu_Redo.addActionListener(this);
        popupMenu_Cut.addActionListener(this);
        popupMenu_Copy.addActionListener(this);
        popupMenu_Paste.addActionListener(this);
        popupMenu_Delete.addActionListener(this);
        popupMenu_SelectAll.addActionListener(this);

        //$$$$$$$$$$$$$$右键菜单完毕$$$$$$$$$$$$$$$$$$$$$$

//以下为实现撤销功能
        tex.getDocument().addUndoableEditListener(new UndoableEditListener() {

            public void undoableEditHappened(UndoableEditEvent e) {//添加撤销管理器
                undo.addEdit(e.getEdit());
            }
        });
        tex.getDocument().addDocumentListener(this);//添加监听器

        statusLabel = new JLabel("按Ctrl+F1获取帮助");//添加状态栏
        this.getContentPane().add(statusLabel, BorderLayout.SOUTH);
        setSize(580, 700);
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tbar = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        tex = new javax.swing.JTextArea();
        mb = new javax.swing.JMenuBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tbar.setRollover(true);
        getContentPane().add(tbar, java.awt.BorderLayout.PAGE_START);

        tex.setColumns(20);
        tex.setRows(5);
        jScrollPane1.setViewportView(tex);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);
        setJMenuBar(mb);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DaveNotepad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(DaveNotepad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DaveNotepad.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DaveNotepad.class.getName()).log(Level.SEVERE, null, ex);
                }
                Font font = new Font("Dialog", Font.PLAIN, 12); //一下是改变默认的组建上显示的字体，这样更加美观一些
                UIManager.put("MenuBar.font", font);
                UIManager.put("MenuItem.font", font);
                UIManager.put("Menu.font", font);
                UIManager.put("PopupMenu.font", font);
                UIManager.put("ToolBar.font", font);
                UIManager.put("ToolTip.font", font);
                UIManager.put("TabbedPane.font", font);
                UIManager.put("Label.font", font);
                UIManager.put("List.font", font);
                UIManager.put("ComboBox.font", font);
                UIManager.put("Button.font", font);
                UIManager.put("Table.font", font);
                UIManager.put("TableHeader.font", font);
                UIManager.put("Tree.font", font);
                UIManager.put("TextField.font", font);
                UIManager.put("TextArea.font", font);
                UIManager.put("TitledBorder.font", font);
                UIManager.put("OptionPane.font", font);
                UIManager.put("RadioButton.font", font);
                UIManager.put("CheckBox.font", font);
                UIManager.put("ToggleButton.font", font);
                UIManager.put("Dialog.font", font);
                UIManager.put("Panel.font", font);
                new DaveNotepad().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuBar mb;
    private javax.swing.JToolBar tbar;
    private javax.swing.JTextArea tex;
    // End of variables declaration//GEN-END:variables

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_new || e.getSource() == mi1) {//新建
            tex.requestFocusInWindow();
            Object[] options = {"确定", "取消"};
            int s = JOptionPane.showOptionDialog(null, "请注意保存文档！按“确定”新建文件",
                    "警告！", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE, null, options, options[0]);
            if (s == JOptionPane.YES_OPTION) {
                tex.setText("");
            }
            statusLabel.setText("空白文档");
            file = null;

        } else if (e.getSource() == mi2 || e.getSource() == bt_open) {//打开
            tex.requestFocusInWindow();
            if (file != null) {
                filechooser.setSelectedFile(file);
            }
            filechooser.addChoosableFileFilter(new JAVAFileFilter("txt"));//实现文件过滤，见下面的JAVAFileFilter类
            filechooser.addChoosableFileFilter(new JAVAFileFilter("c"));
            filechooser.addChoosableFileFilter(new JAVAFileFilter("cpp"));
            filechooser.addChoosableFileFilter(new JAVAFileFilter("sql"));
            filechooser.addChoosableFileFilter(new JAVAFileFilter("doc"));
            filechooser.addChoosableFileFilter(new JAVAFileFilter("class"));
            filechooser.addChoosableFileFilter(new JAVAFileFilter("java"));
            int returnVal = filechooser.showOpenDialog(DaveNotepad.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = filechooser.getSelectedFile();
                openFile(tex);
                isNewfile = false; //文件为已保存的
                statusLabel.setText("当前打开文件:" + file.getAbsoluteFile() + "   行：" + Integer.toString(tex.getRows()) + " 列：" + Integer.toString(tex.getColumns()));//设置状态栏显示当前打开文档的路径和文件名
                tex.setCaretPosition(0);//设置光标的位置在开始

            }
        } else if (e.getSource() == mi3 || e.getSource() == bt_save) {//保存
            tex.requestFocusInWindow();
            saveFile(tex);

        } else if (e.getSource() == mi4) { //另存为
            tex.requestFocusInWindow();
            isNewfile = true;
            saveFile(tex);

        } else if (e.getSource() == mi5) {//页面设置
            tex.requestFocusInWindow();
        } else if (e.getSource() == mi6) {//打印
            tex.requestFocusInWindow();
            try {
                tex.print();
            } catch (PrinterException e1) {
                e1.printStackTrace();
            }
        } else if (e.getSource() == mi7) {//退出
            tex.requestFocusInWindow();
            quitProg();

        } else if (e.getSource() == me1 || e.getSource() == bt_undo || e.getSource() == popupMenu_Undo) {//撤销
            tex.requestFocusInWindow();

            if (undo.canUndo()) {
                try {
                    undo.undo();
                    me12.setEnabled(true);
                    popupMenu_Redo.setEnabled(true);
                    bt_redo.setEnabled(true);
                } catch (CannotUndoException ex) {
                    System.out.println("Unable to undo: " + ex);
                    ex.printStackTrace();
                }
            }
            if (!undo.canUndo()) {
                me1.setEnabled(false);
                popupMenu_Undo.setEnabled(false);
                bt_undo.setEnabled(false);

                me12.setEnabled(true);
                popupMenu_Redo.setEnabled(true);
                bt_redo.setEnabled(true);
            }
        } else if (e.getSource() == me12 || e.getSource() == bt_redo || e.getSource() == popupMenu_Redo) {//恢复撤销
            tex.requestFocusInWindow();

            if (undo.canRedo()) {
                try {
                    undo.redo();
                    me12.setEnabled(true);
                    popupMenu_Redo.setEnabled(true);
                    bt_redo.setEnabled(true);
                } catch (CannotUndoException ex) {
                    System.out.println("Unable to redo: " + ex);
                    ex.printStackTrace();
                }
            }
            if (!undo.canRedo()) {
                me1.setEnabled(true);
                popupMenu_Undo.setEnabled(true);
                bt_undo.setEnabled(true);

                me12.setEnabled(false);
                popupMenu_Redo.setEnabled(false);
                bt_redo.setEnabled(false);
            }
        } else if (e.getSource() == me2 || e.getSource() == bt_cut || e.getSource() == popupMenu_Cut) {//剪切
            tex.requestFocusInWindow();
            tex.cut();
            checkMenuItemEnabled();//检查菜单的可用性
        } else if (e.getSource() == me3 || e.getSource() == bt_copy || e.getSource() == popupMenu_Copy) {//复制
            tex.requestFocusInWindow();
            tex.copy();
            checkMenuItemEnabled();//检查菜单的可用性
        } else if (e.getSource() == me4 || e.getSource() == bt_paste || e.getSource() == popupMenu_Paste) {//粘贴
            tex.requestFocusInWindow();
            tex.paste();
            checkMenuItemEnabled();//检查菜单的可用性
        } else if (e.getSource() == me5 || e.getSource() == popupMenu_Delete) {//删除
            tex.requestFocusInWindow();
            String sCopy = tex.getSelectedText();//获取当前选中文本
            if (sCopy != null) {
                tex.replaceSelection("");
            }
            checkMenuItemEnabled();//检查菜单的可用性
        } else if (e.getSource() == me6) {//查找
            tex.requestFocusInWindow();
            mySearch();
        } else if (e.getSource() == me7) {//查找下一个
            tex.requestFocusInWindow();
            mySearch();
        } else if (e.getSource() == me8) {//替换
            tex.requestFocusInWindow();
            mySearch();
        } else if (e.getSource() == me9) {//转到
            tex.requestFocusInWindow();
        } else if (e.getSource() == me10 || e.getSource() == popupMenu_SelectAll) {//全选
            tex.requestFocusInWindow();
            tex.selectAll();
        } else if (e.getSource() == me11) {//时间/日期
            tex.requestFocusInWindow();
            getDate();
        } else if (e.getSource() == mo1) { //设置文本域可以自动换行
            tex.requestFocusInWindow();
            if (tex.getLineWrap()) {
                tex.setLineWrap(false);
            } else {
                tex.setLineWrap(true);
            }
        } else if (e.getSource() == mo2) {//字体
            tex.requestFocusInWindow();
            SetFont setfont = new SetFont(tex);
        } else if (e.getSource() == mh1) {//帮助主题

            helpFrame();
        } else if (e.getSource() == mh2) {//关于本记事本
            aboutFrame();
        } else if (e.getSource() == bt_bold) {//粗体设置
            Font newfont;
            if (tex.getFont().getStyle() == Font.BOLD) {
                newfont = new Font(tex.getFont().getName(), Font.PLAIN, tex.getFont().getSize());
            } else if (tex.getFont().getStyle() == Font.BOLD + Font.ITALIC) {
                newfont = new Font(tex.getFont().getName(), Font.PLAIN + Font.ITALIC, tex.getFont().getSize());
            } else {
                newfont = new Font(tex.getFont().getName(), tex.getFont().getStyle() + Font.BOLD, tex.getFont().getSize());
            }
            tex.setFont(newfont);
        } else if (e.getSource() == bt_italic) {
            Font newfont;
            if (tex.getFont().getStyle() == Font.ITALIC) {
                newfont = new Font(tex.getFont().getName(), Font.PLAIN, tex.getFont().getSize());
            } else if (tex.getFont().getStyle() == Font.ITALIC + Font.BOLD) {
                newfont = new Font(tex.getFont().getName(), Font.PLAIN + Font.BOLD, tex.getFont().getSize());
            } else {
                newfont = new Font(tex.getFont().getName(), tex.getFont().getStyle() + Font.ITALIC, tex.getFont().getSize());
            }
            tex.setFont(newfont);
        } else if (e.getActionCommand() == "Type") {
            Font newfont = new Font(fontname, tex.getFont().getStyle(), tex.getFont().getSize());
            tex.setFont(newfont);
        } else if (e.getActionCommand() == "Size") {
            Font newfont = new Font(tex.getFont().getName(), tex.getFont().getStyle(), fontsize);
            tex.setFont(newfont);
        }


    }

    void quitProg() { //退出菜单的响应
        Object[] options = {"确定", "取消"};
        int s = JOptionPane.showOptionDialog(null, "请注意保存文档！按“确定”退出", "警告！",
                JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                options, options[0]);
        if (s == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    void getDate() { //获取当前系统日期和时间，对“日期/时间”菜单的响应
        SimpleDateFormat TimeFormat = new SimpleDateFormat(
                " yyyy-MM-dd HH:mm:ss ");
        Calendar curdate = Calendar.getInstance();
        curdate = Calendar.getInstance(Locale.CHINESE); //以上为获取系统当前的日期和时间
        tex.insert(TimeFormat.format(curdate.getTime()), tex.getCaretPosition()); //将获得的日期和时间添加到文本的当前位置
    }

    void helpFrame() { //帮助菜单的弹出窗口
        Icon help_icon = new ImageIcon("icons//doit.jpg");
        String help = "感谢您的使用！\n本记事本程序暂无帮助，您可以参考WindowsXP的记事本帮助程序！\n" + "欢迎和作者联系交流！\n" + "Build By Davezhang\nQQ:442803117\nBolg:http://hi.baidu.com.442803117" + "\nEmail:442803117@qq.com";
        JOptionPane.showConfirmDialog(null, help, "帮助",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                help_icon);
    }

    void aboutFrame() {//“关于本记事本”菜单的弹出窗口
        Icon help_icon = new ImageIcon("icons//doit.jpg");
        String help = "DOIT(R)记事本\n版本1.0 (内部版本号 2008.xp_sp2_dave.080423-10)\n版权" + "所有 （C） 2008-2008 DOIT。\n最终解释权归本人所有，" + "授权给：\n\nBuild By Davezhang\nQQ:442803117\nBolg:http://hi.baidu.com.442803117" + "\nEmail:442803117@qq.com";
        JOptionPane.showConfirmDialog(null, help, "帮助",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                help_icon);
    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$保存文件和另存为文件
    void saveFile(JTextArea text) {//保存文件和另存为文件
        tex.requestFocusInWindow();
        if (isNewfile) { //如果是未保存过的文件

            JFileChooser fileChooser = new JFileChooser();
            /*fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("txt"));
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("c"));
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("cpp"));
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("sql"));
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("doc"));
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("class"));
            fileChooser.addChoosableFileFilter(new JAVAFileFilter("java"));*/
            fileChooser.setApproveButtonText("确定");
            fileChooser.setDialogTitle("另存为");

            int result = fileChooser.showSaveDialog(this);

            if (result == JFileChooser.CANCEL_OPTION) {
                statusLabel.setText("没有选择任何文件");
                return;
            }

            File saveFileName = fileChooser.getSelectedFile();
            if (saveFileName == null || saveFileName.getName().equals("")) {
                JOptionPane.showMessageDialog(this, "不合法的文件名", "不合法的文件名",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    FileWriter fw = new FileWriter(saveFileName);
                    BufferedWriter bfw = new BufferedWriter(fw);
                    bfw.write(tex.getText(), 0, tex.getText().length());
                    bfw.flush();
                    fw.close();
                    isNewfile = false;
                    file = saveFileName;
                    oldValue = tex.getText();
                    statusLabel.setText("　当前打开文件:" + saveFileName.getAbsoluteFile());
                } catch (IOException ioException) {
                }
            }
        } else {//isNewfile为flase时，即文件已经保存过的情况
            try {
                FileWriter fw = new FileWriter(file);
                BufferedWriter bfw = new BufferedWriter(fw);
                bfw.write(tex.getText(), 0, tex.getText().length());
                bfw.flush();
                fw.close();
            } catch (IOException ioException) {
            }
        }
    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$打开文件
    void openFile(JTextArea text) { //打开文件
        try {
            FileReader fr = new FileReader(file);
            int len = (int) file.length();
            char[] buffer = new char[len];
            fr.read(buffer, 0, len);
            fr.close();
            text.setText(new String(buffer));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$查找和替换功能$$$$$$$$$$$$$$$$$$
    public void mySearch() {
        final JDialog findDialog = new JDialog(this, "查找与替换", true);
        Container con = findDialog.getContentPane();
        con.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel searchContentLabel = new JLabel("查找内容(N) :");
        JLabel replaceContentLabel = new JLabel("替换为(P)　 :");
        final JTextField findText = new JTextField(30);
        final JTextField replaceText = new JTextField(30);
        final JCheckBox matchcase = new JCheckBox("区分大小写(C)");

        ButtonGroup bGroup = new ButtonGroup();
        final JRadioButton up = new JRadioButton("向上(U)");
        final JRadioButton down = new JRadioButton("向下(D)");
        down.setSelected(true);
        bGroup.add(up);
        bGroup.add(down);
        JButton searchNext = new JButton("查找下一个(F)");
        JButton replace = new JButton("替换(R)");
        final JButton replaceAll = new JButton("全部替换(A)");
        //"替换"按钮的事件处理
        replace.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (replaceText.getText().length() == 0 && tex.getSelectedText() != null) {
                    tex.replaceSelection("");
                }
                if (replaceText.getText().length() > 0 && tex.getSelectedText() != null) {
                    tex.replaceSelection(replaceText.getText());
                }
            }
        });
        //"替换全部"按钮的事件处理
        replaceAll.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                tex.setCaretPosition(0); //将光标放到编辑区开头
                int a = 0, b = 0, replaceCount = 0;
                if (findText.getText().length() == 0) {
                    JOptionPane.showMessageDialog(findDialog, "请填写查找内容!", "提示",
                            JOptionPane.WARNING_MESSAGE);
                    findText.requestFocus(true);
                    return;
                }
                while (a > -1) {
                    int FindStartPos = tex.getCaretPosition();
                    String str1, str2, str3, str4, strA, strB;
                    str1 = tex.getText();
                    str2 = str1.toLowerCase();
                    str3 = findText.getText();
                    str4 = str3.toLowerCase();

                    if (matchcase.isSelected()) {
                        strA = str1;
                        strB = str3;
                    } else {
                        strA = str2;
                        strB = str4;
                    }
                    if (up.isSelected()) {
                        if (tex.getSelectedText() == null) {
                            a = strA.lastIndexOf(strB, FindStartPos - 1);
                        } else {
                            a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                        }
                    } else if (down.isSelected()) {
                        if (tex.getSelectedText() == null) {
                            a = strA.indexOf(strB, FindStartPos);
                        } else {
                            a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                        }
                    }

                    if (a > -1) {
                        if (up.isSelected()) {
                            tex.setCaretPosition(a);
                            b = findText.getText().length();
                            tex.select(a, a + b);
                        } else if (down.isSelected()) {
                            tex.setCaretPosition(a);
                            b = findText.getText().length();
                            tex.select(a, a + b);
                        }
                    } else {
                        if (replaceCount == 0) {
                            JOptionPane.showMessageDialog(findDialog,
                                    "找不到您查找的内容!", "记事本",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(findDialog, "成功替换" + replaceCount + "个匹配内容!", "替换成功",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    if (replaceText.getText().length() == 0 && tex.getSelectedText() != null) {
                        tex.replaceSelection("");
                        replaceCount++;
                    }

                    if (replaceText.getText().length() > 0 && tex.getSelectedText() != null) {
                        tex.replaceSelection(replaceText.getText());
                        replaceCount++;
                    }
                }//end while
            }
        }); //"替换全部"按钮的事件处理结束

        //"查找下一个"按钮事件处理
        searchNext.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int a = 0, b = 0;
                int FindStartPos = tex.getCaretPosition();
                String str1, str2, str3, str4, strA, strB;
                str1 = tex.getText();
                str2 = str1.toLowerCase();
                str3 = findText.getText();
                str4 = str3.toLowerCase();

                //"区分大小写"的CheckBox被选中
                if (matchcase.isSelected()) {
                    strA = str1;
                    strB = str3;
                } else {
                    strA = str2;
                    strB = str4;
                }
                if (up.isSelected()) {
                    if (tex.getSelectedText() == null) {
                        a = strA.lastIndexOf(strB, FindStartPos - 1);
                    } else {
                        a = strA.lastIndexOf(strB, FindStartPos - findText.getText().length() - 1);
                    }
                } else if (down.isSelected()) {
                    if (tex.getSelectedText() == null) {
                        a = strA.indexOf(strB, FindStartPos);
                    } else {
                        a = strA.indexOf(strB, FindStartPos - findText.getText().length() + 1);
                    }
                }
                if (a > -1) {
                    if (up.isSelected()) {
                        tex.setCaretPosition(a);
                        b = findText.getText().length();
                        tex.select(a, a + b);
                    } else if (down.isSelected()) {
                        tex.setCaretPosition(a);
                        b = findText.getText().length();
                        tex.select(a, a + b);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "找不到您查找的内容!", "记事本",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });//"查找下一个"按钮事件处理结束

        //"取消"按钮及事件处理
        JButton cancel = new JButton("取消");
        cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                findDialog.dispose();
            }
        });
        //创建"查找与替换"对话框的界面
        JPanel bottomPanel = new JPanel();
        JPanel centerPanel = new JPanel();
        JPanel topPanel = new JPanel();
        JPanel direction = new JPanel();

        direction.setBorder(BorderFactory.createTitledBorder("方向"));
        direction.add(up);
        direction.add(down);

        JPanel replacePanel = new JPanel();
        replacePanel.setLayout(new GridLayout(1, 2));
        replacePanel.add(replace);
        replacePanel.add(replaceAll);

        topPanel.add(searchContentLabel);
        topPanel.add(findText);
        topPanel.add(searchNext);

        centerPanel.add(replaceContentLabel);
        centerPanel.add(replaceText);
        centerPanel.add(replacePanel);

        bottomPanel.add(matchcase);
        bottomPanel.add(direction);
        bottomPanel.add(cancel);

        con.add(topPanel);
        con.add(centerPanel);
        con.add(bottomPanel);
        con.add(replacePanel);
        //设置"查找与替换"对话框的大小、可更改大小(否)、位置和可见性
        findDialog.setSize(550, 240);
        findDialog.setResizable(true);
        findDialog.setLocation(230, 280);
        findDialog.setVisible(true);
    }//方法mySearch()结束
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

//$$$$$$$$$$$$$$$$$$$$设置菜单项的可用性:剪切,复制,粘贴,删除功能$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
    public void checkMenuItemEnabled() {
        //主要是通过判断编辑区是否有文本被选中来设置这些操作(剪切,复制,删除)的可用性;
        //通过判断剪贴板信息设置粘贴选项的可用性
        String selectText = tex.getSelectedText();
        if (selectText == null) {
            me2.setEnabled(false); //剪切
            popupMenu_Cut.setEnabled(false);
            bt_cut.setEnabled(false);
            me3.setEnabled(false); //复制
            popupMenu_Copy.setEnabled(false);
            bt_copy.setEnabled(false);
            me5.setEnabled(false); //删除
            popupMenu_Delete.setEnabled(false);

        } else {
            me2.setEnabled(true); //剪切
            popupMenu_Cut.setEnabled(true);
            bt_cut.setEnabled(true);
            me3.setEnabled(true); //复制
            popupMenu_Copy.setEnabled(true);
            bt_copy.setEnabled(true);
            me5.setEnabled(true); //删除
            popupMenu_Delete.setEnabled(true);

        }
        //通过判断系统剪贴板判定粘贴功能可用性判断
        Transferable contents = clipboard.getContents(this);
        if (contents == null) {
            me4.setEnabled(false);
            popupMenu_Paste.setEnabled(false);
            bt_paste.setEnabled(false);
        } else {
            me4.setEnabled(true);
            popupMenu_Paste.setEnabled(true);
            bt_paste.setEnabled(true);
        }
    }//方法checkMenuItemEnabled()结束
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$实现MouseListener接口中的方法
    public void mouseClicked(MouseEvent e) {
        checkMenuItemEnabled();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
        checkMenuItemEnabled();
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {//显示右键菜单
        if (e.isPopupTrigger())// getY() 返回事件相对于源组件的垂直 y 坐标。 int getYOnScreen() 返回事件的绝对垂直 y 坐标。
        //此处用前者会出现菜单位置问题
        {
            popupMenu.show(this, e.getXOnScreen(), e.getYOnScreen());
        }
    }

//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$实现DocumentListener接口中的方法
    public void changedUpdate(DocumentEvent arg0) {

        me1.setEnabled(true);
        popupMenu_Undo.setEnabled(true);
        bt_undo.setEnabled(true);

    }

    public void insertUpdate(DocumentEvent arg0) {

        me1.setEnabled(true);
        popupMenu_Undo.setEnabled(true);
        bt_undo.setEnabled(true);

    }

    public void removeUpdate(DocumentEvent arg0) {

        me1.setEnabled(true);
        popupMenu_Undo.setEnabled(true);
        bt_undo.setEnabled(true);

    }

//$$$
//$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$实现Transferable接口的方法
    public Object getTransferData(DataFlavor arg0)
            throws UnsupportedFlavorException, IOException {
        // TODO 自动生成方法存根
        return null;
    }

    public DataFlavor[] getTransferDataFlavors() {
        // TODO 自动生成方法存根
        return null;
    }

    public boolean isDataFlavorSupported(DataFlavor arg0) {
        // TODO 自动生成方法存根
        return false;
    }

    class JAVAFileFilter extends FileFilter {//实现文件过滤

        String ext;

        public JAVAFileFilter(String ext) {
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
                if (extension.equals(ext)) {
                    return true;
                }
            }
            return false;
        }

        public String getDescription() {
            if (ext.equals("txt")) {
                return "记事本文件(*.txt)";
            }
            if (ext.equals("c")) {
                return "C源文件 (*.c)";
            }
            if (ext.equals("cpp")) {
                return "C++源文件(*.cpp)";
            }
            if (ext.equals("sql")) {
                return "SQL文件(*.sql)";
            }
            if (ext.equals("doc")) {
                return "Word文档(*.doc)";
            }
            if (ext.equals("java")) {
                return "JAVA源文件 (*.java)";
            }
            if (ext.equals("class")) {
                return "JAVA类文件(*.class)";
            }
            return "";
        }
    }

    class Win extends WindowAdapter {

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    }
//############################################字体设置类###################################

    class SetFont extends JFrame implements ActionListener {

        /**
         * 模拟系统记事本的格式菜单下的字体设置菜单，并新增加了颜色设置功能
         */
// private static final long serialVersionUID = 1L;
        final JDialog fontDialog;
        final JTextField tfFont, tfSize, tfStyle;
        final int fontStyleConst[] = {Font.PLAIN, Font.BOLD, Font.ITALIC,
            Font.BOLD + Font.ITALIC};
        final JList listStyle, listFont, listSize;
        Color backgroundColor, fontColor;
        JLabel sample;
        Font newFont;
        JButton fontOkButton;
        JTextArea editArea;

//构造函数SetFont
        public SetFont(JTextArea tex) {
            fontDialog = new JDialog(this, "字体设置", true);
            Container con = fontDialog.getContentPane();
            con.setLayout(new BorderLayout());

            editArea = tex;
            backgroundColor = editArea.getBackground();
            fontColor = editArea.getForeground();

            JPanel topPanel = new JPanel(); //存放字体、字形、字号、颜色、预览面板的面板
            topPanel.setLayout(new GridLayout(2, 3));

            JPanel basePanel = new JPanel(); //存放确定和取消按钮的面板
            basePanel.setLayout(new FlowLayout());

            Font currentFont = editArea.getFont();//获取当前编辑文本的字体

            JPanel fontPanel = new JPanel(); //字体设置面板
            JPanel stylePanel = new JPanel(); //字形设置面板
            JPanel sizePanel = new JPanel(); //字号设置面板

            JPanel fontcolorPanel = new JPanel(); //字体颜色设置面板
            fontcolorPanel.setLayout(new BorderLayout());
            JPanel backgroundcolorPanel = new JPanel(); //背景颜色设置面板
            backgroundcolorPanel.setLayout(new BorderLayout());
            JPanel demoPanel = new JPanel(new BorderLayout()); //预览面板

            final JLabel bgclabel = new JLabel("单击改变背景颜色");
            bgclabel.setOpaque(true); //设置透明
            bgclabel.setHorizontalAlignment(SwingConstants.CENTER); //设置文字剧中
            bgclabel.setBackground(Color.WHITE); //设置背景色为白色，此处为初始化的文本区背景色
            bgclabel.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                }

                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                }

                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                }

                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    Color color = JColorChooser.showDialog(SetFont.this, "设置背景颜色", Color.BLACK);
                    if (color != null) //若为null值表示用户按下Cancel按钮
                    {
                        backgroundColor = color;
                        bgclabel.setBackground(backgroundColor);
                        sample.setBackground(backgroundColor);
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                }
            });

            final JLabel fclabel = new JLabel("单击改变字体颜色");
            fclabel.setHorizontalAlignment(SwingConstants.CENTER);
            fclabel.setOpaque(true);
            fclabel.setBackground(Color.BLACK); //此处为设置字体的颜色，初始化为黑色
            fclabel.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent e) {
                    // TODO Auto-generated method stub
                }

                public void mouseEntered(MouseEvent e) {
                    // TODO Auto-generated method stub
                }

                public void mouseExited(MouseEvent e) {
                    // TODO Auto-generated method stub
                }

                public void mousePressed(MouseEvent e) {
                    // TODO Auto-generated method stub
                    Color color = JColorChooser.showDialog(SetFont.this, "设置字体颜色", Color.BLACK);
                    if (color != null) //若为null值表示用户按下Cancel按钮
                    {
                        fontColor = color;
                        fclabel.setBackground(fontColor);
                        sample.setForeground(fontColor);
                    }
                }

                public void mouseReleased(MouseEvent e) {
                    // TODO Auto-generated method stub
                }
            });

            JLabel lblFont = new JLabel("字体(F):");
            lblFont.setPreferredSize(new Dimension(80, 20));
            JLabel lblStyle = new JLabel("字形(Y):");
            lblStyle.setPreferredSize(new Dimension(80, 20));
            JLabel lblSize = new JLabel("大小(S):");
            lblSize.setPreferredSize(new Dimension(80, 20));
            tfFont = new JTextField(12);   //设置可显示的列数
            tfFont.setText(currentFont.getFontName());
            tfFont.selectAll();
            tfFont.setPreferredSize(new Dimension(20, 20));

            tfStyle = new JTextField(10);
            if (currentFont.getStyle() == Font.PLAIN) {
                tfStyle.setText("常规");
            } else if (currentFont.getStyle() == Font.BOLD) {
                tfStyle.setText("粗体");
            } else if (currentFont.getStyle() == Font.ITALIC) {
                tfStyle.setText("斜体");
            } else if (currentFont.getStyle() == (Font.BOLD + Font.ITALIC)) {
                tfStyle.setText("粗斜体");
            }

            tfFont.selectAll();
            tfStyle.setPreferredSize(new Dimension(20, 20));
            tfSize = new JTextField(8);
            tfSize.setText(currentFont.getSize() + "");
            tfSize.selectAll();
            tfSize.setPreferredSize(new Dimension(20, 20));

            final String fontStyle[] = {"常规", "粗体", "斜体", "粗斜体"};
            listStyle = new JList(fontStyle);

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            final String fontName[] = ge.getAvailableFontFamilyNames(); //获取系统的本地字体
            int defaultFontIndex = 0;
            for (int i = 0; i < fontName.length; i++) {
                if (fontName[i].equals(currentFont.getFontName())) {
                    defaultFontIndex = i;
                    break;
                }
            }
            listFont = new JList(fontName);
            listFont.setSelectedIndex(defaultFontIndex);
            listFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); //单选
            listFont.setVisibleRowCount(5);
            listFont.setFixedCellWidth(120);
            listFont.setFixedCellHeight(15);
            listFont.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent event) {
                    tfFont.setText(fontName[listFont.getSelectedIndex()]);
                    tfFont.requestFocus();
                    tfFont.selectAll();
                    updateSample();
                }
            });

            listStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            if (currentFont.getStyle() == Font.PLAIN) {
                listStyle.setSelectedIndex(0);
            } else if (currentFont.getStyle() == Font.BOLD) {
                listStyle.setSelectedIndex(1);
            } else if (currentFont.getStyle() == Font.ITALIC) {
                listStyle.setSelectedIndex(2);
            } else if (currentFont.getStyle() == (Font.BOLD + Font.ITALIC)) {
                listStyle.setSelectedIndex(3);
            }

            listStyle.setVisibleRowCount(5);
            listStyle.setFixedCellWidth(110);
            listStyle.setFixedCellHeight(15);
            listStyle.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent event) {
                    tfStyle.setText(fontStyle[listStyle.getSelectedIndex()]);
                    tfStyle.requestFocus();
                    tfStyle.selectAll();
                    updateSample();
                }
            });

            final String fontSize[] = {"8", "9", "10", "12", "14", "15", "16", "18",
                "20", "21", "22", "24", "26", "28", "30", "36", "48", "54", "72", "89"};
            listSize = new JList(fontSize);
            int defaultFontSizeIndex = 0;
            for (int i = 0; i < fontSize.length; i++) {
                if (fontSize[i].equals(currentFont.getSize() + "")) {
                    defaultFontSizeIndex = i;
                    break;
                }
            }
            listSize.setSelectedIndex(defaultFontSizeIndex);

            listSize.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listSize.setVisibleRowCount(5);
            listSize.setFixedCellWidth(70);
            listSize.setFixedCellHeight(15);
            listSize.addListSelectionListener(new ListSelectionListener() {

                public void valueChanged(ListSelectionEvent event) {
                    tfSize.setText(fontSize[listSize.getSelectedIndex()]);
                    tfSize.requestFocus();
                    tfSize.selectAll();
                    updateSample();
                }
            });
            fontOkButton = new JButton("确定");
            fontOkButton.addActionListener(this);
            JButton cancelButton = new JButton("取消");
            cancelButton.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    fontDialog.dispose();
                }
            });

            sample = new JLabel("Dave记事本");
            sample.setOpaque(true);
            sample.setHorizontalAlignment(SwingConstants.CENTER);
            sample.setBackground(Color.WHITE); //设置背景色
            sample.setForeground(Color.BLACK); //设置前景色，即字的颜色
//   sample.setPreferredSize(new Dimension(130, 100)); //设置文本域的大小
            demoPanel.setBorder(BorderFactory.createTitledBorder("预览")); //这是边框标题
            demoPanel.add(sample, BorderLayout.CENTER);

            fontPanel.add(lblFont);
            fontPanel.add(tfFont);
            fontPanel.add(new JScrollPane(listFont));

            stylePanel.add(lblStyle);
            stylePanel.add(tfStyle);
            stylePanel.add(new JScrollPane(listStyle));

            sizePanel.add(lblSize);
            sizePanel.add(tfSize);
            sizePanel.add(new JScrollPane(listSize));

            fontcolorPanel.setBorder(BorderFactory.createTitledBorder("字体颜色"));
            fontcolorPanel.add(fclabel, BorderLayout.CENTER);
            backgroundcolorPanel.setBorder(BorderFactory.createTitledBorder("背景颜色"));
            backgroundcolorPanel.add(bgclabel, BorderLayout.CENTER);

            basePanel.add(fontOkButton);
            basePanel.add(cancelButton);

            topPanel.add(fontPanel);
            topPanel.add(stylePanel);
            topPanel.add(sizePanel);
            topPanel.add(fontcolorPanel);
            topPanel.add(backgroundcolorPanel);
            topPanel.add(demoPanel);
            con.add(basePanel, BorderLayout.SOUTH);
            con.add(topPanel, BorderLayout.CENTER);
            updateSample();

            fontDialog.setSize(450, 340);
            fontDialog.setLocation(200, 200);
            fontDialog.setResizable(false);
            fontDialog.setVisible(true);
        }//构造函数结束

//设置文本编辑区的字体
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == fontOkButton) {
                newFont = new Font(tfFont.getText(), fontStyleConst[listStyle.getSelectedIndex()], Integer.parseInt(tfSize.getText()));
                fontDialog.dispose();
                editArea.setFont(newFont);
                editArea.setBackground(backgroundColor);
                editArea.setForeground(fontColor);

            }
        }//End method actionPerformed

        public Font getFont() {
            return newFont;
        }

        public Color getbackgroundColor() {
            return backgroundColor;
        }

        public Color getfontColor() {
            return fontColor;
        }

//更新示例显示的字体和风格大小等
        public void updateSample() {
            Font sampleFont = new Font(tfFont.getText(), fontStyleConst[listStyle.getSelectedIndex()], Integer.parseInt(tfSize.getText()));
            sample.setFont(sampleFont);
        }//End method updateSample
    } //##########################################################End of class SetFont######################
}
