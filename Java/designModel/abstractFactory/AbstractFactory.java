package cn.lxr.abstractFactory;

import cn.lxr.abstractFactory.entity.Color;
import cn.lxr.abstractFactory.entity.Shape;

public abstract class AbstractFactory {

	abstract Color getColor(String color);
	abstract Shape getShape(String shape);
}
