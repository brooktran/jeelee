package hw.db.exp1;

//生产者线程
public class Producer extends Thread {
	private Buffer buffer;

	private int number;

	public Producer(Buffer buffer, int number) {
		this.buffer = buffer;
		this.number = number;
	}

	public void run() {
		int i = 0;
		for (;;) {
			buffer.put(i);
			System.out.println("生产者#" + number + "放" + i++);
			try {
				sleep((int) (Math.random() * 100));
			} catch (InterruptedException exc) {
			}
		}
	}
}