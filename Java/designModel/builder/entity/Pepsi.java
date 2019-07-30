package cn.lxr.builder.entity;

import cn.lxr.builder.service.ColdDrink;

public class Pepsi extends ColdDrink {

	@Override
	public String name() {
		return "Pepsi";
	}

	@Override
	public float price() {
		return 20.0f;
	}

}
