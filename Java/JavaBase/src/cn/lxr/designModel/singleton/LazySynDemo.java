package cn.lxr.designModel.singleton;

/**
 * 懒汉式 线程安全
 * @author admin
 *
 */
public class LazySynDemo {
	private static LazySynDemo instance;

	private LazySynDemo() {

	}

	public static synchronized LazySynDemo getInstance() {
		if (instance == null) {
			instance = new LazySynDemo();
		}
		return instance;
	}
}
