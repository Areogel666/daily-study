package cn.lxr.productor_consumer.method1;

import java.util.List;
import java.util.Random;

public class Productor implements Runnable {

	private List<Integer> list;
	private int maxLength;

	public Productor(List list, int maxLength) {
		this.list = list;
		this.maxLength = maxLength;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (list) {
				try {
					while (list.size() == maxLength) {
						System.out.println("生产者" + Thread.currentThread().getName() + "  list以达到最大容量，进行wait");
						list.wait();
						System.out.println("生产者" + Thread.currentThread().getName() + "  退出wait");
					}
					Random random = new Random();
					int i = random.nextInt();
					System.out.println("生产者" + Thread.currentThread().getName() + " 生产数据" + i);
					list.add(i);
					list.notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
