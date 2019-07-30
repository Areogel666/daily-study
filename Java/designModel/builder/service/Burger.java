package cn.lxr.builder.service;

public abstract class Burger implements Item {

	@Override
	public abstract String name();

	@Override
	public abstract float price();

	@Override
	public Packing packing() {
		return new Wrapper();
	}
}
