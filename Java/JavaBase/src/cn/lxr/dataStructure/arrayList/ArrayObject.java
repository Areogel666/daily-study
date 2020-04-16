package cn.lxr.dataStructure.arrayList;

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
	 * 归并排序
	 * <p>将数组拆成两部分，分别排序，然后合并成为一个有序数组，不断递归直到拆分的数组只剩一个数据项</p>
	 */
	public void mergeSort() {
		T[] workSpace = (T[]) new Object[nElems];
		recMergeSort(workSpace, 0, nElems - 1);
	}
	
	/**
	 * 递归排序数组
	 * @param lowerBound 
	 * @param upperBound 
	 * @param workSpace 空数组，用于排序操作
	 */
	private void recMergeSort(T[] workSpace, int lowerBound, int upperBound) {
		if (lowerBound == upperBound) { 
			return;
		} else {
			int mid = (lowerBound + upperBound) / 2;
			recMergeSort(workSpace, lowerBound, mid);
			recMergeSort(workSpace, mid + 1, upperBound);
			merge(workSpace, lowerBound, mid + 1, upperBound);
		}
	}
	
	/**
	 * 合并排序两个有序数组
	 * @param workSpace
	 * @param lowPtr 第一个数组index
	 * @param highPtr 第二个数组index
	 * @param upperBound
	 */
	private void merge(T[] workSpace, int lowPtr, int highPtr, int upperBound) {
		int workPtr = 0; // workSpace index
		int lowerBound = lowPtr;
		int mid = highPtr - 1;
		while (lowPtr <= mid && highPtr <= upperBound) {
			if (compare(arr[lowPtr], arr[highPtr]) <= 0) {
				workSpace[workPtr++] = arr[lowPtr++];
			} else {
				workSpace[workPtr++] = arr[highPtr++];
			}
		}
		while (lowPtr <= mid) {
			workSpace[workPtr++] = arr[lowPtr++];
		}
		while (highPtr <= upperBound) {
			workSpace[workPtr++] = arr[highPtr++];
		}
		// 将工作组数赋值给arr
		for (int i = 0; i < upperBound - lowerBound + 1; i++) {
			arr[lowerBound + i] = workSpace[i];
		}
	}
	
	/**
	 * 希尔排序
	 * <p>加大间隔做插入排序</p>
	 * 比较顺序：(n=6) 4<->0 5<->1 6<->2; 1<->0 2<->1<->0 ... 6<->5<->4<->3<->2<->1<->0
	 */
	public void shellSort() {
		int inner, outer;
		T temp;
		// 间隔序列  1, 4, 13, 40, 121, 364...
		int h = 1;
		while (h < nElems / 3) {
			h = 3 * h + 1;
		}
		// 按间隔序列循环排序
		while (h >= 1) {
			for (outer = h; outer < nElems; outer++) {
				temp = arr[outer];
				inner = outer;
				while (inner > h - 1 && compare(arr[inner - h], temp) > 0) {
					arr[inner] = arr[inner - h];
					inner -= h;
				}
				arr[inner] = temp;
			}
			h = (h - 1) / 3;
		}
	}
	
	/**
	 * 划分
	 * <p>把数据按照特定值分为两组</p>
	 */
	public int partitionIt(int left, int right, T pivot) {
		int leftPtr = left - 1;
		int rightPtr = right;
		while (true) {
			while (leftPtr < right && compare(arr[++leftPtr], pivot) < 0) {
				; // 左边小于pivot时不操作，最终存储第一个需要交换的leftPtr
			}
			while (rightPtr > left && compare(arr[--rightPtr], pivot) > 0) {
				; // 右边大于pivot时不操作，最终存储第一个需要交换的rightPtr
			}
			if (leftPtr >= rightPtr) {
				break;
			} else {
				swap(leftPtr, rightPtr);
			}
		}
		swap(leftPtr, right); // 这一步对于划分本身是不用的，但是快排时需要将枢纽swap到排序好的位置上
		return leftPtr;
	}
	
	/**
	 * 快速排序
	 * <p>把数据递归划分，每次划分将枢纽swap到排序好的位置</p>
	 */
	public void quickSort() {
		recQuickSort(0, nElems - 1);
	}

	/**
	 * 快速排序递归方法
	 * @param left
	 * @param right
	 */
	private void recQuickSort(int left, int right) {
		if (right <= left) {
			return;
		} else {
			T pivot = arr[right];
			int partition = partitionIt(left, right, pivot);
			recQuickSort(left, partition - 1);
			recQuickSort(partition + 1, right);
		}
	}
	/**
	 * 快速排序（加入三数据取中）
	 * <p>把数据递归划分，每次划分将枢纽swap到排序好的位置</p>
	 */
	public void quickSort1() {
		recQuickSort1(0, nElems - 1);
	}
	
	/**
	 * 快速排序递归方法
	 * @param left
	 * @param right
	 */
	private void recQuickSort1(int left, int right) {
		int size = right - left + 1;
		if (size < 10) { // 小于10个数据，使用插入排序
			insertionSort(left, right);
		} else {
			T median = medianOf3(left, right);
			int partition = partitionIt1(left, right, median);
			recQuickSort1(left, partition - 1);
			recQuickSort1(partition + 1, right);
		}
	}
	
	/**
	 * 划分
	 * <p>把数据按照特定值分为两组</p>
	 */
	public int partitionIt1(int left, int right, T pivot) {
		int leftPtr = left;
		int rightPtr = right - 1;
		while (true) {
			while (compare(arr[++leftPtr], pivot) < 0) {
				; // 左边小于pivot时不操作，最终存储第一个需要交换的leftPtr
			}
			while (compare(arr[--rightPtr], pivot) > 0) {
				; // 右边大于pivot时不操作，最终存储第一个需要交换的rightPtr
			}
			if (leftPtr >= rightPtr) {
				break;
			} else {
				swap(leftPtr, rightPtr);
			}
		}
		swap(leftPtr, right - 1); // 这一步对于划分本身是不用的，但是快排时需要将枢纽swap到排序好的位置上
		return leftPtr;
	}
	
	
	/**
	 * 三数据取中（尽量选择中值作为枢纽）
	 * @return
	 */
	private T medianOf3(int left, int right) {
		int center = (left + right) / 2;
		if (compare(arr[left], arr[center]) > 0) {
			swap(left, center);
		}
		if (compare(arr[left], arr[right]) > 0) {
			swap(left, right);
		}
		if (compare(arr[center], arr[right]) > 0) {
			swap(center, right);
		}
		swap(center, right - 1);
		return arr[right - 1];
	}
	
	private void insertionSort(int left, int right) {
		int in, out;
		for (out = left + 1; out <= right; out++) {
			T temp = arr[out];
			in = out;
			while (in > left && compare(arr[in - 1], temp) >= 0) {
				arr[in] = arr[in - 1];
				--in;
			}
			arr[in] = temp;
		}
	}
}
