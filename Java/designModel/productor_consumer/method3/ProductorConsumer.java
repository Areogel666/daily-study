package cn.lxr.productor_consumer.method3;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用BlockingQueue实现生产者-消费者
 * 
 * @author admin
 *
 */
public class ProductorConsumer {

	private static LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue<>();

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(15);
		for (int i = 0; i < 5; i++) {
			service.submit(new Productor(queue));
		}
		for (int i = 0; i < 10; i++) {
			service.submit(new Consumer(queue));
		}
	}

}
