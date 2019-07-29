package cn.lxr.productor_consumer.method2;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

	private List<Integer> list;
    private Lock lock;
    private Condition full;
	private Condition empty;

    public Consumer(List list, Lock lock) {
        this.list = list;
        this.lock = lock;
        this.full = lock.newCondition();
		this.empty = lock.newCondition();
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            Condition condition = lock.newCondition();
            try {
                while (list.isEmpty()) {
                    System.out.println("消费者" + Thread.currentThread().getName() + "  list为空，进行wait");
                    empty.await();
                    System.out.println("消费者" + Thread.currentThread().getName() + "  退出wait");
                }
                Integer element = list.remove(0);
                System.out.println("消费者" + Thread.currentThread().getName() + "  消费数据：" + element);
                full.signalAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

}
