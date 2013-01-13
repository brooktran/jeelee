/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ManualDialog.java
 *
 * Created on 2010-4-3, 17:44:42
 */

package org.divine.gui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.divine.app.liuyao.EightDiagrams;
import org.divine.app.liuyao.FourQuadrant;
import org.divine.app.liuyao.LiuYao;
import org.divine.app.liuyao.Lunar;
import org.zhiwu.app.AppDialog;
import org.zhiwu.app.Application;

/**
 *
 * @author root
 */
public class ManualDialog extends AppDialog {
	private static final long serialVersionUID = 1L;
	private final Yao[] yaos = new Yao[6];
	private Lunar lunar;
	private DefaultStyledDocument doc;
	private Style defaultStyle;
	private KeyAdapter VK_ENTER_Adapter;
	
	private final ItemListener listener1 = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				removeComboListener();
				setupComboBox();
				addComboListener();
			}
		}
	};

	private final ItemListener listener2 = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				removeComboListener();
				if (jComboBox1.getSelectedItem().equals("农历")) {
					lunar.setCalendar(Lunar.lunarToSolar(jComboBox2
							.getSelectedIndex()
							+ Lunar.BASE_YEAR,
							jComboBox3.getSelectedIndex() + 1, jComboBox4
									.getSelectedIndex(), jComboBox5
									.getSelectedIndex() + 1, jComboBox6
									.getSelectedIndex() + 1, 0));
				} else {
					lunar.set(Calendar.YEAR, Integer.parseInt(e.getItem()
							.toString()));
				}
				setupComboBox();
				addComboListener();
			}
		}
	};

	private final ItemListener listener3 = new ItemListener() {// month
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				removeComboListener();
				if (jComboBox1.getSelectedItem().equals("农历")) {
					lunar.set(Lunar.LUNAR_MONTH, ((JComboBox) e.getSource())
							.getSelectedIndex() + 1);
				} else {
					lunar.set(Calendar.MONTH, ((JComboBox) e.getSource())
							.getSelectedIndex() + 1);
				}
				setupComboBox();
				addComboListener();
			}
		}
	};

	private final ItemListener listener4 = new ItemListener() {// day
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				removeComboListener();
				if (jComboBox1.getSelectedItem().equals("农历")) {
					lunar.set(Lunar.LUNAR_DAY, ((JComboBox) e.getSource())
							.getSelectedIndex()+1);
				} else {
					lunar.set(Calendar.DAY_OF_MONTH,
							((JComboBox) e.getSource()).getSelectedIndex());
				}
				setupComboBox();
				addComboListener();
			}
		}
	};

	private final ItemListener listener5 = new ItemListener() {
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				removeComboListener();
				lunar.set(Calendar.HOUR_OF_DAY, ((JComboBox) e.getSource())
						.getSelectedIndex() + 1);
				setupComboBox();
				addComboListener();
			}
		}
	};

	/** Creates new form ManualDialog */
	public ManualDialog(Application app) {
		super(app);
		initComponents();
		init();
		
		//System.out.println(getLocale());
	}

	private void init() {
		Calendar calendar = Calendar.getInstance();
		lunar = new Lunar(calendar);

		StyleContext sc = new StyleContext();
		defaultStyle = sc.getStyle(StyleContext.DEFAULT_STYLE);
		defaultStyle = sc.addStyle("MainStyle", defaultStyle);
		StyleConstants.setFontFamily(defaultStyle, "宋体");
		StyleConstants.setLeftIndent(defaultStyle, 16);
		StyleConstants.setRightIndent(defaultStyle, 16);
		StyleConstants.setFirstLineIndent(defaultStyle, 16);
		StyleConstants.setFontFamily(defaultStyle, "serif");
		StyleConstants.setFontSize(defaultStyle, 14);
		StyleConstants.setForeground(defaultStyle, Color.RED);
		doc = new DefaultStyledDocument(sc);
		doc.setLogicalStyle(0, defaultStyle);
		jTextPane1.setDocument(doc);

		setupComboBox();

		//
		for (int i = 5; i >= 0; i--) {
			yaos[i] = new Yao(Lunar.getChineseNumber(i + 1) + "爻");
			jPanel2.add(yaos[i]);
		}
		setSize(500, 500);
		addComboListener();

		VK_ENTER_Adapter=new KeyAdapter() {
			/* (non-Javadoc)
			 * @see java.awt.event.KeyAdapter#keyTyped(java.awt.event.KeyEvent)
			 */
			@Override
			public void keyTyped(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ENTER){
					jButton1ActionPerformed(null);
				}
			}
		};
		
		jComboBox1.addKeyListener(VK_ENTER_Adapter);
		jComboBox2.addKeyListener(VK_ENTER_Adapter);
		jComboBox3.addKeyListener(VK_ENTER_Adapter);
		jComboBox4.addKeyListener(VK_ENTER_Adapter);
		jComboBox5.addKeyListener(VK_ENTER_Adapter);
		jTextField1.addKeyListener(VK_ENTER_Adapter);
		
		for(int i=0,j=yaos.length;i<j;i++){
			yaos[i].sixYaoBox.addKeyListener(VK_ENTER_Adapter);
		}
	}

	/**
	 * 
	 */
	private void addComboListener() {
		jComboBox1.addItemListener(listener1);
		jComboBox2.addItemListener(listener2);
		jComboBox3.addItemListener(listener3);
		jComboBox4.addItemListener(listener4);
		jComboBox5.addItemListener(listener5);
	}

	/**
	 * 
	 */
	private void removeComboListener() {
		jComboBox1.removeItemListener(listener1);
		jComboBox2.removeItemListener(listener2);
		jComboBox3.removeItemListener(listener3);
		jComboBox4.removeItemListener(listener4);
		jComboBox5.removeItemListener(listener5);
	}

	/**
	 * 
	 */
	private void setupComboBox() {
		String[] comboBoxModel;
		// jCombox2 年
		comboBoxModel = new String[Lunar.LAST_YEAR - Lunar.BASE_YEAR];
		for (int i = Lunar.BASE_YEAR; i < Lunar.LAST_YEAR; i++) {
			comboBoxModel[i - Lunar.BASE_YEAR] = i + "";
		}
		jComboBox2.setModel(new DefaultComboBoxModel(comboBoxModel));

		if (jComboBox1.getSelectedItem().equals("农历")) {
			jComboBox2.setSelectedIndex(lunar.get(Lunar.LUNAR_YEAR) - 1900);

			// jCombox3 月
			comboBoxModel = new String[12];
			for (int i = 1; i < 13; i++) {
				comboBoxModel[i - 1] = Lunar.chineseNumber[i] + "月";
			}
			jComboBox3.setModel(new DefaultComboBoxModel(comboBoxModel));
			jComboBox3.setSelectedIndex(lunar.get(Lunar.LUNAR_MONTH) - 1);

			// jCombox4 日
			int days = Lunar.lunarMonthDays(lunar.get(Lunar.LUNAR_YEAR), lunar
					.get(Lunar.LUNAR_MONTH));
			comboBoxModel = new String[days];
			for (int i = 0; i < days; i++) {
				comboBoxModel[i] = Lunar.getChinaDayString(i + 1);
			}
			jComboBox4.setModel(new DefaultComboBoxModel(comboBoxModel));
			jComboBox4.setSelectedIndex(lunar.get(Lunar.LUNAR_DAY)-1 );

			// jComboBox5 时
			// comboBoxModel=new String[12];
			// for (int i = 0,j=23; i < 12; i++) {
			// comboBoxModel[i] = Lunar.Zhi[i]+"时 "+j%24+" ~ "+(j+2)%24;
			// j+=2;
			// }
			// jComboBox5.setModel(new DefaultComboBoxModel(comboBoxModel));
			// jComboBox5.setSelectedIndex(lunar.getLunarTime().getBranch().getValue());

		} else {
			//System.out.println(lunar.get(Calendar.YEAR));
			jComboBox2.setSelectedIndex(lunar.get(Calendar.YEAR) - 1900);

			// jCombox3 月
			comboBoxModel = new String[12];
			for (int i = 1; i < 13; i++) {
				comboBoxModel[i - 1] = i + "月";
			}
			jComboBox3.setModel(new DefaultComboBoxModel(comboBoxModel));
			jComboBox3.setSelectedIndex(lunar.get(Calendar.MONTH));

			// jCombox4 日
			int month = lunar.get(Calendar.MONTH) + 1;
			int days = (month == 1 || month == 3 || month == 5 || month == 7
					|| month == 8 || month == 10 || month == 12) ? 31
					: (month == 2 ? 28 : 30);

			comboBoxModel = new String[days];
			for (int i = 0; i < days; i++) {
				comboBoxModel[i] = (i + 1) + "日";
			}
			jComboBox4.setModel(new DefaultComboBoxModel(comboBoxModel));
			jComboBox4.setSelectedIndex(lunar.get(Calendar.DAY_OF_MONTH)-1);
		}

		comboBoxModel = new String[24];
		for (int i = 0; i < 24; i++) {
			comboBoxModel[i] = i + "时";
		}
		jComboBox5.setModel(new DefaultComboBoxModel(comboBoxModel));
		jComboBox5.setSelectedIndex(lunar.get(Calendar.HOUR_OF_DAY));

		comboBoxModel = new String[60];
		for (int i = 0; i < 60; i++) {
			comboBoxModel[i] = i + "分";
		}
		jComboBox6.setModel(new DefaultComboBoxModel(comboBoxModel));
		jComboBox6.setSelectedIndex(lunar.get(Calendar.MINUTE));

		try {
			doc.remove(0, doc.getLength());
			doc.insertString(0, lunar.toString(), defaultStyle);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		jTextPane1.setLocation(0, 0);
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox();
        jComboBox2 = new javax.swing.JComboBox();
        jComboBox3 = new javax.swing.JComboBox();
        jComboBox4 = new javax.swing.JComboBox();
        jComboBox5 = new javax.swing.JComboBox();
        jComboBox6 = new javax.swing.JComboBox();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();

        jPanel1.setAutoscrolls(true);
        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel5.setAutoscrolls(true);
        jPanel5.setLayout(new java.awt.BorderLayout());

        jTextPane1.setEditable(false);
        jScrollPane2.setViewportView(jTextPane1);

        jPanel5.add(jScrollPane2, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel5, java.awt.BorderLayout.CENTER);

        jLabel1.setLabelFor(jTextField1);
        jLabel1.setText("占事:");
        jPanel4.add(jLabel1);

        jTextField1.setMinimumSize(new java.awt.Dimension(10, 21));
        jTextField1.setPreferredSize(new java.awt.Dimension(80, 21));
        jPanel4.add(jTextField1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "农历", "阳历" }));
        jPanel4.add(jComboBox1);

        jPanel4.add(jComboBox2);

        jPanel4.add(jComboBox3);

        jPanel4.add(jComboBox4);

        jPanel4.add(jComboBox5);

        jPanel4.add(jComboBox6);

        jPanel1.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(jPanel1, java.awt.BorderLayout.NORTH);

        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        jButton1.setText("确定");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);

        jButton2.setText("取消");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton2);

        getContentPane().add(jPanel3, java.awt.BorderLayout.PAGE_END);
        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

	private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton1ActionPerformed
		FourQuadrant[] sixYaos = new FourQuadrant[6];
		for (int i = yaos.length - 1; i >= 0; i--) {
			sixYaos[i] = (FourQuadrant) yaos[i].sixYaoBox.getSelectedItem();
		}
		EightDiagrams diagrams = new EightDiagrams(sixYaos);
		diagrams.setup();
		
		LiuYao liuYao = new LiuYao(lunar, diagrams);
		String problem=jTextField1.getText();
		if(problem.equals( "")){
			problem="占事";
		}
		liuYao.setProblem(problem);
		fireOptionSelected(AppDialog.COMFIRM_OPTION, liuYao);
	}// GEN-LAST:event_jButton1ActionPerformed

	private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jButton2ActionPerformed
		fireOptionSelected(CLOSE_OPTION);
	}// GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JComboBox jComboBox3;
    private javax.swing.JComboBox jComboBox4;
    private javax.swing.JComboBox jComboBox5;
    private javax.swing.JComboBox jComboBox6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextPane jTextPane1;
    // End of variables declaration//GEN-END:variables

}

class Yao extends JPanel {
	public JComboBox sixYaoBox;
	private JLabel label;

	public Yao(String name) {
		init(name);
	}

	/**
	 *
	 */
	private void init(String name) {
		setLayout(new FlowLayout(FlowLayout.CENTER, 40, 0));
		label = new JLabel(FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG).getName());

		add(new JLabel(name));

		sixYaoBox = new JComboBox();
		sixYaoBox.setModel(new DefaultComboBoxModel(new FourQuadrant[] {
				FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.SHAO_YIN),
				FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YANG), FourQuadrant.getFourQuadrant(FourQuadrant.TAI_YIN) }));
		add(sixYaoBox);
		sixYaoBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setText(sixYaoBox.getSelectedItem().toString());
			}
		});
		add(label);

	}
}
