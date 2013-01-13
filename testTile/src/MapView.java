import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.Scrollable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * <B>MapView</B>
 * 
 * @author Brook Tran. Email: <a
 *         href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2009-9-21 created
 * @since testTile Ver 1.0
 * 
 */
public class MapView extends JPanel implements Scrollable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 * @since testTile
	 */
	public MapView() {
		setAutoscrolls(true);
		setPreferredSize(new Dimension(1000,1000));
		
	}

	/**
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		paintGrid(g);
		paintString(g);
	}

	/**
	 * 
	 * @param g
	 */
	private void paintString(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawString("hahahahahah", 10, 100);
		
	}

	/**
	 * 
	 * @param g
	 */
	private void paintGrid(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		Rectangle clipRectangle = g2d.getClipBounds();
		Dimension tileSize = new Dimension(48, 24);

		clipRectangle.x -= tileSize.width / 2;
		clipRectangle.width += tileSize.width;
		clipRectangle.height += tileSize.height;
		
		g2d.setColor(Color.BLACK);
		g2d.drawLine(-1000, -1000, 1000, 1000);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ssss");
		
		JScrollPane pane=new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
				,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		JPanel panel=new JPanel();
		pane.setOpaque(true);
		panel.setOpaque(true);
		
		//panel.add(new JButton("ddddd"));
		panel.add(new MapView());
		//panel.add(new JLabel("sdf"));
		panel.repaint();
		
		
		pane.setBorder(new LineBorder(Color.RED, 2));
		pane.add(panel);
		
		panel.setBorder(new LineBorder(Color.GREEN,1));
		panel.setSize(500, 500);
		panel.setLocation(0, 0);
		
		pane.setSize(500,500);
		panel.setBackground(Color.RED);
		
		frame.setContentPane(pane );
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 800);
		frame.setVisible(true);
	}

	/**
	 * @see javax.swing.Scrollable#getPreferredScrollableViewportSize()
	 */
	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see javax.swing.Scrollable#getScrollableBlockIncrement(java.awt.Rectangle,
	 *      int, int)
	 */
	public int getScrollableBlockIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		 Dimension tsize = new Dimension(48,24);
	        if (orientation == SwingConstants.VERTICAL) {
	            return (visibleRect.height / tsize.height) * tsize.height;
	        } else {
	            return (visibleRect.width / tsize.width) * tsize.width;
	        }
	}

	/**
	 * @see javax.swing.Scrollable#getScrollableTracksViewportHeight()
	 */
	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see javax.swing.Scrollable#getScrollableTracksViewportWidth()
	 */
	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * @see javax.swing.Scrollable#getScrollableUnitIncrement(java.awt.Rectangle,
	 *      int, int)
	 */
	public int getScrollableUnitIncrement(Rectangle visibleRect,
			int orientation, int direction) {
		 Dimension tsize =  new Dimension(48,24);
	        if (orientation == SwingConstants.VERTICAL) {
	            return tsize.height;
	        } else {
	            return tsize.width;
	        }
	}

}
