package cn.lxr.designModel.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyHandler implements InvocationHandler {
	//接口的一个引用，多态的特性会使得在程序运行的时候，它实际指向的是实现它的子类对象
	private InterfaceOne interfaceOne;
	//我们使用Proxy类的静态方法newProxyInstance方法，将代理对象伪装成那个被代理的对象
	/**
	* ①这个方法会将targetOne指向实际实现接口的子类对象
	* ②根据被代理类的信息返回一个代理类对象
	*/
	public Object setObj(InterfaceOne interfaceOne) {
		this.interfaceOne = interfaceOne;
		// public static Object newProxyInstance(ClassLoader loader, //被代理类的类加载器
		// 		Class<?>[] interfaces, //被代理类实现的接口
		// 		InvocationHandler h) //实现InvocationHandler的代理类对象
		return Proxy.newProxyInstance(interfaceOne.getClass().getClassLoader(),interfaceOne.getClass().getInterfaces(),this);
	}
	
	/**
	 * 当通过代理类的对象发起对接口被重写的方法的调用的时候，都会转换为对invoke方法的调用
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("这是我代理之前要准备的事情......");
//		这里回想一下在静态代理的时候，我们显示指定代理类需要执行的是被代理类的哪些方法；
//		而在这里的动态代理实现中，我们并不知道代理类会实现什么方法，他是根据运行时通过反射来
//		知道自己要去指定被代理类的什么方法的
		Object returnVal = method.invoke(this.interfaceOne, args);//这里的返回值，就相当于真正调用的被代理类方法的返回值
		System.out.println("这是我代理之后要处理的事情......");
		return returnVal;
	}

}
