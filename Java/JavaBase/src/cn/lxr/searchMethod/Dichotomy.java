package cn.lxr.searchMethod;
/**
 * 二分法
 * @author admin
 *
 */

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.Test;

public class Dichotomy {

	@Test
	public void test() {
		//必需排序
		Random random = new Random();
		int[] array = random.ints(100, 0, 100).sorted().toArray();
		Arrays.stream(array).forEach(System.out :: println);
		
		int searchNum = 26;//待查找数字
		int dichotomy = dichotomy(array, searchNum);
		if (dichotomy >= 0) {
			System.out.println("查到下标：" + dichotomy + "，数字：" + array[dichotomy]);
		} else {
			System.out.println("未查到");
		}
	}
	
	private int dichotomy(int[] array, int searchNum) {
		int maxCount = array.length - 1;
		int minCount = 0;
		int compareCount = 0;
		int count = 0; // 记录循环次数
		while (true) {
			count ++;
			compareCount = (maxCount + minCount) / 2;
			if (array[compareCount] == searchNum) {
				System.out.println("循环了" + count + "次");
				return compareCount;
			} else if (maxCount < minCount) {//未查到
				System.out.println("循环了" + count + "次");
				return -1; 
			} else if (array[compareCount] > searchNum){
				maxCount = compareCount - 1;
			} else {
				minCount = compareCount + 1;
			}
		}
	}
}
