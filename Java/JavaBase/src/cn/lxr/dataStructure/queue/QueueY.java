package cn.lxr.dataStructure.queue;
/**
 * 无nElem队列
 * @author admin
 *
 */
public class QueueY {

	private long[] arr;
	private int maxSize;
	private int front; //队头
	private int rear; //队尾
	
	public QueueY(int maxSize) {
		this.maxSize = maxSize + 1; // size+1
		this.arr = new long[this.maxSize];
		this.rear = -1;
		this.front = 0;
	}
	
	/**
	 * 插入
	 */
	public void insert(long val) {
		if (isFull()) {
			System.out.println("队列已满");
			return;
		}
		if (rear == maxSize - 1) { // 循环队列
			rear = -1;
		}
		arr[++rear] = val;
	}
	
	/**
	 * 移除
	 */
	public long remove() {
		if (isEmpty()) {
			System.out.println("队列为空");
			return 0;
		}
		long temp = arr[front++];
		if (front == maxSize) { //循环队列
			front = 0;
		}
		return temp;
	}
	
	/**
	 * 查看
	 */
	public long peek() {
		if (isEmpty()) {
			System.out.println("队列为空");
		}
		return arr[front];
	}
	
	/**
	 * 是否为空
	 */
	public boolean isEmpty() {
		return (rear + 1 == front) || (rear + 1 == front + maxSize);
	}
	
	/**
	 * 是否已满
	 */
	public boolean isFull() {
		return (rear + 2 == front) || (rear + 2 == front + maxSize);
	}
	
	/**
	 * 长度
	 */
	public int size() {
		if (rear >= front) {
			return rear - front + 1;
		} else {
			return (maxSize - front) + (rear + 1);
		}
	}
}
