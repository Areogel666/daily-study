package cn.lxr.dataStructure.queue;

public class QueueX {

	private long[] arr;
	private int maxSize;
	private int front; //队头
	private int rear; //队尾
	private int nElem; //当前元素数量
	
	public QueueX(int maxSize) {
		this.maxSize = maxSize;
		this.arr = new long[maxSize];
		this.rear = -1;
		this.front = 0;
		this.nElem = 0;
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
		nElem++;
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
		nElem--;
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
		return (nElem == 0);
	}
	
	/**
	 * 是否已满
	 */
	public boolean isFull() {
		return (nElem == maxSize);
	}
	
	/**
	 * 长度
	 */
	public int size() {
		return nElem;
	}
}
