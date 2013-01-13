package com.test.gui;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;

/**
 * 类<B> JTreeTest </B>是
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0 2009-3-11 新建
 * @since chenzhw Ver 1.0
 * 
 */
public class JTreeTest {
	public static void main(String args[]) {
		JFrame frame = new JFrame("JComponent Hierarchy");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font = (Font) UIManager.get("Tree.font");
		font = new Font(font.getFontName(), Font.BOLD, font.getSize() - 3);
		UIManager.put("Tree.font", font);

		Vector jEditorPaneVector = new NamedVector("JEditorPane",
				new Object[] { "JTextPane" });

		Vector jTextFieldVector = new NamedVector("JTextField",
				new Object[] { "JPasswordField" });

		Vector jTextComponentVector = new NamedVector(
				"JTextComponent",
				new Object[] { jEditorPaneVector, "JTextArea", jTextFieldVector });

		Vector jLayeredPaneVector = new NamedVector("JLayeredPane",
				new Object[] { "JDesktopPane" });

		Vector jToggleButtonVector = new NamedVector("JToggleButton",
				new Object[] { "JCheckBox", "JRadioButton" });

		Vector jMenuItemVector = new NamedVector("JMenuItem", new Object[] {
				"JCheckBoxMenuItem", "JMenu", "JRadioButtonMenuItem" });

		Vector abstractButtonVector = new NamedVector(
				"Abstract Button",
				new Object[] { "JButton", jMenuItemVector, jToggleButtonVector });

		Object jComponentNodes[] = { abstractButtonVector, "JColorChooser",
				"JComboBox", "JFileChooser", "JInternalFrame", "JLabel",
				jLayeredPaneVector, "JList", "JMenuBar", "JOptionPane",
				"JPanel", "JPopupMenu", "JProgressBar", "JRootPane",
				"JScrollBar", "JScrollPane", "JSeparator", "JSlider",
				"JSplitPane", "JTabbedPane", "JTable", jTextComponentVector,
				"JToolBar", "JTree", "JViewPort" };
		Vector jComponentVector = new NamedVector("JComponent", jComponentNodes);

		Object rootNodes[] = { jComponentVector };
		Vector rootVector = new NamedVector("Root", rootNodes);

		JTree tree = new JTree(rootVector);
		tree.putClientProperty("JTree.lineStyle", "Angled");

		JScrollPane scrollPane = new JScrollPane(tree);
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.setSize(250, 480);
		frame.setVisible(true);
	}
}

class NamedVector extends Vector {
	String name;

	public NamedVector(String name) {
		this.name = name;
	}

	public NamedVector(String name, Object elements[]) {
		this.name = name;
		for (int i = 0, n = elements.length; i < n; i++) {
			add(elements[i]);
		}
	}

	public String toString() {
		return "[" + name + "]";
	}
}
