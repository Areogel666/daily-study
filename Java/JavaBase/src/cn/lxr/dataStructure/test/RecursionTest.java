package cn.lxr.dataStructure.test;

import org.junit.Test;

import cn.lxr.dataStructure.arrayList.ArrayObject;
import cn.lxr.dataStructure.recursion.Towers;

public class RecursionTest {

	@Test
	public void testPower() {
		Towers.doTower(6, 'A', 'B', 'C');
		System.out.println("共移动" + Towers.runCount + "次"); // 2n + 1 次
	}
	
	@Test
	public void testMergeSort() {
		ArrayObject<Integer> arr = new ArrayObject<>(16);
		arr.insert(22);
		arr.insert(23);
		arr.insert(52);
		arr.insert(74);
		arr.insert(2);
		arr.insert(58);
		arr.insert(93);
		arr.insert(12);
		arr.insert(35);
		arr.insert(78);
		arr.display();
		arr.mergeSort();
		System.out.println("排序后...");
		arr.display();
	}
}
