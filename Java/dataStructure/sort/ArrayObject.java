package cn.lxr.dataStructure.sort;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

public class ArrayObject<T> implements Cloneable{

	protected T[] arr;
	protected int nElems;

	public ArrayObject() {
		super();
		this.arr = (T[]) new Object[10];
	}

	public ArrayObject(int max) {
		super();
		this.arr = (T[]) new Object[max];
	}

	public ArrayObject(T[] array) {
		super();
		this.arr = array;
		this.nElems = array.length;
	}
	
	@Override
	public String toString() {
		return arr.toString();
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// 深拷贝
		ArrayObject<T> arrObj = (ArrayObject<T>) super.clone();
		arrObj.arr = this.arr.clone();
		return arrObj;
	}
	
	/**
	 * 长度
	 */
	public int size() {
		return this.nElems;
	}

	/**
	 * 插入
	 */
	public void insert(T t) {
		arr[nElems] = t;
		nElems++;
	}

	/**
	 * 显示内容
	 */
	public void display() {
		Arrays.stream(arr).forEach(System.out::println);
	}

	/**
	 * 根据下标查找 返回内容
	 */
	public T get(int i) {
		if (i > nElems) {
			return null;
		}
		return arr[i];
	}

	/**
	 * 设置内容
	 */
	public void set(int i, T t) {
		if (i > nElems) {
			throw new ArrayIndexOutOfBoundsException();
		} else {
			arr[i] = t;
		}
	}

	/**
	 * 根据内容查找 返回下标
	 */
	public int find(T t) {
		int searchKey;
		for (searchKey = 0; searchKey < nElems; searchKey++) {
			if (arr[searchKey] == t) {
				return searchKey;
			}
		}
		return -1;
	}

	/**
	 * 删除
	 */
	public void delete(T t) {
		int delKey;
		for (delKey = 0; delKey < nElems; delKey++) {
			if (arr[delKey] == t) {
				break;
			}
		}
		if (delKey == nElems) {
			return;
		}
		for (int i = delKey; i < nElems; i++) {
			arr[i] = arr[i + 1];
		}
		nElems--;
	}

	/**
	 * 冒泡排序
	 * <p>两两比较，两两交换，一次循环得到极值</p>
	 */
	public void bubbleSort() {
		LocalDateTime start = LocalDateTime.now();
		for (int i = nElems; i > 0; i--) {
			for (int j = 0; j < i - 1; j++) {
				if (compare(arr[j], arr[j + 1]) > 0) {
					swap(j, j + 1);
				}
			}
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println("bubbleSort处理" + nElems + "个数据，用时：" + ChronoUnit.MILLIS.between(start, end) + "毫秒");
	}

	/**
	 * 选择排序
	 * <p>两两比较，记录极值，最后交换，一次循环得到极值</p>
	 */
	public void selectSort() {
		LocalDateTime start = LocalDateTime.now();
		int minKey;
		for (int i = 0; i < nElems - 1; i++) {
			minKey = i;
			for (int j = i; j < nElems - 1; j++) {
				if (compare(arr[j + 1], arr[minKey]) < 0) {
					minKey = j + 1;
				}
			}
			swap(i, minKey);
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println("selectSort处理" + nElems + "个数据，用时：" + ChronoUnit.MILLIS.between(start, end) + "毫秒");
	}
	
	/**
	 * 插入排序
	 * <p>按顺序比较，确保已比较的数据有序</p>
	 */
	public void insertSort() {
		LocalDateTime start = LocalDateTime.now();
		T tempVal;
		int j;
		for (int i = 1; i < nElems; i++) {
			tempVal = arr[i];
			for (j = i; j > 0 && compare(tempVal, arr[j - 1]) < 0; j--) {//当与左边数据比较且tempVal值更小时
				arr[j] = arr[j - 1];
			}
//			for (j = i; j > 0; j--) {//当与左边数据比较时
//				if (compare(tempVal, arr[j - 1]) >= 0) {//数值较大时退出比较
					//break的时候没有j--，所以退出循环时的j正好是tempVal应插入的下标
//					break;
//				}
//				arr[j] = arr[j - 1];
//			}
			arr[j] = tempVal; 
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println("insertSort处理" + nElems + "个数据，用时：" + ChronoUnit.MILLIS.between(start, end) + "毫秒");
	}
	
	/**
	 * 交换两个成员
	 */
	public void swap(int key1, int key2) {
		if (key1 < 0 || key1 >= nElems) {
			throw new ArrayIndexOutOfBoundsException();
		}
		if (key2 < 0 || key2 >= nElems) {
			throw new ArrayIndexOutOfBoundsException();
		}
		T temp = arr[key1];
		arr[key1] = arr[key2];
		arr[key2] = temp;
	}

	/**
	 * 比较大小
	 */
	protected int compare(T t1, T t2) {
		if (t1 instanceof Integer && t2 instanceof Integer) {
			int num1 = (Integer) t1;
			int num2 = (Integer) t2;
			if (num1 > num2) {
				return 1;
			} else if (num1 == num2) {
				return 0;
			} else {
				return -1;
			}
		}
		return 999;
	}
}
