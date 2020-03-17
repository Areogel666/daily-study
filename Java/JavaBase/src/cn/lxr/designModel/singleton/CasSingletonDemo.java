package cn.lxr.designModel.singleton;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 使用CAS锁实现（线程安全）
 * 
 * @author admin
 *
 */
public class CasSingletonDemo {

	/** 利用AtomicReference */
	private static final AtomicReference<CasSingletonDemo> INSTANCE = new AtomicReference<CasSingletonDemo>();

	/**
	 * 私有化
	 */
	private CasSingletonDemo() {

	}

	/**
	 * 用CAS确保线程安全
	 */
	public static final CasSingletonDemo getInstance() {

		for (;;) {
			CasSingletonDemo current = INSTANCE.get();

			if (current != null) {

				return current;

			}

			current = new CasSingletonDemo();

			if (INSTANCE.compareAndSet(null, current)) {

				return current;

			}

		}

	}

	public static void main(String[] args) {

		CasSingletonDemo singleton1 = CasSingletonDemo.getInstance();

		CasSingletonDemo singleton2 = CasSingletonDemo.getInstance();

		System.out.println(singleton1 == singleton2);

	}

}
