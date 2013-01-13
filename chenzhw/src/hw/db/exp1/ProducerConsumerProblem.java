package hw.db.exp1;

//主程序
public class ProducerConsumerProblem {
	public static void main(String[] args) {
		Buffer buffer = new Buffer();//共享这个buffer
		new Producer(buffer, 101).start();
		new Consumer(buffer, 108).start();
		new Consumer(buffer, 109).start();
	}
}
