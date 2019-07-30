package cn.lxr.builder.entity;

import cn.lxr.builder.service.ColdDrink;

public class Coke extends ColdDrink {

	@Override
	public String name() {
		return "Coke Cola";
	}

	@Override
	public float price() {
		return 18.0f;
	}

}
