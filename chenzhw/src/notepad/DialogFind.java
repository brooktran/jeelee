package notepad;

import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DialogFind extends JDialog {
  JPanel panel1 = new JPanel();
  JLabel jLabelContent = new JLabel();
  JTextField jTextFieldContent = new JTextField();
  JButton jButtonFindNext = new JButton();
  JButton jButtonClose = new JButton();
  Notepad_Frame1 notepad_Frame = null;
  int start = 0;
  int end = 0;

  public DialogFind(Frame frame, String title, boolean modal) {
    super(frame, title, modal);
    this.notepad_Frame =(Notepad_Frame1) frame;
    try {
      jbInit();
      this.setResizable(false);
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public DialogFind() {
    this(null, "", false);
  }
  
  private void jbInit() throws Exception {
    panel1.setLayout(null);
    jLabelContent.setFont(new java.awt.Font("Dialog", 0, 15));
    jLabelContent.setText("查找内容:");
    jLabelContent.setBounds(new Rectangle(22, 35, 70, 27));
    jTextFieldContent.setBounds(new Rectangle(105, 35, 151, 27));
    jButtonFindNext.setBounds(new Rectangle(275, 21, 104, 26));
    jButtonFindNext.setText("查找下一个");
    jButtonFindNext.addActionListener(new DialogFind_jButtonFindNext_actionAdapter(this));
    jButtonClose.setBounds(new Rectangle(275, 62, 104, 26));
    jButtonClose.setText("关闭");
    jButtonClose.addActionListener(new DialogFind_jButtonClose_actionAdapter(this));
    getContentPane().add(panel1);
    panel1.add(jTextFieldContent, null);
    panel1.add(jLabelContent, null);
    panel1.add(jButtonFindNext, null);
    panel1.add(jButtonClose, null);
  }

  void jButtonFindNext_actionPerformed(ActionEvent e) {
    if(jTextFieldContent.getText().equals("")) {
      JOptionPane.showMessageDialog(this,"查找内容不能为空");
    } else {
      System.out.println("jTextpane = "+notepad_Frame.getSelectFrame().jTextPane.getText().length());
      String findContent = jTextFieldContent.getText().trim();
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

  void jButtonClose_actionPerformed(ActionEvent e) {
    this.setVisible(false);
  }
}

class DialogFind_jButtonFindNext_actionAdapter implements ActionListener {
  DialogFind adaptee;

  DialogFind_jButtonFindNext_actionAdapter(DialogFind adaptee) {
    this.adaptee = adaptee;
  }
  
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonFindNext_actionPerformed(e);
  }
}

class DialogFind_jButtonClose_actionAdapter implements ActionListener {
  DialogFind adaptee;

  DialogFind_jButtonClose_actionAdapter(DialogFind adaptee) {
    this.adaptee = adaptee;
  }
  
  public void actionPerformed(ActionEvent e) {
    adaptee.jButtonClose_actionPerformed(e);
  }
}