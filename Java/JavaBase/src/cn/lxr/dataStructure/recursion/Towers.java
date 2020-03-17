package cn.lxr.dataStructure.recursion;

/**
 * 汉诺塔问题
 * @author admin
 *
 */
public class Towers {

	public static int runCount = 0;
	
	/**
	 * 
	 * @param topN 盘数
	 * @param from 源塔座
	 * @param inter 中介塔座
	 * @param to 目标塔座
	 */
	public static void doTower(int topN, char from, char inter, char to) {
		runCount ++;
		if (topN == 1) {
			System.out.println("Disk 1 from " + from + " to " + to);
		} else {
			doTower(topN - 1, from, to, inter); // 子塔移动到中介塔座
			System.out.println("Disk " + topN + " from " + from + " to " + to); // 最后一个盘子从源塔座移到目标塔座
			doTower(topN - 1, inter, from, to); // 子塔移动到目标塔座
		}
	}
}
