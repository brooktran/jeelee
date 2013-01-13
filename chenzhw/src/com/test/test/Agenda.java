package com.test.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


/**
 * 
 * @author Brook Tran. Email: brook.tran.c@gmail.com
 * @version Ver 1.0 2008-11-10 新建
 * @since agenda Ver 1.0
 * 
 */
public class Agenda {

	/**
	 * 所有议程的Map
	 */
	private Map<String, ArrayList<AgendaData>> agendaMap;

	public Agenda() {
		agendaMap = new HashMap<String, ArrayList<AgendaData>>();
	}

	/**
	 * 添加新的议程
	 * 
	 * @param username
	 *            议程发起人
	 * @param other
	 *            被预约人
	 * @param start
	 *            议程开始时间
	 * @param end
	 *            议程结束时间
	 * @param title
	 *            议程标识
	 * @return true 如果添加成功
	 * @throw IllegalCommand 如果与现有议程时间不重叠
	 */
	public boolean addAgenda(String username, String other, Date start,
			Date end, String title) throws IllegalCommand {

		AgendaData tmpAgenda = new AgendaData(other, start, end, title);

		// 检查议程时间是否重叠
		byte userAgenda = checkAgenda(username, tmpAgenda);
		byte otherAgenda = checkAgenda(other, tmpAgenda);

		if (userAgenda == -1) {// 是否与现有议程时间不重叠
			throw new IllegalCommand("用户" + username + "议程时间重叠");
		}
		if (otherAgenda == -1) {
			throw new IllegalCommand("用户" + other + "议程时间重叠");
		}
		return true;
	}

	public void addUserAgenda(String username, AgendaData tmpAgenda) {
		// 添加新的议程
		Iterator<Entry<String, ArrayList<AgendaData>>> it = agendaMap
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<AgendaData>> e = (Entry<String, ArrayList<AgendaData>>) it
					.next();
			if (username == e.getKey()) {
				e.getValue().add(tmpAgenda);// 添加新议程
			}
		}
	}

	public void addNewAgenda(String username, AgendaData tmpAgenda) {// 议程中还没有该用户的议程信息（插入新的议程信息）
		ArrayList<AgendaData> tmpAgendaList = new ArrayList<AgendaData>();
		tmpAgendaList.add(tmpAgenda);
		agendaMap.put(username, tmpAgendaList);// 添加新议程
	}

	public void addOther(String username, AgendaData tmpAgenda) {
		// 则添加被预约人
		Iterator<Entry<String, ArrayList<AgendaData>>> it = agendaMap
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<AgendaData>> e = 
				(Entry<String, ArrayList<AgendaData>>) it.next();
			if (username == e.getKey()) {
				e.getValue();// 添加新议程
			}
		}
	}

	/**
	 * 作用:检查议程时间是否相互重叠
	 * 
	 * @param username
	 *            用户名
	 * @param start
	 *            议程开始时间
	 * @param end
	 *            议程结束时间
	 * @return 0:议程时间相同; -1议程时间交错重叠;1议程时间不重叠;
	 */
	public byte checkAgenda(String username, AgendaData tmpAgenda) {
		if (!agendaMap.containsKey(username)) {
			return 1;// 用户议程表为空
		}
		Iterator<Entry<String, ArrayList<AgendaData>>> it = agendaMap
				.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, ArrayList<AgendaData>> e = (Entry<String, ArrayList<AgendaData>>) it
					.next();
			if (username == e.getKey()) {// 找到用户username
				List<AgendaData> agendaList = e.getValue();
				for (AgendaData a : agendaList) {
					if (tmpAgenda.getStart().equals(a.getStart())
							&& tmpAgenda.getEnd().equals(a.getEnd())) {
						if (tmpAgenda.getTitle().equals(a.getTitle()))
							return 0;// 议程相同
						else
							return -1;// 议程重叠
					} else if (tmpAgenda.getStart().after(a.getStart())
							|| (tmpAgenda.getEnd().before(a.getEnd()))) {// 是否重叠?
						return -1;// 议程重叠
					}
				}
				return 1;// 找到用户username并且时间不重叠
			}// /~if(username==e.getKey()
		}// /~while(it.hasNext()
		return 1;// 找不到用户名，则添加新的用户议程
	}
}
