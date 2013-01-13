/* LiuYao.java 1.0 2010-2-2
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
package org.divine.app.liuyao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.divine.app.liuyao.resolve.DefaultResolver;
import org.divine.app.liuyao.resolve.Item;
import org.divine.app.liuyao.resolve.Level;
import org.divine.app.liuyao.resolve.Pillar;
import org.divine.app.liuyao.resolve.ResolverListener;
import org.divine.persistance.LiuyaoDataManager;
import org.jtools.app.persistent.DataException;

/**
 * <B>LiuYao</B>.
 * 
 * @author Brook Tran. Email: <a href="mailto:Brook.Tran.C@gmail.com">Brook.Tran.C@gmail.com</a>
 * @version Ver 1.0.01 2010-4-3 created
 * @since org.divine.app.liuyao Ver 1.0
 */
public class LiuYao {
	public static final int PillarYear = 0;
	public static final int PillarMonth = 1;
	public static final int PillarDay = 2;
	public static final int PillarHour = 3;



	private DefaultResolver resolver;
	


	/** 起卦四柱. */
	private Lunar lunar;

	/** 六爻八卦. */
	private EightDiagrams diagrams;

	/** 所测何事. */
	private String problem="";

	private final String id;

	private String parseText;

	/** 变卦. */
	private EightDiagrams changeDiagrams;
/** 四柱 */
	private final List<Pillar> pillars = new ArrayList<Pillar>(8);
	
	/**
	 * The Constructor.
	 * 
	 * @param lunar the lunar
	 * @param diagrams the diagrams
	 */
	public LiuYao(Lunar lunar, EightDiagrams diagrams) {
		this(lunar, diagrams,lunar.getCalendar().getTimeInMillis() + diagrams.hashCode() + "");
	}
	
	/**
	 * 
	 */
	public LiuYao(Lunar lunar, EightDiagrams diagrams,String id) {
		this.lunar = lunar;
		this.diagrams = diagrams;

		init();
		setup();
		this.id=id;
	}

	/**
	 * Inits the.
	 */
	private void init() {
//		pillars.add(new Pillar(Level.YEAR_LEVEL, lunar.getLunarYear()));
		pillars.add(new Pillar(Level.MONTH_LEVEL, lunar.getLunarMonth()));
		pillars.add(new Pillar(Level.DAY_LEVEL, lunar.getLunarDay()));
//		pillars.add(new Pillar(Level.HOUR_LEVEL, lunar.getLunarTime()));
		
		parseText="";
	}

	/**
	 * Setup.
	 */
	private void setup() {
		// setup change
		changeDiagrams = diagrams.getChangedDiagrams();

		resolver=new DefaultResolver(this);		

	}
	
	/**
	 * 是否有变卦
	 * Checks for changed.
	 * 
	 * @return true, if successful
	 */
	public boolean hasChanged(){
		return diagrams.isChanged();
	}
	
	
	/**
	 * @param pillars
	 */
	private void analyseFourSeasons(EightDiagrams diagrams, List<Pillar> pillars) {
		if (diagrams != null) {
			for (int i = 0; i < 6; i++) {
				Yao yao = diagrams.getYao(i);
				// if (yao.getLevel().equals(Level.CHANGED_LEVEL)) {
				for (int k = 0, m = pillars.size(); k < m; k++) {
					yao.addRelation(pillars.get(k));
				}
				// }
			}
		}
	}

	/**
	 * Sets the lunar.
	 * 
	 * @param lunar the lunar to set
	 */
	public void setLunar(Lunar lunar) {
		this.lunar = lunar;
	}

	/**
	 * Gets the lunar.
	 * 
	 * @return the lunar
	 */
	public Lunar getLunar() {
		return lunar;
	}

	/**
	 * Sets the diagrams.
	 * 
	 * @param diagrams the diagrams to set
	 */
	public void setDiagrams(EightDiagrams diagrams) {
		this.diagrams = diagrams;
	}

	/**
	 * Gets the diagrams.
	 * 
	 * @return the diagrams
	 */
	public EightDiagrams getDiagrams() {
		return diagrams;
	}

	/**
	 * Gets the problem.
	 * 
	 * @return the problem
	 */
	public String getProblem() {
		return problem;
	}

	/**
	 * Gets the solar.
	 * 
	 * @return the solar
	 */
	public String getSolarString() {
		return Lunar.CHINESE_TIME_FORMAT.format(lunar.getCalendar().getTime());
	}

	/**
	 * Gets the lunar string.
	 * 
	 * @return the lunar string
	 */
	public String getLunarString() {
		return lunar.getLunarString();
	}

	/**
	 * Gets the lunar term.
	 * 
	 * @return the lunar term
	 */
	public String getLunarTerm() {
		return lunar.getLunarTerm();
	}

	/**
	 * Gets the gan zhi string.
	 * 
	 * @return the gan zhi string
	 */
	public String getGanZhiString() {
		return lunar.getLunarYear().toString() + "年    "
				+ lunar.getLunarMonth().toString() + "月    "
				+ lunar.getLunarDay().toString() + "日    "
				+ lunar.getLunarTime().toString() + "时";
	}

	/**
	 * Gets the empty string.
	 * 
	 * @return the empty string
	 */
	public String getEmptyString() {
		return lunar.getEmptyString();
	}

	/**
	 * Gets the six beings.
	 * 
	 * @return the six beings
	 */
	public SixBeings getSixBeings() {
		return lunar.getLunarDay().getSterm().getSixBeings();
	}


	/**
	 * Gets the change diagrams.
	 * 
	 * @return the change diagrams
	 */
	public EightDiagrams getChangeDiagrams() {
		return changeDiagrams;
	}

	/**
	 * Parses the diagram.
	 * 
	 * @param diagrams the diagrams
	 * 
	 * @return the string[]
	 * 
	 * @throws DataException the data exception
	 */
	public static String[] parseDiagram(EightDiagrams diagrams)
			throws DataException, IOException {
		String[] parseStrings = new String[] { "", "", "", "", "", "", "", "" };// title
		// yaos[6]
		// yaos[5]
		// yaos[4]
		// yaos[3]
		// yaos[2]
		// yaos[1]
		if (diagrams != null) {
			int p = parseStrings.length - 1;
			parseStrings[p] += diagrams.getName();
			if (diagrams.isSixOpposition()) {
				parseStrings[p] += "(六冲)";
			}
			if (diagrams.isSixCoopration()) {
				parseStrings[p] += "(六合)";
			}
			if (diagrams.isLoaf()) {
				parseStrings[p] += "(游魂)";
			}
			if (diagrams.isReturn()) {
				parseStrings[p] += "(归魂)";
			}
			parseStrings[p] += " :" + diagrams.getBelongName() + "宫";

			for (int i = 0; i < 6; i++) {
				parseStrings[i] = diagrams.getYao(i).toString();
				if ((i) == diagrams.getSelf()) {
					parseStrings[i] += "　世";
				} else if ((diagrams.getSelf() + 3) % 6 == (i)) {
					parseStrings[i] += "　应";
				} else {
					parseStrings[i] += "　　";
				}
			}

		}
		return parseStrings;
	}


	/**
	 * @param text
	 */
	public void setProblem(String problem) {
		this.problem = problem;
	}

	/**
	 * 
	 */
	public void save() throws IOException {
		// System.out.println(getProblem());
		// for(int i=0;i<6;i++){
		// System.out.println(getDiagrams().getYao(i));
		// }
		// System.out.println(getLunar().getCalendar());
		// Element element=doc.getDefaultRootElement();
		// for(int i=0;i<element.getElementCount();i++){
		// System.out.println(element.getElement(i));
		// }

		// try {
		// ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new
		// File("some.txt")));
		// doc=(StyledDocument) ois.readObject();
		// ois.close();
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } catch (ClassNotFoundException e) {
		// e.printStackTrace();
		// }
		//		
		// try {
		// System.out.println(doc.getText(0, 100));
		// } catch (BadLocationException e) {
		// e.printStackTrace();
		// }

		LiuyaoDataManager manager;
		try {
			manager = LiuyaoDataManager.getInstance();
			manager.saveLiuyao(this);
		} catch (DataException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return
	 */
	public String getID() {
		return id;
	}

	/**
	 * @return
	 */
	public String getParseText() {
		return parseText;
	}

	/**
	 * @param string
	 */
	public void setParseText(String parseText) {
		this.parseText = parseText;
	}

	/**
	 * 以字符串形式返回排盘结果
	 * @return
	 */
	public String[] paiPan() throws DataException, IOException {
		String[] listModel=new String[9];
		

		String[] diaStrings = parseDiagram(diagrams);
		String[] chasStrings = parseDiagram(changeDiagrams);
		SixBeings being = getSixBeings();
		
		listModel[1] = getGanZhiString() + "(" + getEmptyString() + ")";
		listModel[2] = "六神　　伏神　　　 "+ diaStrings[diaStrings.length - 1] + "　　　　" + chasStrings[chasStrings.length - 1];

		List<org.divine.app.liuyao.Yao> hideList = diagrams
					.getYaos(Level.HIDE_LEVEL);// 伏神
		for (int i = 5; i >= 0; i--) {
			String yaoString = "";
			yaoString += being.next(i + 6).getName() + "　";// 六神
			

			boolean insertHide = false;
//			if (diagrams.hasHideYao()) {// 伏神
				for (Yao yao:hideList) {
//					Yao yao = iterator.next();
					if (yao.getIndex() == i) {
						// doc.insertString(
						// doc.getLength(),
						// yao.getRelative()
						// + yao
						// .getBranch()
						// .toString(),
						// EMPHASIS_STYLE);
						yaoString += yao.getRelative()
								+ yao.getBranch().toString();
						insertHide = true;
					}
				}
//			}
			if (!insertHide) {
				// doc.insertString(doc.getLength(), "\t",
				// EMPHASIS_STYLE);
				yaoString += "　　　　";
			}
			// 爻
			// doc.insertString(doc.getLength(),
			// diaStrings[i]
			// + "\t", EMPHASIS_STYLE);
			// doc.insertString(doc.getLength(),
			// chasStrings[i] + "\n", EMPHASIS_STYLE);
			yaoString += diaStrings[i] + "　" + chasStrings[i];
			listModel[8 - i] = yaoString;// 
		}

		return listModel;
	}

	/**
	 * 
	 */
	public void resolve() {
		resolver.start();
	}

	/**
	 * 
	 */
	public void addResolverListener(ResolverListener l) {
		resolver.addResolverListener(l);		
	}

	/**
	 * @param pillarmonth2
	 * @return
	 */
	public Item getPillar(int index) {
		return pillars.get(index);
	}
	
	public Iterator<Pillar> pillarIterator(){
		return pillars.iterator();
	}


}
