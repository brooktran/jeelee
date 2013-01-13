package notepad;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogFindAndReplace extends JDialog {
  
  JPanel panel1 = new JPanel();
  JLabel jLabel1 = new JLabel();
  JTextField jTextFieldFindContent = new JTextField();
  JButton jButtonFindNext = new JButton();
  JButton jButtonReplace = new JButton();
  JButton jButtonReplaceAll = new JButton();
  JButton jButtonClose = new JButton();
  JLabel jLabel2 = new JLabel();
  JTextField jTextFieldReplaceAs = new JTextField();
  JCheckBox jCheckBox1 = new JCheckBox();
  int start = 0;
  int end = 0;
  Notepad_Frame1 notepad_Frame = null;

  public DialogFindAndReplace(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    this.notepad_Frame = (Notepad_Frame1)frame;
    try {
      jbInit();
      pack();
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DialogFindAndReplace() {
    
    this(null, "", false);
  }
  private void jbInit() throws Exception { 
    
    panel1.setLayout(null);
    jLabel1.setFont(new java.awt.Font("Dialog", 0, 15));
    jLabel1.setText("查找内容：");
    jLabel1.setBounds(new Rectangle(28, 37, 88, 28));
    jTextFieldFindContent.setText("");
    jTextFieldFindContent.setBounds(new Rectangle(108, 37, 168, 22));
    jButtonFindNext.setBounds(new Rectangle(291, 30, 99, 26));
    jButtonFindNext.setText("查找下一个");
    jButtonFindNext.addActionListener(new DialogFindAndReplace_jButtonFindNext_actionAdapter(this));
    jButtonReplace.setBounds(new Rectangle(291, 67, 99, 26));
    jButtonReplace.setText("替换");
    jButtonReplace.addActionListener(new DialogFindAndReplace_jButtonReplace_actionAdapter(this));
    jButtonReplaceAll.setBounds(new Rectangle(291, 106, 99, 26));
    jButtonReplaceAll.setText("替换全部");
    jButtonReplaceAll.addActionListener(new DialogFindAndReplace_jButtonReplaceAll_actionAdapter(this));
    jButtonClose.setBounds(new Rectangle(291, 146, 99, 26));
    jButtonClose.setText("关闭");
    jButtonClose.addActionListener(new DialogFindAndReplace_jButtonClose_actionAdapter(this));
    jLabel2.setFont(new java.awt.Font("Dialog", 0, 15));
    jLabel2.setText("替换为:");
    jLabel2.setBounds(new Rectangle(28, 83, 88, 28));
    jTextFieldReplaceAs.setText("");
    jTextFieldReplaceAs.setBounds(new Rectangle(109, 83, 167, 22));
    jCheckBox1.setFont(new java.awt.Font("Dialog", 0, 13));
    jCheckBox1.setText("区分大小写");
    jCheckBox1.setBounds(new Rectangle(30, 137, 112, 32));
    getContentPane().add(panel1);
    panel1.add(jLabel1, null);
    panel1.add(jLabel2, null);
    panel1.add(jTextFieldFindContent, null);
    panel1.add(jTextFieldReplaceAs, null);
    panel1.add(jButtonFindNext, null);
    panel1.add(jCheckBox1, null);
    panel1.add(jButtonReplace, null);
    panel1.add(jButtonReplaceAll, null);
    panel1.add(jButtonClose, null);
  }

  void jButtonFindNext_actionPerformed(ActionEvent e) {
    if(jTextFieldFindContent.getText().equals("")) {
      JOptionPane.showMessageDialog(this,"查找内容不能为空");
    } else {
      System.out.println("jTextpane = "+notepad_Frame.getSelectFrame().jTextPane.getText().length());
      String findContent = jTextFieldFindContent.getText().trim();
      String text = notepad_Frame.getSelectFrame().jTextPane.getText();
      start = text.indexOf(findContent,end);
      if(start != -1) {
        end = findContent.length() + start;
        System.out.println("start = " + start + ",end = " + end);
        notepad_Frame.getSelectFrame().jTextPane.select(start, end);
      } else {
        JOptionPane.showMessageDialog(this,"没有此内容");
        start = 0;
        end = 0;
      }
    }
  }

  void jButtonReplace_actionPerformed(ActionEvent e) {
    jButtonFindNext_actionPerformed(e);
    if(notepad_Frame.getSelectFrame().jTextPane.getText().indexOf(jTextFieldFindContent.getText().trim()) == -1) {
      JOptionPane.showMessageDialog(this,"替换完毕");
      return;
    }
    String replaceContent = jTextFieldReplaceAs.getText();
    notepad_Frame.getSelectFrame().jTextPane.replaceSelection(replaceContent);
  }

  void jButtonReplaceAll_actionPerformed(ActionEvent e) {
    String replaceContent = jTextFieldReplaceAs.getText().trim();
    while(notepad_Frame.getSelectFrame().jTextPane.getText().indexOf(jTextFieldFindContent.getText().trim()) != -1) {
      jButtonReplace_actionPerformed(e);
    }
    JOptionPane.showMessageDialog(this,"替换完毕");
  }

  void jButtonClose_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}

class DialogFindAndReplace_jButtonFindNext_actionAdapter implements ActionListener {
  DialogFindAndReplace adaptee;

  DialogFindAndReplace_jButtonFindNext_actionAdapter(DialogFindAndReplace adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonFindNext_actionPerformed(e);
  }
}

class DialogFindAndReplace_jButtonReplace_actionAdapter implements ActionListener {
  DialogFindAndReplace adaptee;

  DialogFindAndReplace_jButtonReplace_actionAdapter(DialogFindAndReplace adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonReplace_actionPerformed(e);
  }
}

class DialogFindAndReplace_jButtonReplaceAll_actionAdapter implements ActionListener {
  DialogFindAndReplace adaptee;

  DialogFindAndReplace_jButtonReplaceAll_actionAdapter(DialogFindAndReplace adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonReplaceAll_actionPerformed(e);
  }
}

class DialogFindAndReplace_jButtonClose_actionAdapter implements ActionListener {
  DialogFindAndReplace adaptee;

  DialogFindAndReplace_jButtonClose_actionAdapter(DialogFindAndReplace adaptee) {
    this.adaptee = adaptee;
  }
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonClose_actionPerformed(e);
  }
}