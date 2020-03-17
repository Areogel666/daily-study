package cn.lxr.designModel.singleton;

/**
 * 饿汉模式 线程安全
 * 
 * @author admin
 */
public class HungryDemo {
	private final static HungryDemo instance = new HungryDemo();

	private HungryDemo() {

	}

	public static HungryDemo getInstance() {
		return instance;
	}

}
