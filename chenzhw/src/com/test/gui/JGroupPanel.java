package com.test.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

/**
 * <p>
 * Title:OpenSwing
 * </p>
 * <p>
 * Description: JGroupPanel 组群面板<BR>
 * 类似QQ界面的组群管理面板
 * </p>
 * @author Brook Tran. Email: <a href="mailto:brook.tran.c@gmail.com">brook.tran.c@gmail.com<\a>
 * @version Ver 1.0 2008-11-31 修改
 * @since eclipse Ver 1.01
 * 
 */
public class JGroupPanel extends JPanel {
	
	/** 用来管理组的三个容器 */
	private JPanel panelNorth = new JPanel() {
	};

	private JPanel panelCenter = new JPanel();

	private JPanel panelSouth = new JPanel();

	/** 当前全部组的集合 */
	private List<Component> groupList = new ArrayList<Component>();

	/** 是否已禁止添加组件 */
	private boolean addFlag = false;

	/** 当前激活的组 */
	private JGroupContainer activeGroupContainer = null;
	
	/**插入新组之前是否清空所有组(如默认组)*/
	private boolean isClearAll = false;

	//transient   修饰该对象是非持久性对象，序列化的时候不需要保存它。
	transient ActionListener actionListener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			JButton btnTitle = (JButton) e.getSource();
			expandGroup((JGroupContainer) btnTitle.getParent());
		}
	};

	public JGroupPanel() {
		initComponents();
		//createDefaultGroup();
	}

	/**
     * @param groupNames String[] 预设组名
     */
    public JGroupPanel(String groupNames[]) {
        initComponents();
        addGroup(groupNames);
    }
    
    public JGroupPanel(String groupName){
    	initComponents();
    	addGroup(groupName);
    }
    
	private void initComponents() {
		// Constructs a new border layout with no gaps between components.
		this.setLayout(new BorderLayout());

		this.add(panelNorth, BorderLayout.NORTH);
		this.add(panelCenter, BorderLayout.CENTER);
		this.add(panelSouth, BorderLayout.SOUTH);

		panelNorth.setLayout(new GroupLayout());
		panelCenter.setLayout(new BorderLayout());
		panelSouth.setLayout(new GroupLayout());

		addFlag = true;// ****
	}

	private void createDefaultGroup() {
		// Default Group
		Color bg[] = { Color.black, Color.red, Color.orange, Color.yellow,
				Color.green, Color.cyan, Color.blue, Color.white };
		for (int i = 1; i <= bg.length; i++) {
			insertGroup(i - 1, "Group " + i, bg[i - 1]);
			Color mc = new Color(255 - bg[i - 1].getRed(),
					255 - bg[i - 1].getGreen(),
					255 - bg[i - 1].getBlue());
			for (int j = 1; j <= 5; j++) {
				JButton bttcomponent = new JButton("component " + j + " of " + i);
				addGroupComponent(i - 1, bttcomponent);
				bttcomponent.setPreferredSize(new Dimension(1, 40));
				
				//如果为 true，则该组件绘制其边界内的所有像素。
				//否则该组件可能不绘制部分或所有像素，从而允许其底层像素透视出来。 
				bttcomponent.setOpaque(false);
				bttcomponent.setForeground(mc);
			}
			getGroup(i - 1).setComponentGap(20, 10);
            getGroup(i - 1).getTitleBtn().setForeground(bg[i - 1]);
		}
		expandGroup(0);
        isClearAll = true;
	}
	
    /**
     * 作用:按名称展开组
     * 先获取组的序号index，再调用expandGroup(int index)
     * @param groupName
     */
    public void expandGroup(String groupName) {
        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (getGroupName(i).equals(groupName)) {
                expandGroup(i);
            }
        }
    }
    
    /**
     * 展开组
     * @param index int 组的顺序号
     */
    public void expandGroup(int index) {
        expandGroup(getGroup(index));
    }
    
    /**
     * 展开组.
     * 把JGroupContainer上面的组都放在panelNorth面板里面
     * 把JGroupContainer下面的组都放在panelSouth面板里面
     * @param group JGroupContainer 要展开的组
     */
    protected void expandGroup(JGroupContainer group) {
    	
    	panelNorth.removeAll();
        panelCenter.removeAll();
        panelSouth.removeAll();
        /* removeAll()
    	 * Removes all the components from this container. 
    	 * This method also notifies the layout manager to 
    	 * remove the components from this container's layout 
    	 * via the removeLayoutComponent method. 
    	 */
        boolean hasAddCenter = false;//是否找到已经添加的组
        for (int i = 0,j=groupList.size(); i <j ; i++) {
            Component component = (Component) groupList.get(i);
            if (hasAddCenter) {
                panelSouth.add(component);
            }
            else if (component == group) {
                panelCenter.add(component, BorderLayout.CENTER);
                hasAddCenter = true;
            }
            else {
                panelNorth.add(component);
            }
        }
        if (activeGroupContainer != null) {
        	activeGroupContainer.collapse();
        }
        activeGroupContainer = group;
        activeGroupContainer.expand();
        panelNorth.doLayout();
        panelCenter.doLayout();
        panelSouth.doLayout();
        super.doLayout();
    }
    
    /**
     * 作用:展开第一个组
     */
    public void expandGroup() {
    	expandGroup(0);
	}
    
    /**
     * 按组名收缩组
     * @param groupName String 组名
     */
    public void collapseGroup(String groupName) {
        for (int i = getGroupCount() - 1; i >= 0; i--) {
            if (getGroupName(i).equals(groupName)) {
                collapseGroup(i);
            }
        }
    }
    
    /**
     * 按组序号收缩组
     * @param groupIndex int 组的顺序号
     */
    public void collapseGroup(int groupIndex) {
        collapseGroup(getGroup(groupIndex));
    }
    
    /**
     * 收缩组
     * @param group JGroupContainer 组
     */
    protected void collapseGroup(JGroupContainer group) {
        if (group == activeGroupContainer) { 
        	activeGroupContainer.collapse();
        	activeGroupContainer = null;
        }
    }
    
    /**
     * 按组名添加组
     * @param groupName String 组名
     */
    public void addGroup(String groupName) {
        this.insertGroup(getGroupCount(), groupName);
    }
    /**
     * 作用:按组名和背景添加组
     * @param groupName	组名
     * @param color	该组的背景颜色
     */
    public void addGroup(String groupName,Color color){
    	this.insertGroup(getGroupCount(), groupName,color);
    }
     
    /**
     * 按组名添加多个组
     * @param groupNames String[] 组名
     */
    public void addGroup(String[] groupNames) {
        for (int i = 0,j=groupNames.length; i <j ; i++) {
            addGroup(groupNames[i]);
        }
    }
    
    /**
     * 插入一个组
     * @param groupIndex int 顺序号
     * @param groupName String 组名
     * @param bg Color 背景色
     */
    public void insertGroup(int groupIndex, String groupName, Color bg) {
        if (groupIndex < 0 || groupIndex > groupList.size()) {
            throw new ArrayIndexOutOfBoundsException("index:" + groupIndex +
                " >count:" + groupList.size());
        }
        if(isClearAll){//清除所有组
            while(getGroupCount()>0){
                removeGroup(0);
            }
            isClearAll = false;
        }
        // 在相应位置插入组的组件 
        int countNorth = panelNorth.getComponentCount();
        int countCenter = panelCenter.getComponentCount();
        int countSouth = panelSouth.getComponentCount();
        JGroupContainer group;
        if (groupIndex <= countNorth) {
            group = insertGroup(panelNorth, groupIndex, groupName, bg);
        }
        else if (groupIndex <= countNorth + countCenter) {
            group = insertGroup(panelCenter, groupIndex - countNorth, groupName, bg);
        }
        else if (groupIndex <= countNorth + countCenter + countSouth) {
            group = insertGroup(panelSouth, groupIndex - countNorth - countCenter, groupName,
                                bg);
        }
        else {
            group = insertGroup(panelSouth, countSouth, groupName, bg);
        }
        group.getTitleBtn().addActionListener(actionListener);
        groupList.add(groupIndex, group);

    }

    /**
     * 按序号和组名插入一个组
     * @param groupIndex int 顺序号
     * @param groupName String 组名
     */
    public void insertGroup(int groupIndex, String groupName) {
        insertGroup(groupIndex, groupName, UIManager.getColor("Desktop.background"));
    }
    
    /**
     * 在相应面板插入一个组
     * @param p JPanel 目标面板
     * @param groupIndex int 顺序号
     * @param groupName String 组名
     * @return JGroupContainer
     */
    private JGroupContainer insertGroup(JPanel p, int groupIndex, String groupName,
                                        Color bg) {
        JGroupContainer group = new JGroupContainer(groupName, bg);
        p.add(group);
        return group;
    }
    
    /**
     * 按序号删除一个组
     * @param groupIndex int 顺序号
     */
    public void removeGroup(int groupIndex) {
    	JGroupContainer groupContainer=(JGroupContainer)groupList.get(groupIndex);
    	groupContainer.getParent().remove(groupContainer);//从面板删除组
    	groupContainer.getTitleBtn().removeActionListener(actionListener);
    	/*Removes an ActionListener from the button. 
    	If the listener is the currently set Action for the button, 
    	then the Action is set to null. */
    }
    
    /**
     * 按组名删除一个组
     * @param groupName String 组名
     */
    public void removeGroup(String groupName) {
        for (int i = getGroupCount() - 1; i >= 0; i--) {//从后面开始寻找
            if (getGroupName(i).equals(groupName)) {
                this.removeGroup(i);
            }
        }
    }
    
    
    /**
     * 设置组名
     * @param groupIndex int 顺序号
     * @param groupName String 组名
     */
    public void setGroupName(int groupIndex, String groupName) {
        this.getGroup(groupIndex).setName(groupName);
    }
    
    
    /**
     * 取得组名
     * @param groupIndex int 顺序号
     * @return String 组名
     */
    public String getGroupName(int groupIndex) {
        return getGroup(groupIndex).getName();
    }

    
    /**
     * 取得全部组名
     * @return String[]
     */
    public String[] getGroupNames() {
        String groupNames[] = new String[getGroupCount()];
        for (int i = 0; i < getGroupCount(); i++) {
        	groupNames[i] = getGroupName(i);
        }
        return groupNames;
    }
    
    /**
     * 取得当前组的总数
     * @return int
     */
    public int getGroupCount() {
        return groupList.size();
    }

    /**
     * 往组中添加成员组件
     * @param groupIndex int 组的顺序号
     * @param component Component 成员组件
     */
    public void addGroupComponent(int groupIndex, Component component) {
        getGroup(groupIndex).addComponent(component,getGroup(groupIndex).getcomponentCount());
    }
    /**
     * 往组中插入成员组件
     * @param groupIndex int 组的顺序号
     * @param componentIndex int 插入的顺序号
     * @param component Component 成员组件
     */
    public void insertcomponent(int groupIndex, int componentIndex, Component component) {
        getGroup(groupIndex).addComponent(component,componentIndex);
    }

    /**
     * 从组中移除成员组件
     * @param groupIndex int
     * @param componentIndex int
     */
    public void removecomponent(int groupIndex, int componentIndex) {
        getGroup(groupIndex).removecomponent(componentIndex);
    }

    /**
     * 取得成员组件
     * @param groupIndex int 组的顺序号
     * @param componentIndex int 成员组件的顺序号
     * @return Component 成员组件
     */
    public Component getcomponent(int groupIndex, int componentIndex) {
        return getGroup(groupIndex).getcomponent(componentIndex);
    }

    /**
     * 取得全部成员组件
     * @param groupIndex int 组的顺序号
     * @return Component[] 全部成员组件
     */
    public Component[] getcomponents(int groupIndex) {
        return getGroup(groupIndex).getcomponents();
    }

    /**
     * 取得成员组件的总数
     * @param groupIndex int 组的顺序号
     * @return int 总数
     */
    public int getcomponentCount(int groupIndex) {
        return getGroup(groupIndex).getcomponentCount();
    }

    /**
     * 取得组
     * @param index int 组的顺序号
     * @return JGroupContainer 组
     */
    protected JGroupContainer getGroup(int index) {
        return (JGroupContainer) groupList.get(index);
    }

    /**
     * 覆写的addImpl方法,禁止再向JGroupPane中添加组件
     * @param component Component
     * @param constraints Object
     * @param index int
     */
    @Override
    protected void addImpl(Component component, Object constraints, int index) {
        if (addFlag) {
            if (! (component instanceof JGroupContainer)) {
                throw new UnsupportedOperationException(
                    "JGroupPane can't add component!");
            }
        }
        else {
            super.addImpl(component, constraints, index);
            // Adds the specified component to this container at the specified
            // index.
        }
    }

    /**
     * 作用:下次插入新组之前清空所有组(如默认组)
     */
    public void setClearFlag() {
		this.isClearAll=true;
	}
    /**
     * <p>Title: OpenSwing</p>
     * <p>Description: 组面板布局管理器</p>
     */
    class GroupLayout implements LayoutManager, java.io.Serializable {
        int vGap = 0;
        int hGap = 0;
        public GroupLayout() {
        }

        public GroupLayout(int hg, int vg) {
            this.hGap = hg;
            this.vGap = vg;
        }

        public void addLayoutComponent(String name, Component Component) {
        }

        public void removeLayoutComponent(Component Component) {
        }

        public Dimension preferredLayoutSize(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int ncomponents = parent.getComponentCount();
                int w = 0;
                int h = 0;
                for (int i = 0; i < ncomponents; i++) {
                    Component component = parent.getComponent(i);
                    Dimension d = component.getPreferredSize();
                    if (w < d.width) {
                        w = d.width;
                    }
                    h += d.height + vGap;
                }
                return new Dimension(insets.left + insets.right + w + 2 * hGap,
                                     insets.top + insets.bottom + h + 2 * vGap);
            }
        }

        public Dimension minimumLayoutSize(Container parent) {
            return preferredLayoutSize(parent);
        }

        public void layoutContainer(Container parent) {
            synchronized (parent.getTreeLock()) {
                Insets insets = parent.getInsets();
                int ncomponents = parent.getComponentCount();
                if (ncomponents == 0) {
                    return;
                }
                int y = insets.top + vGap;
                for (int c = 0; c < ncomponents; c++) {
                    int h = parent.getComponent(c).getPreferredSize().height;
                    parent.getComponent(c).setBounds(
                        insets.left + hGap,
                        y,
                        parent.getWidth() - insets.left - insets.right -
                        2 * hGap, h);
                    y += h + vGap;
                }
            }
        }

        @Override
        public String toString() {
            return getClass().getName();
        }
    }
	class JGroupContainer extends JPanel {
		private JButton btnGroupTitle = new JButton();

		private JPanel pcomponents = new JPanel();

		private JScrollPane sp;

		/**
		 * 作用:
		 * 
		 * @since eclipse
		 */
		public JGroupContainer() {
			this("");
		}

		public JGroupContainer(String name) {
			this(name, UIManager.getColor("Desktop.background"));
		}

		/**
		 * @param name
		 *            String 组名
		 * @param background
		 *            Color 成员组件所在面板背景色
		 */
		public JGroupContainer(String name, Color backgroundColor) {
			btnGroupTitle.setText(name);
			btnGroupTitle.setFocusable(false);
			pcomponents.setBackground(backgroundColor);
			this.setLayout(new BorderLayout());
			this.add(btnGroupTitle, BorderLayout.NORTH);

			// 设置滚动条
			pcomponents.setBackground(backgroundColor);

			Color thumbColor = UIManager.getColor("ScrollBar.thumb");
			Color trackColor = UIManager.getColor("ScrollBar.track");
			Color trackHighlightColor = UIManager
					.getColor("ScorllBar.trackHighlight");

			UIManager.put("ScrollBar.thumb", backgroundColor);
			UIManager.put("ScorllBar.track", backgroundColor);
			UIManager.put("ScrollBar.trackHighlight", backgroundColor);

			sp = new JScrollPane(pcomponents);
			sp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

			this.add(sp, BorderLayout.CENTER);
			collapse();// 收缩组
			UIManager.put("ScrollBar.thumb", thumbColor);
			UIManager.put("ScrollBar.track", trackColor);
			UIManager.put("ScrollBar.trackHighlight", trackHighlightColor);
		}

		/**
		 * 设置间距
		 * 
		 * @param hGap
		 *            int 横间距
		 * @param vGap
		 *            int 竖间距
		 */
		public void setComponentGap(int hGap, int vGap) {
			pcomponents.setLayout(new GroupLayout(hGap, vGap));
		}

		/**
		 * 取得组件的标题
		 * 
		 * @return JButton
		 */
		public JButton getTitleBtn() {
			return btnGroupTitle;
		}

		/**
		 * 取得组的成员组件面板
		 * 
		 * @return JPanel
		 */
		public JPanel getcomponentsContainer() {
			return pcomponents;
		}

		/**
		 * 收缩组
		 */
		public void collapse() {
			sp.setVisible(false);
			this.revalidate();
			// 将组建标志为无效，并将组建层次结构中的第一个容器（一般是panel)在事件派发线程上执行validate().
		}

		/**
		 * 展开组
		 */
		public void expand() {
			sp.setVisible(true);
			this.revalidate();
		}

		/**
		 * 设置组名
		 * 
		 * @param name
		 *            String 组名
		 */
		@Override
		public void setName(String name) {
			btnGroupTitle.setText(name);
		}

		/**
		 * 取得组名
		 * 
		 * @return String
		 */
		public String getNmae() {
			return btnGroupTitle.getText();
		}

		/**
		 * 添加一个成员组件
		 * 
		 * @param index
		 *            int 顺序号
		 * @param component
		 *            Component 成员组件
		 */
		public void addComponent(Component component, int index) {
			pcomponents.add(component, index);
			pcomponents.doLayout();
			/*
			 * Causes this container to lay out its components. Most programs
			 * should not call this method directly, but should invoke the
			 * validate method instead.
			 */
		}

		/**
		 * 删除一个成员组件
		 * 
		 * @param index
		 *            int 顺序号
		 */
		public void removecomponent(int index) {
			pcomponents.remove(index);
			pcomponents.doLayout();
		}

		/**
		 * 取得一个成员组件
		 * 
		 * @param index
		 *            int 顺序号
		 * @return Component 成员组件
		 */
		public Component getcomponent(int index) {
			return pcomponents.getComponent(index);
		}

		/**
		 * 取得全部成员组件
		 * 
		 * @return Component[] 成员组件
		 */
		public Component[] getcomponents() {
			Component[] components = new Component[getcomponentCount()];
			for (int i = 0, j = components.length; i < j; i++) {
				components[i] = pcomponents.getComponent(i);
			}
			return components;
		}

		/**
		 * 取得成员组件总数
		 * 
		 * @return int 总数
		 */
		public int getcomponentCount() {
			return pcomponents.getComponentCount();
		}

		/**
		 * 重写的toString方法
		 * 
		 * @return String
		 */
		/*
		 * （非 Javadoc）
		 * 
		 * @see java.awt.Component#toString()
		 */
		@Override
		public String toString() {
			return getName();
		}
	}

	/**
	 * 测试程序
	 * 
	 * @param args
	 *            String[]
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("JGroupPanel Demo");
		frame.getContentPane().setLayout(new BorderLayout());
		JGroupPanel groupPanel=new JGroupPanel();
		groupPanel.createDefaultGroup();
		frame.getContentPane().add(groupPanel, BorderLayout.CENTER);
		frame.setSize(150, 600);
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(d.width - frame.getSize().width - 10, 10);
		frame.setVisible(true);
		
		
//		 Default Group
		/*Color bg[] = { Color.black, Color.red, Color.orange, Color.yellow,
				Color.green, Color.cyan, Color.blue, Color.white };
		for (int i = 1; i <= bg.length; i++) {
			insertGroup(i - 1, "Group " + i, bg[i - 1]);
			Color mc = new Color(255 - bg[i - 1].getRed(),
					255 - bg[i - 1].getGreen(),
					255 - bg[i - 1].getBlue());
			for (int j = 1; j <= 5; j++) {
				JButton bttcomponent = new JButton("component " + j + " of " + i);
				addComponent(i - 1, bttcomponent);
				bttcomponent.setPreferredSize(new Dimension(1, 40));
				
				//如果为 true，则该组件绘制其边界内的所有像素。
				//否则该组件可能不绘制部分或所有像素，从而允许其底层像素透视出来。 
				bttcomponent.setOpaque(false);
				bttcomponent.setForeground(mc);
			}
			getGroup(i - 1).setComponentGap(20, 10);
            getGroup(i - 1).getTitleBtn().setForeground(bg[i - 1]);
		}
		expandGroup(0);
        isClearAll = true;*/
		
		
		
	}
}













