package cn.lxr.dataStructure.test;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection; 
import java.util.Random;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import cn.lxr.dataStructure.arrayList.ArrayObject;
import junit.framework.TestCase;

public class ArrayTest {

	@Test
	@Ignore
	public void testRandomSort() throws CloneNotSupportedException {
//		Integer[] array = { 4, 2, 325, 23, 435, 65, 234, 28, 3, 39, 64 };
		int count = 40000;
		Integer[] array = new Integer[count];
		randomIntegerArray(array);
		Integer[] clone = array.clone();
		ArrayObject<Integer> arrObj = new ArrayObject<Integer>(array);
		ArrayObject<Integer> arrObj1 = (ArrayObject<Integer>) arrObj.clone();
		ArrayObject<Integer> arrObj2 = (ArrayObject<Integer>) arrObj.clone();
		arrObj.bubbleSort();
//		arrObj.display();
		arrObj1.selectSort();
//		arrObj1.display();
		arrObj2.insertSort();
//		arrObj2.display();
		this.selectSort(array);
		this.insertSort(clone);
//		TestCase.assertEquals("1,2排序不一致", arrObj.get(0), arrObj1.get(0));
//		Assert.assertEquals("2,3排序不一致", arrObj1.get(0), arrObj2.get(0));
		
	}
	
	@Test
	@Ignore
	public void testHalfOrderSort() throws CloneNotSupportedException {
//		Integer[] array = { 4, 2, 325, 23, 435, 65, 234, 28, 3, 39, 64 };
		int count = 20000;
		Integer[] array = new Integer[count];
		Integer[] array1 = new Integer[count/2];
		randomIntegerArray(array);
		randomIntegerArray(array1);
//		Arrays.sort(array);
		Arrays.sort(array1);
		/*================数组拼接（测试半有序数组）===============*/
		Integer[] arrayCopy = Arrays.copyOf(array, count * 3 / 2);
		for (int i = 0; i < array1.length; i++) {
			arrayCopy[count + i] = array1[i];
		}
		/*=======================================================*/
		ArrayObject<Integer> arrObj = new ArrayObject<Integer>(arrayCopy);
		ArrayObject<Integer> arrObj1 = (ArrayObject<Integer>) arrObj.clone();
		ArrayObject<Integer> arrObj2 = (ArrayObject<Integer>) arrObj.clone();
		arrObj.bubbleSort();
//		arrObj.display();
		arrObj1.selectSort();
//		arrObj1.display();
		arrObj2.insertSort();
//		arrObj2.display();
		
	}

	@Test
	public void testShellSort() {
		int maxSize = 20;
		ArrayObject <Integer> arr = new ArrayObject<>(maxSize);
		for (int i = 0; i < maxSize; i++) {
			int n = (int) (Math.random() * 99);
			arr.insert(n);
		}
		arr.display();
		System.out.println("=====ShellSort=====");
		arr.shellSort();
		arr.display();
	}
	
	
	private void randomIntegerArray(Integer[] array) {
		Random random = new Random();
		for (int i = 0; i < array.length; i++) {
			array[i] = random.nextInt();
		}
	}
	
	
	/**
	 * 选择排序
	 * <p>两两比较，记录极值，最后交换，一次循环得到极值</p>
	 */
	public void selectSort(Integer[] arr) {
		LocalDateTime start = LocalDateTime.now();
		int minKey;
		for (int i = 0; i < arr.length - 1; i++) {
			minKey = i;
			for (int j = i; j < arr.length - 1; j++) {
				if (arr[j + 1] < arr[minKey]) {
					minKey = j + 1;
				}
			}
			swap(i, minKey, arr);
		}
		LocalDateTime end = LocalDateTime.now();
		System.out.println("selectSort处理" + arr.length + "个数据，用时：" + ChronoUnit.MILLIS.between(start, end) + "毫秒");
	}
	
	/**
	 * 插入排序
	 * <p>按顺序比较，确保已比较的数据有序</p>
	 */
	public void insertSort(Integer[] arr) {
		LocalDateTime start = LocalDateTime.now();
		Integer tempVal;
		int j;
		for (int i = 1; i < arr.length; i++) {
			tempVal = arr[i];
			for (j = i; j > 0 && tempVal < arr[j - 1]; j--) {//当与左边数据比较且tempVal值更小时
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
		System.out.println("insertSort处理" + arr.length + "个数据，用时：" + ChronoUnit.MILLIS.between(start, end) + "毫秒");
	}
	
	/**
	 * 交换两个成员
	 */
	public void swap(int key1, int key2, Integer[] arr) {
		Integer temp = arr[key1];
		arr[key1] = arr[key2];
		arr[key2] = temp;
	}
}
