package org.chenzhw.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
/**
 * A very simple applet demonstrating usage of javax.swing.undo package.
 */
public class undomgrtest extends JApplet {

	JButton linebutton;
	JButton circlebutton;
	JButton undobutton;
	JButton redobutton;
	JPanel buttonpanel;
	JGraphPanel graphpanel;
	LineListener linelistener;
	CircleListener circlelistener;
	UndoListener undolistener;
	RedoListener redolistener;
	LinkedList shapes;
	UndoManager undomgr;

	@Override
	public void init() {

		// Force SwingApplet to come up in the System L&F
		String laf = UIManager.getSystemLookAndFeelClassName();
		try {
			UIManager.setLookAndFeel(laf);
			// If you want the Cross Platform L&F instead, comment out the above line and
			// uncomment the following:
			// UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		} catch (UnsupportedLookAndFeelException exc) {
			System.err.println("Warning: UnsupportedLookAndFeel: " + laf);
		} catch (Exception exc) {
			System.err.println("Error loading " + laf + ": " + exc);
		}

		getContentPane().setLayout(new BorderLayout());
		linebutton = new JButton("Draw Line");
		circlebutton = new JButton("Draw Circle");
		undobutton = new JButton("Undo");
		redobutton = new JButton("Redo");
		graphpanel = new JGraphPanel(false);
		graphpanel.setPreferredSize(new Dimension(300,300));
		buttonpanel = new JPanel(false);
		buttonpanel.setLayout(new FlowLayout());
		buttonpanel.add(linebutton);
		buttonpanel.add(circlebutton);
		buttonpanel.add(undobutton);
		buttonpanel.add(redobutton);
		getContentPane().add(buttonpanel,BorderLayout.SOUTH);
		getContentPane().add(graphpanel,BorderLayout.NORTH);
		linelistener = new LineListener();
		linebutton.addActionListener(linelistener);
		circlelistener = new CircleListener();
		circlebutton.addActionListener(circlelistener);
		undolistener = new UndoListener();
		undobutton.addActionListener(undolistener);
		redolistener = new RedoListener();
		redobutton.addActionListener(redolistener);
		shapes = new LinkedList();
		undomgr = new UndoManager();
	}

	@Override
	public void stop() {

	}
	class JGraphPanel extends JPanel{
		public JGraphPanel(boolean doublebuffer)
		{
			super(doublebuffer);
		}
		@Override
		public void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.white);
			g2.fill3DRect(0,0,getWidth(),getHeight(),true);
			Shape shape;
			g2.setColor(Color.black);
			for(Iterator it = shapes.iterator();it.hasNext();){
				shape = (Shape)it.next();
				g2.draw(shape);
			}
		}
	}
	class LineListener implements ActionListener{
		Shape temp;
		public void actionPerformed(ActionEvent e){
			temp = new Line2D.Double(0.0,0.0,Math.random()*100.0,Math.random()*100.0);
			shapes.add(temp);
			repaint();
			UndoableEdit edit = new graphEdit(temp);
			undomgr.addEdit(edit);
		}
	}
	class CircleListener implements ActionListener{
		Shape temp;
		public void actionPerformed(ActionEvent e){
			temp = new Ellipse2D.Double(0.0,0.0,Math.random()*100.0,Math.random()*100.0);
			shapes.add(temp);
			repaint();
			UndoableEdit edit = new graphEdit(temp);
			undomgr.addEdit(edit);

		}
	}
	class UndoListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				undomgr.undo();
			}catch(CannotUndoException ex){
				System.err.println("Can't Undo More");
			}
		}
	}
	class RedoListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			try{
				undomgr.redo();
			}catch(CannotRedoException ex){
				System.err.println("Can't Redo More");
			}
		}
	}

	class graphEdit extends AbstractUndoableEdit{
		Shape shape;
		public graphEdit(Shape _shape){
			shape = _shape;
		}
		@Override
		public void undo(){
			shapes.remove(shape);
			repaint();
			System.out.println("undo draw line");
		}
		@Override
		public void redo(){
			shapes.add(shape);
			repaint();
			System.out.println("redo draw line");
		}
	}
}
