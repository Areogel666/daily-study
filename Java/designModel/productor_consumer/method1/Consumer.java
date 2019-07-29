package cn.lxr.productor_consumer.method1;

import java.util.List;

public class Consumer implements Runnable {

	private List<Integer> list;

	public Consumer(List list) {
		this.list = list;
	}

	@Override
	public void run() {
		while (true) {
			synchronized (list) {
				try {
					while (list.isEmpty()) {
						System.out.println("消费者" + Thread.currentThread().getName() + "  list为空，进行wait");
						list.wait();
						System.out.println("消费者" + Thread.currentThread().getName() + "  退出wait");
					}
					Integer element = list.remove(0);
					System.out.println("消费者" + Thread.currentThread().getName() + "  消费数据：" + element);
					list.notifyAll();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
