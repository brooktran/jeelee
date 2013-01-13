package hw.db.exp1;

//消费者线程
public class Consumer extends Thread {
	private Buffer buffer;

	private int number;

	public Consumer(Buffer buffer, int number) {
		this.buffer = buffer;
		this.number = number;
	}

	public void run() {
		int i = 0;
		for (;;) {
			int v = buffer.get(i);
			System.out.println("消费者#" + number + "取" + v);
		}
	}
}