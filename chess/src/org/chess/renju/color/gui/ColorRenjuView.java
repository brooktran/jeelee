/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ColorRenjuView.java
 *
 * Created on 2011-10-16, 18:12:12
 */
package org.chess.renju.color.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.chess.app.ChessViewAdapter;
import org.chess.app.actions.NewGameAction;
import org.chess.game.GameMode;
import org.chess.game.GameModeFactory;
import org.chess.renju.color.ColorRenjuProxy;
import org.chess.renju.color.actions.NewChallengeGameAction;
import org.chess.renju.color.actions.NewRandomGameAction;
import org.chess.renju.color.actions.NewSuvivalGameAction;
import org.chess.renju.color.actions.NextPiecesAction;
import org.zhiwu.utils.ArrayUtils;
 
/**
 * <B>ColorRenjuView</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2011-10-17 created
 * @since Renju Ver 1.0
 * 
 */
public class ColorRenjuView extends ChessViewAdapter {
	private final  ColorRenjuProxy renju;
	private final PropertyChangeListener renjuListener=new PropertyChangeListener() {
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			String name = evt.getPropertyName();
			if(name.equals("score")){
				scoreLabel.setText(evt.getNewValue().toString());
				return;
			}
			if(name.equals("mode")){
				modelNameLabel.setText(evt.getNewValue().toString());
				return;
			}
			if(name.equals("state")){
				String state = evt.getNewValue().toString();
				if(state.equals("over") || state.equals("pass")){
					nextPiecesButton.setEnabled(false);
					
					highScoreLabel.setText(ArrayUtils.arrayToCommaString(renju.getMode().getHighScore()));
//					JOptionPane.showMessageDialog(ColorRenjuView.this,
//							"^-^ , hey, the game is over (^oo^)", "Renju", 
//							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			if(name.equals("board")){
				GameMode mode = renju.getMode();
				int value =(int)((double)renju.getEmptyCount()*100/(mode.getColumn()*mode.getRow())) ;
				jProgressBar1.setValue(value);
			}
			
		}
	};
 
    public ColorRenjuView() {
    	renju = new ColorRenjuProxy();
    	renju.begin();
    	renju.addPropertyChangeListener(renjuListener);
    }
    
    @Override
    protected void initActions() {
//    	putAction(NewGameAction.ID, new NewGameAction(app));
//    	putAction(SaveGameAction.ID, new SaveGameAction(app));
    	putAction(NextPiecesAction.ID, new NextPiecesAction(app,renju));
    	putAction(NewChallengeGameAction.ID, new NewChallengeGameAction(app));
    	putAction(NewSuvivalGameAction.ID, new NewSuvivalGameAction(app));
    	putAction(NewRandomGameAction.ID, new NewRandomGameAction(app));
    	
    }
    
    @Override
    public void init() {
    	super.init();
    	initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jButton4 = new javax.swing.JButton();
        jSplitPane2 = new javax.swing.JSplitPane();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        scoreLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        newGameButton = new javax.swing.JButton();
        nextPiecesButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        modelNameLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel5 = new javax.swing.JLabel();
        highScoreLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 204, 153));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(255, 204, 153));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jSplitPane1.setDividerSize(1);
        jSplitPane1.setResizeWeight(0.3);

        jButton4.setAction(getAction(NewChallengeGameAction.ID));
        jButton4.setFont(new java.awt.Font("华文彩云", 1, 48)); // NOI18N
        jButton4.setForeground(new java.awt.Color(0, 0, 153));
        jButton4.setText("挑战模式");
        jSplitPane1.setLeftComponent(jButton4);

        jSplitPane2.setDividerSize(1);
        jSplitPane2.setResizeWeight(0.5);

        jButton1.setAction(getAction(NewRandomGameAction.ID));
        jButton1.setFont(new java.awt.Font("黑体", 3, 48));
        jButton1.setText("随机模式");
        jSplitPane2.setRightComponent(jButton1);

        jButton5.setAction(getAction(NewSuvivalGameAction.ID));
        jButton5.setFont(new java.awt.Font("华文行楷", 3, 48));
        jButton5.setForeground(new java.awt.Color(102, 0, 51));
        jButton5.setText("生存模式");
        jSplitPane2.setLeftComponent(jButton5);

        jSplitPane1.setRightComponent(jSplitPane2);

        jPanel1.add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jScrollPane1.setViewportView(jPanel1);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(246, 203, 118));
        jPanel2.setPreferredSize(new java.awt.Dimension(495, 50));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 15));

        jPanel3.setMinimumSize(new java.awt.Dimension(110, 30));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(212, 133, 74));
        jLabel1.setFont(new java.awt.Font("华文琥珀", 1, 24));
        jLabel1.setText("分数:");
        jLabel1.setOpaque(true);
        jPanel3.add(jLabel1, java.awt.BorderLayout.WEST);

        scoreLabel.setBackground(new java.awt.Color(0, 127, 74));
        scoreLabel.setFont(new java.awt.Font("华文琥珀", 0, 24));
        scoreLabel.setForeground(new java.awt.Color(255, 255, 255));
        scoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        scoreLabel.setMinimumSize(new java.awt.Dimension(70, 15));
        scoreLabel.setOpaque(true);
        scoreLabel.setPreferredSize(new java.awt.Dimension(80, 15));
        jPanel3.add(scoreLabel, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jPanel4);

        newGameButton.setAction(app.getModel().getAction(NewGameAction.ID));
        newGameButton.setBackground(new java.awt.Color(255, 204, 153));
        newGameButton.setText("新游戏");
        newGameButton.setEnabled(false);
        jPanel2.add(newGameButton);

        nextPiecesButton.setAction(getAction(NextPiecesAction.ID));
        nextPiecesButton.setText("下一组");
        nextPiecesButton.setEnabled(false);
        jPanel2.add(nextPiecesButton);

        jPanel5.setBackground(new java.awt.Color(246, 203, 118));
        jPanel5.setMinimumSize(new java.awt.Dimension(110, 30));
        jPanel5.setLayout(new java.awt.BorderLayout());

        modelNameLabel.setFont(new java.awt.Font("华文行楷", 2, 36));
        modelNameLabel.setForeground(new java.awt.Color(0, 0, 255));
        modelNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        modelNameLabel.setText("彩球连珠");
        modelNameLabel.setMaximumSize(new java.awt.Dimension(996, 26));
        modelNameLabel.setMinimumSize(new java.awt.Dimension(100, 15));
        modelNameLabel.setPreferredSize(new java.awt.Dimension(150, 28));
        jPanel5.add(modelNameLabel, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel5);

        descriptionLabel.setText("_");
        jPanel2.add(descriptionLabel);

        add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jToolBar1.setBackground(new java.awt.Color(249, 216, 134));
        jToolBar1.setRollover(true);

        jProgressBar1.setBackground(new java.awt.Color(255, 0, 0));
        jProgressBar1.setMaximumSize(new java.awt.Dimension(100, 14));
        jProgressBar1.setPreferredSize(new java.awt.Dimension(100, 14));
        jToolBar1.add(jProgressBar1);

        jLabel5.setText("  最高分:");
        jToolBar1.add(jLabel5);

        highScoreLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jToolBar1.add(highScoreLabel);

        add(jToolBar1, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel highScoreLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel modelNameLabel;
    private javax.swing.JButton newGameButton;
    private javax.swing.JButton nextPiecesButton;
    private javax.swing.JLabel scoreLabel;
    // End of variables declaration//GEN-END:variables

	@Override
	public void newGame() {
		renju.reset();
		jScrollPane1.setViewportView(new ColorBoardView(renju));
		newGameButton.setEnabled(true);
		nextPiecesButton.setEnabled(true);
		scoreLabel.setText(renju.getScore()+"");
		highScoreLabel.setText(ArrayUtils.arrayToCommaString(renju.getMode().getHighScore()));
		descriptionLabel.setText(renju.getMode().getDescription());
		
		validate();
		repaint();
	}


	public void newGame(String mode) {
		renju.setMode(GameModeFactory.getGameMode(mode));
		newGame();
	}
	
}