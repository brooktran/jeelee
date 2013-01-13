package com.test.javacoreadvance.thread;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 线程练习:shows an animated(活泼) bouncing all
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
public class Bounce {

	public static void main(String[] args) {
		JFrame frame = new BounceFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}

/**
 * A runnable that animates a bouncing ball.
 */
class BallRunnable implements Runnable {
	/**
	 * Constructs the runnable.
	 * 
	 * @aBall the ball to bounce
	 * @aPanel the component in which the ball bounces
	 */
	public BallRunnable(Ball aBall, Component aComponent) {
		ball = aBall;
		component = aComponent;
	}

	public void run() {
		try {
			for (int i = 1; i <= STEPS; i++) {
				ball.move(component.getBounds());
				component.repaint();
				Thread.sleep(DELAY);
			}
		} catch (InterruptedException e) {
		}
	}

	private Ball ball;

	private Component component;

	public static final int STEPS = 1000;

	public static final int DELAY = 5;
}

/**
 * A ball that moves and bounces off the edges of a rectangle
 */

class Ball {
	private static final int YSIZE = 15;

	private static final int XSIZE = 15;

	private double x;

	private double y;

	private double dx = 1;

	private double dy = 1;

	public Ball() {
		Random rd = new Random();
		x = rd.nextDouble() * XSIZE * 100;
		y = rd.nextDouble() * YSIZE;

	}

	/**
	 * move the ball to the next position ,reversing direction if it hits one of
	 * the edges
	 */
	public void move(Rectangle2D bounds) {
		x += dx;
		y += dy;
		if (x < bounds.getMinX()) {
			x = bounds.getMinX();
			dx = -dx;
		}
		if (x + XSIZE >= bounds.getMaxX()) {
			x = bounds.getMaxX() - XSIZE;
			dx = -dx;
		}
		if (y < bounds.getMinY()) {
			y = bounds.getMinY();
			dy = -dy;
		}
		if (y + YSIZE >= bounds.getMaxY()) {
			y = bounds.getMaxY() - YSIZE;
			dy = -dy;
		}
	}

	public Ellipse2D getShape() {
		return new Ellipse2D.Double(x, y, XSIZE, YSIZE);
	}
}

/**
 * the frame with panel and buttons
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-6 新建
 * @since eclipse Ver 1.0
 * 
 */
class BounceFrame extends JFrame {
	private BallPanel panel;

	public static final int DEFAULT_WIDTH = 450;

	public static final int DEFAULT_HIGHT = 350;

	public static final int STEPS = 1000;

	public static final int DELAY = 3;

	/**
	 * constructs the frame with the panel for shwing the bouncing ball and
	 * start and close buttons
	 */
	public BounceFrame() {
		setSize(DEFAULT_WIDTH, DEFAULT_HIGHT);
		setTitle("Bounce");

		panel = new BallPanel();
		add(panel, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		addButton(buttonPanel, "Start", new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				addBall();
			}

		});
		addButton(buttonPanel, "Close", new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		add(buttonPanel, BorderLayout.SOUTH);
	}

	/**
	 * adds a button to a container.
	 * 
	 * @param c
	 * @param title
	 * @param listener
	 */
	public void addButton(Container c, String title, ActionListener listener) {
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	/**
	 * adds a bouncing ball to the panel and makes it bounce 1000 times.
	 */
	public void addBall() {
		Ball ball = new Ball();
		panel.add(ball);

		Runnable r = new BallRunnable(ball, panel);
		Thread t = new Thread(r);
		t.start();
		/*
		 * for(int i=1;i<=STEPS;i++){ ball.move(panel.getBounds());
		 * panel.paint(panel.getGraphics()); Thread.sleep(DELAY); }
		 */
	}
}

/**
 * the panel that draws the balls
 */
class BallPanel extends JPanel {
	private ArrayList<Ball> balls = new ArrayList<Ball>();

	/**
	 * add a ball to the panel
	 * 
	 * @param b
	 *            the ball to add
	 */
	public void add(Ball b) {
		balls.add(b);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Ball b : balls) {
			g2.fill(b.getShape());
		}
	}
}
