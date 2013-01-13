/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DivineView.java
 *
 * Created on 2010-4-3, 17:54:07
 */

package org.divine.gui;

import java.awt.Component;
import java.awt.dnd.DropTarget;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListCellRenderer;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.JTextComponent;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.undo.UndoManager;

import org.divine.actions.ManualAction;
import org.divine.app.liuyao.EightDiagrams;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.Yao;
import org.divine.app.liuyao.resolve.Level;
import org.divine.app.liuyao.resolve.ResolveEvent;
import org.divine.app.liuyao.resolve.ResolverListener;
import org.divine.gui.datatransfer.LYDropTargetListener;
import org.divine.persistance.LiuyaoDataManager;
import org.jtools.app.persistent.DataException;
import org.zhiwu.app.AbsView;
import org.zhiwu.app.Application;
import org.zhiwu.app.action.AppAction;
import org.zhiwu.utils.AppResources;
import org.zhiwu.utils.DateUtils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * 1.divine/liuyao是否显示天干;
 * 2.divine/liuyao是否显示神煞;
 * 3.divine/liuyao/save保存为txt/doc/xml 等格式;
 * 4.divine/liuyao是否显示卦身
 * @author root
 */
public class DivineView extends AbsView {
	private static final long serialVersionUID = 1L;
	private LiuYao liuyao;
	private DefaultStyledDocument doc;
	private Style DEFAULT_STYLE;
	private Style EMPHASIS_STYLE;// emphasis
	private DeleteLiuyaoAction deleteLiuyaoAction;
	
	private final UndoManager undoManager=new UndoManager(){
		/**
		 * 
		 */
		private static final long serialVersionUID = 5292403681223959756L;

		@Override
		public void undoableEditHappened(javax.swing.event.UndoableEditEvent e) {
			this.addEdit(e.getEdit());
		};
	};
	
	private final DocumentListener dl=new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			setUnsaveChanged(true);
		}
		@Override
		public void insertUpdate(DocumentEvent e) {
			setUnsaveChanged(true);
		}
		@Override
		public void changedUpdate(DocumentEvent e) {
			setUnsaveChanged(true);
		}
	};
	
	{
		StyleContext sc = new StyleContext();

		DEFAULT_STYLE = sc.getStyle(StyleContext.DEFAULT_STYLE);
		DEFAULT_STYLE = sc.addStyle("MainStyle", DEFAULT_STYLE);
		StyleConstants.setFontFamily(DEFAULT_STYLE, "宋体");
		StyleConstants.setLeftIndent(DEFAULT_STYLE, 16);
		StyleConstants.setRightIndent(DEFAULT_STYLE, 16);
		StyleConstants.setFirstLineIndent(DEFAULT_STYLE, 16);
		StyleConstants.setFontFamily(DEFAULT_STYLE, "serif");
		StyleConstants.setFontSize(DEFAULT_STYLE, 16); 
		// StyleConstants.setForeground(DEFAULT_STYLE, Color.RED);
		doc = new DefaultStyledDocument(sc);
		doc.setLogicalStyle(0, DEFAULT_STYLE);

		EMPHASIS_STYLE = sc.addStyle("ConstantWidth", null);
		StyleConstants.setFontFamily(EMPHASIS_STYLE, "宋体");
		StyleConstants.setBold(EMPHASIS_STYLE, true);

		// correctStyle = sc.addStyle("ConstantWidth", null);
		// StyleConstants.setFontFamily(correctStyle, "宋体");
		// // StyleConstants.setForeground(correctStyle, Color.RED);
		// StyleConstants.setFontSize(correctStyle, 16);
		// StyleConstants.setBackground(correctStyle, new Color(178,230,142));
		// // StyleConstants.setBold(correctStyle, true);
		//
		// // Create and add the heading style
		// wrongStyle = sc.addStyle("Heanding2", null);
		// StyleConstants.setForeground(wrongStyle, Color.RED);
		// StyleConstants.setFontSize(wrongStyle, 16);
		// StyleConstants.setFontFamily(wrongStyle, "宋体");// ????
		// // StyleConstants.setLeftIndent(wrongStyle, 8);// 左缩进
		// StyleConstants.setFirstLineIndent(wrongStyle, 0);
		// StyleConstants.setStrikeThrough(wrongStyle, true);
	}

	/** Creates new form DivineView */
	public DivineView() {

		// app.getm

		initComponents();
		initDocument();

		String[] s = new String[] { "动静阴阳，反覆迁变。", "虽万象之纷纭，须一理而融贯。",
				"夫！人有贤不肖之殊，卦有过不及之异，太过者损之斯成，不及者益之则利。", "生扶拱合，时雨滋苗。",
				"克害刑冲，秋霜杀草。", "长生帝旺，争如金谷之园。", "死墓绝空，乃是泥犁之地。 ",
				"日辰为六爻之主宰，喜其灭项以安刘。", "月建乃万卦之提纲，岂可助桀而为虐。", "最恶者岁君，宜静不宜动。",
				"最要者身位，喜扶不喜伤。", "世为己，应为人，大且契合。", "动为始，变为终，最怕交争。",
				"应位遭伤，不利他人之事。", "世爻受制，岂宜自己之谋。", "世应俱空，人无准实。", "内外竞发，事必翻腾。",
				"世或交重，两目顾瞻於马首。", "应如发动，一心似托於猿攀。", "用神有气无他故，所作皆成。",
				"主象徒存更被伤，凡谋不遂。", "有伤须救，无故勿空。", "空逢冲而有用，合遭破以无功。", "自空化空，必成凶咎。",
				"刑合克合，终见乖淫。", "动值合而绊住，静得冲而暗兴。", "入墓难克，带旺匪空。", "有助有扶，衰弱休囚亦吉。",
				"贪生贪合，刑冲克害皆忘。", "别衰旺以明克合，辨动静以定刑冲。", "并不并，冲不冲，因多字眼。",
				"刑非刑，合非合，为少支神。", "爻遇令星，物难我害。", "伏居空地，事与愿违。",
				"伏无提拔终徒尔，飞不推开亦枉然。", "空下伏神易於引拔，制中弱主难以维持。", "日伤爻，真罹其祸。",
				"爻伤日，徒受其名。", "墓中人，不冲不发。", "身上鬼，不去不安。", "德入卦而无谋不遂，忌临身而多阻无成。",
				"卦遇凶星，避之则吉。", "爻逢忌杀，敌之无伤。", "主象休囚，怕见刑冲克害。", "用爻变动，忌遭死墓绝空。",
				"用化用，有用无用。", "空化空，虽空不空。", "养主狐疑，墓多暗味；", "化病兮伤损，化胎兮勾连。",
				"凶化长生，炽而未散。", "吉连沐浴，败而不成。", "戒回头之克我，勿反德以扶人。", "恶曜孤寒，怕日辰之并起。",
				"用爻重叠，喜墓库之收藏。", "事阻隔兮间发，心退悔兮世空。", "卦爻发动，须看交重。", "动变比和，当了进退。",
				"煞生身莫将吉断，用克世勿作凶看，盖生中有刑害之两防，而合处有克伤之一虑。", "刑害不宜临用，死绝岂可持身。",
				"动逢冲而事散，绝逢生而事成。", "如逢合住，须冲破以成功。", "若遇休囚，必生旺而成事。",
				"速则动而克世，缓则静而生身。", "父亡而事无头绪，福隐而事不称情。", "鬼虽祸灾，伏犹无气。",
				"子虽福德，多反无功。", "究父母推为体统，论官鬼断作祸殃，财乃禄神，子为福德。",
				"兄弟交重，必至谋为多阻滞。卦身重叠，须知事体两交关。", "虎兴而遇吉神，不害其为吉。", "龙动而逢凶曜，难掩其为凶。",
				"玄武主盗贼之事，亦必官爻。", "朱雀本口舌之神，然须兄弟。", "疾病大宜天喜，若临凶煞必生悲。",
				"出行最怕往亡，如系吉神终获利。", "是故吉凶神煞之多端，何如生克制化之一理。",
				"呜呼！卜易者知前则易；求占者鉴後则灵。", "筮必诚心！何妨子日！ " };
		jLabel1.setText(s[(int) Math.abs(Math.random() * s.length)]);
		jLabel2.setText(s[(int) Math.abs(Math.random() * s.length)]);
		jLabel3.setText(s[(int) Math.abs(Math.random() * s.length)]);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhiwu.app.AbsView#init()
	 */
	@Override
	public void init() {
		super.init();
		new DropTarget(jLabel1, new LYDropTargetListener());
		setListModel();
		jList2.addMouseListener(new MouseAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2){
					if(DivineView.this.liuyao !=null){
						if(hasUnsavChanged()){
							int op=JOptionPane.showConfirmDialog(DivineView.this, "是否保存卦例?", getTitle(), JOptionPane.YES_NO_CANCEL_OPTION);
							if(op==JOptionPane.YES_OPTION){
								save();
							}else if (op==JOptionPane.CANCEL_OPTION) {
								return;
							}
						}
					}
					int s=jList2.getSelectedIndex();
					LiuYao ly=(LiuYao)jList2.getModel().getElementAt(s);
					setData(ly);
//					jList2.setSelectedIndex(0);
					
					setUnsaveChanged(false);
				}
				deleteLiuyaoAction.setEnabled(true);
			}
		});
		
		deleteLiuyaoAction=new DeleteLiuyaoAction(app,jList2);
		jToolBar3.add(deleteLiuyaoAction);
		
	
		jTextPane1.addCaretListener(new CaretListener() {
			@Override
			public void caretUpdate(CaretEvent e) {
				DivineView.this.caretUpdate(e);
			}
		});
		
		jTextPane1.addKeyListener(new KeyAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyPressed(KeyEvent e) {
				int keycode = e.getKeyCode();
				if (e.getModifiers() == KeyEvent.CTRL_MASK) {
					if (keycode == KeyEvent.VK_Z) {
						if (undoManager.canUndo())
							undoManager.undo();
					}
					if (keycode == KeyEvent.VK_Y) {
						if (undoManager.canRedo())
							undoManager.redo();
					}
				}

			}
		});
		
		
		addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				if (!e.getPropertyName().equals("data")) {
					return;
				}
				try {
					final String[] listModel = liuyao.paiPan();
					if (liuyao.getParseText().length() != 0) {
						byte[] docBytes=new BASE64Decoder().decodeBuffer(liuyao.getParseText());
						ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(docBytes));
						doc=(DefaultStyledDocument) ois.readObject();
						setStyledDocument(doc);
//						return;
					}else {
						doc.remove(0, doc.getLength());

						doc.insertString(doc.getLength(), "占事:", DEFAULT_STYLE);
						doc.insertString(doc.getLength(), liuyao.getProblem(),EMPHASIS_STYLE);

						doc.insertString(doc.getLength(), "\n起卦时间: ",DEFAULT_STYLE);
						doc.insertString(doc.getLength(), liuyao.getSolarString(),EMPHASIS_STYLE);

						doc.insertString(doc.getLength(), "\n起卦农历: ",DEFAULT_STYLE);
						doc.insertString(doc.getLength(), liuyao.getLunarString(),EMPHASIS_STYLE);

						// doc.insertString(doc.getLength(), "月节气: ",
						// DEFAULT_STYLE);
						// doc.insertString(doc.getLength(),
						// liuyao.getLunarTerm() + "\n",
						// EMPHASIS_STYLE);

						// doc.insertString(doc.getLength(), "神煞: ",
						// DEFAULT_STYLE);
						// doc.insertString(doc.getLength(), "还没有   哈哈 \n",
						// EMPHASIS_STYLE);

						doc.insertString(doc.getLength(), "\n干支: ", DEFAULT_STYLE);
						doc.insertString(doc.getLength(), listModel[1]+"\n", DEFAULT_STYLE);
						doc.insertString(doc.getLength(), listModel[2]+"\n", DEFAULT_STYLE);
						for (int i = 5; i >= 0; i--) {
							doc.insertString(doc.getLength(), listModel[8-i]+"\n", DEFAULT_STYLE);
						}
						
//						doc.insertString(doc.getLength(), liuyao.getGanZhiString(),EMPHASIS_STYLE);

//						doc.insertString(doc.getLength(), "\n旬空: ", DEFAULT_STYLE);
//						doc.insertString(doc.getLength(), liuyao.getEmptyString(),EMPHASIS_STYLE);


						
//						doc.insertString(doc.getLength(), "\n六神\t", DEFAULT_STYLE);
//						doc.insertString(doc.getLength(), "伏神　", DEFAULT_STYLE);
//
//						doc.insertString(doc.getLength(),diaStrings[diaStrings.length - 1] + "　　　　",DEFAULT_STYLE);
//						doc.insertString(doc.getLength(),chasStrings[chasStrings.length - 1] + "\n",DEFAULT_STYLE);

					
						
						// if (liuyao.getRepeat() != LiuYao.REPEAT_NON) {
						// doc.insertString(doc.getLength(),
						// liuyao.getRepeatString()
						// + "\n", DEFAULT_STYLE);
						// } else if (liuyao.getReverse() !=
						// LiuYao.REVERSE_NON) {
						// doc.insertString(doc.getLength(),
						// liuyao.getReverseString()
						// + "\n", DEFAULT_STYLE);
						// }

						// 分析
//						EightDiagrams changeDiagrams=liuyao.getChangeDiagrams();
//						EightDiagrams diagrams=liuyao.getDiagrams();
//						outputAnalyse(changeDiagrams, doc, Level.CHANGED_LEVEL);
//						outputAnalyse(changeDiagrams, doc, Level.CHANGE_LEVEL);
//						outputAnalyse(diagrams, doc, Level.YAO_LEVEL);


						doc.insertString(doc.getLength(), "\n起卦程序:先知六爻", DEFAULT_STYLE);
						
						liuyao.addResolverListener(new ResolverListener() {

							@Override
							public void outputResolve(ResolveEvent e) {
								Yao yao = e.getYao();

								System.out.print(yao.getLevel().getName() + "("
										+ (yao.getIndex() + 1) + "爻" + ")"
										+ yao.getRelative() + yao.getBranch()
										+ yao.getRelationString() +
										(yao.hasPower()?"有":"无")+"权\n");
							}
							
						});
						liuyao.resolve();
						
					}
					
					jList1.setModel(new AbstractListModel() {
						/**
						 * 
						 */
						private static final long serialVersionUID = -8265604992766183841L;

						@Override
						public int getSize() {
							return listModel.length;
						}

						@Override
						public Object getElementAt(int arg0) {
							return listModel[arg0];
						}
					});
					jTextPane1.setCaretPosition(0);
				} catch (DataException de) {
					JOptionPane.showConfirmDialog(DivineView.this, "data error"
							+ de.getMessage());
				} catch (BadLocationException be) {
					be.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (ClassNotFoundException e3) {
					e3.printStackTrace();
				}

			}
		});
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#initActions()
	 */
	@Override
	protected void initActions() {
		putAction(ManualAction.ID, new ManualAction(app));
	}
	
	/**
	 * 
	 */
	protected void setListModel() {
		try {
			jList2.setModel(new XMLListModel(LiuyaoDataManager.getInstance().getSaved()));
			jList2.setCellRenderer(new XMLCellRender());
		} catch (DataException e2) {
			e2.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private void initDocument() {
		setStyledDocument(doc);
		// jScrollPane2.setVisible(false);
		try {
			doc
					.insertString( 
							0,
							"\n先知六爻旨在为六爻爱好者提供断卦辅助信息以及一些辅助功能, "
									+ "软件还处在开发阶段, 如有问题请参考:\n博客: http://blog.sina.com.cn/kkliuyao/ \n\n请点击\"手动起卦\"开始排盘",
							EMPHASIS_STYLE);
			setUnsaveChanged(false);
		} catch (BadLocationException ex) {
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jToolBar2 = new javax.swing.JToolBar();
        jButton4 = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane5 = new javax.swing.JScrollPane();
        jPanel3 = new javax.swing.JPanel();
        jToolBar3 = new javax.swing.JToolBar();
        jScrollPane6 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();

        setPreferredSize(new java.awt.Dimension(600, 500));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 5));

        jLabel1.setText("jLabel1");
        jPanel1.add(jLabel1);

        jLabel2.setText("2233");
        jPanel1.add(jLabel2);

        jLabel3.setText("jLabel3");
        jPanel1.add(jLabel3);

        add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jSplitPane3.setResizeWeight(0.2);

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.Y_AXIS));

        jToolBar1.setRollover(true);

        jButton1.setText("用神");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jButton2.setText("四柱");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton2);

        jButton3.setText("应期");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);

        jPanel2.add(jToolBar1);

        jToolBar2.setRollover(true);

        jButton4.setText("对比");
        jButton4.setFocusable(false);
        jButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar2.add(jButton4);

        jPanel2.add(jToolBar2);

        jScrollPane3.setViewportView(jPanel2);

        jSplitPane2.setLeftComponent(jScrollPane3);

        jList1.setDragEnabled(true);
        jScrollPane4.setViewportView(jList1);

        jSplitPane2.setRightComponent(jScrollPane4);

        jScrollPane2.setViewportView(jSplitPane2);

        jSplitPane1.setLeftComponent(jScrollPane2);

        jTextPane1.setDragEnabled(true);
        jScrollPane1.setViewportView(jTextPane1);

        jSplitPane1.setRightComponent(jScrollPane1);

        jSplitPane3.setRightComponent(jSplitPane1);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("已存卦例"));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jToolBar3.setRollover(true);
        jPanel3.add(jToolBar3, java.awt.BorderLayout.PAGE_END);

        jScrollPane6.setViewportView(jList2);

        jPanel3.add(jScrollPane6, java.awt.BorderLayout.CENTER);

        jScrollPane5.setViewportView(jPanel3);

        jSplitPane3.setLeftComponent(jScrollPane5);

        add(jSplitPane3, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JToolBar jToolBar3;
    // End of variables declaration//GEN-END:variables
	/**
	 * @param liuyao
	 */
	public void setData(LiuYao liuYao) {
		LiuYao oldValue = this.liuyao;
		this.liuyao = liuYao;
		firePropertyChange("data", oldValue, this.liuyao);
		setUnsaveChanged(true);
	}

	/**
	 * @param changeDiagrams
	 * @throws BadLocationException
	 */
	@SuppressWarnings("unused")
	private void outputAnalyse(EightDiagrams diagrams, Document doc, Level level)
			throws BadLocationException {
		if (diagrams != null) {
			for (int i = 0; i < 6; i++) {
				org.divine.app.liuyao.Yao yao = diagrams.getYao(i);
				if (yao.getLevel().equals(level)) {
					doc.insertString(doc.getLength(), level.getName() + "("
							+ (i + 1) + "爻" + ")" + yao.getRelative()
							+ yao.getBranch() + yao.getRelationString() + "\n",
							DEFAULT_STYLE);
				}
			}
		}
	}

	/**
	 * 
	 */
	public LiuYao getData() {
		return liuyao;
	}

	/**
	 * 
	 */
	public StyledDocument getDoc() {
		return doc;
	}
	
	/* (non-Javadoc)
	 * @see org.zhiwu.app.AbsView#save()
	 */
	@Override
	public void save() {
		if(liuyao == null){
			return ;
		}
		
		
		ByteArrayOutputStream baos=new ByteArrayOutputStream();
		ObjectOutputStream os;
		try {
			doc.removeDocumentListener(dl);
			doc.removeUndoableEditListener(undoManager);
			
			os = new ObjectOutputStream(baos);
			os.writeObject(doc);
			baos.flush();
			baos.close();
			os.flush();
			os.close();
			liuyao.setParseText(new BASE64Encoder().encode(baos.toByteArray()));
			
//			setStyledDocument(liuyao.save(doc));
			liuyao.save();
			setUnsaveChanged(false);
			setListModel();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @param save
	 */
	public void setStyledDocument(StyledDocument doc) {
		jTextPane1.setStyledDocument(doc);
		doc.addDocumentListener(dl);
		doc.addUndoableEditListener(undoManager);
	}

	/**
	 * @param e
	 */
	protected void caretUpdate(CaretEvent e) {
		JTextComponent r = (JTextComponent) e.getSource();
		try {
			int pos = r.getCaretPosition();
			int line = getLineOffset(r, pos);
			int lineStartOffset = getLineStartOffset(r, line);
			jLabel1.setText((line + 1) + ":" + (pos - lineStartOffset + 1));
		} catch (BadLocationException e1) {
			e1.printStackTrace();
		}
	}
	
	
	public int getLineOffset(JTextComponent r, int offset)
	throws BadLocationException {
		Document doc = r.getDocument();
		if (offset < 0) {
			throw new BadLocationException("offset must be greater than 0",
					offset);
		} else if (offset > doc.getLength()) {
			throw new BadLocationException(
					"offset must be less than the length of the document",
					offset);
		} else {
			Element root = doc.getDefaultRootElement();
			return root.getElementIndex(offset);
		}
	}
	
	public int getLineStartOffset(JTextComponent editor, int line)
	throws BadLocationException {
		Document doc = editor.getDocument();
		// int lineCount = getLineCount();
		if (line < 0) {
			throw new BadLocationException("offset must be greater than 0",
					line);
		}
		// else if (line > lineCount) {
		// throw new BadLocationException(
		// "offset must be less than the length of the document", line);
		// }
		else {
			Element root = doc.getDefaultRootElement();
			Element lineElement = root.getElement(line);
			return lineElement.getStartOffset();
		}
	}
	
	
	class DeleteLiuyaoAction extends AppAction{
		private static final long serialVersionUID = 1L;
		public static final String ID="delete";
		private final JList list;
		
		/**
		 * @param app
		 */
		public DeleteLiuyaoAction(Application app,JList list) {
			super(app);
			this.list=list;
			AppResources resource = AppResources.getResources("org.jtools.app.Labels");
			resource.configAction(this, ID);
			setEnabled(false);
		}

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			app.setEnable(false);
			try {
				LiuyaoDataManager manager=LiuyaoDataManager.getInstance();
				int[] selectes=list.getSelectedIndices();
				
				int op=JOptionPane.showConfirmDialog(// TODO I18N
						DivineView.this, 
						"确定删除所选卦例?",
						getTitle(), 
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE);
				if (op!=JOptionPane.OK_OPTION) {
					return;
				}
				
				
				XMLListModel model=(XMLListModel) list.getModel();
				for(int i=0,j=selectes.length;i<j;i++){
					LiuYao ly=(LiuYao) model.getElementAt(selectes[i]);
					manager.deleteLiuyao(ly);
				}
				setListModel();
			} catch (DataException e1) {
				e1.printStackTrace();
			}
			setEnabled(false);
			app.setEnable(true);
		}
		@Override
		public String getID() {
			return ID;
		}
		
	}	
}

class XMLListModel extends AbstractListModel{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7987218194878189589L;
	private final List<LiuYao> liuYaos;

	/**
	 * 
	 */
	public XMLListModel(List<LiuYao> data) {
		this.liuYaos=data;
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int index) {
		return liuYaos.get(index);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		return liuYaos.size();
	}
	
}

class XMLCellRender extends JLabel implements ListCellRenderer{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4284702095558991702L;

	/**
	 * 
	 */
	public XMLCellRender() {
		setOpaque(true);
	}

	/* (non-Javadoc)
	 * @see javax.swing.ListCellRenderer#getListCellRendererComponent(javax.swing.JList, java.lang.Object, int, boolean, boolean)
	 */
	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {
		LiuYao ly=(LiuYao)value;
		setText(ly.getProblem()+" "+DateUtils.format(ly.getLunar().getCalendar().getTime(), "yy-MM-dd HH:mm:ss"));
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		return this;
	}
}






















