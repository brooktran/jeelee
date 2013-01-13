package notepad;

import java.awt.Component;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

public class InternalFrame extends JInternalFrame
{
  private boolean chanageTextPane;       //false为不需要保存，true为需要保存
  private newInteface temp = null;

  public InternalFrame()
  {
    super();
  }
  public InternalFrame(newInteface temp,String title,boolean a,boolean b,boolean c,boolean d,boolean flag)
  {
    super(title,a,b,c,d);
    this.temp = temp;
    chanageTextPane = flag;
  }

  public void setChanageTextPane(boolean chanageTextPane)
  {
    this.chanageTextPane = chanageTextPane;
  }

  public void doDefaultCloseAction()
  {
    dispose();
  }

  public Component getFocusOwner()//失去焦点
  {
    super.getFocusOwner();
    return null;
  }

  public Component getMostRecentFocusOwner()   //获得焦点
  {
    System.out.println(temp.getNewIntefaceTitle()+"getMostRecentFocusOwner:"+temp.getChanageTextPane());
    if(chanageTextPane == true)
    {
      temp.notepad_Frame.jMenuFileSave.setEnabled(true);
      temp.notepad_Frame.jMenuFileSaveAs.setEnabled(true);
      temp.notepad_Frame.jButtonSave.setEnabled(true);
    }
    else
    {
      temp.notepad_Frame.jMenuFileSave.setEnabled(false);
      temp.notepad_Frame.jMenuFileSaveAs.setEnabled(false);
      temp.notepad_Frame.jButtonSave.setEnabled(false);

    }
    return null;
  }

  public void dispose()
  {
    if(chanageTextPane)
    {
     // JOptionPane dialog = new JOptionPane();
      int optionType = JOptionPane.showConfirmDialog(this,this.getTitle()+"文件是否保存","关闭文件",JOptionPane.YES_NO_CANCEL_OPTION);
      if(optionType == JOptionPane.YES_OPTION)
      {
         temp.notepad_Frame.saveOption();

      }
      else if(optionType == JOptionPane.CANCEL_OPTION)
      {
         System.out.println("取消");
         return;
      }
    }
    super.dispose();
    System.out.println("dispoy = "+this.getTitle());
    temp.notepad_Frame.removeClose(this.getTitle());
    if (temp.notepad_Frame.vectorFile.isEmpty())
    {
      temp.notepad_Frame.jMenuFileSave.setEnabled(false);
      temp.notepad_Frame.jMenuFileSaveAs.setEnabled(false);
      temp.notepad_Frame.jButtonSave.setEnabled(false);
      temp.notepad_Frame.jMenuFind.setEnabled(false);
      temp.notepad_Frame.jMenuFindReplace.setEnabled(false);
      temp.notepad_Frame.jButtonCopy.setEnabled(false);
      temp.notepad_Frame.jButtonPaste.setEnabled(false);
      temp.notepad_Frame.jButtonCut.setEnabled(false);
      temp.notepad_Frame.jMenuEditCut.setEnabled(false);
      temp.notepad_Frame.jMenuEditCopy.setEnabled(false);
      temp.notepad_Frame.jMenuEditPaste.setEnabled(false);
      temp.notepad_Frame.jMenuEditSelectAll.setEnabled(false);
      temp.notepad_Frame.statusBarXY.setText("");
    }
  }
}