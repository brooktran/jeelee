package notepad;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileView;

//**********************************************************************
/**
 * <p>Title: </p>
 * <p>Description: 编辑器主窗口</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//**********************************************************************
public class Notepad_Frame1 extends JFrame
{
  //静态变量

  /** 子窗口Vector */
  static Vector vectorFile = new Vector();
  
  /** 
   * private static final String mac = "com.sun.java.swing.plaf.mac.MacLookAndFeel";
   */
  
  /** JAVA风格 */
  private static final String metal = "javax.swing.plaf.metal.MetalLookAndFeel";
        
  /** motif风格 */
  private static final String motif = "com.sun.java.swing.plaf.motif.MotifLookAndFeel";
        
  /** windows风格 */
  private static final String windows = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";

  /** 定义窗口风格变量*/
  private static String currentLookAndFeel = metal;
  
  //private变量

  /** 主窗口容器 */
  public JDesktopPane desktopPane = null;
  
  /** 子窗口容器 */
  public JInternalFrame internalFrame = null;
  
  /** 子窗口个数同时用于显示于默认子窗口文???? */
  private int count = 1;
  
  /** 子窗口 */
  private newInteface selectFrame = null;

  /** 主容器面板 */
  private JPanel contentPane = new JPanel();
  
  /** 菜单栏 */
  private JMenuBar jMenuBar1 = new JMenuBar();
  
  /** 菜单栏(帮助) */
  private JMenu jMenuHelp = new JMenu();
  
  /** 菜单栏(文件) */
  private JMenu jMenuFile = new JMenu();
  
  /** 菜单栏(编辑) */
  private JMenu jMenuEdit = new JMenu();
  
  /** 菜单栏(视图) */
  private JMenu jMenuView = new JMenu();
  
  /** 菜单栏(外观) */
  private JMenu jMenuWaiGuan = new JMenu();
  
  /** 菜单栏(格式) */
  private JMenu jMenuFormat = new JMenu();

  /** 菜单栏(退出) */
  private JMenuItem jMenuFileExit = new JMenuItem();
  
  /** 菜单栏(关于) */
  private JMenuItem jMenuHelpAbout = new JMenuItem();
  
  /** 工具栏 */
  private JToolBar jToolBar = new JToolBar();
  
  /** 工具栏打开按键 */
  private JButton jButtonOpen = new JButton();
  
  /** 工具栏关闭按键 */
  private JButton jButtonClose = new JButton();  
  
  /** 工具栏保存按键 */
  public JButton jButtonSave = new JButton();
  
  /** 工具栏撤消按键 */
  private JButton jButtonUndo = new JButton();
  
  /** 工具栏重做按键 */
  private JButton jButtonRedo = new JButton();
  
  /** 工具栏拷贝按键 */
  public JButton jButtonCopy = new JButton();
  
  /** 工具栏粘贴按键 */
  public JButton jButtonPaste = new JButton();
  
  /** 工具栏剪切按键 */
  public JButton jButtonCut = new JButton();

  /** 工具栏帮助按键 */
  private JButton jButtonHelp = new JButton();  
  
  /** 工具栏新建按键 */
  private JButton jButtonNew = new JButton();
  
  /** 打开文件图键 */
  private ImageIcon imageOpenFile;        
  
  /** 关闭文件图键 */
  private ImageIcon imageCloseFile;        
  
  /** 帮助图标 */
  private ImageIcon imageHelp;             
  
  /** 保存文件图标 */
  private ImageIcon imageSave;             
  
  /** 撤消图标 */
  private ImageIcon imageUndo;             
  
  /** 重做图标 */
  private ImageIcon imageRedo;             
  
  /** 拷贝图标 */
  private ImageIcon imageCopy;             
  
  /** 粘贴图标 */
  private ImageIcon imagePaste;            
  
  /** 剪切图标 */
  private ImageIcon imageCut;              
  
  /** 新建图标 */
  private ImageIcon imageNew;              
  
  /** 标题图标 */
  private Image imageTitle;                
  
  /** 窗口布局 */
  private BorderLayout borderLayout1 = new BorderLayout();

  /** 菜单项(打开) */
  private JMenuItem jMenuFileOpen = new JMenuItem();
  
  /** 菜单项(新建) */
  private JMenuItem jMenuFileNew = new JMenuItem();
  
  /** 菜单项(保存) */
  public JMenuItem jMenuFileSave = new JMenuItem();
  
  /** 菜单项(另存为) */
  public JMenuItem jMenuFileSaveAs = new JMenuItem();
  
  /** 菜单项(页面设置) */
  private JMenuItem jMenuPageSetup = new JMenuItem();
  
  /** 菜单项(打印) */
  private JMenuItem jMenuFilePrint = new JMenuItem();

  /** 菜单项(重做) */
  private JMenuItem jMenuEditUndo = new JMenuItem();
  
  /** 菜单项(剪切) */
  public JMenuItem jMenuEditCut = new JMenuItem();
  
  /** 菜单项(拷贝) */
  public JMenuItem jMenuEditCopy = new JMenuItem();
  
  /** 菜单项(粘贴) */
  public JMenuItem jMenuEditPaste = new JMenuItem();
  
  /** 菜单项(删除) */
  private JMenuItem jMenuEditDelete = new JMenuItem();
  
  /** 菜单项(字休) */
  private JMenuItem jMenuFormatFont = new JMenuItem();
  
  /** 菜单项(查找) */
  public JMenuItem jMenuFind = new JMenuItem();
  
  /** 菜单项(查找并替换) */
  public JMenuItem jMenuFindReplace = new JMenuItem();
  
  /** 菜单项(状态栏) */
  private JCheckBoxMenuItem jCheckBoxMenuViewStatusBar = new JCheckBoxMenuItem();
  
  /** 菜单项(工具栏) */
  private JCheckBoxMenuItem jCheckBoxMenuViewToolBar = new JCheckBoxMenuItem();
  
  /** 菜单项(全选) */
  public JMenuItem jMenuEditSelectAll = new JMenuItem();
  
  /** 菜单项(java风格) */
  private JRadioButtonMenuItem jRadioButtonMenuJava = new JRadioButtonMenuItem();
  
  /** 菜单项(motif风格) */
  private JRadioButtonMenuItem jRadioButtonMenuMotif = new JRadioButtonMenuItem();
  
  /** 菜单项(windows风格) */
  private JRadioButtonMenuItem jRadioButtonMenuWindows = new JRadioButtonMenuItem();
  
  /** 状态栏面板 */
  private JPanel jPanelstatus = new JPanel();
  
  /** 网格布局 */
  private GridLayout gridLayout1 = new GridLayout();
  
  /** 状态栏标签 */
  private JLabel statusBar = new JLabel();
  
  /** 状态栏标签(显示时间) */
  private JLabel statusBarTime = new JLabel();
  
  /** 状态栏标签(显示坐标) */
  public JLabel statusBarXY = new JLabel();
  
  /** 当前日期及时间 */
  private Date time = new Date();
  
  /** JFileChooser filechooser = new JFileChooser(); */
 
  /** 文件 */
  private File file = null;
  
  /** changeTextArea等于true表示文本内容改变，关闭时需提示保存，fals则没有改变 */
  public boolean chanageTextArea = false;
  

  //方法定义

  /**构造方法   */

  public Notepad_Frame1()
  {
    enableEvents(AWTEvent.WINDOW_EVENT_MASK);
    
    try {
      
      jbInit();
    } catch (Exception e) {
      
      e.printStackTrace();
    }
  }

  //public方法

  /**
   * 初始化变量
   *
   * @exception Exception  
   *
   */

  private void jbInit() throws Exception
  {
    desktopPane = new JDesktopPane();
    internalFrame = new JInternalFrame();
    Toolkit kt = Toolkit.getDefaultToolkit();
    
    /*//窗口左上角图标加载 
    imageTitle = kt.getImage(notepad.Notepad_Frame1.class.getResource("ImageTitle1.gif"));
System.out.println(kt.getScreenSize());
    //窗口左上角图标设置 
    this.setIconImage(imageTitle);
    
    //图标装载 
    imageOpenFile = new ImageIcon(notepad.Notepad_Frame1.class.getResource("openFile.png"));
    imageCloseFile = new ImageIcon(notepad.Notepad_Frame1.class.getResource("closeFile.png"));
    imageHelp = new ImageIcon(notepad.Notepad_Frame1.class.getResource("help.png"));
    imageSave = new ImageIcon(notepad.Notepad_Frame1.class.getResource("saveFile.gif"));
    imageUndo = new ImageIcon(notepad.Notepad_Frame1.class.getResource("undo.gif"));
    imageRedo = new ImageIcon(notepad.Notepad_Frame1.class.getResource("redo.gif"));
    imageCopy = new ImageIcon(notepad.Notepad_Frame1.class.getResource("copy.gif"));
    imagePaste = new ImageIcon(notepad.Notepad_Frame1.class.getResource("paste.gif"));
    imageCut = new ImageIcon(notepad.Notepad_Frame1.class.getResource("cut.gif"));
    imageNew = new ImageIcon(notepad.Notepad_Frame1.class.getResource("newFile.gif"));*/
    
    contentPane = (JPanel)this.getContentPane();
    
    //主容器布局设置 
    contentPane.setLayout(borderLayout1);
    
    //屏幕的窥胰取得 
    int notepad_Frame1Width = Toolkit.getDefaultToolkit().getScreenSize().width;
  
    //屏幕的长度取得 
    int notepad_Frame1Height = Toolkit.getDefaultToolkit().getScreenSize().height-20;
    
    //设置主容器大小为屏幕的大小 
    this.setSize(new Dimension(notepad_Frame1Width, notepad_Frame1Height));
    
    //设置程序标题栏 
    this.setTitle("JHelper");
    
    jMenuFile.setToolTipText("");
    jMenuFile.setText("文件");
    jMenuFileExit.setText("Exit");
    
    //退出系统快捷设置(Ctrl+W) 
    jMenuFileExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke('W',KeyEvent.CTRL_MASK, false));
    
    jMenuFileExit.addActionListener(new Notepad_Frame1_jMenuFileExit_ActionAdapter(this));
    jMenuHelp.setToolTipText("");
    jMenuHelp.setText("帮助");
    jMenuHelpAbout.setToolTipText("");
    jMenuHelpAbout.setText("关于...");
    
    //帮助快捷设置(Ctrl+H) 
    jMenuHelpAbout.setAccelerator(javax.swing.KeyStroke.getKeyStroke('H',KeyEvent.CTRL_MASK, false));
    jMenuHelpAbout.addActionListener(new Notepad_Frame1_jMenuHelpAbout_ActionAdapter(this));
    jButtonOpen.setIcon(imageOpenFile);
    jButtonOpen.addActionListener(new Notepad_Frame1_jButtonOpen_actionAdapter(this));
    jButtonOpen.setToolTipText("打开(Ctrl+O)");
    jButtonClose.setIcon(imageCloseFile);
    jButtonClose.addActionListener(new Notepad_Frame1_jButtonClose_actionAdapter(this));
    jButtonClose.setToolTipText("关闭(Ctrl+W)");
    jButtonHelp.setIcon(imageHelp);
    jButtonHelp.addActionListener(new Notepad_Frame1_jButtonHelp_actionAdapter(this));
    
    //工具栏帮助快捷键大小设置 
    jButtonHelp.setMaximumSize(new Dimension(28, 28));
    jButtonHelp.setMinimumSize(new Dimension(28, 28));
    jButtonHelp.setToolTipText("帮助");
    jButtonSave.setIcon(imageSave);
    jButtonSave.setEnabled(false);
    jMenuFileOpen.setToolTipText("");
    jMenuFileOpen.setText("打开...");
    
    //打开快捷键设置(Ctrl+O)
    jMenuFileOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke('O',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileOpen.addActionListener(new Notepad_Frame1_jMenuFileOpen_actionAdapter(this));
    jMenuFileNew.setToolTipText("");
    jMenuFileNew.setText("新建");
    
    //新建快捷键设置(Ctrl+N) 
    jMenuFileNew.setAccelerator(javax.swing.KeyStroke.getKeyStroke('N',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileNew.addActionListener(new Notepad_Frame1_jMenuFileNew_actionAdapter(this));
    jMenuFileSave.setToolTipText("");
    jMenuFileSave.setText("保存");
    jMenuFileSave.setEnabled(false);
    
    //保存快捷键设置(Ctrl+S) 
    jMenuFileSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke('S',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuFileSave.addActionListener(new Notepad_Frame1_jMenuFileSave_actionAdapter(this));
    jMenuFileSaveAs.setToolTipText("");
    jMenuFileSaveAs.setText("另存为...");
    jMenuFileSaveAs.setEnabled(false);
    jMenuFileSaveAs.addActionListener(new Notepad_Frame1_jMenuFileSaveAs_actionAdapter(this));
    jMenuPageSetup.setToolTipText("");
    jMenuPageSetup.setText("页面设置...");
    jMenuFilePrint.setToolTipText("");
    jMenuFilePrint.setText("打印...");
    
    //打印快捷键设置(Ctrl+P) 
    jMenuFilePrint.setAccelerator(javax.swing.KeyStroke.getKeyStroke('P',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEdit.setToolTipText("");
    jMenuEdit.setText("编辑");
    jMenuFormat.setToolTipText("");
    jMenuFormat.setText("格式");
    jMenuView.setToolTipText("");
    jMenuView.setText("查看");
    jMenuEditUndo.setToolTipText("");
    jMenuEditUndo.setText("撤消");
    
    //撤消快捷键设置(Ctrl+Z)
    jMenuEditUndo.setAccelerator(javax.swing.KeyStroke.getKeyStroke('Z',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditCut.setToolTipText("");
    jMenuEditCut.setText("剪切");
    jMenuEditCut.setEnabled(false);
    jMenuEditCut.setAccelerator(javax.swing.KeyStroke.getKeyStroke('X',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditCut.addActionListener(new Notepad_Frame1_jMenuEditCut_actionAdapter(this));
    jMenuEditCopy.setToolTipText("");
    jMenuEditCopy.setText("复制");
    jMenuEditCopy.setEnabled(false);
    
    //拷贝快捷键设置(Ctrl+C) 
    jMenuEditCopy.setAccelerator(javax.swing.KeyStroke.getKeyStroke('C',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditCopy.addActionListener(new Notepad_Frame1_jMenuEditCopy_actionAdapter(this));
    jMenuEditPaste.setToolTipText("");
    jMenuEditPaste.setText("粘贴");
    jMenuEditPaste.setEnabled(false);
    
    //粘贴快捷键设置(Ctrl+V)
    jMenuEditPaste.setAccelerator(javax.swing.KeyStroke.getKeyStroke('V',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditPaste.addActionListener(new Notepad_Frame1_jMenuEditPaste_actionAdapter(this));
    jMenuEditDelete.setText("删除");
    jMenuEditDelete.setEnabled(false);
    jMenuFormatFont.setText("字体...");
    jCheckBoxMenuViewStatusBar.setSelected(true);
    jCheckBoxMenuViewStatusBar.setText("状态栏");
    jCheckBoxMenuViewStatusBar.addActionListener(new Notepad_Frame1_jCheckBoxMenuViewStatusBar_actionAdapter(this));
    jCheckBoxMenuViewToolBar.setSelected(true);
    jCheckBoxMenuViewToolBar.setText("工具栏");
    jCheckBoxMenuViewToolBar.addActionListener(new Notepad_Frame1_jCheckBoxMenuViewToolBar_actionAdapter(this));
    jPanelstatus.setLayout(gridLayout1);
    statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
    statusBarTime.setBorder(BorderFactory.createLoweredBevelBorder());
    statusBarXY.setBorder(BorderFactory.createLoweredBevelBorder());
    statusBarTime.setText("时间:" + time.toLocaleString());
    gridLayout1.setColumns(2);
    jButtonSave.setMaximumSize(new Dimension(28, 28));
    jButtonSave.setMinimumSize(new Dimension(28, 28));
    jButtonSave.setPreferredSize(new Dimension(28, 28));
    jButtonSave.setToolTipText("保存文件(Ctrl+S)");
    jButtonSave.setText("");
    jButtonSave.addActionListener(new Notepad_Frame1_jButtonSave_actionAdapter(this));
    jMenuEditSelectAll.setText("全选");
    jMenuEditSelectAll.setEnabled(false);
    
    //全选??捷??设置(Ctrl+A) 
    jMenuEditSelectAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke('A',java.awt.event.KeyEvent.CTRL_MASK, false));
    jMenuEditSelectAll.addActionListener(new Notepad_Frame1_jMenuEditSelectAll_actionAdapter(this));
    jButtonUndo.setMaximumSize(new Dimension(28, 28));
    jButtonUndo.setMinimumSize(new Dimension(28, 28));
    jButtonUndo.setPreferredSize(new Dimension(28, 28));
    jButtonUndo.setToolTipText("撤消(Ctrl+Z)");
    jButtonUndo.setIcon(imageUndo);
    jButtonRedo.setMaximumSize(new Dimension(28, 28));
    jButtonRedo.setMinimumSize(new Dimension(28, 28));
    jButtonRedo.setPreferredSize(new Dimension(28, 28));
    jButtonRedo.setToolTipText("重做(Ctrl+Y)");
    jButtonRedo.setIcon(imageRedo);
    jButtonCopy.setMaximumSize(new Dimension(28, 28));
    jButtonCopy.setMinimumSize(new Dimension(28, 28));
    jButtonCopy.setPreferredSize(new Dimension(28, 28));
    jButtonCopy.setToolTipText("复制(Ctrl+C)");
    jButtonCopy.setEnabled(false);
    jButtonCopy.setIcon(imageCopy);
    jButtonCopy.addActionListener(new Notepad_Frame1_jButtonCopy_actionAdapter(this));
    jButtonPaste.setMaximumSize(new Dimension(28, 28));
    jButtonPaste.setMinimumSize(new Dimension(28, 28));
    jButtonPaste.setPreferredSize(new Dimension(28, 28));
    jButtonPaste.setToolTipText("粘贴(Ctrl+V)");
    jButtonPaste.setEnabled(false);
    jButtonPaste.setIcon(imagePaste);
    jButtonPaste.addActionListener(new Notepad_Frame1_jButtonPaste_actionAdapter(this));
    jButtonCut.setMaximumSize(new Dimension(28, 28));
    jButtonCut.setMinimumSize(new Dimension(28, 28));
    jButtonCut.setPreferredSize(new Dimension(28, 28));
    jButtonCut.setToolTipText("剪切(Ctrl+X)");
    jButtonCut.setEnabled(false);
    jButtonCut.setIcon(imageCut);
    jButtonCut.addActionListener(new Notepad_Frame1_jButtonCut_actionAdapter(this));
    jMenuWaiGuan.setText("外观样式");
    jRadioButtonMenuJava.setText("JAVA风格");
    jRadioButtonMenuJava.setSelected(true);
    jRadioButtonMenuJava.addActionListener(new Notepad_Frame1_jRadioButtonMenuJava_actionAdapter(this));
    jRadioButtonMenuMotif.setText("Motif风格");
    jRadioButtonMenuMotif.addActionListener(new Notepad_Frame1_jRadioButtonMenuMotif_actionAdapter(this));
    jRadioButtonMenuWindows.setText("Windows风格");
    jRadioButtonMenuWindows.addActionListener(new Notepad_Frame1_jRadioButtonMenuWindows_actionAdapter(this));
    jButtonNew.setMaximumSize(new Dimension(28, 28));
    jButtonNew.setMinimumSize(new Dimension(28, 28));
    jButtonNew.setPreferredSize(new Dimension(28, 28));
    jButtonNew.setIcon(imageNew);
    jButtonNew.setText("");
    jButtonNew.addActionListener(new Notepad_Frame1_jButtonNew_actionAdapter(this));
    jButtonNew.setToolTipText("新建(Ctrl+N)");
    jMenuFind.setText("查找...");
    jMenuFind.setEnabled(false);
    jMenuFind.addActionListener(new Notepad_Frame1_jMenuFind_actionAdapter(this));
    jMenuFindReplace.setText("查找替换...");
    jMenuFindReplace.setEnabled(false);
    jMenuFindReplace.addActionListener(new Notepad_Frame1_jMenuFindReplace_actionAdapter(this));
    jToolBar.add(jButtonNew, null);
    jToolBar.add(jButtonOpen);
    jToolBar.add(jButtonSave);
    jToolBar.add(jButtonClose);
    jToolBar.addSeparator();
    jToolBar.add(jButtonUndo, null);
    jToolBar.add(jButtonRedo, null);
    jToolBar.add(jButtonCopy, null);
    jToolBar.add(jButtonPaste, null);
    jToolBar.add(jButtonCut, null);
    jToolBar.addSeparator();
    jToolBar.add(jButtonHelp);
    jMenuFile.add(jMenuFileNew);
    jMenuFile.add(jMenuFileOpen);
    jMenuFile.add(jMenuFileSave);
    jMenuFile.add(jMenuFileSaveAs);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuPageSetup);
    jMenuFile.add(jMenuFilePrint);
    jMenuFile.addSeparator();
    jMenuFile.add(jMenuFileExit);
    jMenuHelp.add(jMenuHelpAbout);
    jMenuBar1.add(jMenuFile);
    jMenuBar1.add(jMenuEdit);
    jMenuBar1.add(jMenuFormat);
    jMenuBar1.add(jMenuView);
    jMenuBar1.add(jMenuHelp);
    this.setJMenuBar(jMenuBar1);
    contentPane.add(jToolBar, BorderLayout.NORTH);
    contentPane.add(desktopPane,BorderLayout.CENTER);
    contentPane.add(jPanelstatus, BorderLayout.SOUTH);
    jPanelstatus.add(statusBar, null);
    jPanelstatus.add(statusBarTime, null);
    jPanelstatus.add(statusBarXY, null);
    jMenuEdit.add(jMenuEditUndo);
    jMenuEdit.addSeparator();
    jMenuEdit.add(jMenuEditCut);
    jMenuEdit.add(jMenuEditCopy);
    jMenuEdit.add(jMenuEditPaste);
    jMenuEdit.add(jMenuEditDelete);
    jMenuEdit.addSeparator();
    jMenuEdit.add(jMenuEditSelectAll);
    jMenuFormat.add(jMenuFormatFont);
    jMenuView.add(jMenuWaiGuan);
    jMenuView.add(jMenuFind);
    jMenuView.add(jMenuFindReplace);
    jMenuView.add(jCheckBoxMenuViewStatusBar);
    jMenuView.add(jCheckBoxMenuViewToolBar);
    jMenuWaiGuan.add(jRadioButtonMenuJava);
    jMenuWaiGuan.add(jRadioButtonMenuMotif);
    jMenuWaiGuan.add(jRadioButtonMenuWindows);
  }

  /**File | Exit action performed*/
  public void jMenuFileExit_actionPerformed(ActionEvent e) {
    
    if(vectorFile.isEmpty()) {
      
      // 退出系统 
      System.exit(0);
      
    } else {
      
      int dialogType = 
             JOptionPane.showConfirmDialog(this, "文件是否要保存", "关闭",JOptionPane.YES_NO_OPTION);
      if (dialogType == JOptionPane.YES_OPTION) {
        
        return;
        
      } else {
        
        // 退出系统
        System.exit(0);
      }
    }
  }

  /**Help | About action performed*/
  public void jMenuHelpAbout_actionPerformed(ActionEvent e)
  {
    Notepad_Frame1_AboutBox dlg = new Notepad_Frame1_AboutBox(this);
    Dimension dlgSize = dlg.getPreferredSize();
    Dimension frmSize = getSize();
    Point loc = getLocation();
    dlg.setLocation( (frmSize.width - dlgSize.width) / 2 + loc.x,
                    (frmSize.height - dlgSize.height) / 2 + loc.y);
    dlg.setModal(true);
    dlg.pack();
    dlg.show();
  }

  //Overridden so we can exit when window is closed
  protected void processWindowEvent(WindowEvent e) {
    
    super.processWindowEvent(e);
    if (e.getID() == WindowEvent.WINDOW_CLOSING) {
      jMenuFileExit_actionPerformed(null);
    }
  }
  
  protected void jButtonOpen_actionPerformed(ActionEvent e) {
    jMenuFileOpen_actionPerformed(e);
  }

  protected void jButtonHelp_actionPerformed(ActionEvent e) {
    jMenuHelpAbout_actionPerformed(e);
  }

  protected void jButtonClose_actionPerformed(ActionEvent e) {
    jMenuFileExit_actionPerformed(e);
  }

  protected void jMenuFileOpen_actionPerformed(ActionEvent e) {
    
    try {
      
      UIManager.setLookAndFeel(currentLookAndFeel);
      
    } catch (Exception e1) {
      
      e1.printStackTrace();
    }
    
    JFileChooser filechooser = new JFileChooser();
    filechooser.setSelectedFile(file);
    filechooser.addChoosableFileFilter(new JavaFileFilter("jsp"));
    filechooser.addChoosableFileFilter(new JavaFileFilter("html"));
    filechooser.addChoosableFileFilter(new JavaFileFilter("txt"));
    filechooser.addChoosableFileFilter(new JavaFileFilter("java"));
    filechooser.setFileView(new FileIcon());
    int returnVal = filechooser.showOpenDialog(Notepad_Frame1.this);
    
    //如果在弹出的对话框中选择yes或ok的场合
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      file = filechooser.getSelectedFile();
      if(file != null) {
        newInteface temp = null;
        Enumeration en = vectorFile.elements();
        
        while (en.hasMoreElements()) {
          temp = (newInteface) en.nextElement();
          if (getFileName(temp.getNewIntefaceTitle()).equals(getFileName(file.toString()))) {
            temp.setJInternalFrameEnabled(file.toString());
            break;
          } else {
            temp = null;
          }
        }
        if (file != null && temp == null) {
          openFile();
        }
      }
    }
  }

  //********************************************************************
  /**
   * 打开文件
   * 
   */
  //********************************************************************
  protected void openFile() {
    
    newInteface temp = new newInteface(this,file.toString(),false);
    
    // 设置打开的文件标记为假 
    temp.setSaveFlag(false);
    
    // 设置子窗口标题栏 
    temp.setNewIntefaceTitle(file.toString());
    
    // 把打开的子窗口加到Vector中 
    vectorFile.addElement(temp);
    
    FileReader in = null;
    try {
      
      in = new FileReader(file);
      int n = 0;
      while ( (n = in.read()) != -1) {
        
        String str = String.valueOf( (char) n);
        temp.insertString(str);
      }
      
    } catch (Exception e) {
      
      e.printStackTrace();
      
    } finally {
      
      if(in != null) {
        try {
          in.close();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    
    //将编辑栏下查找菜单消彖为可用 
    jMenuFind.setEnabled(true);
    
    //将编辑栏下查找并替换菜单消彖为可用 
    jMenuFindReplace.setEnabled(true);
    
    //将编辑栏下剪切菜单消彖为可用 
    jMenuEditCut.setEnabled(true);
    
    //将编辑栏下拷贝菜单消彖为可用 
    jMenuEditCopy.setEnabled(true);
    
    //将编辑栏下粘贴菜单消彖为可用 
    jMenuEditPaste.setEnabled(true);
    
    //将编辑栏下全选菜单消彖为可用 
    jMenuEditSelectAll.setEnabled(true);
    
    //将工具栏拷贝按键设为可用 
    jButtonCopy.setEnabled(true);
    
    //将工具栏粘贴按键设为可用
    jButtonPaste.setEnabled(true);
    
    //将工具栏剪切按键设为可用 
    jButtonCut.setEnabled(true);
  }

  //********************************************************************
  /**
   * 当选择保存时
   * 
   */
  //********************************************************************
  protected void saveOption() {
    
    //设置当前可编辑窗口
    setSelectFrame();
    
    if (selectFrame != null) {
      
      File file = new File(selectFrame.getNewIntefaceTitle());
      
      if (selectFrame.saveFlag == true) {
      
        //第一次保存的场合 
        try {
          
          UIManager.setLookAndFeel(currentLookAndFeel);
          
        } catch (Exception e1) {
          
          e1.printStackTrace();
        }
        
        JFileChooser filechooser = new JFileChooser();
        filechooser.setSelectedFile(file);
        filechooser.addChoosableFileFilter(new JavaFileFilter("jsp"));
        filechooser.addChoosableFileFilter(new JavaFileFilter("html"));
        filechooser.addChoosableFileFilter(new JavaFileFilter("txt"));
        filechooser.addChoosableFileFilter(new JavaFileFilter("java"));
        filechooser.setFileView(new FileIcon());
        int returnVal = filechooser.showSaveDialog(Notepad_Frame1.this);
        
        if (returnVal == JFileChooser.APPROVE_OPTION) {
          
          //如果在弹出的对话框中选择yes或ok的场合
          file = filechooser.getSelectedFile();
          saveFile(file);
          selectFrame.setTitle(getFileName(file.toString()));/////////
          jMenuFileSave.setEnabled(false);
          jMenuFileSaveAs.setEnabled(false);
          jButtonSave.setEnabled(false);
        }
      } else {
        
        //不是第一次保存的场合 
        saveFile(file);
        jMenuFileSave.setEnabled(false);
        jMenuFileSaveAs.setEnabled(false);
        jButtonSave.setEnabled(false);
      }
    }
  }

  protected void jMenuFileSave_actionPerformed(ActionEvent e) {
    saveOption();
  }

  protected void jMenuFileSaveAs_actionPerformed(ActionEvent e) {
    
    File file = null;
    try {
      
      UIManager.setLookAndFeel(currentLookAndFeel);
      
    } catch (Exception e1) {
      
      e1.printStackTrace();
    }
    
    JFileChooser filechooser = new JFileChooser();
    filechooser.setSelectedFile(file);
    filechooser.addChoosableFileFilter(new JavaFileFilter("jsp"));
    filechooser.addChoosableFileFilter(new JavaFileFilter("html"));
    filechooser.addChoosableFileFilter(new JavaFileFilter("txt"));
    filechooser.addChoosableFileFilter(new JavaFileFilter("java"));
    filechooser.setFileView(new FileIcon());
    int returnVal = filechooser.showSaveDialog(Notepad_Frame1.this);
    
    //如果在弹出的对话框中选择yes或ok的场合
    if (returnVal == JFileChooser.APPROVE_OPTION) {
      file = filechooser.getSelectedFile();
      saveFile(file);
    }
  }

  //********************************************************************
  /**
   * 当选择保存时
   * 
   * @param file 要保存的文件
   */
  //********************************************************************
  protected void saveFile(File file) {
    
    FileWriter out = null;
    BufferedWriter bufOut = null;
    
    try {
      
      out = new FileWriter(file);
      bufOut = new BufferedWriter(out);
      
      //设置当前可编辑窗口
      setSelectFrame();
      
System.out.println(selectFrame.getNewIntefaceTitle());
      
      if(selectFrame != null) {
        bufOut.write(selectFrame.jTextPane.getText());
        bufOut.flush();
      }
      
    } catch (Exception e) {
      
      e.printStackTrace();
      
    } finally {
      
      if(bufOut != null) {
        try {
          
          bufOut.close();
          
        } catch(Exception e) {
          
          e.printStackTrace();
        }
      }
      
      if(out != null) {
        try {
          
          out.close();
          
        } catch(Exception e) {
          
          e.printStackTrace();
          
        }
      }
    }
    
    //把此子窗口设置为不是第一次保存
    selectFrame.saveFlag = false;
    
    //把此子窗口设置为没有改变
    selectFrame.setChanageTextPane(false);
  }
  
  //********************************************************************
  /**
   * 获取文件名
   * 
   * @param str 带路径的文件名
   * @return  去掉路径后的文件名
   */
  //********************************************************************
  private String getFileName(String str) {
    int post;
    post = str.lastIndexOf("\\",str.length());
    return str.substring(post+1);
  }

  protected void jMenuEditSelectAll_actionPerformed(ActionEvent e) {
    
    //设置当前可编辑窗口
    setSelectFrame();
    
    if(selectFrame != null) {
      selectFrame.jTextPane.selectAll();
    }
  }

  protected void jCheckBoxMenuViewStatusBar_actionPerformed(ActionEvent e) {
    
    if (jCheckBoxMenuViewStatusBar.isSelected()) {
      
      jPanelstatus.setVisible(true);
      
    } else {
      
      jPanelstatus.setVisible(false);
    }
  }

  protected void jCheckBoxMenuViewToolBar_actionPerformed(ActionEvent e) {
    
    if (jCheckBoxMenuViewToolBar.isSelected()) {
      jToolBar.setVisible(true);
    } else {
      jToolBar.setVisible(false);
    }
  }

  protected void jButtonSave_actionPerformed(ActionEvent e) {
    
    jMenuFileSave_actionPerformed(e);
  }

  //********************************************************************
  /**
   * 新建文件事件
   * 
   * @param argEvent ActionEvent
   * 
   */
  //********************************************************************
  protected void jMenuFileNew_actionPerformed(ActionEvent argEvent) {
    
    file = null;
    String title = "未标题"+count++;
    newInteface temp = new newInteface(this,title,true);
    temp.setNewIntefaceTitle(title);
    temp.setChanageTextPane(false);
    vectorFile.addElement(temp);
    jMenuFileSave.setEnabled(true);
    jMenuFileSaveAs.setEnabled(true);
    jButtonSave.setEnabled(true);
    jMenuFind.setEnabled(true);
    jMenuFindReplace.setEnabled(true);
    jButtonCopy.setEnabled(true);
    jButtonPaste.setEnabled(true);
    jButtonCut.setEnabled(true);
    jMenuEditCut.setEnabled(true);
    jMenuEditCopy.setEnabled(true);
    jMenuEditPaste.setEnabled(true);
    jMenuEditSelectAll.setEnabled(true);
  }

  //********************************************************************
  /**
   * 把当前可编辑窗口赋给select
   * 
   */
  //********************************************************************
  private void setSelectFrame() {
    
    newInteface temp = null;
    Enumeration en = vectorFile.elements();
    while(en.hasMoreElements()) {
      temp = (newInteface) en.nextElement();
      temp.setSelectTitle();
      if(temp.getNewIntefaceTitle().equals(newInteface.TITLE)) {
        selectFrame = temp;
      }
    }
  }

  void removeClose(String title) {
    newInteface temp = null;
    Enumeration en = vectorFile.elements();
    while(en.hasMoreElements()) {
      temp = (newInteface) en.nextElement();
      if(temp.getNewIntefaceTitle().equals(title)) {
        selectFrame = temp;
      }
    }
    temp.removeClose(title);
    vectorFile.removeElement(selectFrame);
  }

  void jMenuEditCut_actionPerformed(ActionEvent e) {
    setSelectFrame();
    if(selectFrame != null) {
      selectFrame.jMenuPopCut_actionPerformed(e);
    }
  }

  //返回当前可编辑窗口
  newInteface getSelectFrame() {
    setSelectFrame();
    return selectFrame;
  }

  void jMenuEditCopy_actionPerformed(ActionEvent e) {
    setSelectFrame();
    if(selectFrame != null) {
      selectFrame.jMenuPopCopy_actionPerformed(e);
    }
  }

  void jMenuEditPaste_actionPerformed(ActionEvent e) {
    setSelectFrame();
    if(selectFrame != null) {
      selectFrame.jMenuPopPaste_actionPerformed(e);
    }
  }

  void jButtonCopy_actionPerformed(ActionEvent e) {
    jMenuEditCopy_actionPerformed(e);
  }

  void jButtonPaste_actionPerformed(ActionEvent e) {
    jMenuEditPaste_actionPerformed(e);
  }

  void jButtonCut_actionPerformed(ActionEvent e) {
    jMenuEditCut_actionPerformed(e);
  }

  void jRadioButtonMenuJava_actionPerformed(ActionEvent e) {
     if (jRadioButtonMenuJava.isSelected() == true) {
       currentLookAndFeel = metal;
       jRadioButtonMenuMotif.setSelected(false);
       jRadioButtonMenuWindows.setSelected(false);
       setLook(currentLookAndFeel);
     }
  }

  void jRadioButtonMenuMotif_actionPerformed(ActionEvent e) {
    if(jRadioButtonMenuMotif.isSelected() == true) {
      currentLookAndFeel = motif;
      jRadioButtonMenuJava.setSelected(false);
      jRadioButtonMenuWindows.setSelected(false);
      setLook(currentLookAndFeel);
    }
  }

  void jRadioButtonMenuWindows_actionPerformed(ActionEvent e) {
    if(jRadioButtonMenuWindows.isSelected() == true) {
      currentLookAndFeel = windows;
      jRadioButtonMenuJava.setSelected(false);
      jRadioButtonMenuMotif.setSelected(false);
      setLook(currentLookAndFeel);
    }
  }

  void setLook(String temp) {
    
    try {
      
      UIManager.setLookAndFeel(temp);
      SwingUtilities.updateComponentTreeUI(this);
      
    } catch(Exception e) {
      
      e.printStackTrace();
    }
  }

  void jButtonNew_actionPerformed(ActionEvent e) {
    
    jMenuFileNew_actionPerformed(e);
  }

  void jMenuFind_actionPerformed(ActionEvent e) {
    
    DialogFind find = new DialogFind(this,"查找",true);
    Dimension frmSize = getSize();
    Point loc = getLocation();
    find.setLocation( (frmSize.width - 400) / 2 + loc.x,
                    (frmSize.height - 160) / 2 + loc.y);
    find.pack();
    find.setSize(400,160);
    find.show();
  }

  void jMenuFindReplace_actionPerformed(ActionEvent e) {
    
    DialogFindAndReplace findReplace = new DialogFindAndReplace(this,"查找替换",true);
    Dimension  frmSize = getSize();
    Point loc = getLocation();
    findReplace.setLocation((frmSize.width-400) / 2 + loc.x,
                            (frmSize.height - 230) / 2 + loc.y);
    findReplace.pack();
    findReplace.setSize(400,230);
    findReplace.show();
  }
}

//**********************************************************************
/**
 * <p>Title: </p>
 * <p>Description: 编辑器主窗口</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */
//**********************************************************************
class Notepad_Frame1_jMenuFileExit_ActionAdapter implements ActionListener {
  
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFileExit_ActionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileExit_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuHelpAbout_ActionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuHelpAbout_ActionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuHelpAbout_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonOpen_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonOpen_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonOpen_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonHelp_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonHelp_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonHelp_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonClose_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonClose_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonClose_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuFileOpen_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFileOpen_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileOpen_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuFileSave_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFileSave_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileSave_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuFileSaveAs_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFileSaveAs_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileSaveAs_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuEditSelectAll_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;
  Notepad_Frame1_jMenuEditSelectAll_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuEditSelectAll_actionPerformed(e);
  }
}

class Notepad_Frame1_jCheckBoxMenuViewStatusBar_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jCheckBoxMenuViewStatusBar_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuViewStatusBar_actionPerformed(e);
  }
}

class Notepad_Frame1_jCheckBoxMenuViewToolBar_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jCheckBoxMenuViewToolBar_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jCheckBoxMenuViewToolBar_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonSave_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonSave_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }

  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonSave_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuFileNew_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFileNew_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFileNew_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuEditCut_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuEditCut_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuEditCut_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuEditCopy_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuEditCopy_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuEditCopy_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuEditPaste_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuEditPaste_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuEditPaste_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonCopy_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonCopy_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonCopy_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonPaste_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonPaste_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonPaste_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonCut_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonCut_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonCut_actionPerformed(e);
  }
}
/*
class Notepad_Frame1_jTextArea1_keyAdapter extends java.awt.event.KeyAdapter
{
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jTextArea1_keyAdapter(Notepad_Frame1 adaptee)
  {
    this.adaptee = adaptee;
  }
  public void keyTyped(KeyEvent e)
  {
    adaptee.jTextArea1_keyTyped(e);
  }
}*/

class Notepad_Frame1_jRadioButtonMenuJava_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jRadioButtonMenuJava_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButtonMenuJava_actionPerformed(e);
  }
}

class Notepad_Frame1_jRadioButtonMenuMotif_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jRadioButtonMenuMotif_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButtonMenuMotif_actionPerformed(e);
  }
}

class Notepad_Frame1_jRadioButtonMenuWindows_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jRadioButtonMenuWindows_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  
  public void actionPerformed(ActionEvent e) {
    adaptee.jRadioButtonMenuWindows_actionPerformed(e);
  }
}

class Notepad_Frame1_jButtonNew_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jButtonNew_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonNew_actionPerformed(e);
  }
}
/*
class JAVAFileFilter extends FileFilter {
  
  private String ext;
  
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
}*/

//class FileIcon extends FileView {
  
  //********************************************************************
  /**
   * 返回值为null的话,java look and feel功能会处历阳这个消嗫,<BR>
   * 并取得相关值来加以设置.一般而言可以使用f.getName()当返回值.
   * 
   * @param f 僔僗僥儉僐儞僥僉僗僩
   */
  //********************************************************************
  
//  public String getName(File f) {
//    
//	  return null; 
//                    
//  }

  //********************************************************************
  /**
   * 返回值为null的话,java look and feel功能会处历阳这个消嗫,并取得相关<BR>
   * 值来加以设置.你也可以自己设置对此图片的描素,如这是一张风景图片等等.
   * 
   * @param f 
   */
  //********************************************************************
//  public String getDescription(File f) {
//    
//	  return null; 
//	                 
//  }
//
//  public String getTypeDescription(File f) {
//    
//    String extension = getExtensionName(f);
//      
//    if(extension.equals("java")) {
//      return "JAVA Source File";
//    }
//    if(extension.equals("html")) {
//      return "HTML File";
//    }
//    if(extension.equals("txt")) {
//      return "Text File";
//    }
//    if(extension.equals("jsp")) {
//      return "Java Server Pages File";
//    }
//    return "";
//  }
//
//  public Icon getIcon(File f) {
//    
//    String extension = getExtensionName(f);
//    if(extension.equals("java")) {
//      
//      return new ImageIcon(notepad.Notepad_Frame1.class.getResource("java.gif"));
//    }
//    if(extension.equals("html")) {
//      return new ImageIcon(notepad.Notepad_Frame1.class.getResource("html.gif"));
//    }
//    if(extension.equals("txt")) {
//      return new ImageIcon(notepad.Notepad_Frame1.class.getResource("txt.gif"));
//    }
//    if(extension.equals("jsp")) {
//      return new ImageIcon(notepad.Notepad_Frame1.class.getResource("jsp.gif"));
//    }
//    return null;
//  }
  
  //********************************************************************
  /**
   * 返回值为null的话,java look and feel功能会处历阳这个消嗫,并取得相关<BR>
   * 值来加以设置.若你不希望某个目录被浏览,则返回值可以设为Boolean.FALSE.
   * 
   * @param f 
   */
  //********************************************************************
//  public Boolean isTraversable(File f) {
//  	return null; 
//                     
//  }

  //********************************************************************
  /**
   * 在FileIcon类中我们增加一个getExtensionName()方法,用来返回文??的扩展??<BR>
   * 名称.
   * 
   * @param f 
   */
  //********************************************************************
//  public String getExtensionName(File f) {
//   	String extension ="";
//	  String fileName = f.getName();
//        int index = fileName.lastIndexOf('.');
//
//        if (index > 0 && index < fileName.length()-1) {
//            extension = fileName.substring(index+1).toLowerCase();
//        }
//        return extension;
//    }
//}*/

class Notepad_Frame1_jMenuFind_actionAdapter implements ActionListener {
  
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFind_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFind_actionPerformed(e);
  }
}

class Notepad_Frame1_jMenuFindReplace_actionAdapter implements ActionListener {
  Notepad_Frame1 adaptee;

  Notepad_Frame1_jMenuFindReplace_actionAdapter(Notepad_Frame1 adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jMenuFindReplace_actionPerformed(e);
  }
}
