package cn.lxr.util.baseUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SortUtil {

	public static void main(String[] args) {
		int[] array = {13,23,8,5,62,41};
//		array = insertSort(array);
//		array = sheelSort(array);
//		array = selectSort(array);
//		array = heapSort(array);
//		array = bubbleSort(array);
//		array = quickSort(array);
		array = mergeSort(array);
//		array = cardinalSort(array);
		System.out.println(Arrays.toString(array));
	}
	
	/**
	 * 直接插入排序
	 * @param array
	 */
	public static int[] insertSort(int[] array){
		int length=array.length;//数组长度，将这个提取出来是为了提高速度。
		int insertNum;//要插入的数
		for(int i=1;i<length;i++){//插入的次数
		    insertNum=array[i];//要插入的数
			int j=i-1;//已经排序好的序列元素个数
			while(j>=0&&array[j]>insertNum){//序列从后到前循环，将大于insertNum的数向后移动一格
			      array[j+1]=array[j];//元素移动一格
			      j--;
		    }
		  	array[j+1]=insertNum;//将需要插入的数放在要插入的位置。
		}
		return array;
	}
	
	/**
	 * 希尔排序
	 * @param array
	 * @return 
	 */
	public static int[] sheelSort(int[] array){
		int d  = array.length;
		while (d != 0) {
			d = d / 2;
			for (int x = 0; x < d; x++) {//分的组数
				for (int i = x + d; i < array.length; i += d) {//组中的元素，从第二个数开始
					int j = i - d;//j为有序序列最后一位的位数
					int temp = array[i];//要插入的元素
					for (; j >= 0 && temp < array[j]; j -= d) {//从后往前遍历。
					   array[j + d] = array[j];//向后移动d位
					}
				    array[j + d] = temp;
				}
			}
		}
		return array;
	}
	
	/**
	 * 简单选择排序
	 * @param array
	 */
	public static int[] selectSort(int[] array){
		int length = array.length;
		for (int i = 0; i < length; i++) {//循环次数
			int key = array[i];
			int position = i;
			for (int j = i + 1; j < length; j++) {//选出最小的值和位置
				if (array[j] < key) {
					key = array[j];
					position = j;
				}
			}
			array[position]=array[i];//交换位置
			array[i]=key;
		}
		return array;
	}
	
	/**
	 * 堆排序
	 * @param array
	 */
	public static int[] heapSort(int[] array){
		System.out.println("开始排序");
		int arrayLength=array.length;
		//循环建堆  
		for(int i=0;i < arrayLength;i++){
			//建堆  
			buildMaxHeap(array,arrayLength-1-i);
			//交换堆顶和最后一个元素  
			swap(array,0,arrayLength-1-i);
			System.out.println(Arrays.toString(array));
		}
		return array;
	}

	private static void swap(int[] data, int i, int j) {
		int tmp=data[i];
		data[i]=data[j];
		data[j]=tmp;
	}

	//对data数组从0到lastIndex建大顶堆  
	private static void buildMaxHeap(int[] data, int lastIndex) {
		//从lastIndex处节点（最后一个节点）的父节点开始  
		for(int i=(lastIndex-1)/2;i>=0;i--){
			//k保存正在判断的节点  
			int k=i;
			//如果当前k节点的子节点存在  
			while(k*2+1<=lastIndex){
				//k节点的左子节点的索引  
				int biggerIndex=2*k+1;
				//如果biggerIndex小于lastIndex，即biggerIndex+1代表的k节点的右子节点存在  
				if(biggerIndex < lastIndex){
					//若果右子节点的值较大  
					if(data[biggerIndex] < data[biggerIndex+1]){
						//biggerIndex总是记录较大子节点的索引  
						biggerIndex++;
					}
				}
				//如果k节点的值小于其较大的子节点的值  
				if(data[k] < data[biggerIndex]){
					//交换他们  
					swap(data,k,biggerIndex);
					//将biggerIndex赋予k，开始while循环的下一次循环，重新保证k节点的值大于其左右子节点的值  
					k=biggerIndex;
				}else{
					break;
				}
			}
		}
	}
	
	/**
	 * 冒泡排序
	 * @param array
	 * @return 
	 */
	public static int[] bubbleSort(int[] array){
		int length=array.length;
		int temp;
		for(int i=0;i<length;i++){
			for(int j=0;j<length-1;j++){
				if(array[j]>array[j+1]){
					temp=array[j];
					array[j]=array[j+1];
					array[j+1]=temp;
				}
			}
		}
		return array;
	}
	
	/**
	 * 快速排序
	 * @param numbers
	 * @param start 起始下标
	 * @param end 结束下标
	 */
	public static int[] quickSort(int[] numbers, int start, int end) {   
		if (start < end) {   
			int base = numbers[start]; // 选定的基准值（第一个数值作为基准值）   
			int temp; // 记录临时中间值   
			int i = start, j = end;   
			do {   
				while ((numbers[i] < base) && (i < end)){
					i++;   
				}
				while ((numbers[j] > base) && (j > start)){
					j--;   
				}
				if (i <= j) {   
					temp = numbers[i];   
					numbers[i] = numbers[j];   
					numbers[j] = temp;   
					i++;   
					j--;  
				} 
		   } while (i <= j);   
		   if (start < j){
			   quickSort(numbers, start, j);   
		   }
		   if (end > i){
			   quickSort(numbers, i, end);   
		   }
		}
		return numbers;
	}
	
	/**
	 * 快速排序
	 * @param array
	 */
	public static int[] quickSort(int[] array) {
		return quickSort(array, 0, array.length-1);
	}
	
	/**
	 * 归并排序
	 * @param numbers
	 * @param left
	 * @param right
	 */
	public static int[] mergeSort(int[] numbers){ 
		return mergeSort(numbers, 0, numbers.length-1);
	}
	
	/**
	 * 归并排序
	 * @param numbers
	 */
	public static int[] mergeSort(int[] numbers, int left, int right){   
		int t = 1;// 每组元素个数   
		int size = right - left + 1;   
		while (t < size) {   
			int s = t;// 本次循环每组元素个数   
			t = 2 * s;   
			int i = left;   
			while (i + (t - 1) < size) {   
				merge(numbers, i, i + (s - 1), i + (t - 1));   
				i += t;   
		    }   
			if (i + (s - 1) < right) {
				merge(numbers, i, i + (s - 1), right);   
			}
		} 
		return numbers;
	}   

	private static void merge(int[] data, int p, int q, int r){   
		int[] B = new int[data.length];   
		int s = p;   
		int t = q + 1;   
		int k = p;   
		while (s <= q && t <= r) {   
			if (data[s] <= data[t]) {   
				B[k] = data[s];   
			    s++;   
			} else {   
			    B[k] = data[t];   
			    t++;   
			}   
			k++;   
		}   
		for (int Q = 0; Q <= r-k+1; Q++) {
			if (s == q + 1) {
				B[k++] = data[t++];   
			}
			else {
				B[k++] = data[s++];   
			}
		}
		for (int i = p; i <= r; i++) {
			data[i] = B[i];   
		}
	}
	
	/**
	 * 基数排序
	 * @param array
	 * @return
	 */
	public static int[] cardinalSort(int[] array){
		//首先确定排序的趟数;     
		int max = array[0];
		for (int i = 1; i < array.length; i++) {
			if (array[i] > max) {
		        max = array[i];
		    }
		}
		int time = 0;
		//判断位数;     
		while (max > 0) {
		    max /= 10;
		    time++;
		}
		//建立10个队列;     
		List<ArrayList<Integer>> queue = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 10; i++) {
		    ArrayList<Integer> queue1 = new ArrayList<Integer>();
		    queue.add(queue1);
		}
		//进行time次分配和收集;     
		for (int i = 0; i < time; i++) {
			//分配数组元素;     
			for (int j = 0; j < array.length; j++) {
				//得到数字的第time+1位数;   
				int x = array[j] % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
				ArrayList<Integer> queue2 = queue.get(x);
				queue2.add(array[j]);
				queue.set(x, queue2);
		    }
			int count = 0;//元素计数器;     
			//收集队列元素;     
			for (int k = 0; k < 10; k++) {
				while (queue.get(k).size() > 0) {
					ArrayList<Integer> queue3 = queue.get(k);
					array[count] = queue3.get(0);
					queue3.remove(0);
					count++;
				}
		    }
		}
		return array;
	}
}
