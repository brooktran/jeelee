package hw.db.exp1;

import java.io.*;

//生产者与消费者共享的缓冲区,必须实现读写同步
public class Buffer {
	private int contents;

	private boolean available = false;
	
	/**
	 * 作用:从缓冲区获取Value 也就是Contents
	 * @param value
	 * @return 缓冲区的数据
	 */
	public synchronized int get(int value) {
		while (!available) {//当缓冲区没有数据时
			try {
				wait();
			} catch (InterruptedException exc) {
			}
		}
		available = false;
		System.out.println("消费" + contents);
		notifyAll();//Wakes up all threads that are waiting on this object's monitor. 
		return contents;
	}

	/**
	 * 作用:将Contents放入缓冲区
	 * @param value
	 */
	public synchronized void put(int value) {
		while (available) {//当缓冲区存在数据时等待 
			try {
				wait();
			} catch (InterruptedException exc) {
			}
		}
		contents = value;
		available = true;
		System.out.println("生产" + contents);
		notifyAll();
	}
}
