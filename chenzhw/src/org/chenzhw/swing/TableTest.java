package org.chenzhw.swing;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

/**
 * <B>TableTest</B>
 * 
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com</a>
 * @version Ver 1.0.01 2009-11-5 created
 * @since chenzhw Ver 1.0
 * 
 */
public class TableTest {

	// This customized renderer can render objects of the type TextandIcon
	TableCellRenderer iconHeaderRenderer = new DefaultTableCellRenderer() {
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value,
				boolean isSelected, boolean hasFocus, int row, int column) {
			// Inherit the colors and font from the header component
			if (table != null) {
				JTableHeader header = table.getTableHeader();
				if (header != null) {
					setForeground(header.getForeground());
					setBackground(header.getBackground());
					setFont(header.getFont());
				}
			}

			if (value instanceof TextAndIcon) {
				setIcon(((TextAndIcon)value).icon);
				setText(((TextAndIcon)value).text);
			} else {
				setText((value == null) ? "" : value.toString());
				setIcon(null);
			}
			setBorder(UIManager.getBorder("TableHeader.cellBorder"));
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	};

	// This class is used to hold the text and icon values
	// used by the renderer that renders both text and icons
	class TextAndIcon {
		TextAndIcon(String text, Icon icon) {
			this.text = text;
			this.icon = icon;
		}
		String text;
		Icon icon;
	}



	JTable table ;
	/**
	 * 
	 */
	public TableTest() {
		DefaultTableModel model=new DefaultTableModel();
		table = new JTable(model);

		// Create 2 columns
		model.addColumn("Col1");
		model.addColumn("Col2");


		// the header value for this column will be overwritten
		// with a TextandIcon object

		// Set the icon renderer on the second column
		table.getTableHeader().getColumnModel()
		.getColumn(1).setHeaderRenderer(iconHeaderRenderer);

		// Set the text and icon values on the second column for the icon render
		table.getColumnModel().getColumn(1).setHeaderValue(
				new TextAndIcon("Col2", new ImageIcon(getClass().getResource("pen.png"))));
	}




	public static void main(String[] args) {
		JFrame frame = new JFrame("Label Header");   
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		TableTest table=new TableTest();

		JScrollPane scrollPane = new JScrollPane(table.table);   


		frame.add(scrollPane, BorderLayout.CENTER);   
		frame.setSize(300, 150);   
		frame.setVisible(true);   

	}

}
