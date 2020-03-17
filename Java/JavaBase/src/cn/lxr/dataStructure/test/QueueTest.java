package cn.lxr.dataStructure.test;

import org.junit.Test;

import cn.lxr.dataStructure.queue.QueuePriority;
import cn.lxr.dataStructure.queue.QueueX;
import cn.lxr.dataStructure.queue.QueueY;

public class QueueTest {

	@Test
	public void testQueueBase() {
		QueueX queue = new QueueX(5);
		queue.insert(1);
		queue.insert(2);
		queue.insert(3);
		queue.insert(4);
		queue.remove();
		queue.remove();
		queue.insert(5);
		queue.insert(6);
		queue.insert(7);
		queue.insert(8);
		queue.insert(9);
		while (!queue.isEmpty()) {
			System.out.println(queue.remove());
		}
		QueueY queue1 = new QueueY(5);
		queue1.insert(1);
		queue1.insert(2);
		queue1.insert(3);
		queue1.insert(4);
		queue1.remove();
		queue1.remove();
		queue1.insert(5);
		queue1.insert(6);
		queue1.insert(7);
		queue1.insert(8);
		queue1.insert(9);
		while (!queue1.isEmpty()) {
			System.out.println(queue1.remove());
		}
		
		QueuePriority queue2 = new QueuePriority(5);
		queue2.insert(20);
		queue2.insert(30);
		queue2.insert(10);
		queue2.insert(50);
		queue2.insert(40);
		while (!queue2.isEmpty()) {
			long item = queue2.remove();
			System.out.println("item=" + item);
		}
	}
}
