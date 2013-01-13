/* @(#)UndoDrawing.java 1.0 2010-1-4
 * 
 * Copyright (c) 2010 by Brook Tran
 * All rights reserved.
 * 
 * The copyright of this software is own by the authors.
 * You may not use, copy or modify this software, except
 * in accordance with the license agreement you entered into 
 * with the copyright holders. For details see accompanying license
 * terms.
 */
package org.chenzhw.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEditSupport;

/**
 * <B>UndoDrawing</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2010-1-4 created
 * @since chenzhw Ver 1.0
 * 
 */
public class UndoDrawing {

	public static void main(String[] args) {
		JFrame frame = new JFrame("drawing sample");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		UndoableDrawingPanel drawingPanel = new UndoableDrawingPanel();

		UndoManager manager = new UndoManager();
		drawingPanel.addUndoableEditListener(manager);

		JToolBar toolbar=new JToolBar();
		toolbar.add(UndoManagerHelper.getUndoAction(manager));
		toolbar.add(UndoManagerHelper.getRedoAction(manager));

		Container content = frame.getContentPane();
		content.add(toolbar, BorderLayout.NORTH);
		content.add(drawingPanel, BorderLayout.CENTER);
		frame.setSize(500, 500);
		frame.setVisible(true);

	}
}
class UndoableDrawingPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	UndoableEditSupport undoableEditSupport = new UndoableEditSupport(this);

	Polygon polygon =new Polygon();

	public UndoableDrawingPanel (){
		MouseListener mouseListener =new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				undoableEditSupport.postEdit(new UndoableDrawEdit(UndoableDrawingPanel.this));
				polygon.addPoint(e.getX(), e.getY());
				repaint();
			}
		};
		addMouseListener(mouseListener);
	}

	public void addUndoableEditListener(UndoableEditListener l){
		undoableEditSupport.addUndoableEditListener(l);
	}

	public void removeUndoableEditListener(UndoableEditListener l){
		undoableEditSupport.removeUndoableEditListener(l);
	}

	public void setPolygon(Polygon newValue){
		polygon=newValue;
		repaint();
	}
	public Polygon getPolygon() {
		Polygon returnValue;
		if(polygon.npoints==0){
			returnValue=new Polygon();
		}else{
			returnValue = new Polygon(polygon.xpoints,polygon.ypoints,polygon.npoints);
		}
		return returnValue;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawPolygon(polygon);
	}
}

class UndoManagerHelper{
	public static Action getUndoAction(UndoManager manager ){
		return new UndoAction(manager, "undo");
	}

	public static Action getRedoAction(UndoManager manager){
		return new RedoAction(manager, "redo");
	}

	private abstract static class UndoRedoAction extends AbstractAction {
		UndoManager manager = new UndoManager();
		String errorMessage = "Cannot undo";
		String errorTitle = "Undo Problem";

		protected UndoRedoAction(UndoManager manager,String name){
			super(name);
			this.manager =manager;
		}

		public void setErrorMessage(String  newValue){
			errorMessage= newValue;
		}
		public void setErrorTitle(String newValue) {
			errorTitle = newValue;
		}

		protected void showMessage(Object source) {
			if (source instanceof Component) {
				JOptionPane.showMessageDialog((Component) source, errorMessage,
						errorTitle, JOptionPane.WARNING_MESSAGE);
			} else {
				System.err.println(errorMessage);
			}
		}
	}

	public static class UndoAction extends UndoRedoAction{
		public UndoAction(UndoManager manager,String name){
			super(manager, name);
			setErrorMessage("Cannot undo");setErrorTitle("Undo Problem");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				manager.undo();
			}catch (CannotUndoException cannotUndoException) {
				showMessage( e.getSource());
			}
		}
	}

	public static class RedoAction extends UndoRedoAction{
		String errorMessage = "Cannot redo";
		String errorTitle = "Redo Problem";
		public RedoAction(UndoManager manager,String name){
			super(manager, name);setErrorMessage("Cannot redo");setErrorTitle("Redo Problem");
		}
		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				manager.redo();
			} catch (CannotRedoException cannotRedoException) {
				showMessage(e.getSource());
			}
		}
	}
}
class UndoableDrawEdit extends AbstractUndoableEdit{
	UndoableDrawingPanel panel;
	Polygon polygon,savedPolygon;

	public UndoableDrawEdit(UndoableDrawingPanel panel){
		this.panel=panel;
		polygon =panel.getPolygon();
	}

	@Override
	public String getPresentationName(){
		return "polygon of size " +polygon.npoints;
	}

	@Override
	public void redo() throws CannotRedoException{
		super.redo();
		if (savedPolygon == null) {
			throw new CannotUndoException();
		}else{
			panel.setPolygon(savedPolygon);
			savedPolygon=null;
		}
	}

	@Override
	public void undo() throws CannotUndoException{
		super.undo();
		savedPolygon=panel.getPolygon();
		panel.setPolygon(polygon);
	}

}
