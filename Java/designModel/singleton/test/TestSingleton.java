package cn.lxr.singleton.test;

import cn.lxr.singleton.CasSingletonDemo;
import cn.lxr.singleton.DoubleLockDemo;
import cn.lxr.singleton.HungryDemo;
import cn.lxr.singleton.LazyDemo;
import cn.lxr.singleton.LazySynDemo;
import cn.lxr.singleton.LocalDemo;
import cn.lxr.singleton.SingletonEnum;
import cn.lxr.singleton.StaticInnerDemo;

public class TestSingleton {

	private static LazyDemo instance1;
	private static LazyDemo instance2;

	public static void main(String[] args) {
		System.out.println("===============懒汉非线程安全================");
		//多次测试发现有时true有时false，证明线程不安全
		Thread t1 = new Thread((Runnable)() -> {
			instance1 = LazyDemo.getInstance();
			System.out.println("over1");
		});
		Thread t2 = new Thread((Runnable)() -> {
			instance2 = LazyDemo.getInstance();
			System.out.println("over2");
		});
		t1.start();
		t2.start();
		try {
			Thread.currentThread().sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(instance1);
		System.out.println(instance2);
		System.out.println(instance1 == instance2);
		
		System.out.println("===============懒汉线程安全================");
		System.out.println(LazySynDemo.getInstance() == LazySynDemo.getInstance());
		System.out.println("===============饿汉线程安全================");
		System.out.println(HungryDemo.getInstance() == HungryDemo.getInstance());
		System.out.println("===============使用ThreadLocal实现单例模式================");
		System.out.println(LocalDemo.getInstance() == LocalDemo.getInstance());
		System.out.println("===============枚举================");
		System.out.println(SingletonEnum.INSTANCE == SingletonEnum.INSTANCE);
		System.out.println("===============静态内部类加载================");
		System.out.println(StaticInnerDemo.getInstance() == StaticInnerDemo.getInstance());
		System.out.println("===============双重校验锁法================");
		System.out.println(DoubleLockDemo.getInstance() == DoubleLockDemo.getInstance());
		System.out.println("===============使用CAS锁实现（线程安全）================");
		System.out.println(CasSingletonDemo.getInstance() == CasSingletonDemo.getInstance());
	}
}
