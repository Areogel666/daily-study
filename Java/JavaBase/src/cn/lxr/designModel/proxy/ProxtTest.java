package cn.lxr.designModel.proxy;

public class ProxtTest {
	public static void main(String[] args) {
		//创建被代理类的对象
		InterfaceOneImpl interfaceOneImpl = new InterfaceOneImpl();
		//创建实现了InvocationHandler的代理类对象，然后调用其中的setObj方法完成两项操作
		//①将被代理类对象传入，运行时候调用的是被代理类重写的方法
		//②返回一个类对象，通过代理类对象执行接口中的方法
		DynamicProxyHandler dynamicProxyHandler = new DynamicProxyHandler();
		InterfaceOne interfaceOne = (InterfaceOne) dynamicProxyHandler.setObj(interfaceOneImpl);
		//调用该方法运行时都会转为对DynamicProxyHandler中的invoke方法的调用
		interfaceOne.action(); 
		
	}
}
