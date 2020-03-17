package cn.lxr.designModel.singleton;

/**
 * 双重校验锁法 
 * 线程安全
 * @author admin
 *
 */
public class DoubleLockDemo {
	private volatile static DoubleLockDemo instance;

	private DoubleLockDemo() {
		System.out.println("Singleton has loaded");
	}

	public static DoubleLockDemo getInstance() {

		if (instance == null) {

			synchronized (DoubleLockDemo.class) {

				if (instance == null) {

					instance = new DoubleLockDemo();

				}

			}

		}

		return instance;

	}
}
