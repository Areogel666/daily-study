package cn.lxr.productor_consumer.method2;

import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Productor implements Runnable {

	private List<Integer> list;
	private int maxLength;
	private Lock lock;
	private Condition full;
	private Condition empty;
	
	public Productor(List list, int maxLength, Lock lock) {
		this.list = list;
		this.maxLength = maxLength;
		this.lock = lock;
		this.full = lock.newCondition();
		this.empty = lock.newCondition();
	}

	@Override
	public void run() {
		while (true) {
			lock.lock();
			try {
				while (list.size() == maxLength) {
					System.out.println("生产者" + Thread.currentThread().getName() + "  list以达到最大容量，进行wait");
					full.await();
					System.out.println("生产者" + Thread.currentThread().getName() + "  退出wait");
				}
				Random random = new Random();
				int i = random.nextInt();
				System.out.println("生产者" + Thread.currentThread().getName() + " 生产数据" + i);
				list.add(i);
				empty.signalAll();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}

}
