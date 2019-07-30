package cn.lxr.builder.controller;

import cn.lxr.builder.entity.ChickenBurger;
import cn.lxr.builder.entity.Coke;
import cn.lxr.builder.entity.Pepsi;
import cn.lxr.builder.entity.VegBurger;

public class MealBuilder {
	public Meal prepareVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new VegBurger());
		meal.addItem(new Coke());
		return meal;
	}

	public Meal prepareNonVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new ChickenBurger());
		meal.addItem(new Pepsi());
		return meal;
	}
}
