package cn.lxr.productor_consumer.method3;

import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Productor implements Runnable {

	private BlockingQueue queue;

    public Productor(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Random random = new Random();
                int i = random.nextInt();
                System.out.println("生产者" + Thread.currentThread().getName() + "生产数据" + i);
                queue.put(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
