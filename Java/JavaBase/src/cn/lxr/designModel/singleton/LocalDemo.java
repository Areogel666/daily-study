package cn.lxr.designModel.singleton;
/**
 * 使用ThreadLocal实现单例模式（线程安全）
 * @author admin
 *
 */
public class LocalDemo {
	private static final ThreadLocal tlLocalDemo = new ThreadLocal() {
		@Override
		protected LocalDemo initialValue() {
			return new LocalDemo();
		}
	};

	/**
	 * Get the focus finder for this thread.
	*/
	public static LocalDemo getInstance() {
		return (LocalDemo) tlLocalDemo.get();
	}

	// enforce thread local access
	private LocalDemo() {
		
	}

}
