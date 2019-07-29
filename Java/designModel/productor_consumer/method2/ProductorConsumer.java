package cn.lxr.productor_consumer.method2;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 使用Lock中Condition的await/signalAll实现生产者-消费者
 * @author admin
 *
 */
public class ProductorConsumer {

	private static ReentrantLock lock = new ReentrantLock();
	
	public static void main(String[] args) {
		LinkedList linkedList = new LinkedList();
	    ExecutorService service = Executors.newFixedThreadPool(15);
	    for (int i = 0; i < 5; i++) {
	        service.submit(new Productor(linkedList, 8, lock));
	    }
	    for (int i = 0; i < 10; i++) {
	        service.submit(new Consumer(linkedList, lock));
	    }
	}

}
