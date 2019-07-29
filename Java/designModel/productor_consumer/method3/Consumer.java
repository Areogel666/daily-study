package cn.lxr.productor_consumer.method3;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

	private BlockingQueue queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Integer element = (Integer) queue.take();
                System.out.println("消费者" + Thread.currentThread().getName() + "正在消费数据" + element);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
