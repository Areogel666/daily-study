package cn.lxr.dataStructure.queue;

/**
 * 优先级队列
 * @author admin
 *
 */
public class QueuePriority {

	private int maxSize;
	private long[] queArray;
	private int nItems;
	
	public QueuePriority(int s) {
		maxSize = s;
		queArray = new long[maxSize];
		nItems = 0;
	}
	
	public void insert(long item) {
		int j;
		if (nItems == 0) { //如果没元素则直接插入
			queArray[nItems++] = item;
		} else { //如果有则排序插入
			for (j = nItems - 1; j >= 0; j--) {
				if (queArray[j] < item) {
					queArray[j + 1] = queArray[j];
				} else {
					break;
				}
			}
			queArray[j + 1] = item;
			nItems++;
		}
	}
	
	public long remove() {
		return queArray[--nItems];
	}
	
	public long peekMin() {
		return queArray[nItems - 1];
	}
	
	public boolean isEmpty() {
		return nItems == 0;
	}

	public boolean isFull() {
		return nItems == maxSize;
	}
	
}
