package cn.lxr.designModel.singleton;
/**
 * 懒汉式（线程不安全）
 * @author admin
 *
 */
public class LazyDemo {
	private static LazyDemo instance;

	private LazyDemo() {

	}

	public static LazyDemo getInstance() {
		if (instance == null) {
			instance = new LazyDemo();
		}
		return instance;
	}
}
