package cn.lxr.designModel.proxy;

public class InterfaceOneImpl implements InterfaceOne {

	@Override
	public void action() {
		System.out.println("我实现了接口方法，我是被代理类...");
	}

}
