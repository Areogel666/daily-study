package cn.lxr.designModel.singleton;

/**
 * 静态内部类加载
 * 线程安全
 * @author admin
 *
 */
public class StaticInnerDemo {

	//静态内部类
	private static class SingletonHolder {

		private static StaticInnerDemo instance = new StaticInnerDemo();

	}

	private StaticInnerDemo() {

		System.out.println("Singleton has loaded");

	}

	public static StaticInnerDemo getInstance() {

		return SingletonHolder.instance;

	}
}
