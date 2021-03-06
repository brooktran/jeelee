/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JReaderView.java
 *
 * Created on 2010-7-29, 9:56:45
 */

package org.jreader.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.jreader.app.JReader;
import org.jreader.app.ReaderItemChangedListener;
import org.jreader.app.actions.AddReaderAction;
import org.jreader.persistent.Category;
import org.jreader.persistent.ReaderDAO;
import org.jreader.persistent.ReaderItem;
import org.jreader.persistent.Subscriber;
import org.zhiwu.app.AbsView;
import org.zhiwu.app.Application;
import org.zhiwu.utils.AppResources;
import org.zhiwu.xml.DataSourceException;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 *
 * @author root
 */
public class JReaderView extends AbsView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* * Creates new form JReaderView */
	public JReaderView() {
		initComponents();
	}
	
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.zhiwu.app.AbsView#initApplication(org.zhiwu.app.IApplication)
	 */
	@Override
	public void initApplication(Application app) {
		super.initApplication(app);
		app.getModel().addUserObject(new JReader());
		
		initApplicationComponents();

		initBrowser();
	}

	/**
	 * 
	 */
	private void initBrowser() {
		URL url;
		try {
			url = new URL("http://blog.sina.com.cn/rss/1737124411.xml");
			
			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build(new XmlReader(url));

			
			List<SyndEntry> list = feed.getEntries();
			int ii=0;
			for (Iterator<SyndEntry> i = list.iterator(); i.hasNext() && ii<1;ii++) {
				SyndEntry entry = i.next();
				jEditorPane1.setContentType("text/html");
				jEditorPane1.setText(entry.getDescription().getValue());
				jEditorPane1.setPage("http://www.google.com");
//				System.out.println(entry.getDescription().getValue());
//				break;
			}
			
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/*
	 * *
	 */
	private void initApplicationComponents() {
		JToolBar readerBar = new JToolBar("subscribe");
		readerBar.setFloatable(true);
		readerBar.add(new AddReaderAction((JReader) app.getModel().getUserObject(JReader.class), app));

		jPanel1.add(readerBar, BorderLayout.NORTH);

		initTree();
	}

	/*
	 * *
	 */
	private void initTree() {
		JReader r = (JReader) app.getModel().getUserObject(JReader.class);
		r.addReaderChangedListener(new ReaderItemChangedListener() {
			@Override
			public void itemChanged(ReaderItemEvent e) {
				jTree1.setModel(getTreeModel());
			}
		});

		jTree1.setCellRenderer(new SubscriberTreeCellRenderer());

		jTree1.getCellEditor().addCellEditorListener(new CellEditorListener() {

			@Override
			public void editingStopped(ChangeEvent e) {
				DefaultCellEditor c = (DefaultCellEditor) e.getSource();
				System.out.println(c.getComponent());
			}

			@Override
			public void editingCanceled(ChangeEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	/*
	 * *
	 */
	private DefaultTreeModel getTreeModel() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("reader");
		DefaultTreeModel model = new DefaultTreeModel(root);

		DefaultMutableTreeNode categoryNode, subcriberNode;
		try {
			ReaderDAO readerDao = ReaderDAO.getInstance();
			List<Category> categorys = readerDao.getCategory();
			for (Category c : categorys) {
				categoryNode = new DefaultMutableTreeNode(c);
				List<Subscriber> subscribers = readerDao.getSubcribers(c);

				for (Subscriber sub : subscribers) {
					subcriberNode = new DefaultMutableTreeNode(sub);
					categoryNode.add(subcriberNode);
				}
				root.add(categoryNode);
			}
			return model;
		} catch (DataSourceException e) {
			e.printStackTrace();
			return null;
		}
	}

	/*
	 * * This method is called from within the constructor to initialize the
	 * form. WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	@SuppressWarnings("unchecked")
	// <editor-fold defaultstate="collapsed"
	// <editor-fold defaultstate="collapsed"
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jSplitPane2 = new javax.swing.JSplitPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();

        setPreferredSize(new java.awt.Dimension(980, 800));
        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jTree1.setModel(getTreeModel());
        jTree1.setEditable(true);
        jTree1.setRootVisible(false);
        jTree1.setShowsRootHandles(true);
        jScrollPane1.setViewportView(jTree1);

        jPanel1.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jSplitPane2.setDividerLocation(198);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "item1", "item2", "item3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setToolTipText("000000");
        jScrollPane2.setViewportView(jList1);

        jSplitPane2.setLeftComponent(jScrollPane2);

        jScrollPane3.setViewportView(jEditorPane1);

        jSplitPane2.setRightComponent(jScrollPane3);

        jSplitPane1.setRightComponent(jSplitPane2);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables

}

class SubscriberTreeCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ImageIcon categoryIcon;
	ImageIcon subscriberIcon;

	/**
	 * 
	 */
	public SubscriberTreeCellRenderer() {
		AppResources resources = AppResources
				.getResources("org.jreader.app.Labels");
		categoryIcon = new ImageIcon(resources.getString("category.icon"));
		subscriberIcon = new ImageIcon(resources.getString("subscriber.icon"));

//		setLeafIcon(subscriberIcon);
//		setOpenIcon(categoryIcon);
//		setClosedIcon(categoryIcon);
	
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.swing.tree.DefaultTreeCellRenderer#getTreeCellRendererComponent
	 * (javax.swing.JTree, java.lang.Object, boolean, boolean, boolean, int,
	 * boolean)
	 */
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel, boolean expanded, boolean leaf, int row,
			boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf,
				row, hasFocus);

		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		if (node.getUserObject() instanceof ReaderItem) {
			ReaderItem item = (ReaderItem) node.getUserObject();
			setText(item + "");

			if (item instanceof Subscriber) {
				setToolTipText(((Subscriber) item).getUri());
				setIcon(subscriberIcon);
			} else if (item instanceof Category) {
				setIcon(categoryIcon);
			}
		}

		return this;

	}
}

class ArticleListModel extends AbstractListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getElementAt(int)
	 */
	@Override
	public Object getElementAt(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.ListModel#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

}